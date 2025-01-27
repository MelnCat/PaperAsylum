package dev.melncat.paperasylum.item.ranged

import com.destroystokyo.paper.ParticleBuilder
import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.behavior.ItemCooldown
import net.kyori.adventure.sound.Sound
import org.bukkit.Color
import org.bukkit.FluidCollisionMode
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.network.event.serverbound.ServerboundPlayerActionPacketEvent
import xyz.xenondevs.nova.util.item.novaItem
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import xyz.xenondevs.nova.world.player.WrappedPlayerInteractEvent

class America : ItemBehavior {
	private val hitSound = Sound.sound()
		.type(PaperAsylum.key("item.america.hit"))
		.volume(0.7f)
		.build()
	
	
	override fun handleInteract(player: Player, itemStack: ItemStack, action: Action, wrappedEvent: WrappedPlayerInteractEvent) {
		if (!action.isRightClick) return
		val hit = player.location.world.rayTrace(
			player.eyeLocation,
			player.location.direction,
			100.0,
			FluidCollisionMode.NEVER,
			true,
			0.0) {
			it !is Player || it.uniqueId != player.uniqueId
		}
		hit?.hitEntity?.let {
			if (it is LivingEntity) {
				it.damage(200.0, player)
				val v = it.location.subtract(player.location).toVector().normalize().multiply(50)
				it.velocity = it.velocity.clone().add(v)
			}
		}
		player.world.playSound(hitSound, player.location.x, player.location.y, player.location.z)
		player.velocity = player.velocity.clone().add(player.location.direction.multiply(-50))
		itemStack.subtract()
	}
	
	
}