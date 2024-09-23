package io.github.techtastic.hexweb

import dev.architectury.event.events.common.TickEvent

object HexWeb {
    const val MOD_ID: String = "hexweb"

    @JvmStatic
    fun init() {
        TickEvent.SERVER_PRE.register { server ->
            HTTPRequestsHandler.setup()
        }
    }
}