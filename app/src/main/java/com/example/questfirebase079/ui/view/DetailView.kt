@file:Suppress("DUPLICATE_LABEL_IN_WHEN")

package com.example.questfirebase079.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questfirebase079.model.Mahasiswa
import com.example.questfirebase079.navigation.DestinasiNavigasi
import com.example.questfirebase079.ui.CustomWidget.CustomTopAppBar
import com.example.questfirebase079.ui.ViewModel.DetailUiState
import com.example.questfirebase079.ui.ViewModel.DetailViewModel
import com.example.questfirebase079.ui.ViewModel.PenyediaViewModel

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Mahasiswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    nim: String,
    navigateBack: () -> Unit,
    onClick: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    viewModel.getMhs(nim)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getMhs(nim)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Mahasiswa")
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is DetailUiState.Loading -> {
                // Show loading state
                Text("Loading...", Modifier.fillMaxSize())
            }
            is DetailUiState.Success -> {
                val mahasiswa = (uiState as DetailUiState.Success).mahasiswa
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Display mahasiswa details
                    ItemDetailMhs(mahasiswa = mahasiswa)
                }
            }
            is DetailUiState.Error -> {
                // Show error state with a message
                Text(
                    text = "Error: Gagal memuat data. Silakan coba lagi.",
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.error
                )
            }

            is DetailUiState.Error -> TODO()
            DetailUiState.Loading -> TODO()
            is DetailUiState.Success -> TODO()
        }
    }
}

@Composable
fun ItemDetailMhs(
    modifier: Modifier = Modifier,
    mahasiswa: Mahasiswa
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailMhs(judul = "NIM", isinya = mahasiswa.nim)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMhs(judul = "Nama", isinya = mahasiswa.nama)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMhs(judul = "Alamat", isinya = mahasiswa.alamat)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMhs(judul = "Jenis Kelamin", isinya = mahasiswa.gender)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMhs(judul = "Kelas", isinya = mahasiswa.kelas)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMhs(judul = "Angkatan", isinya = mahasiswa.angkatan)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMhs(judul = "Judul Skripsi", isinya = mahasiswa.judulskripsi)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMhs(judul = "Pembimbing 1", isinya = mahasiswa.pembimbing1)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailMhs(judul = "Pembimbing 2", isinya = mahasiswa.pembimbing2)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ComponentDetailMhs(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp
            )
        )
    }
}