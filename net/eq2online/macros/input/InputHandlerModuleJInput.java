/*     */ package net.eq2online.macros.input;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.core.LiteLoader;
/*     */ import com.mumfrey.liteloader.util.jinput.ComponentRegistry;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ import net.java.games.input.Component;
/*     */ import net.java.games.input.Controller;
/*     */ import net.java.games.input.Event;
/*     */ import net.java.games.input.EventQueue;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ public class InputHandlerModuleJInput
/*     */   implements IInputHandlerModule
/*     */ {
/*     */   private static final String KEY_YAXIS = "y";
/*     */   private static final String KEY_XAXIS = "x";
/*  53 */   private static Pattern PATTERN_CONFIG = Pattern.compile("^(\\d{1,4}|x|y|KEY_[a-z0-9]+)=(.+)$", 2);
/*     */ 
/*     */   
/*     */   private static Field fdMouseDX;
/*     */ 
/*     */   
/*     */   private static Field fdMouseDY;
/*     */ 
/*     */   
/*     */   private final Macros macros;
/*     */ 
/*     */   
/*     */   private ComponentRegistry registry;
/*     */   
/*  67 */   private Map<Component, Integer> mappings = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private Map<Component, Float> axisMappingsX = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private Map<Component, Float> axisMappingsY = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private boolean[] states = new boolean[10000];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   private boolean[] stateMask = new boolean[this.states.length];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private List<Controller> pollDevices = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public InputHandlerModuleJInput(Macros macros) {
/*  96 */     this.macros = macros;
/*     */ 
/*     */     
/*     */     try {
/* 100 */       this.registry = LiteLoader.getInput().getComponentRegistry();
/*     */       
/* 102 */       fdMouseDX = Mouse.class.getDeclaredField("dx");
/* 103 */       fdMouseDY = Mouse.class.getDeclaredField("dy");
/* 104 */       fdMouseDX.setAccessible(true);
/* 105 */       fdMouseDY.setAccessible(true);
/*     */     }
/* 107 */     catch (Exception ex) {
/*     */       
/* 109 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClearSettings() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadSettings(ISettingsStore settings) {
/* 121 */     this.mappings.clear();
/* 122 */     this.pollDevices.clear();
/* 123 */     this.registry.enumerate();
/*     */     
/* 125 */     for (int i = 0; i < this.states.length; i++) {
/*     */       
/* 127 */       this.states[i] = false;
/* 128 */       this.stateMask[i] = false;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 133 */       BufferedReader bufferedreader = getSettingsReader();
/*     */       
/* 135 */       for (String configLine = ""; (configLine = bufferedreader.readLine()) != null; ) {
/*     */         
/* 137 */         Matcher configLineMatcher = PATTERN_CONFIG.matcher(configLine);
/* 138 */         if (configLineMatcher.matches())
/*     */         {
/* 140 */           readConfigLine(configLineMatcher.group(1), configLineMatcher.group(2));
/*     */         }
/*     */       } 
/*     */       
/* 144 */       bufferedreader.close();
/*     */     }
/* 146 */     catch (Exception ex) {
/*     */       
/* 148 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void readConfigLine(String key, String mappingPath) {
/* 154 */     if (isQuoted(mappingPath)) {
/*     */       
/* 156 */       if (!key.equalsIgnoreCase("x") && !key.equalsIgnoreCase("y"))
/*     */       {
/* 158 */         readKeyName(key, mappingPath);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 163 */       readMapping(key, mappingPath);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void readKeyName(String key, String mappingPath) {
/* 169 */     Integer mappingID = getMappingID(key);
/* 170 */     setKeyName(mappingID.intValue(), mappingPath.substring(1, mappingPath.length() - 1));
/*     */   }
/*     */ 
/*     */   
/*     */   private void readMapping(String key, String mappingPath) {
/* 175 */     for (Component component : this.registry.getComponents(mappingPath)) {
/*     */       
/* 177 */       if (key.equalsIgnoreCase("x") || key.equalsIgnoreCase("y")) {
/*     */         
/* 179 */         addAxisMapping(key, mappingPath, component);
/*     */         
/*     */         continue;
/*     */       } 
/* 183 */       addKeyMapping(key, mappingPath, component);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addAxisMapping(String key, String mappingPath, Component component) {
/* 190 */     Map<Component, Float> mappings = key.equalsIgnoreCase("x") ? this.axisMappingsX : this.axisMappingsY;
/*     */     
/* 192 */     if (component != null && component.isAnalog() && !mappings.containsKey(component)) {
/*     */       
/* 194 */       mappings.put(component, Float.valueOf(0.0F));
/*     */       
/* 196 */       for (Controller controller : this.registry.getControllers(mappingPath)) {
/*     */         
/* 198 */         Log.info("Hooking {0} to mouse {1} axis via JInput", new Object[] { ComponentRegistry.getDescriptor(controller, component), key });
/*     */         
/* 200 */         if (!this.pollDevices.contains(controller))
/*     */         {
/* 202 */           this.pollDevices.add(controller);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addKeyMapping(String key, String mappingPath, Component component) {
/* 210 */     Integer mappingID = getMappingID(key);
/*     */     
/* 212 */     if (mappingID.intValue() < 1 || mappingID.intValue() > 255 || component == null || component.isAnalog()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 217 */     for (Controller controller : this.registry.getControllers(mappingPath)) {
/*     */       
/* 219 */       if (!this.pollDevices.contains(controller))
/*     */       {
/* 221 */         this.pollDevices.add(controller);
/*     */       }
/*     */     } 
/*     */     
/* 225 */     Log.info("Hooking {0} as ID {1} via JInput", new Object[] { ComponentRegistry.getDescriptor(this.registry.getController(mappingPath), component), mappingID });
/* 226 */     this.mappings.put(component, mappingID);
/* 227 */     this.stateMask[mappingID.intValue()] = true;
/* 228 */     setKeyName(mappingID.intValue(), "<" + mappingID + ">");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Integer getMappingID(String strMappingId) {
/* 237 */     Integer mappingID = Integer.valueOf(0);
/*     */ 
/*     */     
/*     */     try {
/* 241 */       if (strMappingId.toLowerCase().startsWith("key_")) {
/*     */         
/* 243 */         String keyName = strMappingId.substring(4).toLowerCase();
/*     */         
/* 245 */         for (int key = 0; key < 255; key++) {
/*     */           
/* 247 */           if (Keyboard.getKeyName(key).toLowerCase().equals(keyName)) {
/*     */             
/* 249 */             mappingID = Integer.valueOf(key);
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 256 */         mappingID = Integer.valueOf(Integer.parseInt(strMappingId));
/*     */       }
/*     */     
/* 259 */     } catch (NumberFormatException numberFormatException) {}
/*     */     
/* 261 */     return mappingID;
/*     */   }
/*     */ 
/*     */   
/*     */   private BufferedReader getSettingsReader() throws FileNotFoundException {
/* 266 */     File settingsFile = this.macros.getFile(".jinput.txt");
/*     */ 
/*     */     
/* 269 */     if (settingsFile.exists())
/*     */     {
/* 271 */       return new BufferedReader(new FileReader(settingsFile));
/*     */     }
/*     */ 
/*     */     
/* 275 */     return new BufferedReader(new InputStreamReader(InputHandlerModuleJInput.class.getResourceAsStream("/.jinput.txt")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSaveSettings(ISettingsStore settings) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialise(InputHandler inputHandler, ISettingsStore settings) {
/* 286 */     onLoadSettings(settings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(bib minecraft, ISettingsStore settings) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(List<InputHandler.InputEvent> keyEventQueue, boolean overrideKeyDown, boolean modifierKeyDown) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onTick(List<InputHandler.InputEvent> keyEventQueue) {
/* 302 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean injectEvents(InputHandler.BufferProcessor processor, ByteBuffer keyDownBuffer, ByteBuffer mouseDownBuffer) {
/* 308 */     boolean pushed = inject(processor);
/* 309 */     setKeyStates(keyDownBuffer);
/* 310 */     setAxisStates();
/* 311 */     return pushed;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean inject(InputHandler.BufferProcessor processor) {
/* 316 */     boolean pushed = false;
/*     */     
/* 318 */     for (Controller controller : this.pollDevices) {
/*     */       
/* 320 */       controller.poll();
/* 321 */       EventQueue controllerQueue = controller.getEventQueue();
/*     */       
/* 323 */       for (Event event = new Event(); controllerQueue.getNextEvent(event); ) {
/*     */         
/* 325 */         Component cmp = event.getComponent();
/*     */         
/* 327 */         if (this.mappings.containsKey(cmp)) {
/*     */           
/* 329 */           int key = ((Integer)this.mappings.get(cmp)).intValue();
/* 330 */           boolean state = (event.getValue() == 1.0F);
/* 331 */           int character = CharMap.getKeyChar(key);
/*     */           
/* 333 */           if (processor.writeKeyboardEvent(key, state, character)) {
/*     */             
/* 335 */             this.states[key] = state;
/* 336 */             pushed = true;
/*     */           }  continue;
/*     */         } 
/* 339 */         if (this.axisMappingsX.containsKey(cmp)) {
/*     */           
/* 341 */           this.axisMappingsX.put(cmp, Float.valueOf(event.getValue())); continue;
/*     */         } 
/* 343 */         if (this.axisMappingsY.containsKey(cmp))
/*     */         {
/* 345 */           this.axisMappingsY.put(cmp, Float.valueOf(event.getValue()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 350 */     return pushed;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setKeyStates(ByteBuffer keyDownBuffer) {
/* 355 */     for (int key = 0; key < this.states.length; key++) {
/*     */       
/* 357 */       if (this.stateMask[key])
/*     */       {
/* 359 */         keyDownBuffer.put(key, (byte)(this.states[key] ? 1 : 0));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setAxisStates() {
/* 366 */     float dx = 0.0F, dy = 0.0F;
/*     */ 
/*     */     
/*     */     try {
/* 370 */       if (fdMouseDX != null) {
/*     */         
/* 372 */         for (Float fDX : this.axisMappingsX.values())
/*     */         {
/* 374 */           dx += fDX.floatValue() * 4.0F;
/*     */         }
/*     */         
/* 377 */         fdMouseDX.setAccessible(true);
/* 378 */         dx += ((Integer)fdMouseDX.get(null)).intValue();
/* 379 */         fdMouseDX.set(null, Integer.valueOf((int)dx));
/*     */       } 
/* 381 */       if (fdMouseDY != null)
/*     */       {
/* 383 */         for (Float fDY : this.axisMappingsY.values())
/*     */         {
/* 385 */           dy -= fDY.floatValue() * 4.0F;
/*     */         }
/*     */         
/* 388 */         fdMouseDY.setAccessible(true);
/* 389 */         dy += ((Integer)fdMouseDY.get(null)).intValue();
/* 390 */         fdMouseDY.set(null, Integer.valueOf((int)dy));
/*     */       }
/*     */     
/* 393 */     } catch (Exception ex) {
/*     */       
/* 395 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isQuoted(String string) {
/* 401 */     return (string.startsWith("\"") && string.endsWith("\""));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setKeyName(int key, String name) {
/* 406 */     if (key < 0 || key > 255) {
/*     */       return;
/*     */     }
/*     */     try {
/* 410 */       Field fieldKeyName = Keyboard.class.getDeclaredField("keyName");
/* 411 */       fieldKeyName.setAccessible(true);
/* 412 */       String[] keyName = (String[])fieldKeyName.get(null);
/* 413 */       keyName[key] = name;
/*     */     }
/* 415 */     catch (Exception exception) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\input\InputHandlerModuleJInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */