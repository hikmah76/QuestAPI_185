package com.example.meet12.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meet12.model.Mahasiswa
import com.example.meet12.ui.customwidget.CostumeTopAppBar
import com.example.meet12.ui.navigation.DestinasiNavigasi
import com.example.meet12.ui.viewmodel.DetailUiState
import com.example.meet12.ui.viewmodel.DetailViewModel
import com.example.meet12.ui.viewmodel.PenyediaViewModel

object DestinasiDetail : DestinasiNavigasi {
    override val route = "item_detail"
    override val titleRes = "Detail Mahasiswa"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    navigateToEdit: (String) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = PenyediaViewModel.Factory
    )
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val detailUiState = viewModel.detailUiState
    val showDialog = remember { mutableStateOf(false) } // State untuk menampilkan dialog konfirmasi hapus

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Detail Mahasiswa",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
    ) { innerPadding ->
        // Menampilkan BodyDetail yang berisi informasi mahasiswa
        BodyDetail(
            detailUiState = detailUiState,
            modifier = Modifier.padding(innerPadding),
            onEditClick = { nim ->
                navigateToEdit(nim)
            },
            onDeleteClick = {
                showDialog.value = true
            }
        )
    }

    if (showDialog.value) {
        DeleteConfirmationDialog(
            onConfirmDelete = {
                viewModel.deleteMhs()
                navigateBack()
            },
            onDismiss = { showDialog.value = false }
        )
    }
}

@Composable
fun BodyDetail(
    detailUiState: DetailUiState,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when (detailUiState) {
            is DetailUiState.Success -> {
                ItemDetail(
                    mahasiswa = detailUiState.mahasiswa,
                    modifier = Modifier.fillMaxWidth(),
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick
                )
            }
            is DetailUiState.Error -> Text(text = "Error: Data tidak ditemukan")
            is DetailUiState.Loading -> CircularProgressIndicator()
        }
    }
}

@Composable
fun ItemDetail(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier,
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ComponentDetail("NIM", mahasiswa.nim)
            ComponentDetail("Nama", mahasiswa.nama)
            ComponentDetail("Jenis Kelamin", mahasiswa.jenisKelamin)
            ComponentDetail("Alamat", mahasiswa.alamat)
            ComponentDetail("Kelas", mahasiswa.kelas)
            ComponentDetail("Angkatan", mahasiswa.angkatan)

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton (
                    onClick = { onEditClick(mahasiswa.nim) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Edit")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onDeleteClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(text = "Hapus")
                }
            }
        }
    }
}

@Composable
fun ComponentDetail(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onConfirmDelete: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Konfirmasi Hapus") },
        text = { Text("Apakah Anda yakin ingin menghapus data ini?") },
        modifier = modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirmDelete) {
                Text("Hapus", color = Color.Red)
            }
        }
    )
}
