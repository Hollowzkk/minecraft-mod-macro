/*     */ package net.eq2online.macros.gui.helpers;
/*     */ 
/*     */ import afr;
/*     */ import afw;
/*     */ import agr;
/*     */ import ahp;
/*     */ import aip;
/*     */ import bib;
/*     */ import bmg;
/*     */ import bmp;
/*     */ import fi;
/*     */ import java.util.List;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.mixin.IContainerCreative;
/*     */ import net.eq2online.macros.core.mixin.ICreativeSlot;
/*     */ import net.eq2online.macros.core.mixin.IGuiContainer;
/*     */ import net.eq2online.macros.core.mixin.IGuiContainerCreative;
/*     */ import net.eq2online.macros.scripting.variable.ItemID;
/*     */ import uk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SlotHelper
/*     */ {
/*     */   private final bib mc;
/*     */   
/*     */   public static class SlotClick
/*     */   {
/*     */     public bmg craftingGui;
/*     */     public agr slot;
/*     */     public int slotNumber;
/*     */     public int button;
/*     */     public afw clickType;
/*     */     
/*     */     public SlotClick(bmg craftingGui, agr slot, int slotNumber, int button, boolean shift) {
/*  42 */       this(craftingGui, slot, slotNumber, button, shift ? afw.b : afw.a);
/*     */     }
/*     */ 
/*     */     
/*     */     public SlotClick(bmg craftingGui, agr slot, int slotNumber, int button, afw clickType) {
/*  47 */       this.craftingGui = craftingGui;
/*  48 */       this.slot = slot;
/*  49 */       this.slotNumber = slotNumber;
/*  50 */       this.button = button;
/*  51 */       this.clickType = clickType;
/*     */     }
/*     */ 
/*     */     
/*     */     public void execute(SlotHelper slots) {
/*  56 */       slots.survivalInventorySlotClick(this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*  62 */       return String.format("SlotClick[%s,%s,%s,%s]", new Object[] { (this.slot != null) ? Integer.valueOf(this.slot.e) : "-", Integer.valueOf(this.slotNumber), 
/*  63 */             Integer.valueOf(this.button), this.clickType });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SlotHelper(Macros macros, bib mc) {
/*  71 */     this.mc = mc;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean currentScreenIsContainer() {
/*  76 */     return (this.mc.m != null && this.mc.m instanceof bmg);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean currentScreenIsInventory() {
/*  81 */     return (this.mc.m != null && this.mc.m instanceof bmx);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean noScreenInGame() {
/*  86 */     return (this.mc.m == null && this.mc.h != null && this.mc.h.bv != null && this.mc.h.bv.a != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public bmg getGuiContainer() {
/*  94 */     if (this.mc.m instanceof bmg)
/*     */     {
/*  96 */       return (bmg)this.mc.m;
/*     */     }
/*     */     
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/* 104 */     if (!currentScreenIsContainer())
/*     */     {
/* 106 */       return 0;
/*     */     }
/*     */     
/* 109 */     bmg containerGui = getGuiContainer();
/*     */     
/* 111 */     if (containerGui instanceof bmp)
/*     */     {
/* 113 */       return 600;
/*     */     }
/*     */     
/* 116 */     return containerGui.h.c.size();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void survivalInventorySlotClick(SlotClick click) {
/* 121 */     survivalInventorySlotClick(click.craftingGui, click.slot, click.slotNumber, click.button, click.clickType);
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
/*     */   public void survivalInventorySlotClick(bmg craftingGui, agr slot, int slotNumber, int button, boolean shift) {
/* 133 */     survivalInventorySlotClick(craftingGui, slot, slotNumber, button, shift ? afw.b : afw.a);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void survivalInventorySlotClick(bmg craftingGui, agr slot, int slotNumber, int button, afw clickType) {
/*     */     try {
/* 140 */       if (craftingGui == null || craftingGui instanceof bmp)
/* 141 */         return;  ((IGuiContainer)craftingGui).mouseClick(slot, slotNumber, button, clickType);
/*     */     }
/* 143 */     catch (Exception ex) {
/*     */       
/* 145 */       Log.printStackTrace(ex);
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
/*     */   public void containerSlotClick(int slotNumber, int button, boolean shift) {
/*     */     try {
/* 158 */       if (!currentScreenIsContainer()) {
/*     */         
/* 160 */         if (noScreenInGame() && slotNumber >= 1 && slotNumber <= 9)
/*     */         {
/* 162 */           this.mc.h.bv.d = slotNumber - 1;
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 168 */       bmg containerGui = getGuiContainer();
/* 169 */       afr slots = containerGui.h;
/*     */       
/* 171 */       if (containerGui instanceof bmp)
/*     */       {
/* 173 */         if (slotNumber < 45) {
/*     */           
/* 175 */           ((IGuiContainerCreative)containerGui).setCreativeTab(ahp.n);
/* 176 */           agr slot = (slotNumber < 5) ? ((IGuiContainerCreative)containerGui).getBinSlot() : slots.a(slotNumber);
/* 177 */           ((IGuiContainerCreative)containerGui).mouseClick(slot, slotNumber, button, shift ? afw.b : afw.a);
/*     */         }
/* 179 */         else if (slotNumber < 54) {
/*     */           
/* 181 */           agr slot = slots.a(slotNumber);
/* 182 */           ((IGuiContainerCreative)containerGui).mouseClick(slot, slotNumber, button, shift ? afw.b : afw.a);
/*     */         } else {
/* 184 */           if (slotNumber > 599 && slotNumber < 700) {
/*     */             return;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 191 */           int pageNumber = slotNumber / 100 - 1;
/* 192 */           ((IGuiContainerCreative)containerGui).setCreativeTab(ahp.a[pageNumber]);
/* 193 */           slotNumber -= (pageNumber + 1) * 100;
/*     */           
/* 195 */           if (scrollContainerTo((bmp)containerGui, slotNumber))
/*     */           {
/* 197 */             slotNumber -= getCreativeInventoryScroll((bmp)containerGui);
/* 198 */             agr slot = slots.a(slotNumber);
/* 199 */             ((IGuiContainerCreative)containerGui).mouseClick(slot, slotNumber, button, shift ? afw.b : afw.a);
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 205 */       else if ((slotNumber >= 0 && slotNumber < slots.c.size()) || slotNumber == -999)
/*     */       {
/* 207 */         agr slot = (slotNumber == -999) ? null : slots.a(slotNumber);
/* 208 */         ((IGuiContainer)containerGui).mouseClick(slot, slotNumber, button, shift ? afw.b : afw.a);
/*     */       }
/*     */     
/*     */     }
/* 212 */     catch (Exception ex) {
/*     */       
/* 214 */       ex.printStackTrace();
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
/*     */   private static boolean scrollContainerTo(bmp containerGui, int slotNumber) throws IllegalArgumentException, SecurityException {
/* 228 */     float currentScroll = 0.0F;
/* 229 */     int lastInventoryScroll = setScrollPosition(containerGui, currentScroll);
/*     */     
/* 231 */     fi fi = ((IContainerCreative)containerGui.h).getItemsList();
/* 232 */     float scrollIncrement = (float)(1.0D / (fi.size() / 9 - 5 + 1));
/*     */     
/* 234 */     while (!isInRange(slotNumber, getCreativeInventoryScroll(containerGui), 45)) {
/*     */       
/* 236 */       currentScroll += scrollIncrement;
/*     */       
/* 238 */       int inventoryScroll = setScrollPosition(containerGui, currentScroll);
/* 239 */       if (inventoryScroll == lastInventoryScroll)
/*     */       {
/* 241 */         return isInRange(slotNumber, inventoryScroll, 45);
/*     */       }
/*     */     } 
/*     */     
/* 245 */     return true;
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
/*     */   protected static int setScrollPosition(bmp containerGui, float scrollPosition) {
/*     */     try {
/* 259 */       ((IGuiContainerCreative)containerGui).setScrollPosition(scrollPosition);
/* 260 */       ((IContainerCreative)containerGui.h).scrollToPosition(scrollPosition);
/* 261 */       return getCreativeInventoryScroll(containerGui);
/*     */     }
/* 263 */     catch (Exception ex) {
/*     */       
/* 265 */       ex.printStackTrace();
/*     */ 
/*     */       
/* 268 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSlotContaining(ItemID itemId, int startSlot) {
/*     */     try {
/* 279 */       if (currentScreenIsContainer()) {
/*     */         
/* 281 */         bmg containerGui = getGuiContainer();
/*     */         
/* 283 */         if (containerGui instanceof bmp) {
/*     */           
/* 285 */           int inventoryIndex = searchInventoryFor(itemId, startSlot, this.mc.h.bx);
/*     */           
/* 287 */           if (inventoryIndex < 0)
/*     */           {
/* 289 */             return searchCreativeTabsFor(itemId, startSlot);
/*     */           }
/*     */           
/* 292 */           return inventoryIndex;
/*     */         } 
/*     */         
/* 295 */         return searchInventoryFor(itemId, startSlot, containerGui.h);
/*     */       } 
/* 297 */       if (noScreenInGame())
/*     */       {
/* 299 */         for (int slot = 0; slot < 9; slot++)
/*     */         {
/* 301 */           if (stackMatchesID(itemId, (aip)this.mc.h.bv.a.get(slot)))
/*     */           {
/* 303 */             return slot + 1;
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 308 */     } catch (Exception ex) {
/*     */       
/* 310 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 313 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int searchInventoryFor(ItemID itemId, int startSlot, afr inventorySlots) {
/* 323 */     List<agr> itemStacks = inventorySlots.c;
/*     */     
/* 325 */     for (int slotContaining = startSlot; slotContaining < itemStacks.size(); slotContaining++) {
/*     */       
/* 327 */       aip slotStack = ((agr)itemStacks.get(slotContaining)).d();
/*     */       
/* 329 */       if (stackMatchesID(itemId, slotStack))
/*     */       {
/* 331 */         return slotContaining;
/*     */       }
/*     */     } 
/*     */     
/* 335 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int searchCreativeTabsFor(ItemID itemId, int startSlot) {
/* 344 */     int pageNumber = 100;
/*     */     
/* 346 */     for (ahp creativeTab : ahp.a) {
/*     */       
/* 348 */       if (creativeTab != ahp.n && creativeTab != ahp.g) {
/*     */         
/* 350 */         fi<aip> itemStacks = fi.a();
/* 351 */         creativeTab.a(itemStacks);
/*     */         
/* 353 */         for (int stackIndex = 0; stackIndex < itemStacks.size(); stackIndex++) {
/*     */           
/* 355 */           if (stackMatchesID(itemId, (aip)itemStacks.get(stackIndex)) && pageNumber + stackIndex >= startSlot)
/*     */           {
/* 357 */             return pageNumber + stackIndex;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 362 */       pageNumber += 100;
/*     */     } 
/*     */     
/* 365 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean stackMatchesID(ItemID itemId, aip slotStack) {
/* 375 */     return ((slotStack == null && itemId.item == null) || (slotStack != null && slotStack
/* 376 */       .c() == itemId.item && (itemId.damage == -1 || itemId.damage == slotStack
/* 377 */       .j())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public aip getSlotStack(int slotId) {
/*     */     try {
/* 388 */       if (currentScreenIsContainer()) {
/*     */         
/* 390 */         bmg containerGui = getGuiContainer();
/*     */         
/* 392 */         if (containerGui instanceof bmp && slotId >= 100)
/*     */         {
/* 394 */           return getStackFromCreativeTabs(slotId);
/*     */         }
/*     */         
/* 397 */         return getStackFromSurvivalInventory(slotId, containerGui.h);
/*     */       } 
/* 399 */       if (noScreenInGame() && slotId >= 1 && slotId <= 9)
/*     */       {
/* 401 */         return (aip)this.mc.h.bv.a.get(slotId - 1);
/*     */       }
/*     */     }
/* 404 */     catch (Exception ex) {
/*     */       
/* 406 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 409 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected aip getStackFromCreativeTabs(int slotId) {
/* 418 */     int pageNumber = 100;
/*     */     
/* 420 */     for (ahp creativeTab : ahp.a) {
/*     */       
/* 422 */       if (creativeTab != ahp.n && creativeTab != ahp.g) {
/*     */         
/* 424 */         fi<aip> itemStacks = fi.a();
/* 425 */         creativeTab.a(itemStacks);
/*     */         
/* 427 */         for (int stackIndex = 0; stackIndex < itemStacks.size(); stackIndex++) {
/*     */           
/* 429 */           int virtualSlotId = pageNumber + stackIndex;
/*     */           
/* 431 */           if (virtualSlotId == slotId)
/*     */           {
/* 433 */             return (aip)itemStacks.get(stackIndex);
/*     */           }
/*     */           
/* 436 */           if (virtualSlotId > slotId)
/*     */           {
/* 438 */             return null;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 443 */       pageNumber += 100;
/*     */     } 
/*     */     
/* 446 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected aip getStackFromSurvivalInventory(int slotId, afr survivalInventory) {
/* 456 */     List<agr> itemStacks = survivalInventory.c;
/*     */     
/* 458 */     if (slotId >= 0 && slotId < itemStacks.size()) {
/*     */       
/* 460 */       aip slotStack = ((agr)itemStacks.get(slotId)).d();
/*     */       
/* 462 */       if (slotStack != null)
/*     */       {
/* 464 */         return slotStack;
/*     */       }
/*     */     } 
/*     */     
/* 468 */     return null;
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
/*     */   public agr getMouseOverSlot(bmg guiContainer, int mouseX, int mouseY) {
/*     */     try {
/* 485 */       agr slot = ((IGuiContainer)guiContainer).getSlot(mouseX, mouseY);
/*     */       
/* 487 */       if (guiContainer instanceof bmp) {
/*     */         
/* 489 */         bmp creativeContainerGui = (bmp)guiContainer;
/*     */         
/* 491 */         if (creativeContainerGui.f() == ahp.n.a())
/*     */         {
/* 493 */           if (slot instanceof ICreativeSlot)
/*     */           {
/* 495 */             return ((ICreativeSlot)slot).getInnerSlot();
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/* 500 */       return slot;
/*     */     }
/* 502 */     catch (Exception exception) {
/*     */       
/* 504 */       return null;
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
/*     */   public static int getSlotIndex(bmg guiContainer, agr mouseOverSlot) {
/* 516 */     if (mouseOverSlot == null)
/*     */     {
/* 518 */       return -1;
/*     */     }
/*     */     
/* 521 */     int slotNumber = mouseOverSlot.e;
/*     */     
/* 523 */     if (guiContainer instanceof bmp)
/*     */     {
/* 525 */       if (slotNumber < 45) {
/*     */         
/* 527 */         int pageNumber = ((bmp)guiContainer).f();
/* 528 */         int scrollPosition = getCreativeInventoryScroll((bmp)guiContainer);
/* 529 */         if (pageNumber == ahp.g.a())
/*     */         {
/* 531 */           return -1;
/*     */         }
/* 533 */         if (pageNumber != ahp.n.a())
/*     */         {
/* 535 */           slotNumber += 100 * (pageNumber + 1) + scrollPosition;
/*     */         
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 541 */         slotNumber -= 9;
/*     */       } 
/*     */     }
/*     */     
/* 545 */     return slotNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getCreativeInventoryScroll(bmp containerGui) {
/*     */     try {
/* 556 */       uk creativeInventory = IGuiContainerCreative.getCreativeInventory();
/* 557 */       aip firstSlotStack = creativeInventory.a(0);
/* 558 */       fi fi = ((IContainerCreative)containerGui.h).getItemsList();
/* 559 */       return fi.indexOf(firstSlotStack);
/*     */     }
/* 561 */     catch (Exception ex) {
/*     */       
/* 563 */       ex.printStackTrace();
/*     */ 
/*     */       
/* 566 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isInRange(int value, int start, int rangeLength) {
/* 577 */     return (value >= start && value < start + rangeLength);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\helpers\SlotHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */