/*    */ package net.eq2online.macros.interfaces;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IEditablePanel
/*    */ {
/*    */   void setMode(EditMode paramEditMode);
/*    */   
/*    */   void tickInGui();
/*    */   
/*    */   public enum EditMode
/*    */   {
/* 13 */     NONE(true, false, 0),
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 18 */     DELETE(false, true, -65536),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 24 */     COPY(false, true, -16711936),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 30 */     MOVE(false, true, -16711681),
/*    */     
/* 32 */     EDIT_BUTTONS(false, false, -256),
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 37 */     EDIT_ALL(false, true, -256),
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 42 */     RESERVE(true, false, -65536);
/*    */     
/*    */     private final boolean settable;
/*    */     
/*    */     private final boolean toggleable;
/*    */     
/*    */     public final int colour;
/*    */ 
/*    */     
/*    */     EditMode(boolean settable, boolean toggleable, int colour) {
/* 52 */       this.settable = settable;
/* 53 */       this.toggleable = toggleable;
/* 54 */       this.colour = colour;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isSettable() {
/* 59 */       return this.settable;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isToggleable() {
/* 64 */       return this.toggleable;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\IEditablePanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */