package dev.melncat.paperasylum.item.melee

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import xyz.xenondevs.nova.world.player.attachment.ItemAttachment

class BirchTree : ItemBehavior {
	override fun handleInventoryTick(player: Player, itemStack: ItemStack, slot: Int) {
		if (player.inventory.itemInMainHand != itemStack) return
		
	}
}