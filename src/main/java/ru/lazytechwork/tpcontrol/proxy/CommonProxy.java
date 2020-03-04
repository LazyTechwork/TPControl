package ru.lazytechwork.tpcontrol.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.lazytechwork.tpcontrol.events.EventsHandler;
import ru.lazytechwork.tpcontrol.triggers.ModTriggers;
import ru.lazytechwork.tpcontrol.triggers.TeleportTrigger;
import ru.lazytechwork.tpcontrol.utils.ArtisanRegistry;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventsHandler()); // Registering Events Handler
        ModTriggers.TELEPORT_TRIGGER = (TeleportTrigger) ArtisanRegistry.registerAdvancementTrigger(new TeleportTrigger());
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
