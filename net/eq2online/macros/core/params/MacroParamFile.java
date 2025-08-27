/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bib;
/*     */ import bkp;
/*     */ import bkq;
/*     */ import blk;
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macro;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*     */ import net.eq2online.macros.gui.screens.GuiEditTextFile;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.interfaces.IRefreshable;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroParamFile
/*     */   extends MacroParamListOnly<String>
/*     */ {
/*     */   public static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ";
/*     */   
/*     */   public MacroParamFile(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<String> provider) {
/*  31 */     super(macros, mc, type, target, params, provider);
/*     */     
/*  33 */     setParameterValue(params.getParameterValueFromStore(provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  42 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null)
/*     */     {
/*  44 */       this.target.getParamStore().setStoredParam(MacroParam.Type.FILE, 0, getParameterValue());
/*     */     }
/*     */     
/*  47 */     Matcher matcher = this.provider.matcher(this.target.getTargetString());
/*  48 */     if (getParameterValue().length() > 0 && !getParameterValue().equalsIgnoreCase("macros.txt")) {
/*     */       
/*  50 */       this.target.setTargetString(matcher.replaceAll(Macro.escapeReplacement("$$<" + getParameterValue() + ">")));
/*  51 */       this.target.compile();
/*  52 */       return false;
/*     */     } 
/*     */     
/*  55 */     this.target.setTargetString(matcher.replaceAll(""));
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initListBox(int width, int height) {
/*  65 */     this.itemListBox = this.macros.getListProvider().getListBox(this.type);
/*  66 */     ((IRefreshable)this.itemListBox).refresh();
/*  67 */     this.itemListBox.setSizeAndPosition(2, 2, 180, height - 4, 20, true);
/*  68 */     this.itemListBox.setSelectedItemIndex(0);
/*  69 */     this.itemListBox.selectData(getParameterValue());
/*  70 */     this.itemListBox.scrollToCentre();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleListBoxClick(GuiMacroParam<String> guiMacroParam) {
/*  80 */     IListEntry<String> selectedItem = this.itemListBox.getSelectedItem();
/*     */ 
/*     */     
/*  83 */     if (selectedItem.getId() == -1) {
/*     */       
/*  85 */       this.mc.a((blk)new GuiEditListEntry(this.macros, this.mc, guiMacroParam, this, null));
/*     */       
/*     */       return;
/*     */     } 
/*  89 */     IListEntry.CustomAction customAction = selectedItem.getCustomAction(true);
/*     */     
/*  91 */     if (customAction == IListEntry.CustomAction.DELETE) {
/*     */       
/*  93 */       if (Macros.isValidFileName((String)selectedItem.getData()))
/*     */       {
/*     */         
/*  96 */         this.mc.a((blk)new bkq((bkp)guiMacroParam, I18n.get("param.action.confirmdeletefile"), (String)selectedItem.getData(), 
/*  97 */               I18n.get("gui.yes"), I18n.get("gui.no"), 0));
/*     */       }
/*     */     }
/* 100 */     else if (customAction == IListEntry.CustomAction.EDIT) {
/*     */       
/* 102 */       GuiEditTextFile guiEditTextFile = new GuiEditTextFile(this.macros, this.mc, (GuiScreenEx)guiMacroParam, this.macros.getFile((String)selectedItem.getData()), ScriptContext.MAIN);
/*     */       
/* 104 */       this.mc.a((blk)guiEditTextFile);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 109 */       setParameterValue((String)selectedItem.getData());
/*     */     } 
/*     */ 
/*     */     
/* 113 */     if (this.itemListBox.isDoubleClicked(true))
/*     */     {
/* 115 */       guiMacroParam.apply();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkForInvalidParameterValue(String paramValue) {
/* 125 */     return !Macros.isValidFileName(paramValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryWindow(GuiEditListEntry<String> gui, boolean editing) {
/* 135 */     gui.displayText = I18n.get("entry.newfile");
/* 136 */     gui.enableIconChoice = false;
/* 137 */     gui.windowHeight = 78;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryTextbox(GuiTextFieldEx textField) {
/* 147 */     textField.minStringLength = 1;
/* 148 */     textField.f(64);
/* 149 */     textField.allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addItem(GuiEditListEntry<String> gui, String newItem, String displayName, int iconID, String newItemData) {
/* 160 */     return this.parentScreen.createFileAndEdit(newItem);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */