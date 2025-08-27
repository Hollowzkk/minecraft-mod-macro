/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import afx;
/*    */ import aip;
/*    */ import bmo;
/*    */ import bsa;
/*    */ import bud;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ public class ScriptActionSetSlotItem
/*    */   extends ScriptAction {
/*    */   public ScriptActionSetSlotItem(ScriptContext context) {
/* 19 */     super(context, "setslotitem");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 31 */     bud thePlayer = this.mc.h;
/* 32 */     bsa playerController = this.mc.c;
/*    */     
/* 34 */     if (params.length > 0 && thePlayer != null && thePlayer.bv != null && playerController != null && playerController.h()) {
/*    */       
/* 36 */       aip itemStack = tryParseItemID(provider.expand(macro, params[0], false)).toItemStack(1);
/*    */       
/* 38 */       if (itemStack.c() != null) {
/*    */         
/* 40 */         if (params.length > 2)
/*    */         {
/* 42 */           int maxStackSize = itemStack.d();
/* 43 */           itemStack.e(Math.min(ScriptCore.tryParseInt(provider.expand(macro, params[2], false), 1), maxStackSize));
/*    */         }
/*    */       
/*    */       } else {
/*    */         
/* 48 */         itemStack = null;
/*    */       } 
/*    */       
/* 51 */       int slot = Math.min(Math.max((params.length > 1) ? ScriptCore.tryParseInt(provider.expand(macro, params[1], false), thePlayer.bv.d) : thePlayer.bv.d, 0), 8);
/*    */ 
/*    */       
/* 54 */       bmo crafting = new bmo(this.mc);
/* 55 */       thePlayer.bx.a((afx)crafting);
/* 56 */       thePlayer.bv.a(slot, itemStack);
/* 57 */       thePlayer.bx.b();
/* 58 */       thePlayer.bx.b((afx)crafting);
/*    */     } 
/*    */     
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPermissable() {
/* 71 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPermissionGroup() {
/* 80 */     return "inventory";
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionSetSlotItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */