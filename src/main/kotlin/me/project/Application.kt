package me.project

import io.ktor.server.application.*
import me.project.data_source.DatabaseFactory
import me.project.di.configKoin
import me.project.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configKoin()
    configStatusPages()
    //configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()
    configDefaultHeader()
}
