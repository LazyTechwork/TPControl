package ru.lazytechwork.tpcontrol.utils;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import ru.lazytechwork.tpcontrol.TPControl;

import java.lang.reflect.Method;

public class ArtisanRegistry {
    private static Method CriterionRegister;

    /*
    public static <T extends ICriterionInstance> ICriterionTrigger<T> registerAdvancementTrigger(ICriterionTrigger<T> trigger) {
        if (CriterionRegister == null) {
            CriterionRegister = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);
            CriterionRegister.setAccessible(true);
        }
        try {
            trigger = (ICriterionTrigger<T>) CriterionRegister.invoke(null, trigger);
            TPControl.LOGGER.info("Registering trigger: " + trigger.getId());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.out.println("Failed to register trigger " + trigger.getId() + "!");
            e.printStackTrace();
        }
        return trigger;
    }*/

    public static <T extends ICriterionTrigger> void registerAdvancementTrigger(T trigger) {
        TPControl.LOGGER.info("Registering trigger: " + trigger.getId());
        CriteriaTriggers.register(trigger);
    }
}
