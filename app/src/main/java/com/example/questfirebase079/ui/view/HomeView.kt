package com.example.questfirebase079.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.questfirebase079.model.Mahasiswa
import com.example.questfirebase079.ui.CustomWidget.CustomTopAppBar
import com.example.questfirebase079.ui.ViewModel.HomeUiState
import com.example.questfirebase079.ui.ViewModel.HomeViewModel
import com.example.questfirebase079.ui.ViewModel.PenyediaViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@JvmOverloads
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = "Title",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getMhs()
                }

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Mahasiswa"
                )
            }
        }
    ){
            innerPadding ->
        HomeStatus(
            homeUiState = viewModel.mhsUiState,
            retryAction = { viewModel.getMhs() },
            modifier = Modifier
                .padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.getMhs()
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Mahasiswa) -> Unit = {},
    onDetailClick: (String) -> Unit = {}
){
    when(homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = Modifier.fillMaxSize())

        is HomeUiState.Success ->
            MhsLayout(
                mahasiswa = homeUiState.mahasiswa,
                modifier = modifier.fillMaxWidth(),
                onDeleteClick = {
                    onDeleteClick(it)
                },
                onDetailClick = {
                    onDetailClick(it.nim)
                }
            )

        is HomeUiState.Error -> onErr(
            message = homeUiState.message.message?:"Error",
            retryAction ,
            modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Loading......")
    }
}

@Composable
fun onErr(
    message: String,
    retryAction: () -> Unit,modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}

@Composable
fun MhsLayout(
    mahasiswa: List<Mahasiswa>,
    modifier: Modifier = Modifier,
    onDeleteClick: (Mahasiswa) -> Unit = {},
    onDetailClick: (Mahasiswa) -> Unit
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(mahasiswa){mahasiswa ->
            MhsCard(
                mahasiswa = mahasiswa,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(mahasiswa) },
                onDeleteClick = {
                    onDeleteClick(mahasiswa)
                }
            )
        }
    }
}


@Composable
fun MhsCard(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier,
    onDeleteClick: (Mahasiswa) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = mahasiswa.nama,
                    style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(mahasiswa) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = mahasiswa.nim,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                )
            }
            Text(
                text = mahasiswa.kelas,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = mahasiswa.alamat,
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}

@Composable
fun deleteMhs(mahasiswa: Mahasiswa) {
    viewModelScope.launch {
        try {
            repository.deleteMhs(mahasiswa)
            getMhs()
        } catch (e: Exception) {
            mhsUiState = HomeUiState.Error(e)
        }
    }
}
