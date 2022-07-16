package xyz.oreodev.item;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class itemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            player.sendMessage("send!");
            ItemStack item = new ItemStack(Material.DIAMOND_HOE, 1, (short) 1);
            ItemMeta meta = item.getItemMeta();
            meta.addEnchant(Enchantment.LUCK, 1, true);
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "ITEM");
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "GENERIC_ATTACK_DAMAGE", 49, AttributeModifier.Operation.ADD_NUMBER));
            item.setItemMeta(meta);
            player.getInventory().addItem(item);

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("nuke")) {
                    player.getInventory().remove(item);
                    Bukkit.broadcastMessage(ChatColor.RED + "핵폭탄 준비완료");
                    ItemStack it2 = new ItemStack(Material.FLINT, 1);
                    ItemMeta it2Meta = it2.getItemMeta();
                    it2Meta.addEnchant(Enchantment.LUCK, 1, true);
                    it2Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    it2Meta.setDisplayName(ChatColor.RED + "NUCLEAR");
                    it2.setItemMeta(it2Meta);
                    player.getInventory().addItem(it2);
                }
            }
        }
        return false;
    }
}
