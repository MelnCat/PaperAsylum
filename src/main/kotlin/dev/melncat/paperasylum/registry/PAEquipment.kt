package dev.melncat.paperasylum.registry

import dev.melncat.paperasylum.PaperAsylum
import net.minecraft.advancements.critereon.EntityEquipmentPredicate.Builder.equipment
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration.layer
import xyz.xenondevs.nova.addon.registry.EquipmentRegistry
import xyz.xenondevs.nova.initialize.Init
import xyz.xenondevs.nova.initialize.InitStage

@Init(stage = InitStage.PRE_PACK)
object PAEquipment : EquipmentRegistry by PaperAsylum.registry {
	
	val ULTRA_INSTINCT = equipment("ultra_instinct") {
		humanoid  {
			layer {
				texture("ultra_instinct")
			}
		}
	}
	
}