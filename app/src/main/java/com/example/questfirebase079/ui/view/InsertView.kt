package com.example.questfirebase079.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questfirebase079.ui.ViewModel.FormErrorState
import com.example.questfirebase079.ui.ViewModel.FormState
import com.example.questfirebase079.ui.ViewModel.InsertUiState
import com.example.questfirebase079.ui.ViewModel.InsertViewModel
import com.example.questfirebase079.ui.ViewModel.MahasiswaEvent
import com.example.questfirebase079.ui.ViewModel.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FormMahasiswa (
    mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    onValueChange: (MahasiswaEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val gender = listOf("Laki-laki", "Perempuan")
    val kelas = listOf("A", "B", "C", "D", "E")

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nama,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nama = it)) },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama") }
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nim,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nim = it)) },
            label = { Text("NIM") },
            isError = errorState.nim != null,
            placeholder = { Text("Masukkan NIM") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text (
            text = errorState.nim ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text("Jenis Kelamin")
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            gender.forEach { item ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mahasiswaEvent.gender == item,
                        onClick = { onValueChange(mahasiswaEvent.copy(gender = item)) }
                    )
                    Text(text = item)
                }
            }
        }
        Text (
            text = errorState.gender ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.alamat,
            onValueChange = { onValueChange(mahasiswaEvent.copy(alamat = it)) },
            label = { Text("Alamat") },
            isError = errorState.alamat != null,
            placeholder = { Text("Masukkan Alamat") }
        )
        Text (
            text = errorState.alamat ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            kelas.forEach{ item ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mahasiswaEvent.kelas == item,
                        onClick = { onValueChange(mahasiswaEvent.copy(kelas = item)) }
                    )
                    Text(text = item)
                }
            }
        }
        Text (
            text = errorState.kelas ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.angkatan,
            onValueChange = { onValueChange(mahasiswaEvent.copy(angkatan = it)) },
            label = { Text("Angkatan") },
            isError = errorState.angkatan != null,
            placeholder = { Text("Masukkan Angkatan") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text (
            text = errorState.angkatan ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.judulskripsi,
            onValueChange = { onValueChange(mahasiswaEvent.copy(judulskripsi = it)) },
            label = { Text("Judul Skripsi") },
            isError = errorState.judulskripsi != null,
            placeholder = { Text("Masukkan Judul Skripsi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text (
            text = errorState.judulskripsi ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.pembimbing1,
            onValueChange = { onValueChange(mahasiswaEvent.copy(pembimbing1 = it)) },
            label = { Text("Nama Pembimbing 1") },
            isError = errorState.pembimbing1 != null,
            placeholder = { Text("Masukkan Nama Pembimbing 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text (
            text = errorState.pembimbing1 ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.pembimbing2,
            onValueChange = { onValueChange(mahasiswaEvent.copy(pembimbing2 = it)) },
            label = { Text("Nama Pembimbing 2") },
            isError = errorState.pembimbing2 != null,
            placeholder = { Text("Masukkan Nama Pembimbing 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text (
            text = errorState.pembimbing2 ?: "",
            color = Color.Red
        )

    }
}

@Composable
fun InserBodyMhs (
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: InsertUiState,
    onClick: () -> Unit = {},
    homeUiState: FormState
) {
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormMahasiswa (
            mahasiswaEvent = uiState.insertUiEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = homeUiState !is FormState.Loading
        ) {
            if (homeUiState is FormState.Loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 8.dp)

                )
                Text("Loading")
            } else {
                Text("Insert")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState
    val uiEvent = viewModel.uiEvent
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope =  rememberCoroutineScope()

    LaunchedEffect (uiState) {
        when (uiState) {
            is FormState.Success -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        uiState.message,
                    )
                }
                delay(700)
                onNavigate()
                viewModel.resetSnackBarMessage()
            }

            is FormState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        uiState.message,
                    )
                }
            }
            else -> Unit
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Insert Mahasiswa") },
                navigationIcon = {
                    Button (onClick = onBack){
                        Text("Back")
                    }
                }
            )
        }
    ) { paddding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddding)
                .padding(16.dp)
        ) {
            InserBodyMhs(
                uiState = uiEvent,
                homeUiState = uiState,
                onValueChange = { updatedEvennt ->
                    viewModel.updateUiEvent(updatedEvennt)
                },
                onClick = {
                    if (viewModel.validateFields()) {
                        viewModel.insertMhs()
                    }
                }
            )
        }
    }
}