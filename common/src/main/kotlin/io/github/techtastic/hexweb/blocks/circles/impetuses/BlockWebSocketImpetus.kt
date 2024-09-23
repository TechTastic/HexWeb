package io.github.techtastic.hexweb.blocks.circles.impetuses

import at.petrak.hexcasting.api.block.circle.BlockAbstractImpetus
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class BlockWebSocketImpetus(properties: Properties) : BlockAbstractImpetus(properties) {
    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity? {
        TODO("Not yet implemented")
    }

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        if (level.isClientSide || type == null) return null

        return BlockEntityTicker(BlockEntityWebSocketImpetus::serverTick) as BlockEntityTicker<T>
    }
}