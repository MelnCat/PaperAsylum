package dev.melncat.paperasylum.bootstrap

import dev.melncat.paperasylum.registry.PADamageType
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.bootstrap.PluginProviderContext
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import io.papermc.paper.registry.RegistryKey
import io.papermc.paper.registry.event.RegistryEvents
import io.papermc.paper.registry.keys.DamageTypeKeys
import io.papermc.paper.registry.keys.tags.DamageTypeTagKeys
import io.papermc.paper.tag.PreFlattenTagRegistrar
import io.papermc.paper.tag.TagEntry
import org.bukkit.damage.DamageEffect
import org.bukkit.damage.DamageScaling
import org.bukkit.damage.DeathMessageType
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.tag.DamageTypeTags
import xyz.xenondevs.nova.registry.NovaRegistryAccess.freeze


class PaperAsylumBootstrap : PluginBootstrap {
	override fun bootstrap(context: BootstrapContext) {
		
		context.lifecycleManager.registerEventHandler(RegistryEvents.DAMAGE_TYPE.freeze().newHandler { event ->
			event.registry().register(
				PADamageType.NO_COOLDOWN
			) {
				it.messageId("paperasylum")
					.damageScaling(DamageScaling.ALWAYS)
					.damageEffect(DamageEffect.HURT)
					.deathMessageType(DeathMessageType.DEFAULT)
					.exhaustion(0f)	
			}
		})
		context.lifecycleManager.registerEventHandler(LifecycleEvents.TAGS.preFlatten(RegistryKey.DAMAGE_TYPE)) { event ->
			event.registrar().addToTag(
				DamageTypeTagKeys.BYPASSES_INVULNERABILITY,
				listOf(TagEntry.valueEntry(DamageTypeKeys.PLAYER_ATTACK), TagEntry.valueEntry(PADamageType.NO_COOLDOWN))
			)
		}
	}
	
	override fun createPlugin(context: PluginProviderContext): JavaPlugin {
		return super.createPlugin(context)
	}
}