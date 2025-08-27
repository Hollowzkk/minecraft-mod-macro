/*    */ package net.eq2online.macros.gui.list;
/*    */ 
/*    */ import bib;
/*    */ import cdg;
/*    */ import cdr;
/*    */ import ceu;
/*    */ import com.mumfrey.liteloader.gl.GL;
/*    */ import java.io.IOException;
/*    */ import nf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourcePackListEntry
/*    */   extends ListEntry<ceu.a>
/*    */ {
/*    */   private nf defaultTexturePackIcon;
/*    */   private ceu resourcePackRepository;
/*    */   private ceu.a texturePack;
/*    */   
/*    */   public ResourcePackListEntry(bib minecraft, int id, String text, ceu.a texturePack) {
/* 23 */     super(id, text, texturePack);
/*    */     
/* 25 */     this.texturePack = texturePack;
/* 26 */     this.resourcePackRepository = minecraft.P();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean renderIcon(bib minecraft, int xPosition, int yPosition) {
/* 32 */     cdr textureManager = minecraft.N();
/*    */     
/* 34 */     if (this.texturePack == null) {
/*    */       
/* 36 */       if (this.defaultTexturePackIcon == null) {
/*    */         
/*    */         try {
/*    */           
/* 40 */           this.defaultTexturePackIcon = textureManager.a("texturepackicon", new cdg(this.resourcePackRepository.a
/* 41 */                 .a()));
/*    */         }
/* 43 */         catch (IOException iOException) {}
/*    */       }
/*    */       
/* 46 */       textureManager.a(this.defaultTexturePackIcon);
/*    */     }
/*    */     else {
/*    */       
/* 50 */       this.texturePack.a(textureManager);
/*    */     } 
/*    */     
/* 53 */     GL.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 54 */     ListEntry.renderer.drawTexturedModalRectF(xPosition, yPosition, xPosition + 16, yPosition + 16, 0.0F, 0.0F, 1.0F, 1.0F);
/*    */     
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\list\ResourcePackListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */