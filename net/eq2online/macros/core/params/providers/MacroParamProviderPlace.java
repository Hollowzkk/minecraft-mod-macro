/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import bib;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamPlace;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ import net.eq2online.macros.struct.Place;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderPlace
/*    */   extends MacroParamProvider<Place>
/*    */ {
/* 20 */   private static final Pattern PATTERN = Pattern.compile("(?<![\\x5C])\\x24\\x24(px|py|pz|pn|p)", 2);
/*    */ 
/*    */   
/*    */   public MacroParamProviderPlace(Macros macros, bib mc, MacroParam.Type type) {
/* 24 */     super(macros, mc, type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 30 */     return PATTERN;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam<Place> getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 36 */     return (MacroParam<Place>)new MacroParamPlace(this.macros, this.mc, getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderPlace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */