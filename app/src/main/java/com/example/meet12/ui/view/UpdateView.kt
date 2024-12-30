package com.example.meet12.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.meet12.ui.customwidget.CostumeTopAppBar
import com.example.meet12.ui.navigation.DestinasiNavigasi
import com.example.meet12.ui.viewmodel.InsertUiEvent
import com.example.meet12.ui.viewmodel.InsertUiState
import com.example.meet12.ui.viewmodel.PenyediaViewModel
import com.example.meet12.ui.viewmodel.UpdateViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModel = viewModel(
        factory = PenyediaViewModel.Factory
    )
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Edit Data Mahasiswa",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.updateUiState,
            onSiswaValueChange = viewModel::updateState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateData()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
        // Menampilkan pesan error jika ada
        viewModel.errorMessage?.let { message ->
            LaunchedEffect (message) {
                // Tampilkan pesan error jika ada
                delay(3000L) // Tampilkan selama 3 detik
                viewModel.resetSnackBarMessage()
            }
        }
    }
}



// Tambahkan objek untuk destinasi edit
object DestinasiEdit : DestinasiNavigasi {
    override val route = "item_edit"
    override val titleRes = "Edit Mahasiswa"
}