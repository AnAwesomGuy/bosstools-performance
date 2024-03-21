package net.anawesomguy.bosstools_performance.mixin;

import net.anawesomguy.bosstools_performance.PerformanceMethodes;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = "net.mrscauthd.boss_tools.events.Methodes$1", remap = false)
public abstract class Methodes$1Mixin {
    /**
     * Allows the planet selection GUI header to be translated at {@code "gui.boss_tools.planet_selection"}.
     *
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Overwrite
    public ITextComponent getDisplayName() {
        return PerformanceMethodes.PLANET_SELECTION;
    }
}
