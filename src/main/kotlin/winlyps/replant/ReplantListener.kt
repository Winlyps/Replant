package winlyps.replant

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.Ageable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class ReplantListener(private val plugin: Plugin) : Listener {

    private val cropMapping: Map<String, Material> = mapOf(
            "wheat" to Material.WHEAT,
            "carrots" to Material.CARROTS,
            "potatoes" to Material.POTATOES,
            "beetroot" to Material.BEETROOTS,
            "nether wart" to Material.NETHER_WART
    )

    private val delay: Long = 10L

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val block: Block = event.block
        val material: Material = block.type

        // Check if the broken block is a crop
        if (cropMapping.values.contains(material)) {
            val crop: Ageable = block.blockData as Ageable

            // Check if the crop is fully grown
            if (crop.age > 0) {
                // Drop the crop items
                val drops: Collection<ItemStack> = block.getDrops()
                for (drop in drops) {
                    block.world.dropItemNaturally(block.location, drop)
                }

                // Replant the crop after a delay
                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    block.type = material
                    val newCrop: Ageable = block.blockData as Ageable
                    newCrop.age = 0
                    block.blockData = newCrop
                }, delay)
            }
        }
    }
}