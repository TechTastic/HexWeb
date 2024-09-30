package io.github.techtastic.hexweb

import dev.architectury.event.events.common.TickEvent
import io.github.techtastic.hexweb.config.HexWebConfig

object HexWeb {
    const val MOD_ID: String = "hexweb"

    @JvmStatic
    fun init() {
        TickEvent.SERVER_PRE.register { server ->
            HTTPRequestsHandler.setup()
        }

        HexWebConfig.setup()
    }
}