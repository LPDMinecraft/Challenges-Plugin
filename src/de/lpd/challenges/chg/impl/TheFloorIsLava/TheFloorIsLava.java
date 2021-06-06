package de.lpd.challenges.chg.impl.TheFloorIsLava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

public class TheFloorIsLava extends Challenge {
	
	private Config cfg;
	private ChallengesMainClass plugin;
	private ArrayList<Block> blocks = new ArrayList<>();
	
	public TheFloorIsLava(ChallengesMainClass plugin) {
		super(plugin, "thefloorislava", "thefloorislava.yml", "thefloorislava");
		this.plugin = plugin;
		
		
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			
			public void run() {
				
				// System.out.println("1");
				if(isEnabled()) {
					/*System.out.println("2");
					String root = "locations.hashmap.0";
					for(String c : cfg.cfg().getConfigurationSection(root).getKeys(false)) {
						String r = root + "." + c;
						
						Location loc = cfg.getBlockLocation(r + ".loc");
						int secs = (int) cfg.cfg().get(r + ".secounds");
						boolean active = (boolean) cfg.cfg().get(r + ".active");
						Material m = Material.getMaterial((String) cfg.cfg().get(r + ".material"));
						
						System.out.println("3");
						
						double prozent = secs / (int)getOption(cfg, "thefloorislava.max", 30);
						
						if(secs > 0) {
							if(isEnabled()) {
								if(active) {
									
									Material set = null;
                                    if(prozent < 0.94) {
										set = Material.MAGMA_BLOCK;
									} else if(prozent < 0.67) {
										set = Material.LAVA;
									} else if(prozent < 0.19) {
										set = Material.OBSIDIAN;
									} else if(prozent < 0.1) {
										set = Material.BEDROCK;
									} else if(prozent < 0.04) {
										set = Material.BEDROCK;
									} else {
										set = m;
									}
                                    loc.getBlock().setType(set);
                                    
                                    cfg.cfg().set(root + ".secounds", cfg.cfg().getInt(root + ".secounds") - 1);
                                    cfg.save();
									
								}
							}
						} else {
							active = false;
						}
					} */
					
					if(blocks != null && !blocks.isEmpty()) {
						try {
							System.out.println("on");
							for(Block c : blocks) {
								Location loc = c.getLoc();
								int secs = c.getSecounds();
								boolean active = c.isActive();
								Material m = c.getMaterial();
								
								double prozent = secs / ((int)getOption(cfg, "thefloorislava.max", 30));
								
								System.out.println(prozent);
								
								if(secs > 0) {
									if(isEnabled()) {
										if(active) {
											
											Material set = null;
		                                    if(prozent < 0.94) {
												set = Material.MAGMA_BLOCK;
											} else if(prozent < 0.67) {
												set = Material.LAVA;
											} else if(prozent < 0.19) {
												set = Material.OBSIDIAN;
											} else if(prozent < 0.1) {
												set = Material.BEDROCK;
											} else if(prozent < 0.04) {
												set = Material.BEDROCK;
											} else {
												set = m;
											}
		                                    if(set != Material.AIR) {
		                                    	loc.getBlock().setType(set);
		                                    }
		                                    
		                                    secs = secs - 1;
		                                    c.secounds = secs;
											blocks.add(c);
										}
									}
								} else {}
							}
							System.out.println("stop");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}, 20, 20);
		
	}

	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
	}

	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.LAVA_BUCKET);
		if(isToggled()) {
			ib.setDisplayName("§6TheFloorisLava " + Starter.STARTPREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6TheFloorisLava " + Starter.STARTPREFIX + "§cOff");
		}
		String[] lore = new String[6];
		lore[0] = Starter.STARTPREFIX + "§aIn dieser Challenge muss du in x Sekunden";
		lore[1] = "§avor der Lava entfliehen";
		lore[2] = "§7Derzeitig ausgewählte Zeit§8: §6" + getOption(cfg, "thefloorislava.max", 30);
		lore[3] = "§6Linksklick §7> §a-1 Sekunde";
		lore[4] = "§6Rechtsklick §7> §a+1 Sekunde";
		lore[5] = "§6Mittelklick §7> §aAn/Aus diese Challenge";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		int a = (int)getOption(cfg, "thefloorislava.max", 30);
		setOption(cfg, "thefloorislava.max", a + 1);
	}

	@Override
	public void onLeftClick(Player p) {
		int a = (int)getOption(cfg, "thefloorislava.max", 30);
		if(a > 0) {
			setOption(cfg, "thefloorislava.max", a - 1);
		}
	}

	@Override
	public void onMiddleClick(Player p) {
		toggle();
	}

	@Override
	public void reset() {
	}
	
	private HashMap<Player, Double> far = new HashMap<>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Location l = e.getTo();
		if(isEnabled() && l != null && l.getWorld() != null) {
			
			double moreX = e.getFrom().getX() - e.getTo().getX();
			double moreY = e.getFrom().getY() - e.getTo().getY();
			double moreZ = e.getFrom().getZ() - e.getTo().getZ();
			if(moreX < 0) {
				moreX = e.getTo().getX() - e.getFrom().getX();
			}
			if(moreY < 0) {
				moreY = e.getTo().getY() - e.getFrom().getY();
			}
			if(moreZ < 0) {
				moreZ = e.getTo().getZ() - e.getFrom().getZ();
			}
			double add = moreX + moreY + moreZ;
			
			if(far.containsKey(e.getPlayer())) {
				add = add + far.get(e.getPlayer());
				far.remove(e.getPlayer());
			}
			far.put(e.getPlayer(), add);
			
			System.out.println(add);
			
			if(far != null && !far.isEmpty() && far.containsKey(e.getPlayer()) && far.get(e.getPlayer()) >= 3) {
				/*float id = new Random().nextFloat();
				String root = "locations.hashmap." + id;
				while(cfg.cfg().contains(root)) {
					id = new Random().nextFloat();
					root = "locations.hashmap." + id;
				}
				cfg.saveBlockLocation(root + ".loc", l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ());
				cfg.cfg().set(root + ".secounds", (int)getOption(cfg, "thefloorislava.max", 30));
				cfg.cfg().set(root + ".active", true);
				cfg.cfg().set(root + ".material", l.getBlock().getType().toString());
				
				cfg.save();*/
				
				blocks.add(new Block(l, (int)getOption(cfg, "thefloorislava.max", 30), true, l.getBlock().getType()));
				
				System.out.println("New Block");
				
				far.remove(e.getPlayer());
			}
		}
	}
	
	public class Block {
		
		private Location loc;
		private int secounds;
		private boolean active;
		private Material material;
		
		public Block(Location loc, int secounds, boolean active, Material material) {
			this.loc = loc;
			this.secounds = secounds;
			this.active = active;
			this.material = material;
		}
		
		public Location getLoc() {
			return loc;
		}
		public int getSecounds() {
			return secounds;
		}
		public boolean isActive() {
			return active;
		}
		public Material getMaterial() {
			return material;
		}
		public void setActive(boolean active) {
			this.active = active;
		}
		public void setLoc(Location loc) {
			this.loc = loc;
		}
		public void setMaterial(Material material) {
			this.material = material;
		}
		public void setSecounds(int secounds) {
			this.secounds = secounds;
		}
		
	}
	
}
