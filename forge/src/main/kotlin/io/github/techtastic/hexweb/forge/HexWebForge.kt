package io.github.techtastic.hexweb.forge

import dev.architectury.platform.forge.EventBuses
import io.github.techtastic.hexweb.HexWeb
import io.github.techtastic.hexweb.HexWeb.init
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.KotlinModLoadingContext

@Mod(HexWeb.MOD_ID)
class HexWebForge {
    init {
        EventBuses.registerModEventBus(HexWeb.MOD_ID, KotlinModLoadingContext.get().getKEventBus())

        init()
    }
}
