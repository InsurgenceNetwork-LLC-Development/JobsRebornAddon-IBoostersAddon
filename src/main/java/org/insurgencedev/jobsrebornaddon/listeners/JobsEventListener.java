package org.insurgencedev.jobsrebornaddon.listeners;

import com.gamingmesh.jobs.api.JobsExpGainEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.models.booster.GlobalBoosterManager;
import org.insurgencedev.insurgenceboosters.settings.IBoostersPlayerCache;

import java.util.Objects;

public final class JobsEventListener implements Listener {

    @EventHandler
    public void onGain(JobsExpGainEvent event) {
        String type = "Jobs";
        final String NAMESPACE = "JOBS_REBORN";
        double totalMulti = 1;

        IBoostersPlayerCache.BoosterFindResult pResult = IBoosterAPI.getCache(Objects.requireNonNull(event.getPlayer().getPlayer()))
                .findActiveBooster(type, NAMESPACE);
        if (pResult instanceof IBoostersPlayerCache.BoosterFindResult.Success boosterResult) {
            totalMulti += getMulti(boosterResult.getBooster().getMultiplier());
        }

        GlobalBoosterManager.BoosterFindResult gResult = IBoosterAPI.getGlobalBoosterManager().findBooster(type, NAMESPACE);
        if (gResult instanceof GlobalBoosterManager.BoosterFindResult.Success boosterResult) {
            totalMulti += getMulti(boosterResult.getBooster().getMultiplier());
        }

        event.setExp(event.getExp() * totalMulti);
    }

    private double getMulti(double amount) {
        return (amount >= 1) ? amount - 1 : amount;
    }
}
