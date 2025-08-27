/*     */ package net.eq2online.macros.scripting.variable.providers;
/*     */ 
/*     */ import bib;
/*     */ import bid;
/*     */ import bxr;
/*     */ import com.mumfrey.liteloader.client.overlays.IEntityRenderer;
/*     */ import net.eq2online.macros.scripting.variable.VariableCache;
/*     */ import nf;
/*     */ import qg;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VariableProviderSettings
/*     */   extends VariableCache
/*     */ {
/*     */   private final bib mc;
/*     */   private bid gameSettings;
/*     */   private String[] shaders;
/*     */   
/*     */   public VariableProviderSettings(bib minecraft) {
/*  30 */     this.mc = minecraft;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initShaders() {
/*  35 */     this.shaders = new String[0];
/*  36 */     this.gameSettings = this.mc.t;
/*     */     
/*  38 */     nf[] availableShaders = ((IEntityRenderer)this.mc.o).getShaders();
/*     */     
/*     */     try {
/*  41 */       this.shaders = new String[availableShaders.length];
/*  42 */       for (int i = 0; i < availableShaders.length; i++)
/*     */       {
/*  44 */         this.shaders[i] = availableShaders[i].toString();
/*     */       }
/*     */     }
/*  47 */     catch (Exception ex) {
/*     */       
/*  49 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {
/*  56 */     if (this.shaders == null)
/*     */     {
/*  58 */       initShaders();
/*     */     }
/*     */     
/*  61 */     if (this.gameSettings != null) {
/*     */       
/*  63 */       storeVariable("FOV", getOptionIntValue(bid.a.c, 0.0F, 1.0F));
/*  64 */       storeVariable("GAMMA", getOptionIntValue(bid.a.d, 0.0F, 100.0F));
/*  65 */       storeVariable("SENSITIVITY", getOptionIntValue(bid.a.b, 0.0F, 200.0F));
/*  66 */       storeVariable("MUSIC", getSoundLevel(qg.b, 0.0F, 100.0F));
/*  67 */       storeVariable("SOUND", getSoundLevel(qg.a, 0.0F, 100.0F));
/*  68 */       storeVariable("RECORDVOLUME", getSoundLevel(qg.c, 0.0F, 100.0F));
/*  69 */       storeVariable("WEATHERVOLUME", getSoundLevel(qg.d, 0.0F, 100.0F));
/*  70 */       storeVariable("BLOCKVOLUME", getSoundLevel(qg.e, 0.0F, 100.0F));
/*  71 */       storeVariable("HOSTILEVOLUME", getSoundLevel(qg.f, 0.0F, 100.0F));
/*  72 */       storeVariable("NEUTRALVOLUME", getSoundLevel(qg.g, 0.0F, 100.0F));
/*  73 */       storeVariable("PLAYERVOLUME", getSoundLevel(qg.h, 0.0F, 100.0F));
/*  74 */       storeVariable("AMBIENTVOLUME", getSoundLevel(qg.i, 0.0F, 100.0F));
/*     */       
/*  76 */       storeVariable("CAMERA", this.gameSettings.aw);
/*     */     } 
/*     */     
/*  79 */     storeVariable("FPS", bib.af());
/*  80 */     storeVariable("CHUNKUPDATES", bxr.a);
/*     */     
/*  82 */     setCachedVariable("SHADERGROUPS", this.shaders);
/*     */     
/*  84 */     if (this.mc.o != null && this.mc.o.a()) {
/*     */       
/*  86 */       storeVariable("SHADERGROUP", this.mc.o.f().b());
/*     */     }
/*     */     else {
/*     */       
/*  90 */       storeVariable("SHADERGROUP", "");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/*  97 */     return getCachedValue(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getOptionIntValue(bid.a option, float min, float max) {
/* 111 */     return (int)(min + this.gameSettings.a(option) * (max - min));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getSoundLevel(qg category, float min, float max) {
/* 116 */     return (int)(min + this.gameSettings.a(category) * (max - min));
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\providers\VariableProviderSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */