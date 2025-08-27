/*     */ package net.eq2online.macros.gui.designable.editor.browse;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.core.CustomResourcePack;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxResource;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiDialogBoxBrowseResources
/*     */   extends GuiDialogBox
/*     */ {
/*     */   private final CustomResourcePack resources;
/*     */   private GuiListBoxResource listBox;
/*     */   
/*     */   public GuiDialogBoxBrowseResources(bib minecraft, blk parentScreen, String windowTitle, CustomResourcePack resources) {
/*  22 */     super(minecraft, parentScreen, 260, 174, windowTitle);
/*  23 */     this.resources = resources;
/*  24 */     this.movable = true;
/*  25 */     this.listBox = new GuiListBoxResource(this.j, 1, this.dialogX + 4, this.dialogY + 4, 252, 145, this.resources);
/*  26 */     this.listBox.refresh();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/*  32 */     IListEntry<String> selectedItem = this.listBox.getSelectedItem();
/*  33 */     return (selectedItem != null) ? selectedItem.getData() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initDialog() {
/*  39 */     this.listBox.setSizeAndPosition(this.dialogX + 4, this.dialogY + 4, 252, 145);
/*  40 */     addControl((GuiControl)this.listBox);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiControl control) {
/*  46 */     if (control == this.listBox)
/*     */     {
/*  48 */       if (this.listBox.isDoubleClicked(true))
/*     */       {
/*  50 */         dialogSubmit();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/*  58 */     GuiControlEx.HandledState action = this.listBox.listBoxKeyTyped(keyChar, keyCode);
/*  59 */     if (action == GuiControlEx.HandledState.ACTION_PERFORMED) {
/*     */       
/*  61 */       actionPerformed((GuiControl)this.listBox);
/*     */     }
/*  63 */     else if (action == GuiControlEx.HandledState.HANDLED) {
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*  68 */     super.dialogKeyTyped(keyChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/*  74 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) throws IOException {
/*  96 */     mouseWheelDelta /= 120;
/*     */     
/*  98 */     while (mouseWheelDelta > 0) {
/*     */       
/* 100 */       this.listBox.up();
/* 101 */       actionPerformed((GuiControl)this.listBox);
/* 102 */       mouseWheelDelta--;
/*     */     } 
/*     */     
/* 105 */     while (mouseWheelDelta < 0) {
/*     */       
/* 107 */       this.listBox.down();
/* 108 */       actionPerformed((GuiControl)this.listBox);
/* 109 */       mouseWheelDelta++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\browse\GuiDialogBoxBrowseResources.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */