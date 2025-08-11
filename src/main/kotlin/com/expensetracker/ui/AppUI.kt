package com.expensetracker.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.expensetracker.viewmodel.ExpenseViewModel

private sealed class Screen {
    data object List : Screen()
    data object Add : Screen()
}

@OptIn(ExperimentalMaterial3Api::class) // <-- ADD THIS LINE
@Composable
fun AppUI(viewModel: ExpenseViewModel) {
    var currentScreen: Screen by remember { mutableStateOf(Screen.List) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("💰 Expense Tracker") }
            )
        },
        floatingActionButton = {
            if (currentScreen is Screen.List) {
                FloatingActionButton(onClick = { currentScreen = Screen.Add }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add new expense"
                    )
                }
            }
        }
    ) { paddingValues ->
        when (currentScreen) {
            is Screen.List -> ExpenseListScreen(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
            is Screen.Add -> AddExpenseScreen(
                viewModel = viewModel,
                onBack = { currentScreen = Screen.List },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    }
}