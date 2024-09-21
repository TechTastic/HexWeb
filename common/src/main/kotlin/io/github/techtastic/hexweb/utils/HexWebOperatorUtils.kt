package io.github.techtastic.hexweb.utils

import at.petrak.hexcasting.api.casting.iota.*
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import com.google.gson.*
import io.github.techtastic.hexweb.casting.iota.ConnectionIota
import io.github.techtastic.hexweb.casting.iota.JsonIota
import io.github.techtastic.hexweb.casting.mishap.MishapCannotJson
import ram.talia.moreiotas.api.casting.iota.StringIota

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

    fun JsonElement.toIota(): Iota {
        if (this.isJsonNull) return NullIota()

        if (this.isJsonPrimitive) {
            if (this.asJsonPrimitive.isBoolean) return BooleanIota(this.asBoolean)
            if (this.asJsonPrimitive.isNumber) return DoubleIota(this.asNumber.toDouble())
            return StringIota.make(this.asString)
        }

        if (this.isJsonArray) return ListIota(this.asJsonArray.map { it.toIota() })

        return JsonIota(this.asJsonObject)
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

        throw MishapCannotJson(this)
    }
}
