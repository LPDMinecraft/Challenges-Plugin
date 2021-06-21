package de.lpd.challenges.chg.impl.BreakUpgradeTool;

import de.lpd.challenges.languages.LanguagesManager;
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
import java.util.HashMap;

public class BreakUpgradeTool extends Challenge {

	private Config cfg;
	
	public BreakUpgradeTool(ChallengesMainClass plugin) {
		super(plugin, "breakupgradetool", "config.yml", "breakupgradetool", 3*9, true, "Break Upgrade Tool", "chmenu", "challenge-breakupgradetool", "Challenges Menu");
	}

	@Override
	public void cfg(Config cfg) {
		this.cfg = cfg;
	}

	@Override
	public ItemStack getItem(Player p) {
		ItemBuilder ib = new ItemBuilder(Material.ENCHANTED_BOOK);
		ib.setDisplayName(LanguagesManager.translate("§6Entchante jedes Abbauen", p.getUniqueId()));
		String[] lore = new String[4];
		lore[0] = Starter.START_PREFIX + LanguagesManager.translate("§aIn dieser Challenge muss man Minecraft", p.getUniqueId());
		lore[1] = LanguagesManager.translate("§adurchspielen. Bei jedem Block abbauen wird es um eine belibige", p.getUniqueId());
		lore[2] = LanguagesManager.translate("§aZahl Entchantmens hochgelevelt auf das Tool.", p.getUniqueId());
		lore[3] = LanguagesManager.translate("§6Mittelklick §7> §aÖffne das Inventart", p.getUniqueId());
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		/*double level = (double) getOption(cfg, "breakupgradetool.levelplus", 1);
		level++;
		setOption(cfg, "breakupgradetool.levelplus", level);*/
	}

	@Override
	public void onLeftClick(Player p) {
		/*double level = (double) getOption(cfg, "breakupgradetool.levelplus", 1);
		level--;
		setOption(cfg, "breakupgradetool.levelplus", level);*/
	}

	@Override
	public void onMiddleClick(Player p) {
		p.openInventory(getInventory(1, p));
	}

	@Override
	public void reset() {
		setOption(cfg, "breakupgradetool.levelplus", 1.00);
	}

	@Override
	public void ifPlayerDies(Player p2) {

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

	private HashMap<Player, Integer> fix = new HashMap<>();

	public ItemStack entchant(ItemStack item, Player p) {
		int fix1 = 0;
		if(fix.containsKey(p)) {
			fix1 = fix.get(p);
			fix.remove(p);
		}
		fix1++;
		fix.put(p, fix1);
		if(fix1 > 1) {
			fix.remove(p);
			ItemMeta iMeta = item.getItemMeta();
			int i = getRandomNumber(0, Enchantment.values().length - 1);

			if(Enchantment.values()[i].equals(Enchantment.BINDING_CURSE) || Enchantment.values()[i].equals(Enchantment.VANISHING_CURSE) || Enchantment.values()[i].equals(Enchantment.SILK_TOUCH)) {
				return entchant(item, p);
			}

			double level = (double)getOption(cfg, "breakupgradetool.levelplus", 1.00);
			if(iMeta.hasEnchant(Enchantment.values()[i])) {
				level = iMeta.getEnchantLevel(Enchantment.values()[i]);
				level = level + (double) getOption(cfg, "breakupgradetool.levelplus", 1.00);
				iMeta.removeEnchant(Enchantment.values()[i]);
			}
			iMeta.addEnchant(Enchantment.values()[i], Math.round(Math.round(level)), true);
			item.setItemMeta(iMeta);
			return item;
		}
		return null;
	}

	public int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	String plusMaxHearth1,
			minusMaxHeath1,
	        namei;

	@Override
	public void onClickOnItemEvent(Player p, ItemStack item, InventoryClickEvent e, int page) {
		namei = LanguagesManager.translate("§aEntchante jedes Abbauen(" + isToggled() + "):", p.getUniqueId()) + "§6" + getOption(cfg, "breakupgradetool.levelplus", 1.00) + " Level";

		plusMaxHearth1 = LanguagesManager.translate("§6+1 Entchantment Level", p.getUniqueId());
		minusMaxHeath1 = LanguagesManager.translate("§6-1 Entchantment Level", p.getUniqueId());

		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(plusMaxHearth1)) {
			double level = Double.valueOf(String.valueOf(getOption(cfg, "breakupgradetool.levelplus", 1.00)));
			level = level + 0.5;
			setOption(cfg, "breakupgradetool.levelplus", level);
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(minusMaxHeath1)) {
			double level = Double.valueOf(String.valueOf(getOption(cfg, "breakupgradetool.levelplus", 1.00)));
			level = level - 0.5;
			setOption(cfg, "breakupgradetool.levelplus", level);
		} else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(namei)) {
			toggle();
		}
	}

	@Override
	public Inventory getInventory(int page, Player p) {
		inv = placeHolder(inv);
		ArrayList<ItemStack> items = new ArrayList<>();

		plusMaxHearth1 = LanguagesManager.translate("§6+1 Entchantment Level", p.getUniqueId());
		minusMaxHeath1 = LanguagesManager.translate("§6-1 Entchantment Level", p.getUniqueId());
		namei = LanguagesManager.translate("§aEntchante jedes Abbauen(" + isToggled() + "):", p.getUniqueId()) + "§6" + getOption(cfg, "breakupgradetool.levelplus", 1.00) + " Level";

		inv.setItem(0, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(plusMaxHearth1).build());
		if(isToggled()) {
			inv.setItem(9, new ItemBuilder(Material.EMERALD_BLOCK).setDisplayName(namei).build());
		} else {
			inv.setItem(9, new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayName(namei).build());
		}
		inv.setItem(18, new ItemBuilder(Material.STONE_BUTTON).setDisplayName(minusMaxHeath1).build());
		inv.setItem(inv.getSize() - 1, new ItemBuilder(Material.BARRIER).setDisplayName(getITEM_BACK(p.getUniqueId())).build());

		return getPage(items, inv, page);
	}
}
