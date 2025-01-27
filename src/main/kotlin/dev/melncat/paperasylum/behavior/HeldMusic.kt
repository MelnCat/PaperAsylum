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
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.commons.provider.provider
import xyz.xenondevs.nova.util.ResourceLocation
import xyz.xenondevs.nova.world.item.behavior.ItemBehavior

class HeldMusic(
	musicKey: String
) : ItemBehavior {
	private var musicPlaying = false
	
	private val music = Sound.sound()
		.volume(0.8f)
		.type(PaperAsylum.key(musicKey))
		.build()
	
	override fun handleInventoryTick(player: Player, itemStack: ItemStack, slot: Int) {
		if (player.inventory.itemInMainHand != itemStack && musicPlaying) {
			musicPlaying = false
			player.stopSound(music)
		}
		else if (!musicPlaying && player.inventory.itemInMainHand == itemStack) {
			musicPlaying = true
			player.playSound(music, Sound.Emitter.self())
		}
	}
}