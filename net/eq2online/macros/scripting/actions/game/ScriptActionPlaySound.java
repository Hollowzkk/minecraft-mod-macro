/*    */ package net.eq2online.macros.scripting.actions.game;
/*    */ 
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*    */ import qg;
/*    */ 
/*    */ public class ScriptActionPlaySound
/*    */   extends ScriptAction
/*    */ {
/*    */   public ScriptActionPlaySound(ScriptContext context) {
/* 16 */     super(context, "playsound");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 22 */     if (params.length > 0) {
/*    */       
/* 24 */       String soundName = provider.expand(macro, params[0], false).toLowerCase().trim();
/*    */       
/* 26 */       float volume = (params.length > 1) ? (Math.min(Math.max(ScriptCore.tryParseFloat(provider.expand(macro, params[1], false), 100.0F), 0.0F), 100.0F) * 0.01F) : 1.0F;
/*    */       
/* 28 */       provider.actionPlaySoundEffect(soundName, qg.a, volume);
/*    */     } 
/*    */     
/* 31 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\game\ScriptActionPlaySound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */