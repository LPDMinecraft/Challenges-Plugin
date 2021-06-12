package de.lpd.challenges.chg.impl.Hearths;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

import java.util.ArrayList;

public class MaxHearth extends Challenge {
	
	private Config cfg;
	private ChallengesMainClass plugin;	
	
	public MaxHearth(ChallengesMainClass plugin) {
		super(plugin, "maxhearth", "config.yml", "maxhearth", 6*9, true, "MaxHerzen", "chmenu", "challenge-maxhearths");
		this.setPlugin(plugin);
		
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				
				if(isEnabled()) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						System.out.println(p.getMaxHealth());
						if(p.getMaxHealth() != (int) getOption(cfg, "maxhearths.max", 20)) {
							p.setMaxHealth((int) getOption(cfg, "maxhearths.max", 20));
							
							System.out.println(getOption(cfg, "maxhearths.max", 20));
						}
					}
				}
			}
			
		}, 0, 1L);
	}

	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
	}

	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.CUT_RED_SANDSTONE_SLAB);
		if(isToggled()) {
			ib.setDisplayName("§6MaxLeben " + Starter.START_PREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6MaxLeben " + Starter.START_PREFIX + "§cOff");
		}
		String[] lore = new String[4];
		lore[0] = Starter.START_PREFIX + "§aIn dieser Challenge muss man Minecraft mit";
		lore[1] = "§aX Herzen durchspielen.";
		lore[2] = "§7Derzeitige Herzen§8: §6" + getOption(cfg, "maxhearths.max", 20) + "/20";
		lore[3] = "§6Mittelklick §7> §aÖffne Inventar";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		/*setOption(cfg, "maxhearths.max", (int)getOption(cfg, "maxhearths.max", 20) + 1);*/

	}

	@Override
	public void onLeftClick(Player p) {
		/*if((int)getOption(cfg, "maxhearths.max", 20) > 1) {
			setOption(cfg, "maxhearths.max", (int)getOption(cfg, "maxhearths.max", 20) - 1);
		}*/
	}

	@Override
	public void onMiddleClick(Player p) {
		p.openInventory(getInventory(1, p));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void reset() {
		if(isEnabled()) {
			setOption(cfg, "maxhearths.max", 20);
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.setMaxHealth((int) getOption(cfg, "maxhearths.max", 20));
			}
		}
	}

	@Override
	public void ifPlayerDies() {

	}

	public ChallengesMainClass getPlugin() {
		return plugin;
	}

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
		if(isToggled()) {
			itemdisplayname = "§6Max Herzen " + Starter.START_PREFIX + "§aOn";
		} else {
			itemdisplayname = "§6Max Herzen " + Starter.START_PREFIX + "§cOff";
		}

		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(plusMaxHearth1) && item.getType() == Material.STONE_BUTTON) {
			setOption(cfg, "maxhearths.max", (int)getOption(cfg, "maxhearths.max", 20) + 1);
			p.openInventory(getInventory(page, p));
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(minusMaxHeath1) && item.getType() == Material.STONE_BUTTON) {
			if((int)getOption(cfg, "maxhearths.max", 20) > 1) {
				setOption(cfg, "maxhearths.max", (int) getOption(cfg, "maxhearths.max", 20) - 1);
				p.openInventory(getInventory(page, p));
			}
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(itemdisplayname)) {
			toggle();
			p.openInventory(getInventory(page, p));
		}

	}

	String plusMaxHearth1 = "§6Füge 0,5 Herzen hinzu",
	       minusMaxHeath1 = "§6Lösche 0,5 Herzen",
			itemdisplayname = "";

	@Override
	public Inventory getInventory(int page, Player p) {
		org.bukkit.inventory.Inventory inv = getInv();
		inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);

		ArrayList<ItemStack> items = new ArrayList<>();

		ItemBuilder ib = new ItemBuilder(Material.REDSTONE_BLOCK);
		if(isToggled()) {
			itemdisplayname = "§6Max Herzen " + Starter.START_PREFIX + "§aOn";
		} else {
			itemdisplayname = "§6Max Herzen " + Starter.START_PREFIX + "§cOff";
		}
		ib.setDisplayName(itemdisplayname);

		inv.setItem(0, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(plusMaxHearth1).build());
		inv.setItem(9, ib.build());
		inv.setItem(18, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(minusMaxHeath1).build());

		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK()).build());

		return getPage(items, inv, page);
	}

	public void setPlugin(ChallengesMainClass plugin) {
		this.plugin = plugin;
	}
	

}
