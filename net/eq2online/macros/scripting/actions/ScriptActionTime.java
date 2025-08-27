/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ 
/*    */ public class ScriptActionTime
/*    */   extends ScriptAction
/*    */ {
/* 16 */   SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
/* 17 */   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
/*    */ 
/*    */   
/*    */   public ScriptActionTime(ScriptContext context) {
/* 21 */     super(context, "time");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 27 */     IReturnValue retVal = null;
/* 28 */     String output = null;
/* 29 */     SimpleDateFormat useFormat = this.isoFormat;
/*    */     
/* 31 */     if (params.length > 1) {
/*    */ 
/*    */       
/*    */       try {
/* 35 */         this.format.applyPattern(provider.expand(macro, params[1], false));
/*    */       }
/* 37 */       catch (Exception ex) {
/*    */         
/* 39 */         this.format.applyPattern("'Bad date format'");
/*    */       } 
/*    */       
/* 42 */       useFormat = this.format;
/*    */     } 
/*    */     
/* 45 */     output = useFormat.format(new Date());
/* 46 */     ReturnValue returnValue = new ReturnValue(output);
/*    */     
/* 48 */     if (params.length > 0)
/*    */     {
/* 50 */       provider.setVariable(macro, params[0], output);
/*    */     }
/*    */     
/* 53 */     return (IReturnValue)returnValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\ScriptActionTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */