package orangeVillager61.ImprovedVillagers.Entities;

public class HarvestTimeUser implements IHarvestTime{
	
	private long time = 0;
	
	@Override
	public void setTime(long l)
	{
		this.time = l;
	}

	@Override
	public long get_time() {
		return time;
	}

}
