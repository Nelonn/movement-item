package me.nelonn.movementitem;

import me.nelonn.customitems.api.CustomItemsAPI;
import me.nelonn.customitems.api.NestedItem;
import me.nelonn.flint.path.Key;

public final class AllItems {


    private static <T extends NestedItem> T register(String id, T item) {
        CustomItemsAPI.get().itemRegistry().register(Key.of(Main.ID, id), item);
        return item;
    }

    public static void register() {
    }
}
