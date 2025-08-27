/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import bib;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamShaderGroup;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderShader
/*    */   extends MacroParamProvider<String>
/*    */ {
/* 16 */   private static final Pattern PATTERN = Pattern.compile("(?<![\\x5C])\\x24\\x24s", 2);
/*    */ 
/*    */   
/*    */   public MacroParamProviderShader(Macros macros, bib mc, MacroParam.Type type) {
/* 20 */     super(macros, mc, type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 26 */     return PATTERN;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam<String> getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 32 */     return (MacroParam<String>)new MacroParamShaderGroup(this.macros, this.mc, getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */