package io.github.techtastic.hexweb.blocks.circles.impetuses

import at.petrak.hexcasting.api.block.circle.BlockCircleComponent
import at.petrak.hexcasting.api.casting.circles.BlockEntityAbstractImpetus
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class BlockEntityWebSocketImpetus(pos: BlockPos, state: BlockState) : BlockEntityAbstractImpetus(null, pos, state) {
    companion object {
        fun serverTick(level: Level, pos: BlockPos, bs: BlockState, impetus: BlockEntityWebSocketImpetus) {
            if (bs.getValue(BlockCircleComponent.ENERGIZED)) return


        }
    }
}