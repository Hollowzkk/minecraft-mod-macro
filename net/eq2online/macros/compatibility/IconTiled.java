/*     */ package net.eq2online.macros.compatibility;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IconTiled
/*     */   implements Icon
/*     */ {
/*     */   private nf textureResource;
/*     */   protected int iconID;
/*     */   protected int iconU;
/*     */   protected int iconV;
/*     */   private int width;
/*     */   private int height;
/*     */   private float uCoord;
/*     */   private float uCoord2;
/*     */   private float vCoord;
/*     */   private float vCoord2;
/*     */   private int textureWidth;
/*     */   private int textureHeight;
/*     */   
/*     */   public IconTiled(nf textureResource, int id) {
/*  27 */     this(textureResource, id, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   public IconTiled(nf textureResource, int id, int iconSize) {
/*  32 */     this(textureResource, id, iconSize, 256);
/*     */   }
/*     */ 
/*     */   
/*     */   public IconTiled(nf textureResource, int id, int iconSize, int textureSize) {
/*  37 */     this(textureResource, id, iconSize, id % textureSize / iconSize * iconSize, id / textureSize / iconSize * iconSize, textureSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public IconTiled(nf textureResource, int id, int iconSize, int iconU, int iconV) {
/*  42 */     this(textureResource, id, iconSize, iconU, iconV, 256);
/*     */   }
/*     */ 
/*     */   
/*     */   public IconTiled(nf textureResource, int id, int iconSize, int iconU, int iconV, int textureSize) {
/*  47 */     this(textureResource, id, iconU, iconV, iconSize, iconSize, textureSize, textureSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public IconTiled(nf textureResource, int id, int iconU, int iconV, int width, int height, int textureWidth, int textureHeight) {
/*  52 */     this.iconID = id;
/*  53 */     this.textureResource = textureResource;
/*     */     
/*  55 */     this.textureWidth = textureWidth;
/*  56 */     this.textureHeight = textureHeight;
/*     */     
/*  58 */     this.width = width;
/*  59 */     this.height = height;
/*     */     
/*  61 */     init(iconU, iconV);
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
/*     */   protected void init(int iconU, int iconV) {
/*  74 */     this.iconU = iconU;
/*  75 */     this.iconV = iconV;
/*     */     
/*  77 */     this.uCoord = iconU / this.textureWidth;
/*  78 */     this.uCoord2 = (iconU + this.width) / this.textureWidth;
/*  79 */     this.vCoord = iconV / this.textureHeight;
/*  80 */     this.vCoord2 = (iconV + this.height) / this.textureHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public nf getTextureResource() {
/*  85 */     return this.textureResource;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(bib minecraft) {
/*  90 */     minecraft.N().a(this.textureResource);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIconId() {
/*  95 */     return this.iconID;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIconId(int id) {
/* 100 */     this.iconID = id;
/* 101 */     init(id % 16 * 16, id / 16 * 16);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconWidth() {
/* 107 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconHeight() {
/* 113 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinU() {
/* 119 */     return this.uCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxU() {
/* 125 */     return this.uCoord2 - Float.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInterpolatedU(double slice) {
/* 131 */     float uSize = this.uCoord2 - this.uCoord;
/* 132 */     return this.uCoord + uSize * (float)slice / 16.0F - Float.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinV() {
/* 138 */     return this.vCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxV() {
/* 144 */     return this.vCoord2 - Float.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInterpolatedV(double slice) {
/* 150 */     float vSize = this.vCoord2 - this.vCoord;
/* 151 */     return this.vCoord + vSize * (float)slice / 16.0F - Float.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIconName() {
/* 157 */     return this.textureResource + "_" + this.iconID;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\compatibility\IconTiled.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */