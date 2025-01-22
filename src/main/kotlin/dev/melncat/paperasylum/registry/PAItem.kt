package dev.melncat.paperasylum.registry

import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.behavior.MeleeBehavior
import dev.melncat.paperasylum.physics.PhysicsManager
import dev.melncat.paperasylum.physics.PointPhysical
import net.minecraft.world.item.ItemUseAnimation
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.addon.registry.ItemRegistry
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitStage
import xyz.xenondevs.nova.world.item.behavior.Consumable
import xyz.xenondevs.nova.world.item.behavior.Equippable
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import xyz.xenondevs.nova.world.item.behavior.Tool
import xyz.xenondevs.nova.world.player.WrappedPlayerInteractEvent

@Init(stage = InitStage.PRE_PACK)
object PAItem : ItemRegistry by PaperAsylum.registry {
	val LONDON = item("london") {
		modelDefinition {
			buildModel { getModel("item/london") }
		}
		behaviors(
			MeleeBehavior(2.0, 1.0, 0.0)
		)
	}
	val TEST = item("test") {
		modelDefinition {
			buildModel { getModel("item/test") }
		}
		behaviors(
			Consumable(
				consumeTime = 1,
				nutrition = 3,
				animation = ItemUseAnimation.BOW
			)
		)
	}
	val ULTRA_INSTINCT = item("ultra_instinct") {
		modelDefinition {
			buildModel { getModel("item/ultra_instinct") }
		}
		behaviors(
			Equippable(PAEquipment.ULTRA_INSTINCT, EquipmentSlot.CHEST)
		)
	}
	val DISTRESSED_RED_BALL = item("distressed_red_ball") {
		behaviors(
			object : ItemBehavior {
				override fun handleInteract(player: Player, itemStack: ItemStack, action: Action, wrappedEvent: WrappedPlayerInteractEvent) {
					if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return
					PhysicsManager.addPhysical(PointPhysical(ItemStack(Material.RED_CONCRETE), player.location, player.eyeLocation.direction))
				}
			}
		)
	}
	
}