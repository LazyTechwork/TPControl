package ru.lazytechwork.tpcontrol.events;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.lazytechwork.tpcontrol.advancements.AdvancementManager;
import ru.lazytechwork.tpcontrol.data.TeleportationData;

import java.util.HashMap;
import java.util.Objects;

public class EventsHandler {
    @SubscribeEvent
    public void onCommand(CommandEvent event) {
        ICommandSender sender = event.getSender();
        String command = event.getCommand().getName();
        if (!Objects.equals(command, "tp"))
            return;
        String pars[] = event.getParameters();
        TeleportationData tpdata = TeleportationData.get(sender.getEntityWorld());
        HashMap<String, Integer> data = tpdata.getTeleportCounts();
        String key = null;
        int tpcount = -1;
        if (pars.length == 1) {
//            if (Objects.equals(pars[0], sender.getName()))
//                return;
            key = pars[0];
            tpcount = data.getOrDefault(pars[0], 0) + 1;
        } else if (pars.length == 2) {
//            if (Objects.equals(pars[0], pars[1]))
//                return;
            key = pars[1];
            tpcount = data.getOrDefault(pars[1], 0) + 1;
        }
        if (key == null)
            return;
        data.put(key, tpcount);
        tpdata.setTeleportCounts(data);
        sender.sendMessage(new TextComponentString("Triggering #0, see more in console (Teleportations " + tpcount + ")"));
        AdvancementManager.TELEPORT_TRIGGER.trigger((EntityPlayerMP) event.getSender().getCommandSenderEntity(), tpcount);
        sender.sendMessage(new TextComponentString(tpdata.toString()));
    }
}
