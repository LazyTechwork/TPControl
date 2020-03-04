package ru.lazytechwork.tpcontrol.events;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventsHandler {
    @SubscribeEvent
    public void onCommand(CommandEvent event) {
        event.getSender().sendMessage(new TextComponentString(event.getCommand().getName() + " " + event.getParameters()[0] + " " + event.getParameters()[1]));
    }
}
