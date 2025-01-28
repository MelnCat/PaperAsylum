package dev.melncat.paperasylum.item.melee

import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.behavior.PABehavior
import io.papermc.paper.datacomponent.DataComponentTypes
import net.kyori.adventure.sound.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.serialization.cbf.NamespacedCompound

class Wand : PABehavior() {
	private val useSound = Sound.sound()
		.type(PaperAsylum.key("item.wand.use"))
		.build()
	override fun handleAttackEntity(player: Player, itemStack: ItemStack, attacked: Entity, event: EntityDamageByEntityEvent) {
		if (consumeCooldown(player, itemStack)) return
		if (attacked is LivingEntity) {
			val difference = attacked.location.subtract(player.location).toVector()
			difference.y = 0.0
			difference.normalize().multiply(20)
			attacked.velocity = attacked.velocity.add(difference)
			player.world.playSound(useSound, player)
		}
	}
	
	override fun modifyClientSideStack(player: Player?, itemStack: ItemStack, data: NamespacedCompound): ItemStack {
		itemStack.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
		return itemStack
	}
}