/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.IconTiled;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.list.EditableListEntry;
/*     */ import net.eq2online.macros.gui.list.ListEntry;
/*     */ import net.eq2online.macros.interfaces.IConfigs;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.ILoadableConfigObserver;
/*     */ import net.eq2online.macros.interfaces.IObserver;
/*     */ import net.eq2online.macros.interfaces.IStringSerialisable;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiListBoxFilebound<T>
/*     */   extends GuiListBox<T>
/*     */   implements ILoadableConfigObserver
/*     */ {
/*  37 */   private static final Pattern PATTERN_LINE = Pattern.compile("^(.+)$");
/*  38 */   private static final Pattern PATTERN_LINE_ICONS = Pattern.compile("^([0-9]{1,3}):(.+)$", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static final Pattern PATTERN_BEGINCONFIG = Pattern.compile("^DIRECTIVE BEGINCONFIG\\(\\) (.+)$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   protected static final Pattern PATTERN_DISPLAYNAME = Pattern.compile("\\[Display=(.+)\\]$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Macros macros;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean editable;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final File file;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Pattern linePattern;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected nf iconTexture;
/*     */ 
/*     */ 
/*     */   
/*  77 */   protected int saveTrimTailSize = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   protected final Map<String, List<IListEntry<T>>> configs = new HashMap<>();
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
/*     */   public GuiListBoxFilebound(Macros macros, bib minecraft, int controlId, boolean showIcons, File file) {
/*  94 */     this(macros, minecraft, controlId, showIcons, file, getDefaultLinePattern(showIcons));
/*     */   }
/*     */ 
/*     */   
/*     */   protected GuiListBoxFilebound(Macros macros, bib minecraft, int controlId, boolean showIcons, File file, Pattern linePattern) {
/*  99 */     this(macros, minecraft, controlId, showIcons, file, linePattern, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected GuiListBoxFilebound(Macros macros, bib minecraft, int controlId, boolean showIcons, File file, boolean editable) {
/* 104 */     this(macros, minecraft, controlId, showIcons, file, getDefaultLinePattern(showIcons), editable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiListBoxFilebound(Macros macros, bib minecraft, int controlId, boolean showIcons, File file, Pattern linePattern, boolean editable) {
/* 110 */     super(minecraft, controlId, showIcons, true, true);
/*     */     
/* 112 */     this.macros = macros;
/* 113 */     this.editable = editable;
/* 114 */     this.linePattern = linePattern;
/* 115 */     this.file = file;
/*     */     
/* 117 */     this.macros.getSettingsHandler().registerObserver((IObserver)this);
/*     */     
/* 119 */     setSortable(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static Pattern getDefaultLinePattern(boolean showIcons) {
/* 124 */     return showIcons ? PATTERN_LINE_ICONS : PATTERN_LINE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getKey();
/*     */ 
/*     */ 
/*     */   
/*     */   public File getFile() {
/* 134 */     return this.file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(IConfigs configs) {
/* 144 */     this.items.clear();
/* 145 */     this.configs.clear();
/* 146 */     this.configs.put("", new ArrayList<>());
/*     */     
/* 148 */     if (this.file.exists()) {
/*     */       
/*     */       try {
/*     */         
/* 152 */         BufferedReader bufferedreader = new BufferedReader(new FileReader(this.file));
/* 153 */         String currentConfigName = "";
/*     */         
/* 155 */         for (String line = ""; (line = bufferedreader.readLine()) != null; ) {
/*     */           
/* 157 */           Matcher beginConfigPatternMatcher = PATTERN_BEGINCONFIG.matcher(line);
/*     */           
/* 159 */           if (beginConfigPatternMatcher.matches()) {
/*     */             
/* 161 */             currentConfigName = beginConfigPatternMatcher.group(1);
/*     */             
/* 163 */             if (!this.configs.containsKey(currentConfigName))
/*     */             {
/* 165 */               this.configs.put(currentConfigName, new ArrayList<>());
/*     */             }
/*     */             
/*     */             continue;
/*     */           } 
/* 170 */           Matcher linePatternMatcher = this.linePattern.matcher(line);
/* 171 */           loadItem(line, linePatternMatcher, this.configs.get(currentConfigName), currentConfigName);
/*     */         } 
/*     */ 
/*     */         
/* 175 */         bufferedreader.close();
/*     */       }
/* 177 */       catch (Exception ex) {
/*     */         
/* 179 */         Log.info("Error loading data for list box in {0}", new Object[] { this.file.getName() });
/* 180 */         Log.printStackTrace(ex);
/*     */       } 
/*     */     }
/*     */     
/* 184 */     onConfigChanged(configs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final List<IListEntry<T>> getConfig(String configName) {
/* 195 */     if (!this.configs.containsKey(configName)) {
/*     */       
/* 197 */       List<IListEntry<T>> newConfig = new ArrayList<>();
/* 198 */       this.configs.put(configName, newConfig);
/* 199 */       notifyNewConfig(newConfig);
/*     */     } 
/*     */     
/* 202 */     return this.configs.get(configName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void notifyNewConfig(List<IListEntry<T>> paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigChanged(IConfigs configs) {
/* 214 */     this.items = getConfig(configs.getActiveConfig());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigAdded(IConfigs configs, String configName, boolean copy) {
/* 224 */     if (!this.configs.containsKey(configName))
/*     */     {
/* 226 */       if (copy) {
/*     */         
/* 228 */         this.configs.put(configName, this.items);
/* 229 */         save();
/* 230 */         load(configs);
/*     */       }
/*     */       else {
/*     */         
/* 234 */         List<IListEntry<T>> newConfig = new ArrayList<>();
/* 235 */         this.configs.put(configName, newConfig);
/* 236 */         notifyNewConfig(newConfig);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigRemoved(IConfigs configs, String configName) {
/* 248 */     if (this.configs.containsKey(configName)) {
/*     */       
/* 250 */       this.configs.remove(configName);
/* 251 */       save();
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
/*     */   protected void loadItem(String line, Matcher linePatternMatcher, List<IListEntry<T>> items, String currentConfigName) {
/* 263 */     if (!linePatternMatcher.matches()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 268 */     if (this.iconsEnabled)
/*     */     {
/* 270 */       line = linePatternMatcher.group(2);
/*     */     }
/*     */     
/* 273 */     String displayName = null;
/* 274 */     Matcher displayNameMatcher = PATTERN_DISPLAYNAME.matcher(line);
/* 275 */     if (displayNameMatcher.find()) {
/*     */       
/* 277 */       displayName = displayNameMatcher.group(1);
/* 278 */       line = line.substring(0, displayNameMatcher.start());
/*     */     } 
/*     */     
/* 281 */     ListEntry<T> newItem = null;
/* 282 */     if (this.iconsEnabled) {
/*     */       
/* 284 */       int iconIndex = Integer.parseInt(linePatternMatcher.group(1));
/*     */       
/* 286 */       if (this.editable)
/*     */       {
/* 288 */         EditableListEntry editableListEntry = new EditableListEntry(this.newItemIndex++, iconIndex, line, this.iconTexture);
/*     */       }
/*     */       else
/*     */       {
/* 292 */         newItem = new ListEntry(this.newItemIndex++, line, null, (iconIndex >= 0), this.iconTexture, iconIndex);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 297 */       newItem = new ListEntry(this.newItemIndex++, line);
/*     */     } 
/*     */     
/* 300 */     items.add(newItem);
/*     */     
/* 302 */     if (displayName != null)
/*     */     {
/* 304 */       newItem.setDisplayName(displayName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void save() {
/*     */     try {
/* 316 */       PrintWriter printwriter = new PrintWriter(new FileWriter(this.file));
/*     */       
/* 318 */       for (Map.Entry<String, List<IListEntry<T>>> config : this.configs.entrySet()) {
/*     */         
/* 320 */         if (!((String)config.getKey()).equals(""))
/*     */         {
/* 322 */           printwriter.println("\nDIRECTIVE BEGINCONFIG() " + (String)config.getKey() + "\n");
/*     */         }
/*     */         
/* 325 */         for (int i = 0; i < ((List)config.getValue()).size() - this.saveTrimTailSize; i++)
/*     */         {
/* 327 */           printwriter.println(serialiseItem(((List<IListEntry<T>>)config.getValue()).get(i)));
/*     */         }
/*     */       } 
/*     */       
/* 331 */       printwriter.close();
/*     */     }
/* 333 */     catch (Exception ex) {
/*     */       
/* 335 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String serialiseItem(IListEntry<T> item) {
/* 341 */     if (item instanceof IStringSerialisable) {
/*     */       
/* 343 */       String ser = ((IStringSerialisable)item).serialise();
/* 344 */       if (ser != null)
/*     */       {
/* 346 */         return ser;
/*     */       }
/*     */     } 
/*     */     
/* 350 */     Icon icon = item.getIcon();
/* 351 */     int iconIndex = (icon != null && icon instanceof IconTiled) ? ((IconTiled)icon).getIconId() : 0;
/* 352 */     String itemSerialised = (this.iconsEnabled ? (iconIndex + ":") : "") + item.getText();
/* 353 */     if (!item.getDisplayName().equals(item.getText()) && item.getDisplayName().length() > 0)
/*     */     {
/* 355 */       itemSerialised = itemSerialised + "[Display=" + item.getDisplayName() + "]";
/*     */     }
/* 357 */     return itemSerialised;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiListBoxFilebound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */