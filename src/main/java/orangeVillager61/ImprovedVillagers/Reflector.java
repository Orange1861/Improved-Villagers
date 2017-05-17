package orangeVillager61.ImprovedVillagers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureComponent;
import orangeVillager61.ImprovedVillagers.Entities.IvVillager;
import orangeVillager61.ImprovedVillagers.generation.VillageStructures;
import orangeVillager61.ImprovedVillagers.generation.VillageStructures.Village;

public class Reflector {
	
	public static Object reflectMethod1(List<VillageStructures.PieceWeight> list, Random random, int size){
		
		Class reflectClass = net.minecraftforge.fml.common.registry.VillagerRegistry.class;
		Method method;
		try {
			method = reflectClass.getMethod("addExtraVillageComponents", VillageStructures.PieceWeight.class, Random.class, int.class);
			Object returnValue = method.invoke(null, list, random, size);
			return returnValue;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
      }
	public static Village reflectMethod2(VillageStructures.Start start, VillageStructures.PieceWeight weight, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType){
		
		Class reflectClass = net.minecraftforge.fml.common.registry.VillagerRegistry.class;
		Method method;
		try {
			method = reflectClass.getMethod("getVillageComponent", List.class, VillageStructures.Start.class, VillageStructures.PieceWeight.class, StructureComponent.class, Random.class, int.class, EnumFacing.class);
			Object returnValue = method.invoke(null, weight, start , structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
			Village returnObj = (Village) returnValue;
			return returnObj;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 {
			return null;
		}
      }
}