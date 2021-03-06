package com.weeryan17.snp;

import java.util.HashMap;
import java.util.Map;

import com.weeryan17.snp.Main;
import com.weeryan17.snp.Util.EntityHider;
import com.weeryan17.snp.Util.Sun;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerClass {
	Map<Player, Integer> map = new HashMap<Player, Integer>();
    public int stop;
    Player p;
    String playerString;
    String playerName;
    private Main instance;
    public PlayerClass(Main instance) {
        this.instance = instance;
    }

    void runClass() {
    	for(final Player p : Bukkit.getOnlinePlayers()) {
    		playerName = p.getName();
            World world = p.getWorld();
            int days = (int)world.getFullTime() / 24000;
            int phase = days % 8;
            if (instance.getDataConfig().get("Players." + playerName + ".type").toString().equals("Vampire")) {
                double rad = Sun.calcPlayerIrradiation(p);
                if (rad >= 0.25 && p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                    p.damage(rad);
                    p.sendMessage(ChatColor.RED + "The day light is burning you");
                    int timer = instance.getConfig().getInt("General." + "Timings" + ".Player Cheaker(ticks)");
                    double seconds = timer/20;
                    double fireticks = seconds+1;
                    p.setFireTicks((int) fireticks);
                }
                
            }
            	EntityHider hide = new EntityHider((Plugin)this.instance, EntityHider.Policy.BLACKLIST);
            if (instance.getDataConfig().get("Players." + playerName + ".type").toString().equals("Werewolf")){
            if (phase == 0 && world.getTime() >= 13000) {
                p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                p.removePotionEffect(PotionEffectType.SPEED);
                p.removePotionEffect(PotionEffectType.INVISIBILITY);
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 4));
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 300, 4));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 4));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 0));
                if (instance.getDataConfig().getBoolean("Players." + playerName + ".Wolf") == false){
                p.sendMessage(ChatColor.DARK_PURPLE + "The moon is full and bright tonight...");
                int moon = instance.getDataConfig().getInt("Players." + playerName + ".FullMoons");
                instance.getDataConfig().set("Players." + playerName + ".FullMoons", moon+1);
                Location loc = p.getLocation();
                final Wolf wolf = (Wolf)loc.getWorld().spawnEntity(loc, EntityType.WOLF);
                this.instance.getDataConfig().set("Players." + playerName + ".Mob", wolf);
                Main.noAI(wolf);
                hide.hideEntity(p, wolf);
            	for(Player pl : Bukkit.getOnlinePlayers()) {
                    pl.playSound(loc, Sound.WOLF_HOWL, 0.5f, 0.0f);
                    pl.hidePlayer(p);
            	}
            	stop = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.instance, new Runnable(){

                    public void run() {
                        run2(p, wolf);
                    }
                }, 0, instance.getConfig().getInt("General." + "Timings" + ".Entity Discusier Teloporting(ticks)"));
            	map.put(p, stop);
                instance.getDataConfig().set("Players." + playerName + ".Wolf", true);
        			instance.saveDataConfig();
            }
        } else {
        	p.removePotionEffect(PotionEffectType.SPEED);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 0));
            if (instance.getDataConfig().getBoolean("Players." + playerName + ".Wolf") == true){
            	for(Player pl : Bukkit.getOnlinePlayers()) {
            		pl.showPlayer(p);
            	}
                instance.getDataConfig().set("Players." + playerName + ".Wolf", false);
                Wolf wolf = (Wolf) instance.getDataConfig().get("Players." + playerName + ".Mob");
                wolf.remove();
        			instance.saveDataConfig();
            Bukkit.getScheduler().cancelTask(map.get(p));
            }
        }
        }
    	}
    }

    public void run2(Player player, Wolf wolf) {
        wolf.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 10));
        wolf.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 300, 10));
  
    }
}
