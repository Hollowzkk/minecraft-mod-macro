/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bib;
/*     */ import cer;
/*     */ import com.mumfrey.liteloader.common.Resources;
/*     */ import com.mumfrey.liteloader.core.LiteLoader;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeSet;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.IconResourcePack;
/*     */ import net.eq2online.macros.interfaces.IObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ import net.eq2online.xml.Xml;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LayoutManager
/*     */   implements ISettingsObserver
/*     */ {
/*     */   private static final String FILE_NAME = ".gui.xml";
/*     */   public static final String XMLNS_GC = "http://eq2online.net/macros/guiconfiguration";
/*     */   public static final String XMLNS_GB = "http://eq2online.net/macros/guibinding";
/*     */   private final Macros macros;
/*     */   private final bib mc;
/*     */   private final DesignableGuiControls controls;
/*     */   
/*     */   public final class Binding
/*     */   {
/*     */     public static final String PLAYBACK = "playback";
/*     */     public static final String INGAME = "ingame";
/*     */     public static final String INDEBUG = "indebug";
/*     */     public static final String INCHAT = "inchat";
/*     */     public static final String SCOREBOARD = "scoreboard";
/*     */     private final String slot;
/*     */     private final Insets margins;
/*     */     private DesignableGuiLayout layout;
/*     */     
/*     */     public Binding(String slot, DesignableGuiLayout layout) {
/*  56 */       this(slot, layout, 0, 0, 0, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Binding(String slot, DesignableGuiLayout layout, int top, int left, int bottom, int right) {
/*  61 */       this(slot, layout, new Insets(top, left, bottom, right));
/*     */     }
/*     */ 
/*     */     
/*     */     public Binding(String slot, DesignableGuiLayout layout, Insets margins) {
/*  66 */       this.slot = slot;
/*  67 */       this.margins = margins;
/*  68 */       this.layout = layout;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSlot() {
/*  73 */       return this.slot;
/*     */     }
/*     */ 
/*     */     
/*     */     public DesignableGuiLayout getLayout() {
/*  78 */       return this.layout;
/*     */     }
/*     */ 
/*     */     
/*     */     public Insets getMargins() {
/*  83 */       return this.margins;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/*  88 */       return this.layout.getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public Rectangle getBoundingBox(Rectangle boundingBox) {
/*  93 */       if (this.margins.top == 0 && this.margins.left == 0 && this.margins.bottom == 0 && this.margins.right == 0)
/*     */       {
/*  95 */         return boundingBox;
/*     */       }
/*     */       
/*  98 */       Rectangle inset = new Rectangle(boundingBox);
/*  99 */       inset.y += this.margins.top;
/* 100 */       inset.x += this.margins.left;
/* 101 */       inset.width -= this.margins.left + this.margins.right;
/* 102 */       inset.height -= this.margins.top + this.margins.bottom;
/* 103 */       return inset;
/*     */     }
/*     */ 
/*     */     
/*     */     void bind(DesignableGuiLayout layout) {
/* 108 */       if (layout != null)
/*     */       {
/* 110 */         this.layout = layout;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     void save(Document xml, Element node) {
/* 116 */       node.setAttribute("slot", this.slot);
/* 117 */       node.setAttribute("top", String.valueOf(this.margins.top));
/* 118 */       node.setAttribute("left", String.valueOf(this.margins.left));
/* 119 */       node.setAttribute("bottom", String.valueOf(this.margins.bottom));
/* 120 */       node.setAttribute("right", String.valueOf(this.margins.right));
/* 121 */       node.setTextContent(getName());
/*     */     }
/*     */ 
/*     */     
/*     */     void load(Document xml, Node node) {
/* 126 */       String layoutName = node.getTextContent();
/* 127 */       bind(LayoutManager.this.getLayout(layoutName, false));
/*     */       
/* 129 */       this.margins.top = Xml.xmlGetAttribute(node, "top", this.margins.top);
/* 130 */       this.margins.left = Xml.xmlGetAttribute(node, "left", this.margins.left);
/* 131 */       this.margins.bottom = Xml.xmlGetAttribute(node, "bottom", this.margins.bottom);
/* 132 */       this.margins.right = Xml.xmlGetAttribute(node, "right", this.margins.right);
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
/* 151 */   protected final Map<String, DesignableGuiLayout> layouts = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   private final Map<String, Binding> bindings = new HashMap<>();
/*     */   
/*     */   private final IconResourcePack iconResources;
/*     */ 
/*     */   
/*     */   public LayoutManager(Macros macros, bib minecraft) {
/* 162 */     this.macros = macros;
/* 163 */     this.mc = minecraft;
/* 164 */     this.controls = new DesignableGuiControls(macros, minecraft);
/*     */     
/* 166 */     createBinding("playback", "default");
/* 167 */     createBinding("ingame", "ingame", 14, 2, 35, 2);
/* 168 */     createBinding("indebug", "ingame");
/* 169 */     createBinding("inchat", "inchat");
/* 170 */     createBinding("scoreboard", "ingame");
/*     */     
/* 172 */     this.iconResources = initIconResourcePack();
/*     */     
/* 174 */     this.macros.getSettingsHandler().registerObserver((IObserver)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IconResourcePack initIconResourcePack() {
/* 180 */     File customIconsDir = this.macros.getFile("icons/custom");
/* 181 */     IconResourcePack icons = new IconResourcePack(customIconsDir, "macros");
/* 182 */     Resources<?, cer> resources = LiteLoader.getGameEngine().getResources();
/* 183 */     resources.registerResourcePack(icons);
/* 184 */     return icons;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 189 */     this.controls.onTick();
/*     */   }
/*     */ 
/*     */   
/*     */   public IconResourcePack getIconResources() {
/* 194 */     return this.iconResources;
/*     */   }
/*     */ 
/*     */   
/*     */   public DesignableGuiControls getControls() {
/* 199 */     return this.controls;
/*     */   }
/*     */ 
/*     */   
/*     */   private void createBinding(String slot, String layout) {
/* 204 */     this.bindings.put(slot, new Binding(slot, getDefaultLayout(layout, false)));
/*     */   }
/*     */ 
/*     */   
/*     */   private void createBinding(String slot, String layout, int top, int left, int bottom, int right) {
/* 209 */     this.bindings.put(slot, new Binding(slot, getDefaultLayout(layout, false), top, left, bottom, right));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addLayout(DesignableGuiLayout layout) {
/* 214 */     this.layouts.put(layout.getName(), layout);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBinding(String slotName, String layoutName) {
/* 219 */     if (slotName != null) {
/*     */       
/* 221 */       Binding binding = this.bindings.get(slotName);
/* 222 */       if (binding != null)
/*     */       {
/* 224 */         binding.bind(getLayout(layoutName));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getSlotNames() {
/* 231 */     List<String> orderedSlotNames = new ArrayList<>();
/*     */     
/* 233 */     orderedSlotNames.add("playback");
/* 234 */     orderedSlotNames.add("ingame");
/* 235 */     orderedSlotNames.add("indebug");
/* 236 */     orderedSlotNames.add("inchat");
/* 237 */     orderedSlotNames.add("scoreboard");
/*     */     
/* 239 */     for (String slotName : this.bindings.keySet()) {
/*     */       
/* 241 */       if (!orderedSlotNames.contains(slotName))
/*     */       {
/* 243 */         orderedSlotNames.add(slotName);
/*     */       }
/*     */     } 
/*     */     
/* 247 */     return orderedSlotNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBoundLayoutName(String slotName) {
/* 252 */     if (this.bindings.get(slotName) != null)
/*     */     {
/* 254 */       return ((Binding)this.bindings.get(slotName)).getName();
/*     */     }
/*     */     
/* 257 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public DesignableGuiLayout getBoundLayout(String slotName, boolean canBeNull) {
/* 262 */     Binding binding = this.bindings.get(slotName);
/* 263 */     if (binding != null)
/*     */     {
/* 265 */       return binding.getLayout();
/*     */     }
/*     */     
/* 268 */     return getDefaultLayout(slotName, canBeNull);
/*     */   }
/*     */ 
/*     */   
/*     */   public Insets getMargins(String slotName) {
/* 273 */     Binding binding = this.bindings.get(slotName);
/* 274 */     if (binding != null)
/*     */     {
/* 276 */       return binding.getMargins();
/*     */     }
/*     */     
/* 279 */     return new Insets(0, 0, 0, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Binding getBinding(String slotName) {
/* 284 */     return this.bindings.get(slotName);
/*     */   }
/*     */ 
/*     */   
/*     */   public DesignableGuiLayout getDefaultLayout(String slotName, boolean canBeNull) {
/* 289 */     if ("playback".equals(slotName)) return getLayout("default"); 
/* 290 */     if ("ingame".equals(slotName)) return getLayout("ingame"); 
/* 291 */     if ("indebug".equals(slotName)) return getLayout("ingame"); 
/* 292 */     if ("scoreboard".equals(slotName)) return getLayout("ingame"); 
/* 293 */     if ("inchat".equals(slotName)) return getLayout("inchat"); 
/* 294 */     return canBeNull ? null : getLayout("default");
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getLayoutNames() {
/* 299 */     List<String> orderedLayoutNames = new ArrayList<>();
/*     */     
/* 301 */     orderedLayoutNames.add("default");
/* 302 */     orderedLayoutNames.add("ingame");
/* 303 */     orderedLayoutNames.add("inchat");
/*     */     
/* 305 */     TreeSet<String> sortedLayoutNames = new TreeSet<>(this.layouts.keySet());
/*     */     
/* 307 */     for (String layoutName : sortedLayoutNames) {
/*     */       
/* 309 */       if (!orderedLayoutNames.contains(layoutName))
/*     */       {
/* 311 */         orderedLayoutNames.add(layoutName);
/*     */       }
/*     */     } 
/*     */     
/* 315 */     return orderedLayoutNames;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuiltinLayout(String layoutName) {
/* 320 */     return layoutName.matches("^(ingame|inchat|default)$");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean layoutExists(String layoutName) {
/* 325 */     if (layoutName == null)
/*     */     {
/* 327 */       return false;
/*     */     }
/* 329 */     return this.layouts.containsKey(layoutName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DesignableGuiLayout getLayout(String name) {
/* 340 */     return getLayout(name, true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DesignableGuiLayout getLayout(String name, boolean create) {
/* 345 */     if (!this.layouts.containsKey(name) && create) {
/*     */       
/* 347 */       DesignableGuiLayout newLayout = new DesignableGuiLayout(this.macros, this.mc, this, name);
/*     */       
/* 349 */       if ("ingame".equals(name)) {
/*     */         
/* 351 */         newLayout.setColumns(2);
/* 352 */         newLayout.setRows(4);
/* 353 */         newLayout.setColumnWidth(0, 324);
/*     */         
/* 355 */         DesignableGuiControlTextArea debug = (DesignableGuiControlTextArea)newLayout.addControl("textarea", 0, 1);
/* 356 */         debug.setName("debug");
/* 357 */         debug.rowSpan = 4;
/*     */       } 
/*     */       
/* 360 */       this.layouts.put(name, newLayout);
/*     */     } 
/*     */     
/* 363 */     return this.layouts.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean deleteLayout(String layoutName) {
/* 368 */     if (layoutName != null && this.layouts.containsKey(layoutName) && !isBuiltinLayout(layoutName)) {
/*     */       
/* 370 */       this.layouts.remove(layoutName);
/*     */       
/* 372 */       for (Iterator<Map.Entry<String, Binding>> iter = this.bindings.entrySet().iterator(); iter.hasNext(); ) {
/*     */         
/* 374 */         Map.Entry<String, Binding> entry = iter.next();
/* 375 */         Binding binding = entry.getValue();
/* 376 */         if (binding.getName().equals(layoutName))
/*     */         {
/* 378 */           binding.bind(getDefaultLayout(entry.getKey(), false));
/*     */         }
/*     */       } 
/*     */       
/* 382 */       return true;
/*     */     } 
/*     */     
/* 385 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkControlExistsInLayouts(int controlId) {
/* 390 */     for (DesignableGuiLayout layout : this.layouts.values()) {
/*     */       
/* 392 */       if (layout.getControl(controlId) != null) return true;
/*     */     
/*     */     } 
/* 395 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClearSettings() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadSettings(ISettingsStore settings) {
/* 411 */     File settingsFile = this.macros.getFile(".gui.xml");
/*     */     
/* 413 */     Xml.xmlClearNamespace();
/* 414 */     Xml.xmlAddNamespace("gc", "http://eq2online.net/macros/guiconfiguration");
/* 415 */     Xml.xmlAddNamespace("gb", "http://eq2online.net/macros/guibinding");
/*     */ 
/*     */     
/*     */     try {
/* 419 */       if (settingsFile.exists()) {
/*     */         
/* 421 */         Document xml = Xml.xmlCreate(settingsFile);
/* 422 */         if (xml != null) loadFromXml(xml);
/*     */       
/*     */       } else {
/*     */         
/* 426 */         InputStream is = DesignableGuiControl.class.getResourceAsStream("/.gui.xml");
/* 427 */         Document xml = Xml.xmlCreate(is);
/* 428 */         if (xml != null) loadFromXml(xml);
/*     */       
/*     */       } 
/* 431 */     } catch (Exception ex) {
/*     */       
/* 433 */       Log.printStackTrace(ex);
/*     */     } 
/*     */     
/* 436 */     Xml.xmlClearNamespace();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSaveSettings(ISettingsStore settings) {
/* 442 */     saveSettings();
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveSettings() {
/* 447 */     this.macros.getPlaybackStatus().setOverlayMode(true);
/*     */ 
/*     */     
/*     */     try {
/* 451 */       saveToXml(new File(this.macros.getMacrosDirectory(), ".gui.xml"));
/*     */     }
/* 453 */     catch (Exception ex) {
/*     */       
/* 455 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadFromXml(Document xml) {
/* 461 */     loadLayouts(xml);
/* 462 */     loadBindings(xml);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadLayouts(Document xml) {
/* 470 */     for (Node guiLayoutNode : Xml.xmlNodes(xml, "/gui/gc:guilayout")) {
/*     */       
/* 472 */       DesignableGuiLayout layout = null;
/*     */ 
/*     */       
/*     */       try {
/* 476 */         layout = new DesignableGuiLayout(this.macros, this.mc, this, guiLayoutNode);
/* 477 */         for (Binding binding : this.bindings.values())
/*     */         {
/* 479 */           if (binding.getLayout().getName().equals(layout.getName()))
/*     */           {
/* 481 */             binding.bind(layout);
/*     */           }
/*     */         }
/*     */       
/* 485 */       } catch (Exception ex) {
/*     */         
/* 487 */         ex.printStackTrace();
/*     */       } 
/*     */       
/* 490 */       if (layout != null)
/*     */       {
/* 492 */         addLayout(layout);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadBindings(Document xml) throws DOMException {
/* 503 */     if ((this.macros.getSettings()).loadLayoutBindings)
/*     */     {
/* 505 */       for (Node guiBindingNode : Xml.xmlNodes(xml, "/gui/gb:bindings/gb:binding")) {
/*     */ 
/*     */         
/*     */         try {
/* 509 */           String slotName = Xml.xmlGetAttribute(guiBindingNode, "slot", "&");
/* 510 */           if ("&".equals(slotName)) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 515 */           Binding binding = this.bindings.get(slotName);
/* 516 */           if (binding != null)
/*     */           {
/* 518 */             binding.load(xml, guiBindingNode);
/*     */           }
/*     */         }
/* 521 */         catch (Exception exception) {}
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void saveToXml(File xmlFile) {
/* 528 */     Document xml = Xml.xmlCreate();
/*     */     
/* 530 */     Element rootNode = xml.createElement("gui");
/* 531 */     rootNode.setAttribute("xmlns:gc", "http://eq2online.net/macros/guiconfiguration");
/* 532 */     rootNode.setAttribute("xmlns:gb", "http://eq2online.net/macros/guibinding");
/* 533 */     xml.appendChild(rootNode);
/*     */     
/* 535 */     saveBindings(xml, rootNode);
/* 536 */     saveLayouts(xml, rootNode);
/*     */     
/* 538 */     Xml.xmlSave(xmlFile, xml);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveLayouts(Document xml, Element rootNode) throws DOMException {
/* 548 */     for (DesignableGuiLayout layout : this.layouts.values()) {
/*     */       
/* 550 */       Element layoutNode = xml.createElement("gc:guilayout");
/* 551 */       layout.save(xml, layoutNode);
/*     */       
/* 553 */       rootNode.appendChild(xml.createComment(" Config for layout '" + layout.getName() + "' "));
/* 554 */       rootNode.appendChild(layoutNode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveBindings(Document xml, Element rootNode) {
/* 564 */     Element bindingsNode = xml.createElement("gb:bindings");
/*     */     
/* 566 */     for (Binding binding : this.bindings.values()) {
/*     */       
/* 568 */       Element bindingNode = xml.createElement("gb:binding");
/* 569 */       binding.save(xml, bindingNode);
/* 570 */       bindingsNode.appendChild(bindingNode);
/*     */     } 
/*     */     
/* 573 */     rootNode.appendChild(xml.createComment(" GUI slot bindings "));
/* 574 */     rootNode.appendChild(bindingsNode);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\LayoutManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */