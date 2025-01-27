package dev.melncat.paperasylum.behavior

import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.component.UseCooldown
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.commons.provider.provider
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import java.util.*

class ItemCooldown(private val cooldown: Float) : ItemBehavior {
	private var nextTime = 0L
	
	override val baseDataComponents = provider(
		DataComponentMap.builder()
			.set(
				DataComponents.USE_COOLDOWN,
				UseCooldown(cooldown, Optional.of(net.minecraft.resources.ResourceLocation.parse("paperasylum:cooldown_${UUID.randomUUID()}")))
			
			).build()
	)
	
	fun hasCooldown(): Boolean {
		return System.currentTimeMillis() < nextTime
	}
	
	fun resetCooldown(player: Player, item: ItemStack) {
		nextTime = System.currentTimeMillis() + (cooldown * 1000L).toLong()
		
		player.setCooldown(item, (cooldown * 20).toInt())
	}
}