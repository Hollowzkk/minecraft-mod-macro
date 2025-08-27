/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import bib;
/*    */ import ceu;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamResourcePack;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderResourcePack
/*    */   extends MacroParamProvider<ceu.a>
/*    */ {
/* 17 */   private static final Pattern PATTERN = Pattern.compile("(?<![\\x5C])\\x24\\x24k", 2);
/*    */ 
/*    */   
/*    */   public MacroParamProviderResourcePack(Macros macros, bib mc, MacroParam.Type type) {
/* 21 */     super(macros, mc, type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 27 */     return PATTERN;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam<ceu.a> getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 33 */     return (MacroParam<ceu.a>)new MacroParamResourcePack(this.macros, this.mc, getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */