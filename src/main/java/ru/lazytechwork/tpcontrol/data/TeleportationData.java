package ru.lazytechwork.tpcontrol.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;
import ru.lazytechwork.tpcontrol.TPControl;

public class TeleportationData extends WorldSavedData {

    private static final String DATA_NAME = TPControl.MODID + "_tpdata";

    public TeleportationData() {
        super(DATA_NAME);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return null;
    }
}
