/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControls;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionSetProperty
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSetProperty(ScriptContext context) {
/* 17 */     this(context, "setproperty");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ScriptActionSetProperty(ScriptContext context, String actionName) {
/* 22 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 28 */     if (params.length > 2) {
/*    */       
/* 30 */       String controlName = provider.expand(macro, params[0], false);
/*    */ 
/*    */       
/*    */       try {
/* 34 */         DesignableGuiControl control = getControl(controlName);
/*    */         
/* 36 */         if (control != null) {
/*    */           
/* 38 */           String propertyName = provider.expand(macro, params[1], false);
/*    */           
/* 40 */           if (control.hasProperty(propertyName))
/*    */           {
/* 42 */             String propertyValue = provider.expand(macro, params[2], false).replace('§', '&');
/* 43 */             int intValue = ScriptCore.tryParseInt(propertyValue, 0);
/* 44 */             boolean boolValue = (propertyValue.toLowerCase().equals("true") || propertyValue.toLowerCase().equals("yes") || intValue != 0);
/*    */ 
/*    */             
/* 47 */             control.setPropertyWithValidation(propertyName, propertyValue, intValue, boolValue);
/*    */           }
/*    */         
/*    */         } 
/* 51 */       } catch (Exception exception) {}
/*    */     } 
/*    */ 
/*    */     
/* 55 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected DesignableGuiControl getControl(String controlName) {
/* 64 */     DesignableGuiControls controls = this.macros.getLayoutManager().getControls();
/* 65 */     DesignableGuiControl control = controls.getControl(controlName);
/*    */     
/* 67 */     if (control == null && controlName.matches("^[0-9]{1,5}$")) {
/*    */       
/* 69 */       int controlId = Integer.parseInt(controlName);
/* 70 */       return controls.getControl(controlId);
/*    */     } 
/*    */     
/* 73 */     return control;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionSetProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */