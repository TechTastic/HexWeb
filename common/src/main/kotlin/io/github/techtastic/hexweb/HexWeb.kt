package io.github.techtastic.hexweb

import com.mojang.datafixers.util.Either
import dev.architectury.event.events.common.TickEvent
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.util.UUID

object HexWeb {
    const val MOD_ID: String = "hexweb"

    var client: OkHttpClient? = null
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    val responses = mutableMapOf<UUID, Either<Response, IOException>>()

    @JvmStatic
    fun init() {
        TickEvent.SERVER_PRE.register { server ->
            this.client = OkHttpClient.Builder().build()
        }
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