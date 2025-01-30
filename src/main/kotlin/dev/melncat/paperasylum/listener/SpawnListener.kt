package dev.melncat.paperasylum.listener

import com.destroystokyo.paper.event.entity.EntityKnockbackByEntityEvent
import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent
import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.registry.PAItem
import net.kyori.adventure.sound.Sound
import net.minecraft.world.entity.ai.attributes.Attributes
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Zombie
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitFun
import xyz.xenondevs.nova.initialize.InitStage
import xyz.xenondevs.nova.util.item.novaItem
import xyz.xenondevs.nova.util.registerEvents

@Init(stage = InitStage.POST_WORLD)
object SpawnListener : Listener {
	@InitFun
	private fun init() {
		registerEvents()
	}
	
	@EventHandler
	fun on(event: PlayerPostRespawnEvent) {
		initializeItems(event.player)
	}
	
	@EventHandler
	fun on(event: PlayerJoinEvent) {
		if (!event.player.hasPlayedBefore()) initializeItems(event.player)
	}
	
	private fun initializeItems(player: Player) {
		val melee = PAItem.melee.random().createItemStack()
		val ranged = PAItem.ranged.random().createItemStack()
		val misc = PAItem.misc.random().createItemStack()
		player.inventory.addItem(melee, ranged, misc)
	}
	
}