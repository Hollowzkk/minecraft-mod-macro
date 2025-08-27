/*     */ package net.eq2online.macros.event;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroEventListWatcher<T extends Collection<L>, L>
/*     */   extends MacroEventValueWatcher<T>
/*     */ {
/*     */   protected T myList;
/*     */   protected L newObject;
/*     */   
/*     */   public MacroEventListWatcher(Macros macros, BuiltinEvent event) {
/*  24 */     this(macros, event, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroEventListWatcher(Macros macros, BuiltinEvent event, boolean suppressInitial) {
/*  29 */     super(macros, event, suppressInitial);
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroEventListWatcher(Macros macros, String eventName, int eventPriority, boolean suppressInitial) {
/*  34 */     super(macros, eventName, eventPriority, suppressInitial);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkValue(T value) {
/*  45 */     if (value != null && this.myList == null) {
/*     */       
/*     */       try {
/*     */         
/*  49 */         this.myList = (T)new ArrayList();
/*     */       }
/*  51 */       catch (Exception ex) {
/*     */         
/*  53 */         Log.printStackTrace(ex);
/*     */       } 
/*     */     }
/*     */     
/*  57 */     if (value != null && this.myList != null) {
/*     */       
/*  59 */       boolean result = false;
/*     */       
/*  61 */       Iterator<L> valueIterator = value.iterator();
/*     */       
/*  63 */       while (valueIterator.hasNext()) {
/*     */         
/*  65 */         L obj = valueIterator.next();
/*     */         
/*  67 */         if (!this.myList.contains(obj)) {
/*     */           
/*  69 */           result = true;
/*  70 */           this.newObject = obj;
/*  71 */           this.myList.add(obj);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  76 */       Iterator<L> listIterator = this.myList.iterator();
/*     */       
/*  78 */       while (listIterator.hasNext()) {
/*     */         
/*  80 */         L obj = listIterator.next();
/*     */         
/*  82 */         if (!value.contains(obj))
/*     */         {
/*  84 */           listIterator.remove();
/*     */         }
/*     */       } 
/*     */       
/*  88 */       if (!this.suppressNext)
/*     */       {
/*  90 */         return result;
/*     */       }
/*     */       
/*  93 */       this.suppressNext = false;
/*     */     } 
/*     */     
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 101 */     this.myList = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public L getNewObject() {
/* 106 */     return this.newObject;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\MacroEventListWatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */