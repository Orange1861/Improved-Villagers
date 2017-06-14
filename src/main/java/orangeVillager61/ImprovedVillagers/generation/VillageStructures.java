package orangeVillager61.ImprovedVillagers.generation;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeMesa;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeSavanna;
import net.minecraft.world.biome.BiomeTaiga;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;
import orangeVillager61.ImprovedVillagers.CommonProxy;
import orangeVillager61.ImprovedVillagers.Reflector;
import orangeVillager61.ImprovedVillagers.Blocks.IvBlocks;

public class VillageStructures
{
    public static void registerVillagePieces()
    {
        MapGenStructureIO.registerStructureComponent(VillageStructures.House1.class, "ViBH");
        MapGenStructureIO.registerStructureComponent(VillageStructures.Field1.class, "ViDF");
        MapGenStructureIO.registerStructureComponent(VillageStructures.Field2.class, "ViF");
        MapGenStructureIO.registerStructureComponent(VillageStructures.Torch.class, "ViL");
        MapGenStructureIO.registerStructureComponent(VillageStructures.Hall.class, "ViPH");
        MapGenStructureIO.registerStructureComponent(VillageStructures.House4Garden.class, "ViSH");
        MapGenStructureIO.registerStructureComponent(VillageStructures.WoodHut.class, "ViSmH");
        MapGenStructureIO.registerStructureComponent(VillageStructures.Church.class, "ViST");
        MapGenStructureIO.registerStructureComponent(VillageStructures.House2.class, "ViS");
        MapGenStructureIO.registerStructureComponent(VillageStructures.Start.class, "ViStart");
        MapGenStructureIO.registerStructureComponent(VillageStructures.Path.class, "ViSR");
        MapGenStructureIO.registerStructureComponent(VillageStructures.House3.class, "ViTRH");
        MapGenStructureIO.registerStructureComponent(VillageStructures.Well.class, "ViW");
    }

    public static List<VillageStructures.PieceWeight> getStructureVillageWeightedPieceList(Random random, int size)
    {
        List<VillageStructures.PieceWeight> list = Lists.<VillageStructures.PieceWeight>newArrayList();
        list.add(new VillageStructures.PieceWeight(VillageStructures.House4Garden.class, 4, MathHelper.getInt(random, 4 + size, 11 + size * 2)));
        list.add(new VillageStructures.PieceWeight(VillageStructures.Church.class, 20, MathHelper.getInt(random, 0 + size, 2 + size/2)));
        list.add(new VillageStructures.PieceWeight(VillageStructures.House1.class, 20, MathHelper.getInt(random, 0 + size, 2 + size/2)));
        list.add(new VillageStructures.PieceWeight(VillageStructures.WoodHut.class, 3, MathHelper.getInt(random, 4 + size, 11 + size * 3)));
        list.add(new VillageStructures.PieceWeight(VillageStructures.Hall.class, 15, MathHelper.getInt(random, 1 + size, 4 + size)));
        list.add(new VillageStructures.PieceWeight(VillageStructures.Field1.class, 3, MathHelper.getInt(random, 4 + size, 10 + size)));
        list.add(new VillageStructures.PieceWeight(VillageStructures.Field2.class, 3, MathHelper.getInt(random, 5 + size, 10 + size * 2)));
        list.add(new VillageStructures.PieceWeight(VillageStructures.House2.class, 15, MathHelper.getInt(random, 0, 2 + size/2)));
        list.add(new VillageStructures.PieceWeight(VillageStructures.House3.class, 8, MathHelper.getInt(random, 0 + size, 3 + size * 2)));
        Reflector.reflectMethod1(list, random, size);
        Iterator<VillageStructures.PieceWeight> iterator = list.iterator();

        while (iterator.hasNext())
        {
            if (((VillageStructures.PieceWeight)iterator.next()).villagePiecesLimit == 0)
            {
                iterator.remove();
            }
        }

        return list;
    }

    private static int updatePieceWeight(List<VillageStructures.PieceWeight> p_75079_0_)
    {
        boolean flag = false;
        int i = 0;

        for (VillageStructures.PieceWeight structurevillagepieces$pieceweight : p_75079_0_)
        {
            if (structurevillagepieces$pieceweight.villagePiecesLimit > 0 && structurevillagepieces$pieceweight.villagePiecesSpawned < structurevillagepieces$pieceweight.villagePiecesLimit)
            {
                flag = true;
            }

            i += structurevillagepieces$pieceweight.villagePieceWeight;
        }

        return flag ? i : -1;
    }

    private static VillageStructures.Village findAndCreateComponentFactory(VillageStructures.Start start, VillageStructures.PieceWeight weight, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType)
    {
        Class <? extends VillageStructures.Village > oclass = weight.villagePieceClass;
        VillageStructures.Village structurevillagepieces$village = null;

        if (oclass == VillageStructures.House4Garden.class)
        {
            structurevillagepieces$village = VillageStructures.House4Garden.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if (oclass == VillageStructures.Church.class)
        {
            structurevillagepieces$village = VillageStructures.Church.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if (oclass == VillageStructures.House1.class)
        {
            structurevillagepieces$village = VillageStructures.House1.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if (oclass == VillageStructures.WoodHut.class)
        {
            structurevillagepieces$village = VillageStructures.WoodHut.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if (oclass == VillageStructures.Hall.class)
        {
            structurevillagepieces$village = VillageStructures.Hall.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if (oclass == VillageStructures.Field1.class)
        {
            structurevillagepieces$village = VillageStructures.Field1.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if (oclass == VillageStructures.Field2.class)
        {
            structurevillagepieces$village = VillageStructures.Field2.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if (oclass == VillageStructures.House2.class)
        {
            structurevillagepieces$village = VillageStructures.House2.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if (oclass == VillageStructures.House3.class)
        {
            structurevillagepieces$village = VillageStructures.House3.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else
        {
            structurevillagepieces$village = Reflector.reflectMethod2(start, weight, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }

        return structurevillagepieces$village;
    }

    private static VillageStructures.Village generateComponent(VillageStructures.Start start, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType)
    {
        int i = updatePieceWeight(start.structureVillageWeightedPieceList);

        if (i <= 0)
        {
            return null;
        }
        else
        {
            int j = 0;

            while (j < 5)
            {
                ++j;
                int k = rand.nextInt(i);

                for (VillageStructures.PieceWeight structurevillagepieces$pieceweight : start.structureVillageWeightedPieceList)
                {
                    k -= structurevillagepieces$pieceweight.villagePieceWeight;

                    if (k < 0)
                    {
                        if (!structurevillagepieces$pieceweight.canSpawnMoreVillagePiecesOfType(componentType) || structurevillagepieces$pieceweight == start.structVillagePieceWeight && start.structureVillageWeightedPieceList.size() > 1)
                        {
                            break;
                        }

                        VillageStructures.Village structurevillagepieces$village = findAndCreateComponentFactory(start, structurevillagepieces$pieceweight, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);

                        if (structurevillagepieces$village != null)
                        {
                            ++structurevillagepieces$pieceweight.villagePiecesSpawned;
                            start.structVillagePieceWeight = structurevillagepieces$pieceweight;

                            if (!structurevillagepieces$pieceweight.canSpawnMoreVillagePieces())
                            {
                                start.structureVillageWeightedPieceList.remove(structurevillagepieces$pieceweight);
                            }

                            return structurevillagepieces$village;
                        }
                    }
                }
            }

            StructureBoundingBox structureboundingbox = VillageStructures.Torch.findPieceBox(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing);

            if (structureboundingbox != null)
            {
                return new VillageStructures.Torch(start, componentType, rand, structureboundingbox, facing);
            }
            else
            {
                return null;
            }
        }
    }

    private static StructureComponent generateAndAddComponent(VillageStructures.Start start, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType)
    {
        if (componentType > 50)
        {
            return null;
        }
        else if (Math.abs(structureMinX - start.getBoundingBox().minX) <= 112 && Math.abs(structureMinZ - start.getBoundingBox().minZ) <= 112)
        {
            StructureComponent structurecomponent = generateComponent(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType + 1);

            if (structurecomponent != null)
            {
                structureComponents.add(structurecomponent);
                start.pendingHouses.add(structurecomponent);
                return structurecomponent;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    private static StructureComponent generateAndAddRoadPiece(VillageStructures.Start start, List<StructureComponent> p_176069_1_, Random rand, int p_176069_3_, int p_176069_4_, int p_176069_5_, EnumFacing facing, int p_176069_7_, boolean isMetro)
    {
        if (p_176069_7_ > 3 + start.terrainType)
        {
            return null;
        }
        else if (Math.abs(p_176069_3_ - start.getBoundingBox().minX) <= 112 && Math.abs(p_176069_5_ - start.getBoundingBox().minZ) <= 112)
        {
            StructureBoundingBox structureboundingbox = VillageStructures.Path.findPieceBox(start, p_176069_1_, rand, p_176069_3_, p_176069_4_, p_176069_5_, facing);

            if (structureboundingbox != null && structureboundingbox.minY > 10)
            {
                StructureComponent structurecomponent = new VillageStructures.Path(start, p_176069_7_, rand, structureboundingbox, facing, isMetro);
                p_176069_1_.add(structurecomponent);
                start.pendingRoads.add(structurecomponent);
                return structurecomponent;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public static class Church extends VillageStructures.Village
        {
            public Church()
            {
            }

            public Church(VillageStructures.Start start, int type, Random rand, StructureBoundingBox p_i45564_4_, EnumFacing facing)
            {
                super(start, type);
                MapGenStructureIO.registerStructureComponent(Church.class, "Village");
                this.setCoordBaseMode(facing);
                this.boundingBox = p_i45564_4_;
            }

            public static VillageStructures.Church createPiece(VillageStructures.Start start, List<StructureComponent> p_175854_1_, Random rand, int p_175854_3_, int p_175854_4_, int p_175854_5_, EnumFacing facing, int p_175854_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175854_3_, p_175854_4_, p_175854_5_, 0, 0, 0, 5, 12, 9, facing);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175854_1_, structureboundingbox) == null ? new VillageStructures.Church(start, p_175854_7_, rand, structureboundingbox, facing) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
            {
                if (this.averageGroundLvl < 0)
                {
                    this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                    if (this.averageGroundLvl < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 12 - 1, 0);
                }

                IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
                IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
                IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 7, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 9, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 3, 0, 8, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 3, 10, 0, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 10, 3, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 10, 3, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 0, 4, 7, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 4, 4, 4, 7, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 8, 3, 4, 8, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 4, 3, 10, 4, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 5, 3, 5, 7, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 9, 0, 4, 9, 4, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, iblockstate, iblockstate, false);
                this.setBlockState(worldIn, iblockstate, 0, 11, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 4, 11, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 2, 11, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 2, 11, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 1, 1, 6, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 1, 1, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 2, 1, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 3, 1, 6, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 3, 1, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 1, 1, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 2, 1, 6, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 3, 1, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2, 1, 2, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 3, 2, 7, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 6, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 7, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 6, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 7, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 6, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 7, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 6, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 7, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 6, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 6, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 3, 8, structureBoundingBoxIn);
                this.placeTorch(worldIn, EnumFacing.SOUTH, 2, 4, 7, structureBoundingBoxIn);
                this.placeTorch(worldIn, EnumFacing.EAST, 1, 4, 6, structureBoundingBoxIn);
                this.placeTorch(worldIn, EnumFacing.WEST, 3, 4, 6, structureBoundingBoxIn);
                this.placeTorch(worldIn, EnumFacing.NORTH, 2, 4, 5, structureBoundingBoxIn);
                IBlockState iblockstate4 = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.WEST);

                for (int i = 1; i <= 9; ++i)
                {
                    this.setBlockState(worldIn, iblockstate4, 3, i, 3, structureBoundingBoxIn);
                }

                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
                this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.NORTH);

                if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
                {
                    this.setBlockState(worldIn, iblockstate1, 2, 0, -1, structureBoundingBoxIn);

                    if (this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
                    {
                        this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
                    }
                }

                for (int k = 0; k < 9; ++k)
                {
                    for (int j = 0; j < 5; ++j)
                    {
                        this.clearCurrentPositionBlocksUpwards(worldIn, j, 12, k, structureBoundingBoxIn);
                        this.replaceAirAndLiquidDownwards(worldIn, iblockstate, j, -1, k, structureBoundingBoxIn);
                    }
                }

                this.spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1, false);
                return true;
            }

            protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
            {
                return 2;
            }
        }
    
    public static class Field1 extends VillageStructures.Village
        {
            /** First crop type for this field. */
            private Block cropTypeA;
            /** Second crop type for this field. */
            private Block cropTypeB;
            /** Third crop type for this field. */
            private Block cropTypeC;
            /** Fourth crop type for this field. */
            private Block cropTypeD;

            public Field1()
            {
            }

            public Field1(VillageStructures.Start start, int type, Random rand, StructureBoundingBox p_i45570_4_, EnumFacing facing)
            {
                super(start, type);
                MapGenStructureIO.registerStructureComponent(Field1.class, "Village");
                this.setCoordBaseMode(facing);
                this.boundingBox = p_i45570_4_;
                this.cropTypeA = this.getRandomCropType(rand);
                this.cropTypeB = this.getRandomCropType(rand);
                this.cropTypeC = this.getRandomCropType(rand);
                this.cropTypeD = this.getRandomCropType(rand);
            }

            /**
             * (abstract) Helper method to write subclass data to NBT
             */
            protected void writeStructureToNBT(NBTTagCompound tagCompound)
            {
                super.writeStructureToNBT(tagCompound);
                tagCompound.setInteger("CA", Block.REGISTRY.getIDForObject(this.cropTypeA));
                tagCompound.setInteger("CB", Block.REGISTRY.getIDForObject(this.cropTypeB));
                tagCompound.setInteger("CC", Block.REGISTRY.getIDForObject(this.cropTypeC));
                tagCompound.setInteger("CD", Block.REGISTRY.getIDForObject(this.cropTypeD));
            }

            /**
             * (abstract) Helper method to read subclass data from NBT
             */
            protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
            {
                super.readStructureFromNBT(tagCompound, p_143011_2_);
                this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
                this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
                this.cropTypeC = Block.getBlockById(tagCompound.getInteger("CC"));
                this.cropTypeD = Block.getBlockById(tagCompound.getInteger("CD"));

                if (!(this.cropTypeA instanceof BlockCrops))
                {
                    this.cropTypeA = Blocks.WHEAT;
                }

                if (!(this.cropTypeB instanceof BlockCrops))
                {
                    this.cropTypeB = Blocks.CARROTS;
                }

                if (!(this.cropTypeC instanceof BlockCrops))
                {
                    this.cropTypeC = Blocks.POTATOES;
                }

                if (!(this.cropTypeD instanceof BlockCrops))
                {
                    this.cropTypeD = Blocks.BEETROOTS;
                }
            }

            private Block getRandomCropType(Random rand)
            {
                switch (rand.nextInt(10))
                {
                    case 0:
                    case 1:
                        return Blocks.CARROTS;
                    case 2:
                    case 3:
                        return Blocks.POTATOES;
                    case 4:
                        return Blocks.BEETROOTS;
                    default:
                        return Blocks.WHEAT;
                }
            }

            public static VillageStructures.Field1 createPiece(VillageStructures.Start start, List<StructureComponent> p_175851_1_, Random rand, int p_175851_3_, int p_175851_4_, int p_175851_5_, EnumFacing facing, int p_175851_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175851_3_, p_175851_4_, p_175851_5_, 0, 0, 0, 13, 4, 9, facing);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175851_1_, structureboundingbox) == null ? new VillageStructures.Field1(start, p_175851_7_, rand, structureboundingbox, facing) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
            {
                if (this.averageGroundLvl < 0)
                {
                    this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                    if (this.averageGroundLvl < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
                }

                IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 12, 4, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 1, 8, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 1, 11, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 0, 0, 12, 0, 8, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 11, 0, 0, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 11, 0, 8, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 1, 9, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);

                for (int i = 1; i <= 7; ++i)
                {
                    int j = ((BlockCrops)this.cropTypeA).getMaxAge();
                    int k = j / 3;
                    this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, k, j)), 1, 1, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, k, j)), 2, 1, i, structureBoundingBoxIn);
                    int l = ((BlockCrops)this.cropTypeB).getMaxAge();
                    int i1 = l / 3;
                    this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, i1, l)), 4, 1, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, i1, l)), 5, 1, i, structureBoundingBoxIn);
                    int j1 = ((BlockCrops)this.cropTypeC).getMaxAge();
                    int k1 = j1 / 3;
                    this.setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getInt(randomIn, k1, j1)), 7, 1, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getInt(randomIn, k1, j1)), 8, 1, i, structureBoundingBoxIn);
                    int l1 = ((BlockCrops)this.cropTypeD).getMaxAge();
                    int i2 = l1 / 3;
                    this.setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getInt(randomIn, i2, l1)), 10, 1, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getInt(randomIn, i2, l1)), 11, 1, i, structureBoundingBoxIn);
                }

                for (int j2 = 0; j2 < 9; ++j2)
                {
                    for (int k2 = 0; k2 < 13; ++k2)
                    {
                        this.clearCurrentPositionBlocksUpwards(worldIn, k2, 4, j2, structureBoundingBoxIn);
                        this.replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), k2, -1, j2, structureBoundingBoxIn);
                    }
                }

                return true;
            }
        }

    public static class Field2 extends VillageStructures.Village
        {
            /** First crop type for this field. */
            private Block cropTypeA;
            /** Second crop type for this field. */
            private Block cropTypeB;

            public Field2()
            {
            }

            public Field2(VillageStructures.Start start, int p_i45569_2_, Random rand, StructureBoundingBox p_i45569_4_, EnumFacing facing)
            {
                super(start, p_i45569_2_);
                MapGenStructureIO.registerStructureComponent(Field2.class, "Village");
                this.setCoordBaseMode(facing);
                this.boundingBox = p_i45569_4_;
                this.cropTypeA = this.getRandomCropType(rand);
                this.cropTypeB = this.getRandomCropType(rand);
            }

            /**
             * (abstract) Helper method to write subclass data to NBT
             */
            protected void writeStructureToNBT(NBTTagCompound tagCompound)
            {
                super.writeStructureToNBT(tagCompound);
                tagCompound.setInteger("CA", Block.REGISTRY.getIDForObject(this.cropTypeA));
                tagCompound.setInteger("CB", Block.REGISTRY.getIDForObject(this.cropTypeB));
            }

            /**
             * (abstract) Helper method to read subclass data from NBT
             */
            protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
            {
                super.readStructureFromNBT(tagCompound, p_143011_2_);
                this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
                this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
            }

            private Block getRandomCropType(Random rand)
            {
                switch (rand.nextInt(10))
                {
                    case 0:
                    case 1:
                        return Blocks.CARROTS;
                    case 2:
                    case 3:
                        return Blocks.POTATOES;
                    case 4:
                        return Blocks.BEETROOTS;
                    default:
                        return Blocks.WHEAT;
                }
            }

            public static VillageStructures.Field2 createPiece(VillageStructures.Start start, List<StructureComponent> p_175852_1_, Random rand, int p_175852_3_, int p_175852_4_, int p_175852_5_, EnumFacing facing, int p_175852_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175852_3_, p_175852_4_, p_175852_5_, 0, 0, 0, 7, 4, 9, facing);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175852_1_, structureboundingbox) == null ? new VillageStructures.Field2(start, p_175852_7_, rand, structureboundingbox, facing) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
            {
                if (this.averageGroundLvl < 0)
                {
                    this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                    if (this.averageGroundLvl < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
                }

                IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 6, 4, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 0, 0, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 5, 0, 8, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);

                for (int i = 1; i <= 7; ++i)
                {
                    int j = ((BlockCrops)this.cropTypeA).getMaxAge();
                    int k = j / 3;
                    this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, k, j)), 1, 1, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, k, j)), 2, 1, i, structureBoundingBoxIn);
                    int l = ((BlockCrops)this.cropTypeB).getMaxAge();
                    int i1 = l / 3;
                    this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, i1, l)), 4, 1, i, structureBoundingBoxIn);
                    this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, i1, l)), 5, 1, i, structureBoundingBoxIn);
                }

                for (int j1 = 0; j1 < 9; ++j1)
                {
                    for (int k1 = 0; k1 < 7; ++k1)
                    {
                        this.clearCurrentPositionBlocksUpwards(worldIn, k1, 4, j1, structureBoundingBoxIn);
                        this.replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), k1, -1, j1, structureBoundingBoxIn);
                    }
                }

                return true;
            }
        }

    public static class Hall extends VillageStructures.Village
        {
            public Hall()
            {
            }

            public Hall(VillageStructures.Start start, int type, Random rand, StructureBoundingBox p_i45567_4_, EnumFacing facing)
            {
                super(start, type);
                MapGenStructureIO.registerStructureComponent(Hall.class, "Village");
                this.setCoordBaseMode(facing);
                this.boundingBox = p_i45567_4_;
            }

            public static VillageStructures.Hall createPiece(VillageStructures.Start start, List<StructureComponent> p_175857_1_, Random rand, int p_175857_3_, int p_175857_4_, int p_175857_5_, EnumFacing facing, int p_175857_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175857_3_, p_175857_4_, p_175857_5_, 0, 0, 0, 9, 7, 11, facing);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175857_1_, structureboundingbox) == null ? new VillageStructures.Hall(start, p_175857_7_, rand, structureboundingbox, facing) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
            {
                if (this.averageGroundLvl < 0)
                {
                    this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                    if (this.averageGroundLvl < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 7 - 1, 0);
                }

                IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
                IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
                IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
                IBlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
                IBlockState iblockstate5 = this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
                IBlockState iblockstate9 = this.getBiomeSpecificBlockState(Blocks.GRASS.getDefaultState());
                IBlockState iblockstate6 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 8, 0, 10, iblockstate9, iblockstate9, false);
                this.setBlockState(worldIn, iblockstate, 6, 0, 6, structureBoundingBoxIn);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 2, 1, 10, iblockstate6, iblockstate6, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 6, 8, 1, 10, iblockstate6, iblockstate6, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 10, 7, 1, 10, iblockstate6, iblockstate6, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, iblockstate4, iblockstate4, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 5, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 1, 0, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 7, 1, 5, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, iblockstate4, iblockstate4, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 3, 5, iblockstate4, iblockstate4, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, iblockstate4, iblockstate4, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 8, 4, 4, iblockstate4, iblockstate4, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, iblockstate4, iblockstate4, false);
                this.setBlockState(worldIn, iblockstate4, 0, 4, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate4, 0, 4, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate4, 8, 4, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate4, 8, 4, 3, structureBoundingBoxIn);
                IBlockState iblockstate7 = iblockstate1;
                IBlockState iblockstate8 = iblockstate2;

                for (int i = -1; i <= 2; ++i)
                {
                    for (int j = 0; j <= 8; ++j)
                    {
                        this.setBlockState(worldIn, iblockstate7, j, 4 + i, i, structureBoundingBoxIn);
                        this.setBlockState(worldIn, iblockstate8, j, 4 + i, 5 - i, structureBoundingBoxIn);
                    }
                }

                this.setBlockState(worldIn, iblockstate5, 0, 2, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 0, 2, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 8, 2, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate5, 8, 2, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate6, 2, 1, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 2, 2, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate4, 1, 1, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate7, 2, 1, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 1, 1, 3, structureBoundingBoxIn);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 1, 7, 0, 3, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), Blocks.DOUBLE_STONE_SLAB.getDefaultState(), false);
                this.setBlockState(worldIn, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 6, 1, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 6, 1, 2, structureBoundingBoxIn);
                this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 7, 1, 1, CommonProxy.VILLAGE_BUTCHER_LT); 
                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
                this.placeTorch(worldIn, EnumFacing.NORTH, 2, 3, 1, structureBoundingBoxIn);
                this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.NORTH);

                if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
                {
                    this.setBlockState(worldIn, iblockstate7, 2, 0, -1, structureBoundingBoxIn);

                    if (this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
                    {
                        this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
                    }
                }

                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
                this.placeTorch(worldIn, EnumFacing.SOUTH, 6, 3, 4, structureBoundingBoxIn);
                this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 6, 1, 5, EnumFacing.SOUTH);

                for (int k = 0; k < 5; ++k)
                {
                    for (int l = 0; l < 9; ++l)
                    {
                        this.clearCurrentPositionBlocksUpwards(worldIn, l, 7, k, structureBoundingBoxIn);
                        this.replaceAirAndLiquidDownwards(worldIn, iblockstate, l, -1, k, structureBoundingBoxIn);
                    }
                }

                this.spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2, false);
                this.spawnFarmAnimals(worldIn, structureBoundingBoxIn, 3, 1, 9, 4);
                return true;
            }

            protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
            {
                return villagersSpawnedIn == 0 ? 4 : super.chooseProfession(villagersSpawnedIn, currentVillagerProfession);
            }
        }
    
    public static class House1 extends VillageStructures.Village
        {
            public House1()
            {
            }

            public House1(VillageStructures.Start start, int type, Random rand, StructureBoundingBox p_i45571_4_, EnumFacing facing)
            {
                super(start, type);
                MapGenStructureIO.registerStructureComponent(House1.class, "Village");
                this.setCoordBaseMode(facing);
                this.boundingBox = p_i45571_4_;
            }

            public static VillageStructures.House1 createPiece(VillageStructures.Start start, List<StructureComponent> p_175850_1_, Random rand, int p_175850_3_, int p_175850_4_, int p_175850_5_, EnumFacing facing, int p_175850_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175850_3_, p_175850_4_, p_175850_5_, 0, 0, 0, 9, 9, 6, facing);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175850_1_, structureboundingbox) == null ? new VillageStructures.House1(start, p_175850_7_, rand, structureboundingbox, facing) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
            {
                if (this.averageGroundLvl < 0)
                {
                    this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                    if (this.averageGroundLvl < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 9 - 1, 0);
                }

                IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
                IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
                IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
                IBlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
                IBlockState iblockstate5 = this.getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                IBlockState iblockstate6 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 0, 5, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 8, 5, 5, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 1, 8, 6, 4, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 7, 2, 8, 7, 3, iblockstate, iblockstate, false);

                for (int i = -1; i <= 2; ++i)
                {
                    for (int j = 0; j <= 8; ++j)
                    {
                        this.setBlockState(worldIn, iblockstate1, j, 6 + i, i, structureBoundingBoxIn);
                        this.setBlockState(worldIn, iblockstate2, j, 6 + i, 5 - i, structureBoundingBoxIn);
                    }
                }

                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 5, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 8, 1, 5, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 0, 8, 1, 4, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 7, 1, 0, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 4, 0, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 4, 5, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 5, 8, 4, 5, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 0, 8, 4, 0, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 4, 4, iblockstate4, iblockstate4, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 4, 5, iblockstate4, iblockstate4, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 1, 8, 4, 4, iblockstate4, iblockstate4, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 4, 0, iblockstate4, iblockstate4, false);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 3, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 3, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 3, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 3, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 1, iblockstate4, iblockstate4, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 4, 7, 4, 4, iblockstate4, iblockstate4, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 7, 3, 4, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
                this.setBlockState(worldIn, iblockstate4, 7, 1, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 7, 1, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 6, 1, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 5, 1, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 4, 1, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 3, 1, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate6, 6, 1, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate6, 4, 1, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 4, 2, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.CRAFTING_TABLE.getDefaultState(), 7, 1, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
                this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.NORTH);
                this.placeTorch(worldIn, EnumFacing.EAST, 1, 3, 1, structureBoundingBoxIn);
                this.placeTorch(worldIn, EnumFacing.WEST, 7, 3, 1, structureBoundingBoxIn);

                if (this.getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
                {
                    this.setBlockState(worldIn, iblockstate5, 1, 0, -1, structureBoundingBoxIn);

                    if (this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
                    {
                        this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 1, -1, -1, structureBoundingBoxIn);
                    }
                }

                for (int l = 0; l < 6; ++l)
                {
                    for (int k = 0; k < 9; ++k)
                    {
                        this.clearCurrentPositionBlocksUpwards(worldIn, k, 9, l, structureBoundingBoxIn);
                        this.replaceAirAndLiquidDownwards(worldIn, iblockstate, k, -1, l, structureBoundingBoxIn);
                    }
                }

                this.spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1, false);
                return true;
            }

            protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
            {
                return 1;
            }
        }

    public static class House2 extends VillageStructures.Village
        {
            private boolean hasMadeChest;

            public House2()
            {
            }

            public House2(VillageStructures.Start start, int type, Random rand, StructureBoundingBox p_i45563_4_, EnumFacing facing)
            {
                super(start, type);
                MapGenStructureIO.registerStructureComponent(House2.class, "Village");
                this.setCoordBaseMode(facing);
                this.boundingBox = p_i45563_4_;
            }

            public static VillageStructures.House2 createPiece(VillageStructures.Start start, List<StructureComponent> p_175855_1_, Random rand, int p_175855_3_, int p_175855_4_, int p_175855_5_, EnumFacing facing, int p_175855_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175855_3_, p_175855_4_, p_175855_5_, 0, 0, 0, 10, 6, 7, facing);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175855_1_, structureboundingbox) == null ? new VillageStructures.House2(start, p_175855_7_, rand, structureboundingbox, facing) : null;
            }

            /**
             * (abstract) Helper method to write subclass data to NBT
             */
            protected void writeStructureToNBT(NBTTagCompound tagCompound)
            {
                super.writeStructureToNBT(tagCompound);
                tagCompound.setBoolean("Chest", this.hasMadeChest);
            }

            /**
             * (abstract) Helper method to read subclass data from NBT
             */
            protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
            {
                super.readStructureFromNBT(tagCompound, p_143011_2_);
                this.hasMadeChest = tagCompound.getBoolean("Chest");
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
            {
                if (this.averageGroundLvl < 0)
                {
                    this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                    if (this.averageGroundLvl < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
                }

                IBlockState iblockstate = Blocks.COBBLESTONE.getDefaultState();
                IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
                IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
                IBlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                IBlockState iblockstate5 = this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
                IBlockState iblockstate6 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 9, 4, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 0, 6, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 9, 4, 6, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 9, 5, 6, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 8, 5, 5, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, iblockstate3, iblockstate3, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 4, 0, iblockstate5, iblockstate5, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 4, 0, iblockstate5, iblockstate5, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 6, 0, 4, 6, iblockstate5, iblockstate5, false);
                this.setBlockState(worldIn, iblockstate3, 3, 3, 1, structureBoundingBoxIn);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 3, 2, iblockstate3, iblockstate3, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 5, 3, 3, iblockstate3, iblockstate3, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 5, iblockstate3, iblockstate3, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 6, 5, 3, 6, iblockstate3, iblockstate3, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 5, 3, 0, iblockstate6, iblockstate6, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 0, 9, 3, 0, iblockstate6, iblockstate6, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 4, 9, 4, 6, iblockstate, iblockstate, false);
                this.setBlockState(worldIn, Blocks.FLOWING_LAVA.getDefaultState(), 7, 1, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.FLOWING_LAVA.getDefaultState(), 8, 1, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 9, 2, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 9, 2, 4, structureBoundingBoxIn);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 4, 8, 2, 5, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.setBlockState(worldIn, iblockstate, 6, 1, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.FURNACE.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.FURNACE.getDefaultState(), 6, 3, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 8, 1, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 6, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate6, 2, 1, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 1, 1, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 2, 1, 5, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2, 1, 1, 4, structureBoundingBoxIn);

                if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside(new BlockPos(this.getXWithOffset(5, 5), this.getYWithOffset(1), this.getZWithOffset(5, 5))))
                {
                    this.hasMadeChest = true;
                    this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 5, 1, 5, LootTableList.CHESTS_VILLAGE_BLACKSMITH);
                }

                for (int i = 6; i <= 8; ++i)
                {
                    if (this.getBlockStateFromPos(worldIn, i, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, i, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
                    {
                        this.setBlockState(worldIn, iblockstate4, i, 0, -1, structureBoundingBoxIn);

                        if (this.getBlockStateFromPos(worldIn, i, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
                        {
                            this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), i, -1, -1, structureBoundingBoxIn);
                        }
                    }
                }

                for (int k = 0; k < 7; ++k)
                {
                    for (int j = 0; j < 10; ++j)
                    {
                        this.clearCurrentPositionBlocksUpwards(worldIn, j, 6, k, structureBoundingBoxIn);
                        this.replaceAirAndLiquidDownwards(worldIn, iblockstate, j, -1, k, structureBoundingBoxIn);
                    }
                }

                this.spawnVillagers(worldIn, structureBoundingBoxIn, 7, 1, 1, 1, false);
                this.spawnVillagers(worldIn, structureBoundingBoxIn, 7, 1, 1, 1, true);
                return true;
            }

            protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
            {
                return 3;
            }
        }

    public static class House3 extends VillageStructures.Village
	{
	    public House3()
	    {
	    }
	
	    public House3(VillageStructures.Start start, int type, Random rand, StructureBoundingBox p_i45561_4_, EnumFacing facing)
	    {
	        super(start, type);
	        MapGenStructureIO.registerStructureComponent(House3.class, "Village");
	        this.setCoordBaseMode(facing);
	        this.boundingBox = p_i45561_4_;
	    }
	
	    public static VillageStructures.House3 createPiece(VillageStructures.Start start, List<StructureComponent> p_175849_1_, Random rand, int p_175849_3_, int p_175849_4_, int p_175849_5_, EnumFacing facing, int p_175849_7_)
	    {
	        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175849_3_, p_175849_4_, p_175849_5_, 0, 0, 0, 9, 7, 12, facing);
	        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175849_1_, structureboundingbox) == null ? new VillageStructures.House3(start, p_175849_7_, rand, structureboundingbox, facing) : null;
	    }
	
	    /**
	     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
	     * Mineshafts at the end, it adds Fences...
	     */
	    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
	    {
	        if (this.averageGroundLvl < 0)
	        {
	            this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
	
	            if (this.averageGroundLvl < 0)
	            {
	                return true;
	            }
	
	            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 7 - 1, 0);
	        }
	
	        IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
	        IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
	        IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
	        IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
	        IBlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
	        IBlockState iblockstate5 = this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
	        IBlockState iblockstate6 = this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 5, 8, 0, 10, iblockstate5, iblockstate5, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, iblockstate5, iblockstate5, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, iblockstate, iblockstate, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 10, iblockstate, iblockstate, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 2, 0, iblockstate, iblockstate, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 2, 1, 5, iblockstate, iblockstate, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 2, 3, 10, iblockstate, iblockstate, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 10, 7, 3, 10, iblockstate, iblockstate, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, iblockstate5, iblockstate5, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 2, 3, 5, iblockstate5, iblockstate5, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, iblockstate5, iblockstate5, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 3, 4, 4, iblockstate5, iblockstate5, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, iblockstate5, iblockstate5, false);
	        this.setBlockState(worldIn, iblockstate5, 0, 4, 2, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate5, 0, 4, 3, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate5, 8, 4, 2, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate5, 8, 4, 3, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate5, 8, 4, 4, structureBoundingBoxIn);
	        IBlockState iblockstate7 = iblockstate1;
	        IBlockState iblockstate8 = iblockstate2;
	        IBlockState iblockstate9 = iblockstate4;
	        IBlockState iblockstate10 = iblockstate3;
	
	        for (int i = -1; i <= 2; ++i)
	        {
	            for (int j = 0; j <= 8; ++j)
	            {
	                this.setBlockState(worldIn, iblockstate7, j, 4 + i, i, structureBoundingBoxIn);
	
	                if ((i > -1 || j <= 1) && (i > 0 || j <= 3) && (i > 1 || j <= 4 || j >= 6))
	                {
	                    this.setBlockState(worldIn, iblockstate8, j, 4 + i, 5 - i, structureBoundingBoxIn);
	                }
	            }
	        }
	
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 5, 3, 4, 10, iblockstate5, iblockstate5, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 2, 7, 4, 10, iblockstate5, iblockstate5, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 4, 4, 5, 10, iblockstate5, iblockstate5, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 4, 6, 5, 10, iblockstate5, iblockstate5, false);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 3, 5, 6, 10, iblockstate5, iblockstate5, false);
	
	        for (int k = 4; k >= 1; --k)
	        {
	            this.setBlockState(worldIn, iblockstate5, k, 2 + k, 7 - k, structureBoundingBoxIn);
	
	            for (int k1 = 8 - k; k1 <= 10; ++k1)
	            {
	                this.setBlockState(worldIn, iblockstate10, k, 2 + k, k1, structureBoundingBoxIn);
	            }
	        }
	
	        this.setBlockState(worldIn, iblockstate5, 6, 6, 3, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate5, 7, 5, 4, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate4, 6, 6, 4, structureBoundingBoxIn);
	
	        for (int l = 6; l <= 8; ++l)
	        {
	            for (int l1 = 5; l1 <= 10; ++l1)
	            {
	                this.setBlockState(worldIn, iblockstate9, l, 12 - l, l1, structureBoundingBoxIn);
	            }
	        }
	
	        this.setBlockState(worldIn, iblockstate6, 0, 2, 1, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate6, 0, 2, 4, structureBoundingBoxIn);
	        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
	        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate6, 4, 2, 0, structureBoundingBoxIn);
	        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate6, 6, 2, 0, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate6, 8, 2, 1, structureBoundingBoxIn);
	        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
	        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate6, 8, 2, 4, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate5, 8, 2, 5, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate6, 8, 2, 6, structureBoundingBoxIn);
	        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 7, structureBoundingBoxIn);
	        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 8, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate6, 8, 2, 9, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate6, 2, 2, 6, structureBoundingBoxIn);
	        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 7, structureBoundingBoxIn);
	        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 8, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate6, 2, 2, 9, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate6, 4, 4, 10, structureBoundingBoxIn);
	        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 4, 10, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate6, 6, 4, 10, structureBoundingBoxIn);
	        this.setBlockState(worldIn, iblockstate5, 5, 5, 10, structureBoundingBoxIn);
	        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
	        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
	        this.placeTorch(worldIn, EnumFacing.NORTH, 2, 3, 1, structureBoundingBoxIn);
	        this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.NORTH);
	        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, -1, 3, 2, -1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	
	        if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
	        {
	            this.setBlockState(worldIn, iblockstate7, 2, 0, -1, structureBoundingBoxIn);
	
	            if (this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
	            {
	                this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
	            }
	        }
	
	        for (int i1 = 0; i1 < 5; ++i1)
	        {
	            for (int i2 = 0; i2 < 9; ++i2)
	            {
	                this.clearCurrentPositionBlocksUpwards(worldIn, i2, 7, i1, structureBoundingBoxIn);
	                this.replaceAirAndLiquidDownwards(worldIn, iblockstate, i2, -1, i1, structureBoundingBoxIn);
	            }
	        }
	
	        for (int j1 = 5; j1 < 11; ++j1)
	        {
	            for (int j2 = 2; j2 < 9; ++j2)
	            {
	                this.clearCurrentPositionBlocksUpwards(worldIn, j2, 7, j1, structureBoundingBoxIn);
	                this.replaceAirAndLiquidDownwards(worldIn, iblockstate, j2, -1, j1, structureBoundingBoxIn);
	            }
	        }
	
	        this.spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2, true);
	        return true;
	    }
	}

    public static class House4Garden extends VillageStructures.Village
        {
            private boolean isRoofAccessible;

            public House4Garden()
            {
            }

            public House4Garden(VillageStructures.Start start, int p_i45566_2_, Random rand, StructureBoundingBox p_i45566_4_, EnumFacing facing)
            {
                super(start, p_i45566_2_);
                MapGenStructureIO.registerStructureComponent(House4Garden.class, "Village");
                this.setCoordBaseMode(facing);
                this.boundingBox = p_i45566_4_;
                this.isRoofAccessible = rand.nextBoolean();
            }

            /**
             * (abstract) Helper method to write subclass data to NBT
             */
            protected void writeStructureToNBT(NBTTagCompound tagCompound)
            {
                super.writeStructureToNBT(tagCompound);
                tagCompound.setBoolean("Terrace", this.isRoofAccessible);
            }

            /**
             * (abstract) Helper method to read subclass data from NBT
             */
            protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
            {
                super.readStructureFromNBT(tagCompound, p_143011_2_);
                this.isRoofAccessible = tagCompound.getBoolean("Terrace");
            }

            public static VillageStructures.House4Garden createPiece(VillageStructures.Start start, List<StructureComponent> p_175858_1_, Random rand, int p_175858_3_, int p_175858_4_, int p_175858_5_, EnumFacing facing, int p_175858_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175858_3_, p_175858_4_, p_175858_5_, 0, 0, 0, 5, 6, 5, facing);
                return StructureComponent.findIntersecting(p_175858_1_, structureboundingbox) != null ? null : new VillageStructures.House4Garden(start, p_175858_7_, rand, structureboundingbox, facing);
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
            {
                if (this.averageGroundLvl < 0)
                {
                    this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                    if (this.averageGroundLvl < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
                }

                IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
                IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
                IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
                IBlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, iblockstate3, iblockstate3, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 3, 4, 3, iblockstate1, iblockstate1, false);
                this.setBlockState(worldIn, iblockstate, 0, 1, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 0, 2, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 0, 3, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 4, 1, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 4, 2, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 4, 3, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 0, 1, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 0, 2, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 0, 3, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 4, 1, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 4, 2, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 4, 3, 4, structureBoundingBoxIn);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, iblockstate1, iblockstate1, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, iblockstate1, iblockstate1, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 3, 3, 4, iblockstate1, iblockstate1, false);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 1, 1, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 1, 2, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 1, 3, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 2, 3, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 3, 3, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 3, 2, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 3, 1, 0, structureBoundingBoxIn);
                this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.NORTH);
                this.placeTorch(worldIn, EnumFacing.SOUTH, 1, 3, 3, structureBoundingBoxIn);
                
                if (this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
                {
                    this.setBlockState(worldIn, iblockstate2, 2, 0, -1, structureBoundingBoxIn);

                    if (this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
                    {
                        this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
                    }
                }

                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

                if (this.isRoofAccessible)
                {
                    this.setBlockState(worldIn, iblockstate4, 0, 5, 0, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 1, 5, 0, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 2, 5, 0, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 3, 5, 0, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 4, 5, 0, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 0, 5, 4, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 1, 5, 4, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 2, 5, 4, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 3, 5, 4, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 4, 5, 4, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 4, 5, 1, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 4, 5, 2, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 4, 5, 3, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 0, 5, 1, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 0, 5, 2, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate4, 0, 5, 3, structureBoundingBoxIn);
                }

                if (this.isRoofAccessible)
                {
                    IBlockState iblockstate5 = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH);
                    this.setBlockState(worldIn, iblockstate5, 3, 1, 3, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate5, 3, 2, 3, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate5, 3, 3, 3, structureBoundingBoxIn);
                    this.setBlockState(worldIn, iblockstate5, 3, 4, 3, structureBoundingBoxIn);
                }

                this.placeTorch(worldIn, EnumFacing.NORTH, 2, 3, 1, structureBoundingBoxIn);

                for (int j = 0; j < 5; ++j)
                {
                    for (int i = 0; i < 5; ++i)
                    {
                        this.clearCurrentPositionBlocksUpwards(worldIn, i, 6, j, structureBoundingBoxIn);
                        this.replaceAirAndLiquidDownwards(worldIn, iblockstate, i, -1, j, structureBoundingBoxIn);
                    }
                }

                this.spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1, true);
                return true;
            }
        }

    public static class Path extends VillageStructures.Road
        {
            private int length;
			private boolean isMetropolis;

            public Path()
            {
            }

            public Path(VillageStructures.Start start, int p_i45562_2_, Random rand, StructureBoundingBox p_i45562_4_, EnumFacing facing, boolean isMetro)
            {
                super(start, p_i45562_2_);
                MapGenStructureIO.registerStructureComponent(Path.class, "Village");
                this.setCoordBaseMode(facing);
                this.boundingBox = p_i45562_4_;
                this.length = Math.max(p_i45562_4_.getXSize(), p_i45562_4_.getZSize());
                this.isMetropolis = isMetro;
            }

            /**
             * (abstract) Helper method to write subclass data to NBT
             */
            protected void writeStructureToNBT(NBTTagCompound tagCompound)
            {
                super.writeStructureToNBT(tagCompound);
                tagCompound.setInteger("Length", this.length);
            }

            /**
             * (abstract) Helper method to read subclass data from NBT
             */
            protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
            {
                super.readStructureFromNBT(tagCompound, p_143011_2_);
                this.length = tagCompound.getInteger("Length");
            }

            /**
             * Initiates construction of the Structure Component picked, at the current Location of StructGen
             */
            public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
            {
                boolean flag = false;

                for (int i = rand.nextInt(5); i < this.length - 8; i += 2 + rand.nextInt(5))
                {
                    StructureComponent structurecomponent = this.getNextComponentNN((VillageStructures.Start)componentIn, listIn, rand, 0, i);

                    if (structurecomponent != null)
                    {
                        i += Math.max(structurecomponent.getBoundingBox().getXSize(), structurecomponent.getBoundingBox().getZSize());
                        flag = true;
                    }
                }

                for (int j = rand.nextInt(5); j < this.length - 8; j += 2 + rand.nextInt(5))
                {
                    StructureComponent structurecomponent1 = this.getNextComponentPP((VillageStructures.Start)componentIn, listIn, rand, 0, j);

                    if (structurecomponent1 != null)
                    {
                        j += Math.max(structurecomponent1.getBoundingBox().getXSize(), structurecomponent1.getBoundingBox().getZSize());
                        flag = true;
                    }
                }

                EnumFacing enumfacing = this.getCoordBaseMode();

                if (flag && rand.nextInt(3) > 0 && enumfacing != null)
                {
                    switch (enumfacing)
                    {
                        case NORTH:
                        default:
                            VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, this.getComponentType(), this.isMetropolis);
                            break;
                        case SOUTH:
                            VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.WEST, this.getComponentType(), this.isMetropolis);
                            break;
                        case WEST:
                            VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType(), this.isMetropolis);
                            break;
                        case EAST:
                            VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType(), this.isMetropolis);
                    }
                }

                if (flag && rand.nextInt(3) > 0 && enumfacing != null)
                {
                    switch (enumfacing)
                    {
                        case NORTH:
                        default:
                            VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType(), this.isMetropolis);
                            break;
                        case SOUTH:
                            VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.EAST, this.getComponentType(), this.isMetropolis);
                            break;
                        case WEST:
                            VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType(), this.isMetropolis);
                            break;
                        case EAST:
                            VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType(), this.isMetropolis);
                    }
                }
                if (flag && rand.nextInt(3) > 0 && enumfacing != null && isMetropolis)
                {
                    switch (enumfacing)
                    {
                    case NORTH:
                    default:
                        VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX + 10, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType(), this.isMetropolis);
                        break;
                    case SOUTH:
                        VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX + 10, this.boundingBox.minY, this.boundingBox.maxZ - 5, EnumFacing.EAST, this.getComponentType(), this.isMetropolis);
                        break;
                    case WEST:
                        VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType(), this.isMetropolis);
                        break;
                    case EAST:
                        VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX - 20, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType(), this.isMetropolis);
                    }
                }
                if (flag && rand.nextInt(3) > 0 && enumfacing != null && isMetropolis)
                {
                    switch (enumfacing)
                    {
                    case NORTH:
                    default:
                        VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX - 10, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType(), this.isMetropolis);
                        break;
                    case SOUTH:
                        VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX - 10, this.boundingBox.minY, this.boundingBox.maxZ - 5, EnumFacing.EAST, this.getComponentType(), this.isMetropolis);
                        break;
                    case WEST:
                        VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 5, EnumFacing.SOUTH, this.getComponentType(), this.isMetropolis);
                        break;
                    case EAST:
                        VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX - 20, this.boundingBox.minY, this.boundingBox.maxZ + 5, EnumFacing.SOUTH, this.getComponentType(), this.isMetropolis);
                    }
                }
                if (flag && rand.nextInt(3) > 0 && enumfacing != null && isMetropolis)
                {
                    switch (enumfacing)
                    {
                    case NORTH:
                    default:
                        VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType(), this.isMetropolis);
                        break;
                    case SOUTH:
                        VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 25, EnumFacing.EAST, this.getComponentType(), this.isMetropolis);
                        break;
                    case WEST:
                        VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 25, EnumFacing.SOUTH, this.getComponentType(), this.isMetropolis);
                        break;
                    case EAST:
                        VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 25, EnumFacing.SOUTH, this.getComponentType(), this.isMetropolis);
                    }
                }
            }

            public static StructureBoundingBox findPieceBox(VillageStructures.Start start, List<StructureComponent> p_175848_1_, Random rand, int p_175848_3_, int p_175848_4_, int p_175848_5_, EnumFacing facing)
            {
                for (int i = 7 * MathHelper.getInt(rand, 3, 5); i >= 7; i -= 7)
                {
                    StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175848_3_, p_175848_4_, p_175848_5_, 0, 0, 0, 3, 3, i, facing);

                    if (StructureComponent.findIntersecting(p_175848_1_, structureboundingbox) == null)
                    {
                        return structureboundingbox;
                    }
                }

                return null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
            {
                IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.GRASS_PATH.getDefaultState());
                IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
                IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.GRAVEL.getDefaultState());
                IBlockState iblockstate3 = Blocks.COBBLESTONE.getDefaultState();

                for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i)
                {
                    for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j)
                    {
                        BlockPos blockpos = new BlockPos(i, 64, j);

                        if (structureBoundingBoxIn.isVecInside(blockpos))
                        {
                            blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos).down();

                            if (blockpos.getY() < worldIn.getSeaLevel())
                            {
                                blockpos = new BlockPos(blockpos.getX(), worldIn.getSeaLevel() - 1, blockpos.getZ());
                            }

                            while (blockpos.getY() >= worldIn.getSeaLevel() - 1)
                            {
                                IBlockState iblockstate4 = worldIn.getBlockState(blockpos);
                                if (!iblockstate4.getMaterial().isLiquid() && this.isMetropolis){
                                	worldIn.setBlockState(blockpos, iblockstate2, 2);
                                    worldIn.setBlockState(blockpos.down(), iblockstate3, 2);
                                    break;
                                }
                                if (iblockstate4.getBlock() == Blocks.GRASS || iblockstate4.getBlock() == Blocks.HARDENED_CLAY || iblockstate4.getBlock() == Blocks.STAINED_HARDENED_CLAY && worldIn.isAirBlock(blockpos.up()))
                                {
                                    worldIn.setBlockState(blockpos, iblockstate, 2);
                                    break;
                                }

                                if (iblockstate4.getMaterial().isLiquid())
                                {
                                    worldIn.setBlockState(blockpos, iblockstate1, 2);
                                    break;
                                }

                                if (iblockstate4.getBlock() == Blocks.SAND || iblockstate4.getBlock() == Blocks.SANDSTONE || iblockstate4.getBlock() == Blocks.RED_SANDSTONE)
                                {
                                    worldIn.setBlockState(blockpos, iblockstate2, 2);
                                    worldIn.setBlockState(blockpos.down(), iblockstate3, 2);
                                    break;
                                }

                                blockpos = blockpos.down();
                            }
                        }
                    }
                }

                return true;
            }
        }

    public static class PieceWeight
        {
            public Class <? extends VillageStructures.Village > villagePieceClass;
            public final int villagePieceWeight;
            public int villagePiecesSpawned;
            public int villagePiecesLimit;

            public PieceWeight(Class <? extends VillageStructures.Village > p_i2098_1_, int p_i2098_2_, int p_i2098_3_)
            {
                this.villagePieceClass = p_i2098_1_;
                this.villagePieceWeight = p_i2098_2_;
                this.villagePiecesLimit = p_i2098_3_;
            }

            public boolean canSpawnMoreVillagePiecesOfType(int componentType)
            {
                return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
            }

            public boolean canSpawnMoreVillagePieces()
            {
                return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
            }
        }

    public abstract static class Road extends VillageStructures.Village
        {
            public Road()
            {
            }

            protected Road(VillageStructures.Start start, int type)
            {
                super(start, type);
            }
        }

    public static class Start extends VillageStructures.Well
        {
            public BiomeProvider worldChunkMngr;
            /** World terrain type, 0 for normal, 1 for flap map */
            public int terrainType;
            public VillageStructures.PieceWeight structVillagePieceWeight;
            public List<VillageStructures.PieceWeight> structureVillageWeightedPieceList;
            public List<StructureComponent> pendingHouses = Lists.<StructureComponent>newArrayList();
            public List<StructureComponent> pendingRoads = Lists.<StructureComponent>newArrayList();
            public Biome biome;

            public Start()
            {
            }

            public Start(BiomeProvider chunkManagerIn, int p_i2104_2_, Random rand, int p_i2104_4_, int p_i2104_5_, List<VillageStructures.PieceWeight> p_i2104_6_, int p_i2104_7_, boolean isMetro)
            {
                super((VillageStructures.Start)null, 0, rand, p_i2104_4_, p_i2104_5_, isMetro);
                MapGenStructureIO.registerStructureComponent(Start.class, "Village");
                this.worldChunkMngr = chunkManagerIn;
                this.structureVillageWeightedPieceList = p_i2104_6_;
                this.terrainType = p_i2104_7_;
                Biome biome = chunkManagerIn.getBiome(new BlockPos(p_i2104_4_, 0, p_i2104_5_), Biomes.DEFAULT);
                this.biome = biome;
                this.startPiece = this;

                if (biome instanceof BiomeDesert)
                {
                    this.structureType = 1;
                }
                else if (biome instanceof BiomeSavanna)
                {
                    this.structureType = 2;
                }
                else if (biome instanceof BiomeTaiga)
                {
                    this.structureType = 3;
                }
                else if (biome instanceof BiomeMesa)
                {
                	this.structureType = 4;
                }
                else if (biome instanceof BiomeForest)
                {
                	this.structureType = 5;
                }
                this.setStructureType(this.structureType);
                this.isZombieInfested = rand.nextInt(50) == 0;
            }
        }

    public static class Torch extends VillageStructures.Village
        {
            public Torch()
            {
            }

            public Torch(VillageStructures.Start start, int p_i45568_2_, Random rand, StructureBoundingBox p_i45568_4_, EnumFacing facing)
            {
                super(start, p_i45568_2_);
                MapGenStructureIO.registerStructureComponent(Torch.class, "Village");
                this.setCoordBaseMode(facing);
                this.boundingBox = p_i45568_4_;
            }

            public static StructureBoundingBox findPieceBox(VillageStructures.Start start, List<StructureComponent> p_175856_1_, Random rand, int p_175856_3_, int p_175856_4_, int p_175856_5_, EnumFacing facing)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175856_3_, p_175856_4_, p_175856_5_, 0, 0, 0, 3, 4, 2, facing);
                return StructureComponent.findIntersecting(p_175856_1_, structureboundingbox) != null ? null : structureboundingbox;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
            {
                if (this.averageGroundLvl < 0)
                {
                    this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                    if (this.averageGroundLvl < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
                }

                IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 3, 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.setBlockState(worldIn, iblockstate, 1, 0, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 1, 1, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate, 1, 2, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.WOOL.getStateFromMeta(EnumDyeColor.WHITE.getDyeDamage()), 1, 3, 0, structureBoundingBoxIn);
                this.placeTorch(worldIn, EnumFacing.EAST, 2, 3, 0, structureBoundingBoxIn);
                this.placeTorch(worldIn, EnumFacing.NORTH, 1, 3, 1, structureBoundingBoxIn);
                this.placeTorch(worldIn, EnumFacing.WEST, 0, 3, 0, structureBoundingBoxIn);
                this.placeTorch(worldIn, EnumFacing.SOUTH, 1, 3, -1, structureBoundingBoxIn);
                return true;
            }
        }

    public abstract static class Village extends StructureComponent
        {
            protected int averageGroundLvl = -1;
            /** The number of villagers that have been spawned in this component. */
            private int villagersSpawned;
            protected int structureType;
            protected boolean isZombieInfested;
            protected VillageStructures.Start startPiece;
            public Random r = new Random();

            public Village()
            {
            }

            protected Village(VillageStructures.Start start, int type)
            {
                super(type);

                if (start != null)
                {
                    this.structureType = start.structureType;
                    this.isZombieInfested = start.isZombieInfested;
                    startPiece = start;
                }
            }

            /**
             * (abstract) Helper method to write subclass data to NBT
             */
            protected void writeStructureToNBT(NBTTagCompound tagCompound)
            {
                tagCompound.setInteger("HPos", this.averageGroundLvl);
                tagCompound.setInteger("VCount", this.villagersSpawned);
                tagCompound.setByte("Type", (byte)this.structureType);
                tagCompound.setBoolean("Zombie", this.isZombieInfested);
            }

            /**
             * (abstract) Helper method to read subclass data from NBT
             */
            protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
            {
                this.averageGroundLvl = tagCompound.getInteger("HPos");
                this.villagersSpawned = tagCompound.getInteger("VCount");
                this.structureType = tagCompound.getByte("Type");

                if (tagCompound.getBoolean("Desert"))
                {
                    this.structureType = 1;
                }

                this.isZombieInfested = tagCompound.getBoolean("Zombie");
            }

            /**
             * Gets the next village component, with the bounding box shifted -1 in the X and Z direction.
             */
            @Nullable
            protected StructureComponent getNextComponentNN(VillageStructures.Start start, List<StructureComponent> structureComponents, Random rand, int p_74891_4_, int p_74891_5_)
            {
                EnumFacing enumfacing = this.getCoordBaseMode();

                if (enumfacing != null)
                {
                    switch (enumfacing)
                    {
                        case NORTH:
                        default:
                            return VillageStructures.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, this.getComponentType());
                        case SOUTH:
                            return VillageStructures.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, this.getComponentType());
                        case WEST:
                            return VillageStructures.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                        case EAST:
                            return VillageStructures.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                    }
                }
                else
                {
                    return null;
                }
            }

            /**
             * Gets the next village component, with the bounding box shifted +1 in the X and Z direction.
             */
            @Nullable
            protected StructureComponent getNextComponentPP(VillageStructures.Start start, List<StructureComponent> structureComponents, Random rand, int p_74894_4_, int p_74894_5_)
            {
                EnumFacing enumfacing = this.getCoordBaseMode();

                if (enumfacing != null)
                {
                    switch (enumfacing)
                    {
                        case NORTH:
                        default:
                            return VillageStructures.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, this.getComponentType());
                        case SOUTH:
                            return VillageStructures.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, this.getComponentType());
                        case WEST:
                            return VillageStructures.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                        case EAST:
                            return VillageStructures.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                    }
                }
                else
                {
                    return null;
                }
            }

            /**
             * Discover the y coordinate that will serve as the ground level of the supplied BoundingBox. (A median of
             * all the levels in the BB's horizontal rectangle).
             */
            protected int getAverageGroundLevel(World worldIn, StructureBoundingBox structurebb)
            {
                int i = 0;
                int j = 0;
                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k)
                {
                    for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; ++l)
                    {
                        blockpos$mutableblockpos.setPos(l, 64, k);

                        if (structurebb.isVecInside(blockpos$mutableblockpos))
                        {
                            i += Math.max(worldIn.getTopSolidOrLiquidBlock(blockpos$mutableblockpos).getY(), worldIn.provider.getAverageGroundLevel() - 1);
                            ++j;
                        }
                    }
                }

                if (j == 0)
                {
                    return -1;
                }
                else
                {
                    return i / j;
                }
            }

            protected static boolean canVillageGoDeeper(StructureBoundingBox structurebb)
            {
                return structurebb != null && structurebb.minY > 10;
            }

            /**
             * Spawns a number of villagers in this component. Parameters: world, component bounding box, x offset, y
             * offset, z offset, number of villagers
             */
            protected void spawnFarmAnimals(World worldIn, StructureBoundingBox structurebb, int x, int y, int z, int count)
            {
            			int j = this.getXWithOffset(x, z);
                        int k = this.getYWithOffset(y);
                        int l = this.getZWithOffset(x, z);
                        EntityPig entitypig= new EntityPig(worldIn);
                        entitypig.setLocationAndAngles((double)j + 0.5D, (double)k, (double)l + 0.5D, 0.0F, 0.0F);
                        entitypig.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entitypig)), (IEntityLivingData)null);
                        entitypig.enablePersistence();
                        EntityCow entitycow= new EntityCow(worldIn);
                        entitycow.setLocationAndAngles((double)j + 0.5D, (double)k, (double)l + 0.5D, 0.0F, 0.0F);
                        entitycow.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entitycow)), (IEntityLivingData)null);
                        entitycow.enablePersistence();
                        if (r.nextInt(2) == 0){
                        	worldIn.spawnEntity(entitypig);
                        }
                        else{
                        	worldIn.spawnEntity(entitycow);
                        }
                   }
                   
			protected void spawnVillagers(World worldIn, StructureBoundingBox structurebb, int x, int y, int z, int count, boolean allow_children)
			{
			    if (this.villagersSpawned < count)
			    {
			        for (int i = this.villagersSpawned; i < count; ++i)
			        {
			            int j = this.getXWithOffset(x + i, z);
			            int k = this.getYWithOffset(y);
			            int l = this.getZWithOffset(x + i, z);
			
			            if (!structurebb.isVecInside(new BlockPos(j, k, l)))
			            {
			                break;
			            }
			
			            ++this.villagersSpawned;
			
			            if (this.isZombieInfested)
			            {
			                EntityZombieVillager entityzombievillager = new EntityZombieVillager(worldIn);
			                entityzombievillager.setLocationAndAngles((double)j + 0.5D, (double)k, (double)l + 0.5D, 0.0F, 0.0F);
			                entityzombievillager.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityzombievillager)), (IEntityLivingData)null);
			                entityzombievillager.enablePersistence();
			                worldIn.spawnEntity(entityzombievillager);
			            }
			            else
			            {
			                EntityVillager entityvillager = new EntityVillager(worldIn);
			                entityvillager.setLocationAndAngles((double)j + 0.5D, (double)k, (double)l + 0.5D, 0.0F, 0.0F);
			                entityvillager.setProfession(this.chooseForgeProfession(i, entityvillager.getProfessionForge()));
			                entityvillager.finalizeMobSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityvillager)), (IEntityLivingData)null, false);
			                if (allow_children){
			                	if (r.nextInt(3) == 0){
			                		entityvillager.setGrowingAge(-23000);
			                	}
			                }
			                worldIn.spawnEntity(entityvillager);
			            }
			        }
			    }
			}

            @Deprecated // Use Forge version below.
            protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
            {
                return currentVillagerProfession;
            }
            protected net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession chooseForgeProfession(int count, net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession prof)
            {
                return net.minecraftforge.fml.common.registry.VillagerRegistry.getById(chooseProfession(count, net.minecraftforge.fml.common.registry.VillagerRegistry.getId(prof)));
            }

            protected IBlockState getBiomeSpecificBlockState(IBlockState blockstateIn)
            {
                net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID event = new net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID(startPiece == null ? null : startPiece.biome, blockstateIn);
                net.minecraftforge.common.MinecraftForge.TERRAIN_GEN_BUS.post(event);
                if (event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) return event.getReplacement();
                if (this.structureType == 1)
                {
                    if (blockstateIn.getBlock() == Blocks.LOG || blockstateIn.getBlock() == Blocks.LOG2)
                    {
                        return Blocks.SANDSTONE.getDefaultState();
                    }

                    if (blockstateIn.getBlock() == Blocks.COBBLESTONE)
                    {
                        return Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.getMetadata());
                    }

                    if (blockstateIn.getBlock() == Blocks.PLANKS)
                    {
                        return Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata());
                    }
                    if (blockstateIn.getBlock() == Blocks.STONE_SLAB)
                    {
                    	return Blocks.STONE_SLAB2.getDefaultState();
                    }
                    if (blockstateIn.getBlock() == Blocks.OAK_STAIRS)
                    {
                        return Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, blockstateIn.getValue(BlockStairs.FACING));
                    }

                    if (blockstateIn.getBlock() == Blocks.STONE_STAIRS)
                    {
                        return Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, blockstateIn.getValue(BlockStairs.FACING));
                    }

                    if (blockstateIn.getBlock() == Blocks.GRAVEL)
                    {
                        return Blocks.SANDSTONE.getDefaultState();
                    }
                }
                else if (this.structureType == 4)
                {
                    if (blockstateIn.getBlock() == Blocks.PLANKS)
                    {
                        return Blocks.HARDENED_CLAY.getDefaultState();
                    }
                    if (blockstateIn.getBlock() == Blocks.DIRT)
                    {
                        return Blocks.DIRT.getStateFromMeta(1);
                    }
                    if (blockstateIn.getBlock() == Blocks.COBBLESTONE)
                    {
                        return Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(3);
                    }
                    if (blockstateIn.getBlock() == Blocks.STONE_STAIRS)
                    {
                        return IvBlocks.light_blue_stairs.getDefaultState().withProperty(BlockStairs.FACING, blockstateIn.getValue(BlockStairs.FACING));
                    }

                }
                else if (this.structureType == 3)
                {
                    if (blockstateIn.getBlock() == Blocks.LOG || blockstateIn.getBlock() == Blocks.LOG2)
                    {
                        return Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLog.LOG_AXIS, blockstateIn.getValue(BlockLog.LOG_AXIS));
                    }

                    if (blockstateIn.getBlock() == Blocks.PLANKS)
                    {
                        return Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE);
                    }

                    if (blockstateIn.getBlock() == Blocks.OAK_STAIRS)
                    {
                        return Blocks.SPRUCE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, blockstateIn.getValue(BlockStairs.FACING));
                    }

                    if (blockstateIn.getBlock() == Blocks.OAK_FENCE)
                    {
                        return Blocks.SPRUCE_FENCE.getDefaultState();
                    }
                }
                else if (this.structureType == 2)
                {
                    if (blockstateIn.getBlock() == Blocks.LOG || blockstateIn.getBlock() == Blocks.LOG2)
                    {
                        return Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(BlockLog.LOG_AXIS, blockstateIn.getValue(BlockLog.LOG_AXIS));
                    }

                    if (blockstateIn.getBlock() == Blocks.PLANKS)
                    {
                        return Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA);
                    }

                    if (blockstateIn.getBlock() == Blocks.OAK_STAIRS)
                    {
                        return Blocks.ACACIA_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, blockstateIn.getValue(BlockStairs.FACING));
                    }

                    if (blockstateIn.getBlock() == Blocks.COBBLESTONE)
                    {
                        return Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
                    }

                    if (blockstateIn.getBlock() == Blocks.OAK_FENCE)
                    {
                        return Blocks.ACACIA_FENCE.getDefaultState();
                    }
                }
                else if (this.structureType == 5)
                {
                	if (blockstateIn.getBlock() == Blocks.STONE_STAIRS)
                    {
                        return Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, blockstateIn.getValue(BlockStairs.FACING));
                    }
                	 //if (blockstateIn.getBlock() == Blocks.COBBLESTONE)
                     //{
                       //  return Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLog.LOG_AXIS, blockstateIn.getValue(BlockLog.LOG_AXIS));
                     //}
                }

                return blockstateIn;
            }

            protected BlockDoor biomeDoor()
            {
                switch (this.structureType)
                {
                    case 2:
                        return Blocks.ACACIA_DOOR;
                    case 3:
                        return Blocks.SPRUCE_DOOR;
                    default:
                        return Blocks.OAK_DOOR;
                }
            }

            protected void createVillageDoor(World p_189927_1_, StructureBoundingBox p_189927_2_, Random p_189927_3_, int p_189927_4_, int p_189927_5_, int p_189927_6_, EnumFacing p_189927_7_)
            {
                if (!this.isZombieInfested)
                {
                    this.generateDoor(p_189927_1_, p_189927_2_, p_189927_3_, p_189927_4_, p_189927_5_, p_189927_6_, EnumFacing.NORTH, this.biomeDoor());
                }
            }

            protected void placeTorch(World p_189926_1_, EnumFacing p_189926_2_, int p_189926_3_, int p_189926_4_, int p_189926_5_, StructureBoundingBox p_189926_6_)
            {
                if (!this.isZombieInfested)
                {
                    this.setBlockState(p_189926_1_, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, p_189926_2_), p_189926_3_, p_189926_4_, p_189926_5_, p_189926_6_);
                }
            }

            /**
             * Replaces air and liquid from given position downwards. Stops when hitting anything else than air or
             * liquid
             */
            protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn)
            {
                IBlockState iblockstate = this.getBiomeSpecificBlockState(blockstateIn);
                super.replaceAirAndLiquidDownwards(worldIn, iblockstate, x, y, z, boundingboxIn);
            }

            protected void setStructureType(int p_189924_1_)
            {
                this.structureType = p_189924_1_;
            }
        }

    public static class Well extends VillageStructures.Village
        {
            private boolean isMetro;

			public Well()
            {
            }

            public Well(VillageStructures.Start start, int type, Random rand, int x, int z, boolean isMetro)
            {
                super(start, type);
                MapGenStructureIO.registerStructureComponent(Well.class, "Village");
                this.isMetro = isMetro;
                this.setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));

                if (this.getCoordBaseMode().getAxis() == EnumFacing.Axis.Z)
                {
                    this.boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
                }
                else
                {
                    this.boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
                }
            }

            /**
             * Initiates construction of the Structure Component picked, at the current Location of StructGen
             */
            public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
            {
                VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.WEST, this.getComponentType(), this.isMetro);
                VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.EAST, this.getComponentType(), this.isMetro);
                VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType(), this.isMetro);
                VillageStructures.generateAndAddRoadPiece((VillageStructures.Start)componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType(), this.isMetro);
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
            {
                if (this.averageGroundLvl < 0)
                {
                    this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                    if (this.averageGroundLvl < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 3, 0);
                }

                IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
                IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.STONE_SLAB.getDefaultState());
                IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 4, 12, 4, iblockstate, Blocks.FLOWING_WATER.getDefaultState(), false);
                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 12, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 3, 12, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 12, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 3, 12, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 1, 13, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 1, 14, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 4, 13, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 4, 14, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 1, 13, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 1, 14, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 4, 13, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate1, 4, 14, 4, structureBoundingBoxIn);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 15, 1, 4, 15, 4, iblockstate, iblockstate, false);
                this.setBlockState(worldIn, iblockstate2, 1, 12, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2, 1, 12, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2, 4, 12, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2, 4, 12, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2, 2, 12, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2, 3, 12, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2, 2, 12, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate2, 3, 12, 4, structureBoundingBoxIn);

                

                for (int i = 0; i <= 5; ++i)
                {
                    for (int j = 0; j <= 5; ++j)
                    {
                        if (j == 0 || j == 5 || i == 0 || i == 5)
                        {
                            this.setBlockState(worldIn, iblockstate, j, 11, i, structureBoundingBoxIn);
                            this.clearCurrentPositionBlocksUpwards(worldIn, j, 12, i, structureBoundingBoxIn);
                        }
                    }
                }

                return true;
            }
        }

    public static class WoodHut extends VillageStructures.Village
        {
            private boolean isTallHouse;
            private int tablePosition;

            public WoodHut()
            {
            }

            public WoodHut(VillageStructures.Start start, int type, Random rand, StructureBoundingBox structurebb, EnumFacing facing)
            {
                super(start, type);
                MapGenStructureIO.registerStructureComponent(WoodHut.class, "Village");
                this.setCoordBaseMode(facing);
                this.boundingBox = structurebb;
                this.isTallHouse = rand.nextBoolean();
                this.tablePosition = rand.nextInt(3);
            }

            /**
             * (abstract) Helper method to write subclass data to NBT
             */
            protected void writeStructureToNBT(NBTTagCompound tagCompound)
            {
                super.writeStructureToNBT(tagCompound);
                tagCompound.setInteger("T", this.tablePosition);
                tagCompound.setBoolean("C", this.isTallHouse);
            }

            /**
             * (abstract) Helper method to read subclass data from NBT
             */
            protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
            {
                super.readStructureFromNBT(tagCompound, p_143011_2_);
                this.tablePosition = tagCompound.getInteger("T");
                this.isTallHouse = tagCompound.getBoolean("C");
            }

            public static VillageStructures.WoodHut createPiece(VillageStructures.Start start, List<StructureComponent> p_175853_1_, Random rand, int p_175853_3_, int p_175853_4_, int p_175853_5_, EnumFacing facing, int p_175853_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175853_3_, p_175853_4_, p_175853_5_, 0, 0, 0, 4, 6, 5, facing);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175853_1_, structureboundingbox) == null ? new VillageStructures.WoodHut(start, p_175853_7_, rand, structureboundingbox, facing) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
            {
                if (this.averageGroundLvl < 0)
                {
                    this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

                    if (this.averageGroundLvl < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
                }

                IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
                IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
                IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
                IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
                IBlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
                IBlockState iblockstate5 = this.getBiomeSpecificBlockState(Blocks.DIRT.getDefaultState());
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 3, 0, 4, iblockstate, iblockstate, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 3, iblockstate5, iblockstate5, false);

                if (this.isTallHouse)
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 2, 4, 3, iblockstate3, iblockstate3, false);
                }
                else
                {
                    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 2, 5, 3, iblockstate3, iblockstate3, false);
                }

                this.setBlockState(worldIn, iblockstate3, 1, 4, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 2, 4, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 1, 4, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 2, 4, 4, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 0, 4, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 0, 4, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 0, 4, 3, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 3, 4, 1, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 3, 4, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, iblockstate3, 3, 4, 3, structureBoundingBoxIn);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 3, 0, iblockstate3, iblockstate3, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 3, 0, iblockstate3, iblockstate3, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 4, iblockstate3, iblockstate3, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 4, 3, 3, 4, iblockstate3, iblockstate3, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, iblockstate1, iblockstate1, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 1, 3, 3, 3, iblockstate1, iblockstate1, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, iblockstate1, iblockstate1, false);
                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 3, 4, iblockstate1, iblockstate1, false);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 2, structureBoundingBoxIn);
                this.placeTorch(worldIn, EnumFacing.SOUTH, 1, 3, 3, structureBoundingBoxIn);

                if (this.tablePosition > 0)
                {
                    this.setBlockState(worldIn, iblockstate4, this.tablePosition, 1, 3, structureBoundingBoxIn);
                    this.setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), this.tablePosition, 2, 3, structureBoundingBoxIn);
                }

                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
                this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
                this.createVillageDoor(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.NORTH);

                if (this.getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
                {
                    this.setBlockState(worldIn, iblockstate2, 1, 0, -1, structureBoundingBoxIn);

                    if (this.getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
                    {
                        this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 1, -1, -1, structureBoundingBoxIn);
                    }
                }

                for (int i = 0; i < 5; ++i)
                {
                    for (int j = 0; j < 4; ++j)
                    {
                        this.clearCurrentPositionBlocksUpwards(worldIn, j, 6, i, structureBoundingBoxIn);
                        this.replaceAirAndLiquidDownwards(worldIn, iblockstate, j, -1, i, structureBoundingBoxIn);
                    }
                }

                this.spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1, true);
                return true;
     
            }
            protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
            {
                return 0;
            }
        }
}