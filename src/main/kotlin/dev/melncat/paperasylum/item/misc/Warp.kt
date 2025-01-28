package dev.melncat.paperasylum.item.misc

import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.behavior.PABehavior
import dev.melncat.paperasylum.util.RagdollManager
import io.papermc.paper.entity.LookAnchor
import net.kyori.adventure.sound.Sound
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.component.ItemAttributeModifiers
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.commons.provider.provider
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import xyz.xenondevs.nova.world.player.equipment.ArmorEquipEvent

class Warp : PABehavior() {
	private val useSound = Sound.sound()
		.type(PaperAsylum.key("item.warp.use"))
		.build()
	
	override fun handleRightClick(player: Player, itemStack: ItemStack) {
		val entity = player.getTargetEntity(120) as? LivingEntity ?: return
		val behind = entity.location.add(entity.location.direction.setY(0).normalize().multiply(-1.2))
		player.teleport(behind)
		player.lookAt(entity, LookAnchor.EYES, LookAnchor.EYES)
		if (entity is Player) RagdollManager.startRagdoll(entity.uniqueId, 40L)
		entity.velocity = entity.velocity.add(entity.location.direction.setY(0).normalize().multiply(5))
		entity.world.playSound(useSound, player)
		entity.damage(5.0, player)
		itemStack.subtract()
	}
}