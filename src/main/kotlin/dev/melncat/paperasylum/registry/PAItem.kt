package dev.melncat.paperasylum.registry

import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.behavior.HealOnConsume
import dev.melncat.paperasylum.behavior.HeldMusic
import dev.melncat.paperasylum.behavior.ItemCooldown
import dev.melncat.paperasylum.behavior.MeleeBehavior
import dev.melncat.paperasylum.item.melee.BirchTree
import dev.melncat.paperasylum.item.misc.UltraInstinct
import dev.melncat.paperasylum.item.ranged.America
import dev.melncat.paperasylum.item.ranged.Bible
import dev.melncat.paperasylum.item.ranged.Chicago
import dev.melncat.paperasylum.item.ranged.DeathNote
import dev.melncat.paperasylum.item.ranged.DistressedRedBall
import dev.melncat.paperasylum.item.ranged.London
import dev.melncat.paperasylum.item.ranged.Wand
import dev.melncat.paperasylum.physics.PhysicsManager
import dev.melncat.paperasylum.physics.PointPhysical
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.minecraft.world.item.ItemUseAnimation
import org.bukkit.Material
import org.bukkit.damage.DamageSource
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.addon.registry.ItemRegistry
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitStage
import xyz.xenondevs.nova.world.item.behavior.Consumable
import xyz.xenondevs.nova.world.item.behavior.Cooldown
import xyz.xenondevs.nova.world.item.behavior.Equippable
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import xyz.xenondevs.nova.world.item.behavior.Tool
import xyz.xenondevs.nova.world.player.WrappedPlayerInteractEvent

@Init(stage = InitStage.PRE_PACK)
object PAItem : ItemRegistry by PaperAsylum.registry {
	val LONDON = item("london") {
		behaviors(
			MeleeBehavior(2.4, 0.7, 0.001),
			London(),
			HeldMusic("item.london.music")
		)
	}
	val CHICAGO = item("chicago") {
		behaviors(
			Chicago(),
			HeldMusic("item.chicago.music"),
			ItemCooldown(0.125f)
		)
	}
	val AMERICA = item("america") {
		behaviors(
			America(),
			HeldMusic("item.america.music")
		)
	}
	val AIR = item("air") {
		behaviors(
			MeleeBehavior(5.0, 1.0, 0.001)
		)
	}
	val APPLE = item("apple") {
		behaviors(
			Consumable(
				consumeTime = 10,
				nutrition = 0,
				canAlwaysEat = true
			),
			HealOnConsume(3.0)
		)
	}
	val ULTRA_INSTINCT = item("ultra_instinct") {
		behaviors(
			Equippable(PAEquipment.ULTRA_INSTINCT, EquipmentSlot.CHEST),
			UltraInstinct()
		)
	}
	val BIRCH_TREE = item("birch_tree") {
		behaviors(
			BirchTree()
		)
	}
	val BIBLE = item("bible") {
		behaviors(
			ItemCooldown(8f),
			Bible()
		)
	}
	val WAND = item("wand") {
		behaviors(
			ItemCooldown(3f),
			Wand()
		)
	}
	val DEATH_NOTE = item("death_note") {
		behaviors(
			DeathNote()
		)
	}
	val DISTRESSED_RED_BALL = item("distressed_red_ball") {
		behaviors(
			DistressedRedBall(),
			HeldMusic("item.distressed_red_ball.equip")
		)
	}
	
}