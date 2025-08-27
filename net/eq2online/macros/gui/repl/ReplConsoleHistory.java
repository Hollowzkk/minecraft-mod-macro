/*     */ package net.eq2online.macros.gui.repl;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.io.Files;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedList;
/*     */ import joptsimple.internal.Strings;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.interfaces.IObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
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
/*     */ public class ReplConsoleHistory
/*     */   implements ISettingsObserver
/*     */ {
/*     */   private static final String FILE_NAME = ".repl.history.txt";
/*     */   private static final int MAX_HISTORY_SIZE = 1000;
/*     */   private final Macros macros;
/*  31 */   private final LinkedList<String> history = new LinkedList<>();
/*     */   
/*     */   private int historyPos;
/*     */   
/*     */   private String stored;
/*     */   
/*     */   private IHistoryTarget target;
/*     */   
/*     */   private boolean loaded;
/*     */   
/*     */   private boolean dirty;
/*     */   private int dirtyTicks;
/*     */   
/*     */   public ReplConsoleHistory(Macros macros) {
/*  45 */     this.macros = macros;
/*  46 */     this.macros.getSettingsHandler().registerObserver((IObserver)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReplConsoleHistory connect(IHistoryTarget target) {
/*  51 */     this.target = target;
/*  52 */     cancel();
/*  53 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  58 */     if (this.dirty)
/*     */     {
/*  60 */       if (this.dirtyTicks++ > 100)
/*     */       {
/*  62 */         onSaveSettings((ISettingsStore)this.macros);
/*     */       }
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
/*  75 */     if (this.loaded) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  80 */     this.loaded = true;
/*     */     
/*  82 */     File historyFile = this.macros.getFile(".repl.history.txt");
/*  83 */     if (!historyFile.isFile()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  88 */     this.history.clear();
/*     */ 
/*     */     
/*     */     try {
/*  92 */       for (String line : Files.readLines(historyFile, Charsets.UTF_8))
/*     */       {
/*  94 */         if (!Strings.isNullOrEmpty(line))
/*     */         {
/*  96 */           this.history.add(line);
/*     */         }
/*     */       }
/*     */     
/* 100 */     } catch (IOException ex) {
/*     */       
/* 102 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 105 */     cancel();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSaveSettings(ISettingsStore settings) {
/* 111 */     if (!this.dirty || this.history.size() < 1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 116 */     save();
/*     */   }
/*     */ 
/*     */   
/*     */   private void save() {
/* 121 */     this.dirty = false;
/* 122 */     this.dirtyTicks = 0;
/*     */     
/* 124 */     File historyFile = this.macros.getFile(".repl.history.txt");
/* 125 */     historyFile.getParentFile().mkdirs();
/*     */ 
/*     */     
/*     */     try {
/* 129 */       Files.write(Joiner.on('\n').join(this.history), historyFile, Charsets.UTF_8);
/*     */     }
/* 131 */     catch (IOException ex) {
/*     */       
/* 133 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(String line) {
/* 139 */     while (this.history.size() > 999)
/*     */     {
/* 141 */       this.history.removeFirst();
/*     */     }
/*     */     
/* 144 */     this.history.remove(line);
/* 145 */     this.history.add(line);
/* 146 */     this.historyPos = this.history.size();
/* 147 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancel() {
/* 152 */     this.historyPos = this.history.size();
/* 153 */     this.stored = "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 158 */     this.history.clear();
/* 159 */     cancel();
/*     */   }
/*     */ 
/*     */   
/*     */   public void up() {
/* 164 */     if (this.historyPos < 1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 169 */     if (this.historyPos >= this.history.size())
/*     */     {
/* 171 */       this.stored = getText();
/*     */     }
/*     */     
/* 174 */     this.historyPos--;
/* 175 */     setText(this.history.get(this.historyPos));
/*     */   }
/*     */ 
/*     */   
/*     */   public void down() {
/* 180 */     if (this.historyPos >= this.history.size()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 185 */     this.historyPos++;
/*     */     
/* 187 */     if (this.historyPos == this.history.size()) {
/*     */       
/* 189 */       setText(this.stored);
/* 190 */       this.stored = "";
/*     */       
/*     */       return;
/*     */     } 
/* 194 */     setText(this.history.get(this.historyPos));
/*     */   }
/*     */ 
/*     */   
/*     */   private String getText() {
/* 199 */     return (this.target != null) ? this.target.getText() : "";
/*     */   }
/*     */ 
/*     */   
/*     */   private void setText(String text) {
/* 204 */     if (this.target != null)
/*     */     {
/* 206 */       this.target.setText(text);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface IHistoryTarget {
/*     */     String getText();
/*     */     
/*     */     void setText(String param1String);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\repl\ReplConsoleHistory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */