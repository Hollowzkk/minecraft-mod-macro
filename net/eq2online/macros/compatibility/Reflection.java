/*     */ package net.eq2online.macros.compatibility;
/*     */ 
/*     */ import bve;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.scripting.IErrorLogger;
/*     */ import net.minecraft.client.ClientBrandRetriever;
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
/*     */ public class Reflection
/*     */ {
/*  33 */   private static Field fieldModifiers = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean forgeModLoader = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  43 */     forgeModLoader = ClientBrandRetriever.getClientModName().contains("fml");
/*     */ 
/*     */     
/*     */     try {
/*  47 */       fieldModifiers = Field.class.getDeclaredField("modifiers");
/*  48 */       fieldModifiers.setAccessible(true);
/*     */     }
/*  50 */     catch (Throwable throwable) {}
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
/*     */   public static void setPrivateValue(Class<?> instanceClass, Object instance, String fieldName, String obfuscatedFieldName, String seargeFieldName, Object value) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
/*  63 */     setPrivateValueInternal(instanceClass, instance, getObfuscatedFieldName(fieldName, obfuscatedFieldName, seargeFieldName), value);
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
/*     */   public static void setPrivateValue(Class<?> instanceClass, Object instance, String fieldName, Object value) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
/*  76 */     setPrivateValueInternal(instanceClass, instance, fieldName, value);
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
/*     */   public static <T> T getPrivateValue(Class<?> instanceClass, Object instance, String fieldName, String obfuscatedFieldName, String seargeFieldName) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
/*  90 */     return (T)getPrivateValueInternal(instanceClass, instance, getObfuscatedFieldName(fieldName, obfuscatedFieldName, seargeFieldName));
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
/*     */   public static <T> T getPrivateValue(Class<?> instanceClass, Object instance, String fieldName) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
/* 104 */     return (T)getPrivateValueInternal(instanceClass, instance, fieldName);
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
/*     */   private static String getObfuscatedFieldName(String fieldName, String obfuscatedFieldName, String seargeFieldName) {
/* 116 */     if (forgeModLoader) return seargeFieldName; 
/* 117 */     return !bve.a().getClass().getSimpleName().equals("Tessellator") ? obfuscatedFieldName : fieldName;
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
/*     */   private static void setPrivateValueInternal(Class<?> instanceClass, Object instance, String fieldName, Object value) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
/*     */     try {
/* 132 */       Field field = instanceClass.getDeclaredField(fieldName);
/* 133 */       int fieldFieldModifiers = fieldModifiers.getInt(field);
/*     */       
/* 135 */       if ((fieldFieldModifiers & 0x10) != 0)
/*     */       {
/* 137 */         fieldModifiers.setInt(field, fieldFieldModifiers & 0xFFFFFFEF);
/*     */       }
/*     */       
/* 140 */       field.setAccessible(true);
/* 141 */       field.set(instance, value);
/*     */     }
/* 143 */     catch (IllegalAccessException illegalAccessException) {}
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
/*     */   public static Object getPrivateValueInternal(Class<?> instanceClass, Object instance, String fieldName) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
/*     */     try {
/* 158 */       Field field = instanceClass.getDeclaredField(fieldName);
/* 159 */       field.setAccessible(true);
/* 160 */       return field.get(instance);
/*     */     }
/* 162 */     catch (IllegalAccessException illegalaccessexception) {
/*     */       
/* 164 */       return null;
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
/*     */   public static File getPackagePath(Class<?> packageClass) throws MalformedURLException, URISyntaxException, UnsupportedEncodingException {
/* 177 */     File packagePath = null;
/*     */     
/* 179 */     URL protectionDomainLocation = packageClass.getProtectionDomain().getCodeSource().getLocation();
/* 180 */     if (protectionDomainLocation != null) {
/*     */       
/* 182 */       if (protectionDomainLocation.toString().indexOf('!') > -1 && protectionDomainLocation.toString().startsWith("jar:"))
/*     */       {
/* 184 */         protectionDomainLocation = new URL(protectionDomainLocation.toString().substring(4, protectionDomainLocation.toString().indexOf('!')));
/*     */       }
/*     */       
/* 187 */       packagePath = new File(protectionDomainLocation.toURI());
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 192 */       String reflectionClassPath = Reflection.class.getResource("/" + Reflection.class.getName().replace('.', '/') + ".class").getPath();
/*     */       
/* 194 */       if (reflectionClassPath.indexOf('!') > -1) {
/*     */         
/* 196 */         reflectionClassPath = URLDecoder.decode(reflectionClassPath, "UTF-8");
/* 197 */         packagePath = new File(reflectionClassPath.substring(5, reflectionClassPath.indexOf('!')));
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     if (packagePath != null && packagePath.isFile() && packagePath.getName().endsWith(".class")) {
/*     */       
/* 203 */       String discoveredPath = "";
/* 204 */       String absolutePath = packagePath.getAbsolutePath();
/* 205 */       String classPath = System.getProperty("java.class.path");
/* 206 */       String classPathSeparator = System.getProperty("path.separator");
/* 207 */       String[] classPathEntries = classPath.split(classPathSeparator);
/*     */       
/* 209 */       for (String classPathEntry : classPathEntries) {
/*     */ 
/*     */         
/*     */         try {
/* 213 */           String classPathPart = (new File(classPathEntry)).getAbsolutePath();
/* 214 */           if (absolutePath.startsWith(classPathPart) && classPathPart.length() > discoveredPath.length()) {
/* 215 */             discoveredPath = classPathPart;
/*     */           }
/* 217 */         } catch (Exception exception) {}
/*     */       } 
/*     */       
/* 220 */       if (discoveredPath.length() > 0)
/*     */       {
/* 222 */         packagePath = new File(discoveredPath);
/*     */       }
/*     */     } 
/*     */     
/* 226 */     return packagePath;
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
/*     */   public static <T> List<Class<? extends T>> getSubclassesFor(Class<T> superClass, Class<?> packageClass, String prefix, IErrorLogger logger) {
/*     */     try {
/* 239 */       File packagePath = getPackagePath(packageClass);
/*     */       
/* 241 */       if (packagePath != null)
/*     */       {
/* 243 */         LinkedList<Class<? extends T>> classes = new LinkedList<>();
/* 244 */         ClassLoader classloader = superClass.getClassLoader();
/*     */         
/* 246 */         if (packagePath.isDirectory()) {
/*     */           
/* 248 */           enumerateDirectory(prefix, superClass, classloader, classes, packagePath, logger);
/*     */         }
/* 250 */         else if (packagePath.isFile() && (packagePath.getName().endsWith(".jar") || packagePath.getName().endsWith(".zip") || packagePath.getName().endsWith(".litemod"))) {
/*     */           
/* 252 */           enumerateCompressedPackage(prefix, superClass, classloader, classes, packagePath, logger);
/*     */         } 
/*     */         
/* 255 */         return classes;
/*     */       }
/*     */     
/* 258 */     } catch (Throwable th) {
/*     */       
/* 260 */       Log.printStackTrace(th);
/* 261 */       if (logger != null && th.getMessage() != null) logger.logError(th.getClass().getSimpleName() + " " + th.getMessage());
/*     */     
/*     */     } 
/* 264 */     return new LinkedList<>();
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
/*     */   protected static <T> void enumerateCompressedPackage(String prefix, Class<T> superClass, ClassLoader classloader, LinkedList<Class<? extends T>> classes, File packagePath, IErrorLogger logger) throws FileNotFoundException, IOException {
/* 277 */     FileInputStream fileinputstream = new FileInputStream(packagePath);
/* 278 */     ZipInputStream zipinputstream = new ZipInputStream(fileinputstream);
/*     */     
/* 280 */     ZipEntry zipentry = null;
/*     */ 
/*     */     
/*     */     do {
/* 284 */       zipentry = zipinputstream.getNextEntry();
/*     */       
/* 286 */       if (zipentry == null || !zipentry.getName().endsWith(".class"))
/*     */         continue; 
/* 288 */       String classFileName = zipentry.getName();
/* 289 */       String className = (classFileName.lastIndexOf('/') > -1) ? classFileName.substring(classFileName.lastIndexOf('/') + 1) : classFileName;
/*     */       
/* 291 */       if (prefix != null && !className.startsWith(prefix)) {
/*     */         continue;
/*     */       }
/*     */       try {
/* 295 */         String fullClassName = classFileName.substring(0, classFileName.length() - 6).replaceAll("/", ".");
/* 296 */         checkAndAddClass(classloader, superClass, classes, fullClassName, logger);
/*     */       }
/* 298 */       catch (Exception exception) {}
/*     */ 
/*     */     
/*     */     }
/* 302 */     while (zipentry != null);
/*     */     
/* 304 */     fileinputstream.close();
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
/*     */   private static <T> void enumerateDirectory(String prefix, Class<T> superClass, ClassLoader classloader, LinkedList<Class<? extends T>> classes, File packagePath, IErrorLogger logger) {
/* 318 */     enumerateDirectory(prefix, superClass, classloader, classes, packagePath, "", logger);
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
/*     */   private static <T> void enumerateDirectory(String prefix, Class<T> superClass, ClassLoader classloader, LinkedList<Class<? extends T>> classes, File packagePath, String packageName, IErrorLogger logger) {
/* 332 */     File[] classFiles = packagePath.listFiles();
/*     */     
/* 334 */     for (File classFile : classFiles) {
/*     */       
/* 336 */       if (classFile.isDirectory()) {
/*     */         
/* 338 */         enumerateDirectory(prefix, superClass, classloader, classes, classFile, packageName + classFile.getName() + ".", logger);
/*     */ 
/*     */       
/*     */       }
/* 342 */       else if (classFile.getName().endsWith(".class") && (prefix == null || classFile.getName().startsWith(prefix))) {
/*     */         
/* 344 */         String classFileName = classFile.getName();
/* 345 */         String className = packageName + classFileName.substring(0, classFileName.length() - 6);
/* 346 */         checkAndAddClass(classloader, superClass, classes, className, logger);
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
/*     */ 
/*     */   
/*     */   protected static <T> void checkAndAddClass(ClassLoader classloader, Class<T> superClass, LinkedList<Class<? extends T>> classes, String className, IErrorLogger logger) {
/* 361 */     if (className.indexOf('$') > -1) {
/*     */       return;
/*     */     }
/*     */     try {
/* 365 */       Class<?> subClass = classloader.loadClass(className);
/*     */       
/* 367 */       if (subClass != null && !superClass.equals(subClass) && superClass.isAssignableFrom(subClass) && !classes.contains(subClass))
/*     */       {
/* 369 */         classes.add(subClass);
/*     */       }
/*     */     }
/* 372 */     catch (Throwable throwable) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\compatibility\Reflection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */