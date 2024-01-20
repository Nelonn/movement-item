package com.example.plugin;

import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.Contract;

public final class PaperAdapter {

    @Contract("null -> null; !null -> !null")
    public static ServerPlayer adapt(HumanEntity player) {
        if (player == null) return null;
        return ((CraftPlayer) player).getHandle();
    }

    private PaperAdapter() {
        throw new UnsupportedOperationException();
    }
}
