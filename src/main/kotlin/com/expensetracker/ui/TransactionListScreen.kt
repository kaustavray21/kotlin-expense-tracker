// com/expensetracker/ui/TransactionListScreen.kt
package com.expensetracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.expensetracker.model.Transaction
import com.expensetracker.viewmodel.TransactionViewModel
import java.math.BigDecimal
import java.util.UUID

@Composable
fun TransactionListScreen(
    viewModel: TransactionViewModel,
    modifier: Modifier = Modifier
) {
    val transactions by viewModel.transactions.collectAsState()

    if (transactions.isEmpty()) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text("No transactions added yet. Tap '+' to add one!")
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(transactions, key = { it.id }) { transaction ->
                TransactionItem(
                    transaction = transaction,
                    onDeleteClick = viewModel::deleteTransaction,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction,
    onDeleteClick: (UUID) -> Unit,
    modifier: Modifier = Modifier
) {
    val amountColor = if (transaction.amount >= BigDecimal.ZERO) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
    val amountPrefix = if (transaction.amount >= BigDecimal.ZERO) "+" else ""

    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f).padding(vertical = 12.dp)) {
                Text(text = transaction.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = transaction.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$amountPrefix₹${transaction.amount.abs().toPlainString()}",
                    style = MaterialTheme.typography.titleLarge,
                    color = amountColor,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                IconButton(onClick = { onDeleteClick(transaction.id) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Transaction",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}