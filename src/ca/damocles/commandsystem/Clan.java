package ca.damocles.commandsystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ca.damocles.accountsystem.Account;
import ca.damocles.accountsystem.PlayerSetup;
import ca.damocles.clansystem.Clans;
import ca.damocles.menusystem.ClanMenu;
import ca.damocles.menusystem.SelectionMenu;
import ca.damocles.utils.Message;
import mkremins.fanciful.FancyMessage;

public class Clan implements CommandExecutor{
	
	Plugin plugin;
	PlayerSetup playersetup = new PlayerSetup();
	
	public Clan(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labe, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("You must be a player to use commands!");
		}
		Player player = (Player)sender;
		Account account = new Account(player);
		ca.damocles.accountsystem.Character character = account.getLoadedCharacter();
		Clans whatClan = character.getClan();
		ca.damocles.clansystem.Clan clan = new ca.damocles.clansystem.Clan(whatClan);
		if(cmd.getName().equalsIgnoreCase("clan")){
			
		      if (args.length == 0){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan choose");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan info "+ChatColor.GOLD+"[Player]"+ChatColor.YELLOW+" or "+ChatColor.GOLD+"[Clan]");
					//Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan join"+ChatColor.GOLD+" [Color]");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan promote"+ChatColor.GOLD+" [Player]");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan leave");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		            return true;
		      }
		      
		      if(args[0].equalsIgnoreCase("help")){
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan help");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan choose");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan info "+ChatColor.GOLD+"[Player]"+ChatColor.YELLOW+" or "+ChatColor.GOLD+"[Clan]");
					//Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan join"+ChatColor.GOLD+" [Color]");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan promote"+ChatColor.GOLD+" [Player]");
					Message.sendCenteredMessage(player, ChatColor.YELLOW + "/Clan leave");
					Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		            return true;
		      }
		      
		      if(args[0].equalsIgnoreCase("leave")){
		    	  if(character.isInClan()){
		    		  
		    		  if(clan.getProposed() != null){
			    		  if(clan.getProposed().getUsername().equalsIgnoreCase(character.getUsername()))
			    			  clan.setProposed(null);
		    		  }
		    		  
		    		  ca.damocles.clansystem.Rank rank = character.getClanRank();
		    		  switch(rank){
		    		  		case CLANSMEN:
							clan.removeClansmen(character);
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have left "+whatClan.toString()+" Clan!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							return true;
						case KING:
							clan.setKing(null);
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have left "+whatClan.toString()+" Clan!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							return true;
					case NONE:
						break;
					default:
						break;
		    		  }
		    		  return true;
		    	  }else{
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"You are not in a Clan!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	    			  return true;
		    	  }
		      }
		      
		      /*
		      if(args[0].equalsIgnoreCase("invite")){
		    	  if(args.length == 2){
			    	  Clans clan = Clan.getManager().getClan(player);
			    	  if(clan == Clans.UNCLANNED){
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"You are not in a Clan!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    			  return true;
			    	  }
			    	  Player target = Bukkit.getPlayer(args[1]);
			    	  if(player == target){
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"You cannot invite yourself to a Clan!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true;
			    	  }
			    	  boolean canInvite = false;
			    	  if(Clan.getManager().getKing(clan).equalsIgnoreCase(player.getUniqueId()+""))
			    		  canInvite = true;
			    	  if(Clan.getManager().getOfficer(clan).equalsIgnoreCase(player.getUniqueId()+""))
			    		  canInvite = true;
			    	  if(canInvite){
			    		  if(target != null){
			    			  Clan.getManager().addInvitedMember(target, clan);
			    			  return true;
			    		  }else{
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
								Message.sendCenteredMessage(player, ChatColor.YELLOW+"The player ( "+args[1]+" ) does not exist or is offline!");
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    			  return true;
			    		  }
			    	  }else{
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"You are not a King or an Officer!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true;
			    	  }
		    	  }else{
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid usage, check /sevenkings help for correct command usage!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
		    	  }
		      }
		      */
		      
		      if(args[0].equalsIgnoreCase("choose")){
		    	  
		    	  if(character.getLevel() >= 5){
			    	  if(!(character.isInClan())){
			    		  player.openInventory(SelectionMenu.getInstance().generateSelectionInventory());
			    		  return true;
			    	  }else{
			    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
			    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: You are already in a clan!");
			    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true;
			    	  } 
		    	  }else{
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  Message.sendCenteredMessage(player, ChatColor.BOLD + "Game Info");
		    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"You must be Level 5 to choose a clan!");
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
		    	  }
		    	  
		      }
		      
		      if(args[0].equalsIgnoreCase("accept")){
		    	  
		    	  if(whatClan.equals(Clans.UNCLANNED))
		    		  return true;
		    	  
		    	  if(clan.getProposed().getUsername().equalsIgnoreCase(character.getUsername())){
		    		  if(clan.getKing() == null){
		    			  clan.removeClansmen(character);
		    			  clan.setProposed(null);
		    			  clan.setKing(character);
		    			  for(Player target : Bukkit.getOnlinePlayers()){
								if(whatClan.equals(Clans.COLORLESS)){
									Message.sendCenteredMessage(target, ChatColor.GREEN+"----------------------------------------------------");
									Message.sendCenteredMessage(target, ChatColor.BOLD + "Game Info");
									Message.sendCenteredMessage(target, ChatColor.YELLOW+character.getUsername()+" has become the " + ChatColor.DARK_GRAY + whatClan.toString().toLowerCase().substring(0, 1).toUpperCase() + whatClan.toString().toLowerCase().substring(1) + ChatColor.YELLOW + " King!");
									Message.sendCenteredMessage(target, ChatColor.GREEN+"----------------------------------------------------");
								}else{
									Message.sendCenteredMessage(target, ChatColor.GREEN+"----------------------------------------------------");
									Message.sendCenteredMessage(target, ChatColor.BOLD + "Game Info");
									Message.sendCenteredMessage(target, ChatColor.YELLOW+character.getUsername()+" has become the " + ChatColor.valueOf(whatClan.toString().toUpperCase()) + whatClan.toString().toLowerCase().substring(0, 1).toUpperCase() + whatClan.toString().toLowerCase().substring(1) + ChatColor.YELLOW + " King!");
									Message.sendCenteredMessage(target, ChatColor.GREEN+"----------------------------------------------------");
								}
							}
		    		  }
		    		  return true;
		    	  }
		    	  
		      }
		      
		      if(args[0].equalsIgnoreCase("reject")){
		    	  
		    	  if(whatClan.equals(Clans.UNCLANNED))
		    		  return true;
		    	  
		    	  if(clan.getProposed().getUsername().equalsIgnoreCase(character.getUsername())){
		    		  if(clan.getKing() == null){
		    			  clan.addRejected(character);
		    			  clan.setProposed(null);
		    			  if(whatClan.equals(Clans.COLORLESS)){
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								Message.sendCenteredMessage(player, ChatColor.BOLD + "Game Info");
								Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have rejected becoming the " + ChatColor.DARK_GRAY + whatClan.toString().toLowerCase().substring(0, 1).toUpperCase() + whatClan.toString().toLowerCase().substring(1) + ChatColor.YELLOW + " King!");
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							}else{
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
								Message.sendCenteredMessage(player, ChatColor.BOLD + "Game Info");
								Message.sendCenteredMessage(player, ChatColor.YELLOW+"You have rejected becoming the " + ChatColor.valueOf(whatClan.toString().toUpperCase()) + whatClan.toString().toLowerCase().substring(0, 1).toUpperCase() + whatClan.toString().toLowerCase().substring(1) + ChatColor.YELLOW + " King!");
								Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							}
		    		  }
		    		  return true;
		    	  }
		    	  
		      }
		      
		      /*
		      if(args[0].equalsIgnoreCase("kick")){
		    	  if(args.length == 2){
			    	  if(Clan.getManager().isInClan(player)){
			    		  if(Clan.getManager().getRank(player) == Rank.KING || Clan.getManager().getRank(player) == Rank.OFFICER){
			    			  Player target = Bukkit.getPlayer(args[1]);
			    			  if(target != null){
			    				  if(Clan.getManager().getClan(target).equals(Clan.getManager().getClan(player))){
			    					  if(Clan.getManager().getRank(target) == Rank.CLANSMEN){
			    						  Clan.getManager().removeClanMember(target, Clan.getManager().getClan(player));
			    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
			    							Message.sendCenteredMessage(player, ChatColor.YELLOW+"INFO: You have kicked "+args[1]+" from the Clan!");
			    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    							Message.sendCenteredMessage(target, ChatColor.GREEN+"----------------------------------------------------");
			    							Message.sendCenteredMessage(target, ChatColor.BOLD + "Clan Info");
			    							Message.sendCenteredMessage(target, ChatColor.YELLOW+"INFO: You have been kicked out of your clan!");
			    							Message.sendCenteredMessage(target, ChatColor.GREEN+"----------------------------------------------------");
			    			    		  return true;
			    					  }else{
			    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
			    							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: A King/Officer cannot kick a King/Officer!");
			    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    			    		  return true;
			    					  }
			    				  }else{
		    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
		    							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: "+ args[1]+" is not in your clan.");
		    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		    		  return true;
			    				  }
			    			  }else{
	    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	    							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
	    							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: Player " + args[1] +" does not exist or is offline.");
	    							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
					    		  return true;
			    			  }
			    		  }else{
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: Only the King/Officer can kick people!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				    		  return true;
			    		  }
			    	  }else{
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: You are not in a Clan!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true;
			    	  }
		    	  }else{
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: Invalid usage, check /sevenkings help for correct command usage!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
		    	  }
		      }
		      */
		     
		      if(args[0].equalsIgnoreCase("join")){
		    	  if(args.length == 2){
			    	  if(!(character.isInClan())){
			    		  try{
			    			  Clans clanchoice = Clans.valueOf(args[1].toUpperCase());
			    			  ca.damocles.clansystem.Clan theClan = new ca.damocles.clansystem.Clan(clanchoice);
			    			  	theClan.addClansmen(character);
	    						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	    						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
	    						Message.sendCenteredMessage(player, ChatColor.YELLOW+"INFO: You have joined the "+args[1]+" Clan!");
	    						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
	    						return true;
			    		  }catch(Exception IllegalArgumentException){
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: "+args[1]+ " is not a valid Clan!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
				    		  return true;
			    		  }
			    	  }else{
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
							Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
							Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: You are already in a Clan!");
							Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
			    		  return true; 
			    	  }  
		    	  }else{
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
						Message.sendCenteredMessage(player, ChatColor.BOLD + "Clan Info");
						Message.sendCenteredMessage(player, ChatColor.YELLOW+"ERROR: Invalid usage, check /sevenkings help for correct command usage!");
						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
		    	  }
		      }
	
		      
		      if(args[0].equalsIgnoreCase("info")){
		    	  if(args.length == 2){
		    		  for(Clans clans : Clans.values()){
		    			  if(!clan.equals(Clans.UNCLANNED)){
		    				  if(args[1].equalsIgnoreCase(clans.toString().toLowerCase())){
		    					  player.openInventory(ClanMenu.generateClanMenu(clans));
		  						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    					  new FancyMessage("You have opened the ")
		    					  .color(ChatColor.YELLOW)
		    					  .then(clans.toString().toLowerCase())
		    					  .color(ChatColor.valueOf(clans.toString().toUpperCase()))
		    					  .then("'s Clan Menu. Click ")
		    					  .color(ChatColor.YELLOW)
		    					  .then("here")
		    					  .command("/clan info "+args[1])
		    					  .color(ChatColor.GOLD)
		    					  .style(ChatColor.BOLD)
		    					  .then(" to reopen the menu.")
		    					  .color(ChatColor.YELLOW)
		    					  .send(player);
		  						Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    					  
		    				  }
		    			  }
		    		  }
		    		  //Get player menu here.
		    	  }else{
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  Message.sendCenteredMessage(player, ChatColor.YELLOW+"Invalid Arguemnts for proper usage use /Help");
		    		  Message.sendCenteredMessage(player, ChatColor.GREEN+"----------------------------------------------------");
		    		  return true;
		    	  }
		      }
			
		}
		return true;
	}
}
