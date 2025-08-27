/*     */ package net.eq2online.macros.scripting.variable.providers;
/*     */ 
/*     */ import aed;
/*     */ import aip;
/*     */ import amu;
/*     */ import amy;
/*     */ import aow;
/*     */ import awt;
/*     */ import bhc;
/*     */ import bib;
/*     */ import et;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.eq2online.macros.core.bridge.EntityUtil;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.variable.BlockPropertyTracker;
/*     */ import net.eq2online.macros.scripting.variable.IVariableStore;
/*     */ import net.eq2online.util.Game;
/*     */ import rk;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VariableProviderTrace
/*     */   implements IVariableProvider, IVariableStore
/*     */ {
/*     */   private static final String TRACETYPE = "TRACETYPE";
/*     */   private static final String TRACESIDE = "TRACESIDE";
/*     */   private static final String TRACEZ = "TRACEZ";
/*     */   private static final String TRACEY = "TRACEY";
/*     */   private static final String TRACEX = "TRACEX";
/*     */   private static final String TRACEUUID = "TRACEUUID";
/*     */   private static final String TRACEDATA = "TRACEDATA";
/*     */   private static final String TRACEID = "TRACEID";
/*     */   private static final String TRACENAME = "TRACENAME";
/*     */   private final bib mc;
/*     */   private final BlockPropertyTracker hitTracker;
/*  38 */   private final Map<String, Object> vars = new HashMap<>();
/*     */ 
/*     */   
/*  41 */   private String type = "NONE";
/*     */ 
/*     */   
/*     */   public VariableProviderTrace(bib minecraft, bhc traceData) {
/*  45 */     this.mc = minecraft;
/*  46 */     this.hitTracker = new BlockPropertyTracker("TRACE_", this);
/*  47 */     update(traceData);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/*  52 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(bhc traceData) {
/*  57 */     this.vars.clear();
/*     */     
/*  59 */     if (traceData != null && traceData.a == bhc.a.b) {
/*     */       
/*  61 */       et blockPos = traceData.a();
/*  62 */       awt blockState = this.mc.f.o(blockPos);
/*  63 */       awt actualState = blockState.c((amy)this.mc.f, blockPos);
/*  64 */       aow block = blockState.u();
/*  65 */       aip item = block.a((amu)this.mc.f, blockPos, actualState);
/*  66 */       int blockMeta = block.d(blockState);
/*     */       
/*  68 */       String displayName = block.c();
/*     */       
/*     */       try {
/*  71 */         displayName = item.r();
/*     */       }
/*  73 */       catch (Exception exception) {}
/*     */       
/*  75 */       storeType("TILE");
/*  76 */       storeVariable("TRACENAME", displayName);
/*  77 */       storeVariable("TRACEID", Game.getBlockName(block));
/*  78 */       storeVariable("TRACEDATA", blockMeta);
/*  79 */       storeVariable("TRACEUUID", "");
/*     */       
/*  81 */       storeVariable("TRACEX", blockPos.p());
/*  82 */       storeVariable("TRACEY", blockPos.q());
/*  83 */       storeVariable("TRACEZ", blockPos.r());
/*  84 */       storeVariable("TRACESIDE", VariableProviderPlayer.getSideName(traceData.b.a()));
/*     */       
/*  86 */       this.hitTracker.update(actualState);
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
/*     */     }
/* 102 */     else if (traceData != null && traceData.a == bhc.a.c && traceData.d != null) {
/*     */       
/* 104 */       if (traceData.d instanceof aed) {
/*     */         
/* 106 */         aed player = (aed)traceData.d;
/*     */         
/* 108 */         storeType("PLAYER");
/* 109 */         storeVariable("TRACENAME", player.h_());
/* 110 */         storeVariable("TRACEID", player.S());
/* 111 */         storeVariable("TRACEUUID", player.bm().toString());
/*     */       }
/*     */       else {
/*     */         
/* 115 */         storeType("ENTITY");
/* 116 */         storeVariable("TRACENAME", EntityUtil.getEntityName(traceData.d));
/* 117 */         storeVariable("TRACEID", EntityUtil.getEntityId(traceData.d));
/* 118 */         storeVariable("TRACEUUID", "");
/*     */       } 
/*     */       
/* 121 */       storeVariable("TRACEDATA", 0);
/*     */       
/* 123 */       int posX = rk.c(traceData.d.p);
/* 124 */       int posY = rk.c(traceData.d.q);
/* 125 */       int posZ = rk.c(traceData.d.r);
/*     */       
/* 127 */       storeVariable("TRACEX", posX);
/* 128 */       storeVariable("TRACEY", posY);
/* 129 */       storeVariable("TRACEZ", posZ);
/* 130 */       storeVariable("TRACESIDE", "?");
/*     */     }
/*     */     else {
/*     */       
/* 134 */       storeType("NONE");
/* 135 */       storeVariable("TRACENAME", "None");
/* 136 */       storeVariable("TRACEID", Game.getItemName(null));
/* 137 */       storeVariable("TRACEDATA", 0);
/* 138 */       storeVariable("TRACEUUID", "");
/*     */       
/* 140 */       storeVariable("TRACEX", 0);
/* 141 */       storeVariable("TRACEY", 0);
/* 142 */       storeVariable("TRACEZ", 0);
/* 143 */       storeVariable("TRACESIDE", "?");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/* 160 */     return this.vars.get(variableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getVariables() {
/* 166 */     return this.vars.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeVariable(String name, boolean value) {
/* 172 */     this.vars.put(name, Boolean.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeVariable(String name, int value) {
/* 178 */     this.vars.put(name, Integer.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeVariable(String name, String value) {
/* 184 */     this.vars.put(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   private void storeType(String type) {
/* 189 */     this.type = type;
/* 190 */     storeVariable("TRACETYPE", type);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\providers\VariableProviderTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */