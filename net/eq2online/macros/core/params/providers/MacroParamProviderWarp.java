/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import bib;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamWarp;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderWarp
/*    */   extends MacroParamProvider<String>
/*    */ {
/* 19 */   private static final Pattern PATTERN = Pattern.compile("(?<![\\x5C])\\x24\\x24w", 2);
/*    */ 
/*    */   
/*    */   public MacroParamProviderWarp(Macros macros, bib mc, MacroParam.Type type) {
/* 23 */     super(macros, mc, type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 29 */     return PATTERN;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam<String> getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 35 */     return (MacroParam<String>)new MacroParamWarp(this.macros, this.mc, getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderWarp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */