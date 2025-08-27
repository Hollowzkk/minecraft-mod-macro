/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import bib;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ import net.eq2online.macros.gui.controls.GuiDropDownList;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ 
/*    */ public abstract class GuiDialogBoxAlignableProperties
/*    */   extends GuiDialogBoxControlProperties
/*    */ {
/*    */   public GuiDialogBoxAlignableProperties(bib minecraft, GuiScreenEx parentScreen, DesignableGuiControl control) {
/* 13 */     super(minecraft, parentScreen, control);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addAlignmentDropdown(String label, boolean includeFill) {
/* 18 */     GuiDropDownList.GuiDropDownListControl alignment = addDropDown(label, "align", "Select");
/*    */     
/* 20 */     if (includeFill)
/*    */     {
/* 22 */       alignment.addItem("fill", I18n.get("control.properties.label.align.fill"));
/*    */     }
/*    */     
/* 25 */     alignment.addItem("top left", I18n.get("control.properties.label.align.topleft"));
/* 26 */     alignment.addItem("top centre", I18n.get("control.properties.label.align.topcentre"));
/* 27 */     alignment.addItem("top right", I18n.get("control.properties.label.align.topright"));
/* 28 */     alignment.addItem("middle left", I18n.get("control.properties.label.align.middleleft"));
/* 29 */     alignment.addItem("middle centre", I18n.get("control.properties.label.align.middlecentre"));
/* 30 */     alignment.addItem("middle right", I18n.get("control.properties.label.align.middleright"));
/* 31 */     alignment.addItem("bottom left", I18n.get("control.properties.label.align.bottomleft"));
/* 32 */     alignment.addItem("bottom centre", I18n.get("control.properties.label.align.bottomcentre"));
/* 33 */     alignment.addItem("bottom right", I18n.get("control.properties.label.align.bottomright"));
/*    */     
/* 35 */     alignment.selectItemByTag(this.control.getProperty("align", includeFill ? "fill" : "top left"));
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxAlignableProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */