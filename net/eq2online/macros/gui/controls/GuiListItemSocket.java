/*     */ package net.eq2online.macros.gui.controls;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Point;
/*     */ import java.security.InvalidParameterException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.interfaces.IDecoratedListEntry;
/*     */ import net.eq2online.macros.interfaces.IDragDrop;
/*     */ import net.eq2online.macros.interfaces.IInteractiveListEntry;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IRenderedListEntry;
/*     */ import net.eq2online.macros.interfaces.ISocketListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiListItemSocket<T>
/*     */   extends GuiControlEx
/*     */   implements IDragDrop<T>
/*     */ {
/*  24 */   protected List<IDragDrop<T>> dragTargets = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   protected IListEntry<T> dragItem = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   protected Point dragOffset = new Point();
/*     */   
/*     */   protected IListEntry<T> item;
/*     */   
/*     */   private int iconOffset;
/*     */   
/*     */   private int iconSpacing;
/*     */   
/*     */   private int textOffset;
/*     */   
/*  44 */   public int backColour = -2146562560;
/*     */ 
/*     */   
/*     */   private ISocketListener<T> socketListener;
/*     */   
/*     */   private IInteractiveListEntry<T> mouseDownObject;
/*     */   
/*     */   private int timer;
/*     */ 
/*     */   
/*     */   public GuiListItemSocket(bib minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight, String displayText, ISocketListener<T> socketListener) {
/*  55 */     super(minecraft, controlId, xPos, yPos, controlWidth, controlHeight, displayText);
/*     */     
/*  57 */     this.socketListener = socketListener;
/*     */     
/*  59 */     this.textOffset = (controlHeight - 8) / 2;
/*  60 */     this.iconOffset = (controlHeight - 16) / 2;
/*  61 */     this.iconSpacing = 20;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidDragTarget() {
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidDragSource() {
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDragTarget(IDragDrop<T> target) {
/*  84 */     addDragTarget(target, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDragTarget(IDragDrop<T> target, boolean mutual) {
/*  97 */     if (target != null && target.isValidDragTarget()) {
/*     */       
/*  99 */       if (!this.dragTargets.contains(target))
/*     */       {
/* 101 */         this.dragTargets.add(target);
/*     */       }
/*     */       
/* 104 */       if (mutual && target.isValidDragSource())
/*     */       {
/* 106 */         target.addDragTarget(this, false);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 111 */       throw new InvalidParameterException("Target control is not a valid drag target");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeDragTarget(IDragDrop<T> target) {
/* 123 */     removeDragTarget(target, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeDragTarget(IDragDrop<T> target, boolean mutual) {
/* 136 */     if (this.dragTargets.contains(target))
/*     */     {
/* 138 */       this.dragTargets.remove(target);
/*     */     }
/*     */     
/* 141 */     if (mutual)
/*     */     {
/* 143 */       target.removeDragTarget(this, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dragDrop(IDragDrop<T> source, IListEntry<T> object, int mouseX, int mouseY) {
/* 159 */     if (this.l && source != this && mouseX >= this.h && mouseY >= this.i && mouseX < this.h + this.f && mouseY < this.i + this.g)
/*     */     {
/* 161 */       setItem(object);
/*     */     }
/*     */     
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawControl(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/* 170 */     if (!this.m) {
/*     */       return;
/*     */     }
/*     */     
/* 174 */     GL.glPushMatrix();
/* 175 */     GL.glTranslatef(0.0F, 0.0F, 100.0F);
/* 176 */     GL.glEnableDepthTest();
/* 177 */     a(this.h, this.i, this.h + this.f, this.i + this.g, this.backColour);
/*     */     
/* 179 */     GL.glTranslatef(0.0F, 0.0F, -50.0F);
/* 180 */     GL.glEnableDepthTest();
/* 181 */     a(this.h - 1, this.i - 1, this.h + this.f + 1, this.i + this.g + 1, -1);
/*     */     
/* 183 */     GL.glTranslatef(0.0F, 0.0F, 100.0F);
/* 184 */     if (this.item != null) {
/*     */       
/* 186 */       drawItem(this.item, this.mc, mouseX, mouseY, this.h, this.i, this.f, this.g, -1);
/*     */     }
/*     */     else {
/*     */       
/* 190 */       c(this.mc.k, this.j, this.h + 4, this.i + this.g / 2 - 4, -8355712);
/*     */     } 
/*     */     
/* 193 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawItem(IListEntry<T> item, bib minecraft, int mouseX, int mouseY, int itemX, int itemY, int itemWidth, int itemHeight, int itemTextColour) {
/* 212 */     if (item instanceof IRenderedListEntry) {
/*     */       
/* 214 */       ((IRenderedListEntry)item).render(true, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, this.updateCounter);
/*     */     }
/*     */     else {
/*     */       
/* 218 */       if (!item.renderIcon(minecraft, itemX + this.iconOffset, itemY + this.iconOffset) && item.hasIcon() && item.getIcon() != null) {
/*     */         
/* 220 */         a(itemX + this.iconOffset, itemY + this.iconOffset, itemX + this.iconOffset + 16, itemY + this.iconOffset + 16, 1090519039);
/* 221 */         drawIcon(item.getIconTexture(), item.getIcon(), itemX + this.iconOffset, itemY + this.iconOffset, itemX + this.iconOffset + 16, itemY + this.iconOffset + 16);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 226 */       this.renderer.drawStringWithEllipsis("" + item.getDisplayName(), itemX + this.iconSpacing + 4, itemY + this.textOffset, this.f - 32 - this.iconSpacing, itemTextColour);
/*     */ 
/*     */ 
/*     */       
/* 230 */       if (item instanceof IDecoratedListEntry)
/*     */       {
/* 232 */         ((IDecoratedListEntry)item).decorate(true, mouseX, mouseY, itemX, itemY, itemWidth, itemHeight, this.updateCounter);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IListEntry<T> getItem() {
/* 239 */     return this.item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(IListEntry<T> item) {
/* 244 */     IListEntry<T> oldItem = this.item;
/* 245 */     this.item = item;
/*     */     
/* 247 */     if (this.socketListener != null)
/*     */     {
/* 249 */       if (this.item == null && oldItem != null) {
/*     */         
/* 251 */         this.socketListener.onSocketCleared(this);
/*     */       }
/* 253 */       else if (this.item != null && this.item != oldItem) {
/*     */         
/* 255 */         this.socketListener.onSocketChanged(this, this.item);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean b(bib minecraft, int mouseX, int mouseY) {
/* 263 */     if (super.b(minecraft, mouseX, mouseY)) {
/*     */       
/* 265 */       if (this.item != null) {
/*     */ 
/*     */         
/* 268 */         if (this.item instanceof IInteractiveListEntry) {
/*     */           
/* 270 */           this.mouseDownObject = (IInteractiveListEntry<T>)this.item;
/* 271 */           this.actionPerformed = this.mouseDownObject.mousePressed(true, mouseX, mouseY, this.h, this.i, this.f, this.g);
/*     */         } 
/*     */         
/* 274 */         if (this.updateCounter - this.timer < 9) {
/*     */           
/* 276 */           this.actionPerformed = true;
/* 277 */           this.doubleClicked = true;
/*     */         } 
/*     */         
/* 280 */         this.timer = this.updateCounter;
/*     */       } 
/*     */       
/* 283 */       return true;
/*     */     } 
/*     */     
/* 286 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY) {
/* 292 */     if (this.mouseDownObject != null) {
/*     */       
/* 294 */       this.mouseDownObject.mouseReleased(mouseX, mouseY);
/* 295 */       this.mouseDownObject = null;
/*     */     } 
/*     */     
/* 298 */     if (this.socketListener != null && isMouseOver(null, mouseX, mouseY)) {
/*     */       
/* 300 */       this.socketListener.onSocketClicked(this, this.actionPerformed);
/* 301 */       this.actionPerformed = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\GuiListItemSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */