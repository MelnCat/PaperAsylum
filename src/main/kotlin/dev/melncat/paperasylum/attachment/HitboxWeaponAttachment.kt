package dev.melncat.paperasylum.attachment

import dev.melncat.paperasylum.item.melee.BirchTree
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.joml.Math
import org.joml.Quaternionf
import org.joml.Vector3f
import org.joml.Vector3fc
import xyz.xenondevs.commons.collections.mapToIntArray
import xyz.xenondevs.nova.util.item.novaItem
import xyz.xenondevs.nova.util.runTaskLater
import xyz.xenondevs.nova.util.send
import xyz.xenondevs.nova.world.fakeentity.impl.FakeItemDisplay
import xyz.xenondevs.nova.world.player.attachment.Attachment
import xyz.xenondevs.nova.world.player.attachment.ItemAttachment

class HitboxWeaponAttachment(
	player: Player, 
	var item: ItemStack,
	translation: Vector3fc = Vector3f(0f, 0f, 0f),
	scale: Vector3fc = Vector3f(1f, 1f, 1f),
) : ItemAttachment(player, item) {
	
	override fun handleTick() {
		super.handleTick()
		passenger.updateEntityData(true) {
			itemStack = item
		}
		//val item = player.inventory.itemInMainHand.novaItem?.getBehavior(BirchTree)
	}
}