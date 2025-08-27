/*     */ package net.eq2online.macros.scripting.actions.game;
/*     */ 
/*     */ import aec;
/*     */ import aip;
/*     */ import air;
/*     */ import awc;
/*     */ import blk;
/*     */ import bud;
/*     */ import hh;
/*     */ import ho;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.mixin.IGuiEditSign;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ 
/*     */ public class ScriptActionPlaceSign
/*     */   extends ScriptAction
/*     */ {
/*     */   private boolean handlePlacingSign = false;
/*     */   private boolean closeGui = true;
/*  27 */   private int elapsedTicks = 0;
/*     */   
/*  29 */   private String[] signText = new String[4];
/*     */ 
/*     */   
/*     */   public ScriptActionPlaceSign(ScriptContext context) {
/*  33 */     super(context, "placesign");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/*  39 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissable() {
/*  48 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/*  57 */     return "world";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  63 */     bud thePlayer = this.mc.h;
/*     */     
/*  65 */     int signSlotID = -1;
/*     */     
/*  67 */     if (thePlayer != null && thePlayer.bv != null) {
/*     */       
/*  69 */       aec inventory = thePlayer.bv;
/*     */       
/*  71 */       for (int i = 0; i < 9; i++) {
/*     */         
/*  73 */         if (inventory.a.get(i) != null && ((aip)inventory.a.get(i)).c() == air.as) {
/*     */           
/*  75 */           signSlotID = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  80 */       if (signSlotID > -1) {
/*     */         
/*  82 */         this.elapsedTicks = 0;
/*  83 */         this.handlePlacingSign = true;
/*     */         
/*  85 */         aip itemstack = (aip)inventory.a.get(signSlotID);
/*  86 */         provider.actionUseItem(this.mc, thePlayer, itemstack, signSlotID);
/*     */         
/*  88 */         this.signText[0] = (params.length > 0) ? Macros.replaceInvalidChars(provider.expand(macro, params[0], false)) : "";
/*  89 */         this.signText[1] = (params.length > 1) ? Macros.replaceInvalidChars(provider.expand(macro, params[1], false)) : "";
/*  90 */         this.signText[2] = (params.length > 2) ? Macros.replaceInvalidChars(provider.expand(macro, params[2], false)) : "";
/*  91 */         this.signText[3] = (params.length > 3) ? Macros.replaceInvalidChars(provider.expand(macro, params[3], false)) : "";
/*     */         
/*  93 */         if (params.length > 4)
/*     */         {
/*  95 */           this.closeGui = (!params[4].equalsIgnoreCase("true") && !params[4].equals("1"));
/*     */         }
/*     */         else
/*     */         {
/*  99 */           this.closeGui = true;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 104 */         provider.actionAddChatMessage(I18n.get("script.error.nosign"));
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int onTick(IScriptActionProvider provider) {
/* 114 */     if (this.handlePlacingSign) {
/*     */       
/* 116 */       this.elapsedTicks++;
/*     */       
/* 118 */       if (this.elapsedTicks > 200) {
/*     */         
/* 120 */         this.handlePlacingSign = false;
/*     */       } else {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 126 */           blk currentScreen = this.mc.m;
/*     */           
/* 128 */           if ((this.closeGui || this.elapsedTicks > 10) && currentScreen instanceof IGuiEditSign) {
/*     */             
/* 130 */             this.handlePlacingSign = false;
/*     */             
/* 132 */             awc entitySign = ((IGuiEditSign)currentScreen).getSign();
/* 133 */             if (entitySign != null) {
/*     */               
/* 135 */               for (int i = 0; i < 4; i++) {
/*     */                 
/* 137 */                 if (this.signText[i].length() > 15) this.signText[i] = this.signText[i].substring(0, 14); 
/* 138 */                 entitySign.a[i] = (hh)new ho(this.signText[i]);
/*     */               } 
/*     */               
/* 141 */               if (this.closeGui)
/*     */               {
/* 143 */                 entitySign.y_();
/* 144 */                 this.mc.a(null);
/*     */               }
/*     */             
/*     */             } 
/*     */           } 
/* 149 */         } catch (Exception ex) {
/*     */           
/* 151 */           Log.printStackTrace(ex);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionPlaceSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */