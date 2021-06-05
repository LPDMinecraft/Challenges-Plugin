package de.lpd.challenges.chg.impl.BreakUpgradeTool;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.lpd.challenges.chg.Challenge;
import de.lpd.challenges.main.ChallengesMainClass;
import de.lpd.challenges.utils.Config;
import de.lpd.challenges.utils.ItemBuilder;
import de.lpd.challenges.utils.Starter;

public class BreakUpgradeTool extends Challenge {
	
	int level = 1;
	private Config cfg;
	
	public BreakUpgradeTool(ChallengesMainClass plugin) {
		super(plugin, "breakupgradetool", "config.yml", "breakupgradetool");
	}

	@Override
	public void cfg(Config cfg) {
		level = (int) getOption(cfg, "breakupgradetool.levelplus", 1);
		this.cfg = cfg;
	}

	@Override
	public ItemStack getItem() {
		ItemBuilder ib = new ItemBuilder(Material.ENCHANTED_BOOK);
		if(isToggled()) {
			ib.setDisplayName("§6Entchante jedes Abbauen " + Starter.STARTPREFIX + "§aOn");
		} else {
			ib.setDisplayName("§6Entchante jedes Abbauen " + Starter.STARTPREFIX + "§cOff");
		}
		String[] lore = new String[7];
		lore[0] = Starter.STARTPREFIX + "§aIn dieser Challenge muss man Minecraft";
		lore[1] = "§adurchspielen. Bei jedem Block abbauen wird es um eine belibige";
		lore[2] = "§aZahl Entchantmens hochgelevelt auf das Tool.";
		lore[3] = "§aDerzeitige Level pro Abbauen§7: §6" + level;
		lore[4] = "§6Mittelklick §7> §aAn/Aus diese Challenge";
		lore[5] = "§6Rechtsklick §7> §a+1 mehr Level";
		lore[6] = "§6Linksklick §7> §a-1 mehr Level";
		
		ib.setLoreString(lore);
		return ib.build();
	}

	@Override
	public void onRightClick(Player p) {
		level++;
		setOption(cfg, "breakupgradetool.levelplus", level);
	}

	@Override
	public void onLeftClick(Player p) {
		level--;
		setOption(cfg, "breakupgradetool.levelplus", level);
	}

	@Override
	public void onMiddleClick(Player p) {
		toggle();
	}

	@Override
	public void reset() {
		level = 1;
		setOption(cfg, "breakupgradetool.levelplus", level);
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
		
		int level = this.level;
		if(iMeta.hasEnchant(Enchantment.values()[i])) {
			level = iMeta.getEnchantLevel(Enchantment.values()[i]);
			level = level + this.level;
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
	
}
