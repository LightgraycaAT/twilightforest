package twilightforest.client.model.block.patch;

import net.neoforged.neoforge.client.model.AbstractUnbakedModel;
import net.neoforged.neoforge.client.model.StandardModelParameters;

public class UnbakedPatchModel extends AbstractUnbakedModel {

	public final boolean shaggify;

	public UnbakedPatchModel(boolean shaggify, StandardModelParameters parameters) {
		super(parameters);
		this.shaggify = shaggify;
	}
}
