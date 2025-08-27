/*    */ package net.eq2online.macros.gui;
/*    */ 
/*    */ import cdt;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import javax.imageio.ImageIO;
/*    */ import net.eq2online.macros.core.CustomResourcePack;
/*    */ import nf;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IconResourcePack
/*    */   extends CustomResourcePack
/*    */ {
/*    */   public IconResourcePack(File path, String domain) {
/* 20 */     super(path, domain, "textures", "custom", ".png");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream a(nf location) throws IOException {
/* 26 */     InputStream is = super.a(location);
/* 27 */     if (is == null)
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 34 */       return fixAspectRatio(is);
/*    */     }
/* 36 */     catch (Exception ex) {
/*    */       
/* 38 */       ex.printStackTrace();
/* 39 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private InputStream fixAspectRatio(InputStream is) throws IOException {
/* 45 */     BufferedImage image = cdt.a(is);
/* 46 */     if (image == null || image.getWidth() > 1024 || image.getHeight() > 1024)
/*    */     {
/* 48 */       return null;
/*    */     }
/* 50 */     if (image.getWidth() > image.getHeight()) {
/*    */       
/* 52 */       BufferedImage square = new BufferedImage(image.getWidth(), image.getWidth(), 2);
/* 53 */       int space = (image.getWidth() - image.getHeight()) / 2;
/* 54 */       square.getGraphics().drawImage(image, 0, space, null);
/* 55 */       image = square;
/*    */     }
/* 57 */     else if (image.getHeight() > image.getWidth()) {
/*    */       
/* 59 */       BufferedImage square = new BufferedImage(image.getHeight(), image.getHeight(), 2);
/* 60 */       int space = (image.getHeight() - image.getWidth()) / 2;
/* 61 */       square.getGraphics().drawImage(image, space, 0, null);
/* 62 */       image = square;
/*    */     } 
/*    */     
/* 65 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 66 */     ImageIO.write(image, "png", os);
/* 67 */     return new ByteArrayInputStream(os.toByteArray());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String b() {
/* 73 */     return "Macros.CustomIcons";
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\IconResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */