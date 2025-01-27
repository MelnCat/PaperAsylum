package dev.melncat.paperasylum.item.ranged

import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.behavior.ItemCooldown
import io.papermc.paper.datacomponent.DataComponentTypes
import net.kyori.adventure.sound.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.serialization.cbf.NamespacedCompound
import xyz.xenondevs.nova.util.item.novaItem
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior

class Wand : ItemBehavior {
	private val useSound = Sound.sound()
		.type(PaperAsylum.key("item.wand.use"))
		.build()
	override fun handleAttackEntity(player: Player, itemStack: ItemStack, attacked: Entity, event: EntityDamageByEntityEvent) {
		if (itemStack.novaItem!!.getBehavior(ItemCooldown::class).hasCooldown()) return
		if (attacked is LivingEntity) {
			val difference = attacked.location.subtract(player.location).toVector()
			difference.y = 0.0
			difference.normalize().multiply(20)
			attacked.velocity = attacked.velocity.add(difference)
			player.world.playSound(useSound, player)
			itemStack.novaItem!!.getBehavior(ItemCooldown::class).resetCooldown(player, itemStack)
		}
	}
	
	override fun modifyClientSideStack(player: Player?, itemStack: ItemStack, data: NamespacedCompound): ItemStack {
		itemStack.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
		return itemStack
	}
}