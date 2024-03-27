package net.anawesomguy.bosstools_performance.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BipedArmorLayer.class)
public abstract class BipedArmorLayerMixin<T extends LivingEntity, A extends BipedModel<T>> {
    @Unique
    private static final ResourceLocation SPACE_SUIT = new ResourceLocation("boss_tools", "textures/models/armor/space_suit_head.png");
    @Unique
    private static final ResourceLocation NETHERITE_SPACE_SUIT = new ResourceLocation("boss_tools", "textures/models/armor/netherite_space_suit_head.png");

    @WrapOperation(method = "renderModel(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IZLnet/minecraft/client/renderer/entity/model/BipedModel;FFFLnet/minecraft/util/ResourceLocation;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;armorCutoutNoCull(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    private RenderType bosstools_performance$armorTranslucent(ResourceLocation armorResource, Operation<RenderType> original) {
        return armorResource.equals(SPACE_SUIT) || armorResource.equals(NETHERITE_SPACE_SUIT) ? RenderType.entityTranslucent(armorResource) : original.call(armorResource);
    }
}
