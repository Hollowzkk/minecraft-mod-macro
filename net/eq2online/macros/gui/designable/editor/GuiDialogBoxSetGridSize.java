/*     */ package net.eq2online.macros.gui.designable.editor;
/*     */ 
/*     */ import bib;
/*     */ import bje;
/*     */ import blk;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiLabel;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.util.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiDialogBoxSetGridSize
/*     */   extends GuiDialogBox
/*     */ {
/*     */   private static final int ID_DELROW = 20;
/*     */   private static final int ID_ADDROW = 21;
/*     */   private static final int ID_DELCOLUMN = 22;
/*     */   private static final int ID_ADDCOLUMN = 23;
/*     */   private final DesignableGuiLayout layout;
/*     */   private final String autoText;
/*     */   private bje rows;
/*     */   private bje cols;
/*     */   private bje colWidth;
/*     */   private GuiLabel colWidthLabel;
/*     */   
/*     */   public GuiDialogBoxSetGridSize(bib minecraft, GuiScreenEx parentScreen, DesignableGuiLayout layout) {
/*  34 */     super(minecraft, (blk)parentScreen, 240, 115, I18n.get("grid.properties.title"));
/*     */     
/*  36 */     this.autoText = I18n.get("grid.colsize.auto");
/*     */     
/*  38 */     this.layout = layout;
/*  39 */     this.movable = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initDialog() {
/*  48 */     addControl((GuiControl)new GuiLabel(26, this.dialogX + 33, this.dialogY + 18, I18n.get("grid.properties.rows"), -22016));
/*  49 */     addControl((GuiControl)new GuiLabel(27, this.dialogX + 33, this.dialogY + 42, I18n.get("grid.properties.cols"), -22016));
/*  50 */     addControl((GuiControl)(this.colWidthLabel = new GuiLabel(27, this.dialogX + 33, this.dialogY + 66, I18n.get("grid.properties.colWidth"), -22016)));
/*     */ 
/*     */     
/*  53 */     addControl(new GuiControl(20, this.dialogX + 103, this.dialogY + 12, 22, 20, "", 8));
/*  54 */     addControl(new GuiControl(21, this.dialogX + 185, this.dialogY + 12, 22, 20, "", 9));
/*     */     
/*  56 */     addControl(new GuiControl(22, this.dialogX + 103, this.dialogY + 36, 22, 20, "", 8));
/*  57 */     addControl(new GuiControl(23, this.dialogX + 185, this.dialogY + 36, 22, 20, "", 9));
/*     */     
/*  59 */     removeControl(this.btnCancel);
/*     */     
/*  61 */     this.rows = new bje(0, this.q, this.dialogX + 135, this.dialogY + 14, 40, 16);
/*  62 */     this.cols = new bje(1, this.q, this.dialogX + 135, this.dialogY + 38, 40, 16);
/*  63 */     this.colWidth = new bje(2, this.q, this.dialogX + 135, this.dialogY + 62, 40, 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/*  72 */     this.rows.a(String.valueOf(this.layout.getRows()));
/*  73 */     this.rows.g();
/*     */     
/*  75 */     this.cols.a(String.valueOf(this.layout.getColumns()));
/*  76 */     this.cols.g();
/*     */     
/*  78 */     int editingColumn = this.layout.getSelectedColumn();
/*  79 */     if (editingColumn < 0) this.colWidth.b(false); 
/*  80 */     this.colWidthLabel.drawColour = (editingColumn > -1) ? -22016 : -8355712;
/*     */     
/*  82 */     this.colWidth.a(this.layout.getSelectedColumnWidthText());
/*  83 */     this.colWidth.g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawParentScreen(int mouseX, int mouseY, float partialTick) {
/*  93 */     if (this.parent != null) {
/*     */ 
/*     */       
/*  96 */       this.parent.drawScreenWithEnabledState(mouseX, mouseY, partialTick, false);
/*     */     }
/*     */     else {
/*     */       
/* 100 */       super.drawParentScreen(mouseX, mouseY, partialTick);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiControl control) {
/* 111 */     performAction(control.k);
/* 112 */     super.actionPerformed(control);
/*     */   }
/*     */ 
/*     */   
/*     */   private void performAction(int action) {
/* 117 */     if (action == 20) {
/*     */       
/* 119 */       this.layout.removeRow();
/*     */     }
/* 121 */     else if (action == 21) {
/*     */       
/* 123 */       this.layout.addRow();
/*     */     }
/* 125 */     else if (action == 22) {
/*     */       
/* 127 */       this.layout.removeColumn();
/*     */     }
/* 129 */     else if (action == 23) {
/*     */       
/* 131 */       this.layout.addColumn();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/* 142 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */     
/* 144 */     this.colWidth.a(mouseX, mouseY, button);
/*     */     
/* 146 */     if (button == 0 && (mouseX < this.dialogX || mouseY < this.dialogY || mouseX > this.dialogX + this.dialogWidth || mouseY > this.dialogY + this.dialogHeight)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       this.layout.selectColumn();
/* 152 */       this.colWidth.b(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 162 */     super.dialogKeyTyped(keyChar, keyCode);
/*     */     
/* 164 */     if (this.colWidth.m())
/*     */     {
/* 166 */       if (keyCode == 14 || "0123456789".indexOf(keyChar) > -1) {
/*     */         
/* 168 */         if (this.colWidth.b().equals(this.autoText))
/*     */         {
/* 170 */           this.colWidth.a("");
/*     */         }
/*     */         
/* 173 */         this.colWidth.a(keyChar, keyCode);
/* 174 */         this.layout.setSelectedColumnWidth(Util.parsePositiveInt(this.colWidth.b(), 0));
/*     */         
/* 176 */         if (this.colWidth.b().equals(""))
/*     */         {
/* 178 */           this.colWidth.a(this.autoText);
/*     */         }
/*     */       } 
/*     */     }
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
/* 192 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\GuiDialogBoxSetGridSize.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */