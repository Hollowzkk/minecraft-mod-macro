/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionSlotClick
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionSlotClick(ScriptContext context) {
/* 15 */     super(context, "slotclick");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ScriptActionSlotClick(ScriptContext context, String actionName) {
/* 22 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 28 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 40 */     return "inventory";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 46 */     if (params.length > 0) {
/*    */       
/* 48 */       int slotNumber = ScriptCore.tryParseInt(provider.expand(macro, params[0], false), 0);
/* 49 */       int button = 0;
/* 50 */       boolean shift = false;
/*    */       
/* 52 */       if (params.length > 1) {
/*    */         
/* 54 */         String mouseBtn = provider.expand(macro, params[1], false).trim().toLowerCase();
/* 55 */         button = mouseBtn.startsWith("r") ? 1 : 0;
/*    */       } 
/*    */       
/* 58 */       if (params.length > 2) {
/*    */         
/* 60 */         String useShift = provider.expand(macro, params[2], false).trim();
/* 61 */         shift = ("1".equals(useShift) || "true".equalsIgnoreCase(useShift));
/*    */       } 
/*    */       
/* 64 */       this.slotHelper.containerSlotClick(slotNumber, button, shift);
/*    */     } 
/*    */     
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionSlotClick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */