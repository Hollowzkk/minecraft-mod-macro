/*     */ package net.eq2online.macros.scripting.actions.option;
/*     */ 
/*     */ import bib;
/*     */ import buq;
/*     */ import cii;
/*     */ import com.mumfrey.liteloader.client.overlays.IEntityRenderer;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.ReturnValue;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ public class ScriptActionShaderGroup
/*     */   extends ScriptAction
/*     */ {
/*     */   public ScriptActionShaderGroup(ScriptContext context) {
/*  21 */     super(context, "shadergroup");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  27 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  33 */     IEntityRenderer entityRenderer = (IEntityRenderer)this.mc.o;
/*     */     
/*  35 */     if (params.length == 0) {
/*     */       
/*  37 */       deactivateShader(entityRenderer);
/*     */     }
/*  39 */     else if (params.length > 0) {
/*     */       
/*  41 */       String shaderPath = provider.expand(macro, params[0], false).trim();
/*     */       
/*  43 */       if ("+".equals(shaderPath)) {
/*     */         
/*  45 */         activateNextShader(this.mc, entityRenderer);
/*     */       }
/*  47 */       else if ("-".equals(shaderPath)) {
/*     */         
/*  49 */         activatePreviousShader(this.mc, entityRenderer);
/*     */       }
/*  51 */       else if ("".equals(shaderPath)) {
/*     */         
/*  53 */         deactivateShader(entityRenderer);
/*     */       }
/*     */       else {
/*     */         
/*  57 */         if (!shaderPath.endsWith(".json")) shaderPath = shaderPath + ".json"; 
/*  58 */         if (shaderPath.indexOf('/') == -1) shaderPath = "shaders/post/" + shaderPath; 
/*  59 */         nf shaderLocation = new nf(shaderPath);
/*     */         
/*  61 */         selectShader(this.mc, shaderLocation);
/*     */       } 
/*     */     } 
/*     */     
/*  65 */     if (this.mc.o != null && this.mc.o.a())
/*     */     {
/*  67 */       return (IReturnValue)new ReturnValue(this.mc.o.f().b());
/*     */     }
/*     */     
/*  70 */     return (IReturnValue)new ReturnValue("");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deactivateShader(IEntityRenderer entityRenderer) {
/*  75 */     entityRenderer.setShaderIndex(buq.d);
/*  76 */     entityRenderer.selectShader(null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void activateNextShader(bib minecraft, IEntityRenderer entityRenderer) {
/*  81 */     nf[] shaders = entityRenderer.getShaders();
/*  82 */     int shaderIndex = entityRenderer.getShaderIndex() + 1;
/*  83 */     if (shaderIndex > buq.d) {
/*     */       
/*  85 */       shaderIndex = 0;
/*     */     }
/*  87 */     else if (shaderIndex == buq.d) {
/*     */       
/*  89 */       deactivateShader(entityRenderer);
/*     */       
/*     */       return;
/*     */     } 
/*  93 */     entityRenderer.setShaderIndex(shaderIndex);
/*  94 */     entityRenderer.setUseShader(true);
/*  95 */     entityRenderer.selectShader(shaders[shaderIndex]);
/*     */   }
/*     */ 
/*     */   
/*     */   private void activatePreviousShader(bib minecraft, IEntityRenderer entityRenderer) {
/* 100 */     nf[] shaders = entityRenderer.getShaders();
/* 101 */     int shaderIndex = entityRenderer.getShaderIndex() - 1;
/* 102 */     if (shaderIndex < 0) {
/*     */       
/* 104 */       deactivateShader(entityRenderer);
/*     */     }
/*     */     else {
/*     */       
/* 108 */       selectShader(minecraft, entityRenderer, shaders, shaderIndex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void selectShader(bib minecraft, nf shaderLocation) {
/* 114 */     if (!cii.O)
/*     */       return; 
/* 116 */     IEntityRenderer entityRenderer = (IEntityRenderer)minecraft.o;
/* 117 */     nf[] shaders = entityRenderer.getShaders();
/*     */     
/* 119 */     int shaderIndex = -1;
/* 120 */     for (int index = 0; index < shaders.length; index++) {
/*     */       
/* 122 */       if (shaders[index].equals(shaderLocation))
/*     */       {
/* 124 */         shaderIndex = index;
/*     */       }
/*     */     } 
/*     */     
/* 128 */     selectShader(minecraft, entityRenderer, shaders, shaderIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   private void selectShader(bib minecraft, IEntityRenderer entityRenderer, nf[] shaders, int shaderIndex) {
/* 133 */     if (shaderIndex < 0)
/*     */       return; 
/* 135 */     if (minecraft.o.f() != null)
/*     */     {
/* 137 */       minecraft.o.f().a();
/*     */     }
/*     */     
/* 140 */     entityRenderer.setShaderIndex(shaderIndex);
/* 141 */     entityRenderer.selectShader(shaders[shaderIndex]);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionShaderGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */