/*     */ package net.eq2online.macros.permissions;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.Permissible;
/*     */ import com.mumfrey.liteloader.permissions.PermissionsManagerClient;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
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
/*     */ public abstract class MacroModPermissions
/*     */ {
/*     */   public static final String MOD_NAME = "macros";
/*     */   private static PermissionsManagerClient permissionsManager;
/*     */   private static Permissible mod;
/*     */   private static boolean initDone;
/*     */   
/*     */   public static void init(Permissible mod, PermissionsManagerClient permissionsManager) {
/*  42 */     if (!initDone || MacroModPermissions.permissionsManager != permissionsManager) {
/*     */       
/*  44 */       initDone = true;
/*  45 */       MacroModPermissions.mod = mod;
/*  46 */       MacroModPermissions.permissionsManager = permissionsManager;
/*     */       
/*  48 */       initPermissions();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initPermissions() {
/*  57 */     registerPermission("*");
/*     */     
/*  59 */     Macros.getInstance().getEventManager().initPermissions();
/*  60 */     ScriptAction.initPermissions();
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
/*     */   public static void refreshPermissions(bib minecraft) {
/*     */     try {
/*  74 */       permissionsManager.tamperCheck();
/*  75 */       permissionsManager.sendPermissionQuery(mod);
/*     */     }
/*  77 */     catch (IllegalArgumentException illegalArgumentException) {}
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
/*     */   public static long getLastUpdateTime() {
/*  89 */     return permissionsManager.getPermissionUpdateTime(mod).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPermission(String permission) {
/*  99 */     permissionsManager.tamperCheck();
/* 100 */     permissionsManager.registerModPermission(mod, permission);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasPermission(String permission) {
/* 111 */     permissionsManager.tamperCheck();
/* 112 */     return permissionsManager.getModPermission(mod, permission);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\permissions\MacroModPermissions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */