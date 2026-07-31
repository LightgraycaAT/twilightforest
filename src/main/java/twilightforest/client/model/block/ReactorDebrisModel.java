package twilightforest.client.model.block;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.DelegateBlockStateModel;
import twilightforest.block.entity.ReactorDebrisBlockEntity;
import twilightforest.client.renderer.block.ReactorDebrisRenderer;


public class ReactorDebrisModel extends DelegateBlockStateModel {
	public ReactorDebrisModel(BlockStateModel defaultModel) {
		super(defaultModel);
	}

	@Override
	public Material.Baked particleMaterial(BlockAndTintGetter level, BlockPos pos, BlockState state) {
		if (!(level.getBlockEntity(pos) instanceof ReactorDebrisBlockEntity reactorDebrisBlockEntity) || !(level instanceof ClientLevel clientLevel))
			return new Material.Baked(ReactorDebrisRenderer.getSprite(ReactorDebrisBlockEntity.DEFAULT_TEXTURE), false);
		final Identifier textureForParticle = reactorDebrisBlockEntity.textures[clientLevel.getRandom().nextInt(reactorDebrisBlockEntity.textures.length)];
		return new Material.Baked(ReactorDebrisRenderer.getSprite(textureForParticle), false);
	}
}
