package ru.lazytechwork.tpcontrol;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.lazytechwork.tpcontrol.proxy.CommonProxy;

@Mod(modid = TPControl.MODID, version = TPControl.VERSION, name = TPControl.NAME, useMetadata = false, acceptedMinecraftVersions = "[1.12.2]")
public class TPControl {

    public static final String MODID = "tpcontrol";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "TPControl";

    @SidedProxy(clientSide = "ru.lazytechwork.tpcontrol.proxy.ClientProxy", serverSide = "ru.lazytechwork.tpcontrol.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
