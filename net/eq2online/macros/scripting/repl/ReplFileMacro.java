/*    */ package net.eq2online.macros.scripting.repl;
/*    */ 
/*    */ import bib;
/*    */ import blk;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.MacroExecVariableProvider;
/*    */ import net.eq2online.macros.core.MacroParamTarget;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*    */ import net.eq2online.macros.interfaces.IMacroParamStorage;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReplFileMacro
/*    */   extends MacroParamTarget
/*    */ {
/*    */   private final String fileName;
/*    */   private final Repl repl;
/*    */   private final IReplConsole console;
/*    */   private final IMacroParamStorage paramStorage;
/*    */   private final MacroExecVariableProvider contextProvider;
/*    */   private String macro;
/*    */   
/*    */   public ReplFileMacro(Macros macros, bib minecraft, String fileName, Repl repl, IReplConsole console, IMacroParamStorage paramStorage, MacroExecVariableProvider contextProvider) {
/* 28 */     super(macros, minecraft, repl.getContext());
/*    */     
/* 30 */     this.fileName = fileName;
/* 31 */     this.repl = repl;
/* 32 */     this.console = console;
/* 33 */     this.paramStorage = paramStorage;
/* 34 */     this.contextProvider = contextProvider;
/*    */     
/* 36 */     this.macro = getFileContents(this.fileName, 0);
/* 37 */     this.macro = this.contextProvider.provideParameters(this.macro);
/* 38 */     compile();
/*    */   }
/*    */ 
/*    */   
/*    */   public void play() {
/* 43 */     if (hasRemainingParams()) {
/*    */       
/* 45 */       this.mc.a((blk)new GuiMacroParam(this.macros, this.mc, (IMacroParamTarget)this, this.console));
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     handleCompleted();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDisplayName() {
/* 55 */     return this.fileName;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTargetString() {
/* 61 */     return this.macro;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTargetString(String newString) {
/* 67 */     this.macro = newString;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMacroParamStorage getParamStore() {
/* 73 */     return this.paramStorage;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleCompleted() {
/* 79 */     this.console.display();
/*    */     
/* 81 */     this.repl.playMacro(this.macro, false, (IVariableProvider)this.contextProvider);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleCancelled() {
/* 87 */     this.console.display();
/* 88 */     this.console.addLine(I18n.get("repl.console.run.cancelled"));
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\repl\ReplFileMacro.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */