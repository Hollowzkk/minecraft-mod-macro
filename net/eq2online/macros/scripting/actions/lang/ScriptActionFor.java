/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.scripting.Variable;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptAction;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ 
/*    */ 
/*    */ public class ScriptActionFor
/*    */   extends ScriptActionDo
/*    */ {
/* 18 */   private static final Pattern EXPRESSIVE = Pattern.compile("^(.+?)=(.+?) to (.+?)( step (.+?))?$", 2);
/*    */ 
/*    */   
/*    */   public ScriptActionFor(ScriptContext context) {
/* 22 */     super(context, "for");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getExpectedPopCommands() {
/* 28 */     return I18n.get("script.error.stackhint", new Object[] { this, "NEXT" });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean executeStackPush(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 34 */     ScriptActionDo.State state = (ScriptActionDo.State)instance.getState();
/*    */     
/* 36 */     if (params.length > 0) {
/*    */       
/* 38 */       if (state == null) {
/*    */         
/* 40 */         String variableName = params[0].toLowerCase();
/* 41 */         String strFrom = null;
/* 42 */         String strTo = null;
/* 43 */         String strStep = null;
/*    */         
/* 45 */         Matcher m = EXPRESSIVE.matcher(variableName);
/*    */         
/* 47 */         if (params.length > 2) {
/*    */           
/* 49 */           strFrom = provider.expand(macro, params[1], false);
/* 50 */           strTo = provider.expand(macro, params[2], false);
/* 51 */           if (params.length > 3)
/*    */           {
/* 53 */             strTo = provider.expand(macro, params[3], false);
/*    */           }
/*    */         }
/* 56 */         else if (m.matches()) {
/*    */           
/* 58 */           variableName = m.group(1).trim();
/* 59 */           strFrom = provider.expand(macro, m.group(2).trim(), false);
/* 60 */           strTo = provider.expand(macro, m.group(3).trim(), false);
/* 61 */           strStep = (m.group(5) != null) ? provider.expand(macro, m.group(5).trim(), false) : null;
/*    */         } 
/*    */         
/* 64 */         if (strFrom != null && strTo != null && Variable.isValidScalarVariableName(variableName))
/*    */         {
/*    */           
/* 67 */           int from = ScriptCore.tryParseInt(strFrom, 0);
/* 68 */           int to = ScriptCore.tryParseInt(strTo, 0);
/* 69 */           int step = ScriptCore.tryParseInt(strStep, 1);
/* 70 */           state = new ScriptActionDo.State(variableName, from, to, step);
/* 71 */           instance.setState(state);
/*    */         }
/*    */         else
/*    */         {
/* 75 */           return false;
/*    */         }
/*    */       
/*    */       } else {
/*    */         
/* 80 */         state.increment();
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 85 */       return false;
/*    */     } 
/*    */     
/* 88 */     if (state.isActive())
/*    */     {
/* 90 */       provider.setVariable(macro, state.getVariableName(), state.getCounter());
/*    */     }
/*    */     
/* 93 */     return state.isActive();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBePoppedBy(IScriptAction action) {
/* 99 */     return action instanceof ScriptActionNext;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionFor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */