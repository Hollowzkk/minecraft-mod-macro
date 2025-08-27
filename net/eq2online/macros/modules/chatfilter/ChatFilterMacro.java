/*    */ package net.eq2online.macros.modules.chatfilter;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.core.Macro;
/*    */ import net.eq2online.macros.core.MacroPlaybackType;
/*    */ import net.eq2online.macros.core.MacroTemplate;
/*    */ import net.eq2online.macros.core.MacroTriggerType;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.executive.MacroActionProcessor;
/*    */ import net.eq2online.macros.core.executive.interfaces.IMacroHost;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*    */ import net.eq2online.macros.scripting.exceptions.ScriptException;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ChatFilterMacro extends Macro {
/*    */   public boolean pass = true;
/* 18 */   public String newMessage = null;
/*    */ 
/*    */   
/*    */   public ChatFilterMacro(Macros macros, bib mc, MacroTemplate owner, int macroId, IMacroActionContext context) {
/* 22 */     super(macros, mc, owner, macroId, MacroPlaybackType.ONESHOT, MacroTriggerType.EVENT, context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void build() {
/* 31 */     if (this.built) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 36 */     this.built = true;
/* 37 */     this.buildTime = System.currentTimeMillis();
/*    */     
/* 39 */     this.keyDownActions = MacroActionProcessor.compile(ScriptContext.CHATFILTER.getParser(), "$${" + this.keyDownMacro + "}$$", 0, 0, (IMacroHost)this.macros);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean play(boolean trigger, boolean clock) throws ScriptException {
/* 49 */     if (this.killed)
/*    */     {
/* 51 */       return false;
/*    */     }
/*    */     
/* 54 */     build();
/*    */     
/* 56 */     while (this.keyDownActions.execute((IMacro)this, getContext(), false, true, clock) && !this.killed);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     return this.pass;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\modules\chatfilter\ChatFilterMacro.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */