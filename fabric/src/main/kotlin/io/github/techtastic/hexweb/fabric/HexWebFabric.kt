package io.github.techtastic.hexweb.fabric

import io.github.techtastic.hexweb.HexWeb.init
import net.fabricmc.api.ModInitializer

object HexWebFabric : ModInitializer {
    override fun onInitialize() {
        init()
    }
}
