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
import xyz.xenondevs.commons.provider.provider
import xyz.xenondevs.nova.util.ResourceLocation
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior

class MeleeBehavior(
	damage: Double,
	reach: Double,
	knockback: Double,
) : ItemBehavior {
	private val reachId = ResourceLocation(PAItem.addon, "reach")	
	
	override val baseDataComponents = provider(
		DataComponentMap.builder()
			.set(
				DataComponents.ATTRIBUTE_MODIFIERS,
				ItemAttributeModifiers.builder()
					.add(
						Attributes.ATTACK_DAMAGE,
						AttributeModifier(
							Item.BASE_ATTACK_DAMAGE_ID,
							damage - 1.0,
							Operation.ADD_VALUE
						),
						EquipmentSlotGroup.MAINHAND
					)
					.add(
						Attributes.ENTITY_INTERACTION_RANGE,
						AttributeModifier(
							reachId,
							reach,
							Operation.ADD_VALUE
						),
						EquipmentSlotGroup.MAINHAND
					)
					.add(
						Attributes.ATTACK_KNOCKBACK,
						AttributeModifier(
							reachId,
							knockback,
							Operation.ADD_VALUE
						),
						EquipmentSlotGroup.MAINHAND
					)
					.build()
			)
			.build()
	)
}