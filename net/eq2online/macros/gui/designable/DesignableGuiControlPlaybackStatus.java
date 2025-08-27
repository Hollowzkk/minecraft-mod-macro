/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.gl.GLClippingPlanes;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.GuiPlaybackStatus;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxPlaybackStatusProperties;
/*     */ import net.eq2online.util.Colour;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Icon(u = 48, v = 80)
/*     */ public class DesignableGuiControlPlaybackStatus
/*     */   extends DesignableGuiControl
/*     */ {
/*     */   private final GuiPlaybackStatus statusGui;
/*     */   private boolean drawBorder = false;
/*     */   private boolean drawBorderInteractive = true;
/*     */   private boolean clicked;
/*     */   
/*     */   public DesignableGuiControlPlaybackStatus(Macros macros, bib mc, int id) {
/*  33 */     super(macros, mc, id);
/*  34 */     this.statusGui = macros.getPlaybackStatus();
/*  35 */     this.statusGui.setOverlayMode(false);
/*  36 */     this.padding = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(Document xml, Element controlNode, boolean export) {
/*  42 */     this.statusGui.setOverlayMode(false);
/*  43 */     super.save(xml, controlNode, export);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBindable() {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean executeOnClick() {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  61 */     super.initProperties();
/*     */     
/*  63 */     setProperty("colour", "FF00FF00");
/*  64 */     setProperty("background", "B0000000");
/*  65 */     setProperty("border", "interactive");
/*  66 */     setProperty("clip", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(int tickNumber) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/*  77 */     this.foreColour = Colour.parseColour(getProperty("colour", "FF00FF00"), -16711936);
/*  78 */     this.backColour = Colour.parseColour(getProperty("background", "B0000000"), -1442840576);
/*     */     
/*  80 */     String border = getProperty("border", "interactive");
/*  81 */     this.drawBorder = "always".equals(border);
/*  82 */     this.drawBorderInteractive = "interactive".equals(border);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/*  88 */     this.statusGui.setOverlayMode(false);
/*  89 */     boolean interactive = parent.isInteractive();
/*  90 */     boolean clip = getProperty("clip", true);
/*  91 */     if (this.drawBorder || (this.drawBorderInteractive && interactive)) {
/*     */       
/*  93 */       this.e = -this.zIndex;
/*  94 */       if (this.backColour != 0) {
/*     */         
/*  96 */         GL.glAlphaFunc(516, 0.0F);
/*  97 */         a(boundingBox.x, boundingBox.y, boundingBox.x + boundingBox.width, boundingBox.y + boundingBox.height, this.backColour);
/*  98 */         GL.glAlphaFunc(516, 0.1F);
/*     */       } 
/*     */       
/* 101 */       drawRectOutline(boundingBox, this.foreColour, 1.0F);
/*     */ 
/*     */       
/* 104 */       boundingBox = new Rectangle(boundingBox.x + this.padding, boundingBox.y + this.padding, Math.max(4, boundingBox.width - this.padding * 2), Math.max(4, boundingBox.height - this.padding * 2));
/*     */     } 
/*     */     
/* 107 */     if (clip) {
/*     */       
/* 109 */       GLClippingPlanes.glEnableClipping(-1, boundingBox.x + boundingBox.width, -1, boundingBox.y + boundingBox.height);
/*     */     }
/*     */     else {
/*     */       
/* 113 */       boundingBox.height = 2048;
/*     */     } 
/*     */     
/* 116 */     this.statusGui.draw(boundingBox, mouseX, mouseY, interactive);
/*     */     
/* 118 */     if (clip)
/*     */     {
/* 120 */       GLClippingPlanes.glDisableClipping();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, int drawColour) {
/* 127 */     String text = I18n.get("playback.status.label");
/* 128 */     int textSize = this.fontRenderer.a(text) / 2;
/*     */     
/* 130 */     int xMid = boundingBox.x + Math.min(16 + textSize, boundingBox.width / 2);
/* 131 */     int yMid = boundingBox.y + Math.min(16, boundingBox.height / 2);
/*     */     
/* 133 */     drawDoubleEndedArrowH(boundingBox.x, (boundingBox.x + boundingBox.width), yMid, 1.0F, 6.0F, -8355712);
/* 134 */     drawDoubleEndedArrowV(xMid, boundingBox.y, (boundingBox.y + boundingBox.height), 1.0F, 6.0F, -8355712);
/*     */     
/* 136 */     a(xMid - textSize - this.padding, yMid - 4 - this.padding, xMid + textSize + this.padding, yMid + 4 + this.padding, 0xFF000000 | this.backColour);
/*     */ 
/*     */     
/* 139 */     this.fontRenderer.a(text, xMid - textSize, yMid - 4, this.foreColour);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleClick(Rectangle boundingBox, int mouseX, int mouseY, DesignableGuiControl.Listener listener) {
/* 145 */     this.clicked = true;
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseMove(Rectangle boundingBox, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseReleased(Rectangle boundingBox, int mouseX, int mouseY) {
/* 157 */     if (this.clicked)
/*     */     {
/* 159 */       this.statusGui.mouseClick(boundingBox, mouseX, mouseY);
/*     */     }
/* 161 */     this.clicked = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/* 167 */     return (GuiDialogBoxControlProperties)new GuiDialogBoxPlaybackStatusProperties(this.mc, parentScreen, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlPlaybackStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */