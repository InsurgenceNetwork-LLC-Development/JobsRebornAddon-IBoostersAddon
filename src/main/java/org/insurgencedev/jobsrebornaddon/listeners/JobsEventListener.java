package org.insurgencedev.jobsrebornaddon.listeners;

import com.gamingmesh.jobs.api.JobsPaymentEvent;
import com.gamingmesh.jobs.container.CurrencyType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.data.BoosterFindResult;

import java.util.Objects;

public final class JobsEventListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPay(JobsPaymentEvent event) {
        final String TYPE = "Exp";
        final String NAMESPACE = "JOBS_REBORN";
        final double[] totalMulti = {0};
        if (event.getPayment().get(CurrencyType.EXP) <= 0) {
            return;
        }

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
            event.set(CurrencyType.EXP, calculateAmount(event.get(CurrencyType.EXP), totalMulti[0]));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPay2(JobsPaymentEvent event) {
        final String TYPE = "Money";
        final String NAMESPACE = "JOBS_REBORN";
        final double[] totalMulti = {0};
        if (event.getPayment().get(CurrencyType.MONEY) <= 0) {
            return;
        }

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
            event.set(CurrencyType.MONEY, calculateAmount(event.get(CurrencyType.MONEY), totalMulti[0]));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPay3(JobsPaymentEvent event) {
        final String TYPE = "Points";
        final String NAMESPACE = "JOBS_REBORN";
        final double[] totalMulti = {0};
        if (event.getPayment().get(CurrencyType.POINTS) <= 0) {
            return;
        }

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
            event.set(CurrencyType.POINTS, calculateAmount(event.get(CurrencyType.POINTS), totalMulti[0]));
        }
    }

    private double calculateAmount(double amount, double multi) {
        return amount * (multi <= 1 ? 1 + multi : multi);
    }
}
