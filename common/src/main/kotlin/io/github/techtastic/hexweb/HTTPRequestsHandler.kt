package io.github.techtastic.hexweb

import com.google.gson.JsonObject
import com.mojang.datafixers.util.Either
import io.github.techtastic.hexweb.casting.iota.ConnectionIota
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.*
import okhttp3.internal.http.HttpMethod
import java.io.IOException
import java.util.*

object HTTPRequestsHandler {
    var client: OkHttpClient? = null
    val responses = mutableMapOf<UUID, Either<Response, IOException>>()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()

    fun setup() {
        this.client = OkHttpClient.Builder().build()
    }

    fun makeAndQueueRequest(uuid: UUID, body: JsonObject, conn: ConnectionIota.Connection, method: String, endpoint: String) {
        val request = Request.Builder().url("https://${conn.host}:${conn.port}/${endpoint}")
            .method(method, if (HttpMethod.permitsRequestBody(method) || HttpMethod.requiresRequestBody(method))
                body.toString().toRequestBody(JSON) else null).build()
        queueRequest(uuid, request)
    }

    fun queueRequest(uuid: UUID, req: Request) {
        client?.newCall(req)?.enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                responses[uuid] = Either.right(e)
            }

            override fun onResponse(call: Call, response: Response) {
                responses[uuid] = Either.left(response)
            }
        })
    }
}