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
/*     */ 
/*     */ public class ScriptActionKeyUp
/*     */   extends ScriptAction
/*     */ {
/*     */   protected boolean keyState = false;
/*     */   
/*     */   public ScriptActionKeyUp(ScriptContext context) {
/*  31 */     super(context, "keyup");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ScriptActionKeyUp(ScriptContext context, String actionName) {
/*  41 */     super(context, actionName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  47 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissable() {
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/*  65 */     return "input";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  71 */     if (params.length > 0) {
/*     */       
/*  73 */       String parameter = provider.expand(macro, params[0], false);
/*  74 */       bid gameSettings = this.mc.t;
/*     */       
/*  76 */       if ("forward".equalsIgnoreCase(parameter)) {
/*     */         
/*  78 */         bhy.a(gameSettings.T.j(), this.keyState);
/*  79 */         return null;
/*     */       } 
/*  81 */       if ("back".equalsIgnoreCase(parameter)) {
/*     */         
/*  83 */         bhy.a(gameSettings.V.j(), this.keyState);
/*  84 */         return null;
/*     */       } 
/*  86 */       if ("left".equalsIgnoreCase(parameter)) {
/*     */         
/*  88 */         bhy.a(gameSettings.U.j(), this.keyState);
/*  89 */         return null;
/*     */       } 
/*  91 */       if ("right".equalsIgnoreCase(parameter)) {
/*     */         
/*  93 */         bhy.a(gameSettings.W.j(), this.keyState);
/*  94 */         return null;
/*     */       } 
/*  96 */       if ("jump".equalsIgnoreCase(parameter)) {
/*     */         
/*  98 */         bhy.a(gameSettings.X.j(), this.keyState);
/*  99 */         return null;
/*     */       } 
/* 101 */       if ("sneak".equalsIgnoreCase(parameter)) {
/*     */         
/* 103 */         bhy.a(gameSettings.Y.j(), this.keyState);
/* 104 */         return null;
/*     */       } 
/* 106 */       if ("playerlist".equalsIgnoreCase(parameter)) {
/*     */         
/* 108 */         bhy.a(gameSettings.ah.j(), this.keyState);
/* 109 */         return null;
/*     */       } 
/* 111 */       if ("sprint".equalsIgnoreCase(parameter)) {
/*     */         
/* 113 */         bhy.a(gameSettings.Z.j(), this.keyState);
/* 114 */         return null;
/*     */       } 
/*     */       
/* 117 */       int keyCode = ScriptCore.tryParseInt(parameter, 0);
/*     */       
/* 119 */       if (keyCode > 0 && keyCode < 255 && keyCode != gameSettings.ae
/*     */         
/* 121 */         .j() && keyCode != gameSettings.ad
/* 122 */         .j())
/*     */       {
/* 124 */         bhy.a(keyCode, this.keyState);
/*     */       }
/*     */     } 
/*     */     
/* 128 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\input\ScriptActionKeyUp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */