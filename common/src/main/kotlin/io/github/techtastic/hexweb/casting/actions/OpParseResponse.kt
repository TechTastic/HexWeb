package io.github.techtastic.hexweb.casting.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.github.techtastic.hexweb.casting.iota.JsonIota
import io.github.techtastic.hexweb.utils.HexWebOperatorUtils.getResponse

object OpParseResponse: ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val response = args.getResponse(0, argc)

        val json = JsonObject()
        json.addProperty("code", response.code)
        json.add("body", JsonParser.parseString(response.body?.string() ?: "{}"))
        return listOf(JsonIota(json))
    }
}