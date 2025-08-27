/*     */ package net.eq2online.macros.gui.skins;
/*     */ 
/*     */ import bia;
/*     */ import bib;
/*     */ import bua;
/*     */ import cdg;
/*     */ import cdh;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.eq2online.macros.compatibility.IconTiled;
/*     */ import net.eq2online.macros.core.mixin.IThreadDownloadImageData;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ import nf;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UserSkinHandler
/*     */   implements ISettingsObserver
/*     */ {
/*     */   private static final int USER_FACE_IMAGE_SIZE = 8;
/*     */   private static final int BYTES_PER_PIXEL = 4;
/*     */   public static boolean enableSkinDownload = true;
/*     */   private final bib minecraft;
/*  68 */   private final Map<String, cdh> downloadingSkins = new HashMap<>();
/*     */   
/*  70 */   private final Map<String, nf> skinResources = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private final String[] users = new String[1024];
/*     */   
/*  77 */   private final IconTiled[] icons = new IconTiled[1024];
/*     */ 
/*     */ 
/*     */   
/*     */   private final ByteBuffer bufferImageData;
/*     */ 
/*     */ 
/*     */   
/*     */   private final nf textureLocation;
/*     */ 
/*     */ 
/*     */   
/*     */   private cdg userFaceTexture;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserSkinHandler(bib minecraft, nf baseTexture) {
/*  95 */     this.minecraft = minecraft;
/*  96 */     this.textureLocation = baseTexture;
/*     */ 
/*     */     
/*  99 */     this.bufferImageData = bia.c(256);
/*     */ 
/*     */     
/* 102 */     BufferedImage baseImage = null;
/*     */     
/*     */     try {
/* 105 */       baseImage = ImageIO.read(this.minecraft.O().a(this.textureLocation).b());
/*     */     }
/* 107 */     catch (Exception ex) {
/*     */       
/* 109 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 112 */     if (baseImage != null) {
/*     */       
/* 114 */       this.userFaceTexture = new cdg(baseImage);
/* 115 */       this.minecraft.N().a("userfaces", this.userFaceTexture);
/*     */       
/* 117 */       int cols = baseImage.getWidth() / 8;
/*     */       
/* 119 */       for (int id = 0; id < this.icons.length; id++) {
/*     */         
/* 121 */         int uCoord = id % cols * 8;
/* 122 */         int vCoord = id / cols * 8;
/* 123 */         this.icons[id] = new IconTiled(this.textureLocation, id, uCoord, vCoord, 8, 8, baseImage
/* 124 */             .getWidth(), baseImage.getHeight());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextureName() {
/* 135 */     return this.userFaceTexture.b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 143 */     Iterator<Map.Entry<String, cdh>> downloadingSkinsIterator = this.downloadingSkins.entrySet().iterator();
/* 144 */     while (downloadingSkinsIterator.hasNext()) {
/*     */       
/* 146 */       Map.Entry<String, cdh> data = downloadingSkinsIterator.next();
/*     */       
/* 148 */       cdh value = data.getValue();
/* 149 */       if (storeSkin(data.getKey(), value))
/*     */       {
/* 151 */         downloadingSkinsIterator.remove();
/*     */       }
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
/*     */   protected boolean storeSkin(String username, cdh imageDownload) {
/* 164 */     BufferedImage userSkin = ((IThreadDownloadImageData)imageDownload).getDownloadedImage();
/* 165 */     if (userSkin == null)
/*     */     {
/* 167 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 171 */     int freeslot = getFreeSlot();
/*     */ 
/*     */     
/* 174 */     if (freeslot == 0)
/*     */     {
/* 176 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 180 */     this.users[freeslot] = username;
/*     */ 
/*     */     
/* 183 */     int[] playerFaceData = new int[64];
/* 184 */     userSkin.getRGB(8, 8, 8, 8, playerFaceData, 0, 8);
/*     */ 
/*     */     
/* 187 */     int[] playerHelmData = new int[64];
/* 188 */     userSkin.getRGB(40, 8, 8, 8, playerHelmData, 0, 8);
/*     */ 
/*     */     
/* 191 */     this.bufferImageData.clear();
/*     */ 
/*     */     
/* 194 */     for (int pixelIndex = 0; pixelIndex < 64; pixelIndex++) {
/*     */ 
/*     */       
/* 197 */       float hAlpha = (playerHelmData[pixelIndex] >> 24 & 0xFF) / 255.0F;
/* 198 */       float hAlphaD = 1.0F - hAlpha;
/*     */ 
/*     */       
/* 201 */       float r = (playerFaceData[pixelIndex] >> 16 & 0xFF) * hAlphaD + (playerHelmData[pixelIndex] >> 16 & 0xFF) * hAlpha;
/* 202 */       float g = (playerFaceData[pixelIndex] >> 8 & 0xFF) * hAlphaD + (playerHelmData[pixelIndex] >> 8 & 0xFF) * hAlpha;
/* 203 */       float b = (playerFaceData[pixelIndex] >> 0 & 0xFF) * hAlphaD + (playerHelmData[pixelIndex] >> 0 & 0xFF) * hAlpha;
/*     */ 
/*     */       
/* 206 */       this.bufferImageData.put((byte)(int)r);
/* 207 */       this.bufferImageData.put((byte)(int)g);
/* 208 */       this.bufferImageData.put((byte)(int)b);
/* 209 */       this.bufferImageData.put((byte)-1);
/*     */     } 
/*     */ 
/*     */     
/* 213 */     this.bufferImageData.flip();
/*     */ 
/*     */     
/* 216 */     this.minecraft.N().a(this.textureLocation);
/* 217 */     GL11.glTexSubImage2D(3553, 0, freeslot % 32 * 8, freeslot / 32 * 8, 8, 8, 6408, 5121, this.bufferImageData);
/*     */     
/* 219 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFreeSlot() {
/* 228 */     for (int slot = 1; slot < this.users.length; slot++) {
/*     */       
/* 230 */       if (this.users[slot] == null) return slot;
/*     */     
/*     */     } 
/* 233 */     return 0;
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
/*     */   public IconTiled getIconForSkin(String username) {
/* 246 */     for (int i = 0; i < this.users.length; i++) {
/*     */       
/* 248 */       if (this.users[i] != null && this.users[i].equals(username))
/*     */       {
/* 250 */         return this.icons[i];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 255 */     if (!this.downloadingSkins.containsKey(username))
/*     */     {
/* 257 */       beginDownloadingSkin(username);
/*     */     }
/*     */     
/* 260 */     return this.icons[0];
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
/*     */   public boolean hasUserSkin(String username) {
/* 272 */     for (int i = 0; i < this.users.length; i++) {
/*     */       
/* 274 */       if (this.users[i] != null && this.users[i].equals(username))
/*     */       {
/* 276 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 280 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDownloadingUserSkin(String username) {
/* 291 */     return this.downloadingSkins.containsKey(username);
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
/*     */   public void addUser(String username) {
/* 304 */     if (!hasUserSkin(username) && !isDownloadingUserSkin(username))
/*     */     {
/* 306 */       beginDownloadingSkin(username);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void beginDownloadingSkin(String username) {
/* 317 */     if (enableSkinDownload) {
/*     */       
/* 319 */       nf skinResource = bua.e(username);
/* 320 */       cdh skinTexture = bua.a(skinResource, username);
/*     */       
/* 322 */       if (skinTexture != null) {
/*     */         
/* 324 */         this.skinResources.put(username, skinResource);
/* 325 */         this.downloadingSkins.put(username, skinTexture);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClearSettings() {
/* 333 */     enableSkinDownload = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadSettings(ISettingsStore settings) {
/* 340 */     enableSkinDownload = settings.getSetting("skin.download.enabled", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSaveSettings(ISettingsStore settings) {
/* 347 */     settings.setSetting("skin.download.enabled", enableSkinDownload);
/*     */ 
/*     */     
/* 350 */     settings.setSettingComment("skin.download.enabled", "Enable skin downloads for the online user list. Disable this to give all users the 'Steve' skin in the online user list");
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\skins\UserSkinHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */