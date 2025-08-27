/*    */ package net.eq2online.macros.event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MacroEventQueueEntry
/*    */   implements Comparable<MacroEventQueueEntry>
/*    */ {
/*    */   private static int nextSequenceId;
/*    */   private final String name;
/*    */   private final String[] args;
/*    */   private final int priority;
/*    */   private final int sequence;
/*    */   private boolean synchronous;
/*    */   
/*    */   MacroEventQueueEntry(String name, int priority, String... args) {
/* 44 */     this.name = name;
/* 45 */     this.args = args;
/* 46 */     this.priority = Math.min(Math.max(priority, 0), 100);
/* 47 */     this.sequence = nextSequenceId++;
/*    */   }
/*    */ 
/*    */   
/*    */   MacroEventQueueEntry setSynchronous(boolean synchronous) {
/* 52 */     this.synchronous = synchronous;
/* 53 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   void dispatch(MacroEventManager manager) {
/* 58 */     manager.dispatchEvent(this.name, this.synchronous, this.args);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(MacroEventQueueEntry other) {
/* 65 */     if (other == null)
/*    */     {
/* 67 */       throw new NullPointerException();
/*    */     }
/*    */     
/* 70 */     if (other.priority < this.priority || other.sequence > this.sequence) return -1; 
/* 71 */     if (other.priority > this.priority || other.sequence < this.sequence) return 1;
/*    */     
/* 73 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\MacroEventQueueEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */