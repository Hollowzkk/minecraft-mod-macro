/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import com.mumfrey.liteloader.modconfig.ConfigPanel;
/*     */ import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.AutoDiscoveryHandler;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.SpamFilter;
/*     */ import net.eq2online.macros.core.settings.Settings;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownList;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.overlay.OverrideOverlay;
/*     */ import net.eq2online.macros.interfaces.annotations.DropdownLocalisationRoot;
/*     */ import net.eq2online.macros.interfaces.annotations.DropdownStyle;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GuiMacroConfigPanel
/*     */   implements ConfigPanel, GuiDropDownList.IDropDownContainer
/*     */ {
/*     */   static final int ID_EDITRESERVED = 4;
/*     */   protected final bib mc;
/*     */   private final Macros macros;
/*     */   private final GuiMacroConfig parentScreen;
/*     */   private final Settings settings;
/*     */   private final AutoDiscoveryHandler autoDiscoveryHandler;
/*     */   
/*     */   class ConfigOption
/*     */     implements GuiDropDownList.IDropDownContainer
/*     */   {
/*     */     protected final int displayHeight;
/*     */     protected final String binding;
/*     */     
/*     */     ConfigOption(int displayHeight) {
/*  48 */       this(null, displayHeight);
/*     */     }
/*     */ 
/*     */     
/*     */     protected ConfigOption(String binding, int displayHeight) {
/*  53 */       this.displayHeight = displayHeight;
/*  54 */       this.binding = binding;
/*     */     }
/*     */ 
/*     */     
/*     */     int getDisplayHeight() {
/*  59 */       return this.displayHeight;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void apply() {}
/*     */ 
/*     */     
/*     */     int draw(bib minecraft, int mouseX, int mouseY, int yPosition, float partialTicks) {
/*  68 */       return yPosition + this.displayHeight;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void postRender(bib minecraft, int mouseX, int mouseY) {}
/*     */ 
/*     */     
/*     */     boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/*  77 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getContainerWidth() {
/*  83 */       return GuiMacroConfigPanel.this.getContainerWidth();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getContainerHeight() {
/*  89 */       return GuiMacroConfigPanel.this.getContainerHeight() - 32;
/*     */     }
/*     */   }
/*     */   
/*     */   class CheckBoxOption
/*     */     extends ConfigOption
/*     */   {
/*     */     private final CheckBoxOption parent;
/*     */     private final GuiCheckBox checkBox;
/*     */     
/*     */     CheckBoxOption(int id, int xPosition, int height, String displayText, String binding) {
/* 100 */       this(id, xPosition, height, displayText, binding, null);
/*     */     }
/*     */ 
/*     */     
/*     */     CheckBoxOption(int id, int xPosition, int height, String displayText, String binding, CheckBoxOption parent) {
/* 105 */       super(binding, height);
/*     */       
/* 107 */       Boolean settingValue = GuiMacroConfigPanel.this.<Boolean>getSetting(binding);
/*     */       
/* 109 */       this.checkBox = new GuiCheckBox(GuiMacroConfigPanel.this.mc, id, xPosition, 0, I18n.get(displayText), settingValue.booleanValue());
/* 110 */       this.parent = parent;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 116 */       this.checkBox.b(GuiMacroConfigPanel.this.mc, mouseX, mouseY);
/* 117 */       return super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     int draw(bib minecraft, int mouseX, int mouseY, int yPosition, float partialTicks) {
/* 123 */       if (this.parent != null && this.parent.checkBox != null)
/*     */       {
/* 125 */         this.checkBox.l = this.parent.checkBox.checked;
/*     */       }
/*     */       
/* 128 */       this.checkBox.drawCheckboxAt(minecraft, mouseX, mouseY, yPosition, partialTicks);
/* 129 */       return super.draw(minecraft, mouseX, mouseY, yPosition, partialTicks);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void apply() {
/* 135 */       GuiMacroConfigPanel.this.setSetting(this.binding, Boolean.valueOf(this.checkBox.checked));
/*     */     }
/*     */   }
/*     */   
/*     */   class LabelOption
/*     */     extends ConfigOption
/*     */   {
/*     */     private final int xPosition;
/*     */     private final String displayText;
/*     */     private final int displayColour;
/*     */     
/*     */     LabelOption(String text, int xPosition, int height, int displayColour) {
/* 147 */       super(height);
/* 148 */       this.xPosition = xPosition;
/* 149 */       this.displayText = I18n.get(text);
/* 150 */       this.displayColour = displayColour;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     int draw(bib minecraft, int mouseX, int mouseY, int yPosition, float partialTicks) {
/* 156 */       minecraft.k.a(this.displayText, this.xPosition, yPosition, this.displayColour);
/* 157 */       return super.draw(minecraft, mouseX, mouseY, yPosition, partialTicks);
/*     */     }
/*     */   }
/*     */   
/*     */   class ButtonOption
/*     */     extends ConfigOption
/*     */   {
/*     */     private final GuiControl button;
/*     */     
/*     */     ButtonOption(int id, int xPosition, int width, int height, int displayHeight, String displayText) {
/* 167 */       super(displayHeight);
/* 168 */       this.button = new GuiControl(id, xPosition, 0, width, height, I18n.get(displayText));
/*     */     }
/*     */ 
/*     */     
/*     */     GuiControl getButton() {
/* 173 */       return this.button;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     int draw(bib minecraft, int mouseX, int mouseY, int yPosition, float partialTicks) {
/* 179 */       this.button.setYPosition(yPosition);
/* 180 */       this.button.a(minecraft, mouseX, mouseY, partialTicks);
/* 181 */       return super.draw(minecraft, mouseX, mouseY, yPosition, partialTicks);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 187 */       if (this.button.b(GuiMacroConfigPanel.this.mc, mouseX, mouseY))
/*     */       {
/* 189 */         GuiMacroConfigPanel.this.actionPerformed(this);
/*     */       }
/* 191 */       return super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */   }
/*     */   
/*     */   class TextBoxOption
/*     */     extends ConfigOption
/*     */   {
/*     */     protected GuiTextFieldEx textField;
/*     */     
/*     */     TextBoxOption(int id, int xPosition, int width, int height, int displayHeight, String binding) {
/* 201 */       this(binding, displayHeight);
/* 202 */       this
/* 203 */         .textField = new GuiTextFieldEx(id, GuiMacroConfigPanel.this.mc.k, xPosition, 0, width, height, GuiMacroConfigPanel.this.<T>getSetting(binding).toString());
/*     */     }
/*     */ 
/*     */     
/*     */     protected TextBoxOption(String binding, int displayHeight) {
/* 208 */       super(binding, displayHeight);
/*     */     }
/*     */ 
/*     */     
/*     */     GuiTextFieldEx getTextField() {
/* 213 */       return this.textField;
/*     */     }
/*     */ 
/*     */     
/*     */     void updateCursorCounter() {
/* 218 */       this.textField.a();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     int draw(bib minecraft, int mouseX, int mouseY, int yPosition, float partialTicks) {
/* 224 */       this.textField.drawTextBoxAt(yPosition);
/* 225 */       return super.draw(minecraft, mouseX, mouseY, yPosition, partialTicks);
/*     */     }
/*     */ 
/*     */     
/*     */     boolean textboxKeyTyped(char keyChar, int keyCode) {
/* 230 */       this.textField.a(keyChar, keyCode);
/* 231 */       return this.textField.m();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 237 */       this.textField.a(mouseX, mouseY, mouseButton);
/* 238 */       return super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void apply() {
/* 244 */       if (this.binding != null)
/*     */       {
/* 246 */         GuiMacroConfigPanel.this.setSetting(this.binding, getValue());
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected Object getValue() {
/* 252 */       return this.textField.b();
/*     */     }
/*     */   }
/*     */   
/*     */   class NumericTextBoxOption
/*     */     extends TextBoxOption
/*     */   {
/*     */     private final int defaultIntValue;
/*     */     
/*     */     NumericTextBoxOption(int id, int xPosition, int width, int height, int displayHeight, String binding, int digits) {
/* 262 */       super(id, xPosition, width, height, displayHeight, binding);
/*     */       
/* 264 */       this.defaultIntValue = ((Integer)GuiMacroConfigPanel.this.<Integer>getSetting(binding)).intValue();
/* 265 */       this.textField = new GuiTextFieldEx(id, GuiMacroConfigPanel.this.mc.k, xPosition, 0, width, height, this.defaultIntValue, digits);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object getValue() {
/* 272 */       return Integer.valueOf(this.textField.getValueInt(this.defaultIntValue));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class DropDownOption
/*     */     extends ConfigOption
/*     */   {
/*     */     private final GuiDropDownList.GuiDropDownListControl dropDown;
/*     */     
/*     */     private Enum<?> defaultEnumValue;
/*     */     private int yPosition;
/*     */     
/*     */     DropDownOption(int controlId, int xPosition, int controlWidth, int controlHeight, int itemHeight, int displayHeight, Enum<?> defaultValue, String binding) {
/* 286 */       super(binding, displayHeight);
/*     */       
/* 288 */       this.defaultEnumValue = defaultValue;
/* 289 */       this.dropDown = new GuiDropDownList.GuiDropDownListControl(this, GuiMacroConfigPanel.this.mc, controlId, xPosition, 0, controlWidth, controlHeight, itemHeight, "");
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 294 */         Class<? extends Enum<?>> enumClass = (Class)defaultValue.getDeclaringClass();
/*     */         
/* 296 */         Method values = enumClass.getDeclaredMethod("values", new Class[0]);
/* 297 */         Enum[] arrayOfEnum = (Enum[])values.invoke(null, new Object[0]);
/*     */         
/* 299 */         DropdownLocalisationRoot locRootAnnotation = enumClass.<DropdownLocalisationRoot>getAnnotation(DropdownLocalisationRoot.class);
/* 300 */         String localisationRoot = (locRootAnnotation != null) ? locRootAnnotation.value() : null;
/* 301 */         if (localisationRoot != null && !localisationRoot.endsWith("."))
/*     */         {
/* 303 */           localisationRoot = localisationRoot + ".";
/*     */         }
/*     */         
/* 306 */         for (Enum<?> enumValue : arrayOfEnum) {
/*     */           
/* 308 */           DropdownStyle dropDownStyle = enumValue.getClass().getField(enumValue.name()).<DropdownStyle>getAnnotation(DropdownStyle.class);
/*     */           
/* 310 */           if (dropDownStyle == null || !dropDownStyle.hideInDropdown())
/*     */           {
/* 312 */             String name = enumValue.toString().toLowerCase();
/* 313 */             String text = (localisationRoot != null) ? I18n.get(localisationRoot + name) : StringUtils.capitalize(name);
/* 314 */             this.dropDown.addItem(enumValue.name(), text);
/*     */           }
/*     */         
/*     */         } 
/* 318 */       } catch (Exception ex) {
/*     */         
/* 320 */         ex.printStackTrace();
/*     */       } 
/*     */       
/* 323 */       this.dropDown.selectItemByTag(GuiMacroConfigPanel.this.<T>getSetting(this.binding).toString());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     int draw(bib minecraft, int mouseX, int mouseY, int yPosition, float partialTicks) {
/* 329 */       this.yPosition = yPosition;
/* 330 */       return super.draw(minecraft, mouseX, mouseY, yPosition, partialTicks);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void postRender(bib minecraft, int mouseX, int mouseY) {
/* 336 */       this.dropDown.drawControlAt(minecraft, mouseX, mouseY, this.yPosition);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 342 */       return this.dropDown.b(GuiMacroConfigPanel.this.mc, mouseX, mouseY);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void apply() {
/* 348 */       Enum<?> value = GuiMacroConfigPanel.this.getEnumValue(this.dropDown.getSelectedItemTag(), this.defaultEnumValue);
/* 349 */       GuiMacroConfigPanel.this.setSetting(this.binding, value);
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
/* 365 */   private final List<ConfigOption> options = new ArrayList<>();
/*     */   
/* 367 */   private final List<TextBoxOption> textFields = new ArrayList<>();
/*     */   
/*     */   private int totalHeight;
/*     */   
/*     */   private boolean saveChanges = true;
/*     */   
/*     */   private int width;
/*     */ 
/*     */   
/*     */   public GuiMacroConfigPanel() {
/* 377 */     this(Macros.getInstance(), bib.z(), null);
/*     */   }
/*     */ 
/*     */   
/*     */   GuiMacroConfigPanel(Macros macros, bib minecraft, GuiMacroConfig parentScreen) {
/* 382 */     this.macros = macros;
/* 383 */     this.mc = minecraft;
/* 384 */     this.parentScreen = parentScreen;
/* 385 */     this.settings = macros.getSettings();
/* 386 */     this.autoDiscoveryHandler = this.macros.getAutoDiscoveryHandler();
/*     */     
/* 388 */     int displayColour = -22016;
/* 389 */     int controlId = 22;
/*     */     
/* 391 */     int left0 = 6;
/* 392 */     int left1 = 10;
/* 393 */     int left2 = 12;
/* 394 */     int left3 = 18;
/* 395 */     int left4 = 132;
/*     */     
/* 397 */     addOptionLabel("options.option.general", left0, 12, displayColour);
/*     */     
/* 399 */     addOptionCheckbox(controlId++, left1, 18, "options.option.enableoverridechat", "enableOverrideChat");
/* 400 */     addOptionCheckbox(controlId++, left1, 18, "options.option.enableoverridecmdblk", "enableOverrideCmdBlock");
/* 401 */     addOptionCheckbox(controlId++, left1, 18, "options.option.runstatus", "enableStatus");
/* 402 */     addOptionCheckbox(controlId++, left1, 28, "options.option.alwaysgoback", "alwaysGoBack");
/* 403 */     addOptionLabel("options.option.override.popup", left1, -4, 16777215);
/* 404 */     addOptionDropdown(controlId++, left4, 80, 16, 16, 18, (Enum<?>)OverrideOverlay.Style.MINI, "simpleOverridePopup");
/* 405 */     addOptionSpacer(12);
/*     */     
/* 407 */     addOptionLabel("options.option.gui", left0, 12, displayColour);
/* 408 */     addOptionCheckbox(controlId++, left1, 18, "options.option.simple", "simpleGui");
/* 409 */     addOptionCheckbox(controlId++, left1, 18, "options.option.animation", "enableGuiAnimation");
/* 410 */     addOptionCheckbox(controlId++, left1, 18, "options.option.rememberpage", "rememberBindPage");
/* 411 */     addOptionCheckbox(controlId++, left1, 18, "options.option.ignorepage", "bindIgnoresPage");
/* 412 */     addOptionCheckbox(controlId++, left1, 18, "options.option.optionsfirst", "defaultToOptions");
/* 413 */     addOptionCheckbox(controlId++, left1, 18, "options.option.largebuttons", "drawLargeEditorButtons");
/* 414 */     addOptionCheckbox(controlId++, left1, 18, "options.option.autoscale", "autoScaleKeyboard");
/* 415 */     addOptionSpacer(12);
/*     */     
/* 417 */     addOptionLabel("options.option.extra", left0, 12, displayColour);
/*     */     
/* 419 */     addOptionCheckbox(controlId++, left1, 18, "options.option.showslots", "showSlotInfo");
/* 420 */     addOptionCheckbox(controlId++, left1, 18, "options.option.buttonsonchat", "enableButtonsOnChatGui");
/* 421 */     addOptionCheckbox(controlId++, left1, 18, "options.option.filterablechat", "showFilterableChat");
/* 422 */     addOptionSpacer(12);
/*     */     
/* 424 */     addOptionLabel("options.option.spamfilter", left0, 12, displayColour);
/*     */     
/* 426 */     addOptionCheckbox(controlId++, left1, 18, "options.option.spamfilter.enable", "spamFilterEnabled");
/* 427 */     addOptionCheckbox(controlId++, left1, 28, "options.option.spamfilter.ignorecommands", "spamFilterIgnoreCommands");
/* 428 */     addOptionLabel("options.option.spamfilter.style", left1, -4, 16777215);
/* 429 */     addOptionDropdown(controlId++, left4, 80, 16, 16, 26, (Enum<?>)SpamFilter.FilterStyle.QUEUE, "spamFilterStyle");
/* 430 */     addOptionLabel("options.option.spamfilter.queuesize", left1, -4, 16777215);
/* 431 */     addOptionTextBox(controlId++, left4, 80, 16, 20, "spamFilterQueueSize", 3);
/* 432 */     addOptionSpacer(12);
/*     */     
/* 434 */     addOptionLabel("options.option.discovery", left0, 16, displayColour);
/*     */     
/* 436 */     addOptionLabel("options.option.commandhomelist", left2, 12, 16777215);
/* 437 */     addOptionTextbox(controlId++, left2, 200, 16, 18, "cmdHomeList");
/* 438 */     addOptionCheckbox(controlId++, left1, 28, "options.option.waitforhomecount", "requireHomeCount");
/* 439 */     addOptionLabel("options.option.commandtownlist", left2, 12, 16777215);
/* 440 */     addOptionTextbox(controlId++, left2, 200, 16, 28, "cmdTownList");
/* 441 */     addOptionLabel("options.option.commandwarplist", left2, 12, 16777215);
/* 442 */     addOptionTextbox(controlId++, left2, 200, 16, 24, "cmdWarps");
/* 443 */     addOptionLabel("options.option.commandwarplist2", left2, 12, 16777215);
/* 444 */     addOptionTextbox(controlId++, left2, 200, 16, 20, "cmdWarpMorePages");
/* 445 */     addOptionLabel("options.option.commandwarplist2help", left3, 12, 16777215);
/* 446 */     addOptionSpacer(12);
/*     */     
/* 448 */     addOptionLabel("options.option.configs", left0, 16, displayColour);
/*     */     
/* 450 */     addOptionLabel("options.option.configs.use", left2, 12, 16777215);
/* 451 */     addOptionCheckbox(controlId++, left1, 18, "options.option.configs.friends", "configsForFriends");
/* 452 */     addOptionCheckbox(controlId++, left1, 18, "options.option.configs.homes", "configsForHomes");
/* 453 */     addOptionCheckbox(controlId++, left1, 18, "options.option.configs.towns", "configsForTowns");
/* 454 */     addOptionCheckbox(controlId++, left1, 18, "options.option.configs.warps", "configsForWarps");
/* 455 */     addOptionCheckbox(controlId++, left1, 18, "options.option.configs.places", "configsForPlaces");
/* 456 */     addOptionCheckbox(controlId++, left1, 18, "options.option.configs.presets", "configsForPresets");
/* 457 */     addOptionSpacer(14);
/*     */     
/* 459 */     addOptionLabel("options.option.advanced", left0, 16, displayColour);
/* 460 */     if (this.parentScreen != null) addOptionButton(4, left1, 120, 20, 24, "options.option.reserved"); 
/* 461 */     addOptionCheckbox(controlId++, left1, 18, "options.option.compatibilitymode", "compatibilityMode");
/* 462 */     addOptionCheckbox(controlId++, left1, 18, "options.option.disabledeepinject", "disableDeepInjection");
/* 463 */     addOptionCheckbox(controlId++, left1, 18, "options.option.keyboardedit", "keyboardLayoutEditable");
/* 464 */     addOptionCheckbox(controlId++, left1, 18, "options.option.chathistory", "chatHistory");
/* 465 */     addOptionCheckbox(controlId++, left1, 18, "options.option.textboxhighlight", "enableHighlightTextFields");
/* 466 */     addOptionCheckbox(controlId++, left1, 18, "options.option.debounce", "templateDebounceEnabled");
/* 467 */     CheckBoxOption debug = addOptionCheckbox(controlId++, left1, 18, "options.option.debug", "enableDebug");
/* 468 */     addOptionCheckbox(controlId++, left3, 18, "options.option.debug.env", "debugEnvironment", debug);
/* 469 */     addOptionCheckbox(controlId++, left3, 18, "options.option.debug.env.keys", "debugEnvKeys", debug);
/* 470 */     addOptionSpacer(8);
/*     */   }
/*     */ 
/*     */   
/*     */   private ConfigOption addOptionSpacer(int height) {
/* 475 */     return addOption(new ConfigOption(height));
/*     */   }
/*     */ 
/*     */   
/*     */   private LabelOption addOptionLabel(String text, int xPosition, int height, int displayColour) {
/* 480 */     LabelOption newOption = new LabelOption(text, xPosition, height, displayColour);
/* 481 */     addOption(newOption);
/* 482 */     return newOption;
/*     */   }
/*     */ 
/*     */   
/*     */   private CheckBoxOption addOptionCheckbox(int id, int xPosition, int height, String displayText, String binding) {
/* 487 */     CheckBoxOption newOption = new CheckBoxOption(id, xPosition, height, displayText, binding);
/* 488 */     addOption(newOption);
/* 489 */     return newOption;
/*     */   }
/*     */ 
/*     */   
/*     */   private CheckBoxOption addOptionCheckbox(int id, int xPosition, int height, String displayText, String binding, CheckBoxOption parent) {
/* 494 */     CheckBoxOption newOption = new CheckBoxOption(id, xPosition, height, displayText, binding, parent);
/* 495 */     addOption(newOption);
/* 496 */     return newOption;
/*     */   }
/*     */ 
/*     */   
/*     */   private ConfigOption addOptionTextbox(int id, int xPosition, int width, int height, int displayHeight, String binding) {
/* 501 */     return addOption(new TextBoxOption(id, xPosition, width, height, displayHeight, binding));
/*     */   }
/*     */ 
/*     */   
/*     */   private ConfigOption addOptionTextBox(int id, int xPosition, int width, int height, int displayHeight, String binding, int digits) {
/* 506 */     return addOption(new NumericTextBoxOption(id, xPosition, width, height, displayHeight, binding, digits));
/*     */   }
/*     */ 
/*     */   
/*     */   private ConfigOption addOptionButton(int id, int xPosition, int width, int height, int displayHeight, String displayText) {
/* 511 */     return addOption(new ButtonOption(id, xPosition, width, height, displayHeight, displayText));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ConfigOption addOptionDropdown(int id, int xPosition, int controlWidth, int controlHeight, int itemHeight, int displayHeight, Enum<?> defaultValue, String binding) {
/* 517 */     return addOption(new DropDownOption(id, xPosition, controlWidth, controlHeight, itemHeight, displayHeight, defaultValue, binding));
/*     */   }
/*     */ 
/*     */   
/*     */   private ConfigOption addOption(ConfigOption option) {
/* 522 */     this.options.add(option);
/* 523 */     if (option instanceof TextBoxOption)
/*     */     {
/* 525 */       this.textFields.add((TextBoxOption)option);
/*     */     }
/* 527 */     this.totalHeight += option.getDisplayHeight();
/* 528 */     return option;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPanelTitle() {
/* 534 */     return I18n.get("mod.configpanel.title");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContentHeight() {
/* 540 */     return this.totalHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContainerWidth() {
/* 546 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContainerHeight() {
/* 552 */     return this.totalHeight - 32;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPanelShown(ConfigPanelHost host) {
/* 558 */     this.width = host.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPanelResize(ConfigPanelHost host) {
/* 564 */     this.width = host.getWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPanelHidden() {
/* 570 */     if (this.saveChanges) {
/*     */       
/* 572 */       for (ConfigOption option : this.options)
/*     */       {
/* 574 */         option.apply();
/*     */       }
/*     */       
/* 577 */       this.macros.save();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick(ConfigPanelHost host) {
/* 584 */     for (TextBoxOption option : this.textFields)
/*     */     {
/* 586 */       option.updateCursorCounter();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks) {
/* 593 */     int yPos = 4;
/*     */     
/* 595 */     for (ConfigOption option : this.options)
/*     */     {
/* 597 */       yPos = option.draw(this.mc, mouseX, mouseY, yPos, partialTicks);
/*     */     }
/*     */     
/* 600 */     for (int id = this.options.size(); id > 0; id--)
/*     */     {
/* 602 */       ((ConfigOption)this.options.get(id - 1)).postRender(this.mc, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {
/* 609 */     if (mouseButton != 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 614 */     for (ConfigOption option : this.options) {
/*     */       
/* 616 */       if (option.mouseClicked(mouseX, mouseY, mouseButton)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ConfigOption configOption) {
/* 625 */     if (configOption instanceof ButtonOption && this.parentScreen != null)
/*     */     {
/* 627 */       this.parentScreen.a((bja)((ButtonOption)configOption).getButton());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode) {
/* 644 */     handleKeyPress(host, keyChar, keyCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleKeyPress(ConfigPanelHost host, char keyChar, int keyCode) {
/* 649 */     if (keyCode == 1 && this.parentScreen == null) {
/*     */       
/* 651 */       this.saveChanges = false;
/* 652 */       host.close();
/*     */     } 
/*     */     
/* 655 */     if (keyCode == 15) {
/*     */       
/* 657 */       selectNextField();
/* 658 */       return true;
/*     */     } 
/*     */     
/* 661 */     boolean handled = false;
/* 662 */     for (TextBoxOption option : this.textFields)
/*     */     {
/* 664 */       handled |= option.textboxKeyTyped(keyChar, keyCode);
/*     */     }
/* 666 */     return handled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectNextField() {
/* 671 */     TextBoxOption firstTextBox = this.textFields.get(0);
/* 672 */     boolean found = false;
/* 673 */     boolean assigned = false;
/*     */     
/* 675 */     for (TextBoxOption option : this.textFields) {
/*     */       
/* 677 */       GuiTextFieldEx textField = option.getTextField();
/* 678 */       if (textField.m()) {
/*     */         
/* 680 */         textField.b(false);
/* 681 */         found = true; continue;
/*     */       } 
/* 683 */       if (found) {
/*     */         
/* 685 */         textField.b(!assigned);
/* 686 */         assigned = true;
/*     */         
/*     */         continue;
/*     */       } 
/* 690 */       textField.b(false);
/*     */     } 
/*     */ 
/*     */     
/* 694 */     if (!found || !assigned)
/*     */     {
/* 696 */       firstTextBox.getTextField().b(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Enum> T getEnumValue(String stringValue, T defaultValue) {
/*     */     try {
/* 705 */       Class<? extends T> enumClass = defaultValue.getDeclaringClass();
/* 706 */       return Enum.valueOf(enumClass, stringValue);
/*     */     }
/* 708 */     catch (IllegalArgumentException ex) {
/*     */       
/* 710 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T> T getSetting(String settingName) {
/* 716 */     T setting = (T)this.autoDiscoveryHandler.getSetting(settingName);
/* 717 */     if (setting != null)
/*     */     {
/* 719 */       return setting;
/*     */     }
/*     */     
/* 722 */     return (T)this.settings.getSetting(settingName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T> void setSetting(String settingName, T settingValue) {
/* 727 */     if (settingName == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 732 */     this.autoDiscoveryHandler.setSetting(settingName, settingValue);
/* 733 */     this.settings.setSetting(settingName, settingValue);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiMacroConfigPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */