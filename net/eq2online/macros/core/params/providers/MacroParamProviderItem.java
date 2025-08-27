/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import bib;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamItem;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderItem
/*    */   extends MacroParamProvider<Integer>
/*    */ {
/* 19 */   public static final Pattern PATTERN = Pattern.compile("(?<![\\x5C])\\x24\\x24(d|i(:d)?)", 2);
/*    */   
/* 21 */   public static final Pattern PATTERN_NO_DAMAGE = Pattern.compile("(?<![\\x5C])\\x24\\x24i(?!:d)", 2);
/*    */   
/* 23 */   public static final Pattern PATTERN_DAMAGE_ONLY = Pattern.compile("(?<![\\x5C])\\x24\\x24d", 2);
/*    */ 
/*    */   
/*    */   public MacroParamProviderItem(Macros macros, bib mc, MacroParam.Type type) {
/* 27 */     super(macros, mc, type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 33 */     return PATTERN;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam<Integer> getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 39 */     return (MacroParam<Integer>)new MacroParamItem(this.macros, this.mc, getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */