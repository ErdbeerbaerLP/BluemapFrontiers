package de.erdbeerbaerlp.bm_mf.accessors;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import java.util.List;
import java.util.Set;

public interface FrontierDataAccessor {
    List<BlockPos> bmmf$getVertices();

    Set<ChunkPos> bmmf$getChunks();
}
