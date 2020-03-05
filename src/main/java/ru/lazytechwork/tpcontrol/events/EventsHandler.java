package ru.lazytechwork.tpcontrol.events;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
            if (Objects.equals(pars[0], sender.getName()))
                return;
            key = pars[0];
            tpcount = data.getOrDefault(pars[0], 0) + 1;
        } else if (pars.length >= 2) {
            if (Objects.equals(pars[0], pars[1]))
                return;
            key = pars[1];
            tpcount = data.getOrDefault(pars[1], 0) + 1;
        }
        if (key == null)
            return;
        data.put(key, tpcount);
        tpdata.setTeleportCounts(data);
        AdvancementManager.TELEPORT_TRIGGER.trigger((EntityPlayerMP) event.getSender().getCommandSenderEntity(), tpcount);
    }

    @SubscribeEvent
    public void onWorldJoin(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityPlayerMP))
            return;
        EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
        Integer tpcounts = TeleportationData.get(player.getEntityWorld()).getTeleportCounts().get(player.getName());
        if (tpcounts == null)
            tpcounts = 0;
        player.sendMessage(new TextComponentString(I18n.format("messages.tpcontrol.tpstats",
                (ChatFormatting.BOLD.toString() + ChatFormatting.GOLD.toString() + player.getName() +
                        ChatFormatting.RESET.toString()), (ChatFormatting.BOLD.toString() +
                        ChatFormatting.RED.toString() + tpcounts + ChatFormatting.RESET.toString()))));

        if (player.getName().equals("Lololowka"))
            player.sendMessage(new TextComponentString("Made specially for "
                    + ChatFormatting.GOLD + "Lololoshka" + ChatFormatting.RESET +
                    " from Ivan Petrov (" + ChatFormatting.LIGHT_PURPLE + "YT WineGear" + ChatFormatting.RESET + ") ;)"));
    }
}
