// com/expensetracker/viewmodel/TransactionViewModel.kt
package com.expensetracker.viewmodel

import com.expensetracker.db.Database
import com.expensetracker.model.DatabaseManager
import com.expensetracker.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal
import java.time.Month
import java.util.UUID

class TransactionViewModel {

    private val db: Database = DatabaseManager.db
    private val transactionQueries = db.transactionQueries

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _balance = MutableStateFlow(BigDecimal.ZERO)
    val balance: StateFlow<BigDecimal> = _balance.asStateFlow()

    private val _monthlyExpenses = MutableStateFlow<Map<Month, Double>>(emptyMap())
    val monthlyExpenses: StateFlow<Map<Month, Double>> = _monthlyExpenses.asStateFlow()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        val transactionEntities = transactionQueries.selectAll().executeAsList()
        val allTransactions = transactionEntities.map { entity ->
            Transaction(
                id = entity.id,
                title = entity.title,
                amount = entity.amount,
                category = entity.category,
                date = entity.date
            )
        }
        _transactions.value = allTransactions
        calculateBalance()
        prepareChartData(allTransactions)
    }

    private fun calculateBalance() {
        val balanceQuery = transactionQueries.calculateBalance().executeAsOneOrNull()
        _balance.value = balanceQuery?.SUM ?: BigDecimal.ZERO
    }

    private fun prepareChartData(transactions: List<Transaction>) {
        val expensesByMonth = transactions
            .filter { it.amount < BigDecimal.ZERO }
            .groupBy { it.date.month }
            .mapValues { (_, transactions) ->
                transactions.sumOf { it.amount.abs().toDouble() }
            }
        _monthlyExpenses.value = expensesByMonth
    }

    fun addExpense(title: String, amount: BigDecimal, category: String) {
        val expenseAmount = amount.abs().negate()
        val newTransaction = Transaction(title = title, amount = expenseAmount, category = category)
        transactionQueries.insert(
            id = newTransaction.id,
            title = newTransaction.title,
            amount = newTransaction.amount,
            category = newTransaction.category,
            date = newTransaction.date
        )
        loadTransactions()
    }

    fun addIncome(title: String, amount: BigDecimal, category: String) {
        val incomeAmount = amount.abs()
        val newTransaction = Transaction(title = title, amount = incomeAmount, category = category)
        transactionQueries.insert(
            id = newTransaction.id,
            title = newTransaction.title,
            amount = newTransaction.amount,
            category = newTransaction.category,
            date = newTransaction.date
        )
        loadTransactions()
    }

    fun deleteTransaction(id: UUID) {
        transactionQueries.deleteById(id)
        loadTransactions()
    }
}