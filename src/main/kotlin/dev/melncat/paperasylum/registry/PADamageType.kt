package dev.melncat.paperasylum.registry

import io.papermc.paper.registry.keys.DamageTypeKeys
import net.kyori.adventure.key.Key

object PADamageType {
	val NO_COOLDOWN = DamageTypeKeys.create(Key.key("paperasylum:no_cooldown"))
}