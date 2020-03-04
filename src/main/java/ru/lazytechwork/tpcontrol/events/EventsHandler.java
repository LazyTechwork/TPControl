package ru.lazytechwork.tpcontrol.events;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.lazytechwork.tpcontrol.TPControl;

import java.util.Objects;

public class EventsHandler {
    @SubscribeEvent
    public void onCommand(CommandEvent event) {
        event.getSender().sendMessage(new TextComponentString(event.getCommand().getName() + " " + event.getParameters()[0] + " " + event.getParameters()[1]));
        ICommandSender sender = event.getSender();
        String command = event.getCommand().getName();
        String pars[] = event.getParameters();
        if (pars.length == 1) {
            TPControl.LOGGER.info(sender.getName());
            TPControl.LOGGER.info(sender.getDisplayName());
            if (Objects.equals(pars[0], sender.getName()))
                return;
        }
    }
}
