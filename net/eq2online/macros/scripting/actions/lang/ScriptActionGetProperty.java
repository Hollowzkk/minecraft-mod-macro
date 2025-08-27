/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionGetProperty
/*    */   extends ScriptActionSetProperty
/*    */ {
/*    */   public ScriptActionGetProperty(ScriptContext context) {
/* 15 */     super(context, "getproperty");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 21 */     ReturnValue retVal = new ReturnValue("");
/*    */     
/* 23 */     if (params.length > 1) {
/*    */       
/* 25 */       String controlName = provider.expand(macro, params[0], false);
/*    */ 
/*    */       
/*    */       try {
/* 29 */         DesignableGuiControl control = getControl(controlName);
/*    */         
/* 31 */         if (control != null)
/*    */         {
/* 33 */           String propertyName = provider.expand(macro, params[1], false);
/*    */           
/* 35 */           if (control.hasProperty(propertyName))
/*    */           {
/* 37 */             retVal.setString(control.getProperty(propertyName, ""));
/*    */           }
/*    */         }
/*    */       
/* 41 */       } catch (Exception exception) {}
/*    */     } 
/*    */ 
/*    */     
/* 45 */     return (IReturnValue)retVal;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionGetProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */