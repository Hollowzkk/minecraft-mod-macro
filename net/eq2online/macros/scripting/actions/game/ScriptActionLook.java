/*     */ package net.eq2online.macros.scripting.actions.game;
/*     */ 
/*     */ import bud;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.scripting.Direction;
/*     */ import net.eq2online.macros.scripting.DirectionInterpolator;
/*     */ import net.eq2online.macros.scripting.FloatInterpolator;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import vg;
/*     */ 
/*     */ public class ScriptActionLook
/*     */   extends ScriptAction {
/*  19 */   protected FloatInterpolator.InterpolationType interpolationType = FloatInterpolator.InterpolationType.Linear;
/*     */   
/*  21 */   protected static int activeInterpolatorId = 0;
/*     */ 
/*     */   
/*     */   public ScriptActionLook(ScriptContext context) {
/*  25 */     super(context, "look");
/*     */   }
/*     */ 
/*     */   
/*     */   protected ScriptActionLook(ScriptContext context, String actionName) {
/*  30 */     super(context, actionName);
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
/*     */   public boolean isClocked() {
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissable() {
/*  48 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/*  54 */     return "input";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  60 */     bud thePlayer = this.mc.h;
/*     */     
/*  62 */     if (params.length > 1 && thePlayer != null) {
/*     */       
/*  64 */       if (instance.getState() == null) {
/*     */         
/*  66 */         Direction currentDirection = new Direction(thePlayer.v, thePlayer.w);
/*  67 */         Direction targetDirection = getDirection(provider, macro, params, currentDirection);
/*  68 */         instance.setState(new DirectionInterpolator(currentDirection, targetDirection, this.interpolationType, ++activeInterpolatorId));
/*     */       } 
/*     */       
/*  71 */       DirectionInterpolator state = (DirectionInterpolator)instance.getState();
/*     */       
/*  73 */       if (state.getId() >= activeInterpolatorId) {
/*     */         
/*  75 */         Direction newDirection = state.interpolate();
/*  76 */         provider.actionSetEntityDirection((vg)thePlayer, newDirection.yaw, newDirection.pitch);
/*     */         
/*  78 */         return state.isFinished();
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/*  88 */     bud thePlayer = this.mc.h;
/*     */     
/*  90 */     if (params.length > 0 && thePlayer != null && instance.getState() == null) {
/*     */       
/*  92 */       Direction currentDirection = new Direction(thePlayer.v * 360.0F, thePlayer.w * 360.0F);
/*  93 */       Direction targetDirection = getDirection(provider, macro, params, currentDirection);
/*     */       
/*  95 */       if (!targetDirection.isEmpty())
/*     */       {
/*  97 */         provider.actionSetEntityDirection((vg)thePlayer, targetDirection.yaw, targetDirection.pitch);
/*     */       }
/*     */     } 
/*     */     
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Direction getDirection(IScriptActionProvider provider, IMacro macro, String[] params, Direction initialDirection) {
/* 106 */     Direction dir = initialDirection.cloneDirection();
/* 107 */     String[] parsedParams = new String[params.length];
/*     */     
/* 109 */     for (int i = 0; i < params.length; i++)
/*     */     {
/* 111 */       parsedParams[i] = provider.expand(macro, params[i], false);
/*     */     }
/*     */     
/* 114 */     if (parsedParams.length > 0) {
/*     */       
/* 116 */       int delayParam = 1;
/*     */       
/* 118 */       if (parsedParams[0].equalsIgnoreCase("north")) {
/*     */         
/* 120 */         dir.setYawAndPitch(180.0F, 0.0F);
/*     */       }
/* 122 */       else if (parsedParams[0].equalsIgnoreCase("east")) {
/*     */         
/* 124 */         dir.setYawAndPitch(270.0F, 0.0F);
/*     */       }
/* 126 */       else if (parsedParams[0].equalsIgnoreCase("south")) {
/*     */         
/* 128 */         dir.setYawAndPitch(0.0F, 0.0F);
/*     */       }
/* 130 */       else if (parsedParams[0].equalsIgnoreCase("west")) {
/*     */         
/* 132 */         dir.setYawAndPitch(90.0F, 0.0F);
/*     */       }
/* 134 */       else if (parsedParams[0].equalsIgnoreCase("near")) {
/*     */         
/* 136 */         int near = 0;
/* 137 */         if (initialDirection.yaw >= 45.0F && initialDirection.yaw < 135.0F) near = 90; 
/* 138 */         if (initialDirection.yaw >= 135.0F && initialDirection.yaw < 225.0F) near = 180; 
/* 139 */         if (initialDirection.yaw >= 225.0F && initialDirection.yaw < 315.0F) near = 270; 
/* 140 */         dir.setYawAndPitch(near, 0.0F);
/*     */       }
/*     */       else {
/*     */         
/* 144 */         delayParam = 2;
/*     */         
/* 146 */         if (Pattern.matches("^([\\+\\-]?)[0-9]+$", parsedParams[0])) {
/*     */           
/* 148 */           float yawOffset = ScriptCore.tryParseFloat(parsedParams[0], 0.0F);
/*     */           
/* 150 */           if (parsedParams[0].startsWith("+") || parsedParams[0].startsWith("-")) {
/*     */             
/* 152 */             dir.setYaw(initialDirection.yaw + yawOffset);
/*     */           }
/*     */           else {
/*     */             
/* 156 */             dir.setYaw(yawOffset + 180.0F);
/*     */           } 
/*     */         } 
/*     */         
/* 160 */         if (parsedParams.length > 1 && Pattern.matches("^([\\+\\-]?)[0-9]+$", parsedParams[1])) {
/*     */           
/* 162 */           float pitchOffset = ScriptCore.tryParseFloat(parsedParams[1], 0.0F);
/*     */           
/* 164 */           if (parsedParams[1].startsWith("+") || parsedParams[1].startsWith("-")) {
/*     */             
/* 166 */             dir.setPitch(initialDirection.pitch + pitchOffset);
/*     */           }
/*     */           else {
/*     */             
/* 170 */             dir.setPitch(pitchOffset);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 175 */       if (parsedParams.length > delayParam)
/*     */       {
/* 177 */         dir.setDuration((long)(ScriptCore.tryParseFloat(parsedParams[delayParam], 0.0F) * 1000.0F));
/*     */       }
/*     */     } 
/*     */     
/* 181 */     return dir;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionLook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */