package omtteam.openmodularturrets.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import omtteam.openmodularturrets.blocks.LeverBlock;
import omtteam.openmodularturrets.client.render.renderers.blockitem.TileEntityRenderers;
import omtteam.openmodularturrets.client.render.renderers.projectiles.ProjectileRenderers;
import omtteam.openmodularturrets.compatability.IGWHandler;
import omtteam.openmodularturrets.compatability.ModCompatibility;
import omtteam.openmodularturrets.init.ModBlocks;
import omtteam.openmodularturrets.init.ModItems;
import omtteam.openmodularturrets.init.ModTESRItems;
import omtteam.openmodularturrets.items.*;
import omtteam.openmodularturrets.reference.Names;
import omtteam.openmodularturrets.reference.Reference;

public class ClientProxy extends CommonProxy {

    private void registerItemModel(final Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName().toString().toLowerCase()));
    }

    private void registerItemModel(final Item item, int meta, final String variantName) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(item.getRegistryName().toString().toLowerCase()), variantName));
    }

    private void registerItemModel(final Item item, int meta, final String customName, boolean useCustomName) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + customName.toLowerCase()));
    }

    private void registerBlockModelAsItem(final Block block, int meta, final String blockName) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + blockName, "inventory"));
    }

    private void registerBlockModelAsItem(final Block block, int meta, final String blockName, String variantName) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + blockName, variantName));
    }

    @Override
    public void preInit() {
        super.preInit();
        for (int i = 0; i < 5; i++) {
            registerBlockModelAsItem(ModBlocks.turretBase, i, Names.Blocks.turretBase, "tier=" + (i + 1));
        }
        for (int i = 0; i < 10; i++) {
            registerBlockModelAsItem(ModBlocks.expander, i, Names.Blocks.expander,"facing=north,meta=" + i);
        }
        for (int i = 0; i < 15; i++) {
            registerItemModel(ModItems.intermediateProductTiered, i, IntermediateProductTiered.subNames[i], true);
        }
        for (int i = 0; i < 7; i++) {
            registerItemModel(ModItems.addonMetaItem, i, AddonMetaItem.subNames[i], true);
        }
        for (int i = 0; i < 5; i++) {
            registerItemModel(ModItems.upgradeMetaItem, i, UpgradeMetaItem.subNames[i], true);
        }
        for (int i = 0; i < 1; i++) {
            registerItemModel(ModItems.intermediateProductRegular, i, IntermediateProductRegular.subNames[i], true);
        }
        for (int i = 0; i < 5; i++) {
            registerItemModel(ModItems.ammoMetaItem, i, AmmoMetaItem.subNames[i], true);
        }
        for (int i = 0; i < 2; i++) {
            registerItemModel(ModItems.throwableMetaItem, i, ThrowableMetaItem.subNames[i], true);
        }

        StateMap ignoreRotation = new StateMap.Builder().ignore(LeverBlock.ROTATION).build();
        ModelLoader.setCustomStateMapper(ModBlocks.leverBlock, ignoreRotation);
    }

    @Override
    public void initRenderers() {
        super.initRenderers();
        TileEntityRenderers.init();
        ProjectileRenderers.init();
        ModTESRItems.init();
    }

    @Override
    public void initHandlers() {
        super.initHandlers();
        if (ModCompatibility.IGWModLoaded) {
            ModCompatibility.igwHandler = IGWHandler.getInstance();
        }
    }
}