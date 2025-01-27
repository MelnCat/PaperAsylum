package dev.melncat.paperasylum.behavior

import dev.melncat.paperasylum.registry.PAItem
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponents
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.Item
import net.minecraft.world.item.component.ItemAttributeModifiers
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.commons.provider.provider
import xyz.xenondevs.nova.util.ResourceLocation
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior

class HealOnConsume(
	val health: Double
) : ItemBehavior {
	override fun handleConsume(player: Player, itemStack: ItemStack, event: PlayerItemConsumeEvent) {
		player.heal(health)
	}
}