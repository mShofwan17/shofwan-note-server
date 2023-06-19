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
            tryResponse(this) {
                getListNotesUseCase()?.let {
                    call.respond(
                        message = responseSuccess(it),
                        status = httpCode
                    )
                } ?: emptyResult(this)

            }
        }
    }

    fun getNoteById(
        route: Route,
        getNoteByIdUseCase: GetNoteByIdUseCase
    ) {
        route.get("{id?}") {
            tryResponse(this) {
                val id = call.parameters["id"]
                getNoteByIdUseCase(id?.toInt())?.let {
                    call.respond(
                        message = responseSuccess(it),
                        status = httpCode
                    )
                } ?: emptyResult(this)

            }
        }
    }

    fun addNote(
        route: Route,
        addNoteUseCase: AddNoteUseCase
    ) {
        route.post {
            tryResponse(this) {
                val note = call.receive<Note>()
                call.respond(
                    message = responseSuccess(addNoteUseCase(note = note)),
                    status = httpCode
                )
            }
        }
    }
}