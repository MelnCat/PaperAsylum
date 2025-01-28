package dev.melncat.paperasylum.item.misc

import dev.melncat.paperasylum.PaperAsylum
import net.kyori.adventure.sound.Sound
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.component.ItemAttributeModifiers
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.commons.provider.provider
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import xyz.xenondevs.nova.world.player.equipment.ArmorEquipEvent

class UltraInstinct : ItemBehavior {
	private val equipSound = Sound.sound()
		.type(PaperAsylum.key("item.ultra_instinct.equip"))
		.build()
	private var used = false
	
	override val baseDataComponents = provider(
		DataComponentMap.builder()
			.set(
				DataComponents.ATTRIBUTE_MODIFIERS,
				ItemAttributeModifiers.builder()
					.add(
						Attributes.MAX_HEALTH,
						AttributeModifier(
							ResourceLocation.parse("paperasylum:ultra_instinct"),
							10.0,
							Operation.ADD_VALUE
						),
						EquipmentSlotGroup.CHEST
					)
					.build()
			)
			.build()
	)
	override fun handleEquip(player: Player, itemStack: ItemStack, equipped: Boolean, event: ArmorEquipEvent) {
		player.playSound(equipSound, Sound.Emitter.self())
		if (!used) {
			used = true
			player.heal(10.0)
		}
	}
}