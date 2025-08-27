/*    */ package net.eq2online.macros.modules.chatfilter;
/*    */ 
/*    */ import bib;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Map;
/*    */ import net.eq2online.macros.core.Macro;
/*    */ import net.eq2online.macros.core.MacroTemplate;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroActionContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatFilterTemplate
/*    */   extends MacroTemplate
/*    */ {
/*    */   private ChatFilterManager manager;
/* 21 */   private String config = "";
/*    */ 
/*    */   
/*    */   public ChatFilterTemplate(Macros macros, bib mc, ChatFilterManager manager, String config) {
/* 25 */     super(macros, mc, 9999);
/*    */     
/* 27 */     this.manager = manager;
/* 28 */     this.config = config;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getConfig() {
/* 33 */     return this.config;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Macro createInstance(boolean checkModifiers, IMacroActionContext context) {
/* 43 */     return new ChatFilterMacro(this.macros, this.mc, this, this.id, context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveTemplate(PrintWriter writer) {
/* 52 */     if (this.keyDownMacro.length() > 0) {
/*    */       
/* 54 */       String serialisedKeyDownMacro = this.keyDownMacro.replaceAll("\\x82", Macro.escapeReplacement("<BR />"));
/* 55 */       String serialisedFlags = serialiseFlags();
/* 56 */       String serialisedCounters = serialiseCounters();
/*    */       
/* 58 */       writer.println("# Filter for config [" + this.config + "]");
/*    */       
/* 60 */       writer.println("Filter[" + this.config + "].Script=" + serialisedKeyDownMacro);
/* 61 */       if (this.global) writer.println("Filter[" + this.config + "].Global=1"); 
/* 62 */       if (serialisedFlags.length() > 0) writer.println("Filter[" + this.config + "].Flags=" + serialisedFlags); 
/* 63 */       if (serialisedCounters.length() > 0) writer.println("Filter[" + this.config + "].Counters=" + serialisedCounters);
/*    */       
/* 65 */       for (Map.Entry<String, String> string : (Iterable<Map.Entry<String, String>>)this.strings.entrySet())
/*    */       {
/* 67 */         writer.println("Filter[" + this.config + "].String[" + (String)string.getKey() + "]=" + (String)string.getValue());
/*    */       }
/*    */       
/* 70 */       writer.println();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void loadFrom(String line, String key, String value) {
/* 81 */     if ("Script".equalsIgnoreCase(key)) {
/*    */       
/* 83 */       setKeyDownMacro(value.replaceAll("\\<[Bb][Rr] \\/\\>", ""));
/*    */     }
/*    */     else {
/*    */       
/* 87 */       super.loadFrom(line, key, value);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void saveTemplates() {
/* 97 */     this.manager.save();
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\modules\chatfilter\ChatFilterTemplate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */