package io.github.techtastic.hexweb.casting.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getPositiveInt
import at.petrak.hexcasting.api.casting.getPositiveIntUnderInclusive
import at.petrak.hexcasting.api.casting.iota.Iota
import io.github.techtastic.hexweb.casting.iota.ConnectionIota
import ram.talia.moreiotas.api.getString

class OpCreateConnection(val type: Type): ConstMediaAction {
    enum class Type {
        EXAL,
        DIST,
        REFL
    }

    override val argc: Int
        get() = when (type) {
            Type.EXAL -> 5
            Type.DIST -> 2
            Type.REFL -> 0
        }

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        return listOf(when (type) {
            Type.EXAL -> {
                val first = args.getPositiveIntUnderInclusive(0, 255, argc)
                val second = args.getPositiveIntUnderInclusive(1, 255, argc)
                val third = args.getPositiveIntUnderInclusive(2, 255, argc)
                val fourth = args.getPositiveIntUnderInclusive(3, 255, argc)
                val port = args.getPositiveInt(4, argc)

                ConnectionIota(first, second, third, fourth, port)
            }
            Type.DIST -> {
                val host = args.getString(0, argc)
                val port = args.getPositiveInt(1, argc)

                ConnectionIota(host, port)
            }
            Type.REFL -> ConnectionIota()
        })
    }
}