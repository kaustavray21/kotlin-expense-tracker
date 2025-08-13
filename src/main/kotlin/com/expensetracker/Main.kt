package com.expensetracker

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.expensetracker.ui.AppUI
import com.expensetracker.viewmodel.ExpenseViewModel

fun main() = application {
    val viewModel = ExpenseViewModel()
    Window(onCloseRequest = ::exitApplication, title = "Expense Tracker") {
        AppUI(viewModel)
    }
}
