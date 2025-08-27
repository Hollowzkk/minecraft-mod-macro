/*     */ package net.eq2online.macros.core.bridge;
/*     */ 
/*     */ import aed;
/*     */ import fh;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import vg;
/*     */ import vi;
/*     */ 
/*     */ 
/*     */ public class EntityUtil
/*     */ {
/*  15 */   private static final Map<Class<? extends vg>, Integer> ENTITY_IDS = new HashMap<>();
/*     */   
/*     */   private static Field fdEntityRegistry;
/*     */   private static Method mdGetId;
/*     */   private static boolean registryQueried;
/*     */   private static boolean registryExists;
/*     */   
/*     */   static {
/*  23 */     for (Field field : vi.class.getDeclaredFields()) {
/*     */       
/*  25 */       if (fh.class.isAssignableFrom(field.getType()))
/*     */       {
/*  27 */         fdEntityRegistry = field;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  33 */       mdGetId = vi.class.getDeclaredMethod("getID", new Class[] { Class.class });
/*     */     }
/*  35 */     catch (NoSuchMethodException noSuchMethodException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEntityUUID(vg entity) {
/*  44 */     if (entity == null)
/*     */     {
/*  46 */       return "";
/*     */     }
/*     */     
/*  49 */     return entity.bm().toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getEntityName(vg entity) {
/*  54 */     if (entity == null)
/*     */     {
/*  56 */       return "";
/*     */     }
/*     */     
/*  59 */     if (entity instanceof aed)
/*     */     {
/*  61 */       return ((aed)entity).h_();
/*     */     }
/*     */     
/*  64 */     if (entity.n_())
/*     */     {
/*  66 */       return entity.bq();
/*     */     }
/*     */     
/*  69 */     String entityName = vi.b(entity);
/*  70 */     return (entityName == null) ? entity.getClass().getSimpleName() : entityName;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getEntityId(vg entity) {
/*  75 */     if (entity == null)
/*     */     {
/*  77 */       return 0;
/*     */     }
/*     */     
/*  80 */     Class<? extends vg> entityClass = (Class)entity.getClass();
/*  81 */     Integer cachedEntityId = ENTITY_IDS.get(entityClass);
/*  82 */     if (cachedEntityId != null)
/*     */     {
/*  84 */       return cachedEntityId.intValue();
/*     */     }
/*     */     
/*  87 */     int entityId = findEntityId(entityClass);
/*  88 */     ENTITY_IDS.put(entityClass, Integer.valueOf(entityId));
/*  89 */     return entityId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int findEntityId(Class<? extends vg> entityClass) {
/*  95 */     if (!registryQueried) {
/*     */       
/*  97 */       registryQueried = true;
/*  98 */       if (fdEntityRegistry != null) {
/*     */         
/*     */         try {
/*     */           
/* 102 */           fdEntityRegistry.get(null);
/* 103 */           registryExists = true;
/*     */         }
/* 105 */         catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (registryExists) {
/*     */       
/*     */       try {
/*     */         
/* 117 */         return vi.b.a(entityClass);
/*     */       }
/* 119 */       catch (Exception ex) {
/*     */         
/* 121 */         ex.printStackTrace();
/* 122 */         registryExists = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 127 */     if (mdGetId != null) {
/*     */       
/*     */       try {
/*     */         
/* 131 */         return ((Integer)mdGetId.invoke(null, new Object[] { entityClass })).intValue();
/*     */       }
/* 133 */       catch (Exception ex) {
/*     */         
/* 135 */         ex.printStackTrace();
/* 136 */         mdGetId = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 141 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\bridge\EntityUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */