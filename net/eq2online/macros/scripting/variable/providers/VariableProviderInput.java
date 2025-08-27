/*    */ package net.eq2online.macros.scripting.variable.providers;
/*    */ 
/*    */ import net.eq2online.macros.input.InputHandler;
/*    */ import net.eq2online.macros.scripting.variable.VariableCache;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VariableProviderInput
/*    */   extends VariableCache
/*    */ {
/*    */   public void updateVariables(boolean clock) {
/* 20 */     storeVariable("CTRL", InputHandler.isControlDown());
/* 21 */     storeVariable("ALT", InputHandler.isAltDown());
/* 22 */     storeVariable("SHIFT", InputHandler.isShiftDown());
/*    */     
/* 24 */     storeVariable("LMOUSE", Mouse.isButtonDown(0));
/* 25 */     storeVariable("RMOUSE", Mouse.isButtonDown(1));
/* 26 */     storeVariable("MIDDLEMOUSE", Mouse.isButtonDown(2));
/*    */     
/* 28 */     for (int key = 0; key < 255; key++) {
/*    */       
/* 30 */       String keyName = Keyboard.getKeyName(key);
/*    */       
/* 32 */       if (keyName != null)
/*    */       {
/* 34 */         storeVariable("KEY_" + keyName.toUpperCase(), Keyboard.isKeyDown(key));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getVariable(String variableName) {
/* 42 */     return getCachedValue(variableName);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\providers\VariableProviderInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */