package io.github.techtastic.hexweb.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import io.github.techtastic.hexweb.casting.HexWebIotaTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.server.level.ServerLevel

class ConnectionIota(val host: String, val port: Int) : Iota(HexWebIotaTypes.CONNECTION.get(), Connection(host, port)) {
    constructor(conn: Connection): this(conn.host, conn.port)

    constructor(first: Int, second: Int, third: Int, fourth: Int, port: Int): this("$first.$second.$third.$fourth", port)

    constructor(): this("127.0.0.1", 80)

    fun getPayload(): Connection = Connection(host, port)

    override fun isTruthy() = true

    override fun toleratesOther(that: Iota) = typesMatch(this, that)
            && that is ConnectionIota
            && that.host == this.host
            && that.port == this.port

    override fun serialize(): Tag {
        val tag = CompoundTag()
        tag.putString("hexweb\$host", this.host)
        tag.putInt("hexweb\$port", this.port)
        return tag
    }

    companion object {
        val TYPE = object : IotaType<ConnectionIota>() {
            override fun deserialize(tag: Tag, world: ServerLevel) = Companion.deserialize(tag)

            override fun display(tag: Tag): Component {
                val conn = Companion.deserialize(tag)
                return Component.translatable("hexweb.iota.connection", "§a${conn.host}", "§b${conn.port}").withStyle(Style.EMPTY.withColor(this.color()))
            }

            override fun color() = 0x136b18
        }

        fun deserialize(tag: Tag): ConnectionIota {
            val tag = tag as CompoundTag
            return ConnectionIota(tag.getString("hexweb\$host"), tag.getInt("hexweb\$port"))
        }
    }

    data class Connection(val host: String, val port: Int)
}