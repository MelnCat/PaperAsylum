package dev.melncat.paperasylum.item.ranged

import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.behavior.ItemCooldown
import dev.melncat.paperasylum.physics.PhysicsManager
import dev.melncat.paperasylum.physics.PointPhysical
import dev.melncat.paperasylum.registry.PADamageType
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.kyori.adventure.sound.Sound
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.component.UseCooldown
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.damage.DamageSource
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.commons.provider.provider
import xyz.xenondevs.nova.util.item.novaItem
import xyz.xenondevs.nova.util.runTaskLater
import xyz.xenondevs.nova.world.item.behavior.Cooldown
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import xyz.xenondevs.nova.world.player.WrappedPlayerInteractEvent
import java.util.*

class DistressedRedBall : ItemBehavior {
	private val useSound = Sound.sound()
		.type(PaperAsylum.key("item.distressed_red_ball.use"))
		.volume(0.7f)
		.build()
	private val bounceSound = Sound.sound()
		.type(PaperAsylum.key("item.distressed_red_ball.bounce"))
		.volume(0.3f)
		.build()
	
	override fun handleInteract(player: Player, itemStack: ItemStack, action: Action, wrappedEvent: WrappedPlayerInteractEvent) {
		if (!action.isRightClick) return
		player.playSound(useSound, Sound.Emitter.self())
		PhysicsManager.addPhysical(PointPhysical(
			ItemStack(Material.RED_CONCRETE), player.eyeLocation, player.eyeLocation.direction, 20 * 10, { entity, hit ->
			if (hit is LivingEntity) {
				hit.damage(
					1.0, DamageSource.builder(
					RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE).get(PADamageType.NO_COOLDOWN)!!
				).withDirectEntity(player).build()
				)
			}
			entity.location.world.playSound(bounceSound, entity.location.x, entity.location.y, entity.location.z)
			
		}, player.uniqueId, 2.0))
	}
}