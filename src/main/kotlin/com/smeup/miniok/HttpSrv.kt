package com.smeup.miniok

import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.server.Jetty
import org.http4k.server.asServer

object HttpSrv {
    @JvmStatic
    fun main(args: Array<String>) {
        val app = { request: Request ->
            Response(OK).body("Hello")
        }

        val jettyServer = app.asServer(Jetty(8080)).start()

    }
}
