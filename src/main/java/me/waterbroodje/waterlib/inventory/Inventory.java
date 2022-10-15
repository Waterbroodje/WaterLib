package me.waterbroodje.waterlib.inventory;

import me.waterbroodje.waterlib.utilities.Color;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Inventory {
    private final org.bukkit.inventory.Inventory inventory;

    public Inventory(String name, int rows) {
        this.inventory = Bukkit.createInventory(null, rows * 9 - 1, new Color(name).translate());
    }

    public Inventory(String name, InventoryType inventoryType) {
        this.inventory = Bukkit.createInventory(null, inventoryType, new Color(name).translate());
    }

    public Inventory(String name, int rows, Map<Integer, ItemStack> content) {
        this.inventory = Bukkit.createInventory(null, rows * 9 - 1, new Color(name).translate());
        content.keySet().forEach(i -> {
            inventory.setItem(i, content.get(i));
        });
    }

    public Inventory addItem(Integer integer, ItemStack itemStack) {
        this.inventory.setItem(integer, itemStack);
        return this;
    }

    public org.bukkit.inventory.Inventory build() {
        return this.inventory;
    }

    public org.bukkit.inventory.Inventory getInventory() {
        return inventory;
    }
}
