package me.project.data_source

import kotlinx.coroutines.Dispatchers
import me.project.data_source.table.NoteTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseFactory {
    init {
        val jdbcURL = "jdbc:h2:file:./build/db"
        val driverName = "org.h2.Driver"
        val database = Database.connect(
            url = jdbcURL,
            driver = driverName
        )

        transaction(database) {
            SchemaUtils.create(NoteTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T {
        return newSuspendedTransaction(Dispatchers.IO) { block() }
    }
}