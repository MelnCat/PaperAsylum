package dev.melncat.paperasylum
import net.kyori.adventure.key.Key
import net.minecraft.tags.DamageTypeTags
import xyz.xenondevs.nova.addon.Addon

object PaperAsylum : Addon() {
	fun key(key: String) = Key.key("paperasylum", key)
}