package twilightforest.client.model.block;

import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.client.model.generators.blockstate.CustomBlockStateModelBuilder;
import net.neoforged.neoforge.client.model.generators.blockstate.UnbakedMutator;

public class TFSimpleModelBuilder extends CustomBlockStateModelBuilder {
    private final CustomUnbakedBlockStateModel delegated;

    @Override
    public CustomBlockStateModelBuilder with(VariantMutator variantMutator) {
        return this;
    }

    @Override
    public CustomBlockStateModelBuilder with(UnbakedMutator variantMutator) {
        return this;
    }

    public TFSimpleModelBuilder(CustomUnbakedBlockStateModel model){
        delegated = model;
    }

    @Override
    public CustomUnbakedBlockStateModel toUnbaked() {
        return delegated;
    }
}
