/*      */ package net.eq2online.macros.gui.screens;
/*      */ 
/*      */ import bib;
/*      */ import bja;
/*      */ import blk;
/*      */ import bme;
/*      */ import java.io.IOException;
/*      */ import java.util.List;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.eq2online.macros.compatibility.I18n;
/*      */ import net.eq2online.macros.core.MacroPlaybackType;
/*      */ import net.eq2online.macros.core.MacroTemplate;
/*      */ import net.eq2online.macros.core.MacroTriggerType;
/*      */ import net.eq2online.macros.core.Macros;
/*      */ import net.eq2online.macros.core.settings.Settings;
/*      */ import net.eq2online.macros.gui.GuiControl;
/*      */ import net.eq2online.macros.gui.controls.GuiButtonTab;
/*      */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*      */ import net.eq2online.macros.gui.controls.GuiColourCodeSelector;
/*      */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*      */ import net.eq2online.macros.gui.controls.specialised.GuiTextFieldWithHighlight;
/*      */ import net.eq2online.macros.input.IProhibitOverride;
/*      */ import net.eq2online.macros.input.InputHandler;
/*      */ import net.eq2online.macros.interfaces.IHighlighter;
/*      */ import net.eq2online.macros.scripting.VariableExpander;
/*      */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*      */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*      */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*      */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ 
/*      */ public class GuiMacroEdit
/*      */   extends GuiScreenWithHeader
/*      */   implements IProhibitOverride
/*      */ {
/*      */   protected static final int ID_BTN_SAVE = 0;
/*      */   protected static final int ID_BTN_CANCEL = 1;
/*      */   protected static final int ID_BTN_MODE = 2;
/*      */   protected static final int ID_BTN_EDITFILE = 3;
/*      */   protected static final int ID_CHK_SHIFT = 6;
/*      */   protected static final int ID_CHK_ALT = 5;
/*      */   protected static final int ID_CHK_CTRL = 4;
/*      */   protected static final int ID_CHK_INHIBIT = 7;
/*      */   protected static final int ID_CHK_GLOBAL = 8;
/*      */   protected static final int ID_CHK_ALWAYS = 9;
/*      */   protected static final int ID_BTN_SWAP = 50;
/*      */   protected static final int ID_BTN_CMDREF = 51;
/*      */   protected static final int ID_TXT_KEYDOWN = 0;
/*      */   protected static final int ID_TXT_KEYHELD = 1;
/*      */   protected static final int ID_TXT_REPEAT = 2;
/*      */   protected static final int ID_TXT_KEYUP = 3;
/*      */   protected static final int ID_TXT_CONDITION = 4;
/*      */   protected static final int ID_TAB_NORMAL = 100;
/*      */   protected static final int ID_TAB_KEYSTATE = 101;
/*      */   protected static final int ID_TAB_CONDITIONAL = 102;
/*      */   protected final int key;
/*      */   protected final Macros macros;
/*      */   protected final Settings settings;
/*      */   protected final InputHandler inputHandler;
/*      */   protected final MacroTemplate template;
/*      */   protected final MacroTemplate.Options options;
/*      */   protected final blk parentScreen;
/*      */   protected IHighlighter highlighter;
/*      */   protected PreviousScreenInfo last;
/*      */   protected boolean showOptions;
/*      */   protected boolean displayColourCodeHelper;
/*      */   
/*      */   class PreviousScreenInfo {
/*      */     final GuiTextFieldEx selectedTextField;
/*      */     final int cursorPos;
/*      */     
/*      */     public PreviousScreenInfo(GuiTextFieldEx selectedTextField) {
/*   74 */       this.selectedTextField = selectedTextField;
/*   75 */       this.cursorPos = selectedTextField.i();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  128 */   protected char colourCodeKeyChar = Character.MIN_VALUE;
/*      */   
/*      */   protected GuiColourCodeSelector colourCodeHelper;
/*      */   
/*      */   protected GuiButtonTab btnTabNormal;
/*      */   
/*      */   protected GuiButtonTab btnTabKeystate;
/*      */   
/*      */   protected GuiButtonTab btnTabConditional;
/*      */   
/*      */   protected GuiControl btnEditFile;
/*      */   
/*      */   protected GuiControl btnMode;
/*      */   
/*      */   protected GuiControl btnCancel;
/*      */   
/*      */   protected GuiControl btnSave;
/*      */   protected GuiControl btnSwap;
/*      */   protected GuiControl btnCommands;
/*      */   protected GuiCheckBox chkControl;
/*      */   protected GuiCheckBox chkAlt;
/*      */   protected GuiCheckBox chkShift;
/*      */   protected GuiCheckBox chkInhibit;
/*      */   protected GuiCheckBox chkGlobal;
/*      */   protected GuiCheckBox chkAlways;
/*      */   protected final GuiTextFieldEx txtKeyDownMacro;
/*      */   protected final GuiTextFieldEx txtKeyHeldMacro;
/*      */   protected final GuiTextFieldEx txtKeyUpMacro;
/*      */   protected final GuiTextFieldEx txtRepeatDelay;
/*      */   protected final GuiTextFieldEx txtCondition;
/*      */   protected boolean displayConditionResult;
/*      */   protected boolean conditionResult;
/*      */   protected int textBoxPosition;
/*      */   protected int boxPaneTop;
/*      */   
/*      */   public GuiMacroEdit(Macros macros, bib minecraft, blk parentScreen, int key) {
/*  164 */     super(minecraft, 0, 0);
/*      */     
/*  166 */     this.macros = macros;
/*  167 */     this.settings = macros.getSettings();
/*  168 */     this.parentScreen = parentScreen;
/*  169 */     this.key = key;
/*  170 */     this.template = this.macros.getMacroTemplate(key);
/*  171 */     this.inputHandler = this.macros.getInputHandler();
/*  172 */     this.options = this.template.getOptions();
/*      */     
/*  174 */     this.showOptions = this.settings.defaultToOptions;
/*      */     
/*  176 */     this.bannerCentred = false;
/*      */     
/*  178 */     this.txtKeyDownMacro = createTextField(0, this.template.getKeyDownMacro());
/*  179 */     this.txtKeyHeldMacro = createTextField(1, this.template.getKeyHeldMacro());
/*  180 */     this.txtRepeatDelay = createTextField(2, this.template.getRepeatRate(), 4);
/*  181 */     this.txtKeyUpMacro = createTextField(3, this.template.getKeyUpMacro());
/*  182 */     this.txtCondition = createTextField(4, this.template.getMacroCondition());
/*      */   }
/*      */ 
/*      */   
/*      */   protected GuiTextFieldEx createTextField(int id, String initialText) {
/*  187 */     if (this.settings.enableHighlightTextFields)
/*      */     {
/*  189 */       return (GuiTextFieldEx)new GuiTextFieldWithHighlight(id, this.q, 0, 0, 1000, 20, initialText);
/*      */     }
/*      */     
/*  192 */     return new GuiTextFieldEx(id, this.q, 0, 0, 1000, 20, initialText);
/*      */   }
/*      */ 
/*      */   
/*      */   protected GuiTextFieldEx createTextField(int id, int initialValue, int digits) {
/*  197 */     if (this.settings.enableHighlightTextFields)
/*      */     {
/*  199 */       return (GuiTextFieldEx)new GuiTextFieldWithHighlight(id, this.q, 0, 0, 1000, 20, initialValue, digits);
/*      */     }
/*      */     
/*  202 */     return new GuiTextFieldEx(id, this.q, 0, 0, 1000, 20, initialValue, digits);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void b() {
/*  211 */     Keyboard.enableRepeatEvents(true);
/*      */     
/*  213 */     this.n.clear();
/*      */     
/*  215 */     this.colourCodeHelper = new GuiColourCodeSelector(46, 46, 200, 200, 2);
/*      */     
/*  217 */     this
/*  218 */       .btnEditFile = new GuiControl(3, this.l - 82, 63, 78, 20, I18n.get("macro.action.editfile"), 5);
/*  219 */     this
/*  220 */       .btnCancel = new GuiControl(1, this.l - 82, this.m - 46, 78, 20, I18n.get("gui.cancel"), 1);
/*  221 */     this
/*  222 */       .btnSave = new GuiControl(0, this.l - 82, this.m - 24, 78, 20, I18n.get("gui.save"), 7);
/*  223 */     this
/*  224 */       .btnMode = new GuiControl(2, this.l - 82, 89, 78, 20, I18n.get("macro.option.title"), 4);
/*  225 */     this
/*  226 */       .btnCommands = new GuiControl(51, this.l - 82, 111, 78, 20, I18n.get("macro.option.cmdref"), 6);
/*  227 */     this.btnSwap = new GuiControl(50, this.l - 110, this.m - 34, 20, 20, "", 10);
/*      */     
/*  229 */     initControls();
/*  230 */     setupTextBoxes();
/*  231 */     setupControls();
/*      */     
/*  233 */     this.highlighter = (IHighlighter)this.macros.getHighlighter(this.options.getPlaybackType());
/*      */     
/*  235 */     this.bannerColour = 4259648;
/*  236 */     this.bgBottomMargin = 16;
/*  237 */     this.drawBackground = false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initControls() {
/*  242 */     addControl(this.btnEditFile);
/*  243 */     addControl(this.btnCancel);
/*  244 */     addControl(this.btnSave);
/*  245 */     addControl(this.btnMode);
/*  246 */     addControl(this.btnCommands);
/*  247 */     addControl(this.btnSwap);
/*      */     
/*  249 */     addControl(
/*  250 */         (GuiControl)(this.chkControl = new GuiCheckBox(this.j, 4, 14, this.m - 165, I18n.get("macro.keyname.control"), this.options.control)));
/*  251 */     addControl(
/*  252 */         (GuiControl)(this.chkAlt = new GuiCheckBox(this.j, 5, 94, this.m - 165, I18n.get("macro.keyname.alt"), this.options.alt)));
/*  253 */     addControl(
/*  254 */         (GuiControl)(this.chkShift = new GuiCheckBox(this.j, 6, 174, this.m - 165, I18n.get("macro.keyname.shift"), this.options.shift)));
/*  255 */     addControl(
/*  256 */         (GuiControl)(this.chkGlobal = new GuiCheckBox(this.j, 8, 14, this.m - 133, I18n.get("macro.option.global"), this.options.isGlobal)));
/*  257 */     addControl(
/*  258 */         (GuiControl)(this.chkInhibit = new GuiCheckBox(this.j, 7, 14, this.m - 68, I18n.get("macro.option.inhibit"), this.options.inhibitParams)));
/*  259 */     addControl(
/*  260 */         (GuiControl)(this.chkAlways = new GuiCheckBox(this.j, 9, 14, this.m - 101, I18n.get("macro.option.always"), this.options.isOverride)));
/*      */     
/*  262 */     initTabButtons(20);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initTabButtons(int height) {
/*  267 */     addControl(
/*  268 */         (GuiControl)(this.btnTabNormal = new GuiButtonTab(100, 2, 22, 100, height, I18n.get("macro.mode.normal"))));
/*  269 */     addControl(
/*  270 */         (GuiControl)(this.btnTabKeystate = new GuiButtonTab(101, 104, 22, 100, height, I18n.get("macro.mode.keystate"))));
/*  271 */     addControl(
/*  272 */         (GuiControl)(this.btnTabConditional = new GuiButtonTab(102, 206, 22, 100, height, I18n.get("macro.mode.conditional"))));
/*      */     
/*  274 */     this.btnTabKeystate.l = this.options.isType(MacroTriggerType.KEY);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setupTextBoxes() {
/*  279 */     this.txtKeyDownMacro.setSize(this.l - 156, 16);
/*  280 */     this.txtKeyHeldMacro.setSize(this.l - 226 - this.q.a(I18n.get("macro.prompt.repeatrate")), 16);
/*  281 */     this.txtRepeatDelay.setSize(60, 16);
/*  282 */     this.txtKeyUpMacro.setSize(this.l - 156, 16);
/*  283 */     this.txtCondition.setSize(this.l - 156, 16);
/*      */     
/*  285 */     if (this.last == null) {
/*      */       
/*  287 */       this.txtKeyDownMacro.scrollToEnd();
/*  288 */       this.txtKeyHeldMacro.scrollToEnd();
/*  289 */       this.txtKeyUpMacro.scrollToEnd();
/*  290 */       this.txtCondition.scrollToEnd();
/*      */       
/*  292 */       this.txtKeyDownMacro.b(true);
/*      */     }
/*      */     else {
/*      */       
/*  296 */       this.last.selectedTextField.b(true);
/*  297 */       this.last.selectedTextField.e(this.last.cursorPos);
/*  298 */       this.last = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getTextBoxPosition() {
/*  304 */     if (this.options.isPlaybackType(MacroPlaybackType.KEYSTATE))
/*      */     {
/*  306 */       return this.m - 62;
/*      */     }
/*  308 */     if (this.options.isPlaybackType(MacroPlaybackType.CONDITIONAL))
/*      */     {
/*  310 */       return this.m - 72;
/*      */     }
/*      */     
/*  313 */     return this.m - 22;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getPaneTop() {
/*  318 */     if (this.options.isPlaybackType(MacroPlaybackType.KEYSTATE)) {
/*      */       
/*  320 */       if (this.options.isType(MacroTriggerType.EVENT))
/*      */       {
/*  322 */         this.options.setPlaybackType(MacroPlaybackType.ONESHOT);
/*      */       }
/*      */       else
/*      */       {
/*  326 */         return this.textBoxPosition - 152 + 40;
/*      */       }
/*      */     
/*  329 */     } else if (this.options.isPlaybackType(MacroPlaybackType.CONDITIONAL)) {
/*      */       
/*  331 */       return this.textBoxPosition - 152 + 50;
/*      */     } 
/*      */     
/*  334 */     return this.textBoxPosition - 152;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setupControls() {
/*  339 */     this.textBoxPosition = getTextBoxPosition();
/*  340 */     this.boxPaneTop = getPaneTop();
/*      */     
/*  342 */     this.chkGlobal.checked = this.options.isGlobal;
/*      */     
/*  344 */     setupControlPositions(18, this.boxPaneTop + 9);
/*      */ 
/*      */     
/*  347 */     boolean showCheckBoxes = showOptions();
/*  348 */     this.chkInhibit.setVisible((!this.options.isPlaybackType(MacroPlaybackType.KEYSTATE) && showCheckBoxes));
/*  349 */     this.chkAlways.setVisible(showCheckBoxes);
/*  350 */     this.chkGlobal.setVisible(showCheckBoxes);
/*  351 */     this.chkControl.setVisible(showCheckBoxes);
/*  352 */     this.chkAlt.setVisible(showCheckBoxes);
/*  353 */     this.chkShift.setVisible(showCheckBoxes);
/*  354 */     this.btnSwap.setVisible(this.options.isPlaybackType(MacroPlaybackType.CONDITIONAL));
/*      */ 
/*      */     
/*  357 */     this.chkInhibit.setEnabled((this.options.isType(MacroTriggerType.KEY) || this.options.isType(MacroTriggerType.CONTROL)));
/*  358 */     this.chkAlways.setEnabled((this.options.isType(MacroTriggerType.KEY) && !this.inputHandler.isFallbackMode()));
/*  359 */     this.chkControl.setEnabled(this.options.isType(MacroTriggerType.KEY));
/*  360 */     this.chkAlt.setEnabled(this.options.isType(MacroTriggerType.KEY));
/*  361 */     this.chkShift.setEnabled(this.options.isType(MacroTriggerType.KEY));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setupControlPositions(int left, int top) {
/*  366 */     int spc1 = this.options.isPlaybackType(MacroPlaybackType.CONDITIONAL) ? 16 : 32;
/*  367 */     int spc2 = this.options.isPlaybackType(MacroPlaybackType.CONDITIONAL) ? 4 : 0;
/*      */     
/*  369 */     this.chkControl.setPosition(left, top);
/*  370 */     this.chkAlt.setPosition(left + 80, top);
/*  371 */     this.chkShift.setPosition(left + 160, top); top += spc1 + spc2;
/*  372 */     this.chkGlobal.setPosition(left, top); top += spc1;
/*  373 */     this.chkAlways.setPosition(left, top); top += spc1;
/*  374 */     this.chkInhibit.setPosition(left, top);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean showOptions() {
/*  379 */     return this.showOptions;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setPlaybackType(MacroPlaybackType type) {
/*  384 */     this.options.setPlaybackType(type);
/*  385 */     setupControls();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setHelperPosition(int helperPosition) {
/*  390 */     this.colourCodeHelper.setXPosition(Math.min(this.l - this.colourCodeHelper.getWidth() - 4, helperPosition + 4));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void m() {
/*  399 */     Keyboard.enableRepeatEvents(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void displayParentScreen(boolean goBack) {
/*  404 */     if (this.parentScreen != null) {
/*      */       
/*  406 */       this.j.a(this.parentScreen);
/*      */       
/*      */       return;
/*      */     } 
/*  410 */     if (goBack) {
/*      */       
/*  412 */       this.j.a((blk)new GuiMacroBind(this.macros, this.j, false, false));
/*      */       
/*      */       return;
/*      */     } 
/*  416 */     if (this.j.f == null) {
/*      */       
/*  418 */       this.j.a((blk)new bme(null, this.j.t));
/*      */       
/*      */       return;
/*      */     } 
/*  422 */     this.j.a(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void e() {
/*  431 */     this.updateCounter++;
/*      */     
/*  433 */     this.txtKeyDownMacro.a();
/*  434 */     this.txtKeyHeldMacro.a();
/*  435 */     this.txtRepeatDelay.a();
/*  436 */     this.txtKeyUpMacro.a();
/*  437 */     this.txtCondition.a();
/*      */     
/*  439 */     if (this.options.isPlaybackType(MacroPlaybackType.CONDITIONAL)) {
/*      */       
/*  441 */       this.displayConditionResult = this.settings.showConditionalStateInGui;
/*  442 */       this.conditionResult = (this.displayConditionResult && evaluateCondition(this.txtCondition.b()));
/*      */     }
/*      */     else {
/*      */       
/*  446 */       this.conditionResult = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void a(bja guibutton) {
/*  458 */     if (guibutton == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  463 */     if (guibutton.k == 0 || guibutton.k == 1) {
/*      */       
/*  465 */       if (guibutton.k == 0)
/*      */       {
/*  467 */         save();
/*      */       }
/*  469 */       displayParentScreen(true);
/*      */       return;
/*      */     } 
/*  472 */     if (guibutton.k == 2) {
/*      */       
/*  474 */       this.showOptions = !this.showOptions;
/*  475 */       setupControls();
/*      */     }
/*  477 */     else if (guibutton.k == 3) {
/*      */       
/*  479 */       String suggest = null;
/*  480 */       Matcher filePattern = Pattern.compile("<([A-Za-z0-9\\x20_\\-\\.]+\\.txt)>").matcher(this.txtKeyDownMacro.b());
/*      */       
/*  482 */       if (filePattern.find())
/*      */       {
/*  484 */         suggest = filePattern.group(1);
/*      */       }
/*      */       
/*  487 */       this.j.a((blk)new GuiEditTextFile(this.macros, this.j, this, suggest, ScriptContext.MAIN));
/*      */       
/*      */       return;
/*      */     } 
/*  491 */     if (this.options.isType(MacroTriggerType.KEY)) {
/*      */       
/*  493 */       if (guibutton.k == 4) this.options.control = ((GuiCheckBox)guibutton).checked; 
/*  494 */       if (guibutton.k == 5) this.options.alt = ((GuiCheckBox)guibutton).checked; 
/*  495 */       if (guibutton.k == 6) this.options.shift = ((GuiCheckBox)guibutton).checked; 
/*  496 */       if (guibutton.k == 9) this.options.isOverride = ((GuiCheckBox)guibutton).checked;
/*      */     
/*      */     } 
/*  499 */     if (this.options.isType(MacroTriggerType.KEY) || this.options.isType(MacroTriggerType.CONTROL))
/*      */     {
/*  501 */       if (guibutton.k == 7)
/*      */       {
/*  503 */         this.options.inhibitParams = ((GuiCheckBox)guibutton).checked;
/*      */       }
/*      */     }
/*      */     
/*  507 */     if (guibutton.k == 8) {
/*      */       
/*  509 */       this.options.isGlobal = ((GuiCheckBox)guibutton).checked;
/*      */     }
/*  511 */     else if (guibutton.k == 100) {
/*      */       
/*  513 */       setPlaybackType(MacroPlaybackType.ONESHOT);
/*      */     }
/*  515 */     else if (guibutton.k == 101) {
/*      */       
/*  517 */       setPlaybackType(MacroPlaybackType.KEYSTATE);
/*      */     }
/*  519 */     else if (guibutton.k == 102) {
/*      */       
/*  521 */       setPlaybackType(MacroPlaybackType.CONDITIONAL);
/*      */     } 
/*      */     
/*  524 */     if (guibutton.k > 99 && guibutton.k < 103) {
/*      */       
/*  526 */       this.txtKeyDownMacro.b(true);
/*  527 */       this.txtKeyHeldMacro.b(false);
/*  528 */       this.txtRepeatDelay.b(false);
/*  529 */       this.txtKeyUpMacro.b(false);
/*  530 */       this.txtCondition.b(false);
/*      */     } 
/*      */     
/*  533 */     if (guibutton.k == 50 && this.options.isPlaybackType(MacroPlaybackType.CONDITIONAL)) {
/*      */       
/*  535 */       String trueText = this.txtKeyDownMacro.b();
/*  536 */       String falseText = this.txtKeyUpMacro.b();
/*      */       
/*  538 */       this.txtKeyDownMacro.a(falseText);
/*  539 */       this.txtKeyUpMacro.a(trueText);
/*      */       
/*  541 */       this.txtKeyDownMacro.scrollToEnd();
/*  542 */       this.txtKeyUpMacro.scrollToEnd();
/*      */     } 
/*      */     
/*  545 */     if (guibutton.k == 51)
/*      */     {
/*  547 */       this.j.a((blk)new GuiCommandReference(this.j, (blk)this));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void l() throws IOException {
/*  560 */     if (Keyboard.getEventKey() == this.settings.getColourCodeHelperKey(this.inputHandler.getOverrideKeyCode()) && this.colourCodeHelper != null && 
/*  561 */       !this.txtRepeatDelay.m()) {
/*      */       
/*  563 */       if (Keyboard.getEventKeyState()) {
/*      */         
/*  565 */         this.displayColourCodeHelper = true;
/*  566 */         this.colourCodeHelper.setColour(-1);
/*  567 */         this.colourCodeKeyChar = Keyboard.getEventCharacter();
/*      */       }
/*      */       else {
/*      */         
/*  571 */         this.displayColourCodeHelper = false;
/*      */         
/*  573 */         if (this.colourCodeHelper.hasColour())
/*      */         {
/*  575 */           String appendCode = String.format(this.settings.colourCodeFormat, new Object[] { Character.valueOf(this.colourCodeHelper.getColourCode()) });
/*      */           
/*  577 */           this.txtKeyDownMacro.insertText(appendCode);
/*  578 */           this.txtKeyHeldMacro.insertText(appendCode);
/*  579 */           this.txtKeyUpMacro.insertText(appendCode);
/*      */         }
/*      */         else
/*      */         {
/*  583 */           a(this.colourCodeKeyChar, Keyboard.getEventKey());
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  589 */       super.l();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void a(char keyChar, int keyCode) {
/*  601 */     if (this.macros.getLayoutPanels().getKeyboardLayout().keyPressed(keyChar, keyCode)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  606 */     if (keyCode == 1) {
/*      */       
/*  608 */       displayParentScreen(this.settings.alwaysGoBack);
/*      */       
/*      */       return;
/*      */     } 
/*  612 */     if (keyCode == 59) {
/*      */       
/*  614 */       displayContextHelp();
/*      */       
/*      */       return;
/*      */     } 
/*  618 */     if (this.displayColourCodeHelper) {
/*      */       
/*  620 */       this.colourCodeHelper.keyTyped(keyChar, keyCode);
/*      */       
/*      */       return;
/*      */     } 
/*  624 */     if (keyCode == 28 || keyCode == 156) {
/*      */ 
/*      */       
/*  627 */       save();
/*  628 */       displayParentScreen(this.settings.alwaysGoBack);
/*      */       
/*      */       return;
/*      */     } 
/*  632 */     this.txtKeyDownMacro.a(keyChar, keyCode);
/*  633 */     this.txtKeyHeldMacro.a(keyChar, keyCode);
/*  634 */     this.txtRepeatDelay.a(keyChar, keyCode);
/*  635 */     this.txtKeyUpMacro.a(keyChar, keyCode);
/*  636 */     this.txtCondition.a(keyChar, keyCode);
/*      */     
/*  638 */     if (keyCode == 15) {
/*      */       
/*  640 */       boolean shift = InputHandler.isShiftDown();
/*      */       
/*  642 */       if (InputHandler.isControlDown()) {
/*      */         
/*  644 */         int ordinal = (this.options.getPlaybackType().ordinal() + (shift ? 2 : 1)) % 3;
/*  645 */         setPlaybackType(MacroPlaybackType.values()[ordinal]);
/*      */         
/*      */         return;
/*      */       } 
/*  649 */       if (this.options.isPlaybackType(MacroPlaybackType.KEYSTATE)) {
/*      */         
/*  651 */         if (this.txtKeyDownMacro.m())
/*      */         {
/*  653 */           setTextBoxFocusedState(shift ? 3 : 1);
/*      */         }
/*  655 */         else if (this.txtKeyHeldMacro.m())
/*      */         {
/*  657 */           setTextBoxFocusedState(shift ? 0 : 2);
/*      */         }
/*  659 */         else if (this.txtRepeatDelay.m())
/*      */         {
/*  661 */           setTextBoxFocusedState(shift ? 1 : 3);
/*      */         }
/*  663 */         else if (this.txtKeyUpMacro.m())
/*      */         {
/*  665 */           setTextBoxFocusedState(shift ? 2 : 0);
/*      */         }
/*      */       
/*  668 */       } else if (this.options.isPlaybackType(MacroPlaybackType.CONDITIONAL)) {
/*      */         
/*  670 */         if (this.txtCondition.m()) {
/*      */           
/*  672 */           setTextBoxFocusedState(shift ? 3 : 0);
/*      */         }
/*  674 */         else if (this.txtKeyDownMacro.m()) {
/*      */           
/*  676 */           setTextBoxFocusedState(shift ? 4 : 3);
/*      */         }
/*  678 */         else if (this.txtKeyUpMacro.m()) {
/*      */           
/*  680 */           setTextBoxFocusedState(shift ? 0 : 4);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void displayContextHelp() {
/*  688 */     GuiTextFieldEx textbox = getFocusedTextField();
/*  689 */     if (textbox != null) {
/*      */       
/*  691 */       this.last = new PreviousScreenInfo(textbox);
/*  692 */       if (textbox != this.txtCondition) {
/*      */         
/*      */         try {
/*      */           
/*  696 */           String text = textbox.b();
/*  697 */           int cursor = textbox.i();
/*  698 */           int pos = cursor - 1;
/*  699 */           int pos2 = cursor;
/*  700 */           while (pos > 0 && text.substring(pos).matches("^[A-Za-z].*"))
/*      */           {
/*  702 */             pos--;
/*      */           }
/*      */           
/*  705 */           while (pos2 < text.length() && text.substring(pos2).matches("[A-Za-z].*"))
/*      */           {
/*  707 */             pos2++;
/*      */           }
/*      */           
/*  710 */           this.j.a((blk)new GuiCommandReference(this.j, (blk)this, text.substring(pos + 1, pos2)));
/*      */           
/*      */           return;
/*  713 */         } catch (Exception ex) {
/*      */           
/*  715 */           ex.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  720 */     this.j.a((blk)new GuiCommandReference(this.j, (blk)this));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTextBoxFocusedState(int textBoxIndex) {
/*  725 */     this.txtKeyDownMacro.b((textBoxIndex == 0));
/*  726 */     this.txtKeyHeldMacro.b((textBoxIndex == 1));
/*  727 */     this.txtRepeatDelay.b((textBoxIndex == 2));
/*  728 */     this.txtKeyUpMacro.b((textBoxIndex == 3));
/*  729 */     this.txtCondition.b((textBoxIndex == 4));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void save() {
/*  737 */     this.options.setRepeatRate(this.txtRepeatDelay.b());
/*  738 */     this.macros.updateMacroTemplate(this.key, this.template, this.options, this.txtKeyDownMacro
/*      */ 
/*      */ 
/*      */         
/*  742 */         .b(), this.txtKeyHeldMacro
/*  743 */         .b(), this.txtKeyUpMacro
/*  744 */         .b(), this.txtCondition
/*  745 */         .b());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(int mouseX, int mouseY, float f) {
/*  759 */     if (this.j.f == null)
/*      */     {
/*  761 */       c();
/*      */     }
/*      */     
/*  764 */     drawBackground(-1342177280, -1607257293);
/*      */     
/*  766 */     drawPrompt();
/*  767 */     drawHelp(6, this.m - 176, -22016);
/*  768 */     drawLabels(6, this.boxPaneTop, -22016);
/*      */     
/*  770 */     super.a(mouseX, mouseY, f);
/*      */     
/*  772 */     drawTextBoxes();
/*  773 */     drawColourCodeHelper();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawLabels(int left, int top, int foreColour) {
/*  781 */     if (!showOptions()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  786 */     c(this.q, I18n.get("macro.help.line12"), left, top, foreColour);
/*      */     
/*  788 */     if (this.options.getPlaybackType() != MacroPlaybackType.CONDITIONAL) {
/*      */       
/*  790 */       c(this.q, I18n.get("macro.help.global"), left, top + 32, foreColour);
/*  791 */       c(this.q, I18n.get("macro.help.always"), left, top + 64, foreColour);
/*      */       
/*  793 */       if (this.options.getPlaybackType() != MacroPlaybackType.KEYSTATE)
/*      */       {
/*  795 */         c(this.q, I18n.get("macro.help.inhibit"), left, top + 96, foreColour);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void drawColourCodeHelper() {
/*  802 */     if (this.displayColourCodeHelper) {
/*      */       
/*  804 */       int top = this.options.isPlaybackType(MacroPlaybackType.KEYSTATE) ? 60 : 20;
/*      */       
/*  806 */       if (this.txtKeyHeldMacro.m())
/*      */       {
/*  808 */         top = 40;
/*      */       }
/*      */       
/*  811 */       if (this.txtKeyUpMacro.m())
/*      */       {
/*  813 */         top = 20;
/*      */       }
/*      */       
/*  816 */       this.colourCodeHelper.setYPosition(this.m - top);
/*  817 */       this.colourCodeHelper.drawColourCodeSelector(this.j);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void drawPrompt() {
/*  823 */     c(this.q, I18n.get("macro.prompt.bind", new Object[] { getBindingName() }), 6, this.textBoxPosition - 12, 15658496);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void drawTextBoxes() {
/*  828 */     if (this.options.isPlaybackType(MacroPlaybackType.ONESHOT)) {
/*      */       
/*  830 */       drawOneShotInterface(this.textBoxPosition);
/*      */     }
/*  832 */     else if (this.options.isPlaybackType(MacroPlaybackType.KEYSTATE)) {
/*      */       
/*  834 */       drawKeyStateInterface(this.textBoxPosition);
/*      */     }
/*  836 */     else if (this.options.isPlaybackType(MacroPlaybackType.CONDITIONAL)) {
/*      */       
/*  838 */       drawConditionalInterface(this.textBoxPosition);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawOneShotInterface(int textBoxPosition) {
/*  848 */     this.btnTabNormal.active = true;
/*  849 */     this.btnTabKeystate.active = false;
/*  850 */     this.btnTabConditional.active = false;
/*      */     
/*  852 */     this.txtKeyDownMacro.b(true);
/*  853 */     this.txtKeyHeldMacro.b(false);
/*  854 */     this.txtRepeatDelay.b(false);
/*  855 */     this.txtKeyUpMacro.b(false);
/*      */ 
/*      */     
/*  858 */     this.txtKeyDownMacro.drawTextBox(6, textBoxPosition, this.l - 96, 16, this.highlighter);
/*  859 */     setHelperPosition(this.txtKeyDownMacro.getCursorLocation());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawKeyStateInterface(int textBoxPosition) {
/*  869 */     this.btnTabNormal.active = false;
/*  870 */     this.btnTabKeystate.active = true;
/*  871 */     this.btnTabConditional.active = false;
/*      */     
/*  873 */     int labelWidth = this.q.a(I18n.get("macro.prompt.repeatrate"));
/*      */ 
/*      */     
/*  876 */     c(this.q, I18n.get("macro.prompt.keydown"), 6, textBoxPosition + 4, 15658496);
/*  877 */     this.txtKeyDownMacro.drawTextBox(66, textBoxPosition, this.l - 156, 16, this.highlighter);
/*      */ 
/*      */     
/*  880 */     c(this.q, I18n.get("macro.prompt.keyheld"), 6, textBoxPosition + 24, 15658496);
/*  881 */     this.txtKeyHeldMacro.drawTextBox(66, textBoxPosition + 20, this.l - 226 - labelWidth, 16, this.highlighter);
/*      */ 
/*      */     
/*  884 */     c(this.q, I18n.get("macro.prompt.repeatrate"), this.l - 153 - labelWidth, textBoxPosition + 24, 15658496);
/*  885 */     this.txtRepeatDelay.drawTextBox(this.l - 150, textBoxPosition + 20, 60, 16, this.highlighter);
/*      */ 
/*      */     
/*  888 */     c(this.q, I18n.get("macro.prompt.keyup"), 6, textBoxPosition + 44, 15658496);
/*  889 */     this.txtKeyUpMacro.drawTextBox(66, textBoxPosition + 40, this.l - 156, 16, this.highlighter);
/*      */     
/*  891 */     updateHelperPosition(new GuiTextFieldEx[] { this.txtKeyDownMacro, this.txtKeyHeldMacro, this.txtKeyUpMacro });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawConditionalInterface(int yPos) {
/*  900 */     this.btnTabNormal.active = false;
/*  901 */     this.btnTabKeystate.active = false;
/*  902 */     this.btnTabConditional.active = true;
/*      */     
/*  904 */     int textColour = -1118720;
/*  905 */     int enabledLineColour = -22016;
/*  906 */     int otherLineColour = -22016;
/*      */     
/*  908 */     if (this.displayConditionResult) {
/*      */       
/*  910 */       enabledLineColour = this.conditionResult ? -16716288 : -1118720;
/*  911 */       otherLineColour = -8355712;
/*      */       
/*  913 */       int offset = this.conditionResult ? 0 : 20;
/*  914 */       a(84, yPos + offset + 28, this.l - 198 + 86, yPos + offset + 48, enabledLineColour);
/*      */     } 
/*      */ 
/*      */     
/*  918 */     c(this.q, I18n.get("macro.prompt.condition"), 6, yPos + 4, 15658496);
/*  919 */     this.txtCondition.drawTextBox(86, yPos, this.l - 276, 16, this.highlighter);
/*      */ 
/*      */     
/*  922 */     c(this.q, I18n.get("macro.prompt.condtrue"), 36, yPos + 34, this.displayConditionResult ? (this.conditionResult ? enabledLineColour : otherLineColour) : textColour);
/*      */     
/*  924 */     this.txtKeyDownMacro.drawTextBox(86, yPos + 30, this.l - 200, 16, this.highlighter);
/*      */ 
/*      */     
/*  927 */     c(this.q, I18n.get("macro.prompt.condfalse"), 36, yPos + 54, this.displayConditionResult ? (this.conditionResult ? otherLineColour : enabledLineColour) : textColour);
/*      */     
/*  929 */     this.txtKeyUpMacro.drawTextBox(86, yPos + 50, this.l - 200, 16, this.highlighter);
/*      */ 
/*      */     
/*  932 */     this.renderer.drawLine(this.l - 186, yPos + 8, this.l - 166, yPos + 8, 1.0F, enabledLineColour);
/*  933 */     this.renderer.drawLine(this.l - 166, yPos + 8, this.l - 166, yPos + 23, 1.0F, enabledLineColour);
/*  934 */     this.renderer.drawLine(15, yPos + 23, this.l - 166, yPos + 23, 1.0F, enabledLineColour);
/*  935 */     this.renderer.drawArrow(15, yPos + 38, 32, yPos + 38, 0, 1.0F, 5, this.conditionResult ? enabledLineColour : otherLineColour);
/*  936 */     this.renderer.drawLine(15, yPos + 38, 15, yPos + 58, 1.0F, this.conditionResult ? otherLineColour : enabledLineColour);
/*  937 */     this.renderer.drawLine(15, yPos + 23, 15, yPos + 38, 1.0F, enabledLineColour);
/*  938 */     this.renderer.drawArrow(15, yPos + 58, 32, yPos + 58, 0, 1.0F, 5, this.conditionResult ? otherLineColour : enabledLineColour);
/*      */     
/*  940 */     updateHelperPosition(new GuiTextFieldEx[] { this.txtCondition, this.txtKeyDownMacro, this.txtKeyUpMacro });
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateHelperPosition(GuiTextFieldEx... textFields) {
/*  945 */     int helperPosition = 0;
/*      */     
/*  947 */     for (GuiTextFieldEx textField : textFields) {
/*      */       
/*  949 */       if (textField.m()) {
/*      */         
/*  951 */         helperPosition = textField.getCursorLocation();
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  956 */     setHelperPosition(helperPosition);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean evaluateCondition(String condition) {
/*  961 */     IScriptActionProvider scriptActionProvider = ScriptContext.MAIN.getScriptActionProvider();
/*  962 */     String expandedCondition = (new VariableExpander(scriptActionProvider, null, condition, true)).toString();
/*  963 */     IExpressionEvaluator evaluator = scriptActionProvider.getExpressionEvaluator(null, expandedCondition);
/*  964 */     boolean result = (evaluator.evaluate() != 0);
/*  965 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawBackground(int backColour, int backColour2) {
/*  975 */     this.drawHeader = true;
/*  976 */     this.title = I18n.get("macro.edit.title");
/*  977 */     this.banner = I18n.get("macro.currentconfig", new Object[] { this.options.isGlobal ? ("§e" + 
/*  978 */           I18n.get("macro.config.global")) : this.macros
/*  979 */           .getActiveConfigName() });
/*      */ 
/*      */     
/*  982 */     a(this.l - 84, 44, this.l - 2, 61, backColour2);
/*  983 */     c(this.q, I18n.get("macro.action.title"), this.l - 80, 49, 16776960);
/*  984 */     a(this.l - 84, 61, this.l - 2, this.m - 2, backColour);
/*      */ 
/*      */     
/*  987 */     a(2, 44, this.l - 86, 61, backColour2);
/*  988 */     c(this.q, I18n.get(this.showOptions ? "macro.option.title" : "macro.help.title"), 6, 49, 16776960);
/*  989 */     a(2, 61, this.l - 86, this.textBoxPosition - 18, backColour);
/*      */     
/*  991 */     this.btnMode.j = I18n.get(this.showOptions ? "macro.help.title" : "macro.option.title");
/*  992 */     this.btnMode.setIconIndex(this.showOptions ? 6 : 4);
/*  993 */     this.btnTabNormal.setYPosition(22);
/*  994 */     this.btnTabKeystate.setYPosition(22);
/*  995 */     this.btnTabConditional.setYPosition(22);
/*      */ 
/*      */     
/*  998 */     a(2, this.textBoxPosition - 16, this.l - 86, this.m - 2, backColour);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getBindingName() {
/* 1008 */     String modifiers = "";
/*      */     
/* 1010 */     if (this.options.isType(MacroTriggerType.KEY)) {
/*      */       
/* 1012 */       modifiers = this.options.control ? "<CTRL> " : "";
/* 1013 */       modifiers = modifiers + (this.options.alt ? "<ALT> " : "");
/* 1014 */       modifiers = modifiers + (this.options.shift ? "<SHIFT> " : "");
/*      */     } 
/*      */     
/* 1017 */     return modifiers + "<" + this.options.getName(this.key) + ">";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawHelp(int left, int top, int foreColour) {
/* 1026 */     if (showOptions()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1031 */     this.rowPos = top;
/* 1032 */     String prefix = "macro.help.", prefix2 = prefix;
/* 1033 */     int start = 1, end = 11;
/*      */     
/* 1035 */     int eventId = this.template.getID();
/*      */     
/* 1037 */     if (this.options.isPlaybackType(MacroPlaybackType.ONESHOT)) {
/*      */       
/* 1039 */       drawSpacedString(I18n.get("macro.help.line1"), left, foreColour);
/* 1040 */       drawSpacedString(I18n.get("macro.help.line2"), left, foreColour);
/*      */       
/* 1042 */       if (this.options.isType(MacroTriggerType.EVENT)) {
/*      */         
/* 1044 */         prefix = prefix + "event.";
/* 1045 */         prefix2 = prefix2 + "event." + eventId + ".";
/* 1046 */         end = 5;
/*      */       } 
/*      */       
/* 1049 */       start = 3;
/*      */     }
/* 1051 */     else if (this.options.isPlaybackType(MacroPlaybackType.KEYSTATE)) {
/*      */       
/* 1053 */       prefix2 = prefix = "macro.help2.";
/* 1054 */       end = 5;
/*      */     }
/* 1056 */     else if (this.options.isPlaybackType(MacroPlaybackType.CONDITIONAL)) {
/*      */       
/* 1058 */       prefix2 = prefix = "macro.help3.";
/* 1059 */       end = 7;
/*      */     } 
/*      */     
/* 1062 */     for (int line = start; line <= end; line++)
/*      */     {
/* 1064 */       drawSpacedString(I18n.get(((line > 5) ? prefix2 : prefix) + "line" + line), left, foreColour);
/*      */     }
/*      */     
/* 1067 */     if (this.options.isType(MacroTriggerType.EVENT) && this.options.isPlaybackType(MacroPlaybackType.ONESHOT)) {
/*      */       
/* 1069 */       IMacroEvent event = this.macros.getEventManager().getEvent(eventId);
/* 1070 */       if (event != null) {
/*      */         
/* 1072 */         List<String> help = event.getHelp();
/* 1073 */         for (int i = 0; i < 6 && i < help.size(); i++)
/*      */         {
/* 1075 */           drawSpacedString(help.get(i), left, foreColour);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mouseClickedEx(int mouseX, int mouseY, int button) {
/* 1087 */     if (button == 0) {
/*      */       
/* 1089 */       if (this.displayColourCodeHelper && this.colourCodeHelper.mouseClicked(mouseX, mouseY) && this.colourCodeHelper.hasColour()) {
/*      */         
/* 1091 */         this.displayColourCodeHelper = false;
/*      */         
/* 1093 */         String appendCode = String.format(this.settings.colourCodeFormat, new Object[] { Character.valueOf(this.colourCodeHelper.getColourCode()) });
/*      */         
/* 1095 */         this.txtKeyDownMacro.insertText(appendCode);
/* 1096 */         this.txtKeyHeldMacro.insertText(appendCode);
/* 1097 */         this.txtKeyUpMacro.insertText(appendCode);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1102 */       this.txtKeyDownMacro.a(mouseX, mouseY, button);
/*      */       
/* 1104 */       if (this.options.isPlaybackType(MacroPlaybackType.KEYSTATE)) {
/*      */         
/* 1106 */         this.txtKeyHeldMacro.a(mouseX, mouseY, button);
/* 1107 */         this.txtRepeatDelay.a(mouseX, mouseY, button);
/* 1108 */         this.txtKeyUpMacro.a(mouseX, mouseY, button);
/*      */       }
/* 1110 */       else if (this.options.isPlaybackType(MacroPlaybackType.CONDITIONAL)) {
/*      */         
/* 1112 */         this.txtKeyUpMacro.a(mouseX, mouseY, button);
/* 1113 */         this.txtCondition.a(mouseX, mouseY, button);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected GuiTextFieldEx getFocusedTextField() {
/* 1120 */     if (this.txtKeyDownMacro.m()) return this.txtKeyDownMacro; 
/* 1121 */     if (this.txtKeyHeldMacro.m()) return this.txtKeyHeldMacro; 
/* 1122 */     if (this.txtKeyUpMacro.m()) return this.txtKeyUpMacro; 
/* 1123 */     if (this.txtCondition.m()) return this.txtCondition; 
/* 1124 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void openTextEditor(GuiTextFieldEx guiTextFieldMacroEdit) {
/* 1129 */     this.j.a((blk)new GuiEditTextString(this.macros, this.j, this, guiTextFieldMacroEdit.b(), this.options
/* 1130 */           .getName(this.key), ScriptContext.MAIN));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onHeaderClick() {
/* 1136 */     this.options.isGlobal = !this.options.isGlobal;
/*      */   }
/*      */ 
/*      */   
/*      */   public static GuiMacroEdit create(Macros macros, bib minecraft, blk parentScreen, int key) {
/* 1141 */     if ((macros.getSettings()).simpleGui)
/*      */     {
/* 1143 */       return new GuiMacroEditSimple(macros, minecraft, parentScreen, key);
/*      */     }
/*      */     
/* 1146 */     return new GuiMacroEdit(macros, minecraft, parentScreen, key);
/*      */   }
/*      */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiMacroEdit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */