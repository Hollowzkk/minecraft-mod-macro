/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.scripting.api.APIVersion;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacrosAPIModule;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.macros.scripting.api.IScriptedIterator;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModuleLoader
/*     */ {
/*     */   private static final int API_VERSION_MIN = 26;
/*     */   private static final int API_VERSION = 26;
/*     */   private final File modulesDir;
/*     */   
/*     */   public ModuleLoader(File macrosPath) {
/*  35 */     this.modulesDir = new File(macrosPath, "/modules");
/*     */ 
/*     */     
/*     */     try {
/*  39 */       this.modulesDir.mkdirs();
/*     */     }
/*  41 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadModules(IErrorLogger logger) {
/*     */     try {
/*  51 */       LaunchClassLoader classLoader = (LaunchClassLoader)ScriptCore.class.getClassLoader();
/*     */       
/*  53 */       loadCommandLineModules(logger, classLoader);
/*     */       
/*  55 */       if (!this.modulesDir.exists() || !this.modulesDir.isDirectory()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  61 */       List<File> moduleFiles = new ArrayList<>();
/*     */       
/*  63 */       for (File file : this.modulesDir.listFiles(new FilenameFilter()
/*     */           {
/*     */             
/*     */             public boolean accept(File dir, String name)
/*     */             {
/*  68 */               return (name.startsWith("module_") && (name.endsWith(".zip") || name.endsWith(".jar")));
/*     */             }
/*     */           }))
/*     */       {
/*     */         
/*  73 */         moduleFiles.add(file);
/*     */       }
/*     */ 
/*     */       
/*  77 */       for (File module : moduleFiles)
/*     */       {
/*  79 */         loadModule(logger, classLoader, module);
/*     */       }
/*     */     }
/*  82 */     catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadCommandLineModules(IErrorLogger logger, LaunchClassLoader classLoader) {
/*  89 */     String commandLine = System.getProperty("macros.modules");
/*  90 */     if (commandLine == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  95 */     LoadedModuleInfo lmi = new LoadedModuleInfo("CommandLine");
/*     */     
/*  97 */     for (String moduleClass : commandLine.split(",")) {
/*     */ 
/*     */       
/*     */       try {
/* 101 */         loadModuleClass(logger, classLoader, lmi, moduleClass.trim());
/*     */       }
/* 103 */       catch (Exception ex) {
/*     */         
/* 105 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadModule(IErrorLogger logger, LaunchClassLoader classLoader, File module) throws FileNotFoundException, IOException {
/* 112 */     if (!module.isFile()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 117 */     classLoader.addURL(module.toURI().toURL());
/*     */     
/* 119 */     LoadedModuleInfo lmi = new LoadedModuleInfo(module);
/*     */     
/* 121 */     if (!module.isFile() || module.getName().startsWith("module_"));
/*     */     
/* 123 */     ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream(module));
/*     */ 
/*     */     
/*     */     while (true) {
/* 127 */       ZipEntry zipentry = zipinputstream.getNextEntry();
/* 128 */       if (zipentry == null)
/*     */         break; 
/* 130 */       String fileName = (new File(zipentry.getName())).getName();
/*     */       
/* 132 */       if (!zipentry.isDirectory() && fileName.endsWith(".class") && !fileName.contains("$")) {
/*     */         
/* 134 */         String className = zipentry.getName().split("\\.")[0].replace('/', '.');
/* 135 */         if (!loadModuleClass(logger, classLoader, lmi, className) && !fileName.startsWith("ScriptedIterator")) {
/*     */           
/* 137 */           if (logger != null)
/*     */           {
/* 139 */             logger.logError("API: Error initialising " + module.getName());
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     zipinputstream.close();
/*     */ 
/*     */     
/* 150 */     lmi.printStatus();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean loadModuleClass(IErrorLogger logger, LaunchClassLoader classLoader, LoadedModuleInfo lmi, String className) {
/* 155 */     int pos = className.lastIndexOf('.');
/* 156 */     String simpleName = className.substring(pos + 1);
/* 157 */     if (simpleName.startsWith("ScriptAction"))
/*     */     {
/* 159 */       return (lmi.addAction(addModule(logger, (ClassLoader)classLoader, className, IScriptAction.class, "action")) != null);
/*     */     }
/* 161 */     if (simpleName.startsWith("VariableProvider"))
/*     */     {
/* 163 */       return (lmi.addProvider(addModule(logger, (ClassLoader)classLoader, className, IVariableProvider.class, "variable provider")) != null);
/*     */     }
/* 165 */     if (simpleName.startsWith("ScriptedIterator"))
/*     */     {
/* 167 */       return (lmi.addIterator(addModule(logger, (ClassLoader)classLoader, className, IScriptedIterator.class, "iterator")) != null);
/*     */     }
/* 169 */     if (simpleName.startsWith("EventProvider"))
/*     */     {
/* 171 */       return (lmi.addEventProvider(addModule(logger, (ClassLoader)classLoader, className, IMacroEventProvider.class, "event provider")) != null);
/*     */     }
/*     */ 
/*     */     
/* 175 */     return true;
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
/*     */   private <ModuleType extends IMacrosAPIModule> ModuleType addModule(IErrorLogger logger, ClassLoader classLoader, String className, Class<ModuleType> moduleClassType, String moduleType) {
/*     */     try {
/* 188 */       Class<?> moduleClass = classLoader.loadClass(className);
/*     */       
/* 190 */       if (moduleClassType.isAssignableFrom(moduleClass))
/*     */       {
/* 192 */         if (!checkAPIVersion(moduleClass)) {
/*     */           
/* 194 */           Log.info("Macros: API Error. Not loading custom {0} in {1}, bad API version.", new Object[] { moduleType, className });
/* 195 */           if (logger != null) logger.logError("API: Not loading " + moduleClass.getSimpleName() + ", bad API version"); 
/* 196 */           return null;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 202 */           IMacrosAPIModule iMacrosAPIModule = (IMacrosAPIModule)moduleClass.newInstance();
/*     */           
/* 204 */           if (iMacrosAPIModule != null)
/*     */           {
/* 206 */             iMacrosAPIModule.onInit();
/* 207 */             return (ModuleType)iMacrosAPIModule;
/*     */           }
/*     */         
/* 210 */         } catch (Exception exception) {}
/*     */       }
/*     */     
/* 213 */     } catch (Throwable ex) {
/*     */       
/* 215 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean checkAPIVersion(Class<?> moduleClass) {
/* 229 */     APIVersion versionAnnotation = moduleClass.<APIVersion>getAnnotation(APIVersion.class);
/* 230 */     if (versionAnnotation != null) {
/*     */       
/* 232 */       int version = versionAnnotation.value();
/* 233 */       return (version >= 26 && version <= 26);
/*     */     } 
/*     */     
/* 236 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\ModuleLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */