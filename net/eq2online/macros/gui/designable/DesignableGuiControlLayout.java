/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxLayoutProperties;
/*     */ import net.eq2online.macros.gui.screens.GuiDesigner;
/*     */ import net.eq2online.macros.interfaces.IEditablePanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Icon(u = 36, v = 80)
/*     */ public class DesignableGuiControlLayout
/*     */   extends DesignableGuiControlAligned
/*     */ {
/*     */   private final LayoutManager layouts;
/*     */   private DesignableGuiLayout.ClickedControlInfo clickedControl;
/*  21 */   private int clickedTick = -100, currentTick = 0;
/*     */ 
/*     */   
/*     */   public DesignableGuiControlLayout(Macros macros, bib mc, int id) {
/*  25 */     super(macros, mc, id, "fill");
/*     */     
/*  27 */     this.layouts = macros.getLayoutManager();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/*  33 */     return (GuiDialogBoxControlProperties)new GuiDialogBoxLayoutProperties(this.mc, parentScreen, this, this.layouts);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  39 */     super.initProperties();
/*     */     
/*  41 */     setProperty("layout", "");
/*  42 */     setProperty("width", 0);
/*  43 */     setProperty("height", 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/*  49 */     if ("layout".equals(property)) {
/*     */       
/*  51 */       setProperty(property, stringValue);
/*     */     }
/*  53 */     else if ("width".equals(property) || "height".equals(property)) {
/*     */       
/*  55 */       setProperty(property, Math.min(Math.max(intValue, 0), 9999));
/*     */     } 
/*     */     
/*  58 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*     */   }
/*     */ 
/*     */   
/*     */   private DesignableGuiLayout getLayout() {
/*  63 */     String layoutName = getProperty("layout", "");
/*  64 */     if (this.layouts.layoutExists(layoutName))
/*     */     {
/*  66 */       return this.layouts.getLayout(layoutName);
/*     */     }
/*     */     
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private Rectangle getLayoutBoundingBox(Rectangle boundingBox) {
/*  74 */     String align = getProperty("align", this.defaultAlignment);
/*  75 */     if ("fill".equals(align))
/*     */     {
/*  77 */       return boundingBox;
/*     */     }
/*     */     
/*  80 */     int width = Math.max(getProperty("width", 0), 0);
/*  81 */     int height = Math.max(getProperty("height", 0), 0);
/*     */     
/*  83 */     int xPos = boundingBox.x;
/*  84 */     int yPos = boundingBox.y;
/*  85 */     int drawWidth = boundingBox.width;
/*  86 */     int drawHeight = boundingBox.height;
/*     */     
/*  88 */     if (width >= 16) {
/*     */       
/*  90 */       drawWidth = Math.min(width, boundingBox.width);
/*  91 */       if (drawWidth < boundingBox.width)
/*     */       {
/*  93 */         xPos = (int)getXPosition(boundingBox, drawWidth);
/*     */       }
/*     */     } 
/*     */     
/*  97 */     if (height >= 16) {
/*     */       
/*  99 */       drawHeight = Math.min(height, boundingBox.height);
/* 100 */       if (drawHeight < boundingBox.height)
/*     */       {
/* 102 */         yPos = (int)getYPosition(boundingBox, drawHeight);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 107 */     return new Rectangle(xPos, yPos, drawWidth, drawHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBindable() {
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(int tickNumber) {
/* 119 */     this.currentTick++;
/*     */     
/* 121 */     DesignableGuiLayout layout = getLayout();
/* 122 */     if (layout != null)
/*     */     {
/* 124 */       layout.onTick(tickNumber);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/* 131 */     super.update();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClickedEdit(int mouseX, int mouseY) {
/* 137 */     if (this.currentTick - this.clickedTick < 9) {
/*     */       
/* 139 */       DesignableGuiLayout layout = getLayout();
/* 140 */       if (layout != null)
/*     */       {
/* 142 */         this.mc.a((blk)new GuiDesigner(this.macros, this.mc, layout, this.mc.m, true));
/*     */       }
/*     */     } 
/*     */     
/* 146 */     this.clickedTick = this.currentTick;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleClick(Rectangle boundingBox, int mouseX, int mouseY, DesignableGuiControl.Listener listener) {
/* 152 */     DesignableGuiLayout layout = getLayout();
/* 153 */     if (layout != null) {
/*     */       
/* 155 */       this.clickedControl = layout.getControlAt(getLayoutBoundingBox(boundingBox), mouseX, mouseY, null);
/* 156 */       if (this.clickedControl != null)
/*     */       {
/* 158 */         return this.clickedControl.handleClick(mouseX, mouseY, listener);
/*     */       }
/*     */     } 
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleKeyTyped(char keyChar, int keyCode, DesignableGuiControl.Listener listener) {
/* 167 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseMove(Rectangle boundingBox, int mouseX, int mouseY) {
/* 173 */     if (this.clickedControl != null)
/*     */     {
/* 175 */       this.clickedControl.handleMouseMove(mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseReleased(Rectangle boundingBox, int mouseX, int mouseY) {
/* 182 */     if (this.clickedControl != null) {
/*     */       
/* 184 */       this.clickedControl.handleMouseReleased(mouseX, mouseY);
/* 185 */       this.clickedControl = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/* 192 */     DesignableGuiLayout layout = getLayout();
/* 193 */     if (layout != null)
/*     */     {
/* 195 */       layout.draw(getLayoutBoundingBox(boundingBox), mouseX, mouseY, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, int drawColour) {
/* 202 */     DesignableGuiLayout layout = getLayout();
/* 203 */     if (layout != null)
/*     */     {
/* 205 */       layout.draw(getLayoutBoundingBox(boundingBox), 0, 0, false, false, false, IEditablePanel.EditMode.NONE, null, null);
/*     */     }
/*     */     
/* 208 */     this.foreColour = -16711936;
/* 209 */     drawCentredText(boundingBox, getName(), -9);
/* 210 */     this.foreColour = (layout != null) ? -16711936 : -65536;
/* 211 */     drawCentredText(boundingBox, getProperty("layout", ""), 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawCentredText(Rectangle boundingBox, String text, int offset) {
/* 216 */     int xMid = boundingBox.x + boundingBox.width / 2;
/* 217 */     int yMid = boundingBox.y + boundingBox.height / 2;
/*     */     
/* 219 */     int textSize = this.fontRenderer.a(text) / 2;
/* 220 */     a(xMid - textSize - 1, yMid + offset - 1, xMid + textSize + 1, yMid + offset + 9, 0xFF000000 | this.backColour);
/* 221 */     this.fontRenderer.a(text, xMid - textSize, yMid + offset, this.foreColour);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */