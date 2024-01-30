package org.insurgencedev.jobsrebornaddon.listeners;

import com.gamingmesh.jobs.api.JobsExpGainEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.data.BoosterFindResult;

import java.util.Objects;

public final class JobsEventListener implements Listener {

    @EventHandler
    public void onGain(JobsExpGainEvent event) {
        final String TYPE = "Jobs";
        final String NAMESPACE = "JOBS_REBORN";
        final double[] totalMulti = {0};

        BoosterFindResult pResult = IBoosterAPI.INSTANCE.getCache(Objects.requireNonNull(event.getPlayer().getPlayer()))
                .getBoosterDataManager().findActiveBooster(TYPE, NAMESPACE);

        if (pResult instanceof BoosterFindResult.Success boosterResult) {
            totalMulti[0] += boosterResult.getBoosterData().getMultiplier();
        }

        IBoosterAPI.INSTANCE.getGlobalBoosterManager().findGlobalBooster(TYPE, NAMESPACE, globalBooster -> {
            totalMulti[0] += globalBooster.getMultiplier();
            return null;
        }, () -> null);

        if (totalMulti[0] > 0) {
            event.setExp(calculateAmount(event.getExp(), totalMulti[0]));
        }
    }

    private double calculateAmount(double amount, double multi) {
        return amount * (multi < 1 ? 1 + multi : multi);
    }
}
