package dev.melncat.paperasylum.behavior

import dev.melncat.paperasylum.PaperAsylum
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.component.UseCooldown
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.commons.provider.provider
import xyz.xenondevs.nova.util.item.retrieveData
import xyz.xenondevs.nova.util.item.storeData
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import java.util.*

class ItemCooldown(private val cooldown: Float) : ItemBehavior {
	private val nextTimeKey = PaperAsylum.key("next_time")
	override val baseDataComponents = provider(
		DataComponentMap.builder()
			.set(
				DataComponents.USE_COOLDOWN,
				UseCooldown(cooldown, Optional.of(net.minecraft.resources.ResourceLocation.parse("paperasylum:cooldown_${UUID.randomUUID()}")))
			
			).build()
	)
	
	fun hasCooldown(item: ItemStack): Boolean {
		val nextTime = item.retrieveData<Long>(nextTimeKey) ?: 0L
		return System.currentTimeMillis() < nextTime
	}
	
	fun resetCooldown(player: Player, item: ItemStack) {
		item.storeData<Long>(nextTimeKey, System.currentTimeMillis() + (cooldown * 1000L).toLong()) 
		
		player.setCooldown(item, (cooldown * 20).toInt())
	}
}