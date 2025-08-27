/*    */ package net.eq2online.macros.scripting.actions.option;
/*    */ 
/*    */ import aed;
/*    */ import cey;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionChatVisible
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionChatVisible(ScriptContext context) {
/* 17 */     super(context, "chatvisible");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isThreadSafe() {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 29 */     aed.b chatVisibility = aed.b.a;
/*    */     
/* 31 */     if (params.length > 0) {
/*    */       
/* 33 */       String parsed = provider.expand(macro, params[0], false).toLowerCase().trim();
/*    */       
/* 35 */       if (parsed.startsWith("c") || "1".equals(parsed))
/*    */       {
/* 37 */         chatVisibility = aed.b.b;
/*    */       }
/* 39 */       else if (parsed.startsWith("hid") || parsed.startsWith("f") || "off".equals(parsed) || "2".equals(parsed))
/*    */       {
/* 41 */         chatVisibility = aed.b.c;
/*    */       }
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 47 */       chatVisibility = (this.mc.t.o == aed.b.a) ? aed.b.c : aed.b.a;
/*    */     } 
/*    */     
/* 50 */     this.mc.t.o = chatVisibility;
/*    */     
/* 52 */     return (IReturnValue)new ReturnValue(cey.a(chatVisibility.b(), new Object[0]));
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\option\ScriptActionChatVisible.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */