package de.lpd.challenges.chg.impl.WaterMLG;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Mathe;
import de.lpd.challenges.utils.Starter;

public class WaterMLG extends Challenge {
	
	private Config cfg;
	private ChallengesMainClass plugin;
	
	public WaterMLG(ChallengesMainClass plugin) {
		super(plugin, "watermlg", "watermlg.yml", "watermlg", 3*9, true, "Water MLG", "chmenu", "challenge-watermlg", "Challenges Menu");
		this.plugin = plugin;
		send();
	}
	
	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
	}
	
	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.WATER_BUCKET);
		ib.setDisplayName("§6WaterMLG");
		String[] lore = new String[4];
		lore[0] = Starter.START_PREFIX + "§aIn dieser Challenge muss du in x Sekunden";
		lore[1] = "§aeinen WaterMLG machen. Wenn einer dabei stirbt ist die Challange";
		lore[2] = "§avorbei.";
		lore[3] = "§6Mittelklick §7> §aÖffne das Inventart";
		
		ib.setLoreString(lore);
		return ib.build();
	}
	
	@Override
	public void onRightClick(Player p) {
		//setOption(cfg, "watermlg.max", (double)getOption(cfg, "watermlg.max", 120) + 5);
	}
	
	@Override
	public void onLeftClick(Player p) {
		/*if((double)getOption(cfg, "watermlg.max", 30) > 5) {
			setOption(cfg, "watermlg.max", (double)getOption(cfg, "watermlg.max", 120) - 5);
		}*/
	}
	
	@Override
	public void onMiddleClick(Player p) {
		//toggle();
		p.openInventory(getInventory(1, p));
	}
	
	@Override
	public void reset() {
		invs = new HashMap<>();
		loc = new HashMap<>();
	}

	@Override
	public void ifPlayerDies() {

	}

	private HashMap<Player, ItemStack[]> invs = new HashMap<>();
	private HashMap<Player, Location> loc = new HashMap<Player, Location>();
	
	public void send() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				invs = new HashMap<>();
				if(isEnabled()) {
					for(Player c : Bukkit.getOnlinePlayers()) {
						if(c.getGameMode() == GameMode.SURVIVAL) {
							invs.put(c, c.getInventory().getContents());
							c.getInventory().clear();
							c.leaveVehicle();
							
							loc.put(c, c.getLocation());
							
							// 30 - 50 Blöcke
							int min = Math.round(Math.round((double)getOption(cfg, "watermlg.hight.min", 50.00)));
							int max = Math.round(Math.round((double)getOption(cfg, "watermlg.hight.max", 50.00)));
							int r = Mathe.getRandom(min, max);

							World w;
							if(Bukkit.getWorld("watermlg") == null) {
								WorldCreator worldCreator = WorldCreator.name("watermlg");
								worldCreator.environment(World.Environment.NORMAL);
								worldCreator.type(WorldType.FLAT);
								w = Bukkit.createWorld(worldCreator);
							} else {
								w = Bukkit.getWorld("watermlg");
							}
							System.out.println(Mathe.getRandom(Math.round((int)Math.round((double)getOption(cfg, "watermlg.secounds.min", 300.00))), (int)Math.round(Math.round((double)getOption(cfg, "watermlg.secounds.max", 300.00)))));
							System.out.println(r);
							Location locs = new Location(w, 0, 0, 0);
							locs.setY((ChallengesMainClass.getHighestY(locs) + 1));

							locs.getBlock().setType(Material.AIR);
							new Location(locs.getWorld(), locs.getX(), locs.getY() - 1, locs.getZ()).getBlock().setType(Material.BEDROCK);

							locs.setY(locs.getY() + r);

							for(int y = (locs.getBlockY() - 2); y > 0; y--) {
								for(int x = -20; x < 20; x++) {
									for(int z = -20; z < 20; z++) {
										w.getBlockAt(new Location(w, x, y, z)).setType(Material.AIR);
									}
								}
							}

							c.teleport(new Location(w, locs.getX(), locs.getY(), locs.getZ()));
							c.getInventory().addItem(new ItemBuilder(Material.WATER_BUCKET).setDisplayName("§6Der beste Wasser Springer").build());
							c.getInventory().addItem(new ItemBuilder(Material.SLIME_BLOCK).setDisplayName("§6Der beste Schleimer").build());
							c.getInventory().addItem(new ItemBuilder(Material.OAK_BOAT).setDisplayName("§6Der beste Fahrer").build());
							c.getInventory().addItem(new ItemBuilder(Material.HONEY_BLOCK).setDisplayName("§6Der Süßeste").build());
							Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
								
								@Override
								public void run() {
									c.getInventory().setContents(invs.get(c));
									invs.remove(c);
									c.teleport(loc.get(c));
									loc.remove(c);
								}
								
							}, ((r / 10) * 20));
						}
					}
				}
			}
			
		}, 0, 20 * Mathe.getRandom(Math.round((int)Math.round((double)getOption(cfg, "watermlg.secounds.min", 300.00))), (int)Math.round(Math.round((double)getOption(cfg, "watermlg.secounds.max", 300.00)))));
	}

	String plusminSecound = "§6Ändere die minimale Zeit bis zum nächsten MLG um +5 Sekunden",
			minusminSecound = "§6Ändere die minimale Zeit bis zum nächsten MLG um -5 Sekunden",
			plusmaxSecound = "§6Ändere die maximale Zeit bis zum nächsten MLG um +5 Sekunden",
			minusmaxSecound = "§6Ändere die maximale Zeit bis zum nächsten MLG um -5 Sekunden",
			plusminHöhe = "§6Ändere die minimale Höhe für den nächsten MLG um +2 Blöcke",
			minusminHöhe = "§6Ändere die minimale Höhe für den nächsten MLG um -2 Blöcke",
			plusmaxHöhe = "§6Ändere die maximale Höhe für den nächsten MLG um +2 Blöcke",
			minusmaxHöhe = "§6Ändere die maximale Höhe für den nächsten MLG um -2 Blöcke",
			namei = "";

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
		namei = "§6Water MLG(" + isToggled() + ")";

		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(namei)) {
			toggle();
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(plusminSecound)) {
			double c = (double) getOption(cfg, "watermlg.secounds.min", 300.00);
			c = c + 2.5;
			setOption(cfg, "watermlg.secounds.min", c);
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(minusminSecound)) {
			double c = (double) getOption(cfg, "watermlg.secounds.min", 300.00);
			if(c > 0) {
				c = c - 2.5;
				setOption(cfg, "watermlg.secounds.min", c);
			}
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(plusmaxSecound)) {
			double c = (double) getOption(cfg, "watermlg.secounds.max", 300.00);
			c = c + 2.5;
			setOption(cfg, "watermlg.secounds.max", c);
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(minusmaxSecound)) {
			double c = (double) getOption(cfg, "watermlg.secounds.max", 300.00);
			if(c > 0) {
				c = c - 2.5;
				setOption(cfg, "watermlg.secounds.max", c);
			}
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(plusminHöhe)) {
			double c = (double) getOption(cfg, "watermlg.hight.min", 20.00);
			c = c + 1;
			setOption(cfg, "watermlg.watermlg.hight.min", c);
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(minusminHöhe)) {
			double c = (double) getOption(cfg, "watermlg.hight.min", 20.00);
			if(c > 0) {
				c = c - 1;
				setOption(cfg, "watermlg.hight.min", c);
			}
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(plusmaxHöhe)) {
			double c = (double) getOption(cfg, "watermlg.hight.max", 50.00);
			c = c + 1;
			setOption(cfg, "watermlg.hight.max", c);
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(minusmaxHöhe)) {
			double c = (double) getOption(cfg, "watermlg.hight.max", 50.00);
			if(c > 0) {
				c = c - 1;
				setOption(cfg, "watermlg.hight.max", c);
			}
		}

	}

	@Override
	public Inventory getInventory(int page, Player p) {
		inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);

		ArrayList<ItemStack> items = new ArrayList<>();
		namei = "§6Water MLG(" + isToggled() + ")";
		ItemBuilder b;
		if(isToggled()) {
			b = new ItemBuilder(Material.EMERALD_BLOCK);
		} else {
			b = new ItemBuilder(Material.REDSTONE_BLOCK);
		}
		inv.setItem((9 * 2) - 1, b.setDisplayName(namei).build());

		inv.setItem(0, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(plusminSecound).build());
		inv.setItem(9, new ItemBuilder(Material.CLOCK).setDisplayName("§6Minimale Zeit: " + getOption(cfg, "watermlg.secounds.min", 300.00) + " Sekunden").build());
		inv.setItem(18, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(minusminSecound).build());

		inv.setItem(1, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(plusmaxSecound).build());
		inv.setItem(10, new ItemBuilder(Material.CLOCK).setDisplayName("§6Maximale Zeit: " + getOption(cfg, "watermlg.secounds.max", 300.00) + " Sekunden").build());
		inv.setItem(19, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(minusmaxSecound).build());

		inv.setItem(2, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(plusminHöhe).build());
		inv.setItem(11, new ItemBuilder(Material.CLOCK).setDisplayName("§6Minimale Höhe: " + getOption(cfg, "watermlg.hight.min", 50.00)).build());
		inv.setItem(20, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(minusminHöhe).build());

		inv.setItem(3, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(plusmaxHöhe).build());
		inv.setItem(12, new ItemBuilder(Material.CLOCK).setDisplayName("§6Maximale Höhe: " + getOption(cfg, "watermlg.hight.max", 50.00)).build());
		inv.setItem(21, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(minusmaxHöhe).build());

		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK()).build());

		return getPage(items, inv, page);
	}
}
