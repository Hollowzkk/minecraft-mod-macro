/*     */ package net.eq2online.macros.scripting.parser;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.executive.MacroActionProcessor;
/*     */ import net.eq2online.macros.gui.helpers.SlotHelper;
/*     */ import net.eq2online.macros.permissions.MacroModPermissions;
/*     */ import net.eq2online.macros.scripting.ScriptActionBase;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroAction;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionProcessor;
/*     */ import net.eq2online.macros.scripting.api.IMacroActionStackEntry;
/*     */ import net.eq2online.macros.scripting.api.IReturnValue;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.variable.ItemID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScriptAction
/*     */   extends ScriptActionBase
/*     */ {
/*  35 */   protected static int tickedActionCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   protected static int execActionCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   protected static int opActionCount = 0;
/*     */ 
/*     */   
/*     */   protected final Macros macros;
/*     */ 
/*     */   
/*     */   protected final SlotHelper slotHelper;
/*     */ 
/*     */   
/*     */   protected boolean parseVars = true;
/*     */ 
/*     */   
/*     */   ScriptAction(ScriptContext context) {
/*  58 */     this(context, "NULL");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ScriptAction(ScriptContext context, String actionName) {
/*  68 */     super(context, actionName);
/*  69 */     this.macros = (Macros)context.getScriptActionProvider().getMacroEngine();
/*  70 */     this.slotHelper = new SlotHelper(this.macros, this.mc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getTickedActionCount() {
/*  80 */     return tickedActionCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onActionExecuted() {
/*  90 */     execActionCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getExecutedActionCount() {
/* 100 */     return execActionCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onActionSkipped() {
/* 110 */     opActionCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSkippedActionCount() {
/* 120 */     return opActionCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStopped(IScriptActionProvider provider, IMacro macro, IMacroAction instance) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final void onTickInGame(IScriptActionProvider provider) {
/* 142 */     tickedActionCount = 0;
/* 143 */     execActionCount = 0;
/* 144 */     opActionCount = 0;
/*     */     
/* 146 */     if (provider != null)
/*     */     {
/* 148 */       provider.onTick();
/*     */     }
/*     */     
/* 151 */     for (IScriptAction action : ScriptContext.MAIN.getActionsList())
/*     */     {
/* 153 */       tickedActionCount += action.onTick(provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThreadSafe() {
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStackPushOperator() {
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExpectedPopCommands() {
/* 177 */     return "missing statement";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStackPopOperator() {
/* 183 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePoppedBy(IScriptAction action) {
/* 193 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPush(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 199 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeStackPop(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroAction popAction) {
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBreak(IMacroActionProcessor processor, IScriptActionProvider provider, IMacro macro, IMacroAction instance, IMacroAction breakAction) {
/* 213 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConditionalOperator() {
/* 219 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConditionalElseOperator(IScriptAction action) {
/* 225 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesConditionalOperator(IScriptAction action) {
/* 231 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeConditional(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 237 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeConditionalElse(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params, IMacroActionStackEntry top) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 249 */     if (provider.getSettings().isDebugEnabled())
/*     */     {
/* 251 */       Log.info("Executed NULL script action, maybe there is an error in your macro script");
/*     */     }
/*     */     
/* 254 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExecuteNow(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 260 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClocked() {
/* 266 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissable() {
/* 272 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/* 278 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkExecutePermission() {
/* 284 */     return (!isPermissable() || checkPermission(this.actionName, "execute"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkPermission(String actionName, String permission) {
/* 290 */     if ("execute".equals(permission)) {
/*     */       
/* 292 */       String group = getPermissionGroup();
/* 293 */       return MacroModPermissions.hasPermission("script." + ((group == null) ? "" : (group + ".")) + actionName.toLowerCase());
/*     */     } 
/*     */     
/* 296 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerPermissions(String actionName, String actionGroup) {
/* 302 */     String permissionPath = "script.";
/*     */     
/* 304 */     if (actionGroup != null)
/*     */     {
/* 306 */       permissionPath = permissionPath + actionGroup + ".";
/*     */     }
/*     */     
/* 309 */     MacroModPermissions.registerPermission(permissionPath + actionName.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int onTick(IScriptActionProvider provider) {
/* 315 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void displayErrorMessage(IScriptActionProvider provider, IMacro macro, IMacroAction instance, Exception ex, String message) {
/* 320 */     IMacroActionProcessor processor = instance.getActionProcessor();
/* 321 */     String[] errorMessage = ex.getMessage().split("\\r?\\n");
/* 322 */     errorMessage[0] = I18n.get(message, new Object[] { getName().toUpperCase(), errorMessage[0] });
/* 323 */     for (String errorLine : errorMessage) {
/*     */       
/* 325 */       if (processor instanceof MacroActionProcessor) {
/*     */         
/* 327 */         ((MacroActionProcessor)processor).getHost().addScriptError(provider, macro, "§e" + errorLine);
/*     */       }
/*     */       else {
/*     */         
/* 331 */         provider.actionAddChatMessage("§e" + errorLine);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static ItemID tryParseItemID(String value) {
/* 338 */     return new ItemID(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initPermissions() {
/* 343 */     Set<String> actionGroups = new HashSet<>();
/* 344 */     MacroModPermissions.registerPermission("script.*");
/*     */     
/* 346 */     for (ScriptContext context : ScriptContext.getAvailableContexts()) {
/*     */       
/* 348 */       if (context.isCreated())
/*     */       {
/* 350 */         for (Map.Entry<String, IScriptAction> action : context.getActions().entrySet()) {
/*     */           
/* 352 */           if (((IScriptAction)action.getValue()).isPermissable()) {
/*     */             
/* 354 */             String actionGroup = ((IScriptAction)action.getValue()).getPermissionGroup();
/* 355 */             if (actionGroup != null && !actionGroups.contains(actionGroup)) {
/*     */               
/* 357 */               actionGroups.add(actionGroup);
/* 358 */               MacroModPermissions.registerPermission("script." + actionGroup + ".*");
/*     */             } 
/*     */             
/* 361 */             ((IScriptAction)action.getValue()).registerPermissions(action.getKey(), ((IScriptAction)action.getValue()).getPermissionGroup());
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> getDeniedActionList(ScriptContext context) {
/* 370 */     List<String> deniedActions = new ArrayList<>();
/*     */     
/* 372 */     for (IScriptAction action : context.getActionsList()) {
/*     */       
/* 374 */       if (!action.checkExecutePermission())
/*     */       {
/* 376 */         deniedActions.add(action.getName().toUpperCase());
/*     */       }
/*     */     } 
/*     */     
/* 380 */     return deniedActions;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\parser\ScriptAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */