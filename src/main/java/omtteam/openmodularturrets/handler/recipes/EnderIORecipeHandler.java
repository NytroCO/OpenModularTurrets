package omtteam.openmodularturrets.handler.recipes;


import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import omtteam.omlib.util.JSONRecipeBuilder;
import omtteam.openmodularturrets.handler.ConfigHandler;
import omtteam.openmodularturrets.init.ModBlocks;
import omtteam.openmodularturrets.init.ModItems;

@SuppressWarnings({"deprecation", "ConstantConditions"})
class EnderIORecipeHandler {
    public static void init() {
        ItemStack capacitorBank;
        ItemStack capacitorBankVibrant;
        ItemStack capacitorBankBasic;
        ItemStack basicCapacitor;
        ItemStack doubleCapacitor;
        ItemStack octadicCapacitor;
        ItemStack vibrantCrystal;
        ItemStack electricalSteel;
        ItemStack darkSteel;
        ItemStack conductiveIron;
        ItemStack soularium;
        
		/* ModItems */

        Block capBankBlock = Block.REGISTRY.getObject(new ResourceLocation("EnderIO", "blockCapBank"));
        capacitorBank = new ItemStack(capBankBlock, 1, 2);
        capacitorBankVibrant = new ItemStack(capBankBlock, 1, 3);
        capacitorBankBasic = new ItemStack(capBankBlock, 1, 1);

        Item capacitorItem = Item.REGISTRY.getObject(new ResourceLocation("EnderIO", "itemBasicCapacitor"));
        basicCapacitor = new ItemStack(capacitorItem, 1, 0);
        doubleCapacitor = new ItemStack(capacitorItem, 1, 1);
        octadicCapacitor = new ItemStack(capacitorItem, 1, 2);

        Item materialsItem = Item.REGISTRY.getObject(new ResourceLocation("EnderIO", "itemMaterial"));
        vibrantCrystal = new ItemStack(materialsItem, 1, 6);

        Item alloyItem = Item.REGISTRY.getObject(new ResourceLocation("EnderIO", "itemAlloy"));
        electricalSteel = new ItemStack(alloyItem, 1, 0);
        darkSteel = new ItemStack(alloyItem, 1, 6);
        conductiveIron = new ItemStack(alloyItem, 1, 4);
        soularium = new ItemStack(alloyItem, 1, 7);
        // ModItems
        // Barrels
        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModItems.intermediateProductTiered, 1, 11), "AAA", " B ", "AAA", 'A',
                electricalSteel, 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 10));

        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModItems.intermediateProductTiered, 1, 12), "AAA", " B ", "AAA", 'A',
                        darkSteel, 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 11));

        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModItems.intermediateProductTiered, 1, 13), "CAC", " B ", "CAC", 'A',
                Items.DIAMOND, 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 12),
                'C', soularium);

        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModItems.intermediateProductTiered, 1, 14), "AAA", "CBC", "AAA", 'A',
                Blocks.OBSIDIAN, 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 13),
                'C', vibrantCrystal);

        // Chambers
        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModItems.intermediateProductTiered, 1, 6), "AAA", " BC", "AAA", 'A',
                electricalSteel, 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 5), 'C',
                basicCapacitor);

        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModItems.intermediateProductTiered, 1, 7), "AAA", " BC", "AAA", 'A',
                        darkSteel, 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 6), 'C', basicCapacitor);

        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModItems.intermediateProductTiered, 1, 8), "DAD", " BC", "DAD", 'A',
                        Items.DIAMOND, 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 7), 'C',
                        doubleCapacitor, 'D', soularium);

        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModItems.intermediateProductTiered, 1, 9), "ADA", " BC", "ADA", 'A',
                        Blocks.OBSIDIAN, 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 8), 'C',
                        octadicCapacitor, 'D', vibrantCrystal);

        // Sensors
        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModItems.intermediateProductTiered, 1, 1), " A ", "ABA", " C ", 'A',
                electricalSteel, 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 0), 'C',
                RecipeHandler.ioBus);

        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModItems.intermediateProductTiered, 1, 2), " C ", "ABA", " C ", 'A',
                        darkSteel, 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 1), 'C', RecipeHandler.ioBus);

        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModItems.intermediateProductTiered, 1, 3), "EDE", "CBC", "EDE", 'B',
                new ItemStack(ModItems.intermediateProductTiered, 1, 2), 'C', RecipeHandler.ioBus, 'D',
                Items.DIAMOND, 'E', soularium);

        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModItems.intermediateProductTiered, 1, 4), "EDE", "CBC", "EDE", 'B',
                new ItemStack(ModItems.intermediateProductTiered, 1, 3), 'C', RecipeHandler.ioBus, 'D',
                Blocks.OBSIDIAN, 'E', vibrantCrystal);

        // Bases
        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModBlocks.turretBase, 1, 1), "ABA", "ECE", "ADA", 'A',
                electricalSteel, 'B', capacitorBankBasic, 'C',
                new ItemStack(ModItems.intermediateProductTiered, 1, 1), 'D', basicCapacitor, 'E', RecipeHandler.ioBus);

        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModBlocks.turretBase, 1, 2), "ABA", "ECE", "ADA", 'A',
                        darkSteel, 'B', capacitorBank, 'C', new ItemStack(ModItems.intermediateProductTiered, 1, 2), 'D',
                        basicCapacitor, 'E', RecipeHandler.ioBus);

        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModBlocks.turretBase, 1, 3), "ABA", "ECE", "ADA", 'A',
                        soularium, 'B', capacitorBankVibrant, 'C', new ItemStack(ModItems.intermediateProductTiered, 1, 3), 'D',
                        doubleCapacitor, 'E', RecipeHandler.ioBus);

        JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModBlocks.turretBase, 1, 4), "ABA", "ECE", "ADA", 'A',
                        Blocks.OBSIDIAN, 'B', capacitorBankVibrant, 'C',
                        new ItemStack(ModItems.intermediateProductTiered, 1, 4), 'D', octadicCapacitor, 'E', RecipeHandler.ioBus);

        //Power Expanders
        JSONRecipeBuilder.addShapedRecipe(RecipeHandler.expanderPowerTierTwo, "ABA", "ECE", "ADA", 'A',
                        electricalSteel, 'B', capacitorBank, 'C',
                        Blocks.REDSTONE_BLOCK, 'D', basicCapacitor, 'E', RecipeHandler.ioBus);

        JSONRecipeBuilder.addShapedRecipe(RecipeHandler.expanderPowerTierThree, "ABA", "ECE", "ADA", 'A',
                        darkSteel, 'B', capacitorBank, 'C', Blocks.REDSTONE_BLOCK,
                        'D', basicCapacitor, 'E', RecipeHandler.ioBus);

        JSONRecipeBuilder.addShapedRecipe(RecipeHandler.expanderPowerTierFour, "ABA", "ECE", "ADA", 'A',
                        soularium, 'B', capacitorBankVibrant, 'C',
                        Blocks.REDSTONE_BLOCK, 'D', doubleCapacitor, 'E', RecipeHandler.ioBus);

        JSONRecipeBuilder.addShapedRecipe(RecipeHandler.expanderPowerTierFive, "ABA", "ECE", "ADA", 'A',
                        Blocks.OBSIDIAN, 'B', capacitorBankVibrant, 'C',
                        Blocks.REDSTONE_BLOCK, 'D', octadicCapacitor, 'E', RecipeHandler.ioBus);

        //Inventory Expanders
        JSONRecipeBuilder.addShapedRecipe(RecipeHandler.expanderInvTierTwo, "ABA", "ECE", "ADA", 'A',
                        electricalSteel, 'B', capacitorBank, 'C', Blocks.CHEST,
                        'D', basicCapacitor, 'E', RecipeHandler.ioBus);

        JSONRecipeBuilder.addShapedRecipe(RecipeHandler.expanderInvTierThree, "ABA", "ECE", "ADA", 'A',
                        darkSteel, 'B', capacitorBank, 'C', Blocks.CHEST, 'D',
                        basicCapacitor, 'E', RecipeHandler.ioBus);

        JSONRecipeBuilder.addShapedRecipe(RecipeHandler.expanderInvTierFour, "ABA", "ECE", "ADA", 'A',
                        soularium, 'B', capacitorBankVibrant, 'C', Blocks.CHEST,
                        'D', doubleCapacitor, 'E', RecipeHandler.ioBus);

        JSONRecipeBuilder.addShapedRecipe(RecipeHandler.expanderInvTierFive, "ABA", "ECE", "ADA", 'A',
                        Blocks.OBSIDIAN, 'B', capacitorBankVibrant, 'C',
                        Blocks.CHEST, 'D', octadicCapacitor, 'E', RecipeHandler.ioBus);

        // Turrets
        if (ConfigHandler.getGunTurretSettings().isEnabled()) {
            JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModBlocks.machineGunTurret, 1), " A ", "CAC", "DBD", 'A',
                            new ItemStack(ModItems.intermediateProductTiered, 1, 11), 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 6), 'C',
                            electricalSteel, 'D', RecipeHandler.ioBus);
        }

        if (ConfigHandler.getIncendiaryTurretSettings().isEnabled()) {
            JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModBlocks.incendiaryTurret, 1), "A A", "BCB", "DCD", 'A',
                            new ItemStack(ModItems.intermediateProductTiered, 1, 11), 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 6), 'C',
                            electricalSteel, 'D', RecipeHandler.ioBus);
        }

        if (ConfigHandler.getGrenadeTurretSettings().isEnabled()) {
            JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModBlocks.grenadeLauncherTurret, 1), " A ", "CBC", "CDC", 'A',
                            new ItemStack(ModItems.intermediateProductTiered, 1, 12), 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 7), 'C',
                            darkSteel, 'D', RecipeHandler.ioBus);
        }

        if (ConfigHandler.getRelativisticTurretSettings().isEnabled()) {
            JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModBlocks.relativisticTurret, 1), "CAC", "ABA", "CDC", 'A',
                            Items.ENDER_PEARL, 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 2), 'C',
                            darkSteel, 'D', RecipeHandler.ioBus);
        }

        if (ConfigHandler.getRocketTurretSettings().isEnabled()) {
            JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModBlocks.rocketTurret, 1), "CAC", "CAC", "EDE", 'A',
                    new ItemStack(ModItems.intermediateProductTiered, 1, 13), 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 8), 'C',
                    soularium, 'D', RecipeHandler.ioBus, 'E',
                    Items.DIAMOND);
        }

        if (ConfigHandler.getTeleporterTurretSettings().isEnabled()) {
            JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModBlocks.teleporterTurret, 1), "CEC", "ABA", "CDC", 'A',
                            Items.DIAMOND, 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 3), 'C',
                            Items.ENDER_EYE, 'D', RecipeHandler.ioBus, 'E',
                            Items.DIAMOND);
        }

        if (ConfigHandler.getLaserTurretSettings().isEnabled()) {
            JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModBlocks.laserTurret, 1), "EAE", "CBC", "DCD", 'A',
                    new ItemStack(ModItems.intermediateProductTiered, 1, 14), 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 9), 'C',
                    Blocks.OBSIDIAN, 'D', RecipeHandler.ioBus, 'E',
                    vibrantCrystal);
        }

        if (ConfigHandler.getRailgunTurretSettings().isEnabled()) {
            JSONRecipeBuilder.addShapedRecipe(new ItemStack(ModBlocks.railGunTurret, 1), "EAE", "CAC", "DBD", 'A',
                    new ItemStack(ModItems.intermediateProductTiered, 1, 14), 'B', new ItemStack(ModItems.intermediateProductTiered, 1, 9), 'C',
                    Blocks.OBSIDIAN, 'D', RecipeHandler.ioBus, 'E',
                    vibrantCrystal);
        }

        // Ammo
        JSONRecipeBuilder.addShapedRecipe(RecipeHandler.ammoRocket, " A ", "ABA", " C ", 'A',
                electricalSteel, 'B', Items.GUNPOWDER, 'C',
                Items.REDSTONE);

        JSONRecipeBuilder.addShapedRecipe(RecipeHandler.ammoBullet, " A ", " B ", " C ", 'A',
                electricalSteel, 'B', Items.GUNPOWDER, 'C',
                conductiveIron);

        JSONRecipeBuilder.addShapedRecipe(RecipeHandler.ammoGrenade, " C ", "ABA", " A ", 'A', conductiveIron,
                        'B', Items.GUNPOWDER, 'C', Items.REDSTONE);

        JSONRecipeBuilder.addShapedRecipe(RecipeHandler.ammoFerroSlug, " C ", "CBC", " A ", 'A', darkSteel, 'B',
                        Items.FLINT, 'C', Items.REDSTONE);
    }
}
