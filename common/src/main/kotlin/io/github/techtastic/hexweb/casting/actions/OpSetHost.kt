package io.github.techtastic.hexweb.casting.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getPositiveIntUnderInclusive
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import io.github.techtastic.hexweb.casting.iota.ConnectionIota
import io.github.techtastic.hexweb.utils.HexWebOperatorUtils.getConnection
import ram.talia.moreiotas.api.casting.iota.StringIota
import ram.talia.moreiotas.api.getString

class OpSetHost(val type: Type): ConstMediaAction {
    enum class Type {
        EXAL,
        DIST
    }

    override val argc: Int
        get() = when (type) {
            Type.EXAL -> 5
            Type.DIST -> 2
        }

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val conn = args.getConnection(0, argc)
        return listOf(when (type) {
            Type.EXAL -> {
                val first = args.getPositiveIntUnderInclusive(1, 255, argc)
                val second = args.getPositiveIntUnderInclusive(2, 255, argc)
                val third = args.getPositiveIntUnderInclusive(3, 255, argc)
                val fourth = args.getPositiveIntUnderInclusive(4, 255, argc)
                ConnectionIota(first, second, third, fourth, conn.port)
            }
            Type.DIST -> {
                val host = args.getString(1, argc)
                ConnectionIota(host, conn.port)
            }
        })
    }
}