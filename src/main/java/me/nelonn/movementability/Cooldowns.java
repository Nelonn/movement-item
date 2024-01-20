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

import me.nelonn.movementability.utility.DeltaTimer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cooldowns {
    private static final DeltaTimer deltaTimer = new DeltaTimer();
    private static final Map<UUID, Cooldowns> cooldowns = new HashMap<>();

    public static void tick() {
        float deltaTime = deltaTimer.tick();
        cooldowns.values().forEach(it -> it.tick(deltaTime));
    }

    public static Cooldowns getCooldowns(ServerPlayer player) {
        return cooldowns.computeIfAbsent(player.getUUID(), uuid -> new Cooldowns());
    }

    public final Map<ResourceLocation, CooldownInstance> map = new HashMap<>();

    public boolean isOnCooldown(ResourceLocation id) {
        return this.getCooldown(id) > 0.0F;
    }

    public float getCooldown(ResourceLocation id) {
        CooldownInstance cooldown = map.get(id);
        return cooldown == null ? 0 : cooldown.timeLeft;
    }

    public void tick(float deltaTime) {
        if (this.map.isEmpty()) return;
        for (Map.Entry<ResourceLocation, CooldownInstance> entry : map.entrySet()) {
            CooldownInstance cooldown = entry.getValue();
            cooldown.timeLeft = Math.max(cooldown.timeLeft - deltaTime, 0);
            if (cooldown.timeLeft == 0) {
                map.remove(entry.getKey());
            }
        }
    }

    public void addCooldown(ResourceLocation id, int duration) {
        this.map.put(id, new CooldownInstance(duration));
    }

    public void removeCooldown(ResourceLocation id) {
        this.map.remove(id);
    }

    public static class CooldownInstance {
        public final float duration;
        public float timeLeft;

        public CooldownInstance(float duration) {
            this.duration = duration;
            this.timeLeft = duration;
        }
    }

}
