package dev.melncat.paperasylum.item.melee

import com.destroystokyo.paper.ParticleBuilder
import dev.melncat.paperasylum.PaperAsylum
import net.kyori.adventure.sound.Sound
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior

class London : ItemBehavior {
	private val hitSound = Sound.sound()
		.type(PaperAsylum.key("item.london.hit"))
		.volume(0.7f)
		.build()
	
	override fun handleAttackEntity(player: Player, itemStack: ItemStack, attacked: Entity, event: EntityDamageByEntityEvent) {
		player.world.playSound(hitSound, player)
			ParticleBuilder(Particle.DUST)
				.location(attacked.location)
				.data(DustOptions(Color.fromRGB(0x770000), 3f))
				.count(10)
				.extra(2.0)
				.receivers(100)
				.spawn()
		
	}
	
}