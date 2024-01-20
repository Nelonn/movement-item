/*
 * This file is part of MovementItem plugin for coprolite
 * Copyright (C) 2024 Michael Neonov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.nelonn.movementability;

import me.nelonn.movementability.utility.PaperAdapter;
import net.kyori.adventure.text.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.util.function.Consumer;

public class EventListener implements Listener {
    private static final ResourceLocation COOLDOWN_ID = new ResourceLocation(Main.ID, "fly");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");

    private final Plugin plugin;

    public EventListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void on(PlayerSwapHandItemsEvent event) {
        ServerPlayer player = PaperAdapter.adapt(event.getPlayer());
        event.setCancelled(true);

        Cooldowns cooldowns = Cooldowns.getCooldowns(player);
        float cooldown = cooldowns.getCooldown(COOLDOWN_ID);
        if (cooldown > 0) {
            player.getBukkitEntity().sendActionBar(Component.text("Cooldown left: " + DECIMAL_FORMAT.format(cooldown) + "s"));
            return;
        }
        cooldowns.addCooldown(COOLDOWN_ID, 20); // seconds
        throwPlayer(player, 0.0F, 0.9F, 0.0F);
        Bukkit.getScheduler().runTaskTimer(plugin, new Consumer<>() {
            private int ticks = 0;

            @Override
            public void accept(BukkitTask task) {
                if (!player.isAlive()) return;
                ((ServerLevel) player.level()).sendParticles(ParticleTypes.WITCH, player.getX(), player.getY(), player.getZ(), 6, 0.1, 0.1, 0.1, 0.1);
                ticks++;
                if (ticks == 10) {
                    throwPlayer(player, calculateViewVector(0, player.getYRot()).scale(0.75));
                }
                if (ticks >= 16) {
                    task.cancel();
                }
            }
        }, 1, 1);
    }

    public static Vec3 calculateViewVector(float pitch, float yaw) {
        float f2 = pitch * 0.017453292F;
        float f3 = -yaw * 0.017453292F;
        float f4 = Mth.cos(f3);
        float f5 = Mth.sin(f3);
        float f6 = Mth.cos(f2);
        float f7 = Mth.sin(f2);

        return new Vec3((double) (f5 * f6), (double) (-f7), (double) (f4 * f6));
    }

    public void throwPlayer(ServerPlayer player, Vec3 vec3) {
        throwPlayer(player, (float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public void throwPlayer(ServerPlayer player, float dx, float dy, float dz) {
        player.setDeltaMovement(player.getDeltaMovement().add(dx, dy, dz));
        ClientboundSetEntityMotionPacket packet = new ClientboundSetEntityMotionPacket(player);
        player.connection.send(packet);
    }

}
