package me.eccentric_nz.TARDIS.mobfarming;

import org.bukkit.DyeColor;
import org.bukkit.entity.TropicalFish;

public class TARDISFish extends TARDISMob {

    private TropicalFish.Pattern pattern;
    private DyeColor patternColour;

    public TropicalFish.Pattern getPattern() {
        return pattern;
    }

    public void setPattern(TropicalFish.Pattern pattern) {
        this.pattern = pattern;
    }

    DyeColor getPatternColour() {
        return patternColour;
    }

    void setPatternColour(DyeColor patternColour) {
        this.patternColour = patternColour;
    }
}
