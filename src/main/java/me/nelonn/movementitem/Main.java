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

package me.nelonn.movementitem;

import me.nelonn.coprolite.api.PluginContainer;
import me.nelonn.coprolite.api.PluginInitializer;
import me.nelonn.coprolite.paper.CoprolitePlugin;

public class Main implements PluginInitializer {
    public static final String ID = "example-plugin";

    @Override
    public void onInitialize(PluginContainer pluginContainer) {
        CoprolitePlugin.ON_ENABLE.register(plugin -> {
            plugin.getServer().getPluginManager().registerEvents(new EventListener(), plugin);
        });
    }
}