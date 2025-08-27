/*    */ package net.eq2online.macros.core.params;
/*    */ 
/*    */ import bib;
/*    */ import ceu;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ public class MacroParamResourcePack
/*    */   extends MacroParamFixedList<ceu.a>
/*    */ {
/*    */   public MacroParamResourcePack(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<ceu.a> provider) {
/* 15 */     super(macros, mc, type, target, params, provider);
/*    */     
/* 17 */     this.enableTextField = Boolean.valueOf(false);
/* 18 */     setParameterValue(params.getParameterValueFromStore(provider));
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */