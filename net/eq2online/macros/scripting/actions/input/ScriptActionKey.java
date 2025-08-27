/*     */ package net.eq2online.macros.scripting.actions.input;
/*     */ 
/*     */ import bhy;
/*     */ import bid;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.core.mixin.IKeyBinding;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.util.Game;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptActionKey
/*     */   extends ScriptAction
/*     */ {
/*  26 */   private List<bhy> pressedKeys = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public ScriptActionKey(ScriptContext context) {
/*  30 */     super(context, "key");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  36 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissable() {
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  51 */     int keyCode = 0;
/*     */     
/*  53 */     if (params.length > 0) {
/*     */       
/*  55 */       String parameter = provider.expand(macro, params[0], false);
/*  56 */       bid gameSettings = this.mc.t;
/*     */       
/*  58 */       if ("inventory".equalsIgnoreCase(parameter)) {
/*     */         
/*  60 */         keyCode = gameSettings.aa.j();
/*     */       }
/*  62 */       else if ("drop".equalsIgnoreCase(parameter)) {
/*     */         
/*  64 */         keyCode = gameSettings.ac.j();
/*     */       }
/*  66 */       else if ("chat".equalsIgnoreCase(parameter)) {
/*     */         
/*  68 */         keyCode = gameSettings.ag.j();
/*     */       }
/*  70 */       else if ("attack".equalsIgnoreCase(parameter)) {
/*     */         
/*  72 */         keyCode = gameSettings.ae.j();
/*     */       }
/*  74 */       else if ("use".equalsIgnoreCase(parameter)) {
/*     */         
/*  76 */         keyCode = gameSettings.ad.j();
/*     */       }
/*  78 */       else if ("pick".equalsIgnoreCase(parameter)) {
/*     */         
/*  80 */         keyCode = gameSettings.af.j();
/*     */       }
/*  82 */       else if ("screenshot".equalsIgnoreCase(parameter)) {
/*     */         
/*  84 */         keyCode = gameSettings.aj.j();
/*     */       }
/*  86 */       else if ("smoothcamera".equalsIgnoreCase(parameter)) {
/*     */         
/*  88 */         keyCode = gameSettings.al.j();
/*     */       }
/*  90 */       else if ("swaphands".equalsIgnoreCase(parameter)) {
/*     */         
/*  92 */         keyCode = gameSettings.ab.j();
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     if (keyCode != 0) {
/*     */       
/*  98 */       bhy keyBinding = Game.getKeybinding(keyCode);
/*     */       
/* 100 */       if (keyBinding != null) {
/*     */         
/* 102 */         if (((IKeyBinding)keyBinding).getPresses() < 1)
/*     */         {
/* 104 */           bhy.a(keyCode);
/*     */         }
/*     */         
/* 107 */         if (!keyBinding.e()) {
/*     */           
/* 109 */           bhy.a(keyBinding.j(), true);
/* 110 */           this.pressedKeys.add(keyBinding);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int onTick(IScriptActionProvider provider) {
/* 121 */     int tickedActionCount = 0;
/*     */     
/* 123 */     while (this.pressedKeys.size() > 0) {
/*     */       
/* 125 */       bhy keyBinding = this.pressedKeys.remove(0);
/* 126 */       bhy.a(keyBinding.j(), false);
/* 127 */       tickedActionCount++;
/*     */     } 
/*     */     
/* 130 */     return tickedActionCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/* 139 */     return "input";
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\input\ScriptActionKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */