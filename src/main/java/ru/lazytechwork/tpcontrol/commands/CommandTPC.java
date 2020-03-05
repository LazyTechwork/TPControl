package ru.lazytechwork.tpcontrol.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import ru.lazytechwork.tpcontrol.data.TeleportationData;

public class CommandTPC extends CommandBase {

    public static final CommandTPC COMMAND_TPC = new CommandTPC();

    @Override
    public String getName() {
        return "tpc";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    public static void execution(ICommandSender sender) {
        EntityPlayerMP player = (EntityPlayerMP) sender.getCommandSenderEntity();
        Integer tpcounts = TeleportationData.get(sender.getEntityWorld()).getTeleportCounts().get(player.getName());
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

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        execution(sender);
    }
}
