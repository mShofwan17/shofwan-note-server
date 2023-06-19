package me.project.data_source

import org.jetbrains.exposed.sql.Database

class DatabaseFactory {
    private var _database : Database ?= null
    val dbInstance get() = _database
    init {
        val jdbcURL = "jdbc:mysql://localhost:3306/db_shofwan_note"
        val driverName = "com.mysql.cj.jdbc.Driver"
        _database = Database.connect(
            url = jdbcURL,
            driver = driverName,
            user = "root",
            password = "123456nwm"
        )
    }
}