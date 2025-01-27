package dev.melncat.paperasylum.item.ranged

import com.destroystokyo.paper.ParticleBuilder
import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.behavior.ItemCooldown
import io.papermc.paper.datacomponent.DataComponentTypes
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

class Chicago : ItemBehavior {
	private val hitSound = Sound.sound()
		.type(PaperAsylum.key("item.chicago.hit"))
		.volume(0.7f)
		.build()
	
	
	override fun handleInteract(player: Player, itemStack: ItemStack, action: Action, wrappedEvent: WrappedPlayerInteractEvent) {
		if (!action.isRightClick) return
		if (itemStack.novaItem!!.getBehavior(ItemCooldown::class).hasCooldown()) return
		itemStack.novaItem!!.getBehavior(ItemCooldown::class).resetCooldown(player, itemStack)
		player.world.playSound(hitSound, player)
		val hit = player.location.world.rayTrace(
			player.eyeLocation,
			player.location.direction,
			100.0,
			FluidCollisionMode.NEVER,
			true,
			0.0) {
			it !is Player || it.uniqueId != player.uniqueId
		}
		hit?.hitEntity?.let { if (it is LivingEntity) it.damage(0.8, player) }
		for (i in 5..500) {
			val distance = i.toDouble() / 5.0
			val pos = player.eyeLocation.add(player.location.direction.multiply(distance))
			if (pos.block.isSolid) break
			ParticleBuilder(Particle.DUST)
				.data(DustOptions(Color.fromRGB(0xffffff), 0.1f))
				.location(pos)
				.receivers(100)
				.spawn()
		}
	}
	
	
}