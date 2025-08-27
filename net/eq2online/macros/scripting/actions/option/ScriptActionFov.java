/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import bid;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ public class ScriptActionFov
/*    */   extends ScriptActionGamma<bid.a>
/*    */ {
/*    */   public ScriptActionFov(ScriptContext context) {
/* 11 */     super(context, "fov", bid.a.c, 70.0F, 110.0F);
/*    */     
/* 13 */     setMinValue(10.0F);
/* 14 */     setMaxValue(170.0F);
/* 15 */     setNoScale(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 33 */     return "option";
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionFov.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */