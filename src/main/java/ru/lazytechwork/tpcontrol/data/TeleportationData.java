package ru.lazytechwork.tpcontrol.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import ru.lazytechwork.tpcontrol.TPControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeleportationData extends WorldSavedData {

    private static final String DATA_NAME = TPControl.MODID + "_tpdata";
    private HashMap<String, Integer> teleportCounts;

    public TeleportationData(String name) {
        super(name);
    }

    public TeleportationData() {
        super(DATA_NAME);
        this.teleportCounts = new HashMap<String, Integer>();
        markDirty();
    }

    public static TeleportationData get(World world) {
        MapStorage storage = world.getMapStorage();
        assert storage != null;
        TeleportationData data = (TeleportationData) storage.getOrLoadData(TeleportationData.class, DATA_NAME);
        if (data == null) {
            data = new TeleportationData();
            storage.setData(DATA_NAME, data);
        }
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        TPControl.LOGGER.info("readFromNBT");
        if (nbt.hasKey("teleportations")) {
            this.teleportCounts.clear();
            NBTTagList tagList = nbt.getTagList("teleportations", Constants.NBT.TAG_COMPOUND);
            for (int i = 0, tagCount = tagList.tagCount(); i < tagCount; i++) {
                NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
                this.teleportCounts.put(tagCompound.getString("nickname"), tagCompound.getInteger("tpcount"));
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        TPControl.LOGGER.info("writeToNBT");
        NBTTagList tagList = new NBTTagList();
        ArrayList<String> resultString = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : this.teleportCounts.entrySet()) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setString("nickname", entry.getKey());
            tagCompound.setInteger("tpcount", entry.getValue());
            tagList.appendTag(tagCompound);
        }
        compound.setTag("teleportations", tagList);
        return compound;
    }

    public HashMap<String, Integer> getTeleportCounts() {
        return this.teleportCounts;
    }

    public void setTeleportCounts(HashMap<String, Integer> teleportCounts) {
        this.teleportCounts = teleportCounts;
        markDirty();
    }

    @Override
    public String toString() {
        ArrayList<String> resultString = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : this.teleportCounts.entrySet())
            resultString.add(entry.getKey() + ":" + entry.getValue());
        return String.join(";", resultString);
    }
}
