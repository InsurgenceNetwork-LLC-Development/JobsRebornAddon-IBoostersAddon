package org.insurgencedev.jobsrebornaddon.listeners;

import com.gamingmesh.jobs.api.JobsPaymentEvent;
import com.gamingmesh.jobs.container.CurrencyType;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.data.BoosterFindResult;
import org.insurgencedev.insurgenceboosters.data.PermanentBoosterData;

import java.util.Optional;

public final class JobsEventListener implements Listener {

    private final String namespace = "JOBS_REBORN";

    @EventHandler(ignoreCancelled = true)
    public void onPay(JobsPaymentEvent event) {
        if (event.getPayment().get(CurrencyType.EXP) <= 0) {
            return;
        }

        double multi = getMulti(event.getPlayer().getPlayer(), "Exp");

        if (multi > 0) {
            event.set(CurrencyType.EXP, calculateAmount(event.get(CurrencyType.EXP), multi));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPay2(JobsPaymentEvent event) {
        if (event.getPayment().get(CurrencyType.MONEY) <= 0) {
            return;
        }

        double multi = getMulti(event.getPlayer().getPlayer(), "Money");

        if (multi > 0) {
            event.set(CurrencyType.MONEY, calculateAmount(event.get(CurrencyType.MONEY), multi));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPay3(JobsPaymentEvent event) {
        if (event.getPayment().get(CurrencyType.POINTS) <= 0) {
            return;
        }

        double multi = getMulti(event.getPlayer().getPlayer(), "Points");

        if (multi > 0) {
            event.set(CurrencyType.POINTS, calculateAmount(event.get(CurrencyType.POINTS), multi));
        }
    }

    private double getMulti(Player player, String type) {
        AtomicDouble totalMulti = new AtomicDouble(getPersonalPermMulti(player, type) + getGlobalPermMulti(type));

        BoosterFindResult pResult = IBoosterAPI.INSTANCE.getCache(player).getBoosterDataManager().findActiveBooster(type, namespace);
        if (pResult instanceof BoosterFindResult.Success boosterResult) {
            totalMulti.getAndAdd(boosterResult.getBoosterData().getMultiplier());
        }

        IBoosterAPI.INSTANCE.getGlobalBoosterManager().findGlobalBooster(type, namespace, globalBooster -> {
            totalMulti.getAndAdd(globalBooster.getMultiplier());
            return null;
        }, () -> null);

        return totalMulti.get();
    }

    private double getPersonalPermMulti(Player uuid, String type) {
        Optional<PermanentBoosterData> foundMulti = Optional.ofNullable(IBoosterAPI.INSTANCE.getCache(uuid).getPermanentBoosts().getPermanentBooster(type, namespace));
        return foundMulti.map(PermanentBoosterData::getMulti).orElse(0d);
    }

    private double getGlobalPermMulti(String type) {
        AtomicDouble multi = new AtomicDouble(0d);

        IBoosterAPI.INSTANCE.getGlobalBoosterManager().findPermanentBooster(type, namespace, data -> {
            multi.set(data.getMulti());
            return null;
        }, () -> null);

        return multi.get();
    }

    private double calculateAmount(double amount, double multi) {
        return amount * (multi < 1 ? 1 + multi : multi);
    }
}
