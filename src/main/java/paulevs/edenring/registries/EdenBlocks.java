package paulevs.edenring.registries;


import net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.edenring.EdenRing;
import paulevs.edenring.blocks.AuritisOuterLeaves;
import paulevs.edenring.blocks.BalloonMushroomBlock;
import paulevs.edenring.blocks.BalloonMushroomSmallBlock;
import paulevs.edenring.blocks.BalloonMushroomStemBlock;
import paulevs.edenring.blocks.EdenGrassBlock;
import paulevs.edenring.blocks.MossyStoneBlock;
import paulevs.edenring.blocks.OverlayDoublePlantBlock;
import paulevs.edenring.blocks.OverlayPlantBlock;
import paulevs.edenring.blocks.OverlayVineBlock;
import paulevs.edenring.blocks.PulseTreeBlock;
import paulevs.edenring.blocks.SimplePlantBlock;
import paulevs.edenring.blocks.TexturedTerrainBlock;
import ru.bclib.api.TagAPI;
import ru.bclib.blocks.BaseLeavesBlock;
import ru.bclib.blocks.BaseVineBlock;
import ru.bclib.complexmaterials.ComplexMaterial;
import ru.bclib.complexmaterials.WoodenComplexMaterial;
import ru.bclib.config.PathConfig;
import ru.bclib.mixin.common.ComposterBlockAccessor;
import ru.bclib.registry.BlockRegistry;

public class EdenBlocks {
	public static final BlockRegistry REGISTRY = new BlockRegistry(EdenRing.EDEN_TAB, new PathConfig(EdenRing.MOD_ID, "blocks"));
	
	public static final Block EDEN_GRASS_BLOCK = register("eden_grass", new EdenGrassBlock());
	public static final Block EDEN_MYCELIUM = register("eden_mycelium", new TexturedTerrainBlock());
	public static final Block MOSSY_STONE = register("mossy_stone", new MossyStoneBlock());
	
	public static final Block AURITIS_LEAVES = register("auritis_leaves", new BaseLeavesBlock(Blocks.OAK_SAPLING, MaterialColor.GOLD));
	public static final Block AURITIS_OUTER_LEAVES = register("auritis_outer_leaves", new AuritisOuterLeaves());
	public static final ComplexMaterial AURITIS_MATERIAL = new WoodenComplexMaterial(EdenRing.MOD_ID, "auritis", "eden", MaterialColor.COLOR_BROWN, MaterialColor.GOLD).init(REGISTRY, EdenItems.REGISTRY, new PathConfig(EdenRing.MOD_ID, "recipes"));
	
	public static final Block BALLOON_MUSHROOM_SMALL = register("balloon_mushroom_small", new BalloonMushroomSmallBlock());
	public static final Block BALLOON_MUSHROOM_BLOCK = register("balloon_mushroom_block", new BalloonMushroomBlock());
	public static final Block BALLOON_MUSHROOM_STEM = register("balloon_mushroom_stem", new BalloonMushroomStemBlock());
	public static final ComplexMaterial BALLOON_MUSHROOM_MATERIAL = new WoodenComplexMaterial(EdenRing.MOD_ID, "balloon_mushroom", "eden", MaterialColor.COLOR_PURPLE, MaterialColor.COLOR_PURPLE).init(REGISTRY, EdenItems.REGISTRY, EdenRecipes.CONFIG);
	
	public static final Block PULSE_TREE = register("pulse_tree", new PulseTreeBlock());
	public static final ComplexMaterial PULSE_TREE_MATERIAL = new WoodenComplexMaterial(EdenRing.MOD_ID, "pulse_tree", "eden", MaterialColor.COLOR_CYAN, MaterialColor.COLOR_CYAN).init(REGISTRY, EdenItems.REGISTRY, EdenRecipes.CONFIG);
	
	public static final Block MYCOTIC_GRASS = register("mycotic_grass", new SimplePlantBlock(true));
	public static final Block GOLDEN_GRASS = register("golden_grass", new OverlayPlantBlock(true));
	
	public static final Block VIOLUM = register("violum", new OverlayDoublePlantBlock());
	
	public static final Block EDEN_VINE = register("eden_vine", new OverlayVineBlock());
	
	public static void init() {
		Registry.BLOCK.stream().filter(block -> Registry.BLOCK.getKey(block).getNamespace().equals(EdenRing.MOD_ID)).forEach(block -> {
			Properties properties = ((AbstractBlockAccessor) block).getSettings();
			Material material = ((AbstractBlockSettingsAccessor) properties).getMaterial();
			
			if (block instanceof BaseLeavesBlock) {
				TagAPI.addTag(BlockTags.MINEABLE_WITH_HOE, block);
				TagAPI.addTag(BlockTags.LEAVES, block);
				TagAPI.addTag(ItemTags.LEAVES, block);
				ComposterBlockAccessor.callAdd(0.3F, block);
			}
			else if (block instanceof GrassBlock) {
				TagAPI.addTag(BlockTags.MINEABLE_WITH_SHOVEL, block);
			}
			else if (material == Material.PLANT || material == Material.REPLACEABLE_PLANT) {
				TagAPI.addTag(BlockTags.MINEABLE_WITH_HOE, block);
				ComposterBlockAccessor.callAdd(0.1F, block);
			}
			
			if (block instanceof BaseVineBlock) {
				TagAPI.addTag(BlockTags.CLIMBABLE, block);
			}
		});
	}
	
	private static Block register(String name, Block block) {
		return REGISTRY.register(EdenRing.makeID(name), block);
	}
	
	public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return false;
	}
}
