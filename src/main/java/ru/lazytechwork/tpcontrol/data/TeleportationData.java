package ru.lazytechwork.tpcontrol.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import ru.lazytechwork.tpcontrol.TPControl;

import java.util.ArrayList;
import java.util.List;

public class TeleportationData extends WorldSavedData {

    private static final String DATA_NAME = TPControl.MODID + "_tpdata";
    private List<TeleportCount> teleportCounts;

    public TeleportationData() {
        super(DATA_NAME);
    }

    public static TeleportationData get(World world) {
        MapStorage storage = world.getMapStorage();
        TeleportationData data = (TeleportationData) storage.getOrLoadData(TeleportationData.class, DATA_NAME);
        if (data == null) {
            data = new TeleportationData();
            data.teleportCounts = new ArrayList<TeleportCount>();
            storage.setData(DATA_NAME, data);
        }
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey(DATA_NAME)) {
            String[] data = nbt.getString(DATA_NAME).split(";");
            for (int i = 0; i < data.length; i++) {
                String[] info = data[i].split(":");
                teleportCounts.add(new TeleportCount(info[0], Integer.parseInt(info[1])));
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        ArrayList<String> resultString = new ArrayList<String>();
        for (int i = 0; i < teleportCounts.size(); i++)
            resultString.add(teleportCounts.get(i).toString());
        compound.setString(DATA_NAME, String.join(";", resultString));
        return compound;
    }
}
