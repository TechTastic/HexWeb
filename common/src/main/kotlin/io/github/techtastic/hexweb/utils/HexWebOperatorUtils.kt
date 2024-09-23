package io.github.techtastic.hexweb.utils

import at.petrak.hexcasting.api.casting.iota.*
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import com.google.gson.*
import io.github.techtastic.hexweb.HexWeb.responses
import io.github.techtastic.hexweb.casting.iota.ConnectionIota
import io.github.techtastic.hexweb.casting.iota.JsonIota
import io.github.techtastic.hexweb.casting.iota.ResponseIota
import io.github.techtastic.hexweb.casting.mishap.MishapCannotJson
import io.github.techtastic.hexweb.casting.mishap.MishapIOException
import io.github.techtastic.hexweb.casting.mishap.MishapTooEarly
import net.minecraft.world.phys.Vec3
import okhttp3.Response
import okio.IOException
import ram.talia.moreiotas.api.casting.iota.StringIota
import java.util.*

object HexWebOperatorUtils {
    fun List<Iota>.getConnection(idx: Int, argc: Int): ConnectionIota.Connection {
        if (idx >= 0 && idx <= this.lastIndex) {
            val iota = this.get(idx)
            if (iota is ConnectionIota)
                return iota.getPayload()
            throw MishapInvalidIota.ofType(iota, if (argc == 0) idx else argc - (idx + 1), "connection")
        }
        throw MishapNotEnoughArgs(idx + 1, this.size)
    }

    fun List<Iota>.getJsonObject(idx: Int, argc: Int): JsonObject {
        if (idx >= 0 && idx <= this.lastIndex) {
            val iota = this.get(idx)
            if (iota is JsonIota)
                return iota.getPayload()
            throw MishapInvalidIota.ofType(iota, if (argc == 0) idx else argc - (idx + 1), "json")
        }
        throw MishapNotEnoughArgs(idx + 1, this.size)
    }

    fun List<Iota>.getResponse(idx: Int, argc: Int): Response {
        if (idx >= 0 && idx <= this.lastIndex) {
            val iota = this.get(idx)
            if (iota is ResponseIota) {
                val either = responses[iota.getPayload()] ?: throw MishapTooEarly()
                responses.remove(iota.getPayload())
                if (either.right().isPresent) {
                    responses.remove(iota.getPayload())
                    throw MishapIOException(either.right().get())
                }
                return try {
                    responses.remove(iota.getPayload())
                    val response = either.left().orElseThrow()
                    response.close()
                    response
                } catch (ignored: Exception) {
                    throw MishapTooEarly()
                }
            }
            throw MishapInvalidIota.ofType(iota, if (argc == 0) idx else argc - (idx + 1), "response")
        }
        throw MishapNotEnoughArgs(idx + 1, this.size)
    }

    fun JsonElement.toIota(): Iota {
        if (this.isJsonNull) return NullIota()

        if (this.isJsonPrimitive) {
            if (this.asJsonPrimitive.isBoolean) return BooleanIota(this.asBoolean)
            if (this.asJsonPrimitive.isNumber) return DoubleIota(this.asNumber.toDouble())
            return StringIota.make(this.asString)
        }

        if (this.isJsonArray) return ListIota(this.asJsonArray.map { it.toIota() })

        val json = this.asJsonObject
        val vecKeys = listOf("x", "y", "z")
        if (json.asMap().keys.containsAll(vecKeys) && json.asMap().filterKeys { s -> !vecKeys.contains(s) }.isEmpty())
            return Vec3Iota(Vec3(json.get("x").asDouble, json.get("y").asDouble, json.get("z").asDouble))

        val patternKeys = listOf("signature", "start_direction")
        if (json.asMap().keys.containsAll(patternKeys) && json.asMap().filterKeys { s -> !patternKeys.contains(s) }.isEmpty())
            return PatternIota(HexPattern.fromAngles(json.get("signature").asString, HexDir.fromString(json.get("start_direction").asString)))

        val connKeys = listOf("host", "port")
        if (json.asMap().keys.containsAll(connKeys) && json.asMap().filterKeys { s -> !connKeys.contains(s) }.isEmpty())
            return ConnectionIota(json.get("host").asString, json.get("port").asInt)

        return JsonIota(json)
    }

    fun Iota.toJson(): JsonElement {
        if (this is BooleanIota) return JsonPrimitive(this.bool)
        if (this is DoubleIota) return JsonPrimitive(this.double)
        if (this is StringIota) return JsonPrimitive(this.string)
        if (this is NullIota) return JsonNull.INSTANCE
        if (this is JsonIota) return this.json
        if (this is ListIota) {
            val json = JsonArray()
            this.list.forEach { json.add(it.toJson()) }
            return json
        }
        if (this is Vec3Iota) {
            val json = JsonObject()
            json.addProperty("x", this.vec3.x)
            json.addProperty("y", this.vec3.y)
            json.addProperty("z", this.vec3.z)
            return json
        }
        if (this is PatternIota) {
            val json = JsonObject()
            json.addProperty("signature", this.pattern.anglesSignature())
            json.addProperty("start_direction", this.pattern.startDir.toString())
            return json
        }
        if (this is ConnectionIota) {
            val json = JsonObject()
            json.addProperty("host", this.host)
            json.addProperty("port", this.port)
            return json
        }

        throw MishapCannotJson(this)
    }
}
