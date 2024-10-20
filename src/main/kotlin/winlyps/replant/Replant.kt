package winlyps.replant

import org.bukkit.plugin.java.JavaPlugin

class Replant : JavaPlugin() {

    override fun onEnable() {
        // Register the event listener
        server.pluginManager.registerEvents(ReplantListener(this), this)
        logger.info("Replant plugin enabled.")
    }

    override fun onDisable() {
        logger.info("Replant plugin disabled.")
    }
}