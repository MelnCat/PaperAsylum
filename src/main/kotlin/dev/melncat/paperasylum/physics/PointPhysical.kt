package dev.melncat.paperasylum.physics

import org.bukkit.FluidCollisionMode
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import org.joml.Vector3d
import org.joml.Vector3f
import org.joml.Vector3fc
import xyz.xenondevs.nova.util.plus
import xyz.xenondevs.nova.world.fakeentity.FakeEntity
import xyz.xenondevs.nova.world.fakeentity.impl.FakeItemDisplay
import java.util.*

class PointPhysical(val item: ItemStack, 
                    var location: Location, 
                    direction: Vector,
                    var lifetime: Int = -1,
                    val onCollide: ((PointPhysical, Entity?) -> Unit)? = null, 
                    val thrower: UUID? = null, 
                    var speed: Double = 1.0) {
	private lateinit var entity: FakeEntity<*>
	val velocity = direction.normalize().multiply(speed)
	fun setup() {
		entity = FakeItemDisplay(location, true) { d, m ->
			m.itemStack = item
			m.scale = Vector3f(0.1f, 0.1f, 0.1f)
			m.posRotInterpolationDuration = 10
		}
	}
	fun remove() {
		entity.remove()
	}
	
	fun tick() {
		val result = location.world.rayTrace(
			location,
			velocity.clone().normalize(),
			speed,
			FluidCollisionMode.NEVER,
			true,
			0.1) { it !is Player || it.uniqueId != thrower }
		if (result != null) {
			if (result.hitBlockFace != null) when (result.hitBlockFace) {
				BlockFace.UP, BlockFace.DOWN -> velocity.y *= -1
				BlockFace.SOUTH, BlockFace.NORTH -> velocity.z *= -1
				BlockFace.WEST, BlockFace.EAST -> velocity.x *= -1
				else -> {}
			}
			onCollide!!(this, result.hitEntity)
		}
		velocity.y -= 0.1
		location.add(velocity)
		entity.teleport(location)
		if (lifetime != -1 && lifetime-- < 0) PhysicsManager.removePhysical(this)
	}
}