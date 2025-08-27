/*     */ package net.eq2online.macros.gui.thumbnail;
/*     */ 
/*     */ import bib;
/*     */ import cdg;
/*     */ import cdr;
/*     */ import cdt;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.nio.ByteBuffer;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.util.Game;
/*     */ import nf;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Thumbnailer
/*     */ {
/*  29 */   private static int BYTES_PER_PIXEL = 3;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Macros macros;
/*     */ 
/*     */   
/*     */   private final bib minecraft;
/*     */ 
/*     */   
/*     */   private final nf sourceResource;
/*     */ 
/*     */   
/*     */   private final nf dynamicResource;
/*     */ 
/*     */   
/*     */   private final cdg texture;
/*     */ 
/*     */   
/*     */   private final int thumbnailSize;
/*     */ 
/*     */   
/*     */   private final int[] textureData;
/*     */ 
/*     */   
/*  54 */   private int editingIconIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Thumbnailer(Macros macros, bib minecraft, String dynamicResourceName, nf imageResource, int thumbnailSize) {
/*  64 */     this.macros = macros;
/*  65 */     this.minecraft = minecraft;
/*     */     
/*  67 */     this.sourceResource = imageResource;
/*  68 */     this.thumbnailSize = thumbnailSize;
/*     */     
/*  70 */     BufferedImage bufferedImage = null;
/*     */     
/*     */     try {
/*  73 */       bufferedImage = ImageIO.read(this.minecraft.O().a(this.sourceResource).b());
/*     */     }
/*  75 */     catch (Exception ex) {
/*     */       
/*  77 */       ex.printStackTrace();
/*     */     } 
/*     */     
/*  80 */     this.texture = new cdg(bufferedImage);
/*  81 */     this.textureData = this.texture.e();
/*     */     
/*  83 */     cdr textureManager = minecraft.N();
/*  84 */     this.dynamicResource = textureManager.a(dynamicResourceName, this.texture);
/*     */   }
/*     */ 
/*     */   
/*     */   public nf getDynamicResource() {
/*  89 */     return this.dynamicResource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepareCapture(int iconIndex) {
/*  99 */     this.editingIconIndex = iconIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void captureNow(int displayWidth, int displayHeight) {
/* 110 */     if (this.editingIconIndex > -1) {
/*     */       
/* 112 */       int frameSize = (int)Math.min(0.75F * displayWidth, 0.75F * displayHeight);
/* 113 */       int x = (displayWidth - frameSize) / 2;
/* 114 */       int y = (displayHeight - frameSize) / 2;
/* 115 */       int width = displayWidth - x * 2;
/* 116 */       int height = displayHeight - y * 2;
/*     */       
/* 118 */       BufferedImage resourceImage = new BufferedImage(256, 256, 1);
/* 119 */       resourceImage.setRGB(0, 0, 256, 256, this.textureData, 0, 256);
/*     */ 
/*     */       
/* 122 */       BufferedImage capturedImage = captureRegion(x, y, width, height);
/*     */       
/* 124 */       if (capturedImage != null) {
/*     */         
/* 126 */         int xOffset = this.editingIconIndex % this.thumbnailSize * this.thumbnailSize;
/* 127 */         int yOffset = this.editingIconIndex / this.thumbnailSize * this.thumbnailSize;
/*     */         
/* 129 */         Graphics2D resourceGraphicsContext = resourceImage.createGraphics();
/* 130 */         resourceGraphicsContext.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 131 */         resourceGraphicsContext.drawImage(capturedImage, xOffset, yOffset, this.thumbnailSize, this.thumbnailSize, null);
/* 132 */         resourceGraphicsContext.dispose();
/*     */ 
/*     */         
/*     */         try {
/* 136 */           String path = this.sourceResource.a();
/* 137 */           path = path.substring(path.indexOf('/') + 1);
/*     */           
/* 139 */           File imageFile = this.macros.getFile(path);
/* 140 */           File imageFileDir = new File(imageFile.getParent());
/*     */           
/* 142 */           if (!imageFileDir.exists())
/*     */           {
/* 144 */             imageFileDir.mkdirs();
/*     */           }
/*     */           
/* 147 */           ImageIO.write(resourceImage, "png", imageFile);
/* 148 */           int[] outData = new int[this.textureData.length];
/* 149 */           resourceImage.getRGB(0, 0, 256, 256, outData, 0, 256);
/*     */           
/* 151 */           for (int i = 0; i < outData.length; i++)
/*     */           {
/* 153 */             this.textureData[i] = outData[i];
/*     */           }
/*     */           
/* 156 */           cdt.a(this.texture.b(), this.textureData, 256, 256);
/*     */         }
/* 158 */         catch (Exception ex) {
/*     */           
/* 160 */           Game.addChatMessage("§cThumbnail image capture failed");
/* 161 */           Log.printStackTrace(ex);
/*     */         } 
/*     */       } 
/*     */       
/* 165 */       this.editingIconIndex = -1;
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
/*     */   public static BufferedImage captureRegion(int x, int y, int width, int height) {
/* 180 */     ByteBuffer captureBuffer = BufferUtils.createByteBuffer(width * height * BYTES_PER_PIXEL);
/*     */     
/* 182 */     byte[] pixelData = new byte[width * height * 3];
/* 183 */     int[] imageData = new int[width * height];
/*     */ 
/*     */     
/*     */     try {
/* 187 */       GL.glPixelStorei(3333, 1);
/* 188 */       GL.glPixelStorei(3317, 1);
/*     */       
/* 190 */       captureBuffer.clear();
/*     */       
/* 192 */       GL.glReadPixels(x, y, width, height, 6407, 5121, captureBuffer);
/*     */       
/* 194 */       captureBuffer.clear();
/* 195 */       captureBuffer.get(pixelData);
/*     */       
/* 197 */       for (int xPos = 0; xPos < width; xPos++) {
/*     */         
/* 199 */         for (int yPos = 0; yPos < height; yPos++) {
/*     */           
/* 201 */           int pixelIndex = xPos + (height - yPos - 1) * width;
/* 202 */           int r = pixelData[pixelIndex * BYTES_PER_PIXEL + 0] & 0xFF;
/* 203 */           int g = pixelData[pixelIndex * BYTES_PER_PIXEL + 1] & 0xFF;
/* 204 */           int b = pixelData[pixelIndex * BYTES_PER_PIXEL + 2] & 0xFF;
/* 205 */           imageData[xPos + yPos * width] = 0xFF000000 | r << 16 | g << 8 | b;
/*     */         } 
/*     */       } 
/*     */       
/* 209 */       BufferedImage capturedImage = new BufferedImage(width, height, 1);
/* 210 */       capturedImage.setRGB(0, 0, width, height, imageData, 0, width);
/*     */       
/* 212 */       return capturedImage;
/*     */     }
/* 214 */     catch (Exception exception) {
/*     */       
/* 216 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\thumbnail\Thumbnailer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */