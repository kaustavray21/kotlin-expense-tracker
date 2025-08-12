// com/expensetracker/Main.kt
package com.expensetracker

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.expensetracker.ui.AppUI
import com.expensetracker.viewmodel.TransactionViewModel

fun main() = application {
    val viewModel = TransactionViewModel()
    Window(onCloseRequest = ::exitApplication, title = "Expense Tracker") {
        AppUI(viewModel)
    }
}