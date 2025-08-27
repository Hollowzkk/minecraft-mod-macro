/*     */ package net.eq2online.macros.scripting.actions.input;
/*     */ 
/*     */ import bhy;
/*     */ import bid;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import net.eq2online.util.Game;
/*     */ 
/*     */ public class ScriptActionToggleKey
/*     */   extends ScriptAction
/*     */ {
/*     */   public ScriptActionToggleKey(ScriptContext context) {
/*  18 */     super(context, "togglekey");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  24 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissable() {
/*  33 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/*  42 */     return "input";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  48 */     if (params.length > 0) {
/*     */       
/*  50 */       String parameter = provider.expand(macro, params[0], false);
/*  51 */       bid gameSettings = this.mc.t;
/*     */       
/*  53 */       if ("forward".equalsIgnoreCase(parameter)) {
/*     */         
/*  55 */         bhy.a(gameSettings.T.j(), !gameSettings.T.e());
/*  56 */         return null;
/*     */       } 
/*  58 */       if ("back".equalsIgnoreCase(parameter)) {
/*     */         
/*  60 */         bhy.a(gameSettings.V.j(), !gameSettings.V.e());
/*  61 */         return null;
/*     */       } 
/*  63 */       if ("left".equalsIgnoreCase(parameter)) {
/*     */         
/*  65 */         bhy.a(gameSettings.U.j(), !gameSettings.U.e());
/*  66 */         return null;
/*     */       } 
/*  68 */       if ("right".equalsIgnoreCase(parameter)) {
/*     */         
/*  70 */         bhy.a(gameSettings.W.j(), !gameSettings.W.e());
/*  71 */         return null;
/*     */       } 
/*  73 */       if ("jump".equalsIgnoreCase(parameter)) {
/*     */         
/*  75 */         bhy.a(gameSettings.X.j(), !gameSettings.X.e());
/*  76 */         return null;
/*     */       } 
/*  78 */       if ("sneak".equalsIgnoreCase(parameter)) {
/*     */         
/*  80 */         bhy.a(gameSettings.Y.j(), !gameSettings.Y.e());
/*  81 */         return null;
/*     */       } 
/*  83 */       if ("playerlist".equalsIgnoreCase(parameter)) {
/*     */         
/*  85 */         bhy.a(gameSettings.ah.j(), !gameSettings.ah.e());
/*  86 */         return null;
/*     */       } 
/*  88 */       if ("sprint".equalsIgnoreCase(parameter)) {
/*     */         
/*  90 */         bhy.a(gameSettings.Z.j(), !gameSettings.Z.e());
/*  91 */         return null;
/*     */       } 
/*     */       
/*  94 */       int keyCode = ScriptCore.tryParseInt(parameter, 0);
/*     */       
/*  96 */       if (keyCode > 0 && keyCode < 255) {
/*     */         
/*  98 */         bhy keyBinding = Game.getKeybinding(keyCode);
/*     */         
/* 100 */         if (keyBinding != null && keyBinding != gameSettings.ae && keyBinding != gameSettings.ad)
/*     */         {
/* 102 */           bhy.a(keyCode, !keyBinding.e());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\input\ScriptActionToggleKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */