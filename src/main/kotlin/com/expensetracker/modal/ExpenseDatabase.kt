package com.expensetracker.model

import java.util.UUID

object ExpenseDatabase {
    private val expenses = mutableListOf<Expense>()

    fun addExpense(expense: Expense) {
        expenses.add(expense)
    }

    fun getAllExpenses(): List<Expense> = expenses.toList()

    fun deleteExpense(id: UUID) {
        expenses.removeIf { it.id == id }
    }
}