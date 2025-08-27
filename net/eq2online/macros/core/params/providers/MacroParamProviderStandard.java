/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import bib;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamStandard;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderStandard<TItem>
/*    */   extends MacroParamProvider<TItem>
/*    */ {
/* 19 */   private static final Pattern PATTERN = Pattern.compile("(?<![\\x5C])\\x24\\x24\\x3F", 2);
/*    */ 
/*    */   
/*    */   public MacroParamProviderStandard(Macros macros, bib mc, MacroParam.Type type) {
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
/*    */   public MacroParam<TItem> getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 35 */     return (MacroParam<TItem>)new MacroParamStandard(this.macros, this.mc, getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderStandard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */