/*    */ package net.eq2online.macros.scripting.actions.lang;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.ReturnValue;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import org.bouncycastle.util.encoders.Base64;
/*    */ 
/*    */ 
/*    */ public class ScriptActionDecode
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionDecode(ScriptContext context) {
/* 17 */     super(context, "decode");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 23 */     ReturnValue retVal = new ReturnValue("");
/*    */     
/* 25 */     if (params.length > 0) {
/*    */       
/* 27 */       String input = provider.expand(macro, params[0], false);
/* 28 */       String decoded = decode(input);
/* 29 */       retVal.setString(decoded);
/*    */       
/* 31 */       if (params.length > 1) {
/*    */         
/* 33 */         String variableName = provider.expand(macro, params[1], false);
/* 34 */         provider.setVariable(macro, variableName, decoded);
/*    */       } 
/*    */     } 
/*    */     
/* 38 */     return (IReturnValue)retVal;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String decode(String input) {
/*    */     try {
/* 49 */       return new String(Base64.decode(input.getBytes()));
/*    */     }
/* 51 */     catch (Exception ex) {
/*    */       
/* 53 */       return "";
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\lang\ScriptActionDecode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */