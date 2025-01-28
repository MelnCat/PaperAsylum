package dev.melncat.paperasylum.behavior

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.util.item.novaItem
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import xyz.xenondevs.nova.world.player.WrappedPlayerInteractEvent

open class PABehavior : ItemBehavior {
	override fun handleEntityInteract(player: Player, itemStack: ItemStack, clicked: Entity, event: PlayerInteractAtEntityEvent) {
		handleRightClick(player, itemStack)
	}
	
	override fun handleInteract(player: Player, itemStack: ItemStack, action: Action, wrappedEvent: WrappedPlayerInteractEvent) {
		if (!action.isRightClick) return
		handleRightClick(player, itemStack)
		
	}
	
	protected open fun handleRightClick(player: Player, itemStack: ItemStack) {}
	
	protected fun hasCooldown(itemStack: ItemStack) 
	= itemStack.novaItem!!.getBehavior(ItemCooldown::class).hasCooldown()
	
	protected fun resetCooldown(player: Player, itemStack: ItemStack) {
		itemStack.novaItem!!.getBehavior(ItemCooldown::class).resetCooldown(player, itemStack)
	}
	
	protected fun consumeCooldown(player: Player, itemStack: ItemStack): Boolean {
		if (hasCooldown(itemStack)) return true
		itemStack.novaItem!!.getBehavior(ItemCooldown::class).resetCooldown(player, itemStack)
		return false
	}
}