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

public class DeltaTimer {
    private double lastTickTime;

    public DeltaTimer() {
        this.lastTickTime = getCurrentTime();
    }

    private double getCurrentTime() {
        return (double) System.currentTimeMillis() / 1000.0D;
    }

    public float tick() {
        double current = getCurrentTime();
        float deltaTime = (float) (current - this.lastTickTime);
        this.lastTickTime = current;
        return deltaTime;
    }
}
