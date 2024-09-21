package io.github.techtastic.hexweb.casting.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getPositiveInt
import at.petrak.hexcasting.api.casting.getPositiveIntUnderInclusive
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import io.github.techtastic.hexweb.casting.iota.ConnectionIota
import io.github.techtastic.hexweb.utils.HexWebOperatorUtils.getConnection
import ram.talia.moreiotas.api.casting.iota.StringIota
import ram.talia.moreiotas.api.getString

object OpSetPort: ConstMediaAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val conn = args.getConnection(0, argc)
        val port = args.getPositiveInt(1, argc)
        return listOf(ConnectionIota(conn.host, port))
    }
}