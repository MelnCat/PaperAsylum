package dev.melncat.paperasylum.registry

import dev.melncat.paperasylum.PaperAsylum
import dev.melncat.paperasylum.attachment.HitboxWeaponAttachment
import xyz.xenondevs.nova.addon.registry.AttachmentTypeRegistry
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitStage
import xyz.xenondevs.nova.world.player.attachment.ItemAttachment

@Init(stage = InitStage.PRE_PACK)
object PAAttachment : AttachmentTypeRegistry by PaperAsylum.registry {
	
	//val BIRCH_TREE_ATTACHMENT = registerAttachmentType("birch_tree_attachment") { HitboxWeaponAttachment() }
	
}