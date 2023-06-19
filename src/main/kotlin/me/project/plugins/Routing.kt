package me.project.plugins

import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import me.project.presentation.note.noteRouting
import me.project.presentation.root

fun Application.configureRouting() {
    routing {
        root()
        noteRouting()
        staticResources(
            remotePath = "/",
            basePackage = "images"
        )
    }
}
