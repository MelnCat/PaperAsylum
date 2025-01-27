package dev.melncat.paperasylum.listener

import com.destroystokyo.paper.event.entity.EntityKnockbackByEntityEvent
import dev.melncat.paperasylum.PaperAsylum
import net.kyori.adventure.sound.Sound
import net.minecraft.world.entity.ai.attributes.Attributes
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Zombie
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitFun
import xyz.xenondevs.nova.initialize.InitStage
import xyz.xenondevs.nova.util.item.novaItem
import xyz.xenondevs.nova.util.registerEvents

@Init(stage = InitStage.POST_WORLD)
object DeathListener : Listener {
	private val deathSound = Sound.sound()
		.type(PaperAsylum.key("death.fx"))
		.build()
	
	private val dingSound = Sound.sound()
		.type(PaperAsylum.key("death.ding"))
		.build()
	
	@InitFun
	private fun init() {
		registerEvents()
	}
	@EventHandler
	fun on(event: PlayerDeathEvent) {
		// if (event.player.killer == null) return
		event.player.playSound(deathSound, Sound.Emitter.self())
	}
	@EventHandler
	fun on(event: EntityDeathEvent) {
		val killer = event.entity.killer ?: return
		killer.playSound(dingSound, Sound.Emitter.self())
	}
}