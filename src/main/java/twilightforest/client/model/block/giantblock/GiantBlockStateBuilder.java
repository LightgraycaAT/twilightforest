package twilightforest.client.model.block.giantblock;

import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.client.model.generators.blockstate.CustomBlockStateModelBuilder;
import net.neoforged.neoforge.client.model.generators.blockstate.UnbakedMutator;

public class GiantBlockStateBuilder extends CustomBlockStateModelBuilder {
    private final BlockStateModel.Unbaked sourceModel;

    public GiantBlockStateBuilder(BlockStateModel.Unbaked sourceModel) {
        this.sourceModel = sourceModel;
    }

    @Override
    public CustomBlockStateModelBuilder with(VariantMutator variantMutator) {
        return this;
    }

    @Override
    public CustomBlockStateModelBuilder with(UnbakedMutator variantMutator) {
        return this;
    }

    @Override
    public CustomUnbakedBlockStateModel toUnbaked() {
        return new UnbakedGiantBlockStateModel(this.sourceModel);
    }
}
