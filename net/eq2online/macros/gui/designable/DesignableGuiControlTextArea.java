/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Deque;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxTextAreaProperties;
/*     */ import net.eq2online.util.Colour;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Icon(u = 24, v = 88)
/*     */ public class DesignableGuiControlTextArea
/*     */   extends DesignableGuiControl
/*     */ {
/*     */   private static final int MESSAGE_LIMIT = 100;
/*     */   
/*     */   public class TextAreaMessage
/*     */   {
/*     */     public String message;
/*     */     public int updateCounter;
/*     */     
/*     */     public TextAreaMessage(String message) {
/*  33 */       this.message = message;
/*  34 */       this.updateCounter = 0;
/*     */     }
/*     */   }
/*     */   
/*  38 */   protected int messageLifeSpan = 200;
/*     */   
/*  40 */   protected Deque<TextAreaMessage> messages = new LinkedList<>();
/*     */   
/*  42 */   protected Object messageListLock = new Object();
/*     */   
/*     */   protected volatile boolean hasMessagesInQueue;
/*     */   
/*  46 */   protected int lastWidth = 100;
/*     */   
/*  48 */   protected int rowHeight = 9;
/*     */   
/*     */   protected int yPos;
/*     */   
/*  52 */   protected int alpha = 0;
/*     */ 
/*     */   
/*     */   protected DesignableGuiControlTextArea(Macros macros, bib mc, int id) {
/*  56 */     super(macros, mc, id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  62 */     super.initProperties();
/*     */     
/*  64 */     setProperty("colour", "FF00FF00");
/*  65 */     setProperty("lifespan", 200);
/*     */ 
/*     */     
/*  68 */     this.backColour &= 0xFFFFFF;
/*  69 */     this.padding = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBindable() {
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/*  81 */     setProperty("lifespan", this.messageLifeSpan);
/*  82 */     return (GuiDialogBoxControlProperties)new GuiDialogBoxTextAreaProperties(this.mc, parentScreen, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/*  88 */     if ("lifespan".equals(property)) setProperty(property, Math.min(Math.max(intValue, 10), 1200)); 
/*  89 */     if ("colour".equals(property))
/*     */     {
/*  91 */       setProperty(property, Colour.sanitiseColour(stringValue, -16711936));
/*     */     }
/*     */     
/*  94 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/* 100 */     this.foreColour = Colour.parseColour(getProperty("colour", "00FF00"), -16711936);
/* 101 */     this.messageLifeSpan = Math.min(Math.max(getProperty("lifespan", 200), 10), 1200);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean onlyTickWhenVisible() {
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(int tickNumber) {
/* 113 */     synchronized (this.messageListLock) {
/*     */       
/* 115 */       for (TextAreaMessage message : this.messages)
/*     */       {
/* 117 */         message.updateCounter++;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/* 125 */     if (!isVisible()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 130 */     this.lastWidth = boundingBox.width;
/*     */     
/* 132 */     GL.glPushMatrix();
/* 133 */     GL.glTranslatef(boundingBox.x, (boundingBox.y + boundingBox.height), 0.0F);
/*     */     
/* 135 */     GL.glDisableBlend();
/* 136 */     GL.glDisableLighting();
/* 137 */     GL.glDisableDepthTest();
/*     */     
/* 139 */     a(0, this.yPos + this.rowHeight - this.padding, boundingBox.width, 0, this.alpha << 24 | this.backColour);
/*     */     
/* 141 */     this.yPos = -this.rowHeight - this.padding;
/* 142 */     this.alpha = 0;
/*     */     
/* 144 */     synchronized (this.messageListLock) {
/*     */       
/* 146 */       for (Iterator<TextAreaMessage> iter = this.messages.iterator(); iter.hasNext(); ) {
/*     */         
/* 148 */         TextAreaMessage message = iter.next();
/*     */         
/* 150 */         if (message.updateCounter < this.messageLifeSpan) {
/*     */           
/* 152 */           if (this.yPos < -boundingBox.height + this.padding)
/*     */             break; 
/* 154 */           float fade = Math.min(Math.max((1.0F - message.updateCounter / this.messageLifeSpan) * 10.0F, 0.0F), 1.0F);
/* 155 */           this.alpha = Math.max(this.alpha, (int)(128.0F * fade * fade));
/*     */           
/* 157 */           if (this.alpha < 40)
/* 158 */             break;  GL.glDisableBlend();
/* 159 */           this.fontRenderer.a(message.message, this.padding, this.yPos, this.foreColour);
/* 160 */           this.yPos -= this.rowHeight;
/*     */           
/*     */           continue;
/*     */         } 
/* 164 */         iter.remove();
/*     */       } 
/*     */ 
/*     */       
/* 168 */       this.hasMessagesInQueue = (this.messages.size() > 0);
/*     */     } 
/*     */     
/* 171 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, int drawColour) {
/* 177 */     int xMid = boundingBox.x + boundingBox.width / 2;
/* 178 */     int yMid = boundingBox.y + boundingBox.height / 2;
/*     */     
/* 180 */     drawDoubleEndedArrowH(boundingBox.x, (boundingBox.x + boundingBox.width), yMid, 1.0F, 6.0F, -8355712);
/* 181 */     drawDoubleEndedArrowV(xMid, boundingBox.y, (boundingBox.y + boundingBox.height), 1.0F, 6.0F, -8355712);
/*     */     
/* 183 */     int nameSize = this.fontRenderer.a(getName()) / 2;
/*     */     
/* 185 */     a(xMid - nameSize - this.padding, yMid - 4 - this.padding, xMid + nameSize + this.padding, yMid + 4 + this.padding, 0xFF000000 | this.backColour);
/*     */ 
/*     */     
/* 188 */     this.fontRenderer.a(getName(), xMid - nameSize, yMid - 4, this.foreColour);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMessage(String text) {
/* 193 */     synchronized (this.messageListLock) {
/*     */       
/* 195 */       for (String textLine : this.fontRenderer.c(text, this.lastWidth)) {
/*     */         
/* 197 */         TextAreaMessage newLine = new TextAreaMessage(textLine);
/* 198 */         this.messages.addFirst(newLine);
/* 199 */         this.hasMessagesInQueue = true;
/*     */       } 
/*     */       
/* 202 */       for (; this.messages.size() > 100; this.messages.removeLast());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlTextArea.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */