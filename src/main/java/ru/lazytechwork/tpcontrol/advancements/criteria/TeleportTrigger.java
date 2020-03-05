package ru.lazytechwork.tpcontrol.advancements.criteria;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.sun.istack.internal.NotNull;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import ru.lazytechwork.tpcontrol.TPControl;

import java.util.*;

public class TeleportTrigger implements ICriterionTrigger<TeleportTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(TPControl.MODID, "win_minigame");
    private final Map<PlayerAdvancements, Listeners> listeners = new HashMap<>();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {
        Listeners listeners = this.listeners.get(playerAdvancementsIn);

        if (listeners == null) {
            listeners = new Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, listeners);
        }

        listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<Instance> listener) {
        Listeners listeners = this.listeners.get(playerAdvancementsIn);

        if (listeners != null) {
            listeners.remove(listener);

            if (listeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    /**
     * Deserialize a ICriterionInstance of this trigger from the data in the JSON.
     */
    public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        int teleportations = 0;
        if (json.has("teleportations")) {
            teleportations = JsonUtils.getInt(json, "teleportations");
        }

        return new Instance(teleportations);
    }

    public void trigger(EntityPlayerMP player, int teleportations) {
        Listeners listeners = this.listeners.get(player.getAdvancements());

        if (listeners != null) {
            listeners.trigger(teleportations);
        }
    }

    static class Instance extends AbstractCriterionInstance {
        private final int TELEPORTATIONS;

        Instance(@NotNull int teleportations) {
            super(ID);
            this.TELEPORTATIONS = teleportations;
        }

        boolean test(int teleportations) {
            return teleportations >= this.TELEPORTATIONS;
        }
    }

    static class Listeners {
        private final PlayerAdvancements playerAdvancements;
        private final Set<Listener<Instance>> listeners = new HashSet<>();

        Listeners(PlayerAdvancements playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(Listener<Instance> listener) {
            this.listeners.add(listener);
        }

        void remove(Listener<Instance> listener) {
            this.listeners.remove(listener);
        }

        void trigger(int teleportations) {
            List<Listener<Instance>> list = null;

            for (Listener<Instance> listener : this.listeners) {
                if (listener.getCriterionInstance().test(teleportations)) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for (Listener<Instance> listener1 : list) {
                    listener1.grantCriterion(this.playerAdvancements);
                }
            }
        }
    }
}