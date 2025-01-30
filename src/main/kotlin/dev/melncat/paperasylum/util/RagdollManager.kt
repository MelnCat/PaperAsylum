package dev.melncat.paperasylum.util

import dev.geco.gsit.GSitMain
import dev.geco.gsit.api.event.PrePlayerGetUpCrawlEvent
import dev.geco.gsit.objects.GetUpReason
import org.bukkit.Bukkit
import org.bukkit.entity.Pose
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityToggleSwimEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitFun
import xyz.xenondevs.nova.initialize.InitStage
import xyz.xenondevs.nova.util.registerEvents
import xyz.xenondevs.nova.util.runTaskTimer
import java.util.UUID

@Init(stage = InitStage.POST_WORLD)
object RagdollManager : Listener {
	private val ragdolled = mutableMapOf<UUID, Long>()
	
	fun startRagdoll(uuid: UUID, time: Long = 20) {
		ragdolled[uuid] = time
		val player = Bukkit.getPlayer(uuid) ?: return
		GSitMain.getInstance().crawlManager.startCrawl(player)
		player.setPose(Pose.SWIMMING, true)
	}
	
	fun stopRagdoll(uuid: UUID) {
		ragdolled.remove(uuid)
		val player = Bukkit.getPlayer(uuid) ?: return
		// player.setPose(Pose.STANDING)
		GSitMain.getInstance().crawlManager.stopCrawl(player, GetUpReason.PLUGIN)
	}
	
	
	@EventHandler
	fun on(event: PrePlayerGetUpCrawlEvent) {
		if (ragdolled.containsKey(event.player.uniqueId)) event.isCancelled = true
	}
	
	@EventHandler
	fun on(event: PlayerMoveEvent) {
		if (ragdolled.containsKey(event.player.uniqueId)) event.isCancelled = true
	}
	
	@InitFun
	private fun init() {
		registerEvents()
		runTaskTimer(0, 1) {
			for (entry in ragdolled.entries) {
				entry.setValue(entry.value - 1)
				if (entry.value > 0) continue
				stopRagdoll(entry.key)
			}
		}
	}
}