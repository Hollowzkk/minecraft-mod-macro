/*    */ package net.eq2online.macros.gui.hook;
/*    */ 
/*    */ import bib;
/*    */ import blk;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ 
/*    */ 
/*    */ public final class CustomScreenManager
/*    */ {
/*    */   private final Macros macros;
/*    */   private final bib mc;
/*    */   
/*    */   public static final class CustomScreen
/*    */   {
/*    */     private final int index;
/*    */     private final String name;
/*    */     private final Class<? extends blk> screen;
/*    */     
/*    */     CustomScreen(int index, String name, Class<? extends blk> screen) {
/* 26 */       this.index = index;
/* 27 */       this.name = name;
/* 28 */       this.screen = screen;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getIndex() {
/* 33 */       return this.index;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public String toString() {
/* 39 */       return I18n.get(this.name);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     blk getScreen(Macros macros, bib mc, GuiScreenEx parent) {
/*    */       try {
/* 46 */         Constructor<? extends blk> constructor = this.screen.getDeclaredConstructor(new Class[] { Macros.class, bib.class, GuiScreenEx.class });
/* 47 */         return constructor.newInstance(new Object[] { macros, mc, parent });
/*    */       }
/* 49 */       catch (Exception ex) {
/*    */         
/* 51 */         ex.printStackTrace();
/* 52 */         return null;
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   private final List<CustomScreen> customScreens = new ArrayList<>();
/*    */   
/*    */   public CustomScreenManager(Macros macros, bib mc) {
/* 63 */     this.macros = macros;
/* 64 */     this.mc = mc;
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerCustomScreen(String name, Class<? extends blk> screenClass) {
/* 69 */     this.customScreens.add(new CustomScreen(this.customScreens.size(), name, screenClass));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasCustomScreens() {
/* 74 */     return (this.customScreens.size() > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<CustomScreen> getCustomScreens() {
/* 79 */     return Collections.unmodifiableList(this.customScreens);
/*    */   }
/*    */ 
/*    */   
/*    */   public void displayCustomScreen(GuiScreenEx parent, int index) {
/* 84 */     if (index < 0 || index >= this.customScreens.size()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 89 */     blk screen = ((CustomScreen)this.customScreens.get(index)).getScreen(this.macros, this.mc, parent);
/* 90 */     if (screen != null)
/*    */     {
/* 92 */       this.mc.a(screen);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\hook\CustomScreenManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */