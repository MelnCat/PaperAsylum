package dev.melncat.paperasylum.item.misc

import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.behavior.PABehavior
import dev.melncat.paperasylum.util.RagdollManager
import net.kyori.adventure.sound.Sound
import org.bukkit.FluidCollisionMode
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.UUID

class Train : PABehavior() {
	private val music = Sound.sound()
		.type(PaperAsylum.key("item.train.music"))
		.build()
	
	private enum class UseState {
		NOT_USING,
		ACCELERATE,
		RUN
	}
	
	private var states = mutableMapOf<UUID, UseState>()
	private var runTime = 0
	
	override fun handleRightClick(player: Player, itemStack: ItemStack) {
		val state = states[player.uniqueId] ?: UseState.NOT_USING
		if (hasCooldown(itemStack) || state != UseState.NOT_USING) return
		states[player.uniqueId] = UseState.ACCELERATE
		player.world.playSound(music, player)
		(object : BukkitRunnable() {
			override fun run() {
				if (state == UseState.ACCELERATE) {
					runTime++
					if (runTime > 20) {
						runTime = 0
						states[player.uniqueId] = UseState.RUN
					}
					player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 200, (runTime / 5.5).toInt(), false, false, false))
				}
				player.velocity = player.eyeLocation.direction.setY(0).normalize().multiply(
					if (state == UseState.ACCELERATE) runTime * 0.9 / 21.0 else 0.9
				).setY(player.velocity.y)
				if (state == UseState.RUN) {
					runTime++
					if (runTime > 10 * 20) {
						cancel()
						player.world.stopSound(music)
						player.damage(1000.0) // TODO HEART ATTACK
						states[player.uniqueId] = UseState.NOT_USING
						runTime = 0
						player.removePotionEffect(PotionEffectType.SPEED)
					}
				}
				val hit = player.world.rayTrace(
					player.location.add(0.0, 0.5, 0.0),
					player.location.direction.setY(0).normalize(),
					1.0,
					FluidCollisionMode.NEVER,
					true,
					0.5) {
					it !is Player || it.uniqueId != player.uniqueId
				}
				if (hit != null && !(state == UseState.ACCELERATE && hit.hitEntity == null)) {
					val entity = hit.hitEntity as? LivingEntity
					if (entity != null) {
						entity.damage(10.0, player)
						entity.velocity = entity.velocity.add(player.location.direction.setY(0).normalize().multiply(4))
						if (entity is Player) RagdollManager.startRagdoll(entity.uniqueId, 40L)
					} else if (hit.hitBlock != null) {
						player.damage(10.0)
						RagdollManager.startRagdoll(player.uniqueId, 40L)
					}
					player.world.stopSound(music)
					cancel()
					resetCooldown(player, itemStack)
					states[player.uniqueId] = UseState.NOT_USING
					runTime = 0
					player.removePotionEffect(PotionEffectType.SPEED)
				}
			}
		}).runTaskTimer(PaperAsylum.plugin!!, 0L, 1L)
	}
}