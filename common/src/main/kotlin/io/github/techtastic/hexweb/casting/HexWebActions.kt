package io.github.techtastic.hexweb.casting

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.HexRegistries
import dev.architectury.registry.registries.DeferredRegister
import io.github.techtastic.hexweb.HexWeb.MOD_ID
import io.github.techtastic.hexweb.casting.actions.*
import io.github.techtastic.hexweb.casting.actions.spells.OpRequest

object HexWebActions {
    private val ACTIONS = DeferredRegister.create(MOD_ID, HexRegistries.ACTION)


    // Connection

    val CREATE_CONNECTION_EXAL = ACTIONS.register("create_connection/exal") { ActionRegistryEntry(
        HexPattern.fromAngles("qqqqwwwaqawwwqqqqwwwaqaww", HexDir.NORTH_EAST),
        OpCreateConnection(OpCreateConnection.Type.EXAL)
    )}
    val CREATE_CONNECTION_DIST = ACTIONS.register("create_connection/dist") { ActionRegistryEntry(
        HexPattern.fromAngles("qqqqwwewwqqqqwwwaqaww", HexDir.NORTH_EAST),
        OpCreateConnection(OpCreateConnection.Type.DIST)
    )}
    val CREATE_CONNECTION_REFL = ACTIONS.register("create_connection/refl") { ActionRegistryEntry(
        HexPattern.fromAngles("qqqqwwewwqqqqwwew", HexDir.NORTH_EAST),
        OpCreateConnection(OpCreateConnection.Type.REFL)
    )}
    val GET_CONNECTION_INFO_DECO = ACTIONS.register("get_connection_info/deco") { ActionRegistryEntry(
        HexPattern.fromAngles("qqqqweqqqqqd", HexDir.NORTH_EAST),
        OpGetConnectionInfo(OpGetConnectionInfo.Type.DECO)
    )}
    val GET_CONNECTION_INFO_DISI = ACTIONS.register("get_connection_info/disi") { ActionRegistryEntry(
        HexPattern.fromAngles("qqqqwwedew", HexDir.NORTH_EAST),
        OpGetConnectionInfo(OpGetConnectionInfo.Type.DISI)
    )}
    val SET_HOST_EXAL = ACTIONS.register("set_host/exal") { ActionRegistryEntry(
        HexPattern.fromAngles("qqqqwqeeeeew", HexDir.NORTH_EAST),
        OpSetHost(OpSetHost.Type.EXAL)
    )}
    val SET_HOST_DIST = ACTIONS.register("set_host/dist") { ActionRegistryEntry(
        HexPattern.fromAngles("qqqqweqaqe", HexDir.NORTH_EAST),
        OpSetHost(OpSetHost.Type.DIST)
    )}
    val SET_PORT = ACTIONS.register("set_port") { ActionRegistryEntry(
        HexPattern.fromAngles("qqqqwaqa", HexDir.NORTH_EAST),
        OpSetPort
    )}


    // JSON

    val CREATE_JSON = ACTIONS.register("create_json") { ActionRegistryEntry(
        HexPattern.fromAngles("edade", HexDir.NORTH_WEST),
        OpCreateJson
    )}
    val SET_ELEMENT = ACTIONS.register("set_element") { ActionRegistryEntry(
        HexPattern.fromAngles("edadedaa", HexDir.NORTH_WEST),
        OpSetElement
    )}
    val GET_ELEMENT = ACTIONS.register("get_element") { ActionRegistryEntry(
        HexPattern.fromAngles("edadeedd", HexDir.NORTH_WEST),
        OpGetElement
    )}


    // HTTP

    val REQUEST = ACTIONS.register("request") { ActionRegistryEntry(
        HexPattern.fromAngles("qqqqwqdqddqe", HexDir.NORTH_EAST),
        OpRequest
    )}


    fun register() {
        ACTIONS.register()
    }
}