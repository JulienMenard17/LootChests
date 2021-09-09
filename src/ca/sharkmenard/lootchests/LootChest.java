package ca.sharkmenard.lootchests;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class LootChest {
	
	private long lastOpen;
	private Location loc;
	private BlockFace orientation;
	private byte orientByte;
  
	public LootChest(Location loc) {
		this(loc, BlockFace.NORTH);
	}



  
	@SuppressWarnings("deprecation")
	public LootChest(Location loc, BlockFace orient) {
		this.loc = loc;
		lastOpen = System.currentTimeMillis();
		if (orient == null) {
			orientation = BlockFace.NORTH;
		} else {
			orientation = orient;
		} 
		this.loc.getBlock().setType(Material.CHEST);
		switch (orientation) {
			case NORTH:
				orientByte = 2;
				break;
			case SOUTH:
				orientByte = 3;
				break;
			case WEST:
				orientByte = 4;
				break;
			case EAST:
				orientByte = 5;
				break;
			default:
				orientByte = 2;
				break;
		} 
    
		this.loc.getBlock().setData(orientByte);
  }

  
  	public Location getLocation() {
  		return loc;
  	}


  
  	public long getLastOpen() {
  		return lastOpen;
  	}


  
  	public void updateLastOpen() {
  		this.lastOpen = System.currentTimeMillis(); 
  	}


  
  	public BlockFace getOrientation() {
  		return this.orientation; 
  	}
}
