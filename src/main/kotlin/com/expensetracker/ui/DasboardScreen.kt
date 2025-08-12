package com.expensetracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expensetracker.viewmodel.TransactionViewModel
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.FilledPointDrawer
import com.github.tehras.charts.line.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.line.renderer.yaxis.SimpleYAxisDrawer
import java.text.NumberFormat
import java.time.Month
import java.util.Locale

@Composable
fun DashboardScreen(
    viewModel: TransactionViewModel,
    onNavigate: (Screen) -> Unit
) {
    val balance by viewModel.balance.collectAsState()
    val monthlyExpenses by viewModel.monthlyExpenses.collectAsState()
    val recentTransactions by viewModel.transactions.collectAsState()

    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "IN")).apply {
        maximumFractionDigits = 2
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BalanceCard(
            balance = currencyFormatter.format(balance),
            onAddExpense = { onNavigate(Screen.AddTransaction(isIncome = false)) },
            onAddIncome = { onNavigate(Screen.AddTransaction(isIncome = true)) }
        )

        Spacer(Modifier.height(24.dp))

        ExpensesChart(monthlyExpenses)

        Spacer(Modifier.height(24.dp))

        RecentTransactions(recentTransactions.take(4))
    }
}

@Composable
private fun BalanceCard(balance: String, onAddExpense: () -> Unit, onAddIncome: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2E))
    ) {
        Column(Modifier.padding(24.dp)) {
            Text("BALANCE", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
            Text(balance, fontSize = 36.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Row {
                AppButton(text = "+ Add Expense", onClick = onAddExpense)
                Spacer(Modifier.width(16.dp))
                AppButton(text = "+ Add Income", onClick = onAddIncome)
            }
        }
    }
}

@Composable
private fun ExpensesChart(data: Map<Month, Double>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2E))
    ) {
        Column(Modifier.padding(24.dp)) {
            Text("EXPENSES", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(16.dp))
            if (data.isNotEmpty()) {
                val points = data.entries
                    .sortedBy { it.key.value }
                    .map { entry ->
                        LineChartData.Point(
                            value = entry.value.toFloat(),
                            label = entry.key.name.take(3)
                        )
                    }

                LineChart(
                    lineChartData = LineChartData(points = points),
                    modifier = Modifier.height(200.dp),
                    pointDrawer = FilledPointDrawer(color = Color(0xFF00BFA5)),
                    lineDrawer = SolidLineDrawer(color = Color(0xFF00BFA5)),
                    xAxisDrawer = SimpleXAxisDrawer(labelTextColor = Color.Gray),
                    yAxisDrawer = SimpleYAxisDrawer(labelTextColor = Color.Gray)
                )
            } else {
                Box(modifier = Modifier.height(200.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("No expense data for chart.", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
private fun RecentTransactions(transactions: List<com.expensetracker.model.Transaction>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2E))
    ) {
        Column(Modifier.padding(24.dp)) {
            Text("TRANSACTIONS", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(16.dp))
            if (transactions.isEmpty()) {
                Text("No recent transactions.", color = Color.Gray)
            } else {
                transactions.forEach {
                    TransactionRow(it)
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun TransactionRow(transaction: com.expensetracker.model.Transaction) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(transaction.date.toString(), color = Color.White, fontSize = 16.sp)
            Text(transaction.category, color = Color.Gray, fontSize = 14.sp)
        }
        val isIncome = transaction.amount > java.math.BigDecimal.ZERO
        Text(
            text = "${if (isIncome) "+" else "-"}₹${transaction.amount.abs()}",
            color = if (isIncome) Color(0xFF00BFA5) else Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}