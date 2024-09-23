package io.github.techtastic.hexweb

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mojang.datafixers.util.Either
import io.github.techtastic.hexweb.casting.iota.ConnectionIota
import okhttp3.*
import okhttp3.internal.toImmutableList
import java.util.*

object WebSocketHandler {
    var client: OkHttpClient? = null
    val sockets = mutableMapOf<UUID, Pair<WebSocket, Listener>>()

    fun setup() {
        this.client = OkHttpClient.Builder().build()
    }

    fun getOrCreateWebSocket(uuid: UUID, conn: ConnectionIota.Connection, endpoint: String): Pair<WebSocket, Listener> {
        if (this.sockets[uuid] == null) {
            val request = Request.Builder().url("wss://${conn.host}:${conn.port}/${endpoint}").build()
            val listener = Listener()
            val socket = this.client?.newWebSocket(request, listener)
            socket?.let { this.sockets[uuid] = Pair(it, listener) }
        }
        return this.sockets[uuid]!!
    }

    class Listener: WebSocketListener() {
        val backlog = arrayListOf<JsonElement>()

        fun addToLog(element: JsonElement) {
            if (this.backlog.size >= 20)
                this.backlog.removeFirst()
            this.backlog.add(element)
        }

        fun getBackLog(): List<JsonElement> {
            val log = this.backlog.toImmutableList()
            this.backlog.clear()
            return log
        }



        override fun onOpen(webSocket: WebSocket, response: Response) {
            this.addToLog(JsonParser.parseString(response.body?.string() ?: "{}"))

            super.onOpen(webSocket, response)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            this.addToLog(JsonParser.parseString(text))

            super.onMessage(webSocket, text)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            this.addToLog(JsonParser.parseString("{ code: $code, reason: $reason }"))

            super.onClosing(webSocket, code, reason)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            val error = JsonObject()
            error.add("error", JsonParser.parseString(t.message))
            error.add("message", JsonParser.parseString(response?.body?.string() ?: "null"))
            this.addToLog(error)

            super.onFailure(webSocket, t, response)
        }
    }
}