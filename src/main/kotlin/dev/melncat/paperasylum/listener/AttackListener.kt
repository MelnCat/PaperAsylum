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
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitFun
import xyz.xenondevs.nova.initialize.InitStage
import xyz.xenondevs.nova.util.item.novaItem
import xyz.xenondevs.nova.util.registerEvents

@Init(stage = InitStage.POST_WORLD)
object AttackListener : Listener {
	@InitFun
	private fun init() {
		registerEvents()
	}
	
	@EventHandler
	fun on(event: EntityDamageByEntityEvent) {
		val damager = event.damager as? LivingEntity ?: return
		val entity = event.entity as? LivingEntity ?: return
		entity.maximumNoDamageTicks = 0
	}
	
	@EventHandler
	fun on(event: EntityKnockbackByEntityEvent) {
		val hitter = event.hitBy as? LivingEntity ?: return
		event.isCancelled = true
	}
	
}