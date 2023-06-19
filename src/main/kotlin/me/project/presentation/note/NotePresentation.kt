package me.project.presentation.note

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.project.base.BasePresentation
import me.project.models.Note
import me.project.usecases.notes.AddNoteUseCase
import me.project.usecases.notes.GetListNotesUseCase
import me.project.usecases.notes.GetNoteByIdUseCase

object NotePresentation : BasePresentation() {
    fun getAllNotes(
        route: Route,
        getListNotesUseCase: GetListNotesUseCase
    ) {
        route.get {
            tryResponse(this) { httpCode ->
                getListNotesUseCase()?.let {
                    call.respond(
                        message = onSuccess(it),
                        status = httpCode
                    )
                } ?: call.emptyResult()
            }
        }
    }

    fun getNoteById(
        route: Route,
        getNoteByIdUseCase: GetNoteByIdUseCase
    ) {
        route.get("{id?}") {
            tryResponse(this) { httpCode ->
                val id = call.parameters["id"]
                getNoteByIdUseCase(id?.toInt())?.let {
                    call.respond(
                        message = onSuccess(it),
                        status = httpCode
                    )
                } ?: call.emptyResult()

            }
        }
    }

    fun addNote(
        route: Route,
        addNoteUseCase: AddNoteUseCase
    ) {
        route.post {
            tryResponse(this) { httpCode ->
                val note = call.receive<Note>()
                call.respond(
                    message = onSuccess(addNoteUseCase(note = note)),
                    status = httpCode
                )
            }
        }
    }
}