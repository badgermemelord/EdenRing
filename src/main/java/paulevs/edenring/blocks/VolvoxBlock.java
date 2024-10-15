package paulevs.edenring.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import ru.bclib.client.models.ModelsHelper;
import ru.bclib.client.models.PatternsHelper;
import ru.bclib.client.render.BCLRenderLayer;
import ru.bclib.interfaces.BlockModelProvider;
import ru.bclib.interfaces.CustomColorProvider;
import ru.bclib.interfaces.RenderLayerProvider;
import ru.bclib.noise.OpenSimplexNoise;
import ru.bclib.util.ColorUtil;

import java.util.Map;
import java.util.Optional;

public class VolvoxBlock extends SlimeBlock implements RenderLayerProvider, BlockModelProvider, CustomColorProvider {
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise("volvox".hashCode());
	
	private static final int[] COLORS;
	
	public VolvoxBlock() {
		super(FabricBlockSettings.copyOf(Blocks.SLIME_BLOCK));
	}
	
	@Override
	public BCLRenderLayer getRenderLayer() {
		return BCLRenderLayer.TRANSLUCENT;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public UnbakedModel getModelVariant(ResourceLocation stateId, BlockState blockState, Map<ResourceLocation, UnbakedModel> modelCache) {
		Optional<String> pattern = PatternsHelper.createBlockColored(stateId);
		return ModelsHelper.fromPattern(pattern);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public BlockModel getItemModel(ResourceLocation itemID) {
		Optional<String> pattern = PatternsHelper.createBlockColored(itemID);
		return ModelsHelper.fromPattern(pattern);
	}
	
	@Override
	public BlockColor getProvider() {
		return (state, world, pos, tintIndex) -> {
			if (pos == null) {
				return COLORS[0];
			}
			float noise = (float) NOISE.eval(pos.getX() * 0.3, pos.getY() * 0.3, pos.getZ() * 0.3);
			int color = Mth.floor((noise * 0.5F + 0.5F) * 3.5F + 0.25F);
			return COLORS[color];
		};
	}
	
	@Override
	public ItemColor getItemProvider() {
		return (itemStack, i) -> COLORS[0];
	}
	
	static {
		COLORS = new int[4];
		for (byte i = 0; i < 4; i++) {
			float delta = i / 3F;
			int r = Mth.floor(Mth.lerp(delta, 1.0F, 0.5F) * 255);
			int g = Mth.floor(Mth.lerp(delta, 1.0F, 0.6F) * 255);
			COLORS[i] = ColorUtil.color(r, g, 255);
		}
	}
}
