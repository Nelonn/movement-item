package com.example.plugin;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class EventListener implements Listener {

    @EventHandler
    private void on(PlayerSwapHandItemsEvent event) {
        ServerPlayer player = PaperAdapter.adapt(event.getPlayer());
        player.sendSystemMessage(Component.literal("PRESSED F"));
    }

}
