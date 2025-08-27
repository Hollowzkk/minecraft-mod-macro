/*    */ package net.eq2online.macros.scripting.actions.input;
/*    */ 
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionKeyDown
/*    */   extends ScriptActionKeyUp
/*    */ {
/*    */   public ScriptActionKeyDown(ScriptContext context) {
/*  9 */     super(context, "keydown");
/* 10 */     this.keyState = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\input\ScriptActionKeyDown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */