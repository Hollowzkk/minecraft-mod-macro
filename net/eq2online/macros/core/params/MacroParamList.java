/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bib;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderList;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroParamList<TItem>
/*     */   extends MacroParamStandard<TItem>
/*     */ {
/*     */   protected int listPos;
/*     */   protected int listEnd;
/*     */   protected String listName;
/*     */   protected MacroParam.Type listType;
/*     */   protected boolean includeItemDamage;
/*     */   protected String[] listOptions;
/*     */   protected boolean allowArrowKeys;
/*     */   private MacroParamProviderList<TItem> listProvider;
/*     */   
/*     */   public MacroParamList(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProviderList<TItem> provider) {
/*  34 */     super(macros, mc, type, target, params, (MacroParamProvider<TItem>)provider);
/*     */     
/*  36 */     this.listProvider = provider;
/*     */     
/*  38 */     this.listName = this.listProvider.getNextListName();
/*  39 */     this.listType = this.listProvider.getNextListType();
/*  40 */     this.includeItemDamage = this.listProvider.getNextListIncludeDamage();
/*  41 */     this.listOptions = this.listProvider.getNextListOptions();
/*  42 */     this.listPos = this.listProvider.getNextListPos();
/*  43 */     this.listEnd = this.listProvider.getNextListEnd();
/*     */     
/*  45 */     setParameterValue(params.getParameterValueFromStore((MacroParamProvider)provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  54 */     if (this.listPos > -1 && this.listEnd > 0) {
/*     */       
/*  56 */       String macroScript = this.target.getTargetString();
/*     */       
/*  58 */       macroScript = macroScript.substring(0, this.listPos) + getParameterValue() + macroScript.substring(this.listEnd);
/*     */       
/*  60 */       if (this.listName.length() > 0 && !"choice".equalsIgnoreCase(this.listName)) {
/*     */         
/*  62 */         macroScript = macroScript.replaceAll("\\$\\$\\[" + this.listName + "\\]", getParameterValueEscaped());
/*  63 */         this.params.removeNamedVar(this.listName);
/*     */       } 
/*     */       
/*  66 */       this.target.setTargetString(macroScript);
/*     */       
/*  68 */       this.listProvider.clearNextList();
/*  69 */       this.target.compile();
/*     */     } 
/*     */     
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/*  81 */     return MacroParam.getLocalisedString("param.prompt.list", new String[] { this.target.getDisplayName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptPrefix() {
/*  90 */     return this.listName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initListBox(int width, int height) {
/*  99 */     this.allowArrowKeys = (this.listType == MacroParam.Type.ITEM);
/* 100 */     this.itemListBox = createListBox(this.listType, this.listOptions);
/*     */     
/* 102 */     if (this.itemListBox != null) {
/*     */       
/* 104 */       this.itemListBox.setSizeAndPosition(2, 2, 180, height - 40, 20, true);
/*     */       
/* 106 */       IListEntry<TItem> selected = this.itemListBox.getSelectedItem();
/* 107 */       if (selected != null) {
/*     */         
/* 109 */         setParameterValue(selected.getText());
/*     */         
/* 111 */         if (this.listType == MacroParam.Type.ITEM) {
/*     */           
/*     */           try {
/*     */             
/* 115 */             setParameterValue(String.valueOf(selected.getData()));
/*     */           }
/* 117 */           catch (Exception ex) {
/*     */             
/* 119 */             ex.printStackTrace();
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 124 */       if (this.listType == MacroParam.Type.ITEM)
/*     */       {
/* 126 */         updateItemFromListBox(this.includeItemDamage);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiListBox<TItem> createListBox(MacroParam.Type listType, String[] listOptions) {
/* 133 */     return this.macros.getListProvider().getListBox(listType, listOptions, 2, 2, 2, 180, 180, 20, false, false, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiControlEx.HandledState keyTyped(char keyChar, int keyCode) {
/* 142 */     if (this.allowArrowKeys) {
/*     */ 
/*     */       
/* 145 */       if (this.itemListBox != null)
/*     */       {
/* 147 */         return this.itemListBox.listBoxKeyTyped(keyChar, keyCode);
/*     */       }
/*     */       
/* 150 */       return GuiControlEx.HandledState.NONE;
/*     */     } 
/*     */     
/* 153 */     return super.keyTyped(keyChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleListBoxClick(GuiMacroParam<TItem> guiMacroParam) {
/* 163 */     if (this.listProvider.getNextListType() == MacroParam.Type.ITEM) {
/*     */       
/* 165 */       updateItemFromListBox(this.includeItemDamage);
/*     */     }
/*     */     else {
/*     */       
/* 169 */       setParameterValue(this.itemListBox.getSelectedItem().getText());
/*     */     } 
/*     */ 
/*     */     
/* 173 */     if (this.itemListBox.isDoubleClicked(true))
/*     */     {
/* 175 */       guiMacroParam.apply();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */