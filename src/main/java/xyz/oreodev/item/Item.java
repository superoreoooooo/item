package xyz.oreodev.item;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Item extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "item hello!");
        getCommand("item").setExecutor(new itemCommand());
        Bukkit.getPluginManager().registerEvents(new listener(this), this);
    }

    @Override
    public void onDisable() {
    }
}
