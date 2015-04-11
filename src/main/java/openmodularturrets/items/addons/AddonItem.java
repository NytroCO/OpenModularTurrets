package openmodularturrets.items.addons;

import net.minecraft.item.Item;
import openmodularturrets.ModularTurrets;

public abstract class AddonItem extends Item {

    public AddonItem() {
        super();

        this.setCreativeTab(ModularTurrets.modularTurretsTab);
        this.setMaxStackSize(1);
    }
}
