/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bib;
/*     */ import java.util.regex.Matcher;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderItem;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.variable.ItemID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroParamItem
/*     */   extends MacroParam<Integer>
/*     */ {
/*     */   public MacroParamItem(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<Integer> provider) {
/*  25 */     super(macros, mc, type, target, params, provider);
/*     */     
/*  27 */     this.enableTextField = Boolean.valueOf(true);
/*  28 */     this.maxParameterLength = 128;
/*  29 */     setParameterValue(params.getParameterValueFromStore(provider));
/*     */     
/*  31 */     if (!getParameterValue().matches("^[a-z_]+(:\\d+)?$"))
/*     */     {
/*  33 */       setParameterValue("");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  43 */     String macroScript = this.target.getTargetString();
/*     */     
/*  45 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null)
/*     */     {
/*  47 */       this.target.getParamStore().setStoredParam(this.type, getParameterValue());
/*     */     }
/*     */     
/*  50 */     ItemID parsedItemId = new ItemID(getParameterValue());
/*     */     
/*  52 */     if (shouldReplaceFirstOccurrenceOnly()) {
/*     */       
/*  54 */       Matcher firstItem = this.provider.matcher(macroScript);
/*  55 */       if (firstItem.find())
/*     */       {
/*  57 */         if (firstItem.group().contains(":"))
/*     */         {
/*  59 */           firstItem.reset();
/*  60 */           macroScript = firstItem.replaceFirst(parsedItemId.toString());
/*     */         }
/*  62 */         else if (firstItem.group().contains("i") && parsedItemId.isValid())
/*     */         {
/*  64 */           macroScript = MacroParamProviderItem.PATTERN_NO_DAMAGE.matcher(macroScript).replaceFirst(parsedItemId.identifier);
/*     */         }
/*  66 */         else if (firstItem.group().contains("d") && parsedItemId.isValid())
/*     */         {
/*  68 */           macroScript = MacroParamProviderItem.PATTERN_DAMAGE_ONLY.matcher(macroScript).replaceFirst(parsedItemId.getDamage());
/*     */         }
/*     */       
/*     */       }
/*     */     } else {
/*     */       
/*  74 */       if (parsedItemId.isValid()) {
/*     */         
/*  76 */         macroScript = MacroParamProviderItem.PATTERN_NO_DAMAGE.matcher(macroScript).replaceAll(parsedItemId.identifier);
/*  77 */         macroScript = MacroParamProviderItem.PATTERN_DAMAGE_ONLY.matcher(macroScript).replaceAll(parsedItemId.getDamage());
/*     */       } 
/*     */       
/*  80 */       macroScript = this.provider.matcher(macroScript).replaceAll(parsedItemId.toString());
/*     */     } 
/*     */     
/*  83 */     this.target.setTargetString(macroScript);
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldReplaceFirstOccurrenceOnly() {
/*  94 */     return (this.replaceFirstOccurrenceOnly || this.settings.getCompilerFlagItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFirstOccurrenceSupported() {
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getAllowedCharacters() {
/* 113 */     return "0123456789:abcdefghijklmnopqrstuvwxyz_";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/* 122 */     return MacroParam.getLocalisedString("param.prompt.item", new String[] { this.target.getDisplayName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initListBox(int width, int height) {
/* 131 */     this.itemListBox = this.macros.getListProvider().getListBox(this.type);
/* 132 */     this.itemListBox.setSizeAndPosition(2, 2, 180, height - 40, 20, true);
/* 133 */     this.itemListBox.setSelectedItemIndex(0);
/*     */     
/* 135 */     ItemID itemId = new ItemID(getParameterValue());
/* 136 */     if (itemId.isValid()) {
/*     */       
/* 138 */       if (itemId.hasDamage)
/*     */       {
/* 140 */         this.itemListBox.selectIdentifierWithData(itemId.name, Integer.valueOf(itemId.damage));
/*     */       }
/*     */       else
/*     */       {
/* 144 */         this.itemListBox.selectIdentifier(itemId.name);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 149 */       this.itemListBox.selectId(0);
/* 150 */       setParameterValue("");
/*     */     } 
/*     */     
/* 153 */     this.itemListBox.scrollToCentre();
/* 154 */     updateItemFromListBox(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleListBoxClick(GuiMacroParam<Integer> guiMacroParam) {
/* 164 */     updateItemFromListBox(true);
/*     */ 
/*     */     
/* 167 */     if (this.itemListBox.isDoubleClicked(true))
/*     */     {
/* 169 */       guiMacroParam.apply();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiControlEx.HandledState keyTyped(char keyChar, int keyCode) {
/* 177 */     if (this.itemListBox != null)
/*     */     {
/* 179 */       return this.itemListBox.listBoxKeyTyped(keyChar, keyCode);
/*     */     }
/*     */     
/* 182 */     return GuiControlEx.HandledState.NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void autoPopulate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTextFieldTextChanged() {
/* 196 */     if (this.itemListBox != null) {
/*     */       
/* 198 */       ItemID itemId = new ItemID(getParameterValue());
/* 199 */       if (itemId.isValid())
/*     */       {
/* 201 */         if (itemId.hasDamage) {
/*     */           
/* 203 */           this.itemListBox.selectIdentifierWithData(itemId.name, Integer.valueOf(itemId.damage));
/*     */         }
/*     */         else {
/*     */           
/* 207 */           this.itemListBox.selectIdentifier(itemId.name);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */