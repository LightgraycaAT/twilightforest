package twilightforest.client.model.block.patch;

import com.mojang.math.Quadrant;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.*;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.cuboid.CuboidFace;
import net.minecraft.client.resources.model.cuboid.FaceBakery;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.neoforged.neoforge.client.model.DynamicBlockStateModel;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import org.joml.Vector3f;
import twilightforest.block.PatchBlock;
import twilightforest.client.model.block.TFSimpleModelPart;

import java.util.List;

public class PatchModel implements DynamicBlockStateModel {
	public final TFSimpleModelPart part;
	public final Material.Baked texture;
	public final Identifier textureLocation;
	private final boolean shaggify;
	public final ModelBaker baker;

    public PatchModel(ModelBaker baker, TFSimpleModelPart part, Material.Baked texture, Identifier textureLocation, boolean shaggify) {
		this.textureLocation = textureLocation;
		this.texture = texture;
		this.shaggify = shaggify;
		this.part = part;
        this.baker = baker;
    }


    @Override
	public void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockStateModelPart> parts) {
		QuadCollection.Builder builder = new QuadCollection.Builder();
		this.addQuads(builder, state.getValue(PatchBlock.NORTH), state.getValue(PatchBlock.EAST), state.getValue(PatchBlock.SOUTH), state.getValue(PatchBlock.WEST), random);
		part.quads = builder.build();
		parts.add(part);
	}

	private void addQuads(QuadCollection.Builder builder, boolean north, boolean east, boolean south, boolean west, RandomSource posRandom) {
		BoundingBox bb = PatchBlock.AABBFromRandom(posRandom);

		this.addQuadsFromAABB(builder, west ? 0 : bb.minX(), bb.minY(), north ? 0 : bb.minZ(), east ? 16 : bb.maxX(), bb.maxY(), south ? 16 : bb.maxZ());

		if(!shaggify) return;

		long westSeed = posRandom.nextLong();
		long eastSeed = posRandom.nextLong();
		long northSeed = posRandom.nextLong();
		long southSeed = posRandom.nextLong();

		int minY = bb.minY();
		int maxY = bb.maxY();

		// add on shaggy edges
		if (!west) {
			long seed = westSeed;
			seed = seed * seed * 42317861L + seed * 7L;

			int num0 = (int) (seed >> 12 & 3L) + 1;
			int num1 = (int) (seed >> 15 & 3L) + 1;
			int num2 = (int) (seed >> 18 & 3L) + 1;
			int num3 = (int) (seed >> 21 & 3L) + 1;

			int minZ = bb.minZ() + num0;
			int maxZ = bb.maxZ();

			if (maxZ - ((num1 + num2 + num3)) > minZ) {
				// draw two blobs
				int innerZ = bb.maxZ() - num2;
				this.addQuadsFromAABB(builder, bb.minX() - 1, minY, minZ, bb.minX(), maxY, minZ + num1);
				this.addQuadsFromAABB(builder, bb.minX() - 1, minY, innerZ - num3, bb.minX(), maxY, innerZ);
			} else {
				//draw one blob
				this.addQuadsFromAABB(builder, bb.minX() - 1, minY, minZ, bb.minX(), maxY, maxZ - num2);
			}
		}

		if (!east) {
			long seed = eastSeed;
			seed = seed * seed * 42317861L + seed * 17L;

			int num0 = (int) (seed >> 12 & 3L) + 1;
			int num1 = (int) (seed >> 15 & 3L) + 1;
			int num2 = (int) (seed >> 18 & 3L) + 1;
			int num3 = (int) (seed >> 21 & 3L) + 1;

			int minZ = bb.minZ() + num0;
			int maxZ = bb.maxZ();

			if (maxZ - ((num1 + num2 + num3)) > minZ) {
				// draw two blobs
				int innerZ = maxZ - num2;
				this.addQuadsFromAABB(builder, bb.maxX(), minY, minZ, bb.maxX() + 1, maxY, minZ + num1);
				this.addQuadsFromAABB(builder, bb.maxX(), minY, innerZ - num3, bb.maxX() + 1, maxY, innerZ);
			} else {
				//draw one blob
				this.addQuadsFromAABB(builder, bb.maxX(), minY, minZ, bb.maxX() + 1, maxY, maxZ - num2);
			}
		}

		if (!north) {
			long seed = northSeed;
			seed = seed * seed * 42317861L + seed * 23L;

			int num0 = (int) (seed >> 12 & 3L) + 1;
			int num1 = (int) (seed >> 15 & 3L) + 1;
			int num2 = (int) (seed >> 18 & 3L) + 1;
			int num3 = (int) (seed >> 21 & 3L) + 1;

			int minX = bb.minX() + num0;
			int innerX = minX + num1;
			int maxX = bb.maxX() - num2;

			this.addQuadsFromAABB(builder, minX, minY, bb.minZ() - 1, innerX, maxY, bb.minZ());
			this.addQuadsFromAABB(builder, maxX - num3, minY, bb.minZ() - 1, maxX, maxY, bb.minZ());
		}

		if (!south) {
			long seed = southSeed;
			seed = seed * seed * 42317861L + seed * 11L;

			int num0 = (int) (seed >> 12 & 3L) + 1;
			int num1 = (int) (seed >> 15 & 3L) + 1;
			int num2 = (int) (seed >> 18 & 3L) + 1;
			int num3 = (int) (seed >> 21 & 3L) + 1;

			int minX = bb.minX() + num0;
			int maxX = bb.maxX() - num2;

			this.addQuadsFromAABB(builder, minX, minY, bb.maxZ(), minX + num1, maxY, bb.maxZ() + 1);
			this.addQuadsFromAABB(builder, maxX - num3, minY, bb.maxZ(), maxX, maxY, bb.maxZ() + 1);
		}
	}

	private void addQuadsFromAABB(QuadCollection.Builder builder, float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		addQuadsFromVector(builder, Direction.UP, minX, minY, minZ, maxX, maxY, maxZ);
		addQuadsFromVector(builder, Direction.DOWN, minX, minY, minZ, maxX, maxY, maxZ);
		addQuadsFromVector(builder, Direction.NORTH, minX, minY, minZ, maxX, maxY, maxZ);
		addQuadsFromVector(builder, Direction.SOUTH, minX, minY, minZ, maxX, maxY, maxZ);
		addQuadsFromVector(builder, Direction.EAST, minX, minY, minZ, maxX, maxY, maxZ);
		addQuadsFromVector(builder, Direction.WEST, minX, minY, minZ, maxX, maxY, maxZ);
	}

	private void addQuadsFromVector(QuadCollection.Builder builder, Direction direction, float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		Quadrant uvRotation = switch (direction) {
			case EAST, WEST -> Quadrant.R90;
			default -> Quadrant.R0;
		};
		CuboidFace.UVs uvs = switch (direction) {
			case NORTH -> new CuboidFace.UVs(maxX, minZ + 1f, minX, minZ);
			case EAST -> new CuboidFace.UVs(maxX, minZ, maxX - 1f, maxZ);
			case SOUTH -> new CuboidFace.UVs(minX, maxZ, maxX, maxZ - 1f);
			case WEST -> new CuboidFace.UVs(minX, maxZ, minX + 1f, minZ);
			default -> new CuboidFace.UVs(minX, minZ, maxX, maxZ);
		};
		CuboidFace face = new CuboidFace(null, 0, textureLocation.toString(), uvs, uvRotation);

		BakedQuad quad = FaceBakery.bakeQuad(baker, new Vector3f(minX, minY, minZ), new Vector3f(maxX, maxY, maxZ), face, texture, direction, BlockModelRotation.IDENTITY, null, true, 0);
		builder.addCulledFace(direction, quad);
	}

	@Override
	public Material.Baked particleMaterial() {
		return part.particleMaterial();
	}

	@Override
	public @BakedQuad.MaterialFlags int materialFlags() {
		return part.materialFlags();
	}

	public record Unbaked(Identifier modelLocation) implements CustomUnbakedBlockStateModel {

		public static final MapCodec<Unbaked> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Identifier.CODEC.fieldOf("model").forGetter(Unbaked::modelLocation)).apply(instance, Unbaked::new));

		@Override
		public BlockStateModel bake(ModelBaker baker) {
			ResolvedModel resolvedModel = baker.getModel(this.modelLocation);
			UnbakedPatchModel model = (UnbakedPatchModel) resolvedModel.wrapped();

			TextureSlots slots = resolvedModel.getTopTextureSlots();
			boolean ao = resolvedModel.getTopAmbientOcclusion();
			Material.Baked particle = resolvedModel.resolveParticleMaterial(slots, baker);
			Material.Baked texture = baker.materials().resolveSlot(slots, "texture", resolvedModel);
			TFSimpleModelPart part = new TFSimpleModelPart(ao, particle);

			return new PatchModel(baker, part, texture, texture.sprite().atlasLocation(), model.shaggify);
		}

		@Override
		public void resolveDependencies(Resolver resolver) {
			resolver.markDependency(this.modelLocation);
		}

		@Override
		public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
			return CODEC;
		}
	}
}
