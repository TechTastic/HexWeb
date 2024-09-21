package io.github.techtastic.hexweb.casting.actions.spells

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.github.techtastic.hexweb.HexWeb.JSON
import io.github.techtastic.hexweb.HexWeb.client
import io.github.techtastic.hexweb.casting.iota.JsonIota
import io.github.techtastic.hexweb.casting.mishap.MishapIOException
import io.github.techtastic.hexweb.utils.HexWebOperatorUtils.getConnection
import io.github.techtastic.hexweb.utils.HexWebOperatorUtils.getJsonObject
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import ram.talia.moreiotas.api.getString

object OpRequest: ConstMediaAction {
    override val argc: Int
        get() = 4

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val conn = args.getConnection(0, argc)
        val json = args.getJsonObject(1, argc)
        val method = args.getString(2, argc)
        val endpoint = args.getString(3, argc)

        val body = json.toString().toRequestBody(JSON)
        val request = Request.Builder()
            .url("https://${conn.host}:${conn.port}/${endpoint}")
            .method(method, body).build()

        val response = try {
            client.newCall(request).execute()
        } catch (e: IOException) {
            throw MishapIOException(e)
        }

        val result = JsonObject()
        result.addProperty("code", response.code)
        result.add("response", JsonParser.parseString(response.body?.string() ?: "{}"))
        return listOf(JsonIota(result))
    }
}