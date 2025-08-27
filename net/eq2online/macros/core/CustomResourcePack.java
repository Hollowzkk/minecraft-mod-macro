/*     */ package net.eq2online.macros.core;
/*     */ 
/*     */ import cek;
/*     */ import cfe;
/*     */ import cfg;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CustomResourcePack
/*     */   extends cek
/*     */   implements FileFilter
/*     */ {
/*     */   private final String domain;
/*     */   private final String prefix;
/*     */   private final String path;
/*     */   private final String extension;
/*     */   private final Set<String> availableNamespaces;
/*     */   
/*     */   public CustomResourcePack(File path, String domain, String resourceBaseDir, String topLevelDir, String extension) {
/*  32 */     super(path);
/*     */     
/*  34 */     this.domain = domain;
/*  35 */     this.prefix = topLevelDir + "/";
/*  36 */     this.path = String.format("%s/%s/", new Object[] { resourceBaseDir, topLevelDir });
/*  37 */     this.extension = extension;
/*  38 */     this.availableNamespaces = (Set<String>)ImmutableSet.of(this.domain);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/*  43 */     return this.prefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getExtension() {
/*  48 */     return this.extension;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean b(nf location) {
/*  54 */     String domain = location.b();
/*  55 */     String path = location.a();
/*     */     
/*  57 */     if (domain.equals(this.domain) && path
/*  58 */       .startsWith(this.path) && path
/*  59 */       .endsWith(this.extension)) {
/*     */       
/*  61 */       File targetFile = new File(this.a, path.substring(this.path.length()));
/*  62 */       return targetFile.exists();
/*     */     } 
/*     */     
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream a(nf location) throws IOException {
/*  71 */     String domain = location.b();
/*  72 */     String path = location.a();
/*     */     
/*  74 */     if (domain.equals(this.domain) && path
/*  75 */       .startsWith(this.path) && path
/*  76 */       .endsWith(this.extension)) {
/*     */       
/*  78 */       File targetFile = new File(this.a, path.substring(this.path.length()));
/*  79 */       return new BufferedInputStream(new FileInputStream(targetFile));
/*     */     } 
/*     */     
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public cfe a(cfg par1MetadataSerializer, String par2Str) throws IOException {
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> c() {
/*  95 */     return this.availableNamespaces;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String b();
/*     */ 
/*     */   
/*     */   public File getResourceFile(String path) {
/* 103 */     return new File(this.a, path + this.extension);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getResourceLocation(String path) {
/* 108 */     return String.format("%s:%s%s", new Object[] { this.domain, this.prefix, path });
/*     */   }
/*     */ 
/*     */   
/*     */   public String getResourcePath(nf resource) {
/* 113 */     return resource.a().substring(this.prefix.length()) + this.extension;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<nf> getResourceList() {
/* 118 */     ImmutableList.Builder<nf> list = ImmutableList.builder();
/* 119 */     getResourceList(this.a, list, this.prefix);
/* 120 */     return (List<nf>)list.build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(File file) {
/* 126 */     String name = file.getName();
/* 127 */     return ((file.isDirectory() || name.endsWith(this.extension)) && name.toLowerCase().equals(name));
/*     */   }
/*     */ 
/*     */   
/*     */   private void getResourceList(File dir, ImmutableList.Builder<nf> list, String path) {
/* 132 */     if (!dir.isDirectory()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 137 */     for (File file : dir.listFiles(this)) {
/*     */       
/* 139 */       String fileName = file.getName();
/* 140 */       if (file.isDirectory()) {
/*     */         
/* 142 */         getResourceList(file, list, path + fileName + "/");
/*     */       }
/*     */       else {
/*     */         
/* 146 */         list.add(new nf(this.domain, path + fileName.substring(0, fileName.length() - this.extension.length())));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\CustomResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */