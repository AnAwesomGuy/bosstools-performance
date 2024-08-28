package net.anawesomguy.bosstools_perf.mixin;

import net.anawesomguy.bosstools_perf.PerformanceMethodes;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;

import javax.annotation.Nonnull;

@Pseudo
@Mixin(targets = "net.mrscauthd.boss_tools.events.Methodes$1")
public abstract class Methodes$1Mixin implements INamedContainerProvider {
    /**
     * Allows the planet selection GUI header to be translated at {@code "gui.boss_tools.planet_selection"}.
     *
     * @author AnAwesomGuy
     * @reason performance over questionable Space-BossTools code.
     */
    @Nonnull @Overwrite
    public ITextComponent getDisplayName() {
        return PerformanceMethodes.PLANET_SELECTION;
    }
}
