/*     */ package net.eq2online.macros.event;
/*     */ 
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventVariableProvider;
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
/*     */ public class MacroEvent
/*     */   implements IMacroEvent
/*     */ {
/*     */   private final IMacroEventProvider provider;
/*     */   private final String name;
/*     */   private final boolean permissible;
/*     */   private final String permissionGroup;
/*     */   private Icon icon;
/*     */   protected Constructor<IMacroEventVariableProvider> providerConstructor;
/*     */   
/*     */   public MacroEvent(IMacroEventProvider provider, String name, boolean permissible, String permissionGroup, Icon icon) {
/*  47 */     this.provider = provider;
/*  48 */     this.name = name;
/*  49 */     this.permissible = permissible;
/*  50 */     this.permissionGroup = permissionGroup;
/*  51 */     this.icon = icon;
/*     */     
/*  53 */     initProviderClass();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProviderClass() {
/*  62 */     String eventClassName = this.name;
/*     */     
/*  64 */     if (eventClassName.startsWith("on"))
/*     */     {
/*  66 */       eventClassName = "O" + eventClassName.substring(1);
/*     */     }
/*     */     
/*  69 */     if (eventClassName.startsWith("On")) {
/*     */       
/*     */       try {
/*     */         
/*  73 */         String packageName = "net.eq2online.macros.event";
/*  74 */         Class<? extends MacroEvent> eventClass = (Class)getClass();
/*     */ 
/*     */         
/*     */         try {
/*  78 */           Package eventPackage = eventClass.getPackage();
/*     */           
/*  80 */           if (eventPackage == null)
/*     */           {
/*  82 */             String className = eventClass.getName();
/*  83 */             int classNameLength = eventClass.getSimpleName().length();
/*  84 */             packageName = eventClass.getName().substring(0, className.length() - classNameLength - 1);
/*     */           }
/*     */           else
/*     */           {
/*  88 */             packageName = eventPackage.getName();
/*     */           }
/*     */         
/*  91 */         } catch (Exception exception) {}
/*     */         
/*  93 */         String providerClassName = packageName + ".providers." + eventClassName + "Provider";
/*     */         
/*  95 */         Class<? extends IMacroEventVariableProvider> providerClass = (Class)eventClass.getClassLoader().loadClass(providerClassName);
/*     */         
/*  97 */         setVariableProviderClass(providerClass);
/*     */       }
/*  99 */       catch (Exception ex) {
/*     */         
/* 101 */         setVariableProviderClass(null);
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
/*     */   public void setVariableProviderClass(Class<? extends IMacroEventVariableProvider> providerClass) {
/* 116 */     if (providerClass == null) {
/*     */       
/* 118 */       this.providerConstructor = null;
/*     */     }
/* 120 */     else if (IMacroEventVariableProvider.class.isAssignableFrom(providerClass)) {
/*     */ 
/*     */ 
/*     */       
/* 124 */       try { this.providerConstructor = (Constructor)providerClass.getConstructor(new Class[] { IMacroEvent.class }); }
/*     */       
/* 126 */       catch (SecurityException securityException) {  }
/* 127 */       catch (NoSuchMethodException noSuchMethodException) {}
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
/*     */   public String getName() {
/* 139 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getIcon() {
/* 145 */     return this.icon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIcon(Icon icon) {
/* 151 */     this.icon = icon;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroEventProvider getProvider() {
/* 157 */     return this.provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPermissible() {
/* 168 */     return this.permissible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionName() {
/* 177 */     return "events." + ((this.permissionGroup == null) ? "" : (this.permissionGroup + ".")) + getName().toLowerCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermissionGroup() {
/* 188 */     return this.permissionGroup;
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
/*     */   public IMacroEventVariableProvider getVariableProvider(String[] instanceVariables) {
/* 201 */     if (this.providerConstructor != null) {
/*     */       
/*     */       try {
/*     */         
/* 205 */         IMacroEventVariableProvider provider = this.providerConstructor.newInstance(new Object[] { this });
/*     */         
/* 207 */         if (provider != null)
/*     */         {
/* 209 */           provider.initInstance(instanceVariables);
/* 210 */           return provider;
/*     */         }
/*     */       
/* 213 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 216 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDispatch() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getHelp() {
/* 227 */     return this.provider.getHelp(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\MacroEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */