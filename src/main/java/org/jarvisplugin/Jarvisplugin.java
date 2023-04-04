package org.jarvisplugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class Jarvisplugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("jarvisplugin.reload")).setExecutor(new ReloadCommandExecutor());
    }

    public Component formatMessage(String message) {
        return Component.text(message, NamedTextColor.AQUA);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String welcomeMessage = getConfig().getString("welcomeMessage", "Welcome back to the server!");
        player.sendActionBar(formatMessage(welcomeMessage));
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("tonystark.jarvis")) {
            return;
        }

        String inputMessage = event.getMessage();

        Bukkit.getScheduler().runTask(this, () -> {
            ConfigurationSection triggersSection = getConfig().getConfigurationSection("triggers");
            if (triggersSection != null) {
                for (String key : triggersSection.getKeys(false)) {
                    ConfigurationSection triggerSection = triggersSection.getConfigurationSection(key);
                    if (triggerSection != null) {
                        List<String> triggerMessages = triggerSection.getStringList("trigger");
                        String response = triggerSection.getString("response");
                        List<String> commandsToExecute = triggerSection.getStringList("commands");

                        if (triggerMessages.contains(inputMessage)) {
                            String jarvisName = getConfig().getString("jarvisName", "Jarvis");
                            Component jarvisResponse = formatMessage(jarvisName + ": " + response);
                            player.sendMessage(jarvisResponse);

                            if (!commandsToExecute.isEmpty()) {
                                for (String command : commandsToExecute) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                                }
                            }
                            return;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
    }

    private class ReloadCommandExecutor implements CommandExecutor {
        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be executed by a player.");
                return false;
            }

            Player player = (Player) sender;
            if (player.hasPermission("jarvisplugin.reload")) {
                reloadConfig();
                player.sendMessage(ChatColor.GREEN + "Configuration reloaded.");
                return true;
            }
            return false;
        }
    }
}
