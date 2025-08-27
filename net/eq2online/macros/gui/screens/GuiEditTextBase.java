/*    */ package net.eq2online.macros.gui.screens;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.core.MacroHighlighter;
/*    */ import net.eq2online.macros.core.MacroPlaybackType;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ import net.eq2online.macros.interfaces.IHighlighter;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ 
/*    */ public abstract class GuiEditTextBase
/*    */   extends GuiEditText
/*    */   implements IHighlighter
/*    */ {
/*    */   protected final Macros macros;
/*    */   protected final MacroHighlighter highlighter;
/*    */   protected final ScriptContext context;
/*    */   
/*    */   public GuiEditTextBase(Macros macros, bib minecraft, GuiScreenEx parent, ScriptContext context) {
/* 21 */     super(minecraft, parent);
/* 22 */     this.macros = macros;
/* 23 */     this.highlighter = macros.getHighlighter(MacroPlaybackType.ONESHOT);
/* 24 */     this.context = context;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String generateHighlightMask(String text) {
/* 30 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String highlight(String text) {
/* 36 */     if (!text.trim().startsWith("//")) {
/*    */       
/* 38 */       text = this.highlighter.highlightParams(text, "￻", "￺");
/* 39 */       text = this.context.getCore().highlight(text);
/*    */     } 
/*    */     
/* 42 */     return text;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiEditTextBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */