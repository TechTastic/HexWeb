package io.github.techtastic.hexweb.casting

import at.petrak.hexcasting.common.lib.HexRegistries
import dev.architectury.registry.registries.DeferredRegister
import io.github.techtastic.hexweb.HexWeb.MOD_ID
import io.github.techtastic.hexweb.casting.iota.ConnectionIota
import io.github.techtastic.hexweb.casting.iota.JsonIota

object HexWebIotaTypes {
    private val IOTAS = DeferredRegister.create(MOD_ID, HexRegistries.IOTA_TYPE)

    val CONNECTION = IOTAS.register("connection", ConnectionIota::TYPE)
    val JSON = IOTAS.register("json", JsonIota::TYPE)

    fun register() {
        IOTAS.register()
    }
}