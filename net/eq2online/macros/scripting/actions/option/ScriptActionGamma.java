/*     */ package net.eq2online.macros.scripting.actions.option;
/*     */ 
/*     */ import bid;
/*     */ import net.eq2online.macros.scripting.FloatInterpolator;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.ReturnValue;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
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
/*     */ public class ScriptActionGamma<T extends Enum<?>>
/*     */   extends ScriptAction
/*     */ {
/*     */   private T option;
/*  28 */   private float scaleMinValue = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   private float scaleMaxValue = 100.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   private float minValue = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private float maxValue = 100.0F;
/*     */   
/*     */   private boolean noScale;
/*     */ 
/*     */   
/*     */   public ScriptActionGamma(ScriptContext context) {
/*  52 */     super(context, "gamma");
/*     */     
/*  54 */     this.option = (T)bid.a.d;
/*  55 */     this.maxValue = 200.0F;
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
/*     */   protected ScriptActionGamma(ScriptContext context, String actionName, T option, float minValue, float maxValue) {
/*  68 */     super(context, actionName);
/*     */     
/*  70 */     this.option = option;
/*  71 */     this.minValue = minValue;
/*  72 */     this.maxValue = maxValue;
/*     */     
/*  74 */     this.scaleMinValue = this.minValue;
/*  75 */     this.scaleMaxValue = this.maxValue;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setMinValue(float minValue) {
/*  80 */     this.minValue = minValue;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setMaxValue(float maxValue) {
/*  85 */     this.maxValue = maxValue;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setNoScale(boolean noScale) {
/*  90 */     this.noScale = noScale;
/*     */   }
/*     */ 
/*     */   
/*     */   protected T getOption(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String[] params) {
/*  95 */     return this.option;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 107 */     if (params.length > 1) {
/*     */       
/* 109 */       if (instance.getState() == null) {
/*     */         
/* 111 */         float currentValue = getCurrentOptionValue(getOption(provider, macro, instance, params));
/* 112 */         instance.setState(new FloatInterpolator(currentValue, targetValue(provider.expand(macro, params[0], false)), 
/* 113 */               (long)(ScriptCore.tryParseFloat(provider.expand(macro, params[1], false), 0.0F) * 1000.0F), FloatInterpolator.InterpolationType.Linear));
/*     */       } 
/*     */ 
/*     */       
/* 117 */       float newValue = ((FloatInterpolator)instance.getState()).interpolate().floatValue();
/* 118 */       setOptionValue(getOption(provider, macro, instance, params), newValue);
/*     */       
/* 120 */       return ((FloatInterpolator)instance.getState()).isFinished();
/*     */     } 
/*     */     
/* 123 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getCurrentOptionValue(T option) {
/* 128 */     if (option instanceof qg)
/*     */     {
/* 130 */       return this.mc.t.a((qg)option);
/*     */     }
/*     */     
/* 133 */     return this.mc.t.a((bid.a)option);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setOptionValue(T option, float newValue) {
/* 138 */     if (option instanceof qg) {
/*     */       
/* 140 */       this.mc.t.a((qg)option, newValue);
/*     */       
/*     */       return;
/*     */     } 
/* 144 */     this.mc.t.a((bid.a)option, newValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClocked() {
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 159 */     if (params.length > 0) {
/*     */       
/* 161 */       float newValue = targetValue(provider.expand(macro, params[0], false));
/* 162 */       setOptionValue(getOption(provider, macro, instance, params), newValue);
/*     */     } 
/*     */     
/* 165 */     float currentValue = getCurrentOptionValue(getOption(provider, macro, instance, params));
/* 166 */     return (IReturnValue)new ReturnValue(valueToTarget(currentValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float targetValue(String param) {
/* 177 */     float targetValue = ScriptCore.tryParseFloat(param, this.minValue);
/*     */     
/* 179 */     if (this.noScale) return targetValue;
/*     */     
/* 181 */     if (targetValue < this.minValue) targetValue = this.minValue; 
/* 182 */     if (targetValue > this.maxValue) targetValue = this.maxValue;
/*     */     
/* 184 */     return (targetValue - this.scaleMinValue) / (this.scaleMaxValue - this.scaleMinValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String valueToTarget(float value) {
/* 195 */     if (this.noScale)
/*     */     {
/* 197 */       return String.format("%.3f", new Object[] { Float.valueOf(value) });
/*     */     }
/*     */     
/* 200 */     value = value * (this.scaleMaxValue - this.scaleMinValue) + this.scaleMinValue;
/* 201 */     if (value < this.minValue) value = this.minValue; 
/* 202 */     if (value > this.maxValue) value = this.maxValue;
/*     */     
/* 204 */     return String.format("%.3f", new Object[] { Float.valueOf(value) });
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionGamma.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */