package de.lpd.challenges.chg.impl.BreakUpgradeTool;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

import java.util.ArrayList;

public class BreakUpgradeTool extends Challenge {

	private Config cfg;
	
	public BreakUpgradeTool(ChallengesMainClass plugin) {
		super(plugin, "breakupgradetool", "config.yml", "breakupgradetool", 3*9, true, "Break Upgrade Tool", "chmenu", "challenge-breakupgradetool", "Challenges Menu");
	}

	@Override
	public void cfg(Config cfg) {
		getOption(cfg, "breakupgradetool.levelplus", 1);
		this.cfg = cfg;
	}

	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.ENCHANTED_BOOK);
		ib.setDisplayName("§6Entchante jedes Abbauen");
		String[] lore = new String[4];
		lore[0] = Starter.START_PREFIX + "§aIn dieser Challenge muss man Minecraft";
		lore[1] = "§adurchspielen. Bei jedem Block abbauen wird es um eine belibige";
		lore[2] = "§aZahl Entchantmens hochgelevelt auf das Tool.";
		lore[3] = "§6Mittelklick §7> §aÖffne das Inventart";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		/*int level = (int) getOption(cfg, "breakupgradetool.levelplus", 1);
		level++;
		setOption(cfg, "breakupgradetool.levelplus", level);*/
	}

	@Override
	public void onLeftClick(Player p) {
		/*int level = (int) getOption(cfg, "breakupgradetool.levelplus", 1);
		level--;
		setOption(cfg, "breakupgradetool.levelplus", level);*/
		toggle();
	}

	@Override
	public void onMiddleClick(Player p) {
		p.openInventory(getInventory(1, p));
	}

	@Override
	public void reset() {
		setOption(cfg, "breakupgradetool.levelplus", 1);
	}

	@Override
	public void ifPlayerDies() {

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(isEnabled()) {
			if(e.getPlayer() != null && e.getPlayer().getItemInHand() != null) {
				entchant(e.getPlayer().getItemInHand(), e.getPlayer());
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(isEnabled() && e.getDamager() != null) {
			if(e.getDamager() instanceof Player) {
				if(((HumanEntity) e.getDamager()).getItemInHand() != null) {
					entchant(((Player) e.getDamager()).getItemInHand(), (Player) e.getDamager());
				}
			}
		}
	}
	
	public ItemStack entchant(ItemStack item, Player p) {
		ItemMeta iMeta = item.getItemMeta();
		int i = getRandomNumber(0, Enchantment.values().length - 1);
		
		if(Enchantment.values()[i].equals(Enchantment.BINDING_CURSE) || Enchantment.values()[i].equals(Enchantment.VANISHING_CURSE) || Enchantment.values()[i].equals(Enchantment.SILK_TOUCH)) {
			return entchant(item, p);
		}
		
		int level = (int) getOption(cfg, "breakupgradetool.levelplus", 1);
		if(iMeta.hasEnchant(Enchantment.values()[i])) {
			level = iMeta.getEnchantLevel(Enchantment.values()[i]);
			level = level + (int) getOption(cfg, "breakupgradetool.levelplus", 1);
			iMeta.removeEnchant(Enchantment.values()[i]);
		}
		iMeta.addEnchant(Enchantment.values()[i], level, true);
		System.out.println(level);
		item.setItemMeta(iMeta);
		return item;
	}

	public int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	String plusMaxHearth1 = "§6+1 Entchantment Level",
			minusMaxHeath1 = "§6-1 Entchantment Level",
	        namei;

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
		namei = "§aEntchante jedes Abbauen(" + isToggled() + "): §6" + getOption(cfg, "breakupgradetool.levelplus", 1) + " Level";

		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(plusMaxHearth1)) {
			int level = (int) getOption(cfg, "breakupgradetool.levelplus", 1);
			level++;
			setOption(cfg, "breakupgradetool.levelplus", level);
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(minusMaxHeath1)) {
			int level = (int) getOption(cfg, "breakupgradetool.levelplus", 1);
			level--;
			setOption(cfg, "breakupgradetool.levelplus", level);
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(namei)) {
			toggle();
		}
	}

	@Override
	public Inventory getInventory(int page, Player p) {
		inv = placeHolder(inv);

		ArrayList<ItemStack> items = new ArrayList<>();

		namei = "§aEntchante jedes Abbauen(" + isToggled() + "): §6" + getOption(cfg, "breakupgradetool.levelplus", 1) + " Level";

		inv.setItem(0, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(plusMaxHearth1).build());
		if(isToggled()) {
			inv.setItem(9, new ItemBuilder(Material.EMERALD_BLOCK).setDisplayName(namei).build());
		} else {
			inv.setItem(9, new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName(namei).build());
		}
		inv.setItem(18, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(minusMaxHeath1).build());

		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK()).build());

		return getPage(items, inv, page);
	}
}
