package omtteam.openmodularturrets.blocks;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import omtteam.omlib.api.IHasItemBlock;
import omtteam.omlib.blocks.BlockAbstractCamoTileEntity;
import omtteam.omlib.compatibility.theoneprobe.TOPInfoProvider;
import omtteam.omlib.reference.OMLibNames;
import omtteam.omlib.tileentity.EnumMachineMode;
import omtteam.omlib.util.player.PlayerUtil;
import omtteam.openmodularturrets.OpenModularTurrets;
import omtteam.openmodularturrets.handler.config.OMTConfig;
import omtteam.openmodularturrets.init.ModBlocks;
import omtteam.openmodularturrets.items.UsableMetaItem;
import omtteam.openmodularturrets.items.blocks.ItemBlockTurretBase;
import omtteam.openmodularturrets.reference.OMTNames;
import omtteam.openmodularturrets.reference.Reference;
import omtteam.openmodularturrets.tileentity.TurretBase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

import static omtteam.omlib.util.GeneralUtil.*;
import static omtteam.omlib.util.WorldUtil.getTouchingTileEntities;
import static omtteam.omlib.util.player.PlayerUtil.addChatMessage;

public class BlockTurretBase extends BlockAbstractCamoTileEntity implements IHasItemBlock, TOPInfoProvider {
    public static final PropertyInteger TIER = PropertyInteger.create("tier", 1, 5);
    public static final PropertyInteger LIGHT_VALUE = PropertyInteger.create("light_value", 0, 15);
    public static final PropertyInteger LIGHT_OPACITY = PropertyInteger.create("light_opacity", 0, 15);

    public BlockTurretBase() {
        super(Material.ROCK);
        this.setCreativeTab(OpenModularTurrets.modularTurretsTab);
        if (!OMTConfig.BASES.baseBreakable) {
            this.setBlockUnbreakable();
        }
        setDefaultState(this.blockState.getBaseState().withProperty(TIER, 1).withProperty(LIGHT_VALUE, 0).withProperty(LIGHT_OPACITY, 0));
        this.setSoundType(SoundType.STONE);
        this.setUnlocalizedName(OMTNames.Blocks.turretBase);
        this.setRegistryName(Reference.MOD_ID, OMTNames.Blocks.turretBase);
    }

    @Override
    public ItemBlock getItemBlock(Block block) {
        return new ItemBlockTurretBase(block);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public TileEntity createTileEntity(World world, IBlockState state) {
        int MaxCharge;
        int MaxIO;
        switch (state.getValue(TIER) - 1) {
            case 0:
                MaxCharge = OMTConfig.BASES.baseTierOne.getBaseMaxCharge();
                MaxIO = OMTConfig.BASES.baseTierOne.getBaseMaxIo();
                return new TurretBase(MaxCharge, MaxIO, 1, state);
            case 1:
                MaxCharge = OMTConfig.BASES.baseTierTwo.getBaseMaxCharge();
                MaxIO = OMTConfig.BASES.baseTierTwo.getBaseMaxIo();
                return new TurretBase(MaxCharge, MaxIO, 2, state);
            case 2:
                MaxCharge = OMTConfig.BASES.baseTierThree.getBaseMaxCharge();
                MaxIO = OMTConfig.BASES.baseTierThree.getBaseMaxIo();
                return new TurretBase(MaxCharge, MaxIO, 3, state);
            case 3:
                MaxCharge = OMTConfig.BASES.baseTierFour.getBaseMaxCharge();
                MaxIO = OMTConfig.BASES.baseTierFour.getBaseMaxIo();
                return new TurretBase(MaxCharge, MaxIO, 4, state);
            case 4:
                MaxCharge = OMTConfig.BASES.baseTierFive.getBaseMaxCharge();
                MaxIO = OMTConfig.BASES.baseTierFive.getBaseMaxIo();
                return new TurretBase(MaxCharge, MaxIO, 5, state);
            default:
                return new TurretBase(1, 1, 1, state);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    @ParametersAreNonnullByDefault
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return true;
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TIER, meta + 1);
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TIER) - 1;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{TIER, LIGHT_VALUE, LIGHT_OPACITY}, new IUnlistedProperty[]{RENDERBLOCKSTATE});
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isTranslucent(IBlockState state) {
        if (state instanceof IExtendedBlockState) {
            return ((IExtendedBlockState) state).getValue(RENDERBLOCKSTATE).getRenderState().isTranslucent();
        }
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (state.getBlock() instanceof BlockTurretBase) {
            return state.getActualState(world, pos).getValue(LIGHT_VALUE);
        }
        return super.getLightValue(state, world, pos);
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (state.getBlock() instanceof BlockTurretBase) {
            return state.getActualState(world, pos).getValue(LIGHT_OPACITY);
        }
        return super.getLightValue(state, world, pos);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        if (state.getBlock() instanceof BlockTurretBase && worldIn.getTileEntity(pos) instanceof TurretBase) {
            TurretBase base = (TurretBase) worldIn.getTileEntity(pos);
            if (base != null) {
                int lightValue, lightOpacity;
                lightValue = base.getCamoSettings().getLightValue();
                lightOpacity = base.getCamoSettings().getLightOpacity();
                return state.withProperty(LIGHT_VALUE, lightValue).withProperty(LIGHT_OPACITY, lightOpacity);
            }
        }
        return super.getActualState(state, worldIn, pos);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    @ParametersAreNonnullByDefault
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
            ItemStack heldItem = player.getHeldItemMainhand();
            TurretBase base = (TurretBase) world.getTileEntity(pos);
            // Camo clear
            if (player.isSneaking() && OMTConfig.BASES.allowBaseCamo && heldItem == ItemStack.EMPTY) {
                if (base != null) {
                    if (player.getUniqueID().toString().equals(base.getOwner())) {
                        base.setCamoState(state);
                    } else {
                        addChatMessage(player, new TextComponentString(safeLocalize("status.ownership")));
                    }
                }
            }
            // Camo preparation
            Block heldBlock = null;
            IBlockState camoState = null;
            if (heldItem != ItemStack.EMPTY) {
                heldBlock = Block.getBlockFromItem(heldItem.getItem());
                camoState = heldBlock.getStateForPlacement(world, pos, side.getOpposite(), hitX, hitY, hitZ, heldItem.getMetadata(), player, hand);
            }
            // Camo set
            if (!player.isSneaking() && OMTConfig.BASES.allowBaseCamo && heldItem != ItemStack.EMPTY && heldItem.getItem() instanceof ItemBlock &&
                    heldBlock.isFullCube(camoState)
                    && !heldBlock.hasTileEntity(camoState)
                    && !(heldBlock instanceof BlockTurretBase)) {
                if (base != null) {
                    if (PlayerUtil.isPlayerAdmin(player, base)) {
                        base.setCamoState(camoState);
                        return true;
                    } else {
                        addChatMessage(player, new TextComponentString(safeLocalize("status.ownership")));
                    }
                }
            }
            // Write Memory Card
            if (player.isSneaking() && base != null && player.getHeldItemMainhand() != ItemStack.EMPTY
                    && player.getHeldItemMainhand().getItem() instanceof UsableMetaItem && player.getHeldItemMainhand().getItemDamage() == 2) {

                ((UsableMetaItem) player.getHeldItemMainhand().getItem()).setDataStored(player.getHeldItemMainhand(), base.writeMemoryCardNBT());
                return true;
            }
            // Read Memory Card
            if (!player.isSneaking() && base != null && player.getHeldItemMainhand() != ItemStack.EMPTY
                    && player.getHeldItemMainhand().getItem() instanceof UsableMetaItem && player.getHeldItemMainhand().getItemDamage() == 2
                    && ((UsableMetaItem) player.getHeldItemMainhand().getItem()).hasDataStored(player.getHeldItemMainhand())) {

                base.readMemoryCardNBT(((UsableMetaItem) player.getHeldItemMainhand().getItem()).getDataStored(player.getHeldItemMainhand()));
                return true;
            }
            // Open GUI
            if (!player.isSneaking() && base != null) {
                if (PlayerUtil.canPlayerAccessBlock(player, base)) {
                    world.notifyBlockUpdate(pos, state, state, 6);
                    player.openGui(OpenModularTurrets.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
                } else {
                    addChatMessage(player, new TextComponentString(safeLocalize("status.ownership")));
                }
            }
        }
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos neighbor) {
        if (!worldIn.isRemote) {
            TurretBase base = (TurretBase) worldIn.getTileEntity(pos);
            if (base != null && worldIn.isBlockIndirectlyGettingPowered(pos) > 0) {
                base.setRedstone(true);
            } else if (base != null && worldIn.isBlockIndirectlyGettingPowered(pos) == 0) {
                base.setRedstone(false);
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack
            stack) {

        if (!worldIn.isRemote && worldIn.getTileEntity(pos) instanceof TurretBase) {
            EntityPlayerMP player = (EntityPlayerMP) placer;
            TurretBase base = (TurretBase) worldIn.getTileEntity(pos);
            if (base == null) {
                return;
            }
            base.setCamoState(state);
            base.setOwner(player.getUniqueID().toString());
            if (worldIn.isBlockIndirectlyGettingPowered(pos) > 0) {
                base.setRedstone(true);
            } else if (worldIn.isBlockIndirectlyGettingPowered(pos) == 0) {
                base.setRedstone(false);
            }
            base.markDirty();
            switch (state.getValue(TIER)) {
                case 1:
                    this.setResistance(OMTConfig.BASES.baseTierOne.getBaseBlastResistance());
                    this.setHardness(OMTConfig.BASES.baseTierOne.getBaseHardness());
                    break;
                case 2:
                    this.setResistance(OMTConfig.BASES.baseTierTwo.getBaseBlastResistance());
                    this.setHardness(OMTConfig.BASES.baseTierTwo.getBaseHardness());
                    break;
                case 3:
                    this.setResistance(OMTConfig.BASES.baseTierThree.getBaseBlastResistance());
                    this.setHardness(OMTConfig.BASES.baseTierThree.getBaseHardness());
                    break;
                case 4:
                    this.setResistance(OMTConfig.BASES.baseTierFour.getBaseBlastResistance());
                    this.setHardness(OMTConfig.BASES.baseTierFour.getBaseHardness());
                    break;
                case 5:
                    this.setResistance(OMTConfig.BASES.baseTierFive.getBaseBlastResistance());
                    this.setHardness(OMTConfig.BASES.baseTierFive.getBaseHardness());
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        for (EnumFacing facing : EnumFacing.VALUES) {
            for (TileEntity tileEntity : getTouchingTileEntities(worldIn, pos.offset(facing))) {
                if (tileEntity instanceof TurretBase) return false;
            }
        }
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            dropItems(worldIn, pos);
            worldIn.removeTileEntity(pos);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        if (OMTConfig.BASES.baseBreakable) {
            switch (blockState.getValue(TIER)) {
                case 1:
                    return OMTConfig.BASES.baseTierOne.getBaseHardness();
                case 2:
                    return OMTConfig.BASES.baseTierTwo.getBaseHardness();
                case 3:
                    return OMTConfig.BASES.baseTierThree.getBaseHardness();
                case 4:
                    return OMTConfig.BASES.baseTierFour.getBaseHardness();
                case 5:
                    return OMTConfig.BASES.baseTierFive.getBaseHardness();
            }
            return 10.0F;
        } else {
            return -1.0F;
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        if (OMTConfig.BASES.baseBreakable) {
            switch (world.getBlockState(pos).getValue(TIER)) {
                case 1:
                    return OMTConfig.BASES.baseTierOne.getBaseBlastResistance();
                case 2:
                    return OMTConfig.BASES.baseTierTwo.getBaseBlastResistance();
                case 3:
                    return OMTConfig.BASES.baseTierThree.getBaseBlastResistance();
                case 4:
                    return OMTConfig.BASES.baseTierFour.getBaseBlastResistance();
                case 5:
                    return OMTConfig.BASES.baseTierFive.getBaseBlastResistance();
            }
            return 10.0F;
        } else {
            return -1.0F;
        }
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        drops.add(0, new ItemStack(ModBlocks.turretBase, 1, this.getMetaFromState(state)));
        return drops;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(TIER) - 1;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer
            player) {
        return new ItemStack(ModBlocks.turretBase, 1, state.getValue(TIER) - 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    @ParametersAreNonnullByDefault
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> subItems) {
        for (int i = 0; i < 5; i++) {
            subItems.add(new ItemStack(ModBlocks.turretBase, 1, i));
        }
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TurretBase) {
            TurretBase base = (TurretBase) te;
            EnumMachineMode machineMode = base.getMode();
            boolean active = base.isActive();
            probeInfo.text("\u00A76" + safeLocalize(OMLibNames.Localizations.GUI.MODE) + ": \u00A7A" + getMachineModeLocalization(machineMode));
            probeInfo.text("\u00A76" + safeLocalize(OMLibNames.Localizations.GUI.ACTIVE) + ": " + getColoredBooleanLocalizationYesNo(active));
            String ownerName = base.getOwnerName();
            probeInfo.text("\u00A76" + safeLocalize(OMLibNames.Localizations.GUI.OWNER) + ": \u00A7F" + ownerName);
        }
    }
}
