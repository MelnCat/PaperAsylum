package dev.melncat.paperasylum.physics

import dev.melncat.paperasylum.PaperAsylum
import org.bukkit.Bukkit
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitFun
import xyz.xenondevs.nova.initialize.InitStage

@Init(stage = InitStage.POST_WORLD)
object PhysicsManager {
	private val physicals = mutableSetOf<PointPhysical>()
	
	fun addPhysical(physical: PointPhysical) {
		physical.setup()
		physicals.add(physical)
	}
	
	private fun tickPhysics() {
		for (physical in physicals) {
			physical.tick()
		}
	}
	
	@InitFun
	fun init() {
		Bukkit.getScheduler().runTaskTimer(PaperAsylum.plugin!!, { -> 
			tickPhysics()
		}, 0L, 1L)
	}
}