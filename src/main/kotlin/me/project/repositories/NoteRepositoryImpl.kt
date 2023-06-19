package me.project.repositories

import me.project.data_source.DatabaseFactory
import me.project.data_source.table.NoteTable
import me.project.models.Note
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class NoteRepositoryImpl(
    private val db: DatabaseFactory
) : NoteRepository {
    override val instance: Database?
        get() = db.dbInstance

    private fun resultRowToData(row: ResultRow): Note {
        NoteTable.apply {
            return Note(
                id = row[id].value,
                title = row[title],
                description = row[description],
                dateTime = row[timestamp].toInt()
            )
        }
    }

    override suspend fun getListNotes(): List<Note> {
        return transaction(instance) {
            NoteTable.selectAll().map(::resultRowToData)
        }
    }

    override suspend fun getNoteById(id: Int?): Note? {
        return transaction(instance) {
            id?.let {
                NoteTable
                    .select(NoteTable.id eq it)
                    .map(::resultRowToData)
                    .singleOrNull()
            }
        }
    }

    override suspend fun addNote(note: Note): Note? {
        return transaction(instance) {
            val addData = NoteTable.insert { row ->
                note.title?.let { row[title] = it }
                note.description?.let { row[description] = it }
                note.dateTime?.let { row[timestamp] = it.toString() }
            }
            addData.resultedValues?.singleOrNull()?.let(::resultRowToData)
        }
    }

    override suspend fun updateNote(note: Note): Boolean {
        NoteTable.apply {
            return transaction(instance) {
                update({ this@apply.id eq note.id }) { row ->
                    note.title?.let { row[title] = it }
                    note.description?.let { row[description] = it }
                } > 0
            }
        }
    }

    override suspend fun searchByName(name: String): List<Note> {
        NoteTable.apply {
            return transaction(instance) {
                select { title like "%$name%" }
                    .map(::resultRowToData)
            }
        }

    }
}