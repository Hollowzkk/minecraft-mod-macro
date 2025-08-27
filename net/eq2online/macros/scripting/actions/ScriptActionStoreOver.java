/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionStoreOver
/*    */   extends ScriptActionStore
/*    */ {
/*    */   public ScriptActionStoreOver(ScriptContext context) {
/*  9 */     super(context, "storeover");
/* 10 */     this.overwrite = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\ScriptActionStoreOver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */