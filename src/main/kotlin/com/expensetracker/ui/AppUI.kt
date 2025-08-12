// com/expensetracker/ui/AppUI.kt
package com.expensetracker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expensetracker.viewmodel.TransactionViewModel

@Composable
fun AppUI(viewModel: TransactionViewModel) {
    var currentScreen: Screen by remember { mutableStateOf(Screen.Dashboard) }

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF1E1E1E)) {
        Row(modifier = Modifier.fillMaxSize()) {
            NavigationPanel(
                currentScreen = currentScreen,
                onNavigate = ::navigateTo
            )
            Box(modifier = Modifier.weight(1f)) {
                when (val screen = currentScreen) {
                    is Screen.Dashboard -> DashboardScreen(
                        viewModel = viewModel,
                        onNavigate = ::navigateTo
                    )
                    is Screen.Transactions -> TransactionListScreen(
                        viewModel = viewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    is Screen.Categories -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Categories Coming Soon", color = Color.White, fontSize = 24.sp)
                        }
                    }
                    is Screen.AddTransaction -> AddTransactionScreen(
                        viewModel = viewModel,
                        isIncome = screen.isIncome,
                        onBack = { navigateTo(Screen.Dashboard) }
                    )
                }
            }
        }
    }
}

// Keep NavigationPanel, NavigationItem, and AppButton composables the same as in Step 3
@Composable
fun NavigationPanel(currentScreen: Screen, onNavigate: (Screen) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(220.dp)
            .padding(16.dp)
            .background(Color(0xFF2C2C2E), shape = MaterialTheme.shapes.medium)
            .padding(vertical = 24.dp)
    ) {
        Text(
            text = "Expense Tracker",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(32.dp))
        NavigationItem(
            screen = Screen.Dashboard,
            isSelected = currentScreen is Screen.Dashboard,
            onClick = { onNavigate(Screen.Dashboard) }
        )
        NavigationItem(
            screen = Screen.Transactions,
            isSelected = currentScreen is Screen.Transactions,
            onClick = { onNavigate(Screen.Transactions) }
        )
        NavigationItem(
            screen = Screen.Categories,
            isSelected = currentScreen is Screen.Categories,
            onClick = { onNavigate(Screen.Categories) }
        )
    }
}

@Composable
fun NavigationItem(screen: Screen, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(end = 16.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
        )
        Spacer(Modifier.width(12.dp))
        Icon(
            imageVector = screen.icon,
            contentDescription = screen.title,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = screen.title,
            color = if (isSelected) Color.White else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun AppButton(text: String, onClick: () -> Unit) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF00BFA5))
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        )
    }
}