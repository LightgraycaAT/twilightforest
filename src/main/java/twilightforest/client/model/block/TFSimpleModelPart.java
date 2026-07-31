package twilightforest.client.model.block;

import java.util.List;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import org.jspecify.annotations.Nullable;

// [VanillaCopy] SimpleModelWrapper, but make QuadCollection non-final
public class TFSimpleModelPart implements BlockStateModelPart {
    public final boolean useAmbientOcclusion;
    public final Material.Baked particleMaterial;
    public QuadCollection quads;

    public TFSimpleModelPart(boolean useAmbientOcclusion, Material.Baked particleMaterial) {
        this.useAmbientOcclusion = useAmbientOcclusion;
        this.particleMaterial = particleMaterial;
        this.quads = QuadCollection.EMPTY;
    }


    @Override
    public List<BakedQuad> getQuads(@Nullable Direction direction) {
        return this.quads.getQuads(direction);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return useAmbientOcclusion;
    }

    @Override
    public Material.Baked particleMaterial() {
        return particleMaterial;
    }

    @Override
    public @BakedQuad.MaterialFlags int materialFlags() {
        return this.quads.materialFlags();
    }
}

