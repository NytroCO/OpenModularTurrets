package omtteam.openmodularturrets.client.render.renderers.blockitem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import omtteam.omlib.util.MathUtil;
import omtteam.openmodularturrets.client.render.models.ModelDamageAmp;
import omtteam.openmodularturrets.client.render.models.ModelPotatoCannonTurret;
import omtteam.openmodularturrets.client.render.models.ModelRedstoneReactor;
import omtteam.openmodularturrets.client.render.models.ModelSolarPanelAddon;
import omtteam.openmodularturrets.reference.Reference;
import omtteam.openmodularturrets.tileentity.turrets.PotatoCannonTurretTileEntity;
import omtteam.openmodularturrets.util.TurretHeadUtil;
import org.lwjgl.opengl.GL11;

class PotatoCannonTurretRenderer extends TileEntitySpecialRenderer {
    private final ModelSolarPanelAddon solar;
    private final ModelDamageAmp amp;
    private final ModelRedstoneReactor reac;
    private final ModelPotatoCannonTurret model;

    public PotatoCannonTurretRenderer() {
        model = new ModelPotatoCannonTurret();
        solar = new ModelSolarPanelAddon();
        amp = new ModelDamageAmp();
        reac = new ModelRedstoneReactor();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    @SideOnly(Side.CLIENT)
    public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        PotatoCannonTurretTileEntity turretHead = (PotatoCannonTurretTileEntity) te;

        ResourceLocation textures = (new ResourceLocation(Reference.MOD_ID + ":textures/blocks/potato_cannon_turret.png"));
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);

        int rotation;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        if (te == null) {
            GL11.glScalef(0.7F, -0.7F, -0.7F);
            GL11.glTranslatef((float) x + 0.0F, (float) y + 0.4F, (float) z + 0.5F);
            GL11.glRotatef(45.0F, 2.5F, -4.5F, -1.0F);
            model.setRotationForTarget(0, 0);
            model.renderAll();
            GL11.glPopMatrix();
            return;
        }

        if (turretHead.shouldConceal) {
            GL11.glPopMatrix();
            return;
        }

        if (te.getWorld() != null) {
            rotation = te.getBlockMetadata();
            GL11.glRotatef(rotation * 90, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(1.0F, -1F, -1F);
            model.setRotationForTarget(MathUtil.getRotationXYFromYawPitch(turretHead.yaw, turretHead.pitch), MathUtil.getRotationXZFromYawPitch(turretHead.yaw, turretHead.pitch));
            model.Base.rotateAngleX = turretHead.baseFitRotationX;
            model.Base.rotateAngleY = turretHead.baseFitRotationZ;
            model.Pole.rotateAngleX = turretHead.baseFitRotationX;
            model.Pole.rotateAngleY = turretHead.baseFitRotationZ;
            model.BoxUnder.rotateAngleX = turretHead.baseFitRotationX;
            model.renderAll();
        }

        if (turretHead.getBase() != null) {
            if (TurretHeadUtil.hasSolarPanelAddon(turretHead.getBase())) {
                ResourceLocation texturesSolar = (new ResourceLocation(Reference.MOD_ID + ":textures/blocks/addon_solar_panel.png"));
                Minecraft.getMinecraft().renderEngine.bindTexture(texturesSolar);
                solar.setRotationForTarget(MathUtil.getRotationXYFromYawPitch(turretHead.yaw, turretHead.pitch), MathUtil.getRotationXZFromYawPitch(turretHead.yaw, turretHead.pitch));
                solar.renderAll();
            }

            if (TurretHeadUtil.hasDamageAmpAddon(turretHead.getBase())) {
                ResourceLocation texturesAmp = (new ResourceLocation(Reference.MOD_ID + ":textures/blocks/addon_damage_amp.png"));
                Minecraft.getMinecraft().renderEngine.bindTexture(texturesAmp);
                amp.setRotationForTarget(MathUtil.getRotationXYFromYawPitch(turretHead.yaw, turretHead.pitch), MathUtil.getRotationXZFromYawPitch(turretHead.yaw, turretHead.pitch));
                amp.renderAll();
            }

            if (TurretHeadUtil.hasRedstoneReactor(turretHead.getBase())) {
                ResourceLocation texturesReac = (new ResourceLocation(Reference.MOD_ID + ":textures/blocks/addon_redstone_reactor.png"));
                Minecraft.getMinecraft().renderEngine.bindTexture(texturesReac);
                reac.setRotationForTarget(MathUtil.getRotationXYFromYawPitch(turretHead.yaw, turretHead.pitch), MathUtil.getRotationXZFromYawPitch(turretHead.yaw, turretHead.pitch));
                reac.renderAll();
            }
        }

        GL11.glPopMatrix();
    }
}
