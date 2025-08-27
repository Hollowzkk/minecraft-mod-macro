package net.eq2online.macros.fabric.mixin;

import net.minecraft.recipe.ShapelessRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShapelessRecipe.class)
public abstract class MixinShapelessRecipes {
    
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        // TODO: Implementar lógica do mixin para receitas sem forma
    }
    
    // TODO: Adicionar mais métodos conforme necessário
}
