/*    */ package net.eq2online.macros.event;
/*    */ 
/*    */ import bib;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*    */ import net.eq2online.macros.scripting.api.IMacroEventDispatcher;
/*    */ import net.eq2online.macros.scripting.api.IMacroEventManager;
/*    */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroEventProviderBuiltin
/*    */   implements IMacroEventProvider
/*    */ {
/*    */   private final Macros macros;
/*    */   private final MacroEventDispatcherBuiltin dispatcher;
/*    */   
/*    */   public MacroEventProviderBuiltin(Macros macros, bib minecraft) {
/* 26 */     this.dispatcher = new MacroEventDispatcherBuiltin(macros, minecraft);
/* 27 */     this.macros = macros;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IMacroEventDispatcher getDispatcher() {
/* 34 */     return this.dispatcher;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void registerEvents(IMacroEventManager manager) {
/* 40 */     for (BuiltinEvent eventDesc : BuiltinEvent.values()) {
/*    */       
/* 42 */       if (eventDesc.isEnabled(this.macros)) {
/*    */         
/* 44 */         IMacroEvent event = manager.registerEvent(this, eventDesc);
/* 45 */         eventDesc.accept(event);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onInit() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getHelp(IMacroEvent macroEvent) {
/* 58 */     ImmutableList.Builder<String> list = ImmutableList.builder();
/* 59 */     for (int line = 0; line < 6; line++) {
/*    */       
/* 61 */       String key = "macro.help.event." + macroEvent.getName().toLowerCase() + ".line[" + line + "]";
/* 62 */       String help = I18n.get(key);
/* 63 */       if (help == null || help.length() == 0 || help.equals(key)) {
/*    */         break;
/*    */       }
/*    */       
/* 67 */       list.add(help);
/*    */     } 
/*    */     
/* 70 */     return (List<String>)list.build();
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\MacroEventProviderBuiltin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */