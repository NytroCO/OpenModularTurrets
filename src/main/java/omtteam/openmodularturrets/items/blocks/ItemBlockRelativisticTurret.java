package omtteam.openmodularturrets.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import omtteam.openmodularturrets.handler.ConfigHandler;
import omtteam.openmodularturrets.reference.OMTNames;
import omtteam.openmodularturrets.reference.Reference;

import java.text.DecimalFormat;
import java.util.List;

public class ItemBlockRelativisticTurret extends ItemBlock {
    private static final DecimalFormat df = new DecimalFormat("0.0");

    public ItemBlockRelativisticTurret(Block block) {
        super(block);
        this.setRegistryName(Reference.MOD_ID, OMTNames.Blocks.relativisticTurret);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack itemStack, EntityPlayer player, List tooltip, boolean advanced) {
        tooltip.add("");
        tooltip.add(TextFormatting.GOLD + "--" + I18n.translateToLocal("tooltip.info") + "--");
        tooltip.add(I18n.translateToLocal("tooltip.tier") + ": " + TextFormatting.WHITE + "3");
        tooltip.add(I18n.translateToLocal("tooltip.range") + ": " + TextFormatting.WHITE +
                               ConfigHandler.getRelativistic_turret().getRange());
        tooltip.add(I18n.translateToLocal("tooltip.accuracy") + ": " + TextFormatting.WHITE +
                               I18n.translateToLocal("turret.accuracy.high"));
        tooltip.add(I18n.translateToLocal("tooltip.ammo") + ": " + TextFormatting.WHITE +
                               I18n.translateToLocal("turret.ammo.4"));
        tooltip.add(I18n.translateToLocal("tooltip.tier_required") + ": " + TextFormatting.WHITE +
                               I18n.translateToLocal("base.tier.3"));
        tooltip.add("");
        tooltip.add(
                TextFormatting.DARK_PURPLE + "--" + I18n.translateToLocal("tooltip.damage.label") + "--");
        tooltip.add(I18n.translateToLocal("tooltip.damage.stat") + ": " + TextFormatting.WHITE +
                               (ConfigHandler.getRelativistic_turret().getDamage() / 2F) + " " + I18n.translateToLocal(
                "tooltip.health"));
        tooltip.add(I18n.translateToLocal("tooltip.aoe") + ": " + TextFormatting.WHITE + "0");
        tooltip.add(I18n.translateToLocal("tooltip.fire_rate") + ": " + TextFormatting.WHITE + df.format(
                20.0F / ConfigHandler.getRelativistic_turret().getFireRate()));
        tooltip.add(I18n.translateToLocal("tooltip.energy.stat") + ": " + TextFormatting.WHITE +
                               ConfigHandler.getRelativistic_turret().getPowerUsage() + " RF");
        tooltip.add("");
        tooltip.add(TextFormatting.DARK_GRAY + I18n.translateToLocal("flavour.turret.8"));
    }
}
