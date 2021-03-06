package ca.damocles.enchantsystem;

import java.util.HashMap;
import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ca.damocles.itemblueprints.Weapon;
import ca.damocles.itemsystem.ItemHandler;
import ca.damocles.itemsystem.ItemType;
import ca.damocles.itemsystem.ItemType.ItemTypes;

public class BloodThirst implements Listener{
	ItemHandler itemhandler = new ItemHandler();
	
	@EventHandler
	public void bloodThirstTrigger(EntityDeathEvent event){
		if(event.getEntity() instanceof LivingEntity){
			if(event.getEntity().getKiller() != null){
				Player player = event.getEntity().getKiller();
				
				if(player.getInventory().getItemInMainHand() != null){
					ItemStack item = player.getInventory().getItemInMainHand();
					if(new ItemType(item).getType().equals(ItemTypes.WEAPON)){
						Weapon detailItem = new Weapon(item);
						HashMap<Enchantments, Integer> enchantments = detailItem.getEnchantsAndLevel();
							if(enchantments.containsKey(Enchantments.BLOODTHIRST)){
								int chance = 100;
								int rand = new Random().nextInt(100);
								@SuppressWarnings("unused")
								int level = enchantments.get(Enchantments.BLOODTHIRST);
								if((rand <= chance)){
									player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 120, 0));
								}else{
									return;
								}
							}
							return;
					}
				}
				
			}
		}
	}
	
}
