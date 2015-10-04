package com.weeryan17.snp.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.weeryan17.snp.Main;

public class ClanCommand implements CommandExecutor {
	private Main instance;
	
	public ClanCommand(Main instance){
		this.instance = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if(cmd.getName().equalsIgnoreCase("clan")){
			String playerName = sender.getName();
			if(args.length == 0){
				sender.sendMessage(ChatColor.RED + "[SNP Help]");
				sender.sendMessage(ChatColor.YELLOW + "/clan list");
				sender.sendMessage(ChatColor.YELLOW + "   list all clans and wether there open or not");
				sender.sendMessage(ChatColor.YELLOW + "/clan join <name>, or /clan join <player> <name>");
				sender.sendMessage(ChatColor.YELLOW + "   makes you join the clan if it is open, or requests you to join the clan. ");
				sender.sendMessage(ChatColor.YELLOW + "   if you have the permision or you're op you can force another person to join the clan wether open or closed");
				sender.sendMessage(ChatColor.YELLOW + "/clan create <name>");
				sender.sendMessage(ChatColor.YELLOW + "   creates a new clan with the specifyed name");
				sender.sendMessage(ChatColor.YELLOW + "/clan options");
				sender.sendMessage(ChatColor.YELLOW + "   brings up the clan options that you can set if you made the clan");
			} else {
			if(sender instanceof Player){
				String race = this.instance.getConfig().getString("Players." + playerName + ".type");
			if(args.length == 1 && args[0].equals("list")){
	            List<String> list = new ArrayList<String>();
				ConfigurationSection Race = this.instance.getConfig().getConfigurationSection("Clans." + race + ".Clans");
		        for (String key : Race.getKeys(false)) {
		        	String configKey = "." + key;
		            boolean open = this.instance.getConfig().getBoolean("Clans." + race + ".Clans" + configKey + ".Open");
		            if(open == true){
		            	String clan = ChatColor.BLUE + key + ChatColor.AQUA + "[Open]";
		            	list.add(clan);
		            } else {
		            	String clan = ChatColor.BLUE + key + ChatColor.AQUA;
		            	list.add(clan);
		            }
		            
			}
		        String stringlist0 = list.toString();
		        String stringlist1 = Main.removeCharAt(stringlist0, 0);
		        int length = stringlist1.length() - 1;
		        String stringlist2 = Main.removeCharAt(stringlist1, length);
		        sender.sendMessage(ChatColor.RED + "[Clan list]");
		        sender.sendMessage(stringlist2);
		} else if(args.length == 1 && args[0].equals("join")){
				sender.sendMessage(ChatColor.YELLOW + "Please specify a clan name.");
			} else if(args.length == 2 && args[0].equals("join")){
				List<String> list = new ArrayList<String>();
				Map<String, Boolean> map = new HashMap<String, Boolean>();
				ConfigurationSection Race = this.instance.getConfig().getConfigurationSection("Clans." + race + ".Clans");
		        for (String key : Race.getKeys(false)) {
		        	String configKey = "." + key;
		        	boolean open = this.instance.getConfig().getBoolean("Clans." + race + ".Clans" + configKey + ".Open");
		        	list.add(key);
		        	map.put(key, open);
		        }
		        if(list.contains(args[0])){
		        	if(map.get(args[0].toString()) == true){
		        		this.instance.getConfig().set("Players." + playerName + ".Clan", args[0].toString());
		        	} else {
		        		String configKey = "." + args[0];
		        		sender.sendMessage(ChatColor.YELLOW + "This clan isn't open so a request to join the clan has been sent to the owner");
		        		this.instance.getConfig().set("Players." + playerName + ".requesting", args[0]);
		        		String owner = this.instance.getConfig().getString("Clans." + race + ".Clans" + configKey + ".Open");
		        		Player playerOwner = Bukkit.getPlayer(owner);
		        		playerOwner.sendMessage(ChatColor.YELLOW + playerName + " is trying to join your clan.");
		        		playerOwner.sendMessage(ChatColor.YELLOW + "You can accept them in by doing /clan accept " + playerName);
		        	}
		        } else {
		        	sender.sendMessage(ChatColor.YELLOW + "That clan doesn't exist");
		        }
			}
		}
		
	}
		}
		return false;
	}

}
