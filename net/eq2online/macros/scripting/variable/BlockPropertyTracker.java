/*     */ package net.eq2online.macros.scripting.variable;
/*     */ 
/*     */ import aow;
/*     */ import awt;
/*     */ import awu;
/*     */ import axj;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPropertyTracker
/*     */ {
/*     */   private final String prefix;
/*     */   private final IVariableStore store;
/*     */   private final boolean clear;
/*  22 */   private Set<String> strings = new HashSet<>();
/*     */   
/*  24 */   private Set<String> numbers = new HashSet<>();
/*     */   
/*  26 */   private Set<String> flags = new HashSet<>();
/*     */   
/*     */   private awt currentState;
/*     */ 
/*     */   
/*     */   public BlockPropertyTracker(String prefix, IVariableStore store) {
/*  32 */     this(prefix, store, true, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPropertyTracker(String prefix, IVariableStore store, boolean clear) {
/*  37 */     this(prefix, store, clear, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPropertyTracker(String prefix, IVariableStore store, boolean clear, awt initialState) {
/*  42 */     this.prefix = prefix;
/*  43 */     this.store = store;
/*  44 */     this.clear = clear;
/*     */     
/*  46 */     init();
/*  47 */     clear();
/*  48 */     update(initialState);
/*     */   }
/*     */ 
/*     */   
/*     */   private void init() {
/*  53 */     for (Iterator<aow> iter = aow.h.iterator(); iter.hasNext(); ) {
/*     */       
/*  55 */       awu blockState = ((aow)iter.next()).s();
/*  56 */       for (UnmodifiableIterator<axj> unmodifiableIterator = blockState.b().t().keySet().iterator(); unmodifiableIterator.hasNext(); ) { axj<?> property = unmodifiableIterator.next();
/*     */         
/*  58 */         String propertyName = getPropertyName(property);
/*     */         
/*  60 */         if (property instanceof axf) {
/*     */           
/*  62 */           this.flags.add(propertyName); continue;
/*     */         } 
/*  64 */         if (property instanceof axi) {
/*     */           
/*  66 */           this.numbers.add(propertyName);
/*     */           
/*     */           continue;
/*     */         } 
/*  70 */         this.strings.add(propertyName); }
/*     */     
/*     */     } 
/*     */ 
/*     */     
/*  75 */     this.flags.removeAll(this.strings);
/*  76 */     this.flags.remove(this.numbers);
/*  77 */     this.numbers.removeAll(this.strings);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getPropertyName(axj<?> property) {
/*  82 */     return this.prefix + property.a().toUpperCase();
/*     */   }
/*     */ 
/*     */   
/*     */   private void clear() {
/*  87 */     if (!this.clear)
/*     */       return; 
/*  89 */     for (String string : this.strings)
/*     */     {
/*  91 */       this.store.storeVariable(string, "");
/*     */     }
/*     */     
/*  94 */     for (String number : this.numbers)
/*     */     {
/*  96 */       this.store.storeVariable(number, 0);
/*     */     }
/*     */     
/*  99 */     for (String flag : this.flags)
/*     */     {
/* 101 */       this.store.storeVariable(flag, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(awt state) {
/* 107 */     if (this.currentState != state) {
/*     */       
/* 109 */       clear();
/* 110 */       this.currentState = state;
/*     */       
/* 112 */       if (state != null)
/*     */       {
/* 114 */         setProperties(state);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setProperties(awt state) {
/* 121 */     for (axj<?> property : (Iterable<axj<?>>)state.s()) {
/*     */       
/* 123 */       String propertyName = getPropertyName(property);
/* 124 */       if (property instanceof axf && this.flags.contains(propertyName)) {
/*     */         
/* 126 */         this.store.storeVariable(propertyName, ((Boolean)state.c(property)).booleanValue()); continue;
/*     */       } 
/* 128 */       if (property instanceof axi && this.numbers.contains(propertyName)) {
/*     */         
/* 130 */         this.store.storeVariable(propertyName, ((Integer)state.c(property)).intValue());
/*     */         
/*     */         continue;
/*     */       } 
/* 134 */       this.store.storeVariable(propertyName, state.c(property).toString());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\BlockPropertyTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */