package com.expensetracker.model

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.expensetracker.db.Database
import com.expensetracker.db.Transaction
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

object DatabaseManager {
    private val bigDecimalAdapter = object : ColumnAdapter<BigDecimal, String> {
        override fun decode(databaseValue: String) = BigDecimal(databaseValue)
        override fun encode(value: BigDecimal) = value.toPlainString()
    }

    private val dateAdapter = object : ColumnAdapter<LocalDate, String> {
        override fun decode(databaseValue: String) = LocalDate.parse(databaseValue)
        override fun encode(value: LocalDate) = value.toString()
    }

    private val uuidAdapter = object : ColumnAdapter<UUID, String> {
        override fun decode(databaseValue: String) = UUID.fromString(databaseValue)
        override fun encode(value: UUID) = value.toString()
    }

    val db: Database by lazy {
        val driver = JdbcSqliteDriver("jdbc:sqlite:expenses.db")
        Database.Schema.create(driver)
        Database(
            driver = driver,
            TransactionAdapter = Transaction.Adapter(
                amountAdapter = bigDecimalAdapter,
                dateAdapter = dateAdapter,
                idAdapter = uuidAdapter
            )
        )
    }
}