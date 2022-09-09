package xyz.oreodev.item;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class listener implements Listener {
    private Item plugin;
    BukkitScheduler scheduler = Bukkit.getScheduler();
    List<Player> coolDown = new ArrayList<>();

    public listener(Item plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        Entity entity1 = e.getDamager();
        Entity entity2 = e.getEntity();
        if (entity1 instanceof Player player) {
            Bukkit.broadcastMessage(ChatColor.RED + player.getName() + ChatColor.WHITE + " gained " + e.getDamage() + " damage to " + ChatColor.GREEN + entity2.getName() + ChatColor.WHITE + " by using " + player.getInventory().getItemInMainHand().getItemMeta().getDisplayName());
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        e.setCancelled(true);
        Player player = e.getPlayer();
        if (coolDown.contains(player)) return;
        coolDown.add(player);
        itemDelay(player);
        player.setCooldown(player.getInventory().getItemInMainHand().getType(), 10);
        if (e.getAction().isLeftClick()) return;
        if (player.getInventory().getItemInMainHand() == null) return;
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.FLINT)) return;
        if (!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("NUCLEAR")) return;
        if (player.getTargetBlock(5).getType() == Material.AIR) return;
        Location location = player.getTargetBlock(5).getLocation().add(0, 1, 0);
        summonLoL(location);
    }

    @EventHandler
    public void onDie(EntityDeathEvent e) {
        e.getDrops().clear();
        e.setDroppedExp(0);
        e.setDeathSound(null);
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ITEM_GOAT_HORN_SOUND_1, 10f, 2f);
    }

    @EventHandler
    public void onBoom(EntityExplodeEvent e) {
        if (!e.getEntity().hasMetadata("lol")) return;
        if (e.getEntity().getMetadata("lol").get(0).asString().equals("fake")) {
            e.setCancelled(true);
            e.getEntity().remove();
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ITEM_GOAT_HORN_SOUND_1, 100f, 2f);
            e.getEntity().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, e.getEntity().getLocation(), 1);
        }
    }

    public void summonLoL(Location location) {
        TNTPrimed tntPrimed = (TNTPrimed) location.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
        tntPrimed.setMetadata("lol", new FixedMetadataValue(plugin, "fake"));
        tntPrimed.setCustomName(ChatColor.RED + "핵폭탄");
        tntPrimed.setCustomNameVisible(true);
        tntPrimed.setFuseTicks(80);
        location.getWorld().playSound(location, Sound.ENTITY_TNT_PRIMED, 10f, 1f);
        Bukkit.broadcastMessage(ChatColor.RED + "핵폭탄이 설치되었습니다.");
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(ChatColor.RED + "핵폭탄이 설치되었습니다.", "");
        }
    }

    public void itemDelay(Player player) {
        scheduler.runTaskLaterAsynchronously(plugin, () -> coolDown.remove(player), 10);
    }
}
