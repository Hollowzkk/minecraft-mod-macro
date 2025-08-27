/*    */ package net.eq2online.macros.scripting.api;public interface IScriptActionProvider { IMacroEngine getMacroEngine(); ISettings getSettings(); void registerVariableProvider(IVariableProvider paramIVariableProvider); void unregisterVariableProvider(IVariableProvider paramIVariableProvider); void updateVariableProviders(boolean paramBoolean); IVariableProvider getProviderForVariable(String paramString); void registerVariableListener(IVariableListener paramIVariableListener); void unregisterVariableListener(IVariableListener paramIVariableListener); IVariableProviderShared getSharedVariableProvider(); Set<String> getEnvironmentVariables(); Object getVariable(String paramString, IVariableProvider paramIVariableProvider); Object getVariable(String paramString, IMacro paramIMacro); IExpressionEvaluator getExpressionEvaluator(IMacro paramIMacro, String paramString); void actionSendChatMessage(IMacro paramIMacro, IMacroAction paramIMacroAction, String paramString); void actionAddChatMessage(String paramString); void actionDisconnect(); void actionDisplayGuiScreen(String paramString, ScriptContext paramScriptContext); void actionDisplayCustomScreen(String paramString1, String paramString2, boolean paramBoolean); void actionBindScreenToSlot(String paramString1, String paramString2); String actionSwitchConfig(String paramString, boolean paramBoolean); String actionOverlayConfig(String paramString, boolean paramBoolean1, boolean paramBoolean2); void actionRenderDistance(); boolean actionInventoryPick(String paramString, int paramInt); void actionInventorySlot(int paramInt); void actionInventoryMove(int paramInt); void actionSetSprinting(boolean paramBoolean); void actionStopMacros(); void actionStopMacros(IMacro paramIMacro, int paramInt); boolean getFlagValue(IMacro paramIMacro, String paramString); void setFlagVariable(IMacro paramIMacro, String paramString, boolean paramBoolean); String expand(IMacro paramIMacro, String paramString, boolean paramBoolean); void setVariable(IMacro paramIMacro, String paramString1, String paramString2); void setVariable(IMacro paramIMacro, String paramString, int paramInt); void setVariable(IMacro paramIMacro, String paramString1, String paramString2, int paramInt, boolean paramBoolean); void setVariable(IMacro paramIMacro, String paramString, IReturnValue paramIReturnValue); void unsetVariable(IMacro paramIMacro, String paramString); void incrementCounterVariable(IMacro paramIMacro, String paramString, int paramInt); void pushValueToArray(IMacro paramIMacro, String paramString1, String paramString2); String popValueFromArray(IMacro paramIMacro, String paramString);
/*    */   void putValueToArray(IMacro paramIMacro, String paramString1, String paramString2);
/*    */   void clearArray(IMacro paramIMacro, String paramString);
/*    */   void deleteArrayElement(IMacro paramIMacro, String paramString, int paramInt);
/*    */   Object getArrayElement(IMacro paramIMacro, String paramString, int paramInt);
/*    */   int getArrayIndexOf(IMacro paramIMacro, String paramString1, String paramString2, boolean paramBoolean);
/*    */   int getArraySize(IMacro paramIMacro, String paramString);
/*    */   boolean getArrayExists(IMacro paramIMacro, String paramString);
/*    */   void actionPumpCharacters(String paramString);
/*    */   void actionPumpKeyPress(int paramInt, boolean paramBoolean);
/*    */   void actionSelectResourcePacks(String[] paramArrayOfString);
/*    */   void actionUseItem(bib parambib, bud parambud, aip paramaip, int paramInt);
/*    */   void actionBindKey(int paramInt1, int paramInt2);
/*    */   void actionSetEntityDirection(vg paramvg, float paramFloat1, float paramFloat2);
/*    */   void actionRespawnPlayer();
/*    */   void actionSetRenderDistance(String paramString);
/*    */   void onTick();
/*    */   void actionAddLogMessage(String paramString1, String paramString2);
/*    */   void actionSetLabel(String paramString1, String paramString2, String paramString3);
/*    */   void actionClearCrafting();
/*    */   AutoCraftingToken actionCraft(IAutoCraftingInitiator paramIAutoCraftingInitiator, bud parambud, String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
/*    */   void actionBreakLoop(IMacro paramIMacro, IMacroAction paramIMacroAction);
/*    */   void actionBeginUnsafeBlock(IMacro paramIMacro, IMacroAction paramIMacroAction, int paramInt);
/*    */   void actionEndUnsafeBlock(IMacro paramIMacro, IMacroAction paramIMacroAction);
/*    */   void actionScheduleResChange(int paramInt1, int paramInt2);
/*    */   String getSoundResourceNamespace();
/*    */   void actionPlaySoundEffect(String paramString, qg paramqg, float paramFloat);
/*    */   void actionSetCameraMode(int paramInt);
/*    */   void actionDisplayToast(ToastType paramToastType, String paramString, hh paramhh1, hh paramhh2, int paramInt);
/*    */   void actionClearToasts(boolean paramBoolean);
/* 31 */   public enum ToastType { ADVANCEMENT,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 36 */     CHALLENGE,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     GOAL,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 46 */     RECIPE,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     TUTORIAL,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 56 */     HINT,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     NARRATOR; }
/*    */    }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\api\IScriptActionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */