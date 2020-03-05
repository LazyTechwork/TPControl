package ru.lazytechwork.tpcontrol.advancements;

import ru.lazytechwork.tpcontrol.advancements.criteria.TeleportTrigger;
import ru.lazytechwork.tpcontrol.utils.ArtisanRegistry;

public class AdvancementManager {
    public static final TeleportTrigger TELEPORT_TRIGGER = new TeleportTrigger();
    
    public static void registerCriteria () {
        ArtisanRegistry.registerAdvancementTrigger(TELEPORT_TRIGGER);
    }
}
