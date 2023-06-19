package me.project.repositories

import me.project.data_source.DatabaseFactory
import me.project.data_source.table.NoteTable
import me.project.models.Note
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class NoteRepositoryImpl(
    private val db: DatabaseFactory
) : NoteRepository {
    private fun resultRowToData(row: ResultRow): Note {
        return Note(
            id = row[NoteTable.id],
            title = row[NoteTable.title],
            description = row[NoteTable.description],
            dateTime = row[NoteTable.timestamp].toInt()
        )
    }

    override suspend fun getListNotes(): List<Note> {
        return db.dbQuery {
            NoteTable.selectAll().map(::resultRowToData)
        }
    }

    override suspend fun getNoteById(id: Int?): Note? {
        return db.dbQuery {
            id?.let {
                NoteTable
                    .select(NoteTable.id eq id)
                    .map(::resultRowToData)
                    .singleOrNull()
            }
        }
    }

    override suspend fun addNote(note: Note): Note? {
        return db.dbQuery {
            val addData = NoteTable.insert { row ->
                note.title?.let { row[title] = note.title }
                note.description?.let { row[description] = note.description }
                note.dateTime?.let { row[timestamp] = note.dateTime.toString() }
            }
            addData.resultedValues?.singleOrNull()?.let(::resultRowToData)
        }
    }
}