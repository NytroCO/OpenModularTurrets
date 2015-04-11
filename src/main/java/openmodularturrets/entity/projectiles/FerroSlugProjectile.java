package openmodularturrets.entity.projectiles;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import openmodularturrets.entity.projectiles.damagesources.ArmorBypassDamageSource;
import openmodularturrets.handler.ConfigHandler;

public class FerroSlugProjectile extends TurretProjectile {

    public FerroSlugProjectile(World p_i1776_1_) {
        super(p_i1776_1_);
        this.gravity = 0.00F;
    }

    public FerroSlugProjectile(World par1World, ItemStack ammo) {
        super(par1World, ammo);
        this.gravity = 0.00F;
    }

    @Override
    public void onEntityUpdate() {
        if (ticksExisted >= 50) {
            this.setDead();
        }
    }

    @Override
    protected void onImpact(MovingObjectPosition movingobjectposition) {

        if (this.ticksExisted <= 1) {
            return;
        }

        if (movingobjectposition.entityHit != null && !worldObj.isRemote) {

            if (movingobjectposition.typeOfHit.equals(0)) {
                if (worldObj.isAirBlock(movingobjectposition.blockX,
                        movingobjectposition.blockY,
                        movingobjectposition.blockZ)) {
                    return;
                }
            }

            int damage = ConfigHandler.getRailgun_turret().getDamage();

            if (isAmped) {
                damage += ConfigHandler.getDamageAmpDmgBonus() * amp_level;
            }

            worldObj.playSoundEffect(posX, posY, posZ,
                    "openmodularturrets:railGunHit", 1.0F, 1.0F);

            movingobjectposition.entityHit.attackEntityFrom(
                    new ArmorBypassDamageSource("ferroslug"), damage);
            movingobjectposition.entityHit.hurtResistantTime = 0;
        }

        this.setDead();
    }

    @Override
    protected void updateFallState(double par1, boolean par3) {
        this.posY = posY + 12F;
    }

    @Override
    protected float getGravityVelocity() {
        return this.gravity;
    }
}