package orangeVillager61.ImprovedVillagers.Entities;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerInteract;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import orangeVillager61.ImprovedVillagers.Entities.AI.IvVilsPerDoor;
import orangeVillager61.ImprovedVillagers.Entities.AI.VillagerAvoidEvilPlayer;
import orangeVillager61.ImprovedVillagers.Entities.AI.VilsPerDoor;
import orangeVillager61.ImprovedVillagers.Items.IvItems;

public class IvVillager extends EntityVillager{
	
	protected Village villageObj; 
	public String name;
	public int gender;
    protected boolean isWillingToMate;
    protected int wealth;
    //public String Adult_Age;
    //protected int int_Age;
    protected MerchantRecipeList buyingList;
    private static final DataParameter<String> Adult_Age = EntityDataManager.<String>createKey(IvVillager.class, DataSerializers.STRING);
    private static final DataParameter<Integer> int_Age = EntityDataManager.<Integer>createKey(IvVillager.class, DataSerializers.VARINT);
    private int careerId;
    private int careerLevel;
    private boolean isLookingForHome;
    private boolean areAdditionalTasksSet;
    protected final InventoryBasic villagerInventory;
    Random r = new Random();
    /** A multi-dimensional array mapping the various professions, careers and career levels that a Villager may offer */
	public String[] male_list = {"Bob", "Joseph", "Aaron", "Philp", "Adam", "Paul", "Donald", "Ryan", 
									"Mark", "Brian", "Robert", "Willam", "Harold", "Anthony", "Julius", 
									"Mathew", "Tyler", "Noah", "Patrick", "Caden", "Michael", "Jeffery",
									"James", "John", "Thomas", "Otto", "Bill", "Sheldon", "Leonard", 
									"Howard", "Carter", "Theodore", "Herbert", "Paul", "Kurt", "Blaine",
									"Ronald", "Christian", "Frederick", "Justinian", "Justin"};
	public String[] female_list = {"Karen", "Lessie", "Kayla", "Brianna", "Isabella", "Elizabeth",
									  "Kira", "Jadzia", "Abigail", "Chloe", "Olivia", "Sophia", "Emily", 
									  "Charlotte", "Amelia", "Maria", "Daria", "Sarah", "Theodora",
									  "Tia", "Jennifer", "Anglica", "Denna", "Tasha", "Catherine", "Lily",
									  "Amy", "Penny", "Julina", "Audrey", "Avery", "Hoshi", "Leia", "Rachel",
									  "Tina", "Lacy", "Quinn", "Alexandra"};
	
	public IvVillager(World world) {
		super(world);
        this.villagerInventory = new InventoryBasic("Items", false, 20);
        this.setVillagerAge();
	}
	public IvVillager(World world, int professionId, int gender, String name) {
		super(world, professionId);
	    this.setProfession(professionId);
        this.villagerInventory = new InventoryBasic("Items", false, 20);
        this.gender = gender;
        this.name = name;
        this.setCustomNameTag(name);
        this.setVillagerAge();
	}
	public InventoryBasic getVillagerInventory()
    {
        return this.villagerInventory;
    }
	@Override
	protected void entityInit()
    {
		super.entityInit();
		this.getDataManager().register(int_Age, Integer.valueOf(1));
		this.getDataManager().register(Adult_Age, String.valueOf(""));
    }
	protected void setAdultAge(String name)
    {
        this.dataManager.set(Adult_Age, name);
    }
	protected void setIntAge(int num)
    {
        this.dataManager.set(int_Age, num);
    }
	public int getIntAge()
    {
          return (int)this.dataManager.get(int_Age);
    }
    public String getAdultAge()
    {
        return (String)this.dataManager.get(Adult_Age);
    }
    protected void setVillagerAge(){
		if (this.isChild() == false)
        {
        	this.setIntAge(1);
        	if (r.nextInt(6) == 0){
        		this.setAdultAge("Elder");
        	}
        	else if (r.nextInt(2) == 0){
        		this.setAdultAge("Young Adult");
        	}
        	else{
        		this.setAdultAge("Middle Aged");
        	}
        }
        else{
        	this.setAdultAge("Child");
        }
	}
	public void ivVillagerAdultAge(int lifeChangeNum){
		if (world.isRemote == false){
        	if (this.isChild() == false){
        		if (this.getIntAge() >= lifeChangeNum){
        			if (this.getAdultAge().equals("Young Adult")){
        	        	this.setIntAge(1);
        				this.setAdultAge("Middle Aged");
        			}
        			else if (this.getAdultAge().equals("Middle Aged")){
        	        	this.setIntAge(1);
        				this.setAdultAge("Elder");
        			}
        			else if (this.getAdultAge().equals("Elder")){
        				this.setIntAge(this.getIntAge() - 500);
        			}
        		}
        		else{
        				this.setIntAge(this.getIntAge() + 1);
        			}
        		if(this.getAdultAge().equals("Elder"))
        		{
        			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        		}
        			
        	}
        }
	}
	@Override
	public void onLivingUpdate()
	    {
	        super.onLivingUpdate();
	        this.ivVillagerAdultAge(360000);
	    }
	@Override
	protected void onGrowingAdult()
    {
        super.onGrowingAdult();
        this.setAdultAge("Young Adult");
    	this.setIntAge(1);
    }
	@Override
	protected void initEntityAI()
    {
		if (world.isRemote == false){
			BlockPos blockpos = new BlockPos(this);
			this.villageObj = this.world.getVillageCollection().getNearestVillage(blockpos, 32);

		}
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        //this.tasks.addTask(1, new VillagerAvoidEvilPlayer(this, 8.0F, 0.6D, 0.6D, this.villageObj));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityEvoker.class, 12.0F, 0.8D, 0.8D));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityVindicator.class, 8.0F, 0.8D, 0.8D));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityVex.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(1, new EntityAIPanic(this, 0.8D));
        this.tasks.addTask(1, new EntityAITradePlayer(this));
        this.tasks.addTask(2, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(3, new EntityAITempt(this, 0.9D, Items.EMERALD, false));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(5, new IvVilsPerDoor(this));
        this.tasks.addTask(7, new EntityAIFollowGolem(this));
        this.tasks.addTask(9, new EntityAIVillagerInteract(this));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    }
	private void setAdditionalAItasks()
    {
        if (!this.areAdditionalTasksSet)
        {
            this.areAdditionalTasksSet = true;

            if (this.isChild())
            {
                this.tasks.addTask(8, new EntityAIPlay(this, 0.32D));
            }
            else if (this.getProfession() == 0)
            {
                this.tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6D));
            }
        }
    }
	 @Override
	 public void writeEntityToNBT(NBTTagCompound compound)
	    {
	        super.writeEntityToNBT(compound);
	        if (world.isRemote == false){
		        compound.setInteger("Profession", this.getProfession());
		        compound.setString("ProfessionName", this.getProfessionForge().getRegistryName().toString());
		        compound.setInteger("Riches", this.wealth);
		        compound.setInteger("Int_Age", this.getIntAge());
		        compound.setInteger("Career", this.careerId);
		        compound.setInteger("CareerLevel", this.careerLevel);
		        compound.setBoolean("Willing", this.isWillingToMate);
		        if ((this.getAdultAge().equals("")) == false){
			        compound.setString("Adult_Age", this.getAdultAge());
		        }
		        if (this.buyingList != null)
		        {
		            compound.setTag("Offers", this.buyingList.getRecipiesAsTags());
		        }
	        }
	        NBTTagList nbttaglist = new NBTTagList();

	        for (int i = 0; i < this.getVillagerInventory().getSizeInventory(); ++i)
	        {
	            ItemStack itemstack = this.villagerInventory.getStackInSlot(i);

	            if (!itemstack.isEmpty())
	            {
	                nbttaglist.appendTag(itemstack.writeToNBT(new NBTTagCompound()));
	            }
	        }

	        compound.setTag("Inventory", nbttaglist);
	        if (world.isRemote == false){
	        	if (this.gender != 1 || this.gender != 2){
	        		this.gender = r.nextInt(2) + 1;
	        	}
	        	compound.setInteger("Gender", this.gender);
	        	if (this.getCustomNameTag() == null || this.getCustomNameTag() == "None" || this.getCustomNameTag() == "none"){
	        		if (this.gender == 1){
	        			this.name = male_list[r.nextInt(male_list.length)];
	        		}
	        		else if (this.gender == 2){
	        			this.name = female_list[r.nextInt(female_list.length)]; 
	        		}
	        		else{
	        			this.name = "None";
	        			System.out.println("Something went wrong with gender, please report.");
	        			System.out.println("No Name");
	        		}
		        	compound.setString("Name", this.name);
		        	this.setCustomNameTag(this.name);
	        	}
	        }
	        
	    }
	 @Override
	 public void readEntityFromNBT(NBTTagCompound compound){
		 super.readEntityFromNBT(compound);
		 if (world.isRemote == false){
			 this.gender = compound.getInteger("Gender");
			 this.name = compound.getString("Name");
			 //this.setCustomNameTag(this.name);
		 }
		 if (compound.hasKey("Adult_Age"))
         {
             this.setAdultAge(compound.getString("Adult_Age"));
         }
		 if (compound.hasKey("Int_Age")){
			 this.setIntAge(compound.getInteger("Int_Age"));
		 }
		 this.setProfession(compound.getInteger("Profession"));
	        if (compound.hasKey("ProfessionName"))
	        {
	            net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession p =
	                net.minecraftforge.fml.common.registry.VillagerRegistry.instance().getRegistry().getValue(new net.minecraft.util.ResourceLocation(compound.getString("ProfessionName")));
	            if (p == null)
	                p = net.minecraftforge.fml.common.registry.VillagerRegistry.instance().getRegistry().getValue(new net.minecraft.util.ResourceLocation("minecraft:farmer"));
	            this.setProfession(p);
	        }
	        this.wealth = compound.getInteger("Riches");
	        this.careerId = compound.getInteger("Career");
	        this.careerLevel = compound.getInteger("CareerLevel");
	        this.isWillingToMate = compound.getBoolean("Willing");

	        if (compound.hasKey("Offers", 10))
	        {
	            NBTTagCompound nbttagcompound = compound.getCompoundTag("Offers");
	            this.buyingList = new MerchantRecipeList(nbttagcompound);
	        }

	        NBTTagList nbttaglist = compound.getTagList("Inventory", 10);

	        for (int i = 0; i < nbttaglist.tagCount(); ++i)
	        {
	            ItemStack itemstack = new ItemStack(nbttaglist.getCompoundTagAt(i));

	            if (!itemstack.isEmpty())
	            {
	                this.villagerInventory.addItem(itemstack);
	            }
	        }

	        this.setCanPickUpLoot(true);
	        this.setAdditionalAItasks();
		 
	 }
	 private void populateBuyingList()
	    {
	        if (this.careerId != 0 && this.careerLevel != 0)
	        {
	            ++this.careerLevel;
	        }
	        else
	        {
	            this.careerId = this.getProfessionForge().getRandomCareer(this.rand) + 1;
	            this.careerLevel = 1;
	        }

	        if (this.buyingList == null)
	        {
	            this.buyingList = new MerchantRecipeList();
	        }

	        int i = this.careerId - 1;
	        int j = this.careerLevel - 1;
	        java.util.List<EntityVillager.ITradeList> trades = this.getProfessionForge().getCareer(i).getTrades(j);

	        if (trades != null)
	        {
	            for (EntityVillager.ITradeList entityvillager$itradelist : trades)
	            {
	                entityvillager$itradelist.addMerchantRecipe(this, this.buyingList, this.rand);
	            }
	        }
	    }
	 @Override
	 public boolean processInteract(EntityPlayer player, EnumHand hand){
		if (world.isRemote == false){
			BlockPos blockpos = new BlockPos(this);
			this.villageObj = this.world.getVillageCollection().getNearestVillage(blockpos, 32);

		}
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == IvItems.thieving_nose && !this.isChild()){
        	itemstack.damageItem(1, player);
        	this.setHealth(this.getHealth() - 2);
        	this.playHurtSound(getLastDamageSource());
        	if (rand.nextInt(10) + 1 < 6){
        		
        	}
        	if (rand.nextInt(10) + 1 < 9 && rand.nextInt(10) + 1  > 5){
        		this.entityDropItem(new ItemStack(Items.EMERALD, r.nextInt(2) + 1), 0);
        	}
        	if (rand.nextInt(10) + 1 < 10 && rand.nextInt(10) + 1  > 8){
        		this.entityDropItem(new ItemStack(Items.EMERALD, r.nextInt(2) + 1), 0);
        	}
        	if (world.isRemote == false){
        	if (this.villageObj != null)
	            {
	        		this.villageObj.modifyPlayerReputation(player.getName(), -2);
	            }
        	}
            return true;
        }
        else if (!this.holdingSpawnEggOfClass(itemstack, this.getClass()) && this.isEntityAlive() && !this.isTrading() && !this.isChild())
        {
            if (this.buyingList == null)
            {
                this.populateBuyingList();
            }

            if (hand == EnumHand.MAIN_HAND)
            {
                player.addStat(StatList.TALKED_TO_VILLAGER);
            }

            if (!this.world.isRemote && !this.buyingList.isEmpty())
            {
                this.setCustomer(player);
                player.displayVillagerTradeGui(this);
            }
            else if (this.buyingList.isEmpty())
            {
                return super.processInteract(player, hand);
            }

            return true;
        }
        else
        {
            return super.processInteract(player, hand);
        }
    }

}
