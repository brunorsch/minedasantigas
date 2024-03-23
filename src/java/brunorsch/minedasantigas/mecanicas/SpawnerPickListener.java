package brunorsch.minedasantigas.mecanicas;

import static org.bukkit.Material.GOLD_PICKAXE;
import static org.bukkit.Material.MOB_SPAWNER;
import static org.bukkit.enchantments.Enchantment.SILK_TOUCH;
import static org.bukkit.event.EventPriority.HIGHEST;

import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import lombok.val;

public class SpawnerPickListener implements Listener {
    private static final Material PICK_SPAWNER = GOLD_PICKAXE;
    private static final Enchantment ENCHANTMENT_PICK = SILK_TOUCH;

    @EventHandler(priority = HIGHEST)
    public void onBreakBlock(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        val bloco = event.getBlock();

        if(bloco.getState().getData().getItemType().equals(MOB_SPAWNER)) {
            val itemUsado = event.getPlayer().getItemInHand();
            if(PICK_SPAWNER.equals(itemUsado.getType()) && itemUsado.containsEnchantment(ENCHANTMENT_PICK)) {
                bloco.getLocation().getWorld()
                    .dropItemNaturally(
                        bloco.getLocation(),
                        criarItemSpawner((CreatureSpawner) bloco.getState()));
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = HIGHEST)
    public void onPlaceSpawner(BlockPlaceEvent event) {
        if(event.isCancelled()) {
            return;
        }

        val item = event.getItemInHand();

        if(MOB_SPAWNER.equals(item.getType())) {
            if(item.getDurability() != 0) {
                val spawner = (CreatureSpawner) event.getBlockPlaced().getState();
                spawner.setSpawnedType(EntityType.fromId(item.getDurability()));
            }
        }
    }

    @SuppressWarnings("deprecation")
    private ItemStack criarItemSpawner(CreatureSpawner spawnerData) {
        val mobType = spawnerData.getSpawnedType();
        val item = new ItemStack(MOB_SPAWNER, 1, mobType.getTypeId());

        val meta = item.getItemMeta();
        meta.setDisplayName("§d" + spawnerData.getCreatureTypeName() + " Spawner");
        item.setItemMeta(meta);

        item.setDurability(mobType.getTypeId());

        return item;
    }
}