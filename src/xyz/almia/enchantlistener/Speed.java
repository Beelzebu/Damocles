package xyz.almia.enchantlistener;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import xyz.almia.cardinalsystem.Cardinal;
import xyz.almia.itemsystem.Armor;
import xyz.almia.itemsystem.EnchantTypes;
import xyz.almia.itemsystem.Enchantments;
import xyz.almia.itemsystem.ItemHandler;
import xyz.almia.utils.RomanNumerals;

public class Speed {
	
	private Cardinal cardinal = new Cardinal();
	Plugin plugin = cardinal.getPlugin();
	ItemHandler itemhandler = new ItemHandler();
	
	public Speed() {}
	
	public void checkForSpeedEnchant(){
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()){
					if(player.getInventory().getBoots() != null){
						ItemStack item = player.getInventory().getBoots();
						if(itemhandler.getEnchantType(item).equals(EnchantTypes.BOOTS)){
							Armor detailItem = new Armor(item);
							if(detailItem.getEnchants() != null){
								List<Enchantments> enchantments = detailItem.getEnchants();
								if(enchantments.contains(Enchantments.SPEED)){
									int amp = 0;
									ItemMeta im = item.getItemMeta();
									String[] enchantAndDamage = null;
									for(String s : im.getLore()){
										if(s.contains(ChatColor.GRAY + "Speed ")){
											enchantAndDamage = s.split(" ");
										}
									}
									int level = RomanNumerals.romanToInt(enchantAndDamage[1]);
									if(level == 1){
										amp = 0;
									}else if(level == 2){
										amp = 1;
									}else if(level == 3){
										amp = 2;
									}
										player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, amp));
								}
							}
						}
					}
				}
			}
			
        }, 0, 1);
	}
	
}
