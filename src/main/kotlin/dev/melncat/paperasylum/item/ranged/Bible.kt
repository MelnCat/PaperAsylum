package dev.melncat.paperasylum.item.ranged

import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.behavior.ItemCooldown
import dev.melncat.paperasylum.behavior.PABehavior
import dev.melncat.paperasylum.util.RagdollManager
import net.kyori.adventure.sound.Sound
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.component.UseCooldown
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.commons.provider.provider
import xyz.xenondevs.nova.util.item.novaItem
import xyz.xenondevs.nova.util.runTaskLater
import xyz.xenondevs.nova.world.item.behavior.Cooldown
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import xyz.xenondevs.nova.world.player.WrappedPlayerInteractEvent
import java.util.*
import kotlin.math.min

class Bible : PABehavior() {
	private val castSound = Sound.sound()
		.type(PaperAsylum.key("item.bible.cast"))
		.volume(0.9f)
		.build()
	private val explosionSound = Sound.sound()
		.type(PaperAsylum.key("item.bible.explosion"))
		.volume(0.9f)
		.build()
	
	override fun handleRightClick(player: Player, itemStack: ItemStack) {
		if (consumeCooldown(player, itemStack)) return
		val hit = player.rayTraceEntities(100)?.hitEntity?.location
			?: player.rayTraceBlocks(100.0)?.hitBlock?.location
			?: return
		player.playSound(castSound, Sound.Emitter.self())
		runTaskLater(15L) {
			hit.world.strikeLightningEffect(hit)
			hit.world.spawnParticle(Particle.EXPLOSION, hit, 5, 1.0, 1.0, 1.0)
			hit.world.spawnParticle(Particle.LARGE_SMOKE, hit, 25, 1.0, 1.0, 1.0)
			hit.world.spawnParticle(Particle.FLAME, hit, 4, 1.0, 1.0, 1.0)
			hit.world.playSound(explosionSound, hit.x, hit.y, hit.z)
			val nearby = hit.getNearbyLivingEntities(3.0)
			for (entity in nearby) {
				val knock = entity.location.subtract(hit)
				entity.velocity = entity.velocity.add(knock.toVector().normalize().multiply(min(4.0, 1 / (entity.location.distance(hit)))))
				if (entity is Player) RagdollManager.startRagdoll(entity.uniqueId, 60)
				entity.damage(9.0, player)
			}
		}
		
	}
}