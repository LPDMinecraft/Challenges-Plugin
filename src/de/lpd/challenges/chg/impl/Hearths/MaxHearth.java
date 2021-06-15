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
		super(plugin, "maxhearth", "config.yml", "maxhearth", 3*9, true, "Max Herzen", "chmenu", "challenge-maxhearths", "Challenges Menu");
		this.setPlugin(plugin);
		
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				
				if(isEnabled()) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(p.getMaxHealth() != (double) getOption(cfg, "maxhearths.max", 20.00)) {
							p.setMaxHealth((double) getOption(cfg, "maxhearths.max", 20.00));
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
		ib.setDisplayName("§6MaxLeben");
		String[] lore = new String[3];
		lore[0] = Starter.START_PREFIX + "§aIn dieser Challenge muss man Minecraft mit";
		lore[1] = "§aX Herzen durchspielen.";
		lore[2] = "§6Mittelklick §7> §aÖffne Inventar";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		/*setOption(cfg, "maxhearths.max", (double)getOption(cfg, "maxhearths.max", 20) + 1);*/

	}

	@Override
	public void onLeftClick(Player p) {
		/*if((double)getOption(cfg, "maxhearths.max", 20) > 1) {
			setOption(cfg, "maxhearths.max", (double)getOption(cfg, "maxhearths.max", 20) - 1);
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
				p.setMaxHealth((double) getOption(cfg, "maxhearths.max", 20.00));
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
		namei = "§6Max Hearth(" + isToggled() + "): " + ((double)getOption(cfg, "maxhearths.max", 20.00) / 2) + " Herzen sind das maximum";

		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(plusMaxHearth1) && item.getType() == Material.STONE_BUTTON) {
			setOption(cfg, "maxhearths.max", (double)getOption(cfg, "maxhearths.max", 20.00) + 0.5);
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(minusMaxHeath1) && item.getType() == Material.STONE_BUTTON) {
			if((double)getOption(cfg, "maxhearths.max", 20) > 1) {
				setOption(cfg, "maxhearths.max", (double) getOption(cfg, "maxhearths.max", 20.00) - 0.5);
			}
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(namei)) {
			toggle();
		}
		p.openInventory(getInventory(page, p));
	}

	String plusMaxHearth1 = "§6Füge 0,5 Herzen hinzu",
	       minusMaxHeath1 = "§6Lösche 0,5 Herzen",
			namei= "";

	@Override
	public Inventory getInventory(int page, Player p) {
		inv = de.lpd.challenges.invs.Inventory.placeHolder(inv);

		ArrayList<ItemStack> items = new ArrayList<>();

		Material ib;
		if(isToggled()) {
			ib = Material.EMERALD_BLOCK;
		} else {
			ib = Material.REDSTONE_BLOCK;
		}

		inv.setItem(0, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(plusMaxHearth1).build());
		inv.setItem(9, new ItemBuilder(ib).setDisplayName("§6Max Hearth(" + isToggled() + "): " + ((double)getOption(cfg, "maxhearths.max", 20.00) / 2) + " Herzen sind das maximum").build());
		inv.setItem(18, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(minusMaxHeath1).build());

		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK()).build());

		return getPage(items, inv, page);
	}

	public void setPlugin(ChallengesMainClass plugin) {
		this.plugin = plugin;
	}
	

}
