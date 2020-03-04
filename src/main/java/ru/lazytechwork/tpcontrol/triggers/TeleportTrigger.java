package ru.lazytechwork.tpcontrol.triggers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import ru.lazytechwork.tpcontrol.TPControl;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TeleportTrigger implements ICriterionTrigger<TeleportTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(TPControl.MODID, "teleported");
    private final Map<PlayerAdvancements, TeleportTrigger.Listeners> listeners = Maps.<PlayerAdvancements, TeleportTrigger.Listeners>newHashMap();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<TeleportTrigger.Instance> listener) {
        TeleportTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (consumeitemtrigger$listeners == null) {
            consumeitemtrigger$listeners = new Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, consumeitemtrigger$listeners);
        }

        consumeitemtrigger$listeners.add(listener);
    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<TeleportTrigger.Instance> listener) {
        TeleportTrigger.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (consumeitemtrigger$listeners != null) {
            consumeitemtrigger$listeners.remove(listener);

            if (consumeitemtrigger$listeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
    public TeleportTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        int teleportations = json.get("teleportations").getAsInt();
        return new Instance(teleportations);
    }

    public static class Instance extends AbstractCriterionInstance {
        private int teleportations;

        public Instance(int teleportations) {
            super(TeleportTrigger.ID);
            this.teleportations = teleportations;
        }

        public boolean test(int count) {
            return count >= teleportations;
        }
    }

    public void trigger(EntityPlayerMP player, int count) {
        TeleportTrigger.Listeners enterblocktrigger$listeners = this.listeners.get(player.getAdvancements());

        if (enterblocktrigger$listeners != null) {
            enterblocktrigger$listeners.trigger(count);
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<ICriterionTrigger.Listener<TeleportTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<TeleportTrigger.Instance>>newHashSet();

        public Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(ICriterionTrigger.Listener<TeleportTrigger.Instance> listener) {
            this.listeners.add(listener);
        }

        public void remove(ICriterionTrigger.Listener<TeleportTrigger.Instance> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(int oreCount) {
            List<ICriterionTrigger.Listener<TeleportTrigger.Instance>> list = null;

            for (ICriterionTrigger.Listener<TeleportTrigger.Instance> listener : this.listeners) {
                if (((TeleportTrigger.Instance) listener.getCriterionInstance()).test(oreCount)) {
                    if (list == null) {
                        list = Lists.<ICriterionTrigger.Listener<TeleportTrigger.Instance>>newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for (ICriterionTrigger.Listener<TeleportTrigger.Instance> listener1 : list) {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}