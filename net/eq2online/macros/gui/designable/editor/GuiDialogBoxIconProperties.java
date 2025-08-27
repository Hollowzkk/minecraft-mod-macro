/*    */ package net.eq2online.macros.gui.designable.editor;
/*    */ 
/*    */ import bib;
/*    */ import bje;
/*    */ import blk;
/*    */ import net.eq2online.macros.compatibility.I18n;
/*    */ import net.eq2online.macros.core.CustomResourcePack;
/*    */ import net.eq2online.macros.gui.GuiControl;
/*    */ import net.eq2online.macros.gui.GuiDialogBox;
/*    */ import net.eq2online.macros.gui.GuiScreenEx;
/*    */ import net.eq2online.macros.gui.IconResourcePack;
/*    */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*    */ import net.eq2online.macros.gui.designable.editor.browse.GuiDialogBoxBrowseResources;
/*    */ 
/*    */ public class GuiDialogBoxIconProperties
/*    */   extends GuiDialogBoxAlignableProperties {
/*    */   private final IconResourcePack iconResources;
/*    */   private bje txtIcon;
/*    */   
/*    */   public GuiDialogBoxIconProperties(bib minecraft, GuiScreenEx parentScreen, DesignableGuiControl control, IconResourcePack iconResources) {
/* 21 */     super(minecraft, parentScreen, control);
/* 22 */     this.iconResources = iconResources;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initDialog() {
/* 28 */     super.initDialog();
/*    */     
/* 30 */     this.txtIcon = addTextFieldWithButton(I18n.get("control.properties.icon.icon"), "icon", I18n.get("gui.elipsis"), "browse", false);
/* 31 */     addTextField(I18n.get("control.properties.icon.durability"), "durability", false);
/* 32 */     addAlignmentDropdown(I18n.get("control.properties.icon.align"), false);
/* 33 */     addTextField(I18n.get("control.properties.icon.scale"), "scale", true, 32);
/* 34 */     addColourButton(I18n.get("control.properties.backcolour"), "background", 16777216);
/*    */     
/* 36 */     this.txtIcon.b(true);
/* 37 */     this.txtName.b(false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDialogClosed(GuiDialogBox dialog) {
/* 43 */     super.onDialogClosed(dialog);
/*    */     
/* 45 */     if (dialog.getResult() == GuiDialogBox.DialogResult.OK) {
/*    */       
/* 47 */       String result = (String)dialog.getValue();
/*    */       
/* 49 */       if (result != null)
/*    */       {
/* 51 */         this.txtIcon.a(result);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiControl control) {
/* 59 */     if ("browse".equals(control.tag)) {
/*    */       
/* 61 */       this.j.a((blk)new GuiDialogBoxBrowseResources(this.j, (blk)this, I18n.get("resources.browse.title"), (CustomResourcePack)this.iconResources));
/*    */       
/*    */       return;
/*    */     } 
/* 65 */     super.actionPerformed(control);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float partialTicks, boolean enabled) {
/* 71 */     a(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxIconProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */