package orangeVillager61.ImprovedVillagers.Entities;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAIFollowOwner;
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
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import orangeVillager61.ImprovedVillagers.Config;
import orangeVillager61.ImprovedVillagers.Iv;
import orangeVillager61.ImprovedVillagers.Reference;
import orangeVillager61.ImprovedVillagers.Entities.AI.IvVilsPerDoor;
import orangeVillager61.ImprovedVillagers.Entities.AI.VillagerAIFollowParent;
import orangeVillager61.ImprovedVillagers.Entities.AI.VillagerAvoidEvilPlayer;
import orangeVillager61.ImprovedVillagers.Entities.AI.VillagerFollowEmerald;
import orangeVillager61.ImprovedVillagers.Entities.AI.VillagerFollowOwner;
import orangeVillager61.ImprovedVillagers.Entities.AI.VilsPerDoor;
import orangeVillager61.ImprovedVillagers.Items.IvItems;
import orangeVillager61.ImprovedVillagers.client.gui.GuiHandler;

public class IvVillager extends EntityVillager{
	
	//TODO Convert the rest of this Villager from vanilla ints to forge profession
	@ObjectHolder("minecraft:farmer")
	public static VillagerProfession PROFESSION_FARMER = null;

	@ObjectHolder("minecraft:nitwit")
	public static VillagerProfession PROFESSION_NITWIT = null;
	
	@ObjectHolder("minecraft:priest")
	public static VillagerProfession PROFESSION_PRIEST = null;
	
	protected Village villageObj; 
	public String name;
	//public int gender;
    protected boolean isWillingToMate;
    private ItemStackHandler item_handler = new ItemStackHandler(15);
    protected int wealth;
    public int robbed_time;
    protected MerchantRecipeList buyingList;
    protected static final DataParameter<Optional<UUID>> OWNER_DEFINED_ID = EntityDataManager.<Optional<UUID>>createKey(IvVillager.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    //TODO Turn Adult Age into an Enum
    private static final DataParameter<String> Adult_Age = EntityDataManager.<String>createKey(IvVillager.class, DataSerializers.STRING);
    private static final DataParameter<String> Tab = EntityDataManager.<String>createKey(IvVillager.class, DataSerializers.STRING);
    private static final DataParameter<Integer> int_Age = EntityDataManager.<Integer>createKey(IvVillager.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> Is_Hired = EntityDataManager.<Boolean>createKey(IvVillager.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> has_init = EntityDataManager.<Boolean>createKey(IvVillager.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> Hire_Cost = EntityDataManager.<Integer>createKey(IvVillager.class, DataSerializers.VARINT);
    private static final DataParameter<Float> True_Health = EntityDataManager.<Float>createKey(IvVillager.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean> Following = EntityDataManager.<Boolean>createKey(IvVillager.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> Gender = EntityDataManager.<Integer>createKey(IvVillager.class, DataSerializers.VARINT);
    private static final DataParameter<Optional<UUID>> Mother_ID = EntityDataManager.<Optional<UUID>>createKey(IvVillager.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private static final DataParameter<Optional<UUID>> Father_ID = EntityDataManager.<Optional<UUID>>createKey(IvVillager.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    private int careerId;
    private int careerLevel;
    private boolean isLookingForHome;
    private boolean areAdditionalTasksSet;
    protected final InventoryBasic villagerInventory;
    private static final DataParameter<String> mother_name = EntityDataManager.<String>createKey(IvVillager.class, DataSerializers.STRING);
    private static final DataParameter<String> father_name = EntityDataManager.<String>createKey(IvVillager.class, DataSerializers.STRING);
    static Random r = new Random();
    protected ArrayList<UUID> note_list = new ArrayList<UUID>(0);
	public static final String[] en_male_list = {"Bob", "Joseph", "Aaron", "Philp", "Adam", "Paul", "Donald", "Ryan", 
									"Mark", "Brian", "Robert", "Willam", "Harold", "Anthony", "Julius", 
									"Mathew", "Tyler", "Noah", "Patrick", "Caden", "Michael", "Jeffery",
									"James", "John", "Thomas", "Otto", "Bill", "Sheldon", "Leonard", 
									"Howard", "Carter", "Theodore", "Herbert", "Paul", "Kurt", "Blaine",
									"Ronald", "Christian", "Frederick", "Justinian", "Justin", "Gabriel", "Chris",
									"Richard", "Gregory", "Ivan", "Franklin", "Charles", "George", "Duane", 
									"Alexander", "Kevin", "Harry", "Frank", "Homer", "Philip", "Benjamin", "Ben",
									"Christopher", "Daniel"};
	public static final String[] en_female_list = {"Karen", "Lessie", "Kayla", "Brianna", "Isabella", "Elizabeth",
									  "Kira", "Jadzia", "Abigail", "Chloe", "Olivia", "Sophia", "Emily", 
									  "Charlotte", "Amelia", "Maria", "Daria", "Sarah", "Theodora",
									  "Tia", "Jennifer", "Anglica", "Denna", "Tasha", "Catherine", "Lily",
									  "Amy", "Penny", "Julina", "Audrey", "Avery", "Hoshi", "Leia", "Rachel",
									  "Tina", "Lacy", "Quinn", "Alexandra", "Daisy", "Mary", "Anna", "Edith",
									  "Sybil", "Cora", "Maria", "Holly", "Eleanor", "Alex", "Santana", "Jane",
									  "Angela", "Rose", "Emerald", "Victoria", "Andrea", "Emma", "Samantha",
									  "Nicole"};
	public static final String[] es_male_list = {"Juan", "Santiago", "José", "Gabriel", "Sebastián", "Samuel", "Angelo",
										"Nicolás", "Tomás", "Diego", "Leonardo", "Matías", "Alejandro", "Sebastián",
										"Mateo", "Felipe", "Benjamín", "Valentino", "Damián", "Santino", "Vicente",
										"Máximo", "Jorge", "Christopher"};
	
	public static final String[] es_female_list = {"Sofía", "Isabella", "Maria", "Maria José", "Santana", "Valeria", 
									"Ana", "Violeta", "Ana Sofía", "Juliana", "Mara", "Julia", "Jimena", "Sara",
									"Victoria", "Andrea", "Natalia", "Emma", "Samantha", "Daniela", "Valeria",
									"Lucia", "Mia", "Ariana", "Fernanda", "Nicole"};

	private boolean areAdditionalTasksSet2;
	
	public IvVillager(World world) {
		super(world);
        this.villagerInventory = new InventoryBasic("Items", false, 20);
        this.setVillagerAge();
	    this.setHireCost(r.nextInt(21) + 20);
	    this.setGender(r.nextInt(2) + 1);
	    this.name = this.random_name(this.getGender());
	    this.setCustomNameTag(this.name);
	    this.robbed_time = 0;
	}
	public IvVillager(World world, int professionId, int gender, String name) {
		super(world, professionId);
	    this.setProfession(professionId);
        this.villagerInventory = new InventoryBasic("Items", false, 20);
        this.setGender(gender);
        this.name = name;
        this.setCustomNameTag(name);
        this.setVillagerAge();
	    this.setHireCost(r.nextInt(21) + 20);
	    this.robbed_time = 0;
	}
	public IvVillager(World world, int professionId, int gender, String name, UUID father_id, UUID mother_id) {
		super(world, professionId);
	    this.setProfession(professionId);
        this.villagerInventory = new InventoryBasic("Items", false, 20);
        this.setGender(gender);
        this.name = name;
        this.setCustomNameTag(name);
        this.setVillagerAge();
	    this.setHireCost(r.nextInt(21) + 20);
	    this.setFatherId(father_id);
	    this.setMotherId(mother_id);
		this.setFatherName(this.world.getMinecraftServer().getEntityFromUuid(father_id).getCustomNameTag());
		this.setMotherName(this.world.getMinecraftServer().getEntityFromUuid(mother_id).getCustomNameTag());
	    this.robbed_time = 0;
	}
	public InventoryBasic getVillagerInventory()
    {
        return this.villagerInventory;
    }
	public ItemStackHandler getItemHandler()
	{
		return this.item_handler;
	}
	@Override
	public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, @Nullable net.minecraft.util.EnumFacing facing)	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}
		else
		{
			return super.hasCapability(capability, facing);
		}
	}
    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.item_handler);
		}
		else
		{
			return super.getCapability(capability, facing);
		}
    }
	@Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
    }
	
	private void setMoreVillagerNbtStuff()
    {
        if (!this.areAdditionalTasksSet2)
        {
            this.areAdditionalTasksSet2 = true;
            if (this.isChild())
            {
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
                this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
                if (this.getTrueHealth() > 0){
                    this.setHealth(this.getTrueHealth());
                }
            }
            else if (this.getAdultAge().equals("Elder"))
            {
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
                this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
                this.setSize(0.6F, 1.8F);
                if (this.getTrueHealth() > 0){
                    this.setHealth(this.getTrueHealth());
                }
            }
            else if (this.getAdultAge().equals("Middle Aged"))
            {
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
                this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.53D);
                if (this.getTrueHealth() > 0){
                    this.setHealth(this.getTrueHealth());
                }
            }
            else if (this.getAdultAge().equals("Young Adult"))
            {
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(22.0D);
                this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
                if (this.getTrueHealth() > 0){
                    this.setHealth(this.getTrueHealth());
                }
                if(!this.getHasInit())
                {
                	this.setHealth(this.getHealth() + 2);
                }
            }
            else
            {
                this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.55D);
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
                this.setSize(0.6F, 1.95F);
                if (this.getTrueHealth() > 0){
                    this.setHealth(this.getTrueHealth());
                }
            }
        }
    }
	public void addNoteList(UUID player_to_add)
	{
		if (!this.note_list.contains(player_to_add))
		{
			this.note_list.add(player_to_add);
		}
	}
	public void addNoteList(UUID player_to_add, ItemStack itemstack, EntityPlayer playerC)
	{
		if (!this.note_list.contains(player_to_add))
		{
			this.note_list.add(player_to_add);
        	itemstack.damageItem(1, playerC);
        	((EntityPlayerSP) playerC).sendChatMessage("You will now receive notifications from " + this.getName());
		}
	}
	public ArrayList<UUID> getNoteList()
	{
		return this.note_list;
	}
	@Nullable
    public EntityLivingBase getNotePlayer(int num)
    {
        try
        {
            UUID uuid = this.getNoteList().get(num);
            return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }
	@Override
	public void onDeath(DamageSource cause)
    {
       if (!this.world.isRemote && this.world.getGameRules().getBoolean("showDeathMessages") && !(this.note_list == null))
       {
    	   for (int a = 0; a < this.getNoteList().size(); a++)
    	   {
    		   this.getNotePlayer(a).sendMessage(this.getCombatTracker().getDeathMessage());
    	   }
       }
       if (this.getHired())
       {
    	   for (int s = 0; s < 15; s++)
    	   {
    	       this.entityDropItem(this.item_handler.getStackInSlot(s), 0);
    	   }
       }
       for (int i = 0; i < 20; i++)
       {
    	   this.entityDropItem(this.villagerInventory.getStackInSlot(i), 0);
       }
       super.onDeath(cause);
    }
    @Nullable
    public EntityLivingBase getOwner()
    {
        try
        {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }
    @Nullable
    public UUID getOwnerId()
    {
        return (UUID)((Optional)this.dataManager.get(OWNER_DEFINED_ID)).orNull();
    }

    public void setOwnerId(@Nullable UUID p_184754_1_)
    {
        this.dataManager.set(OWNER_DEFINED_ID, Optional.fromNullable(p_184754_1_));
    }
    public Float getTrueHealth()
    {
    	return (Float)this.dataManager.get(True_Health);
    }
	protected void setTrueHealth(Float num)
    {
        this.dataManager.set(True_Health, num);
    }
	@Override
	protected void entityInit()
    {
		super.entityInit();
		this.getDataManager().register(Gender, Integer.valueOf(0));
		this.getDataManager().register(Mother_ID, Optional.<UUID>absent());
		this.getDataManager().register(Father_ID, Optional.<UUID>absent());
		this.getDataManager().register(int_Age, Integer.valueOf(1));
		this.getDataManager().register(Adult_Age, String.valueOf(""));
		this.getDataManager().register(Tab, String.valueOf("Info"));
		this.getDataManager().register(mother_name, String.valueOf(""));
		this.getDataManager().register(father_name, String.valueOf(""));
		this.getDataManager().register(Is_Hired, Boolean.valueOf(false));
		this.getDataManager().register(has_init, Boolean.valueOf(false));
		this.getDataManager().register(Following, Boolean.valueOf(true));
		this.getDataManager().register(Hire_Cost, Integer.valueOf(0));
		this.getDataManager().register(True_Health, Float.valueOf(0));
        this.getDataManager().register(OWNER_DEFINED_ID, Optional.<UUID>absent());
    }
	public static String random_name(int Gender)
	{
		String Name = null;
		if (!Config.use_spanish)
		{
	     		if (Gender == 1){
	     			Name = en_male_list[r.nextInt(en_male_list.length)];
	     		}
	     		else if (Gender == 2){
	     			Name = en_female_list[r.nextInt(en_female_list.length)]; 
	     		}
	     		else{
	     			Name = "None";
	     			System.out.println("Something went wrong with gender, please report.");
	     			System.out.println("No Name");
	     		}
	     	return Name;
		}
		else
		{
			if (Gender == 1){
     			Name = es_male_list[r.nextInt(es_male_list.length)];
     		}
     		else if (Gender == 2){
     			Name = es_female_list[r.nextInt(es_female_list.length)]; 
     		}
     		else{
     			Name = "None";
     			System.out.println("Something went wrong with gender, please report.");
     			System.out.println("No Name");
     		}
     	return Name;
		}
	}
    public IvVillager createChild(EntityAgeable ageable, UUID Father_Id, UUID Mother_Id)
    {
    	String Name = null;
	    int Gender;
	    Gender = r.nextInt(2) + 1;
	    Name = this.random_name(Gender);
        IvVillager IvVillager = new IvVillager(this.world, r.nextInt(6), Gender, Name, Father_Id, Mother_Id);
        IvVillager.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(IvVillager)), (IEntityLivingData)null);
        return IvVillager;
    }
	protected void setAdultAge(String name)
    {
        this.dataManager.set(Adult_Age, name);
    }
	protected void setMotherName(String name)
    {
        this.dataManager.set(mother_name, name);
    }
	protected void setTab(String name)
    {
        this.dataManager.set(Tab, name);
    }
	protected void setFatherName(String name)
    {
        this.dataManager.set(father_name, name);
    }
	protected void setIntAge(int num)
    {
        this.dataManager.set(int_Age, num);
    }
	public int getHireCost()
    {
          return (int)this.dataManager.get(Hire_Cost);
    }
	protected void setHireCost(int num)
    {
        this.dataManager.set(Hire_Cost, num);
    }
	@Nullable
    public UUID getFatherId()
    {
        return (UUID)((Optional)this.dataManager.get(Father_ID)).orNull();
    }

    public void setFatherId(@Nullable UUID p_184754_1_)
    {
        this.dataManager.set(Father_ID, Optional.fromNullable(p_184754_1_));
    }
    @Nullable
    public UUID getMotherId()
    {
        return (UUID)((Optional)this.dataManager.get(Mother_ID)).orNull();
    }

    public void setMotherId(@Nullable UUID p_184754_1_)
    {
        this.dataManager.set(Mother_ID, Optional.fromNullable(p_184754_1_));
    } 
    public int getIntAge()
    {
          return (int)this.dataManager.get(int_Age);
    }
	protected void setHired(boolean bool)
    {
        this.dataManager.set(Is_Hired, bool);
    }
	public boolean getHired()
    {
          return (boolean)this.dataManager.get(Is_Hired);
    }
	protected void setHasInit(boolean bool)
    {
        this.dataManager.set(has_init, bool);
    }
	public boolean getHasInit()
    {
          return (boolean)this.dataManager.get(has_init);
    }
	protected void setGender(int num)
    {
        this.dataManager.set(Gender, num);
    }
	public int getGender()
    {
          return (Integer)this.dataManager.get(Gender);
    }
	public void setFollowing(boolean bool)
    {
        this.dataManager.set(Following, bool);
    }
	public boolean getFollowing()
    {
          return (boolean)this.dataManager.get(Following);
    }
    public String getAdultAge()
    {
        return (String)this.dataManager.get(Adult_Age);
    }
    public String getTab()
    {
        return (String)this.dataManager.get(Tab);
    }
    public String getFatherName()
    {
        return (String)this.dataManager.get(father_name);
    }
    public String getMotherName()
    {
        return (String)this.dataManager.get(mother_name);
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
        	this.setMoreVillagerNbtStuff();
        }
        else{
        	this.setAdultAge("Child");
        	this.setMoreVillagerNbtStuff();

        }
	}
	public void ivVillagerAdultAge(int lifeChangeNum){
		if (world.isRemote == false){
        	if (this.isChild() == false){
        		if (this.getIntAge() >= lifeChangeNum){
        			if (this.getAdultAge().equals("Young Adult")){
        	        	this.setIntAge(1);
        				this.setAdultAge("Middle Aged");
        	        	this.setMoreVillagerNbtStuff();
        			}
        			else if (this.getAdultAge().equals("Middle Aged")){
        	        	this.setIntAge(1);
        				this.setAdultAge("Elder");
        	        	this.setMoreVillagerNbtStuff();
        			}
        			else if (this.getAdultAge().equals("Elder")){
        				this.setIntAge(this.getIntAge() - (2500 * Config.adult_days));
        				if (r.nextInt(3) == 0)
        				{
            				this.damageEntity(Iv.dmgOldAge, 22);
        				}
        			}
    	            if (!this.world.isRemote && !(this.note_list == null))
    	            {
    	         	   for (int a = 0; a < this.getNoteList().size(); a++)
    	         	   {
    	         		   this.getNotePlayer(a).sendMessage(createChatComponent(this.getCustomNameTag() + " has aged to an " + this.getAdultAge()));
    	         	   }
    	            }
        		}
        		else{
        				this.setIntAge(this.getIntAge() + 1);
        			}
        			
        	}
        }
	}
	public static ITextComponent createChatComponent(String message) {
        return new TextComponentTranslation(message);
	}
	@Override
	public void onLivingUpdate()
	    {
	        super.onLivingUpdate();
	        this.ivVillagerAdultAge((Config.adult_days * 24000)/3);
	        if (this.robbed_time > 0)
	        {
	        	this.robbed_time -= 1;
	        }
	        this.setMoreVillagerNbtStuff();
	    }
	@Override
	protected void onGrowingAdult()
    {
        super.onGrowingAdult();
        this.setAdultAge("Young Adult");
    	this.setIntAge(1);
        if (!this.world.isRemote && !(this.note_list == null))
        {
     	   for (int a = 0; a < this.getNoteList().size(); a++)
     	   {
     		   this.getNotePlayer(a).sendMessage(createChatComponent(this.getCustomNameTag() + " has aged to an " + this.getAdultAge()));
     	   }
        }
    	this.setMoreVillagerNbtStuff();
    }
	@Override
	protected void initEntityAI()
    {
		if (world.isRemote == false){
			BlockPos blockpos = new BlockPos(this);
			this.villageObj = this.world.getVillageCollection().getNearestVillage(blockpos, 32);
	        this.tasks.addTask(0, new EntityAISwimming(this));
	        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
	        //this.tasks.addTask(1, new VillagerAvoidEvilPlayer(this, 8.0F, 0.6D, 0.6D, this.villageObj));
	        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityEvoker.class, 12.0F, 0.8D, 0.8D));
	        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityVindicator.class, 8.0F, 0.8D, 0.8D));
	        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityVex.class, 8.0F, 0.6D, 0.6D));
	        this.tasks.addTask(1, new EntityAIPanic(this, 0.8D));
	        this.tasks.addTask(2, new VillagerAIFollowParent(this, 0.8D));
	        this.tasks.addTask(1, new EntityAITradePlayer(this));
	        this.tasks.addTask(2, new EntityAILookAtTradePlayer(this));
	        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
	        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
	        this.tasks.addTask(3, new EntityAITempt(this, 0.85D, Items.EMERALD, false));
	        //this.tasks.addTask(3, new VillagerFollowEmerald(this, 0.9D, Items.EMERALD, false, this.villageObj));
	        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
	        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
	        this.tasks.addTask(5, new IvVilsPerDoor(this));
	        this.tasks.addTask(7, new EntityAIFollowGolem(this));
	        this.tasks.addTask(9, new EntityAIVillagerInteract(this));
	        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
	        this.tasks.addTask(10, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
	        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
		}
    }
	protected void setAdditionalAItasks()
    {
        if (!this.areAdditionalTasksSet)
        {
            this.areAdditionalTasksSet = true;

            if (this.isChild())
            {
                this.tasks.addTask(8, new EntityAIPlay(this, 0.32D));
            }
            else if (this.getProfessionForge() == PROFESSION_FARMER || this.getProfession() == 0)
            {
                this.tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6D));
            }
            if (this.getHired())
            {
                this.tasks.addTask(6, new VillagerFollowOwner(this, 0.9D, 6.0F, 1.5F));
            }
        }
    }
	 @Override
	 public void writeEntityToNBT(NBTTagCompound compound)
	    {
	        super.writeEntityToNBT(compound);
	        //if (world.isRemote == false){
		        compound.setInteger("Profession", this.getProfession());
		        compound.setString("ProfessionName", this.getProfessionForge().getRegistryName().toString());
		        compound.setInteger("Riches", this.wealth);
	        	compound.setInteger("Gender", this.getGender());
		        compound.setInteger("Int_Age", this.getIntAge());
		        compound.setFloat("True_Health", this.getHealth());
		        compound.setInteger("Hire_Cost", this.getHireCost());
		        compound.setBoolean("Is_Hired", this.getHired());
		        compound.setInteger("Career", this.careerId);
		        compound.setInteger("Robbed_Time", this.robbed_time);
		        compound.setString("mother_name", this.getMotherName());
		        compound.setString("father_name", this.getFatherName());
		        compound.setString("tab", this.getTab());
		        compound.setInteger("CareerLevel", this.careerLevel);
		        if (!(this.getMotherId() == null))
		        {
			        compound.setUniqueId("mother_id", this.getMotherId());
		        }
		        if (!(this.getFatherId() == null))
		        {
			        compound.setUniqueId("father_id", this.getFatherId());
		        }
		        compound.setBoolean("Willing", this.isWillingToMate);
		        compound.setBoolean("has_init", this.getHasInit());
		        compound.setTag("Villager_Inv", this.item_handler.serializeNBT());
		        for (int b = 0; b < this.getNoteList().size(); b++)
		        {
		        	compound.setUniqueId("UUID Note " + Integer.toString(b), this.getNoteList().get(b));
		        }
		        if (this.getOwnerId() == null)
		        {
		            compound.setString("OwnerUUID", "");
		        }
		        else
		        {
		            compound.setString("OwnerUUID", this.getOwnerId().toString());
		        }

		        compound.setBoolean("Following", this.getFollowing());
		        if ((this.getAdultAge().equals("")) == false){
			        compound.setString("Adult_Age", this.getAdultAge());
		        }
		        if (this.buyingList != null)
		        {
		            compound.setTag("Offers", this.buyingList.getRecipiesAsTags());
		        }
	        //}
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
	        
	    }
	 
	 public void hire_Villager(EntityPlayer player)
	 {
		 Exception HiringAlreadyHiredVillager = new Exception("Tried to hire an already hired villager");

		 if (item_handler.getStackInSlot(0).getCount() >= this.getHireCost() && item_handler.getStackInSlot(0).getItem().equals(Items.EMERALD) && !this.getHired())
		 {
			 int remaining_i = item_handler.getStackInSlot(0).getCount() - this.getHireCost();
			 this.villagerInventory.addItem(new ItemStack(Items.EMERALD, item_handler.getStackInSlot(0).getCount() - remaining_i));
			 this.setHired(true);
	         this.setOwnerId(player.getUniqueID());
	         item_handler.setStackInSlot(0, new ItemStack(Items.EMERALD, remaining_i));
             this.tasks.addTask(6, new VillagerFollowOwner(this, 0.9D, 6.0F, 1.5F));
     		 player.openGui(Iv.instance, GuiHandler.Hauler, this.world, getEntityId(), 0, 0);
		 }
		 else if (this.getHired())
		 {
			 try {
				throw HiringAlreadyHiredVillager;
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
	 }
	 public void change_tab(int button, EntityPlayer player)
	 {
		 if (button == 0)
		 {
			this.setTab("Info");
     		player.openGui(Iv.instance, GuiHandler.Info, world, getEntityId(), 0, 0);
		 }
		 else if (button == 1)
		 {
			this.setTab("Hire");
     		player.openGui(Iv.instance, GuiHandler.Hire, world, getEntityId(), 0, 0);
		 }
		 else if (button == 2)
		 {
			this.setTab("Hauler");
     		player.openGui(Iv.instance, GuiHandler.Hauler, world, getEntityId(), 0, 0);
		 }
		 else if (button == 3)
		 {
			this.setTab("Info");
            if (this.buyingList == null)
            {
                this.populateBuyingList();
            }

            if (!this.world.isRemote && !this.buyingList.isEmpty())
            {
                this.setCustomer(player);
                player.displayVillagerTradeGui(this);
            }
		 }
		 else if (button == 4)
		 {
			 this.setTab("Inventory");
	     	 player.openGui(Iv.instance, GuiHandler.Inventory, world, getEntityId(), 0, 0);
		 }
	 }
	 public void change_following()
	 {
		 if (this.getFollowing() == false){
			 this.setFollowing(true);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if (this.getFollowing() == true){
				this.setFollowing(false);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	 }
	 @Override
	 public void readEntityFromNBT(NBTTagCompound compound){
		 super.readEntityFromNBT(compound);
	     String s;
		 if (world.isRemote == false){
			 if (compound.hasKey("Gender"))
			 {
				 this.setGender(compound.getInteger("Gender"));
			 }
			 this.name = compound.getString("Name");
		 }
		 this.item_handler.deserializeNBT(compound.getCompoundTag("Villager_Inv"));
		 this.setFatherName(compound.getString("father_name"));
		 this.setMotherName(compound.getString("mother_name"));
		 this.setTab(compound.getString("tab"));
	     for (int b = 0; b < this.getNoteList().size(); b++)
	     {
	         this.addNoteList(compound.getUniqueId("UUID Note " + Integer.toString(b)));
	     }
		 if (compound.hasKey("Adult_Age"))
         {
             this.setAdultAge(compound.getString("Adult_Age"));
             if (this.isChild())
             {
            	 this.setAdultAge("Child");
             }
         }
		 if (compound.hasKey("Is_Hired"))
         {
             this.setHired(compound.getBoolean("Is_Hired"));
         }
		 if (compound.hasKey("has_init"))
         {
             this.setHired(compound.getBoolean("has_init"));
         }
		 if (compound.hasKey("Following"))
         {
             this.setFollowing(compound.getBoolean("Following"));
         }
		 if (compound.hasKey("Int_Age")){
			 this.setIntAge(compound.getInteger("Int_Age"));
		 }
		 if (compound.hasKey("mother_id")){
			 this.setMotherId(compound.getUniqueId("mother_id"));
		 }
		 if (compound.hasKey("father_id")){
			 this.setFatherId(compound.getUniqueId("father_id"));
		 }
		 if (compound.hasKey("True_Health")){
			 this.setTrueHealth(compound.getFloat("True_Health"));
		 }
		 this.robbed_time = compound.getInteger("Robbed_Time");
		 if (compound.hasKey("Hire_Cost")){
			 this.setHireCost(compound.getInteger("Hire_Cost"));
		 }
		 else {
			    this.setHireCost(r.nextInt(21) + 20);
		 }
		 if (compound.hasKey("OwnerUUID", 8))
	        {
	            s = compound.getString("OwnerUUID");
	        }
	        else
	        {
	            String s1 = compound.getString("Owner");
	            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
	        }

	        if (!s.isEmpty())
	        {
	            try
	            {
	                this.setOwnerId(UUID.fromString(s));
	                this.setHired(true);
	            }
	            catch (Throwable var4)
	            {
	                this.setHired(false);
	            }
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
	        this.setMoreVillagerNbtStuff();
	        this.setHasInit(true);
		        	//this.setCustomNameTag(this.name);
	 }
	 protected void populateBuyingList()
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
        if (itemstack.getItem() == IvItems.thieving_nose && !this.isChild() && this.robbed_time == 0){
        	itemstack.damageItem(1, player);
        	this.setHealth(this.getHealth() - 2);
        	this.playHurtSound(getLastDamageSource());
        	int randy = rand.nextInt(10) + 1 ;
        	if (randy < 6){
        		
        	}
        	if (randy < 9 && randy  > 5){
        		this.entityDropItem(new ItemStack(Items.EMERALD, r.nextInt(2) + 1), 0);
        		if (rand.nextInt(2) == 0)
        		{
        			this.robbed_time = 1200;
        		}
        	}
        	if (randy < 10 && randy + 1  > 8){
        		this.entityDropItem(new ItemStack(Items.EMERALD, r.nextInt(5) + 1), 0);
        		if (rand.nextInt(2) == 0)
        		{
        			this.robbed_time = 1200;
        		}
        	}
        	if (world.isRemote == false){
        	if (this.villageObj != null)
	            {
	        		this.villageObj.modifyPlayerReputation(player.getName(), -2);
	            }
        	}
        	if (!this.world.isRemote && !(this.note_list == null))
            {
         	   for (int a = 0; a < this.getNoteList().size(); a++)
         	   {
         		   this.getNotePlayer(a).sendMessage(createChatComponent(this.getCustomNameTag() + " was robbed by " + player.getName()));
         	   }
            }
            return true;
        }
        else if (itemstack.getItem() == IvItems.notification_marker)
        {
        	this.addNoteList(player.getUniqueID(), itemstack, player);
        	itemstack.damageItem(1, player);
        	return true;
        }
        else if (this.getTab().equals("Hire"))
        {
        	if (!world.isRemote) {
        		player.openGui(Iv.instance, GuiHandler.Hire, world, getEntityId(), 0, 0);
        	}
        	return true;
        }
        else if (this.getTab() == "Info")
        {
        	if (!world.isRemote) {
        		player.openGui(Iv.instance, GuiHandler.Info, world, getEntityId(), 0, 0);
        		}
        	return true;
        }
        else if (this.getTab().equals("Hauler"))
        {
        	if (!world.isRemote) {
        		player.openGui(Iv.instance, GuiHandler.Hauler, world, getEntityId(), 0, 0);
        		}
        	return true;
        }
        else if (this.getTab().equals("Inventory"))
        {
        	if (!world.isRemote) {
        		player.openGui(Iv.instance, GuiHandler.Inventory, world, getEntityId(), 0, 0);
        		}
        	return true;
        }
        else if (!this.holdingSpawnEggOfClass(itemstack, EntityVillager.class) && this.isEntityAlive() && !this.isChild())
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
