package dev.melncat.paperasylum.item.ranged

import com.destroystokyo.paper.ParticleBuilder
import dev.melncat.paperasylum.PaperAsylum
import net.kyori.adventure.sound.Sound
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.component.UseCooldown
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.commons.provider.provider
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import xyz.xenondevs.nova.world.player.WrappedPlayerInteractEvent
import java.util.*

class DeathNote : ItemBehavior {
	private val useSound = Sound.sound()
		.type(PaperAsylum.key("item.death_note.use"))
		.build()
	val particle = ParticleBuilder(Particle.DUST)
		.color(0x00FFFF)
		.count(15)
		.data(DustOptions(Color.fromRGB(0x00FFFF), 5f))
	override fun handleInteract(player: Player, itemStack: ItemStack, action: Action, wrappedEvent: WrappedPlayerInteractEvent) {
		if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return
		val hit = player.rayTraceEntities(100)?.hitEntity as? LivingEntity ?: return
		particle.location(hit.location.add(0.0, 1.0, 0.0)).receivers(100).spawn()
		hit.damage(10000.0, player)
		itemStack.subtract()
		hit.world.playSound(useSound, player)
		hit.playSound(useSound, Sound.Emitter.self())
	}
}