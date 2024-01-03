package org.insurgencedev.jobsrebornaddon;

import org.insurgencedev.insurgenceboosters.api.addon.IBoostersAddon;
import org.insurgencedev.insurgenceboosters.api.addon.InsurgenceBoostersAddon;
import org.insurgencedev.jobsrebornaddon.listeners.JobsEventListener;

@IBoostersAddon(name = "JobsRebornAddon", version = "1.0.0", author = "InsurgenceDev", description = "JobsReborn Support")
public class JobsRebornAddon extends InsurgenceBoostersAddon {

    @Override
    public void onAddonReloadablesStart() {
        registerEvent(new JobsEventListener());
    }
}
