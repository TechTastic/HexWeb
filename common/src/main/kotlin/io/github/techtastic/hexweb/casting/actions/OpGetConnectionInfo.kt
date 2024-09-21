package io.github.techtastic.hexweb.casting.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import io.github.techtastic.hexweb.utils.HexWebOperatorUtils.getConnection
import ram.talia.moreiotas.api.casting.iota.StringIota

class OpGetConnectionInfo(val type: Type): ConstMediaAction {
    enum class Type {
        DECO,
        DISI
    }

    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val conn = args.getConnection(0, argc)
        val result: MutableList<Iota> = when (type) {
            Type.DISI -> conn.host.split(".").map { DoubleIota(it.toDouble()) }.toMutableList()
            Type.DECO -> mutableListOf(StringIota.make(conn.host))
        }
        result.add(DoubleIota(conn.port.toDouble()))
        return result
    }
}