/*      */ package net.eq2online.macros.input;
/*      */ import bhy;
/*      */ import bib;
/*      */ import blk;
/*      */ import com.mumfrey.liteloader.util.Input;
/*      */ import java.nio.BufferOverflowException;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.ArrayList;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.compatibility.I18n;
/*      */ import net.eq2online.macros.compatibility.Reflection;
/*      */ import net.eq2online.macros.core.MacroModCore;
/*      */ import net.eq2online.macros.core.Macros;
/*      */ import net.eq2online.macros.gui.screens.GuiMacroPlayback;
/*      */ import net.eq2online.macros.interfaces.ISettingsStore;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ 
/*      */ public class InputHandler implements ISettingsObserver {
/*      */   public static final int ID_VIRTUAL_MWHEELUP = 63;
/*      */   public static final int ID_VIRTUAL_MWHEELDOWN = 64;
/*      */   public static final int ID_MWHEELUP = 248;
/*      */   public static final int ID_MWHEELDOWN = 249;
/*      */   public static final int ID_LMOUSE = 250;
/*      */   public static final int ID_RMOUSE = 251;
/*      */   public static final int ID_MIDDLEMOUSE = 252;
/*      */   public static final int ID_MOUSE3 = 253;
/*      */   public static final int ID_MOUSE4 = 254;
/*      */   public static final int ID_MOUSE5 = 240;
/*      */   public static final int ID_MOUSE6 = 241;
/*      */   public static final int ID_MOUSE7 = 242;
/*      */   public static final int ID_MOUSE8 = 243;
/*      */   public static final int ID_MOUSE9 = 244;
/*      */   public static final int ID_MOUSE10 = 245;
/*      */   public static final String NAME_MWHEELUP = "MWHEELUP";
/*      */   public static final String NAME_MWHEELDOWN = "MWHEELDOWN";
/*      */   public static final String NAME_LMOUSE = "LMOUSE";
/*      */   public static final String NAME_RMOUSE = "RMOUSE";
/*      */   public static final String NAME_MIDDLEMOUSE = "MIDDLEMOUSE";
/*      */   public static final String NAME_MOUSE3 = "MOUSE3";
/*      */   public static final String NAME_MOUSE4 = "MOUSE4";
/*      */   public static final String NAME_MOUSE5 = "MOUSE5";
/*      */   public static final String NAME_MOUSE6 = "MOUSE6";
/*      */   public static final String NAME_MOUSE7 = "MOUSE7";
/*      */   public static final String NAME_MOUSE8 = "MOUSE8";
/*      */   public static final String NAME_MOUSE9 = "MOUSE9";
/*      */   public static final String NAME_MOUSE10 = "MOUSE10";
/*      */   private static final int FALLBACK_FAILURE_LIMIT = 1000;
/*      */   
/*      */   public enum BindingComboMode {
/*   51 */     NORMAL,
/*      */     
/*   53 */     DIRECT,
/*      */     
/*   55 */     DISABLED;
/*      */ 
/*      */     
/*      */     public String getDescription() {
/*   59 */       if (this == DISABLED)
/*      */       {
/*   61 */         return I18n.get("options.option.mode.disabled");
/*      */       }
/*      */       
/*   64 */       String activateText = bid.a(InputHandler.KEY_ACTIVATE.j());
/*   65 */       if (this == DIRECT)
/*      */       {
/*   67 */         return I18n.get("options.option.mode.direct", new Object[] { activateText });
/*      */       }
/*      */       
/*   70 */       String sneakText = bid.a(InputHandler.KEY_SNEAK.j());
/*   71 */       return I18n.get("options.option.mode.normal", new Object[] { sneakText, activateText });
/*      */     }
/*      */ 
/*      */     
/*      */     public BindingComboMode getNextMode() {
/*   76 */       if (this == DIRECT) return DISABLED; 
/*   77 */       if (this == DISABLED) return NORMAL; 
/*   78 */       return DIRECT;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class InputEvent
/*      */   {
/*      */     int key;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     char character;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean state;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean overrideKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean modifierKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean deep;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     InputEvent(int key, char character, boolean state, boolean overrideKeyDown, boolean modifierKeyDown) {
/*  121 */       this.key = key;
/*  122 */       this.character = character;
/*  123 */       this.state = state;
/*  124 */       this.overrideKey = overrideKeyDown;
/*  125 */       this.modifierKey = modifierKeyDown;
/*  126 */       this.deep = false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     InputEvent(char character, boolean state, boolean deep) {
/*  137 */       this.key = CharMap.getKeyCode(character);
/*  138 */       this.character = character;
/*  139 */       this.state = state;
/*  140 */       this.deep = deep;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     InputEvent(int key, boolean state, boolean deep) {
/*  151 */       this.key = key;
/*  152 */       this.character = CharMap.getKeyChar(key);
/*  153 */       this.state = state;
/*  154 */       this.deep = deep;
/*      */     }
/*      */ 
/*      */     
/*      */     void write(InputHandler.BufferProcessor processor) {
/*  159 */       processor.writeKeyboardEvent(this.key, this.state, this.character);
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
/*      */   static final class BufferProcessor
/*      */   {
/*      */     private static final int BUFFER_SIZE = 50;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  180 */     private ByteBuffer keyboardBuffer = ByteBuffer.allocate(900);
/*  181 */     private ByteBuffer mouseBuffer = ByteBuffer.allocate(1100);
/*      */     private boolean updateKeyboardBuffer;
/*      */     private boolean updateMouseBuffer;
/*      */     
/*      */     void reset() {
/*  186 */       this.updateKeyboardBuffer = false;
/*  187 */       this.updateMouseBuffer = false;
/*      */       
/*  189 */       this.keyboardBuffer.clear();
/*  190 */       this.mouseBuffer.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void processBuffers(ByteBuffer keyReadBuffer, ByteBuffer mouseReadBuffer) {
/*  196 */       if (this.updateMouseBuffer) {
/*      */         
/*  198 */         this.updateMouseBuffer = false;
/*  199 */         this.mouseBuffer.flip();
/*  200 */         mouseReadBuffer.clear();
/*  201 */         mouseReadBuffer.put(this.mouseBuffer);
/*  202 */         mouseReadBuffer.flip();
/*      */       } 
/*      */ 
/*      */       
/*  206 */       if (this.updateKeyboardBuffer) {
/*      */         
/*  208 */         this.updateKeyboardBuffer = false;
/*  209 */         this.keyboardBuffer.flip();
/*  210 */         keyReadBuffer.clear();
/*  211 */         keyReadBuffer.put(this.keyboardBuffer);
/*  212 */         keyReadBuffer.flip();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void onConsumeMouseEvent() {
/*  218 */       this.updateMouseBuffer = true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean writeMouseEvent(byte button, boolean state, int mouseX, int mouseY, int dWheel, long nanos) {
/*      */       try {
/*  225 */         this.updateMouseBuffer = true;
/*  226 */         this.mouseBuffer.put(button);
/*  227 */         this.mouseBuffer.put((byte)(state ? 1 : 0));
/*  228 */         this.mouseBuffer.putInt(mouseX);
/*  229 */         this.mouseBuffer.putInt(mouseY);
/*  230 */         this.mouseBuffer.putInt(dWheel);
/*  231 */         this.mouseBuffer.putLong(nanos);
/*      */       }
/*  233 */       catch (BufferOverflowException ex) {
/*      */         
/*  235 */         System.err.printf("Mouse Event Buffer overflow in InputHandler.BufferProcessor%n", new Object[0]);
/*  236 */         return false;
/*      */       } 
/*      */       
/*  239 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void onConsumeKeyboardEvent() {
/*  244 */       this.updateKeyboardBuffer = true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean writeKeyboardEvent(int id, boolean state, int character) {
/*  249 */       return writeKeyboardEvent(id, state, character, 1L, false);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean writeKeyboardEvent(int id, boolean state, int character, long nanos, boolean repeat) {
/*      */       try {
/*  256 */         this.updateKeyboardBuffer = true;
/*  257 */         this.keyboardBuffer.putInt(id);
/*  258 */         this.keyboardBuffer.put((byte)(state ? 1 : 0));
/*  259 */         this.keyboardBuffer.putInt(character);
/*  260 */         this.keyboardBuffer.putLong(nanos);
/*  261 */         this.keyboardBuffer.put((byte)(repeat ? 1 : 0));
/*      */       }
/*  263 */       catch (BufferOverflowException ex) {
/*      */         
/*  265 */         System.err.printf("Keyboard Event Buffer overflow in InputHandler.BufferProcessor%n", new Object[0]);
/*  266 */         return false;
/*      */       } 
/*      */       
/*  269 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class MouseEvent
/*      */   {
/*      */     byte button;
/*      */     
/*      */     int id;
/*      */     boolean state;
/*      */     int mouseX;
/*      */     int mouseY;
/*      */     int dWheel;
/*      */     long nanos;
/*      */     boolean damping;
/*      */     boolean consumeEvent;
/*      */     
/*      */     void read(ByteBuffer readBuffer) {
/*  288 */       this.damping = false;
/*  289 */       this.consumeEvent = false;
/*      */       
/*  291 */       this.button = readBuffer.get();
/*  292 */       this.id = this.button + ((this.button > 4) ? 235 : 250);
/*  293 */       this.state = (readBuffer.get() != 0);
/*  294 */       this.mouseX = readBuffer.getInt();
/*  295 */       this.mouseY = readBuffer.getInt();
/*  296 */       this.dWheel = readBuffer.getInt();
/*  297 */       this.nanos = readBuffer.getLong();
/*      */     }
/*      */ 
/*      */     
/*      */     void write(InputHandler.BufferProcessor processor) {
/*  302 */       processor.writeMouseEvent(this.button, this.state, this.mouseX, this.mouseY, this.dWheel, this.nanos);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static final class KeyboardEvent
/*      */   {
/*      */     boolean consumeEvent;
/*      */     int id;
/*      */     boolean state;
/*      */     int character;
/*      */     long nanos;
/*      */     boolean repeat;
/*      */     
/*      */     void read(ByteBuffer readBuffer) {
/*  317 */       this.consumeEvent = false;
/*      */       
/*  319 */       this.id = readBuffer.getInt() & 0xFF;
/*  320 */       this.state = (readBuffer.get() != 0);
/*  321 */       this.character = readBuffer.getInt();
/*  322 */       this.nanos = readBuffer.getLong();
/*  323 */       this.repeat = (readBuffer.get() == 1);
/*      */     }
/*      */ 
/*      */     
/*      */     void write(InputHandler.BufferProcessor processor) {
/*  328 */       processor.writeKeyboardEvent(this.id, this.state, this.character, this.nanos, this.repeat);
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
/*  374 */   public static final bhy KEY_OVERRIDE = new bhy("key.macro_override", 0, "key.categories.macros");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  379 */   public static final bhy KEY_ACTIVATE = new bhy("key.macros", 41, "key.categories.macros");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  384 */   public static final bhy KEY_REPL = new bhy("key.macro_repl", 0, "key.categories.macros");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  389 */   protected static bhy KEY_SNEAK = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  394 */   private static int fallbackFailureCount = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   private final bib mc;
/*      */ 
/*      */ 
/*      */   
/*      */   private final Macros macros;
/*      */ 
/*      */ 
/*      */   
/*      */   private final Settings settings;
/*      */ 
/*      */ 
/*      */   
/*      */   private final MacroModCore core;
/*      */ 
/*      */   
/*  413 */   private int activateKeyCode = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  418 */   private int overrideKeyCode = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  423 */   private int replKeyCode = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  428 */   private int sneakKeyCode = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean overrideKey = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean modifierKey = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fallbackMode = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean tickUpdated = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  454 */   private boolean[] pressedKeys = new boolean[256];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  460 */   private final List<InputEvent> keyEventQueue = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  465 */   private final List<InputEvent> pendingKeyEvents = new ArrayList<>();
/*      */   
/*  467 */   private final BufferProcessor bufferProcessor = new BufferProcessor();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  472 */   private static ByteBuffer keyDownOverrideBuffer = BufferUtils.createByteBuffer(256);
/*      */   
/*  474 */   private List<IInputHandlerModule> inputHandlerModules = new ArrayList<>();
/*      */   
/*  476 */   private int mWheelUpDamping = 0;
/*  477 */   private int mWheelDownDamping = 0;
/*      */   
/*  479 */   private InputEvent nextEvent = null;
/*      */ 
/*      */   
/*      */   private boolean nextEventProcessed = false;
/*      */ 
/*      */   
/*      */   private ByteBuffer mouseReadBuffer;
/*      */   
/*      */   private ByteBuffer keyReadBuffer;
/*      */   
/*      */   private ByteBuffer mouseDownBuffer;
/*      */   
/*      */   private ByteBuffer keyDownBuffer;
/*      */   
/*      */   private boolean deep;
/*      */ 
/*      */   
/*      */   public InputHandler(MacroModCore core, Macros macros, bib minecraft) {
/*  497 */     this.core = core;
/*  498 */     this.macros = macros;
/*  499 */     this.mc = minecraft;
/*  500 */     this.settings = macros.getSettings();
/*      */ 
/*      */     
/*  503 */     this.macros.getSettingsHandler().registerObserver((IObserver)this);
/*      */     
/*  505 */     update();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOverrideKeyDown() {
/*  510 */     return this.overrideKey;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFallbackMode() {
/*  515 */     return this.fallbackMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getOverrideKeyCode() {
/*  520 */     return this.overrideKeyCode;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getReplKeyCode() {
/*  525 */     return this.replKeyCode;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSneakKeyCode() {
/*  530 */     return this.sneakKeyCode;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getKeyDebugInfo() {
/*  535 */     StringBuilder debugInfo = new StringBuilder();
/*  536 */     if (this.overrideKey)
/*      */     {
/*  538 */       debugInfo.append("[OVERRIDE] ");
/*      */     }
/*  540 */     if (isTriggerActive(this.activateKeyCode))
/*      */     {
/*  542 */       debugInfo.append("[MACRO] ");
/*      */     }
/*  544 */     if (isTriggerActive(this.sneakKeyCode))
/*      */     {
/*  546 */       debugInfo.append("[SNEAK] ");
/*      */     }
/*  548 */     return debugInfo.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void init(Input input) {
/*  554 */     input.registerKeyBinding(KEY_ACTIVATE);
/*  555 */     input.registerKeyBinding(KEY_OVERRIDE);
/*  556 */     input.registerKeyBinding(KEY_REPL);
/*      */     
/*  558 */     if (!this.settings.enableOverride) {
/*      */       
/*  560 */       KEY_OVERRIDE.b(0);
/*  561 */       bhy.c();
/*      */     } 
/*      */ 
/*      */     
/*  565 */     KEY_SNEAK = this.mc.t.Y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update() {
/*      */     try {
/*  576 */       this.mouseReadBuffer = (ByteBuffer)Reflection.getPrivateValue(Mouse.class, null, "readBuffer");
/*  577 */       this.mouseDownBuffer = (ByteBuffer)Reflection.getPrivateValue(Mouse.class, null, "buttons");
/*  578 */       this.keyReadBuffer = (ByteBuffer)Reflection.getPrivateValue(Keyboard.class, null, "readBuffer");
/*  579 */       this.keyDownBuffer = (ByteBuffer)Reflection.getPrivateValue(Keyboard.class, null, "keyDownBuffer");
/*      */     }
/*  581 */     catch (Exception ex) {
/*      */       
/*  583 */       ex.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addModule(IInputHandlerModule module) {
/*  593 */     if (module != null && !this.inputHandlerModules.contains(module)) {
/*      */       
/*  595 */       this.macros.getSettingsHandler().registerObserver((IObserver)module);
/*  596 */       this.inputHandlerModules.add(module);
/*  597 */       module.initialise(this, (ISettingsStore)this.macros);
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
/*      */   public boolean register() {
/*  611 */     if (this.settings.compatibilityMode)
/*      */     {
/*  613 */       this.fallbackMode = true;
/*      */     }
/*      */     
/*  616 */     if (!this.fallbackMode) {
/*      */       
/*      */       try {
/*      */         
/*  620 */         for (IInputHandlerModule module : this.inputHandlerModules)
/*      */         {
/*  622 */           module.register(this.mc, (ISettingsStore)this.macros);
/*      */         }
/*      */       }
/*  625 */       catch (Exception ex) {
/*      */         
/*  627 */         Log.printStackTrace(ex);
/*  628 */         this.fallbackMode = true;
/*      */       } 
/*      */     }
/*      */     
/*  632 */     return !this.fallbackMode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPendingKeyEventCount() {
/*  642 */     return (this.pendingKeyEvents != null) ? this.pendingKeyEvents.size() : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onClearSettings() {
/*  648 */     CharMap.notifySettingsCleared();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLoadSettings(ISettingsStore settings) {
/*  654 */     CharMap.notifySettingsLoaded(settings);
/*      */ 
/*      */     
/*  657 */     if (this.settings.compatibilityMode)
/*      */     {
/*  659 */       this.fallbackMode = true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onSaveSettings(ISettingsStore settings) {
/*  666 */     CharMap.saveSettings(settings);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pumpKeyChars(String keyChars, boolean deep) {
/*  677 */     for (int c = 0; c < keyChars.length(); c++)
/*      */     {
/*  679 */       pumpKeyChar(keyChars.charAt(c), deep);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pumpKeyChar(char keyChar, boolean deep) {
/*  690 */     this.pendingKeyEvents.add(new InputEvent(keyChar, true, deep));
/*  691 */     this.pendingKeyEvents.add(new InputEvent(keyChar, false, deep));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pumpKeyCode(int keyCode, boolean deep) {
/*  701 */     this.pendingKeyEvents.add(new InputEvent(keyCode, true, deep));
/*  702 */     this.pendingKeyEvents.add(new InputEvent(keyCode, false, deep));
/*      */   }
/*      */ 
/*      */   
/*      */   public void onTimerUpdate() {
/*  707 */     if (this.nextEvent != null && this.nextEventProcessed) {
/*      */       
/*  709 */       this.nextEvent = null;
/*  710 */       this.nextEventProcessed = false;
/*      */     } 
/*      */     
/*  713 */     if (this.nextEvent == null && this.pendingKeyEvents.size() > 0)
/*      */     {
/*  715 */       this.nextEvent = this.pendingKeyEvents.remove(0);
/*      */     }
/*      */     
/*  718 */     processBuffers(true, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void performDeepInjection() {
/*  723 */     if (this.deep) {
/*      */       
/*  725 */       this.deep = false;
/*  726 */       processBuffers(true, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateKeys() {
/*  732 */     this.activateKeyCode = KEY_ACTIVATE.j();
/*  733 */     this.overrideKeyCode = KEY_OVERRIDE.j();
/*  734 */     this.replKeyCode = KEY_REPL.j();
/*  735 */     this.sneakKeyCode = KEY_SNEAK.j();
/*      */     
/*  737 */     if (this.overrideKeyCode == 0)
/*      */     {
/*  739 */       this.overrideKeyCode = this.mc.t.Z.j();
/*      */     }
/*      */     
/*  742 */     this
/*  743 */       .overrideKey = (this.settings.enableOverride && isKeyDown(this.overrideKeyCode) && !(this.mc.m instanceof bme));
/*      */     
/*  745 */     this.modifierKey = isKeyDown(this.sneakKeyCode);
/*      */   }
/*      */ 
/*      */   
/*      */   public void processBuffers(boolean readEvents, boolean allowDeepInjection) {
/*  750 */     updateKeys();
/*      */     
/*  752 */     if (this.fallbackMode || (this.mc.f == null && !(this.mc.m instanceof bme))) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  757 */     this.mc.B.a("inputprocessor");
/*  758 */     this.tickUpdated = true;
/*  759 */     for (IInputHandlerModule module : this.inputHandlerModules)
/*      */     {
/*  761 */       module.update(this.keyEventQueue, this.overrideKey, this.modifierKey);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  766 */       this.bufferProcessor.reset();
/*      */       
/*  768 */       for (IInputHandlerModule module : this.inputHandlerModules)
/*      */       {
/*  770 */         module.injectEvents(this.bufferProcessor, this.keyDownBuffer, this.mouseDownBuffer);
/*      */       }
/*      */       
/*  773 */       if (readEvents) {
/*      */         
/*  775 */         boolean canOverride = isScreenOverridable(this.mc.m);
/*  776 */         readMouseEvents(canOverride);
/*  777 */         readKeyboardEvents(canOverride);
/*  778 */         updateKeyStateBuffer(keyDownOverrideBuffer);
/*      */       } 
/*      */       
/*  781 */       if (this.mc.f != null)
/*      */       {
/*  783 */         injectEvents(allowDeepInjection);
/*      */       }
/*      */       
/*  786 */       this.bufferProcessor.processBuffers(this.keyReadBuffer, this.mouseReadBuffer);
/*      */     }
/*  788 */     catch (Exception ex) {
/*      */       
/*  790 */       fallbackFailureCount++;
/*      */       
/*  792 */       if (fallbackFailureCount > 1000) {
/*      */         
/*  794 */         Log.info("Error in custom input handler: {0}", new Object[] { ex.getMessage() });
/*  795 */         this.fallbackMode = true;
/*      */       } 
/*      */     } 
/*  798 */     this.mc.B.b();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readMouseEvents(boolean canOverride) {
/*  803 */     this.mouseReadBuffer.mark();
/*  804 */     MouseEvent mouseEvent = new MouseEvent();
/*      */ 
/*      */     
/*  807 */     while (this.mouseReadBuffer.hasRemaining()) {
/*      */       
/*  809 */       mouseEvent.read(this.mouseReadBuffer);
/*      */       
/*  811 */       if (mouseEvent.button == -1 && mouseEvent.dWheel != 0)
/*      */       {
/*  813 */         if (mouseEvent.dWheel > 0) {
/*      */           
/*  815 */           mouseEvent.button = 63;
/*  816 */           mouseEvent.id = 248;
/*  817 */           mouseEvent.state = true;
/*  818 */           mouseEvent.damping = (this.mWheelUpDamping != 0);
/*  819 */           this.mWheelUpDamping = 1;
/*      */         }
/*  821 */         else if (mouseEvent.dWheel < 0) {
/*      */           
/*  823 */           mouseEvent.button = 64;
/*  824 */           mouseEvent.id = 249;
/*  825 */           mouseEvent.state = true;
/*  826 */           mouseEvent.damping = (this.mWheelDownDamping != 0);
/*  827 */           this.mWheelDownDamping = 1;
/*      */         } 
/*      */       }
/*      */       
/*  831 */       boolean override = this.macros.isKeyAlwaysOverridden(mouseEvent.id, true, false);
/*  832 */       if (this.overrideKey)
/*      */       {
/*  834 */         override = !override;
/*      */       }
/*      */       
/*  837 */       if (override && canOverride && this.mc.m == null && this.macros.isMacroBound(mouseEvent.id, true)) {
/*      */         
/*  839 */         this.bufferProcessor.onConsumeMouseEvent();
/*      */         
/*  841 */         if (mouseEvent.state)
/*      */         {
/*  843 */           mouseEvent.consumeEvent = true;
/*      */         }
/*      */         
/*  846 */         if (!mouseEvent.damping)
/*      */         {
/*  848 */           this.keyEventQueue.add(new InputEvent(mouseEvent.id, false, mouseEvent.state, true, this.modifierKey));
/*      */         }
/*      */       }
/*  851 */       else if (this.core.getThumbnailHandler().isCapturingThumbnail() && mouseEvent.state) {
/*      */         
/*  853 */         mouseEvent.consumeEvent = true;
/*  854 */         this.bufferProcessor.onConsumeMouseEvent();
/*  855 */         if (!mouseEvent.damping)
/*      */         {
/*  857 */           this.keyEventQueue.add(new InputEvent(mouseEvent.id, false, mouseEvent.state, true, this.modifierKey));
/*      */         }
/*      */       } 
/*      */       
/*  861 */       if (!mouseEvent.consumeEvent) {
/*      */         
/*  863 */         if ((this.mc.m == null || !mouseEvent.state) && (this.macros
/*  864 */           .isMacroBound(mouseEvent.id, true) || mouseEvent.id == this.activateKeyCode || mouseEvent.id == this.overrideKeyCode || mouseEvent.id == this.replKeyCode))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  869 */           if (!mouseEvent.damping)
/*      */           {
/*  871 */             this.keyEventQueue.add(new InputEvent(mouseEvent.id, false, mouseEvent.state, this.overrideKey, this.modifierKey));
/*      */           }
/*      */         }
/*      */         
/*  875 */         mouseEvent.write(this.bufferProcessor);
/*      */       } 
/*      */     } 
/*      */     
/*  879 */     this.mouseReadBuffer.reset();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readKeyboardEvents(boolean canOverride) {
/*  884 */     this.keyReadBuffer.mark();
/*  885 */     KeyboardEvent keyEvent = new KeyboardEvent();
/*      */ 
/*      */     
/*  888 */     while (this.keyReadBuffer.hasRemaining()) {
/*      */       
/*  890 */       keyEvent.read(this.keyReadBuffer);
/*      */       
/*  892 */       boolean override = this.macros.isKeyAlwaysOverridden(keyEvent.id, true, false);
/*  893 */       if (this.overrideKey)
/*      */       {
/*  895 */         override = !override;
/*      */       }
/*      */       
/*  898 */       if (!keyEvent.state)
/*      */       {
/*  900 */         keyDownOverrideBuffer.put(keyEvent.id, (byte)0);
/*      */       }
/*      */       
/*  903 */       if (override && canOverride && this.macros.isMacroBound(keyEvent.id, true)) {
/*      */         
/*  905 */         this.bufferProcessor.onConsumeKeyboardEvent();
/*      */         
/*  907 */         if (keyEvent.state) {
/*      */           
/*  909 */           keyDownOverrideBuffer.put(keyEvent.id, (byte)1);
/*  910 */           keyEvent.consumeEvent = true;
/*      */         } 
/*      */         
/*  913 */         if (!keyEvent.repeat)
/*      */         {
/*  915 */           this.keyEventQueue.add(new InputEvent(keyEvent.id, (char)keyEvent.character, keyEvent.state, true, this.modifierKey));
/*      */         }
/*      */       }
/*  918 */       else if (this.core.getThumbnailHandler().isCapturingThumbnail() && keyEvent.state && (keyEvent.id == 28 || keyEvent.id == 156 || keyEvent.id == 1)) {
/*      */ 
/*      */ 
/*      */         
/*  922 */         keyEvent.consumeEvent = true;
/*  923 */         this.bufferProcessor.onConsumeKeyboardEvent();
/*  924 */         this.keyEventQueue.add(new InputEvent(keyEvent.id, (char)keyEvent.character, keyEvent.state, true, this.modifierKey));
/*      */       } 
/*      */       
/*  927 */       if (!keyEvent.consumeEvent) {
/*      */         
/*  929 */         if ((this.mc.m == null || !keyEvent.state) && !keyEvent.repeat && (this.macros
/*      */           
/*  931 */           .isMacroBound(keyEvent.id, true) || keyEvent.id == this.activateKeyCode || keyEvent.id == this.overrideKeyCode || keyEvent.id == this.replKeyCode))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  936 */           this.keyEventQueue.add(new InputEvent(keyEvent.id, (char)keyEvent.character, keyEvent.state, this.overrideKey, this.modifierKey));
/*      */         }
/*      */         
/*  939 */         keyEvent.write(this.bufferProcessor);
/*      */       } 
/*      */     } 
/*      */     
/*  943 */     this.keyReadBuffer.reset();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateKeyStateBuffer(ByteBuffer copyBuffer) {
/*  950 */     for (int k = 0; k < this.keyDownBuffer.capacity(); k++) {
/*      */       
/*  952 */       if (copyBuffer.get(k) > 0 && this.keyDownBuffer.get(k) != 0)
/*      */       {
/*  954 */         this.keyDownBuffer.put(k, (byte)0);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean injectEvents(boolean allowDeepInjection) {
/*  962 */     if (this.nextEvent == null)
/*      */     {
/*  964 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  970 */       if (!this.nextEventProcessed)
/*      */       {
/*  972 */         this.nextEvent.write(this.bufferProcessor);
/*      */       }
/*      */       
/*  975 */       this.nextEventProcessed = true;
/*      */ 
/*      */       
/*  978 */       this.keyDownBuffer.put(this.nextEvent.key, (byte)1);
/*      */       
/*  980 */       if (!this.settings.disableDeepInjection && this.nextEvent.deep && allowDeepInjection)
/*      */       {
/*  982 */         this.deep = true;
/*      */       }
/*      */     }
/*  985 */     catch (BufferOverflowException bufferOverflowException) {}
/*      */     
/*  987 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTick() {
/*  995 */     if (this.mWheelUpDamping > 0)
/*      */     {
/*  997 */       this.mWheelUpDamping--;
/*      */     }
/*      */     
/* 1000 */     if (this.mWheelDownDamping > 0)
/*      */     {
/* 1002 */       this.mWheelDownDamping--;
/*      */     }
/*      */     
/* 1005 */     if (!this.tickUpdated && !this.fallbackMode) {
/*      */       
/* 1007 */       Log.info("Enhanced key capture failed, enabling fallback mode");
/* 1008 */       this.fallbackMode = true;
/*      */     } 
/*      */     
/* 1011 */     this.tickUpdated = false;
/*      */     
/* 1013 */     if (!this.fallbackMode) {
/*      */       
/* 1015 */       for (IInputHandlerModule module : this.inputHandlerModules) {
/*      */         
/* 1017 */         if (!module.onTick(this.keyEventQueue))
/*      */           return; 
/*      */       } 
/* 1020 */       while (this.keyEventQueue.size() > 0)
/*      */       {
/* 1022 */         InputEvent event = this.keyEventQueue.remove(0);
/*      */         
/* 1024 */         if (event.state) {
/*      */           
/* 1026 */           handleKey(event);
/*      */           
/* 1028 */           if (event.key == 248 || event.key == 249) {
/*      */             
/* 1030 */             event.state = false;
/* 1031 */             this.keyEventQueue.add(event);
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/* 1036 */         this.pressedKeys[event.key] = false;
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1042 */       int key = Keyboard.getEventKey();
/*      */       
/* 1044 */       this.overrideKey = (this.settings.enableOverride && isKeyDown(this.overrideKeyCode));
/* 1045 */       this.modifierKey = isKeyDown(this.sneakKeyCode);
/*      */       
/* 1047 */       if (Keyboard.getEventKeyState()) {
/*      */ 
/*      */         
/* 1050 */         if (this.mc.m == null)
/*      */         {
/* 1052 */           handleKey(key, this.overrideKey, this.modifierKey);
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1057 */         this.pressedKeys[key] = false;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleKey(InputEvent event) {
/* 1069 */     handleKey(event.key, event.overrideKey, event.modifierKey);
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
/*      */   private void handleKey(int key, boolean overrideKeyDown, boolean modifierKeyDown) {
/* 1081 */     if (key == this.activateKeyCode && !overrideKeyDown && !this.pressedKeys[this.activateKeyCode]) {
/*      */       
/* 1083 */       if (this.settings.bindingMode != BindingComboMode.DISABLED)
/*      */       {
/* 1085 */         if (this.settings.bindingMode == BindingComboMode.DIRECT)
/*      */         {
/* 1087 */           modifierKeyDown = !modifierKeyDown;
/*      */         }
/*      */ 
/*      */         
/* 1091 */         if (modifierKeyDown)
/*      */         {
/* 1093 */           this.mc.a((blk)new GuiMacroBind(this.macros, this.mc, true, true));
/*      */ 
/*      */         
/*      */         }
/* 1097 */         else if ((this.replKeyCode == 0 || this.replKeyCode == this.activateKeyCode) && this.settings.enableRepl)
/*      */         {
/* 1099 */           this.mc.a((blk)new GuiMacroRepl(this.macros, this.mc, null));
/*      */         }
/*      */         else
/*      */         {
/* 1103 */           this.mc.a((blk)new GuiMacroPlayback(this.macros, this.mc));
/*      */         }
/*      */       
/*      */       }
/*      */       else
/*      */       {
/* 1109 */         this.mc.a((blk)new GuiMacroPlayback(this.macros, this.mc));
/*      */       }
/*      */     
/* 1112 */     } else if (key == this.replKeyCode) {
/*      */       
/* 1114 */       this.mc.a((blk)new GuiMacroRepl(this.macros, this.mc, null));
/*      */     }
/* 1116 */     else if (this.settings.compatibilityMode && this.settings.enableOverride && key == this.overrideKeyCode) {
/*      */ 
/*      */       
/* 1119 */       if (!modifierKeyDown && isScreenOverridable(this.mc.m))
/*      */       {
/* 1121 */         this.mc.a((blk)new GuiMacroPlayback(this.macros, this.mc, true, null));
/*      */       }
/*      */     }
/* 1124 */     else if (this.core.getThumbnailHandler().isCapturingThumbnail()) {
/*      */       
/* 1126 */       if (key > 249 || key == 28 || key == 156)
/*      */       {
/* 1128 */         this.core.getThumbnailHandler().captureThumbnail();
/*      */       }
/* 1130 */       else if (key == 1)
/*      */       {
/* 1132 */         this.core.getThumbnailHandler().cancelCaptureThumbnail(true);
/*      */       }
/*      */     
/* 1135 */     } else if (key > 0 && key < this.pressedKeys.length) {
/*      */       
/* 1137 */       if (!this.pressedKeys[key]) {
/*      */         
/* 1139 */         this.pressedKeys[key] = true;
/* 1140 */         this.macros.autoPlayMacro(key, overrideKeyDown);
/*      */       } 
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
/*      */   public void notifyKeyDown(int key) {
/* 1153 */     this.pressedKeys[key] = true;
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
/*      */   public boolean isScreenOverridable(blk screen) {
/* 1165 */     if (screen instanceof IProhibitOverride) return false; 
/* 1166 */     if (screen instanceof ble) return false; 
/* 1167 */     if (screen instanceof bme) return false; 
/* 1168 */     if (screen instanceof bls) return false; 
/* 1169 */     if (screen instanceof blc) return false; 
/* 1170 */     if (screen instanceof bkm) return false; 
/* 1171 */     if (screen instanceof bln) return false; 
/* 1172 */     if (screen instanceof bnw) return false; 
/* 1173 */     if (screen instanceof bml && !this.settings.enableOverrideCmdBlock) return false; 
/* 1174 */     if (screen instanceof bkn && !this.settings.enableOverrideChat) return false; 
/* 1175 */     if (this.core.getAutoDiscoveryHandler().isActive()) return false; 
/* 1176 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isKeyDown(int keyCode, int... keyCodes) {
/* 1181 */     if (isKeyDown(keyCode))
/*      */     {
/* 1183 */       return true;
/*      */     }
/*      */     
/* 1186 */     for (int code : keyCodes) {
/*      */       
/* 1188 */       if (isKeyDown(code))
/*      */       {
/* 1190 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1194 */     return false;
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
/*      */   public static boolean isKeyDown(int keyCode) {
/*      */     try {
/* 1208 */       if (keyCode == 0)
/*      */       {
/* 1210 */         return false;
/*      */       }
/* 1212 */       if (keyCode < 0)
/*      */       {
/* 1214 */         return Mouse.isButtonDown(keyCode + 100);
/*      */       }
/* 1216 */       if (keyCode < 255)
/*      */       {
/* 1218 */         return Keyboard.isKeyDown(keyCode);
/*      */       }
/*      */     }
/* 1221 */     catch (ArrayIndexOutOfBoundsException ex) {
/*      */       
/* 1223 */       return false;
/*      */     } 
/*      */     
/* 1226 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isKeyReallyDown(int keyCode) {
/* 1231 */     if (keyCode > 0 && keyCode < 255 && keyDownOverrideBuffer.get(keyCode) == 1)
/*      */     {
/* 1233 */       return true;
/*      */     }
/*      */     
/* 1236 */     return isKeyDown(keyCode);
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
/*      */   public static boolean isTriggerActive(int mappingId) {
/* 1249 */     if (mappingId < 255) {
/*      */       
/* 1251 */       if (mappingId == 250) return Mouse.isButtonDown(0); 
/* 1252 */       if (mappingId == 251) return Mouse.isButtonDown(1); 
/* 1253 */       if (mappingId == 252) return Mouse.isButtonDown(2); 
/* 1254 */       return isKeyReallyDown(mappingId);
/*      */     } 
/*      */     
/* 1257 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isControlDown() {
/* 1262 */     return (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157));
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAltDown() {
/* 1267 */     return (Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184));
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isShiftDown() {
/* 1272 */     return (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
/*      */   }
/*      */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\input\InputHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */