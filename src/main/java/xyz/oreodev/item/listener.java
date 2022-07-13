package xyz.oreodev.item;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class listener implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        Entity entity1 = e.getDamager();
        Entity entity2 = e.getEntity();
        Bukkit.broadcastMessage("hit : " + entity2.getName() + " by " + entity1.getName() + " damage : " + e.getDamage());
    }
}
