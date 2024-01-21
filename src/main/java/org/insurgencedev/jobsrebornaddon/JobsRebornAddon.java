package org.insurgencedev.jobsrebornaddon;

import org.bukkit.Bukkit;
import org.insurgencedev.insurgenceboosters.api.addon.IBoostersAddon;
import org.insurgencedev.insurgenceboosters.api.addon.InsurgenceBoostersAddon;
import org.insurgencedev.jobsrebornaddon.listeners.JobsEventListener;

@IBoostersAddon(name = "JobsRebornAddon", version = "1.0.1", author = "InsurgenceDev", description = "JobsReborn Support")
public class JobsRebornAddon extends InsurgenceBoostersAddon {

    @Override
    public void onAddonReloadablesStart() {
        if (Bukkit.getPluginManager().isPluginEnabled("Jobs")) {
            registerEvent(new JobsEventListener());
        }
    }
}
