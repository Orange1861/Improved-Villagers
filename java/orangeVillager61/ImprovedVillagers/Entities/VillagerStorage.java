package orangeVillager61.ImprovedVillagers.Entities;

import java.util.Random;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class VillagerStorage implements IVillagerStorage

{
 Random r = new Random();
 private static String name;
 protected int gender;
 protected String[] male_list = {"Bob", "Joseph", "Aaron", "Philp", "Adam", "Paul", "Donald", "Ryan", 
			"Mark", "Brian", "Robert", "Willam", "Harold", "Anthony", "Julius", 
			"Mathew", "Tyler", "Noah", "Patrick", "Caden", "Michael", "Jeffery",
			"James", "John", "Thomas", "Otto", "Bill", "Sheldon", "Leonard", 
			"Howard", "Carter", "Theodore", "Herbert"};
 protected String[] female_list = {"Karen", "Lessie", "Kayla", "Brianna", "Isabella", "Elizabeth",
			  "Kira", "Jadzia", "Abigail", "Chloe", "Olivia", "Sophia", "Emily", 
			  "Charlotte", "Amelia", "Maria", "Daria", "Sarah", "Theodora",
			  "Tia", "Jennifer", "Anglica", "Denna", "Tasha", "Catherine", "Lily",
			  "Amy", "Penny", "Julina", "Audrey", "Avery"};

 @Override
 public String getName()

 {

 return this.name;

 }



@Override
public void setName(String name) {
	if (this.gender != 1 || this.gender != 2){
		this.gender = r.nextInt(2) + 1;
	}
	if (name.equals("None")){
		if (this.gender == 1){
			name = male_list[r.nextInt(male_list.length)];
		}
		else if (this.gender == 2){
			name = female_list[r.nextInt(male_list.length)];
		}
		else{
			name = "None";
			System.out.println("Something went wrong with gender, please report.");
		}
	}
	this.name = name;
}



@Override
public void setGender(int gender) {
	if (gender != 1 || gender != 2){
		gender = r.nextInt(2) + 1;
	}
	this.gender = gender;
}



@Override
public int getGender() {
	// TODO Auto-generated method stub
	return this.gender;
}

}
