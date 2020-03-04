package ru.lazytechwork.tpcontrol.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import ru.lazytechwork.tpcontrol.TPControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeleportationData extends WorldSavedData {

    private static final String DATA_NAME = TPControl.MODID + "_tpdata";
    private HashMap<String, Integer> teleportCounts;

    public TeleportationData() {
        super(DATA_NAME);
        markDirty();
    }

    public static TeleportationData get(World world) {
        MapStorage storage = world.getMapStorage();
        assert storage != null;
        TeleportationData data = (TeleportationData) storage.getOrLoadData(TeleportationData.class, DATA_NAME);
        if (data == null) {
            data = new TeleportationData();
            data.teleportCounts = new HashMap<String, Integer>();
            storage.setData(DATA_NAME, data);
        }
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        TPControl.LOGGER.info("readFromNBT");
        if (nbt.hasKey(DATA_NAME)) {
            String[] data = nbt.getString(DATA_NAME).split(";");
            for (int i = 0; i < data.length; i++) {
                String[] info = data[i].split(":");
                TPControl.LOGGER.info(info[0] + " - " + Integer.parseInt(info[1]));
                teleportCounts.put(info[0], Integer.parseInt(info[1]));
            }
            markDirty();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        TPControl.LOGGER.info("writeToNBT");
        compound.setString(DATA_NAME, toString());
        return compound;
    }

    public HashMap<String, Integer> getTeleportCounts() {
        return teleportCounts;
    }

    public void setTeleportCounts(HashMap<String, Integer> teleportCounts) {
        this.teleportCounts = teleportCounts;
        markDirty();
    }

    @Override
    public String toString() {
        ArrayList<String> resultString = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : teleportCounts.entrySet())
            resultString.add(entry.getKey() + ":" + entry.getValue());
        return String.join(";", resultString);
    }
}
