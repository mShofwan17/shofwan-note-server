package me.project.data_source.table

import org.jetbrains.exposed.sql.Table

object NoteTable : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title",128)
    val description = varchar("description",1024)
    val timestamp = varchar("timestamp",128)

    override val primaryKey = PrimaryKey(id)
}