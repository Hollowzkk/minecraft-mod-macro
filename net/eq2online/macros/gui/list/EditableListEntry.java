/*     */ package net.eq2online.macros.gui.list;
/*     */ 
/*     */ import aip;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import java.awt.Rectangle;
/*     */ import net.eq2online.macros.interfaces.IDecoratedListEntry;
/*     */ import net.eq2online.macros.interfaces.IDraggable;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.rendering.FontRendererLegacy;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EditableListEntry<T>
/*     */   extends ListEntry<T>
/*     */   implements IDecoratedListEntry<T>, IDraggable<T>
/*     */ {
/*  19 */   protected IListEntry.CustomAction customAction = IListEntry.CustomAction.NONE;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean allowEdit = true;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean allowDelete = true;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EditableListEntry(int id, String text, T data, boolean allowEdit, boolean allowDelete) {
/*  33 */     super(id, text, data);
/*     */     
/*  35 */     this.allowEdit = allowEdit;
/*  36 */     this.allowDelete = allowDelete;
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
/*     */   public EditableListEntry(int id, int iconID, String text, nf iconTexture) {
/*  49 */     super(id, text, (T)text, true, iconTexture, iconID);
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
/*     */   public EditableListEntry(int id, aip displayItem, String text) {
/*  61 */     super(id, text, (T)text, displayItem);
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
/*     */   public EditableListEntry(int id, int iconID, String text, nf iconTexture, T data) {
/*  73 */     super(id, text, data, true, iconTexture, iconID);
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
/*     */   public EditableListEntry(int id, aip displayItem, String text, T data) {
/*  85 */     super(id, text, data, displayItem);
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
/*     */   public EditableListEntry(int id, String text, T data, nf iconTexture, Icon icon) {
/*  97 */     super(id, text, data, iconTexture, icon);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 103 */     return (this.displayName != null && this.displayName.length() > 0) ? this.displayName : getText();
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
/*     */   public void decorate(boolean iconEnabled, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height, int updateCounter) {
/* 121 */     FontRendererLegacy fontRendererLegacy = FontRendererLegacy.getInstance();
/*     */     
/* 123 */     if (this.allowDelete)
/*     */     {
/* 125 */       fontRendererLegacy.a("x", xPosition + width - 8, yPosition, -65536);
/*     */     }
/*     */     
/* 128 */     if (this.allowEdit) {
/*     */       
/* 130 */       fontRendererLegacy.a("...", xPosition + width - 8, yPosition + 11, -6250336);
/* 131 */       fontRendererLegacy.a("/", xPosition + width - 8, yPosition + 10, -4145152);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(boolean iconEnabled, int mouseX, int mouseY, Rectangle rect) {
/* 138 */     return mousePressed(iconEnabled, mouseX, mouseY, rect.x, rect.y, rect.width, rect.height);
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
/*     */   public boolean mousePressed(boolean iconEnabled, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height) {
/* 155 */     if (mouseX > xPosition + width - 8 && mouseX < xPosition + width) {
/*     */       
/* 157 */       if (mouseY > yPosition && mouseY < yPosition + 8 && this.allowDelete) {
/*     */         
/* 159 */         this.customAction = IListEntry.CustomAction.DELETE;
/* 160 */         return true;
/*     */       } 
/* 162 */       if (mouseY > yPosition + 10 && mouseY < yPosition + 18 && this.allowEdit) {
/*     */         
/* 164 */         this.customAction = IListEntry.CustomAction.EDIT;
/* 165 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     this.customAction = IListEntry.CustomAction.NONE;
/* 170 */     return false;
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
/*     */   public void mouseReleased(int mouseX, int mouseY) {}
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
/*     */   public IListEntry.CustomAction getCustomAction(boolean clear) {
/* 195 */     IListEntry.CustomAction pendingAction = this.customAction;
/* 196 */     if (clear)
/*     */     {
/* 198 */       this.customAction = IListEntry.CustomAction.NONE;
/*     */     }
/*     */     
/* 201 */     return pendingAction;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDraggable() {
/* 207 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\list\EditableListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */