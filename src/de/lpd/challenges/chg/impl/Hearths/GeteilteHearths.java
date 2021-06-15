package de.lpd.challenges.chg.impl.Hearths;

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
				if(isEnabled()) {
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
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.HEART_OF_THE_SEA, 2);
		ib.setDisplayName("§6GeteilteHerzen");
		String[] lore = new String[4];
		lore[0] = Starter.START_PREFIX + "§aIn dieser Challenge muss man Minecraft mit";
		lore[1] = "§ageteilten Herzen durspielen. Das hei§t. Wenn Spieler 1 Damage";
		lore[2] = "§abekommt, bekommt der Rest auch Damage.";
		lore[3] = "§6Mittelklick §7> §aInventar aufmachen";
		
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
	public void ifPlayerDies() {
		setOption(cfg, "geteilteherzen.herzen", (double)getOption(cfg, "geteilteherzen.herzen", 20.00));
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (isEnabled()) {
				setOption(cfg, "geteilteherzen.herzen", (double) getOption(cfg, "geteilteherzen.herzen", 20.00) - (double) (e.getDamage() * 2));
			}
		}
	}

	@EventHandler
	public void onRegenerate(EntityRegainHealthEvent e) {
		if(e.getEntity() instanceof Player) {
			if(isEnabled()) {
				setOption(cfg, "geteilteherzen.herzen", (double)getOption(cfg, "geteilteherzen.herzen", 20.00) + (double)e.getAmount());
			}
		}
	}

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
        if(isToggled()) {
            itemdisplayname = "§6Geteilte Herzen " + Starter.START_PREFIX.replace("§r", "") + "§aOn";
        } else {
            itemdisplayname = "§6Geteilte Herzen " + Starter.START_PREFIX.replace("§r", "") + "§cOff";
        }
		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(itemdisplayname)) {
			toggle();
		}
		p.openInventory(getInventory(page, p));
	}

	String itemdisplayname;

	@Override
	public Inventory getInventory(int page, Player p) {
		inv = placeHolder(inv);

		ArrayList<ItemStack> items = new ArrayList<>();
		ItemBuilder ib = new ItemBuilder(Material.REDSTONE_BLOCK);
		if(isToggled()) {
			ib = new ItemBuilder(Material.EMERALD_BLOCK);
			itemdisplayname = "§6Geteilte Herzen " + Starter.START_PREFIX + "§aOn";
		} else {
			ib = new ItemBuilder(Material.REDSTONE_BLOCK);
			itemdisplayname = "§6Geteilte Herzen " + Starter.START_PREFIX + "§cOff";
		}
		ib.setDisplayName(itemdisplayname);

		inv.setItem(9, ib.build());
		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK()).build());

		return getPage(items, inv, page);
	}
}
