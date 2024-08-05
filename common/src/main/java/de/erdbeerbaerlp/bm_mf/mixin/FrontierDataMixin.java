package de.erdbeerbaerlp.bm_mf.mixin;

import de.erdbeerbaerlp.bm_mf.accessors.FrontierDataAccessor;
import games.alejandrocoria.mapfrontiers.common.FrontierData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Set;

@Mixin(value = FrontierData.class, remap = false)
public class FrontierDataMixin implements FrontierDataAccessor {
    @Shadow @Final protected List<BlockPos> vertices;

    @Shadow @Final protected Set<ChunkPos> chunks;

    public List<BlockPos> bmmf$getVertices(){
        return vertices;
    }
    public Set<ChunkPos> bmmf$getChunks(){
        return chunks;
    }
}
