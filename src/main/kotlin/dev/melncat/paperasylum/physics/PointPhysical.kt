package dev.melncat.paperasylum.physics

import org.bukkit.FluidCollisionMode
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import org.joml.Vector3d
import xyz.xenondevs.nova.util.plus
import xyz.xenondevs.nova.world.fakeentity.FakeEntity
import xyz.xenondevs.nova.world.fakeentity.impl.FakeItemDisplay

class PointPhysical(val item: ItemStack, var location: Location, var direction: Vector, var speed: Double = 0.5) {
	private lateinit var entity: FakeEntity<*>
	fun setup() {
		entity = FakeItemDisplay(location, true) { d, m ->
			m.itemStack = item
		}
	}
	
	fun tick() {
		val result = location.world.rayTrace(
			location, 
			direction, 
			speed,
			FluidCollisionMode.NEVER, 
			true, 
			0.1,
			null)
		if (result != null)  {
			if (result.hitBlockFace != null) when(result.hitBlockFace) {
				BlockFace.UP, BlockFace.DOWN -> direction.y *= -1
				BlockFace.SOUTH, BlockFace.NORTH -> direction.x *= -1
				BlockFace.WEST, BlockFace.EAST -> direction.z *= -1
				else -> {}
			}
		}
		val velocity = direction.normalize().multiply(speed)
		location.add(velocity)
		entity.teleport(location)
		System.out.println("TEST " + location + " " + entity.location)
	}
}