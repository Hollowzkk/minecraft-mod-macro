/*     */ package net.eq2online.macros.event;
/*     */ 
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
/*     */ public class MacroEventValueWatcher<T>
/*     */ {
/*     */   protected final Macros macros;
/*     */   protected boolean suppressNext;
/*     */   protected String eventName;
/*     */   protected int eventPriority;
/*     */   protected T currentValue;
/*     */   protected T lastValue;
/*     */   
/*     */   public MacroEventValueWatcher(Macros macros, BuiltinEvent event) {
/*  47 */     this(macros, event, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroEventValueWatcher(Macros macros, BuiltinEvent event, boolean suppressInitial) {
/*  52 */     this(macros, event.getName(), event.getDefaultDispatchPriority(), suppressInitial);
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
/*     */   public MacroEventValueWatcher(Macros macros, String eventName, int eventPriority, boolean suppressInitial) {
/*  68 */     this.macros = macros;
/*  69 */     this.eventName = eventName;
/*  70 */     this.eventPriority = eventPriority;
/*  71 */     this.suppressNext = suppressInitial;
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
/*     */   public boolean checkValue(T value) {
/*  83 */     if (this.eventName == null)
/*     */     {
/*  85 */       throw new IllegalStateException("Invalid watcher state found whilst checking value");
/*     */     }
/*     */     
/*  88 */     if (value != null && (this.currentValue == null || !this.currentValue.equals(value))) {
/*     */       
/*  90 */       this.lastValue = this.currentValue;
/*  91 */       this.currentValue = value;
/*     */       
/*  93 */       if (!this.suppressNext)
/*     */       {
/*  95 */         return true;
/*     */       }
/*     */       
/*  98 */       this.suppressNext = false;
/*     */     } 
/*     */     
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void suppressNext() {
/* 110 */     this.lastValue = this.currentValue;
/* 111 */     this.suppressNext = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkValueAndSuppress(T value) {
/* 122 */     checkValue(value);
/* 123 */     suppressNext();
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
/*     */   public void checkValueAndDispatch(T value, String... eventArgs) {
/* 135 */     if (checkValue(value))
/*     */     {
/* 137 */       sendEvent(eventArgs);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEvent() {
/* 147 */     return this.eventName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendEvent(String... eventArgs) {
/* 158 */     this.macros.sendEvent(this.eventName, this.eventPriority, eventArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getLastValue() {
/* 167 */     return this.lastValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\MacroEventValueWatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */