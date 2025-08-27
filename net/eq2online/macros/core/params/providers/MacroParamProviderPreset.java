/*    */ package net.eq2online.macros.core.params.providers;
/*    */ 
/*    */ import bib;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.core.MacroParamProvider;
/*    */ import net.eq2online.macros.core.MacroParams;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.core.params.MacroParamPreset;
/*    */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MacroParamProviderPreset
/*    */   extends MacroParamProvider<String>
/*    */ {
/* 20 */   private static final Pattern PATTERN = Pattern.compile("(?<![\\x5C])\\x24\\x24([0-9])", 2);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   private static final Pattern[] PATTERNS = new Pattern[] {
/* 26 */       Pattern.compile("(?<![\\x5C])\\x24\\x240"), 
/* 27 */       Pattern.compile("(?<![\\x5C])\\x24\\x241"), 
/* 28 */       Pattern.compile("(?<![\\x5C])\\x24\\x242"), 
/* 29 */       Pattern.compile("(?<![\\x5C])\\x24\\x243"), 
/* 30 */       Pattern.compile("(?<![\\x5C])\\x24\\x244"), 
/* 31 */       Pattern.compile("(?<![\\x5C])\\x24\\x245"), 
/* 32 */       Pattern.compile("(?<![\\x5C])\\x24\\x246"), 
/* 33 */       Pattern.compile("(?<![\\x5C])\\x24\\x247"), 
/* 34 */       Pattern.compile("(?<![\\x5C])\\x24\\x248"), 
/* 35 */       Pattern.compile("(?<![\\x5C])\\x24\\x249")
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   protected int nextPresetTextIndex = -1;
/*    */ 
/*    */   
/*    */   public MacroParamProviderPreset(Macros macros, bib mc, MacroParam.Type type) {
/* 45 */     super(macros, mc, type);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Matcher find(String script) {
/* 51 */     Matcher matcher = super.find(script);
/*    */     
/* 53 */     if (this.found)
/*    */     {
/* 55 */       this.nextPresetTextIndex = Integer.parseInt(matcher.group(1));
/*    */     }
/*    */     
/* 58 */     return matcher;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNextPresetIndex() {
/* 63 */     return this.nextPresetTextIndex;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Pattern getPattern() {
/* 69 */     return PATTERN;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MacroParam<String> getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 75 */     return (MacroParam<String>)new MacroParamPreset(this.macros, this.mc, getType(), target, params, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Matcher getMatcher(int preset, String input) {
/* 80 */     return getPattern(preset).matcher(input);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Pattern getPattern(int preset) {
/* 85 */     if (preset >= 0 && preset < PATTERNS.length)
/*    */     {
/* 87 */       return PATTERNS[preset];
/*    */     }
/*    */     
/* 90 */     return PATTERN;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderPreset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */