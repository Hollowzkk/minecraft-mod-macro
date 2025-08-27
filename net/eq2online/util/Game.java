/*     */ package net.eq2online.util;
/*     */ 
/*     */ import aed;
/*     */ import ain;
/*     */ import aow;
/*     */ import bhy;
/*     */ import bib;
/*     */ import biq;
/*     */ import bjb;
/*     */ import cgt;
/*     */ import chd;
/*     */ import cho;
/*     */ import hh;
/*     */ import ho;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.compatibility.PrivateFields;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ import net.eq2online.macros.scripting.SoundEffect;
/*     */ import net.minecraftforge.client.settings.KeyBindingMap;
/*     */ import nf;
/*     */ import qe;
/*     */ import qg;
/*     */ import rg;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Game
/*     */ {
/*  35 */   public static final ain ITEM_AIR_VIRTUAL = (new ain()
/*     */     {
/*     */       public ain init()
/*     */       {
/*  39 */         c("air");
/*  40 */         return this;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public String a() {
/*  46 */         return "Air";
/*     */       }
/*  48 */     }).init();
/*     */   
/*     */   public static class Settings
/*     */     implements ISettingsObserver
/*     */   {
/*     */     protected boolean stripDefaultNamespace = true;
/*     */     
/*     */     public void setStripDefaultNamespace(boolean stripDefaultNamespace) {
/*  56 */       this.stripDefaultNamespace = stripDefaultNamespace;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isStripDefaultNamespace() {
/*  61 */       return this.stripDefaultNamespace;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onClearSettings() {
/*  67 */       this.stripDefaultNamespace = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onLoadSettings(ISettingsStore settings) {
/*  73 */       this.stripDefaultNamespace = settings.getSetting("script.stripdefaultnamespace", true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onSaveSettings(ISettingsStore settings) {
/*  79 */       settings.setSetting("script.stripdefaultnamespace", this.stripDefaultNamespace);
/*  80 */       settings.setSettingComment("script.stripdefaultnamespace", "If enabled, items and blocks in the default \"minecraft\" namespace will be have the minecraft: prefix stripped from their names when returned by script commands or environment variables");
/*     */     }
/*     */   }
/*     */   
/*  84 */   public static final Settings SETTINGS = new Settings();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addChatMessage(String message) {
/*  93 */     addChatMessage((hh)new ho(message));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addChatMessage(hh message) {
/* 100 */     bib minecraft = bib.z();
/* 101 */     bjb chatGUI = minecraft.q.d();
/* 102 */     chatGUI.a(message);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void playSoundFX(qe soundEvent, qg category, float soundVolume, float soundPitch) {
/* 120 */     bib minecraft = bib.z();
/* 121 */     cho soundHandler = minecraft.U();
/* 122 */     soundHandler.a((cgt)new SoundEffect(soundEvent, category, soundVolume, soundPitch));
/*     */   }
/*     */ 
/*     */   
/*     */   public static aed getPlayerMP() {
/* 127 */     bib mc = bib.z();
/* 128 */     chd server = mc.F();
/* 129 */     if (server != null)
/*     */     {
/* 131 */       return (aed)server.am().a(server.Q());
/*     */     }
/*     */     
/* 134 */     return (aed)mc.h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static bhy getKeybinding(int keyCode) {
/*     */     try {
/* 144 */       Object map = PrivateFields.StaticFields.KEYBIND_HASH.get();
/* 145 */       if (map instanceof rg) {
/*     */ 
/*     */         
/* 148 */         rg<bhy> kb = (rg<bhy>)map;
/* 149 */         bhy keyBinding = (bhy)kb.a(keyCode);
/* 150 */         return keyBinding;
/*     */       } 
/* 152 */       if (map instanceof KeyBindingMap)
/*     */       {
/* 154 */         List<bhy> keys = ((KeyBindingMap)map).lookupAll(keyCode);
/* 155 */         bhy keyBinding = (keys.size() > 0) ? keys.get(0) : null;
/* 156 */         return keyBinding;
/*     */       }
/*     */     
/* 159 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ain getItem(nf name) {
/* 169 */     if ("air".equals(name))
/*     */     {
/* 171 */       return ITEM_AIR_VIRTUAL;
/*     */     }
/*     */     
/* 174 */     return (ain)ain.g.c(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static aow getBlock(nf name) {
/* 179 */     return (aow)aow.h.c(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getItemName(ain item) {
/* 184 */     if (item == ITEM_AIR_VIRTUAL)
/*     */     {
/* 186 */       return "air";
/*     */     }
/*     */     
/* 189 */     nf itemName = (nf)ain.g.b(item);
/* 190 */     return (itemName == null) ? "air" : stripNamespace(itemName.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getBlockName(aow block) {
/* 195 */     nf blockName = (nf)aow.h.b(block);
/* 196 */     return (blockName == null) ? "air" : stripNamespace(blockName.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String stripNamespace(String itemName) {
/* 205 */     return (SETTINGS.stripDefaultNamespace && itemName.startsWith("minecraft:")) ? itemName.substring(10) : itemName;
/*     */   }
/*     */ 
/*     */   
/*     */   public static biq getIngameGui() {
/* 210 */     return (bib.z()).q;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2onlin\\util\Game.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */