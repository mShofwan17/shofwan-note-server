package me.project.presentation.note

import io.ktor.server.routing.*
import me.project.usecases.notes.AddNoteUseCase
import me.project.usecases.notes.GetListNotesUseCase
import me.project.usecases.notes.GetNoteByIdUseCase
import org.koin.ktor.ext.inject

fun Route.noteRouting() {
    val getListNotesUseCase: GetListNotesUseCase by inject()
    val getNoteByIdUseCase: GetNoteByIdUseCase by inject()
    val addNoteUseCase: AddNoteUseCase by inject()

    route("notes") {
        NotePresentation.apply {
            val route = this@route
            getAllNotes(
                route,
                getListNotesUseCase
            )

            getNoteById(
                route,
                getNoteByIdUseCase
            )

            addNote(
                route,
                addNoteUseCase
            )
        }

    }
}

