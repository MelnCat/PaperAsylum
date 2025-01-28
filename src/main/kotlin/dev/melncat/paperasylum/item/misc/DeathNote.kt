package dev.melncat.paperasylum.item.misc

import com.destroystokyo.paper.ParticleBuilder
import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.behavior.PABehavior
import net.kyori.adventure.sound.Sound
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class DeathNote : PABehavior() {
	private val useSound = Sound.sound()
		.type(PaperAsylum.key("item.death_note.use"))
		.build()
	
	val particle = ParticleBuilder(Particle.DUST)
		.color(0x00FFFF)
		.count(15)
		.data(DustOptions(Color.fromRGB(0x00FFFF), 5f))
	
	override fun handleRightClick(player: Player, itemStack: ItemStack) {
		val hit = player.rayTraceEntities(100)?.hitEntity as? LivingEntity ?: return
		particle.location(hit.location.add(0.0, 1.0, 0.0)).receivers(100).spawn()
		hit.damage(10000.0, player)
		itemStack.subtract()
		hit.world.playSound(useSound, player)
		hit.playSound(useSound, Sound.Emitter.self())
	}
}