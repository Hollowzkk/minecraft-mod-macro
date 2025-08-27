/*     */ package net.eq2online.macros.scripting.variable.providers;
/*     */ import ahg;
/*     */ import aio;
/*     */ import aip;
/*     */ import air;
/*     */ import ams;
/*     */ import amu;
/*     */ import aow;
/*     */ import avj;
/*     */ import awc;
/*     */ import awt;
/*     */ import axw;
/*     */ import bhc;
/*     */ import bib;
/*     */ import bki;
/*     */ import bkm;
/*     */ import bkn;
/*     */ import bko;
/*     */ import bkq;
/*     */ import bkr;
/*     */ import bks;
/*     */ import bkt;
/*     */ import bku;
/*     */ import bkv;
/*     */ import bkw;
/*     */ import bkx;
/*     */ import bky;
/*     */ import bkz;
/*     */ import bla;
/*     */ import blb;
/*     */ import blc;
/*     */ import ble;
/*     */ import blf;
/*     */ import blg;
/*     */ import blh;
/*     */ import bli;
/*     */ import blj;
/*     */ import blk;
/*     */ import blm;
/*     */ import bsc;
/*     */ import bud;
/*     */ import cey;
/*     */ import et;
/*     */ import java.util.Map;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.bridge.EntityUtil;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.helpers.SlotHelper;
/*     */ import net.eq2online.macros.scripting.variable.BlockPropertyTracker;
/*     */ import net.eq2online.util.Game;
/*     */ import oh;
/*     */ import oq;
/*     */ import rk;
/*     */ import ua;
/*     */ import vg;
/*     */ import vl;
/*     */ 
/*     */ public class VariableProviderPlayer extends VariableCache {
/*  59 */   private static final Map<Class<? extends blk>, String> GUICLASSES = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final String[] BLOCK_SIDES = new String[] { "B", "T", "N", "S", "W", "E" };
/*     */   
/*  67 */   private static final String[] SIGN_LINES_EMPTY = new String[] { "", "", "", "" };
/*     */ 
/*     */   
/*     */   private final bib mc;
/*     */ 
/*     */   
/*     */   private final SlotHelper slotHelper;
/*     */ 
/*     */   
/*     */   private float prevRotationYaw;
/*     */ 
/*     */   
/*     */   static {
/*  80 */     GUICLASSES.put(blk.class, "UNKNOWN");
/*     */ 
/*     */     
/*  83 */     GUICLASSES.put(blu.class, "GUISTATS");
/*  84 */     GUICLASSES.put(bmb.class, "GUISCREENADVANCEMENTS");
/*  85 */     GUICLASSES.put(bkn.class, "GUICHAT");
/*  86 */     GUICLASSES.put(bml.class, "GUICOMMANDBLOCK");
/*  87 */     GUICLASSES.put(bko.class, "GUICONFIRMOPENLINK");
/*  88 */     GUICLASSES.put(bme.class, "GUICONTROLS");
/*  89 */     GUICLASSES.put(bks.class, "GUICREATEFLATWORLD");
/*  90 */     GUICLASSES.put(boi.class, "GUICREATEWORLD");
/*  91 */     GUICLASSES.put(blm.class, "GUICUSTOMIZESKIN");
/*  92 */     GUICLASSES.put(bku.class, "GUICUSTOMIZEWORLDSCREEN");
/*  93 */     GUICLASSES.put(bky.class, "GUIDISCONNECTED");
/*  94 */     GUICLASSES.put(blj.class, "GUIDOWNLOADTERRAIN");
/*  95 */     GUICLASSES.put(bmt.class, "GUIENCHANTMENT");
/*  96 */     GUICLASSES.put(bla.class, "GUIERRORSCREEN");
/*  97 */     GUICLASSES.put(blh.class, "GUIFLATPRESETS");
/*  98 */     GUICLASSES.put(bkv.class, "GUIGAMEOVER");
/*  99 */     GUICLASSES.put(bmv.class, "GUIHOPPER");
/* 100 */     GUICLASSES.put(blg.class, "GUIINGAMEMENU");
/* 101 */     GUICLASSES.put(blc.class, "GUILANGUAGE");
/* 102 */     GUICLASSES.put(blr.class, "GUIMAINMENU");
/* 103 */     GUICLASSES.put(blf.class, "GUIMEMORYERRORSCREEN");
/* 104 */     GUICLASSES.put(bmy.class, "GUIMERCHANT");
/* 105 */     GUICLASSES.put(bnf.class, "GUIMULTIPLAYER");
/* 106 */     GUICLASSES.put(ble.class, "GUIOPTIONS");
/* 107 */     GUICLASSES.put(bmh.class, "GUIREPAIR");
/* 108 */     GUICLASSES.put(bkz.class, "GUISCREENADDSERVER");
/* 109 */     GUICLASSES.put(bmj.class, "GUISCREENBOOK");
/* 110 */     GUICLASSES.put(bkt.class, "GUISCREENCUSTOMIZEPRESETS");
/* 111 */     GUICLASSES.put(bkw.class, "GUISCREENDEMO");
/* 112 */     GUICLASSES.put(blo.class, "GUISCREENOPTIONSSOUNDS");
/* 113 */     GUICLASSES.put(bki.class, "GUISCREENREALMSPROXY");
/* 114 */     GUICLASSES.put(bnw.class, "GUISCREENRESOURCEPACKS");
/* 115 */     GUICLASSES.put(bkx.class, "GUISCREENSERVERLIST");
/* 116 */     GUICLASSES.put(bli.class, "GUISCREENWORKING");
/* 117 */     GUICLASSES.put(bll.class, "GUISHARETOLAN");
/* 118 */     GUICLASSES.put(blb.class, "GUISLEEPMP");
/* 119 */     GUICLASSES.put(bln.class, "GUISNOOPER");
/* 120 */     GUICLASSES.put(bls.class, "GUIVIDEOSETTINGS");
/* 121 */     GUICLASSES.put(blt.class, "GUIWINGAME");
/* 122 */     GUICLASSES.put(boj.class, "GUIWORLDEDIT");
/* 123 */     GUICLASSES.put(bok.class, "GUIWORLDSELECTION");
/* 124 */     GUICLASSES.put(bkq.class, "GUIYESNO");
/* 125 */     GUICLASSES.put(bmi.class, "GUIBEACON");
/* 126 */     GUICLASSES.put(bmk.class, "GUIBREWINGSTAND");
/* 127 */     GUICLASSES.put(bmm.class, "GUICHEST");
/* 128 */     GUICLASSES.put(bmp.class, "GUICONTAINERCREATIVE");
/* 129 */     GUICLASSES.put(bmn.class, "GUICRAFTING");
/* 130 */     GUICLASSES.put(bmq.class, "GUIDISPENSER");
/* 131 */     GUICLASSES.put(bmz.class, "GUIEDITCOMMANDBLOCKMINECART");
/* 132 */     GUICLASSES.put(bnb.class, "GUIEDITSIGN");
/* 133 */     GUICLASSES.put(bnc.class, "GUIEDITSTRUCTURE");
/* 134 */     GUICLASSES.put(bmu.class, "GUIFURNACE");
/* 135 */     GUICLASSES.put(bmx.class, "GUIINVENTORY");
/* 136 */     GUICLASSES.put(bmw.class, "GUISCREENHORSEINVENTORY");
/* 137 */     GUICLASSES.put(bna.class, "GUISHULKERBOX");
/* 138 */     GUICLASSES.put(bkm.class, "SCREENCHATOPTIONS");
/* 139 */     GUICLASSES.put(bkr.class, "GUICONNECTING");
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
/* 155 */   private String facingDirection = "S";
/*     */   
/*     */   private awc sign;
/*     */   
/* 159 */   private String[] signLines = new String[] { "", "", "", "" };
/*     */   
/*     */   private int signUpdateTicks;
/*     */   
/*     */   private BlockPropertyTracker hitTracker;
/*     */ 
/*     */   
/*     */   public VariableProviderPlayer(Macros macros, bib minecraft) {
/* 167 */     this.mc = minecraft;
/* 168 */     this.slotHelper = new SlotHelper(macros, minecraft);
/* 169 */     this.hitTracker = new BlockPropertyTracker("HIT_", (IVariableStore)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {
/* 175 */     bud player = this.mc.h;
/* 176 */     float partialTicks = this.mc.aj();
/*     */     
/* 178 */     storeVariable("GUI", getNameOfCurrentGuiScreen());
/* 179 */     storeVariable("DISPLAYWIDTH", this.mc.d);
/* 180 */     storeVariable("DISPLAYHEIGHT", this.mc.e);
/*     */     
/* 182 */     DesignableGuiLayout layout = null;
/* 183 */     if (this.mc.m instanceof GuiCustomGui && (layout = ((GuiCustomGui)this.mc.m).getLayout()) != null) {
/*     */       
/* 185 */       storeVariable("SCREEN", layout.getName());
/* 186 */       storeVariable("SCREENNAME", layout.getDisplayName());
/*     */     }
/*     */     else {
/*     */       
/* 190 */       storeVariable("SCREEN", "");
/* 191 */       storeVariable("SCREENNAME", "");
/*     */     } 
/*     */     
/* 194 */     if (player != null) {
/*     */       
/* 196 */       int posX = rk.c(player.p);
/* 197 */       int posY = rk.c(player.q);
/* 198 */       int posZ = rk.c(player.r);
/*     */       
/* 200 */       String fposX = String.format("%.3f", new Object[] { Float.valueOf((float)player.p) });
/* 201 */       String fposY = String.format("%.3f", new Object[] { Float.valueOf((float)player.q) });
/* 202 */       String fposZ = String.format("%.3f", new Object[] { Float.valueOf((float)player.r) });
/*     */       
/* 204 */       et pos = new et(posX, posY, posZ);
/*     */       
/* 206 */       int yaw = (int)(player.v % 360.0F);
/* 207 */       int realYaw = yaw - 180;
/* 208 */       int pitch = (int)(player.w % 360.0F);
/*     */       
/* 210 */       while (yaw < 0)
/*     */       {
/* 212 */         yaw += 360;
/*     */       }
/* 214 */       while (realYaw < 0)
/*     */       {
/* 216 */         realYaw += 360;
/*     */       }
/* 218 */       while (pitch < 0)
/*     */       {
/* 220 */         pitch += 360;
/*     */       }
/*     */       
/* 223 */       if (this.prevRotationYaw != player.v) {
/*     */         
/* 225 */         this.prevRotationYaw = player.v;
/* 226 */         this.facingDirection = "S";
/* 227 */         if (yaw >= 45 && yaw < 135) this.facingDirection = "W"; 
/* 228 */         if (yaw >= 135 && yaw < 225) this.facingDirection = "N"; 
/* 229 */         if (yaw >= 225 && yaw < 315) this.facingDirection = "E";
/*     */       
/*     */       } 
/* 232 */       String biomeName = "UNKNOWN";
/*     */       
/* 234 */       if (this.mc.f != null && this.mc.f.e(pos)) {
/*     */         
/* 236 */         axw playerChunk = this.mc.f.f(pos);
/* 237 */         biomeName = playerChunk.a(pos, this.mc.f.C()).l();
/*     */       } 
/*     */       
/* 240 */       int gameMode = 0;
/* 241 */       String gameModeName = "NOT_SET";
/*     */       
/* 243 */       bsc playerInfo = this.mc.v().a(player.bm());
/* 244 */       if (playerInfo != null) {
/*     */         
/* 246 */         ams playerGameMode = playerInfo.b();
/* 247 */         gameMode = playerGameMode.a();
/* 248 */         gameModeName = playerGameMode.name();
/*     */       } 
/*     */       
/* 251 */       String vehicle = "NONE";
/* 252 */       float vehicleHealth = 0.0F;
/* 253 */       vg ridingEntity = player.bJ();
/* 254 */       if (ridingEntity != null) {
/*     */         
/* 256 */         vehicle = ridingEntity.h_();
/* 257 */         if ("entity.MinecartRideable.name".equals(vehicle))
/*     */         {
/* 259 */           vehicle = cey.a("entity.Minecart.name", new Object[0]);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 265 */         vehicleHealth = (ridingEntity instanceof vq) ? ((vq)ridingEntity).cd() : ((ridingEntity instanceof afe) ? (40.0F - ((afe)ridingEntity).s()) : ((ridingEntity instanceof afd) ? (40.0F - ((afd)ridingEntity).p()) : 0.0F));
/*     */       } 
/*     */       
/* 268 */       int itemUse = player.cL();
/* 269 */       int itemUseMax = player.cJ().m();
/* 270 */       int itemUsePct = (itemUseMax != 0) ? rk.d(itemUse / itemUseMax * 100.0F) : 0;
/* 271 */       int bowCharge = (player.cJ().c() == air.g) ? rk.d(ahg.b(itemUse) * 100.0F) : 0;
/*     */       
/* 273 */       storeVariable("PLAYER", player.h_());
/* 274 */       storeVariable("DISPLAYNAME", player.i_().d());
/* 275 */       storeVariable("UUID", player.bm().toString());
/* 276 */       storeVariable("HEALTH", rk.d(player.cd()));
/* 277 */       storeVariable("ARMOUR", player.cg());
/* 278 */       storeVariable("HUNGER", player.di().a());
/* 279 */       storeVariable("SATURATION", rk.f(player.di().e()));
/* 280 */       storeVariable("INVSLOT", player.bv.d + 1);
/* 281 */       storeVariable("XP", (int)(player.bR * player.dh()));
/* 282 */       storeVariable("TOTALXP", player.bQ);
/* 283 */       storeVariable("LEVEL", player.bP);
/* 284 */       storeVariable("MODE", gameMode);
/* 285 */       storeVariable("GAMEMODE", gameModeName);
/* 286 */       storeVariable("XPOS", posX);
/* 287 */       storeVariable("YPOS", posY);
/* 288 */       storeVariable("ZPOS", posZ);
/* 289 */       storeVariable("XPOSF", fposX);
/* 290 */       storeVariable("YPOSF", fposY);
/* 291 */       storeVariable("ZPOSF", fposZ);
/* 292 */       storeVariable("YAW", yaw);
/* 293 */       storeVariable("CARDINALYAW", realYaw);
/* 294 */       storeVariable("PITCH", pitch);
/* 295 */       storeVariable("DIRECTION", this.facingDirection);
/* 296 */       storeVariable("LIGHT", this.mc.f.j(pos));
/* 297 */       storeVariable("DIMENSION", getNameForDimension(player.am, player.l));
/* 298 */       storeVariable("OXYGEN", player.aZ());
/* 299 */       storeVariable("BIOME", biomeName);
/* 300 */       storeVariable("VEHICLE", vehicle);
/* 301 */       storeVariable("VEHICLEHEALTH", rk.d(vehicleHealth));
/* 302 */       storeVariable("FLYING", player.bO.b);
/* 303 */       storeVariable("CANFLY", player.bO.c);
/* 304 */       storeVariable("ATTACKPOWER", rk.d(player.n(partialTicks) * 100.0F));
/* 305 */       storeVariable("ATTACKSPEED", rk.d(player.dr()));
/* 306 */       storeVariable("ITEMUSEPCT", itemUsePct);
/* 307 */       storeVariable("ITEMUSETICKS", itemUse);
/* 308 */       storeVariable("BOWCHARGE", bowCharge);
/*     */       
/* 310 */       storeVariable("CONTAINERSLOTS", this.slotHelper.getContainerSize());
/*     */       
/* 312 */       ua difficulty = getDifficulty(player, pos);
/* 313 */       int diff = (int)(difficulty.b() * 100.0F);
/* 314 */       storeVariable("LOCALDIFFICULTY", diff);
/*     */     }
/*     */     else {
/*     */       
/* 318 */       storeVariable("PLAYER", "Player");
/* 319 */       storeVariable("DISPLAYNAME", "Player");
/* 320 */       storeVariable("UUID", "");
/* 321 */       storeVariable("HEALTH", 20);
/* 322 */       storeVariable("ARMOUR", 20);
/* 323 */       storeVariable("HUNGER", 20);
/* 324 */       storeVariable("SATURATION", 0);
/* 325 */       storeVariable("INVSLOT", 1);
/* 326 */       storeVariable("XP", 0);
/* 327 */       storeVariable("TOTALXP", 0);
/* 328 */       storeVariable("LEVEL", 0);
/* 329 */       storeVariable("MODE", 0);
/* 330 */       storeVariable("GAMEMODE", "NOT_SET");
/* 331 */       storeVariable("LIGHT", 15);
/* 332 */       storeVariable("XPOS", 0);
/* 333 */       storeVariable("YPOS", 0);
/* 334 */       storeVariable("ZPOS", 0);
/* 335 */       storeVariable("XPOSF", "0.000");
/* 336 */       storeVariable("YPOSF", "0.000");
/* 337 */       storeVariable("ZPOSF", "0.000");
/* 338 */       storeVariable("YAW", 0);
/* 339 */       storeVariable("CARDINALYAW", 180);
/* 340 */       storeVariable("PITCH", 0);
/* 341 */       storeVariable("DIRECTION", "S");
/* 342 */       storeVariable("DIMENSION", "SURFACE");
/* 343 */       storeVariable("OXYGEN", 0);
/* 344 */       storeVariable("BIOME", "UNKNOWN");
/* 345 */       storeVariable("VEHICLE", "NONE");
/* 346 */       storeVariable("VEHICLEHEALTH", 0);
/* 347 */       storeVariable("FLYING", false);
/* 348 */       storeVariable("CANFLY", false);
/* 349 */       storeVariable("ATTACKPOWER", 100);
/* 350 */       storeVariable("ATTACKSPEED", 5);
/* 351 */       storeVariable("ITEMUSEPCT", 0);
/* 352 */       storeVariable("ITEMUSETICKS", 0);
/* 353 */       storeVariable("BOWCHARGE", 0);
/*     */       
/* 355 */       storeVariable("CONTAINERSLOTS", 0);
/*     */       
/* 357 */       storeVariable("LOCALDIFFICULTY", 0);
/*     */     } 
/*     */     
/* 360 */     updateArmourSlotVars(player, 0, "BOOTS");
/* 361 */     updateArmourSlotVars(player, 1, "LEGGINGS");
/* 362 */     updateArmourSlotVars(player, 2, "CHESTPLATE");
/* 363 */     updateArmourSlotVars(player, 3, "HELM");
/*     */     
/* 365 */     updateEquipmentVars(player, vl.a, "", partialTicks);
/* 366 */     updateEquipmentVars(player, vl.a, "MAINHAND", partialTicks);
/* 367 */     updateEquipmentVars(player, vl.b, "OFFHAND", partialTicks);
/*     */     
/* 369 */     updateHitVars(player);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateEquipmentVars(bud thePlayer, vl hand, String prefix, float partialTicks) {
/* 374 */     if (thePlayer != null && thePlayer.b(hand) != null) {
/*     */       
/* 376 */       aio cooldownTracker = thePlayer.dt();
/* 377 */       aip currentItem = thePlayer.b(hand);
/* 378 */       String itemCode = currentItem.c().a(currentItem);
/* 379 */       String idFromItem = Game.getItemName(currentItem.c());
/* 380 */       String itemId = String.format("%s:%d", new Object[] { idFromItem, Integer.valueOf(currentItem.g() ? currentItem.j() : 0) });
/* 381 */       if (itemCode == null) itemCode = "";
/*     */       
/* 383 */       storeVariable(prefix + "ITEM", idFromItem);
/* 384 */       storeVariable(prefix + "ITEMIDDMG", itemId);
/* 385 */       storeVariable(prefix + "ITEMCODE", itemCode);
/* 386 */       storeVariable(prefix + "ITEMNAME", currentItem.r());
/* 387 */       storeVariable(prefix + "DURABILITY", currentItem.k() - currentItem.j());
/* 388 */       storeVariable(prefix + "ITEMDAMAGE", currentItem.k());
/* 389 */       storeVariable(prefix + "STACKSIZE", currentItem.E());
/* 390 */       storeVariable(prefix + "COOLDOWN", rk.d(cooldownTracker.a(currentItem.c(), partialTicks) * 100.0F));
/*     */     }
/*     */     else {
/*     */       
/* 394 */       storeVariable(prefix + "ITEM", Game.getItemName(null));
/* 395 */       storeVariable(prefix + "ITEMIDDMG", "air:0");
/* 396 */       storeVariable(prefix + "ITEMCODE", "air");
/* 397 */       storeVariable(prefix + "ITEMNAME", "Hand");
/* 398 */       storeVariable(prefix + "DURABILITY", 0);
/* 399 */       storeVariable(prefix + "ITEMDAMAGE", 0);
/* 400 */       storeVariable(prefix + "STACKSIZE", 0);
/* 401 */       storeVariable(prefix + "COOLDOWN", 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateHitVars(bud player) {
/* 407 */     bhc objectHit = this.mc.s;
/* 408 */     int blockDamage = 0;
/* 409 */     boolean clearSign = true;
/*     */     
/* 411 */     if (player != null && objectHit != null && objectHit.a == bhc.a.b) {
/*     */       
/* 413 */       et blockPos = objectHit.a();
/* 414 */       awt blockState = this.mc.f.o(blockPos);
/* 415 */       awt actualState = blockState.c((amy)this.mc.f, blockPos);
/* 416 */       aow block = blockState.u();
/* 417 */       aip item = block.a((amu)this.mc.f, blockPos, actualState);
/* 418 */       int blockMeta = block.d(blockState);
/*     */       
/* 420 */       String displayName = block.c();
/*     */       
/*     */       try {
/* 423 */         displayName = item.r();
/*     */       }
/* 425 */       catch (Exception exception) {}
/*     */       
/* 427 */       storeVariable("HIT", "TILE");
/* 428 */       storeVariable("HITNAME", displayName);
/* 429 */       storeVariable("HITID", Game.getBlockName(block));
/* 430 */       storeVariable("HITDATA", blockMeta);
/* 431 */       storeVariable("HITUUID", "");
/*     */       
/* 433 */       storeVariable("HITX", blockPos.p());
/* 434 */       storeVariable("HITY", blockPos.q());
/* 435 */       storeVariable("HITZ", blockPos.r());
/* 436 */       storeVariable("HITSIDE", getSideName(objectHit.b.a()));
/*     */       
/* 438 */       this.hitTracker.update(actualState);
/*     */       
/* 440 */       clearSign = updateTileEntityVars(blockPos, block);
/*     */       
/* 442 */       Map<Integer, oh> damagedBlocks = ((IRenderGlobal)this.mc.g).getDamageProgressMap();
/* 443 */       if (damagedBlocks != null)
/*     */       {
/* 445 */         for (oh damage : damagedBlocks.values()) {
/*     */           
/* 447 */           if (damage.b().equals(blockPos)) {
/*     */             
/* 449 */             blockDamage = Math.max(0, damage.c());
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 455 */     } else if (player != null && objectHit != null && objectHit.a == bhc.a.c && objectHit.d != null) {
/*     */       
/* 457 */       storeVariable("HIT", (objectHit.d instanceof aed) ? "PLAYER" : "ENTITY");
/* 458 */       storeVariable("HITNAME", EntityUtil.getEntityName(objectHit.d));
/* 459 */       storeVariable("HITID", EntityUtil.getEntityId(objectHit.d));
/* 460 */       storeVariable("HITDATA", 0);
/* 461 */       storeVariable("HITUUID", EntityUtil.getEntityUUID(objectHit.d));
/*     */       
/* 463 */       storeVariable("HITX", 0);
/* 464 */       storeVariable("HITY", 0);
/* 465 */       storeVariable("HITZ", 0);
/* 466 */       storeVariable("HITSIDE", "?");
/*     */       
/* 468 */       this.hitTracker.update(null);
/*     */     }
/*     */     else {
/*     */       
/* 472 */       storeVariable("HIT", "NONE");
/* 473 */       storeVariable("HITNAME", "None");
/* 474 */       storeVariable("HITID", Game.getItemName(null));
/* 475 */       storeVariable("HITDATA", 0);
/* 476 */       storeVariable("HITUUID", "");
/*     */       
/* 478 */       storeVariable("HITX", 0);
/* 479 */       storeVariable("HITY", 0);
/* 480 */       storeVariable("HITZ", 0);
/* 481 */       storeVariable("HITSIDE", "?");
/*     */       
/* 483 */       this.hitTracker.update(null);
/*     */     } 
/*     */     
/* 486 */     if (clearSign) {
/*     */       
/* 488 */       this.sign = null;
/* 489 */       setCachedVariable("SIGNTEXT", SIGN_LINES_EMPTY);
/*     */     } 
/*     */     
/* 492 */     storeVariable("HITPROGRESS", blockDamage);
/* 493 */     this.signUpdateTicks++;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean updateTileEntityVars(et blockPos, aow block) {
/* 498 */     if (block instanceof ats) {
/*     */       
/* 500 */       avj teSign = this.mc.f.r(blockPos);
/* 501 */       if (teSign instanceof awc) {
/*     */         
/* 503 */         if (teSign != this.sign || this.signUpdateTicks > 50) {
/*     */           
/* 505 */           this.signUpdateTicks = 0;
/* 506 */           this.sign = (awc)teSign;
/*     */           
/* 508 */           for (int pos = 0; pos < 4; pos++)
/*     */           {
/* 510 */             this.signLines[pos] = readSignLine(this.sign, pos);
/*     */           }
/*     */         } 
/*     */         
/* 514 */         setCachedVariable("SIGNTEXT", this.signLines);
/* 515 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 527 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   static String readSignLine(awc sign, int pos) {
/* 532 */     return (sign.a[pos] != null) ? sign.a[pos].d() : "";
/*     */   }
/*     */ 
/*     */   
/*     */   ua getDifficulty(bud thePlayer, et pos) {
/* 537 */     ua difficulty = this.mc.f.D(pos);
/*     */     
/* 539 */     if (this.mc.D() && this.mc.F() != null) {
/*     */       
/* 541 */       oq localPlayer = this.mc.F().am().a(this.mc.h.bm());
/*     */       
/* 543 */       if (localPlayer != null)
/*     */       {
/* 545 */         difficulty = localPlayer.l.D(new et((vg)localPlayer));
/*     */       }
/*     */     } 
/*     */     
/* 549 */     return difficulty;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/* 555 */     return getCachedValue(variableName);
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
/*     */   public void updateArmourSlotVars(bud thePlayer, int armourSlot, String armourName) {
/* 567 */     aip armourItem = (thePlayer != null) ? thePlayer.bv.g(armourSlot) : null;
/* 568 */     storeVariable(armourName + "ID", (armourItem != null) ? Game.getItemName(armourItem.c()) : "");
/* 569 */     storeVariable(armourName + "NAME", (armourItem != null) ? armourItem.r() : "None");
/* 570 */     storeVariable(armourName + "DURABILITY", (armourItem != null) ? (armourItem.k() - armourItem.j()) : 0);
/* 571 */     storeVariable(armourName + "DAMAGE", (armourItem != null) ? armourItem.k() : 0);
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
/*     */   public static String getNameForDimension(int dimension, amu worldObj) {
/* 583 */     switch (dimension) {
/*     */       case -1:
/* 585 */         return "NETHER";
/* 586 */       case 0: return "SURFACE";
/* 587 */       case 1: return "END";
/*     */     } 
/* 589 */     if (worldObj == null || worldObj.s == null)
/*     */     {
/* 591 */       return "UNKNOWN";
/*     */     }
/* 593 */     return ("" + worldObj.s.q().b()).toUpperCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getNameOfCurrentGuiScreen() {
/* 604 */     blk currentScreen = this.mc.m;
/* 605 */     if (currentScreen == null) return "NONE";
/*     */     
/* 607 */     Class<? extends blk> screenClass = blk.class;
/* 608 */     Class<? extends blk> currentClass = (Class)currentScreen.getClass();
/*     */ 
/*     */     
/* 611 */     for (Map.Entry<Class<? extends blk>, String> classMapping : GUICLASSES.entrySet()) {
/*     */       
/* 613 */       if (((Class)classMapping.getKey()).isAssignableFrom(currentClass) && !((Class)classMapping.getKey()).isAssignableFrom(screenClass))
/*     */       {
/* 615 */         screenClass = classMapping.getKey();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 620 */     if (screenClass.equals(blk.class))
/*     */     {
/* 622 */       return currentScreen.getClass().getSimpleName().toUpperCase();
/*     */     }
/*     */     
/* 625 */     return GUICLASSES.get(screenClass);
/*     */   }
/*     */ 
/*     */   
/*     */   static String getSideName(int sideHit) {
/* 630 */     if (sideHit > -1 && sideHit < 7)
/*     */     {
/* 632 */       return BLOCK_SIDES[sideHit];
/*     */     }
/*     */     
/* 635 */     return "?";
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\variable\providers\VariableProviderPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */