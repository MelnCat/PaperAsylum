package dev.melncat.paperasylum.behavior

import com.destroystokyo.paper.ParticleBuilder
import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.registry.PAItem
import net.kyori.adventure.sound.Sound
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.component.DataComponents
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.Item
import net.minecraft.world.item.component.ItemAttributeModifiers
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitScheduler
import xyz.xenondevs.commons.provider.provider
import xyz.xenondevs.nova.util.ResourceLocation
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior
import java.util.UUID

class HeldMusic(
	musicKey: String
) : ItemBehavior {
	private var musicPlaying = mutableMapOf<UUID, Boolean>()
	
	private val music = Sound.sound()
		.volume(0.8f)
		.type(PaperAsylum.key(musicKey))
		.build()
	
	private fun checkInventory(player: Player, itemStack: ItemStack) {
		if (musicPlaying[player.uniqueId] != true && player.inventory.itemInMainHand == itemStack) {
			musicPlaying[player.uniqueId] = true
			player.playSound(music, Sound.Emitter.self())
			(object : BukkitRunnable() {
				override fun run() {
					if (player.inventory.itemInMainHand != itemStack && musicPlaying[player.uniqueId] == true) {
						musicPlaying[player.uniqueId] = false
						player.stopSound(music)
						cancel()
					}
				}
			}).runTaskTimer(PaperAsylum.plugin!!, 0L, 1L)
		}
	}
	
	override fun handleInventoryTick(player: Player, itemStack: ItemStack, slot: Int) {
		checkInventory(player, itemStack)
	}
}