package com.expensetracker.model

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class Expense(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val amount: BigDecimal,
    val category: String,
    val date: LocalDate = LocalDate.now()
)