/*    */ package net.eq2online.macros.scripting.parser;
/*    */ 
/*    */ public class UnrecognisedAction
/*    */   extends ScriptAction
/*    */ {
/*    */   private final String name;
/*    */   
/*    */   protected UnrecognisedAction(ScriptContext context, String actionName) {
/*  9 */     super(context);
/* 10 */     this.name = actionName;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 16 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\parser\UnrecognisedAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */