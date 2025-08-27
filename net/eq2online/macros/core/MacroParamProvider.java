/*    */ package net.eq2online.macros.core;
/*    */ 
/*    */ import bib;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MacroParamProvider<TItem>
/*    */ {
/*    */   protected final Macros macros;
/*    */   protected final bib mc;
/*    */   private final MacroParam.Type type;
/*    */   protected boolean found;
/*    */   protected int start;
/*    */   
/*    */   protected MacroParamProvider(Macros macros, bib mc, MacroParam.Type type) {
/* 27 */     this.macros = macros;
/* 28 */     this.mc = mc;
/* 29 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public MacroParam.Type getType() {
/* 34 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public String highlight(String macro, String prefix, String suffix) {
/* 39 */     return matcher(macro).replaceAll(prefix + "$0" + suffix);
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 44 */     this.found = false;
/* 45 */     this.start = Integer.MAX_VALUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matcher find(String script) {
/* 50 */     Matcher matcher = matcher(script);
/* 51 */     this.found = matcher.find();
/* 52 */     this.start = this.found ? matcher.start() : Integer.MAX_VALUE;
/* 53 */     return matcher;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getStart() {
/* 58 */     return this.start;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFound() {
/* 63 */     return this.found;
/*    */   }
/*    */ 
/*    */   
/*    */   public Matcher matcher(String charSequence) {
/* 68 */     return getPattern().matcher(charSequence);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract Pattern getPattern();
/*    */   
/*    */   public abstract MacroParam<TItem> getMacroParam(IMacroParamTarget paramIMacroParamTarget, MacroParams paramMacroParams);
/*    */   
/*    */   public static List<MacroParamProvider<?>> getProviders(Macros macros, bib minecraft) throws Exception {
/* 77 */     List<MacroParamProvider<?>> providers = new ArrayList<>();
/* 78 */     for (MacroParam.Type type : MacroParam.Type.values())
/*    */     {
/* 80 */       providers.add(type.getProvider(macros, minecraft));
/*    */     }
/* 82 */     return providers;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\MacroParamProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */