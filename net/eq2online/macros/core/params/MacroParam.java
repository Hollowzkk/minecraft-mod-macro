/*     */ package net.eq2online.macros.core.params;
/*     */ import bib;
/*     */ import bip;
/*     */ import bir;
/*     */ import blk;
/*     */ import cgp;
/*     */ import cgt;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderFriend;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderHome;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderItem;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderList;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderPlace;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderTown;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderUser;
/*     */ import net.eq2online.macros.core.params.providers.MacroParamProviderWarp;
/*     */ import net.eq2online.macros.core.settings.Settings;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*     */ import net.eq2online.macros.gui.screens.GuiMacroParam;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.macros.struct.ItemInfo;
/*     */ import qf;
/*     */ 
/*     */ public abstract class MacroParam<TItem> {
/*     */   public static final String ESCAPE_CHARACTER = "\\x5C";
/*     */   public static final String ESCAPE_CHARACTER_NONPRINTING = "";
/*     */   public static final String PARAM_PREFIX = "\\x24\\x24";
/*     */   public static final String PARAM_SEQUENCE = "(?<![\\x5C])\\x24\\x24";
/*     */   protected final Macros macros;
/*     */   protected final Settings settings;
/*     */   protected final bib mc;
/*     */   protected final MacroParams params;
/*     */   protected final MacroParamProvider<TItem> provider;
/*     */   protected final IMacroParamTarget target;
/*     */   protected final Type type;
/*     */   
/*     */   public enum Type {
/*  49 */     NAMED((String)MacroParamProviderNamed.class),
/*     */ 
/*     */     
/*  52 */     NORMAL((String)MacroParamProviderStandard.class),
/*     */ 
/*     */     
/*  55 */     ITEM((String)MacroParamProviderItem.class),
/*     */ 
/*     */     
/*  58 */     FRIEND((String)MacroParamProviderFriend.class),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     USER((String)MacroParamProviderUser.class),
/*     */ 
/*     */     
/*  67 */     TOWN((String)MacroParamProviderTown.class),
/*     */ 
/*     */     
/*  70 */     WARP((String)MacroParamProviderWarp.class),
/*     */ 
/*     */     
/*  73 */     HOME((String)MacroParamProviderHome.class),
/*     */ 
/*     */     
/*  76 */     PLACE((String)MacroParamProviderPlace.class),
/*     */ 
/*     */     
/*  79 */     FILE((String)MacroParamProviderFile.class),
/*     */ 
/*     */     
/*  82 */     PRESET((String)MacroParamProviderPreset.class),
/*     */ 
/*     */     
/*  85 */     RESOURCE_PACK((String)MacroParamProviderResourcePack.class),
/*     */ 
/*     */     
/*  88 */     SHADER_GROUP((String)MacroParamProviderShader.class),
/*     */ 
/*     */     
/*  91 */     LIST((String)MacroParamProviderList.class);
/*     */ 
/*     */     
/*     */     private Class<? extends MacroParamProvider> providerClass;
/*     */ 
/*     */ 
/*     */     
/*     */     Type(Class<? extends MacroParamProvider> providerClass) {
/*  99 */       this.providerClass = providerClass;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <TItem> MacroParamProvider<TItem> getProvider(Macros macros, bib minecraft) throws Exception {
/* 106 */       Constructor<? extends MacroParamProvider<TItem>> ctor = (Constructor)this.providerClass.getDeclaredConstructor(new Class[] { Macros.class, bib.class, Type.class });
/*     */       
/* 108 */       return ctor.newInstance(new Object[] { macros, minecraft, this });
/*     */     }
/*     */ 
/*     */     
/*     */     public String getKey() {
/* 113 */       return name();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getKey(String context) {
/* 118 */       return (context != null) ? String.format("%s%s", new Object[] { name(), context }) : name();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   protected int position = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String parameterValue;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Boolean enableTextField;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 187 */   protected int maxParameterLength = 256;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String promptMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String promptPrefix;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean itemListBoxInitialised = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 207 */   protected GuiListBox<TItem> itemListBox = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiTextFieldEx txtParam;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiCheckBox chkReplaceAll;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiMacroParam<TItem> parentScreen;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean replaceFirstOccurrenceOnly = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroParam(Macros macros, bib mc, Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<TItem> provider) {
/* 236 */     this.macros = macros;
/* 237 */     this.settings = macros.getSettings();
/* 238 */     this.mc = mc;
/* 239 */     this.type = type;
/* 240 */     this.target = target;
/* 241 */     this.params = params;
/* 242 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFound() {
/* 252 */     return (this.position > -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 262 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isType(Type type) {
/* 267 */     return (this.type == type);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPosition() {
/* 272 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public IMacroParamTarget getTarget() {
/* 277 */     return this.target;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getParameterValue() {
/* 282 */     return this.parameterValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getParameterValueEscaped() {
/* 287 */     return Macro.escapeReplacement(this.parameterValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParameterValue(String newValue) {
/* 292 */     this.parameterValue = newValue;
/*     */     
/* 294 */     if (this.txtParam != null)
/*     */     {
/* 296 */       this.txtParam.a(newValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiListBox<TItem> getListBox(int width, int height) {
/* 302 */     if (!this.itemListBoxInitialised) {
/*     */       
/* 304 */       this.itemListBoxInitialised = true;
/* 305 */       initListBox(width, height);
/*     */     } 
/*     */     
/* 308 */     if (this.itemListBox != null)
/*     */     {
/* 310 */       setupListBox(width, height);
/*     */     }
/*     */     
/* 313 */     return this.itemListBox;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initListBox(int width, int height) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setupListBox(int width, int height) {
/* 323 */     if (this.itemListBox != null)
/*     */     {
/* 325 */       this.itemListBox.setSize(180, height - (this.enableTextField.booleanValue() ? 40 : 4));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void initControls(int width, int height) {
/* 331 */     if (this.enableTextField.booleanValue()) {
/*     */       
/* 333 */       this.promptMessage = (this.target.getPromptMessage() == null) ? getPromptMessage() : this.target.getPromptMessage();
/* 334 */       this.promptPrefix = getPromptPrefix();
/*     */       
/* 336 */       bip fontRenderer = this.mc.k;
/* 337 */       int promptPrefixLength = fontRenderer.a(this.promptPrefix);
/* 338 */       if (promptPrefixLength > 0) promptPrefixLength += 6;
/*     */       
/* 340 */       if (this.txtParam == null) {
/*     */         
/* 342 */         this
/* 343 */           .txtParam = new GuiTextFieldEx(0, fontRenderer, promptPrefixLength + 4, height - 20, width - promptPrefixLength - 8, 16, this.parameterValue, getAllowedCharacters(), this.maxParameterLength);
/* 344 */         this.txtParam.b(true);
/* 345 */         this.txtParam.e(this.parameterValue.length() + 1);
/* 346 */         this.txtParam.i(0);
/*     */       }
/*     */       else {
/*     */         
/* 350 */         this.txtParam.setSizeAndPosition(promptPrefixLength + 4, height - 20, width - promptPrefixLength - 8, 16);
/*     */       } 
/*     */     } 
/*     */     
/* 354 */     if (isFirstOccurrenceSupported()) {
/*     */       
/* 356 */       this.chkReplaceAll = new GuiCheckBox(this.mc, 0, 0, 0, I18n.get("param.option.replaceall"), !shouldReplaceFirstOccurrenceOnly());
/* 357 */       this.chkReplaceAll.setPosition(width - 4 - this.chkReplaceAll.getWidth(), height - 39);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 363 */     if (this.txtParam != null)
/*     */     {
/* 365 */       this.txtParam.a();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getAllowedCharacters() {
/* 371 */     return AllowedCharacters.CHARACTERS;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int button) {
/* 376 */     if (this.enableTextField.booleanValue()) {
/*     */       
/* 378 */       this.txtParam.a(mouseX, mouseY, button);
/* 379 */       this.txtParam.b(true);
/*     */     } 
/*     */     
/* 382 */     if (this.chkReplaceAll != null && button == 0)
/*     */     {
/* 384 */       if (this.chkReplaceAll.b(this.mc, mouseX, mouseY)) {
/*     */         
/* 386 */         this.mc.U().a((cgt)cgp.a(qf.ic, 1.0F));
/* 387 */         setReplaceFirstOccurrenceOnly(!this.chkReplaceAll.checked);
/* 388 */         this.chkReplaceAll.checked = !shouldReplaceFirstOccurrenceOnly();
/* 389 */         return false;
/*     */       } 
/*     */     }
/*     */     
/* 393 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiControlEx.HandledState keyTyped(char keyChar, int keyCode) {
/* 399 */     if (this.itemListBox != null && keyCode != 203 && keyCode != 205)
/*     */     {
/* 401 */       return this.itemListBox.listBoxKeyTyped(keyChar, keyCode);
/*     */     }
/*     */     
/* 404 */     return GuiControlEx.HandledState.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void textFieldKeyTyped(char keyChar, int keyCode) {
/* 409 */     if (this.enableTextField.booleanValue()) {
/*     */       
/* 411 */       this.txtParam.a(keyChar, keyCode);
/* 412 */       this.parameterValue = this.txtParam.b();
/*     */       
/* 414 */       onTextFieldTextChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTextFieldTextChanged() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawControls(bib minecraft, int mouseX, int mouseY, float partialTicks, boolean enabled, bip fontRenderer, int width, int height, int updateCounter) {
/* 426 */     if (this.enableTextField.booleanValue() && this.txtParam != null) {
/*     */       
/* 428 */       bir.a(2, height - 36, width - 2, height - 2, -1342177280);
/* 429 */       fontRenderer.a(this.promptMessage, 6, height - 33, enabled ? 16777045 : 1143087650);
/* 430 */       fontRenderer.a(this.promptPrefix, 6, height - 16, enabled ? 16777045 : 1143087650);
/*     */       
/* 432 */       this.txtParam.g();
/*     */     } 
/*     */     
/* 435 */     if (this.chkReplaceAll != null)
/*     */     {
/* 437 */       this.chkReplaceAll.a(minecraft, mouseX, mouseY, partialTicks);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sortList() {
/* 443 */     if (this.itemListBox != null)
/*     */     {
/* 445 */       this.itemListBox.sort();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParent(GuiMacroParam<TItem> parentScreen) {
/* 451 */     this.parentScreen = parentScreen;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAutoPopulateSupported() {
/* 456 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void autoPopulateCancelled() {
/* 461 */     this.mc.a((blk)this.parentScreen);
/*     */   }
/*     */ 
/*     */   
/*     */   public void autoPopulateComplete(List<String> items) {
/* 466 */     if (this.parentScreen != null)
/*     */     {
/* 468 */       this.parentScreen.onAutoPopulateComplete(this, items);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void autoPopulate() {
/* 474 */     if (this.parentScreen != null)
/*     */     {
/* 476 */       this.mc.a((blk)this.parentScreen);
/*     */     }
/*     */     
/* 479 */     this.macros.getAutoDiscoveryHandler().activate(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean apply() {
/* 485 */     this.params.replaceParam(this);
/* 486 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean replace();
/*     */   
/*     */   public void setReplaceFirstOccurrenceOnly(boolean firstOnly) {
/* 493 */     if (isFirstOccurrenceSupported())
/*     */     {
/* 495 */       this.replaceFirstOccurrenceOnly = firstOnly;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldReplaceFirstOccurrenceOnly() {
/* 501 */     return this.replaceFirstOccurrenceOnly;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFirstOccurrenceSupported() {
/* 506 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addItem(GuiEditListEntry<TItem> gui, String newItem, String displayName, int iconID, TItem newItemData) {
/* 511 */     IListEntry<TItem> newListEntry = this.itemListBox.createObject(newItem, iconID, newItemData);
/* 512 */     newListEntry.setDisplayName(displayName);
/* 513 */     this.itemListBox.addItemAt(newListEntry, this.itemListBox.getItemCount() - 1);
/* 514 */     this.itemListBox.selectItem(newListEntry);
/* 515 */     this.itemListBox.save();
/* 516 */     setParameterValue(newItem);
/*     */     
/* 518 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void editItem(GuiEditListEntry<TItem> gui, String editedText, String displayName, int editedIconID, IListEntry<TItem> editedObject) {}
/*     */ 
/*     */   
/*     */   public void deleteSelectedItem(boolean response) {
/* 527 */     if (this.itemListBox != null)
/*     */     {
/* 529 */       if (response) {
/*     */         
/* 531 */         this.itemListBox.removeSelectedItem();
/* 532 */         this.itemListBox.save();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 537 */         setParameterValue((String)this.itemListBox.getSelectedItem().getData());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void importAllFrom(GuiListBox<TItem> sourceListBox) {
/* 544 */     if (this.itemListBox != null) {
/*     */       
/* 546 */       while (sourceListBox.getItemCount() > 0)
/*     */       {
/* 548 */         this.itemListBox.addItemAt(sourceListBox.removeItemAt(0), sourceListBox.getItemCount());
/*     */       }
/*     */       
/* 551 */       this.itemListBox.save();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleListBoxClick(GuiMacroParam<TItem> guiMacroParam) {
/* 557 */     if (this.itemListBox != null) {
/*     */       
/* 559 */       IListEntry<TItem> selectedItem = this.itemListBox.getSelectedItem();
/*     */       
/* 561 */       if (selectedItem != null)
/*     */       {
/* 563 */         setParameterValue(selectedItem.getText());
/*     */       }
/*     */ 
/*     */       
/* 567 */       if (this.itemListBox.isDoubleClicked(true))
/*     */       {
/* 569 */         guiMacroParam.apply();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateItemFromListBox(boolean includeDamage) {
/* 576 */     ItemInfo selectedItem = (ItemInfo)this.itemListBox.getSelectedItem();
/*     */ 
/*     */     
/* 579 */     String newParameterValue = selectedItem.getIdentifier();
/*     */     
/* 581 */     int damageValue = selectedItem.getData().intValue();
/* 582 */     if (damageValue > 0 && includeDamage)
/*     */     {
/* 584 */       newParameterValue = newParameterValue + ":" + damageValue;
/*     */     }
/*     */     
/* 587 */     setParameterValue(newParameterValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPromptPrefix() {
/* 592 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/* 597 */     String typeDescription = (this.type == Type.NORMAL) ? "" : (" " + this.type.toString());
/*     */     
/* 599 */     if (this.target.getIteration() > 1)
/*     */     {
/* 601 */       return getLocalisedString("param.prompt.inner", new String[] { this.target.getIterationString(), typeDescription, this.target
/* 602 */             .getDisplayName() });
/*     */     }
/*     */     
/* 605 */     return getLocalisedString("param.prompt.param", new String[] { typeDescription, this.target.getDisplayName() });
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String getLocalisedString(String string, String... args) {
/* 610 */     if (string == null) return ""; 
/* 611 */     String translation = I18n.getRaw(string);
/* 612 */     if (translation == null) translation = string; 
/* 613 */     String format = Util.convertAmpCodes(translation.replace("&e", "&f").replace("&c", "&e"));
/* 614 */     return String.format(format, (Object[])args);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupEditEntryWindow(GuiEditListEntry<TItem> gui, boolean editing) {
/* 619 */     gui.displayText = I18n.get(editing ? "entry.editentry" : "entry.newentry");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryTextbox(GuiTextFieldEx textField) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public nf getIconTexture() {
/* 629 */     return ResourceLocations.DYNAMIC_FRIENDS;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */