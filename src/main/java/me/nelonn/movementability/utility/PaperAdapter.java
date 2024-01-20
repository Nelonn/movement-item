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

package me.nelonn.movementability.utility;

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
