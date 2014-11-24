package modularTurrets.tileentity.turrets;

import modularTurrets.projectiles.BulletProjectile;
import modularTurrets.tileentity.turretBase.TurretBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ShootingEntityMachineGun extends EntityLiving implements IRangedAttackMob {

    public ItemStack stack;

    public ShootingEntityMachineGun(World par1World) {
	    super(par1World);
    }

    public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float f, float accuracy, TurretBase base) {
        if (stack != null) {
            BulletProjectile entityCrap = new BulletProjectile(worldObj, posX, posY + 1.5F, posZ);

            if (TurretHeadUtils.hasDamageAmpAddon(base)) {
                worldObj.playSoundEffect(posX, posY, posZ, "openmodularturrets:amped", 1.0F, 1.0F);
                entityCrap.isAmped = true;
            }

            double d0 = entitylivingbase.posX - this.posX;
            double d1 = entitylivingbase.posY + (double) entitylivingbase.getEyeHeight() - 2.0D - this.posY;
            double d2 = entitylivingbase.posZ - this.posZ;

            entityCrap.setThrowableHeading(d0, d1, d2, 2.0F, accuracy);
            worldObj.playSoundEffect(posX, posY, posZ, "openmodularturrets:machinegun", 1.0F, 1.0F);

            if (!worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(entityCrap);
            }

            for (int i = 0; i <= 20; i++) {
                worldObj.spawnParticle("reddust", posX, posY + 1.8F, posZ, 1.0D, 1.0D, 1.0D);
            }

            stack = null;
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float f) {

    }
}
