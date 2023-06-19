package me.project.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.defaultheaders.*
import io.netty.handler.codec.http.HttpHeaderNames
import java.time.Duration

fun Application.configDefaultHeader() {
    install(DefaultHeaders) {
        val duration = Duration.ofDays(30).seconds
        header(
            name = HttpHeaders.CacheControl,
            value = "public, max-age=$duration, immutable"
        )
    }
}