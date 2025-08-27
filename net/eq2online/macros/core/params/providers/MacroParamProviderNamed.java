/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import bib;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamNamed;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderNamed
/*    */   extends MacroParamProvider<String>
/*    */ {
/* 22 */   private static final Pattern PATTERN = Pattern.compile("(?<![\\x5C])\\x24\\x24\\[([a-z0-9]{1,32})\\]", 2);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   private LinkedHashSet<String> namedVars = new LinkedHashSet<>();
/*    */ 
/*    */   
/*    */   public MacroParamProviderNamed(Macros macros, bib mc, MacroParam.Type type) {
/* 31 */     super(macros, mc, type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 37 */     super.reset();
/* 38 */     this.namedVars.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Matcher find(String script) {
/* 44 */     this.start = script.length();
/*    */ 
/*    */     
/* 47 */     Matcher matcher = matcher(script);
/*    */     
/* 49 */     while (matcher.find()) {
/*    */       
/* 51 */       this.start = matcher.start();
/* 52 */       this.namedVars.add(matcher.group(1));
/*    */     } 
/*    */     
/* 55 */     return matcher;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getNamedVars() {
/* 60 */     return this.namedVars;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getNextNamedVar() {
/* 70 */     if (this.namedVars.size() > 0)
/*    */     {
/* 72 */       return this.namedVars.iterator().next();
/*    */     }
/*    */     
/* 75 */     return "var";
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeNamedVar(String name) {
/* 80 */     this.namedVars.remove(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 86 */     return PATTERN;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam<String> getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 92 */     return (MacroParam<String>)new MacroParamNamed(this.macros, this.mc, getType(), target, params, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderNamed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */