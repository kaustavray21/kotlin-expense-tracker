package com.expensetracker.viewmodel

import com.expensetracker.model.Expense
import com.expensetracker.model.ExpenseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal
import java.util.UUID

class ExpenseViewModel {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        _expenses.value = ExpenseDatabase.getAllExpenses()
    }

    fun addExpense(title: String, amount: BigDecimal, category: String) {
        val newExpense = Expense(title = title, amount = amount, category = category)
        ExpenseDatabase.addExpense(newExpense)
        loadExpenses()
    }

    fun deleteExpense(id: UUID) {
        ExpenseDatabase.deleteExpense(id)
        loadExpenses()
    }
}