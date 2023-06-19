package me.project.presentation.note

import io.ktor.server.routing.*
import me.project.usecases.notes.*
import org.koin.ktor.ext.inject

fun Route.noteRouting() {
    val getListNotesUseCase: GetListNotesUseCase by inject()
    val getNoteByIdUseCase: GetNoteByIdUseCase by inject()
    val addNoteUseCase: AddNoteUseCase by inject()
    val updateNoteUseCase: UpdateNoteUseCase by inject()
    val searchNoteByNameUseCase: SearchNoteByNameUseCase by inject()
    val deleteNoteUseCase: DeleteNoteUseCase by inject()

    route("notes") {
        NotePresentation.apply {
            val route = this@route
            getAllNotes(
                route,
                getListNotesUseCase,
                searchNoteByNameUseCase
            )

            getNoteById(
                route,
                getNoteByIdUseCase
            )

            addNote(
                route,
                addNoteUseCase
            )

            updateNote(
                route,
                updateNoteUseCase,
                getNoteByIdUseCase
            )

            deleteNote(
                route,
                deleteNoteUseCase
            )
        }

    }
}

