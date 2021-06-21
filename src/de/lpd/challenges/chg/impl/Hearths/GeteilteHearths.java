package de.lpd.challenges.chg.impl.Hearths;

import de.lpd.challenges.languages.LanguagesManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

import java.util.ArrayList;

public class GeteilteHearths extends Challenge {
	
	public Config cfg;

	public GeteilteHearths(ChallengesMainClass plugin) {
		super(plugin, "geteilteherzen", "config.yml", "geteiltehearths", 3*9, true, "GeteilteHerzen", "chmenu", "challenge-geteilteherzen", "Challenges Menu");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				if(isEnabled("default")) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(p.getGameMode() == GameMode.SURVIVAL) {
							double h = (double)getOption(cfg, "geteilteherzen.herzen", 20.00);
							if(h > p.getMaxHealth()) {
								setOption(cfg, "geteilteherzen.herzen", p.getMaxHealth());
							}
							h = (double)getOption(cfg, "geteilteherzen.herzen", 20.00);
							p.setHealth(h);
						}
					}
				}
			}
			
		}, 1l, 1l);
	}

	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
	}

	@Override
	public ItemStack getItem(Player p) {
		ItemBuilder ib = new ItemBuilder(Material.HEART_OF_THE_SEA, 2);
		ib.setDisplayName(LanguagesManager.translate("§6GeteilteHerzen", p.getUniqueId()));
		String[] lore = new String[4];
		lore[0] = Starter.START_PREFIX + LanguagesManager.translate("§aIn dieser Challenge muss man Minecraft mit", p.getUniqueId());
		lore[1] = LanguagesManager.translate("§ageteilten Herzen durspielen. Das hei§t. Wenn Spieler 1 Damage", p.getUniqueId());
		lore[2] = LanguagesManager.translate("§abekommt, bekommt der Rest auch Damage.", p.getUniqueId());
		lore[3] = LanguagesManager.translate("§6Mittelklick §7> §aInventar aufmachen", p.getUniqueId());
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {}

	@Override
	public void onLeftClick(Player p) {}

	@Override
	public void onMiddleClick(Player p) {
		p.openInventory(getInventory(1, p));
	}

	@Override
	public void reset() {
		setOption(cfg, "geteilteherzen.herzen", (double)getOption(cfg, "geteilteherzen.herzen", 20.00));
	}

	@Override
	public void ifPlayerDies(Player p) {
		setOption(cfg, "geteilteherzen.herzen", (double)getOption(cfg, "geteilteherzen.herzen", 20.00));
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (isEnabled("default")) {
				setOption(cfg, "geteilteherzen.herzen", (double) getOption(cfg, "geteilteherzen.herzen", 20.00) - (double) (e.getDamage() * 2));
			}
		}
	}

	@EventHandler
	public void onRegenerate(EntityRegainHealthEvent e) {
		if(e.getEntity() instanceof Player) {
			if(isEnabled("default")) {
				setOption(cfg, "geteilteherzen.herzen", (double)getOption(cfg, "geteilteherzen.herzen", 20.00) + (double)e.getAmount());
			}
		}
	}

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
        if(isToggled("default")) {
			itemdisplayname = LanguagesManager.translate("§6Geteilte Herzen " + Starter.START_PREFIX + "§aOn", p.getUniqueId());
        } else {
			itemdisplayname = LanguagesManager.translate("§6Geteilte Herzen " + Starter.START_PREFIX + "§cOff", p.getUniqueId());
        }
		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(itemdisplayname)) {
			toggle("default");
		}
	}

	String itemdisplayname;

	@Override
	public Inventory getInventory(int page, Player p) {
		inv = placeHolder(inv);

		ArrayList<ItemStack> items = new ArrayList<>();
		ItemBuilder ib = new ItemBuilder(Material.REDSTONE_BLOCK);
		if(isToggled("default")) {
			ib = new ItemBuilder(Material.EMERALD_BLOCK);
			itemdisplayname = LanguagesManager.translate("§6Geteilte Herzen " + Starter.START_PREFIX + "§aOn", p.getUniqueId());
		} else {
			ib = new ItemBuilder(Material.REDSTONE_BLOCK);
			itemdisplayname = LanguagesManager.translate("§6Geteilte Herzen " + Starter.START_PREFIX + "§cOff", p.getUniqueId());
		}
		ib.setDisplayName(itemdisplayname);

		inv.setItem(9, ib.build());
		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK(p.getUniqueId())).build());

		return getPage(items, inv, page, 0);
	}
}
