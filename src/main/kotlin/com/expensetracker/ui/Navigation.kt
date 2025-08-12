
package com.expensetracker.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val title: String, val icon: ImageVector) {
    data object Dashboard : Screen("Dashboard", Icons.Default.AccountBalanceWallet)
    data object Transactions : Screen("Transactions", Icons.Default.SwapHoriz)
    data object Categories : Screen("Categories", Icons.Default.Category)
    data class AddTransaction(val isIncome: Boolean) : Screen("Add Transaction", Icons.Default.SwapHoriz)
}