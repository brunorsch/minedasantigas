package brunorsch.minedasantigas.mods;

import static brunorsch.minedasantigas.utils.internals.CommandMapWrapper.withCommandMap;
import static net.minecraft.server.v1_6_R3.Item.BOOK;
import static net.minecraft.server.v1_6_R3.Item.ENCHANTED_BOOK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.messaging.PluginMessageListener;

import brunorsch.minedasantigas.DasAntigas;
import net.minecraft.server.v1_6_R3.ContainerEnchantTable;
import net.minecraft.server.v1_6_R3.EnchantmentInstance;
import net.minecraft.server.v1_6_R3.EnchantmentManager;
import net.minecraft.server.v1_6_R3.EntityPlayer;
import net.minecraft.server.v1_6_R3.IInventory;
import net.minecraft.server.v1_6_R3.ItemStack;
import net.minecraft.server.v1_6_R3.NBTBase;
import net.minecraft.server.v1_6_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_6_R3.NBTTagCompound;

public class EnchantViewModSupport implements PluginMessageListener {
    public static EnchantViewModSupport instance;
    public static final int STAGE_REQUEST = 0;
    public static final int STAGE_SEND = 1;
    public static final int STAGE_ACCEPT = 2;
    public static Random random = new Random();

    private DasAntigas plugin;
    public boolean enabled = false;
    public Map<String, ItemStack[]> newItemStacksMap = new HashMap<>();
    public Map<String, Map<Enchantment, Integer>> enchMaps = new HashMap<>();
    public boolean checkingItems = false;

    public static EnchantViewModSupport create(DasAntigas plugin) {
        instance = new EnchantViewModSupport();
        instance.plugin = plugin;
        return instance;
    }

    public void onEnable() {
        this.enabled = true;
        this.newItemStacksMap.clear();
        this.enchMaps.clear();
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, "EnchantView");
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, "EnchantView", this);
        withCommandMap(commandMap -> commandMap.register("doesenchantviewexist", getCommand()));
    }

    public void onDisable() {
        this.enabled = false;
        this.newItemStacksMap.clear();
        this.enchMaps.clear();
    }

    public Command getCommand() {
        return new Command("doesenchantviewexist") {
            @Override
            public boolean execute(final CommandSender commandSender, final String s, final String[] strings) {
                if (!instance.enabled) {
                    commandSender.sendMessage("EnchantView is disabled.");
                } else {
                    commandSender.sendMessage("Yes, EnchantView exists.");
                }
                return true;
            }
        };
    }

    public EntityPlayer getEntityPlayerForPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public void onPluginMessageReceived(String channel, Player bukkitPlayer, byte[] data) {
        if (!this.enabled)
            return;
        if (channel.equals("EnchantView")) {
            NBTTagCompound compoundIn = NBTCompressedStreamTools.a(data);
            int stage = compoundIn.getInt("stage");
            EntityPlayer player = getEntityPlayerForPlayer(bukkitPlayer);
            if (player == null)
                return;
            if (!(player.activeContainer instanceof ContainerEnchantTable))
                return;
            ContainerEnchantTable container = (ContainerEnchantTable) player.activeContainer;
            if (stage == 0) {
                ItemStack[] newItemStacks = new ItemStack[3];
                for (int i = 0; i < 3; i++)
                    newItemStacks[i] = generateEnchantedItemStack(container, player, i);
                this.newItemStacksMap.put(player.getName(), newItemStacks);
                NBTTagCompound compoundOut = new NBTTagCompound();
                compoundOut.setInt("stage", 1);
                for (int j = 0; j < 3; j++) {
                    NBTTagCompound stackTag = new NBTTagCompound();
                    newItemStacks[j].save(stackTag);
                    compoundOut.set("stack" + j, (NBTBase) stackTag);
                }
                byte[] toSendData = NBTCompressedStreamTools.a(compoundOut);
                bukkitPlayer.sendPluginMessage(plugin, "EnchantView", toSendData);
            } else {
                int slot = compoundIn.getInt("slot");
                enchantItem(container, player, bukkitPlayer, slot);
                this.newItemStacksMap.remove(player.getName());
            }
        }
    }

    private ItemStack generateEnchantedItemStack(ContainerEnchantTable container, EntityPlayer player, int slot) {
        ItemStack newItemStack = ItemStack.b(container.getSlot(0).getItem());
        if (container.costs[slot] > 0 &&
            newItemStack != null && (
            player.expLevel >= container.costs[slot] || player
                .getBukkitEntity().getGameMode()
                .equals(GameMode.CREATIVE))) {
            List<EnchantmentInstance> enchList = EnchantmentManager.b(random,
                newItemStack, container.costs[slot]);
            if (enchList != null) {
                Map<Enchantment, Integer> enchMap = new HashMap<>();
                this.enchMaps.put(player.getBukkitEntity().getDisplayName(), enchMap);
                boolean isBook = (newItemStack.getItem() == BOOK);
                if (isBook)
                    newItemStack = new ItemStack(BOOK);
                int enchToPick = isBook ? random.nextInt(enchList.size()) : -1;
                for (int i = 0; i < enchList.size(); i++) {
                    EnchantmentInstance enchData = enchList.get(i);
                    if (!isBook || i == enchToPick)
                        if (isBook) {
                            ENCHANTED_BOOK.a(newItemStack, enchData);
                            enchMap.put(Enchantment.getByName(enchData.enchantment.a()),
                                Integer.valueOf(enchData.level));
                        } else {
                            newItemStack.addEnchantment(enchData.enchantment,
                                enchData.level);
                            enchMap.put(Enchantment.getByName(enchData.enchantment.a()),
                                Integer.valueOf(enchData.level));
                        }
                }
            }
        }
        return newItemStack;
    }

    private void enchantItem(ContainerEnchantTable container, EntityPlayer player, Player bukkitPlayer, int slot) {
        Block table = player.getBukkitEntity().getTargetBlock(null, 7);
        if (!table.getType().equals(Material.ENCHANTMENT_TABLE))
            table = null;
        EnchantItemEvent enchantItemEvent = new EnchantItemEvent(bukkitPlayer,
            (InventoryView) container.getBukkitView(), table, container.getBukkitView().getItem(0),
            container.costs[slot], this.enchMaps.get(bukkitPlayer.getDisplayName()), slot);
        Bukkit.getServer().getPluginManager().callEvent(enchantItemEvent);
        if (!enchantItemEvent.isCancelled()) {
            ItemStack stack = ((ItemStack[]) this.newItemStacksMap.get(player.getName()))[slot];
            player.levelDown(-container.costs[slot]);
            container.enchantSlots.setItem(0, stack);
            container.a((IInventory) container.enchantSlots);
        }
        this.enchMaps.remove(bukkitPlayer.getDisplayName());
    }
}