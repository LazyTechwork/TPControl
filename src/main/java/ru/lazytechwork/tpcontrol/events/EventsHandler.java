package ru.lazytechwork.tpcontrol.events;

import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.lazytechwork.tpcontrol.TPControl;

public class EventsHandler {
    @SubscribeEvent
    public void onCommand(CommandEvent event) {
        TPControl.LOGGER.info(event.getCommand().getName());
        TPControl.LOGGER.info(event.getParameters());
    }
}
