/*    */ package net.eq2online.macros.scripting;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.eq2online.macros.core.CustomResourcePack;
/*    */ 
/*    */ public class PlaySoundResourcePack
/*    */   extends CustomResourcePack
/*    */ {
/*  9 */   public static final Object CUSTOM_SOUND_PREFIX = "custom";
/* 10 */   public static final String CUSTOM_SOUND_PATH = "sounds/" + CUSTOM_SOUND_PREFIX + "/";
/*    */   
/*    */   public static final String CUSTOM_SOUND_EXTENSION = ".ogg";
/*    */   
/*    */   public PlaySoundResourcePack(File path, String domain) {
/* 15 */     super(path, domain, "sounds", "custom", ".ogg");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String b() {
/* 21 */     return "Macros.CustomSounds";
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\PlaySoundResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */