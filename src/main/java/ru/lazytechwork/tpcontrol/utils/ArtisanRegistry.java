package ru.lazytechwork.tpcontrol.utils;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;

public class ArtisanRegistry {
    public static <T extends ICriterionTrigger> void registerAdvancementTrigger(T trigger) {
        CriteriaTriggers.register(trigger);
    }
}
