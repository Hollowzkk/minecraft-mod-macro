package net.eq2online.macros.fabric.mixin;

import net.minecraft.recipe.ShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShapedRecipe.class)
public abstract class MixinShapedRecipes {
    
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        // TODO: Implementar lógica do mixin para receitas
    }
    
    // TODO: Adicionar mais métodos conforme necessário
}
