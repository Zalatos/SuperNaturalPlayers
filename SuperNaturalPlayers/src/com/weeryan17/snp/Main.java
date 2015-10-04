package com.weeryan17.snp;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.weeryan17.snp.Commands.ClanCommand;
import com.weeryan17.snp.Commands.ClassCommand;
import com.weeryan17.snp.Commands.Howl;
import com.weeryan17.snp.Commands.MainCommand;
import com.weeryan17.snp.Commands.MobCommand;
import com.weeryan17.snp.Commands.VampBatCommand;
import com.weeryan17.snp.Commands.VampBlCommand;
import com.weeryan17.snp.Commands.WitherCommand;
import com.weeryan17.snp.Util.Events;
import com.weeryan17.snp.PlayerClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

public class Main extends JavaPlugin implements Listener, CommandExecutor {
    ProtocolManager protocolManager;
    public static int stop;
    Scoreboard score;
    public static Main plugin;
    String type;
    String player;

    public void onEnable() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.Timer();
        plugin = this;
        ClanCommand exec8 = new ClanCommand(plugin);
        MobCommand exec6 = new MobCommand(plugin);
        WitherCommand exec7 = new WitherCommand(plugin);
        VampBlCommand exec2 = new VampBlCommand(plugin);
        Howl exec4 = new Howl(plugin);
        ClassCommand exec5 = new ClassCommand(plugin);
        MainCommand exec = new MainCommand(plugin);
        VampBatCommand exec3 = new VampBatCommand(plugin);
        Events event = new Events(plugin);
        this.getCommand("class").setExecutor(exec5);
        this.getCommand("wither").setExecutor(exec7);
        this.getCommand("snp").setExecutor(exec);
        this.getCommand("bl").setExecutor(exec2);
        this.getCommand("bat").setExecutor(exec3);
        this.getCommand("howl").setExecutor(exec4);
        this.getCommand("mob").setExecutor(exec6);
        this.getCommand("clan").setExecutor(exec8);
        Bukkit.getServer().getPluginManager().registerEvents(event, this);
        if(!this.getConfig().contains("Clans.")){
        	this.getLogger().info("Clan info not found adding clan info and default clans");
        	this.getConfig().set("Clans." + "Necromancer" + ".Clans" + ".Noximperius" + ".Open" , true);
        	this.getConfig().set("Clans." + "Necromancer" + ".Clans" + ".Noximperius" + ".Owner" , "Server");
        	this.getConfig().set("Clans." + "Necromancer" + ".Clans" + ".Witherheart" + ".Open" , true);
        	this.getConfig().set("Clans." + "Necromancer" + ".Clans" + ".Witherheart" + ".Owner" , "Server");
        	this.getConfig().set("Clans." + "Necromancer" + ".Clans" + ".Deathskull" + ".Open" , true);
        	this.getConfig().set("Clans." + "Necromancer" + ".Clans" + ".Deathskull" + ".Owner" , "Server");
        	this.getConfig().set("Clans." + "Werewolf" + ".Clans" + ".Darkclaw" + ".Open" , true);
        	this.getConfig().set("Clans." + "Werewolf" + ".Clans" + ".Darkclaw" + ".Owner" , "Server");
        	this.getConfig().set("Clans." + "Werewolf" + ".Clans" + ".Silverclaw" + ".Open" , true);
        	this.getConfig().set("Clans." + "Werewolf" + ".Clans" + ".Silverclaw" + ".Owner" , "Server");
        	this.getConfig().set("Clans." + "Werewolf" + ".Clans" + ".Bloodvenom" + ".Open" , true);
        	this.getConfig().set("Clans." + "Werewolf" + ".Clans" + ".Bloodvenom" + ".Owner" , "Server");
        	this.getConfig().set("Clans." + "Vampire" + ".Clans" + ".Nightwing" + ".Open" , true);
        	this.getConfig().set("Clans." + "Vampire" + ".Clans" + ".Nightwing" + ".Owner" , "Server");
        	this.getConfig().set("Clans." + "Vampire" + ".Clans" + ".Ashborn" + ".Open" , true);
        	this.getConfig().set("Clans." + "Vampire" + ".Clans" + ".Ashborn" + ".Owner" , "Server");
        	this.getConfig().set("Clans." + "Vampire" + ".Clans" + ".Darkblood" + ".Open" , true);
        	this.getConfig().set("Clans." + "Vampire" + ".Clans" + ".Darkblood" + ".Owner" , "Server");
        }
        this.saveConfig();
        this.getLogger().info("Super Natural Players plugin enabled");
    }

    public void Timer() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){

            @Override
            public void run() {
                PlayerClass playerClass = new PlayerClass(Main.plugin);
                playerClass.run();
            }
        }, 0, 100);
    }

    public void Timer2(final Player player, final Wolf wolf) {
        stop = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this, new Runnable(){

            @Override
            public void run() {
                PlayerClass playerClass = new PlayerClass(Main.plugin);
                playerClass.run2(player, wolf);
            }
        }, 0, 10);
    }

    public void onDisable() {
    	for(Player pl : Bukkit.getOnlinePlayers()){
    		String name = pl.getName().toString();
    	this.getConfig().set("Players." + name + ".WC", false);
    	this.getConfig().set("Players." + name + ".Truce", true);
        this.saveConfig();
    	}
        this.getLogger().info("Super Natural Players plugin disabled");
    }

    public static void error(String message) {
        Main.log(Level.WARNING, message);
    }

    public static void log(Level level, String message) {
        plugin.getLogger().log(level, message);
    }

    public static void addLore(ItemStack i, String s) {
        if (i.getItemMeta().getLore() == null) {
            ArrayList<String> lore = new ArrayList<String>();
            lore.add(s);
            ItemMeta imeta = i.getItemMeta();
            imeta.setLore(lore);
            i.setItemMeta(imeta);
        } else {
            List<String> lore = i.getItemMeta().getLore();
            lore.add(s);
            ItemMeta imeta = i.getItemMeta();
            imeta.setLore(lore);
            i.setItemMeta(imeta);
        }
    }
    public static void noAI(Entity bukkitEntity) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }
	public static ArrayList<Entity> getNearbyEntitys(Entity entity, double range){
        ArrayList<Entity> nearby = new ArrayList<Entity>();
        for (Entity e : entity.getNearbyEntities(range, range, range)){
                nearby.add(e);
        }
        return nearby;
    }
	public static boolean isAlive(EntityType type){
		if(type != EntityType.ARMOR_STAND && type != EntityType.ARROW && type != EntityType.BOAT && type != EntityType.COMPLEX_PART && type != EntityType.DROPPED_ITEM && type != EntityType.EGG && type != EntityType.ENDER_CRYSTAL && type != EntityType.ENDER_PEARL && type != EntityType.ENDER_SIGNAL && type != EntityType.EXPERIENCE_ORB && type != EntityType.FALLING_BLOCK && type != EntityType.FIREBALL && type != EntityType.FIREWORK && type != EntityType.FISHING_HOOK && type != EntityType.ITEM_FRAME && type != EntityType.LEASH_HITCH && type != EntityType.LIGHTNING && type != EntityType.MINECART && type != EntityType.MINECART_CHEST && type != EntityType.MINECART_COMMAND && type != EntityType.MINECART_FURNACE && type != EntityType.MINECART_HOPPER && type != EntityType.MINECART_MOB_SPAWNER && type != EntityType.MINECART_TNT && type != EntityType.PAINTING && type != EntityType.PRIMED_TNT && type != EntityType.SMALL_FIREBALL && type != EntityType.SNOWBALL && type != EntityType.SPLASH_POTION && type != EntityType.THROWN_EXP_BOTTLE && type != EntityType.UNKNOWN && type != EntityType.WEATHER && type != EntityType.WITHER_SKULL){
		return true;
		} else {
			return false;
		}
	}
	public static int randInt(int min, int max) {

	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	   public static String removeCharAt(String s, int pos) {
		      return s.substring(0, pos) + s.substring(pos + 1);
		   }
}