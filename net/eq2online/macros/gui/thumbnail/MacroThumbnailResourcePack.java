/*     */ package net.eq2online.macros.gui.thumbnail;
/*     */ 
/*     */ import cer;
/*     */ import cfe;
/*     */ import cfg;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroThumbnailResourcePack
/*     */   implements cer
/*     */ {
/*  29 */   public static final Set<String> NAMESPACES = (Set<String>)ImmutableSet.of("macros");
/*     */   
/*  31 */   private final Map<nf, File> fileResources = Maps.newHashMap();
/*     */   
/*     */   private final File baseDir;
/*     */   
/*     */   public MacroThumbnailResourcePack(File dirPath) {
/*  36 */     this.baseDir = dirPath;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream a(nf location) throws IOException {
/*  42 */     File file = this.fileResources.get(location);
/*  43 */     if (file != null && file.exists())
/*     */     {
/*  45 */       return new FileInputStream(file);
/*     */     }
/*     */     
/*  48 */     InputStream jarInputStream = getResourceAsStreamFromJar(location);
/*  49 */     if (jarInputStream != null)
/*     */     {
/*  51 */       return jarInputStream;
/*     */     }
/*     */     
/*  54 */     throw new FileNotFoundException(location.a());
/*     */   }
/*     */ 
/*     */   
/*     */   private InputStream getResourceAsStreamFromJar(nf location) {
/*  59 */     return MacroThumbnailResourcePack.class.getResourceAsStream("/dynamicassets/" + location
/*  60 */         .b() + "/" + location.a());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFileFor(nf location) {
/*  65 */     String path = location.a();
/*  66 */     path = path.substring(path.indexOf('/') + 1);
/*     */     
/*  68 */     addFileResource(location, new File(this.baseDir, path));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFileResource(nf location, File file) {
/*  73 */     this.fileResources.put(location, file);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean b(nf par1ResourceLocation) {
/*  79 */     return (getResourceAsStreamFromJar(par1ResourceLocation) != null || this.fileResources.containsKey(par1ResourceLocation.toString()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> c() {
/*  85 */     return NAMESPACES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public cfe a(cfg par1MetadataSerializer, String par2Str) throws IOException {
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage a() throws IOException {
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String b() {
/* 104 */     return "Macros Icon Thumbnails";
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String getRelativePath(File left, File right) {
/* 109 */     return left.toURI().relativize(right.toURI()).getPath();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\thumbnail\MacroThumbnailResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */