package dev.melncat.paperasylum.listener

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Zombie
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
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
	}
}