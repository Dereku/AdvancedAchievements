package com.hm.achievement.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.hm.achievement.AdvancedAchievements;

public class ListGUIListener implements Listener {

	private AdvancedAchievements plugin;

	public ListGUIListener(AdvancedAchievements plugin) {

		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent event) {

		// Inventory not related to the plugin: do nothing.
		if (!event.getInventory().getName().startsWith(ChatColor.translateAlternateColorCodes('&',
				plugin.getPluginLang().getString("list-gui-title", "&5&lAchievements List")))) {
			return;
		}

		// Prevent players from taking items out of the GUI.
		event.setCancelled(true);

		// GUI corresponding to the achievement listing of a given category. Do not let the player interact with it.
		if (event.getInventory().getItem(0).getType() == Material.STAINED_CLAY)
			return;

		// Main GUI displaying all the categories. Do not let players interact with locked categories or slots not
		// corresponding to a category item.
		if (event.getInventory().getItem(0).getType() != Material.STAINED_CLAY
				&& (event.getCurrentItem().getType() == Material.BARRIER
						|| event.getRawSlot() > AdvancedAchievements.MULTIPLE_ACHIEVEMENTS.length
								+ AdvancedAchievements.NORMAL_ACHIEVEMENTS.length
								- plugin.getDisabledCategorySet().size()))
			return;

		// Create GUI according to whether the player clicked on a normal
		// achievements category or a multiple achievement category.
		if (event.getCurrentItem().getType() == Material.STONE
				|| event.getCurrentItem().getType() == Material.SMOOTH_BRICK
				|| event.getCurrentItem().getType() == Material.BONE
				|| event.getCurrentItem().getType() == Material.WORKBENCH) {
			plugin.getAchievementListCommand().createCategoryGUIMultiple(event.getCurrentItem().getType(),
					(Player) event.getWhoClicked());
		} else {
			plugin.getAchievementListCommand().createCategoryGUINormal(event.getCurrentItem().getType(),
					(Player) event.getWhoClicked());
		}

	}
}
