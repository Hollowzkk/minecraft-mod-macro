/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import bib;
/*    */ import bje;
/*    */ import blk;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.gui.GuiControl;
/*    */ import net.eq2online.macros.gui.GuiDialogBox;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ import net.eq2online.macros.gui.controls.GuiLabel;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ import net.eq2online.macros.gui.designable.LayoutManager;
/*    */ import net.eq2online.macros.gui.designable.editor.browse.GuiDialogBoxBrowseLayouts;
/*    */ import net.eq2online.macros.interfaces.IHighlighter;
/*    */ import net.eq2online.util.Util;
/*    */ 
/*    */ public class GuiDialogBoxLayoutProperties
/*    */   extends GuiDialogBoxAlignableProperties
/*    */   implements IHighlighter
/*    */ {
/*    */   private final LayoutManager layouts;
/*    */   private bje txtLayout;
/*    */   
/*    */   public GuiDialogBoxLayoutProperties(bib minecraft, GuiScreenEx parentScreen, DesignableGuiControl control, LayoutManager layouts) {
/* 25 */     super(minecraft, parentScreen, control);
/* 26 */     this.layouts = layouts;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 32 */     super.initDialog();
/*    */     
/* 34 */     this.txtLayout = addTextFieldWithButton(I18n.get("control.properties.layout.name"), "layout", I18n.get("gui.elipsis"), "browse", false);
/* 35 */     addAlignmentDropdown(I18n.get("control.properties.layout.align"), true);
/* 36 */     addTextField(I18n.get("control.properties.layout.width"), "width", true, this, 32);
/* 37 */     addTextField(I18n.get("control.properties.layout.height"), "height", true, this, 32);
/* 38 */     addControl((GuiControl)new GuiLabel(-999, this.dialogX + 122, getControlTop() - 32, 
/* 39 */           I18n.get("control.properties.layout.help1"), -22016));
/* 40 */     addControl((GuiControl)new GuiLabel(-999, this.dialogX + 122, getControlTop() - 20, 
/* 41 */           I18n.get("control.properties.layout.help2", new Object[] { Integer.valueOf(16) }), -22016));
/*    */     
/* 43 */     this.txtName.b(false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDialogClosed(GuiDialogBox dialog) {
/* 49 */     super.onDialogClosed(dialog);
/*    */     
/* 51 */     if (dialog.getResult() == GuiDialogBox.DialogResult.OK) {
/*    */       
/* 53 */       String result = (String)dialog.getValue();
/*    */       
/* 55 */       if (result != null)
/*    */       {
/* 57 */         this.txtLayout.a(result);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiControl control) {
/* 65 */     if ("browse".equals(control.tag)) {
/*    */       
/* 67 */       this.j.a((blk)new GuiDialogBoxBrowseLayouts(this.j, (blk)this, I18n.get("resources.browse.title"), this.layouts, this.control
/* 68 */             .getProperty("layout", "")));
/*    */       
/*    */       return;
/*    */     } 
/* 72 */     super.actionPerformed(control);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float partialTicks, boolean enabled) {
/* 78 */     a(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String generateHighlightMask(String text) {
/* 84 */     return Util.stringToHighlightMask(highlight(text));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String highlight(String text) {
/* 90 */     int value = GuiDialogBoxControlProperties.tryParseInt(text, 0);
/* 91 */     if (value > 0 && value < 16)
/*    */     {
/* 93 */       return "§4" + text;
/*    */     }
/* 95 */     return text;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxLayoutProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */