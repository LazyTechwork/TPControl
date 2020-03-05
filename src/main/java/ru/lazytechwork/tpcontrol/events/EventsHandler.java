package ru.lazytechwork.tpcontrol.events;

import net.minecraft.advancements.Advancement;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.lazytechwork.tpcontrol.TPControl;
import ru.lazytechwork.tpcontrol.data.TeleportationData;
import ru.lazytechwork.tpcontrol.advancements.AdvancementManager;

import java.util.HashMap;
import java.util.Objects;

public class EventsHandler {
    @SubscribeEvent
    public void onCommand(CommandEvent event) {
        ICommandSender sender = event.getSender();
        String command = event.getCommand().getName();
        if (!Objects.equals(command, "tp"))
            return;
        sender.sendMessage(new TextComponentTranslation("tpcontrol:advancements.tpcontrol.popular_one.title"));
        sender.sendMessage(new TextComponentTranslation("advancements.tpcontrol.popular_one.title"));
        sender.sendMessage(new TextComponentTranslation("advancements.story.lava_bucket.title"));
        String pars[] = event.getParameters();
        TeleportationData tpdata = TeleportationData.get(sender.getEntityWorld());
        HashMap<String, Integer> data = tpdata.getTeleportCounts();
        if (pars.length == 1) {
//            if (Objects.equals(pars[0], sender.getName()))
//                return;
            data.put(pars[0], data.getOrDefault(pars[0], 0) + 1);
            AdvancementManager.TELEPORT_TRIGGER.trigger((EntityPlayerMP) Objects.requireNonNull(sender.getCommandSenderEntity()), data.get(pars[0]));
        } else if (pars.length == 2) {
//            if (Objects.equals(pars[0], pars[1]))
//                return;
            data.put(pars[1], data.getOrDefault(pars[1], 0) + 1);
            AdvancementManager.TELEPORT_TRIGGER.trigger((EntityPlayerMP) Objects.requireNonNull(sender.getCommandSenderEntity()), data.get(pars[1]));
        }
        tpdata.setTeleportCounts(data);
        event.getSender().getServer().getAdvancementManager().reload();
        event.getSender().getServer().getAdvancementManager().getAdvancements().forEach((Advancement advancement) -> TPControl.LOGGER.info(advancement.getId()));
        sender.sendMessage(new TextComponentString(tpdata.toString()));
    }
}
