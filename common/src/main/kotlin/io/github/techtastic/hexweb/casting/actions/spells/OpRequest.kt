package io.github.techtastic.hexweb.casting.actions.spells

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import io.github.techtastic.hexweb.HTTPRequestsHandler
import io.github.techtastic.hexweb.casting.iota.ResponseIota
import io.github.techtastic.hexweb.utils.HexWebOperatorUtils.getConnection
import io.github.techtastic.hexweb.utils.HexWebOperatorUtils.getJsonObject
import ram.talia.moreiotas.api.getString
import java.util.*

object OpRequest: ConstMediaAction {
    override val argc: Int
        get() = 4

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val conn = args.getConnection(0, argc)
        val json = args.getJsonObject(1, argc)
        val method = args.getString(2, argc)
        val endpoint = args.getString(3, argc)

        val uuid = UUID.randomUUID()
        HTTPRequestsHandler.makeAndQueueRequest(uuid, json, conn, method, endpoint)
        return listOf(ResponseIota(uuid))
    }
}