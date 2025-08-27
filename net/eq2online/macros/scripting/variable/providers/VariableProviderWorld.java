/*     */ package net.eq2online.macros.scripting.variable.providers;
/*     */ 
/*     */ import bib;
/*     */ import bsb;
/*     */ import bse;
/*     */ import com.google.common.base.Joiner;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.scripting.variable.VariableCache;
/*     */ import oq;
/*     */ import rp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VariableProviderWorld
/*     */   extends VariableCache
/*     */ {
/*  28 */   private static final String[] EMPTY_STRING_ARRAY = new String[0];
/*     */   
/*  30 */   private static final SimpleDateFormat FORMATTER_DATE = new SimpleDateFormat("yyyy-MM-dd");
/*  31 */   private static final SimpleDateFormat FORMATTER_TIME = new SimpleDateFormat("HH:mm:ss");
/*  32 */   private static final SimpleDateFormat FORMATTER_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */   
/*  34 */   private static final Pattern PATTERN_POPULATIONINFO = Pattern.compile("[0-9]+/([0-9]+)");
/*     */   
/*     */   private final Macros macros;
/*     */   
/*     */   private final bib mc;
/*     */   
/*  40 */   private String[] resourcePacks = EMPTY_STRING_ARRAY;
/*     */   
/*  42 */   private Joiner glue = Joiner.on(",");
/*     */ 
/*     */   
/*     */   public VariableProviderWorld(Macros macros, bib minecraft) {
/*  46 */     this.macros = macros;
/*  47 */     this.mc = minecraft;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {
/*  53 */     bsb bsb = this.mc.f;
/*     */     
/*  55 */     if (bsb != null) {
/*     */       
/*  57 */       long totalWorldTime = bsb.R();
/*  58 */       int totalWorldTicks = (int)(totalWorldTime % 24000L);
/*     */       
/*  60 */       long worldTime = bsb.S();
/*  61 */       int worldTicks = (int)(worldTime % 24000L);
/*  62 */       int dayTicks = (int)((worldTime + 6000L) % 24000L);
/*  63 */       int day = (int)(worldTime / 24000L);
/*     */       
/*  65 */       int dayHour = dayTicks / 1000;
/*  66 */       int dayMinute = (int)((dayTicks % 1000) * 0.06D);
/*     */       
/*  68 */       storeVariable("TOTALTICKS", totalWorldTicks);
/*  69 */       storeVariable("TICKS", worldTicks);
/*  70 */       storeVariable("DAY", day);
/*  71 */       storeVariable("DAYTICKS", dayTicks);
/*  72 */       storeVariable("DAYTIME", String.format("%02d:%02d", new Object[] { Integer.valueOf(dayHour), Integer.valueOf(dayMinute) }));
/*  73 */       storeVariable("RAIN", (int)(bsb.j(0.0F) * 100.0F));
/*  74 */       storeVariable("DIFFICULTY", bsb.ag().name());
/*     */     }
/*     */     else {
/*     */       
/*  78 */       storeVariable("TOTALTICKS", 0);
/*  79 */       storeVariable("TICKS", 0);
/*  80 */       storeVariable("DAY", 0);
/*  81 */       storeVariable("DAYTICKS", 0);
/*  82 */       storeVariable("DAYTIME", "00:00");
/*  83 */       storeVariable("RAIN", 0);
/*  84 */       storeVariable("DIFFICULTY", "PEACEFUL");
/*     */     } 
/*     */     
/*  87 */     storeVariable("SERVER", this.mc.E() ? "SP" : this.macros.getLastServerName());
/*     */     
/*  89 */     int maxPlayers = 0;
/*     */     
/*  91 */     if (this.mc.h != null && this.mc.h.d != null) {
/*     */       
/*  93 */       maxPlayers = this.mc.h.d.a;
/*  94 */       storeVariable("ONLINEPLAYERS", this.mc.h.d.d().size());
/*     */     }
/*     */     else {
/*     */       
/*  98 */       storeVariable("ONLINEPLAYERS", 0);
/*     */     } 
/*     */     
/* 101 */     bse serverData = this.mc.C();
/*     */     
/* 103 */     if (serverData != null) {
/*     */       
/* 105 */       storeVariable("SERVERMOTD", (serverData.d == null) ? "" : serverData.d);
/* 106 */       storeVariable("SERVERNAME", (serverData.a == null) ? "" : serverData.a);
/*     */       
/* 108 */       if (serverData.c != null)
/*     */       {
/* 110 */         Matcher populationInfoMatcher = PATTERN_POPULATIONINFO.matcher(rp.a(serverData.c));
/* 111 */         if (populationInfoMatcher.find())
/*     */         {
/* 113 */           maxPlayers = Math.max(maxPlayers, Integer.parseInt(populationInfoMatcher.group(1)));
/*     */         }
/*     */       }
/*     */     
/* 117 */     } else if (this.mc.E() && this.mc.D()) {
/*     */       
/* 119 */       oq localPlayer = this.mc.F().am().a(this.mc.h.bm());
/*     */       
/* 121 */       storeVariable("SERVERMOTD", "");
/* 122 */       storeVariable("SERVERNAME", this.mc.F().T());
/* 123 */       if (localPlayer != null)
/*     */       {
/* 125 */         storeVariable("SEED", String.valueOf(localPlayer.l.Q()));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 130 */       storeVariable("SERVERMOTD", "");
/* 131 */       storeVariable("SERVERNAME", "Unknown");
/* 132 */       storeVariable("SEED", "0");
/*     */     } 
/*     */     
/* 135 */     storeVariable("MAXPLAYERS", maxPlayers);
/*     */     
/* 137 */     List<String> resourcePackList = this.mc.t.m;
/* 138 */     this.resourcePacks = resourcePackList.<String>toArray(EMPTY_STRING_ARRAY);
/*     */     
/* 140 */     storeVariable("RESOURCEPACKS", this.glue.join((Object[])this.resourcePacks));
/* 141 */     setCachedVariable("RESOURCEPACKS", this.resourcePacks);
/*     */     
/* 143 */     long currentTime = System.currentTimeMillis();
/* 144 */     storeVariable("DATETIME", FORMATTER_DATETIME.format(Long.valueOf(currentTime)));
/* 145 */     storeVariable("DATE", FORMATTER_DATE.format(Long.valueOf(currentTime)));
/* 146 */     storeVariable("TIME", FORMATTER_TIME.format(Long.valueOf(currentTime)));
/*     */     
/* 148 */     Date date = new Date();
/* 149 */     long time = date.getTime() / 1000L;
/* 150 */     storeVariable("TIMESTAMP", (int)time);
/* 151 */     storeVariable("UNIQUEID", "");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/* 157 */     if ("UNIQUEID".equals(variableName))
/*     */     {
/* 159 */       return UUID.randomUUID().toString();
/*     */     }
/*     */     
/* 162 */     return getCachedValue(variableName);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\providers\VariableProviderWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */