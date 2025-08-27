/*     */ package net.eq2online.macros;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import brz;
/*     */ import bsc;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.SpamFilter;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.core.settings.Settings;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.list.FriendListEntry;
/*     */ import net.eq2online.macros.gui.list.OnlineUserListEntry;
/*     */ import net.eq2online.macros.gui.screens.GuiAutoDiscoverStatus;
/*     */ import net.eq2online.macros.interfaces.IChatEventListener;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ import rp;
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
/*     */ public class AutoDiscoveryHandler
/*     */   implements ISettingsObserver, IChatEventListener
/*     */ {
/*     */   protected final Macros macros;
/*     */   protected final bib mc;
/*     */   
/*     */   enum AutoDiscoveryState
/*     */   {
/*  48 */     None,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  53 */     EvaluateLocalPlayers,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     ExecuteList,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     WaitingForList,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     ExecuteWho,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     WaitingForWho,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     ExecuteListTowns,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     WaitingForTowns,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     ExecuteListHomes,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     WaitingForHomes,
/*     */     
/*  95 */     ExecuteListWarps,
/*     */     
/*  97 */     WaitingForWarps,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     Finished,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     Failed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   protected AutoDiscoveryState state = AutoDiscoveryState.None;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   protected int timer = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MacroParam<?> param;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiAutoDiscoverStatus status;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean active;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   protected List<String> items = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   protected int responseTime = 60;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean requireHomeCount;
/*     */ 
/*     */ 
/*     */   
/*     */   public String cmdTownList;
/*     */ 
/*     */ 
/*     */   
/*     */   public String cmdHomeList;
/*     */ 
/*     */ 
/*     */   
/*     */   public String cmdWarps;
/*     */ 
/*     */ 
/*     */   
/*     */   public String cmdWarpMorePages;
/*     */ 
/*     */ 
/*     */   
/* 178 */   protected String[] ignore = new String[0];
/*     */ 
/*     */ 
/*     */   
/*     */   protected String townRegex;
/*     */ 
/*     */   
/*     */   protected String homeCountRegex;
/*     */ 
/*     */   
/* 188 */   protected int homeCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean gotHomeCount = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   protected String homeList = "";
/*     */   
/* 200 */   protected String warpList = "";
/*     */   
/*     */   protected String warpPageRegex;
/*     */   protected String warpIgnoreRegex;
/* 204 */   protected int warpsPages = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean warpPagesQueried = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private SpamFilter spamFilter;
/*     */ 
/*     */ 
/*     */   
/*     */   public AutoDiscoveryHandler(Macros macros, bib mc) {
/* 217 */     this.macros = macros;
/* 218 */     this.mc = mc;
/*     */     
/* 220 */     onClearSettings();
/* 221 */     this.macros.getChatHandler().registerListener(this);
/* 222 */     this.macros.getSettingsHandler().registerObserver((IObserver)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpamFilter(SpamFilter spamFilter) {
/* 227 */     this.spamFilter = spamFilter;
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
/*     */   public void activate(MacroParam<?> param) {
/* 239 */     if (this.active) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 244 */     this.active = true;
/* 245 */     this.param = param;
/*     */     
/* 247 */     this.items.clear();
/*     */     
/* 249 */     this.status = new GuiAutoDiscoverStatus(this.macros, this.mc, this);
/* 250 */     this.mc.a((blk)this.status);
/*     */     
/* 252 */     gotoState(AutoDiscoveryState.None);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 257 */     return this.active;
/*     */   }
/*     */ 
/*     */   
/*     */   public void retry() {
/* 262 */     cancel();
/* 263 */     activate(this.param);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() {
/* 271 */     if (this.active) {
/*     */       
/* 273 */       this.active = false;
/* 274 */       gotoState(AutoDiscoveryState.None);
/* 275 */       this.param.autoPopulateCancelled();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 284 */     this.param.autoPopulateComplete(this.items);
/* 285 */     cancel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finish() {
/* 293 */     if (this.active)
/*     */     {
/* 295 */       if (this.items.size() == 0) {
/*     */         
/* 297 */         gotoState(AutoDiscoveryState.Failed);
/* 298 */         this.status.notifyFailed();
/*     */       }
/*     */       else {
/*     */         
/* 302 */         close();
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
/*     */   protected void gotoState(AutoDiscoveryState state) {
/* 314 */     this.timer = 0;
/* 315 */     this.state = state;
/*     */     
/* 317 */     if (state == AutoDiscoveryState.None)
/*     */     {
/* 319 */       this.items.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 328 */     if (isActive()) {
/*     */       
/* 330 */       this.timer++;
/*     */       
/* 332 */       if (this.param.isType(MacroParam.Type.TOWN)) {
/*     */         
/* 334 */         switch (this.state) {
/*     */           
/*     */           case None:
/* 337 */             gotoState(AutoDiscoveryState.ExecuteListTowns);
/*     */ 
/*     */           
/*     */           case ExecuteListTowns:
/* 341 */             executeListTowns();
/*     */ 
/*     */           
/*     */           case WaitingForTowns:
/* 345 */             if (this.timer > this.responseTime)
/*     */             {
/* 347 */               gotoState(AutoDiscoveryState.Finished);
/*     */             }
/*     */ 
/*     */           
/*     */           case Finished:
/* 352 */             finish();
/*     */ 
/*     */           
/*     */           case Failed:
/*     */             return;
/*     */         } 
/*     */         
/* 359 */         cancel();
/*     */       } 
/*     */ 
/*     */       
/* 363 */       if (this.param.isType(MacroParam.Type.WARP)) {
/*     */         String[] warps; int i;
/* 365 */         switch (this.state) {
/*     */           
/*     */           case None:
/* 368 */             gotoState(AutoDiscoveryState.ExecuteListWarps);
/*     */ 
/*     */           
/*     */           case ExecuteListWarps:
/* 372 */             executeListWarps();
/*     */ 
/*     */           
/*     */           case WaitingForWarps:
/* 376 */             if (this.timer > this.responseTime)
/*     */             {
/* 378 */               gotoState(AutoDiscoveryState.Finished);
/*     */             }
/*     */ 
/*     */           
/*     */           case Finished:
/* 383 */             warps = this.warpList.split(", ");
/*     */             
/* 385 */             for (i = 0; i < warps.length; i++) {
/*     */               
/* 387 */               if (!warps[i].equals("")) addItem(warps[i]); 
/*     */             } 
/* 389 */             finish();
/*     */ 
/*     */           
/*     */           case Failed:
/*     */           
/*     */         } 
/*     */         
/* 396 */         cancel();
/*     */       } 
/*     */ 
/*     */       
/* 400 */       if (this.param.isType(MacroParam.Type.HOME)) {
/*     */         String[] homes; int i;
/* 402 */         switch (this.state) {
/*     */           
/*     */           case None:
/* 405 */             gotoState(AutoDiscoveryState.ExecuteListHomes);
/*     */ 
/*     */           
/*     */           case ExecuteListHomes:
/* 409 */             executeListHomes();
/*     */ 
/*     */           
/*     */           case WaitingForHomes:
/* 413 */             if (this.timer > this.responseTime || (this.gotHomeCount && this.requireHomeCount && this.homeCount < 1))
/*     */             {
/* 415 */               gotoState(AutoDiscoveryState.Finished);
/*     */             }
/*     */ 
/*     */           
/*     */           case Finished:
/* 420 */             homes = this.homeList.split(", ");
/*     */             
/* 422 */             for (i = 0; i < homes.length; i++) {
/*     */               
/* 424 */               if (!homes[i].equals("")) addItem(homes[i]); 
/*     */             } 
/* 426 */             finish();
/*     */ 
/*     */           
/*     */           case Failed:
/*     */           
/*     */         } 
/*     */         
/* 433 */         cancel();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 439 */       finish();
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
/*     */   protected void addItem(String item) {
/* 451 */     for (int i = 0; i < this.ignore.length; i++) {
/*     */       
/* 453 */       if (this.ignore[i].equalsIgnoreCase(item))
/*     */         return; 
/*     */     } 
/* 456 */     if (!this.items.contains(item))
/*     */     {
/* 458 */       this.items.add(item);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void executeListTowns() {
/* 468 */     this.spamFilter.sendChatPacket(this.cmdTownList);
/* 469 */     gotoState(AutoDiscoveryState.WaitingForTowns);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeListWarps() {
/* 474 */     this.warpList = "";
/* 475 */     this.warpsPages = 1;
/* 476 */     this.warpPagesQueried = false;
/*     */     
/* 478 */     this.spamFilter.sendChatPacket(this.cmdWarps);
/* 479 */     gotoState(AutoDiscoveryState.WaitingForWarps);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeListWarpPage() {
/* 484 */     if (!this.warpPagesQueried) {
/*     */       
/* 486 */       this.warpPagesQueried = true;
/*     */       
/* 488 */       for (int warpPage = 2; warpPage <= this.warpsPages; warpPage++) {
/*     */         
/* 490 */         this.spamFilter.sendChatPacket(String.format(this.cmdWarpMorePages, new Object[] { Integer.valueOf(warpPage++) }));
/*     */       } 
/*     */       
/* 493 */       gotoState(AutoDiscoveryState.WaitingForWarps);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void executeListHomes() {
/* 502 */     this.homeCount = 0;
/* 503 */     this.gotHomeCount = !this.requireHomeCount;
/* 504 */     this.homeList = "";
/*     */     
/* 506 */     this.spamFilter.sendChatPacket(this.cmdHomeList);
/* 507 */     gotoState(AutoDiscoveryState.WaitingForHomes);
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
/*     */   public void onChatMessage(String chatMessage, String chatMessageNoColours) {
/* 519 */     if (isActive())
/*     */     {
/* 521 */       switch (this.state) {
/*     */         case WaitingForTowns:
/* 523 */           processChatMessageAsTownList(chatMessage, chatMessageNoColours); break;
/* 524 */         case WaitingForHomes: processChatMessageAsHomeList(chatMessage, chatMessageNoColours); break;
/* 525 */         case WaitingForWarps: processChatMessageAsWarpList(chatMessage, chatMessageNoColours);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSendChatMessage(String message) {
/* 534 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLogMessage(String message) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processChatMessageAsTownList(String chatMessage, String chatMessageNoColours) {
/*     */     try {
/* 552 */       Matcher townPatternMatcher = Pattern.compile(this.townRegex).matcher(chatMessage);
/* 553 */       while (townPatternMatcher.find())
/*     */       {
/* 555 */         addItem(townPatternMatcher.group(1));
/*     */       }
/*     */     }
/* 558 */     catch (Exception exception) {}
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
/*     */   protected void processChatMessageAsHomeList(String chatMessage, String chatMessageNoColours) {
/*     */     try {
/* 572 */       if (this.gotHomeCount) {
/*     */         
/* 574 */         this.homeList += chatMessageNoColours;
/* 575 */         if (!this.requireHomeCount && (this.homeList.split(", ")).length >= this.homeCount)
/*     */         {
/* 577 */           gotoState(AutoDiscoveryState.Finished);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 582 */         Matcher homeCountPatternMatcher = Pattern.compile(this.homeCountRegex).matcher(chatMessage);
/*     */         
/* 584 */         if (homeCountPatternMatcher.matches())
/*     */         {
/* 586 */           this.homeCount = Integer.parseInt(homeCountPatternMatcher.group(1));
/* 587 */           this.gotHomeCount = true;
/*     */         }
/*     */       
/*     */       } 
/* 591 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processChatMessageAsWarpList(String chatMessage, String chatMessageNoColours) {
/* 597 */     Matcher warpListIgnorePatternMatcher = Pattern.compile(this.warpIgnoreRegex, 2).matcher(chatMessage);
/*     */     
/* 599 */     if (!warpListIgnorePatternMatcher.find()) {
/*     */       
/* 601 */       Matcher warpListPagePatternMatcher = Pattern.compile(this.warpPageRegex, 2).matcher(chatMessage);
/*     */       
/* 603 */       if (warpListPagePatternMatcher.find()) {
/*     */         
/* 605 */         this.warpsPages = Integer.parseInt(warpListPagePatternMatcher.group(1));
/* 606 */         this.warpList += ", ";
/* 607 */         executeListWarpPage();
/*     */       }
/*     */       else {
/*     */         
/* 611 */         this.warpList += chatMessageNoColours;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void populateUserListBox(GuiListBox<String> userListBox, boolean friends) {
/* 618 */     Settings settings = this.macros.getSettings();
/*     */     
/* 620 */     brz netclienthandler = this.mc.h.d;
/* 621 */     Collection<bsc> playerList = netclienthandler.d();
/* 622 */     int playerId = 0;
/*     */     
/* 624 */     Pattern namePattern = Pattern.compile("[a-zA-Z0-9_]{2,16}$");
/*     */     
/* 626 */     for (bsc playerEntry : playerList) {
/*     */       
/* 628 */       String trimmedPlayerName = rp.a(playerEntry.a().getName());
/*     */       
/* 630 */       if (trimmedPlayerName.length() > settings.trimCharsUserListStart)
/*     */       {
/* 632 */         trimmedPlayerName = trimmedPlayerName.substring(settings.trimCharsUserListStart);
/*     */       }
/*     */       
/* 635 */       if (trimmedPlayerName.length() > settings.trimCharsUserListEnd)
/*     */       {
/* 637 */         trimmedPlayerName = trimmedPlayerName.substring(0, trimmedPlayerName.length() - settings.trimCharsUserListEnd);
/*     */       }
/*     */       
/* 640 */       Matcher namePatternMatcher = namePattern.matcher(trimmedPlayerName);
/*     */       
/* 642 */       if (namePatternMatcher.find()) {
/*     */         
/* 644 */         String filteredPlayerName = namePatternMatcher.group();
/*     */         
/* 646 */         if (settings.includeSelf || !filteredPlayerName.equalsIgnoreCase(this.mc.h.h_())) {
/*     */           
/* 648 */           if (friends) {
/*     */             
/* 650 */             userListBox.addItem((IListEntry)new FriendListEntry(playerId++, 0, filteredPlayerName));
/*     */             
/*     */             continue;
/*     */           } 
/* 654 */           userListBox.addItem((IListEntry)new OnlineUserListEntry(playerId++, filteredPlayerName, this.macros.getUserSkinHandler()));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 660 */     userListBox.setSortable(true).sort();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClearSettings() {
/* 666 */     this.responseTime = 60;
/*     */     
/* 668 */     this.requireHomeCount = true;
/*     */     
/* 670 */     this.cmdTownList = "/town list";
/* 671 */     this.cmdHomeList = "/listhomes";
/* 672 */     this.cmdWarps = "/warp";
/* 673 */     this.cmdWarpMorePages = "/warp %s";
/*     */     
/* 675 */     this.townRegex = "\\xa7[0-9a-f]([a-zA-Z0-9\\.\\_\\-]+)\\xa7[0-9a-f] \\[[0-9]+\\]\\xa7[0-9a-f]";
/* 676 */     this.homeCountRegex = "^\\xa7[0-9a-f]Home\\(s\\) : \\xa7[0-9a-f]([0-9]+)";
/*     */     
/* 678 */     this.warpPageRegex = "^\\xa77.+?page [0-9]+ of ([0-9]+)";
/* 679 */     this.warpIgnoreRegex = "^\\xa7c";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadSettings(ISettingsStore settings) {
/* 685 */     this.responseTime = settings.getSetting("discover.responsetimeticks", 60);
/*     */     
/* 687 */     this.requireHomeCount = settings.getSetting("discover.waitforhomecount", true);
/*     */     
/* 689 */     this.cmdTownList = settings.getSetting("discover.commandtownlist", "/town list");
/* 690 */     this.cmdHomeList = settings.getSetting("discover.commandhomelist", "/listhomes");
/* 691 */     this.cmdWarps = settings.getSetting("discover.commandwarplist", "/warp");
/* 692 */     this.cmdWarpMorePages = settings.getSetting("discover.commandwarplistpage", "/warp %s");
/*     */     
/* 694 */     this.townRegex = settings.getSetting("discover.townregex", "\\xa7[0-9a-f]([a-zA-Z0-9\\.\\_\\-]+)\\xa7[0-9a-f] \\[[0-9]+\\]\\xa7[0-9a-f]");
/*     */     
/* 696 */     this.homeCountRegex = settings.getSetting("discover.homecountregex", "^\\xa7[0-9a-f]Home\\(s\\) : \\xa7[0-9a-f]([0-9]+)");
/*     */     
/* 698 */     this.warpPageRegex = settings.getSetting("discover.warppageregex", "^\\xa77.+?page [0-9]+ of ([0-9]+)");
/* 699 */     this.warpIgnoreRegex = settings.getSetting("discover.warpignoreregex", "^\\xa7c");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSaveSettings(ISettingsStore settings) {
/* 705 */     settings.setSetting("discover.responsetimeticks", this.responseTime);
/*     */     
/* 707 */     settings.setSetting("discover.waitforhomecount", this.requireHomeCount);
/*     */     
/* 709 */     settings.setSetting("discover.commandtownlist", this.cmdTownList);
/* 710 */     settings.setSetting("discover.commandhomelist", this.cmdHomeList);
/* 711 */     settings.setSetting("discover.commandwarplist", this.cmdWarps);
/* 712 */     settings.setSetting("discover.commandwarplistpage", this.cmdWarpMorePages);
/*     */     
/* 714 */     settings.setSetting("discover.townregex", this.townRegex);
/* 715 */     settings.setSetting("discover.homecountregex", this.homeCountRegex);
/*     */     
/* 717 */     settings.setSetting("discover.warppageregex", this.warpPageRegex);
/* 718 */     settings.setSetting("discover.warpignoreregex", this.warpIgnoreRegex);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getSetting(String settingName) {
/* 724 */     if ("cmdHomeList".equals(settingName)) return (T)this.cmdHomeList; 
/* 725 */     if ("cmdTownList".equals(settingName)) return (T)this.cmdTownList; 
/* 726 */     if ("cmdWarps".equals(settingName)) return (T)this.cmdWarps; 
/* 727 */     if ("cmdWarpMorePages".equals(settingName)) return (T)this.cmdWarpMorePages; 
/* 728 */     if ("requireHomeCount".equals(settingName)) return (T)Boolean.valueOf(this.requireHomeCount);
/*     */     
/* 730 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void setSetting(String settingName, T settingValue) {
/* 735 */     if ("cmdHomeList".equals(settingName)) {
/*     */       
/* 737 */       this.cmdHomeList = (String)settingValue;
/*     */     }
/* 739 */     else if ("cmdTownList".equals(settingName)) {
/*     */       
/* 741 */       this.cmdTownList = (String)settingValue;
/*     */     }
/* 743 */     else if ("cmdWarps".equals(settingName)) {
/*     */       
/* 745 */       this.cmdWarps = (String)settingValue;
/*     */     }
/* 747 */     else if ("cmdWarpMorePages".equals(settingName)) {
/*     */       
/* 749 */       this.cmdWarpMorePages = (String)settingValue;
/*     */     }
/* 751 */     else if ("requireHomeCount".equals(settingName)) {
/*     */       
/* 753 */       this.requireHomeCount = ((Boolean)settingValue).booleanValue();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\AutoDiscoveryHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */