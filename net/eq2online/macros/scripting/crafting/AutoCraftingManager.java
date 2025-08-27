/*      */ package net.eq2online.macros.scripting.crafting;
/*      */ 
/*      */ import aec;
/*      */ import aed;
/*      */ import afr;
/*      */ import afw;
/*      */ import agr;
/*      */ import ain;
/*      */ import aip;
/*      */ import akq;
/*      */ import akt;
/*      */ import aku;
/*      */ import aow;
/*      */ import aox;
/*      */ import bhc;
/*      */ import bib;
/*      */ import blk;
/*      */ import bmg;
/*      */ import bmx;
/*      */ import bud;
/*      */ import com.mumfrey.liteloader.util.log.LiteLoaderLogger;
/*      */ import et;
/*      */ import fi;
/*      */ import java.util.ArrayList;
/*      */ import java.util.IllegalFormatException;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Queue;
/*      */ import net.eq2online.console.Log;
/*      */ import net.eq2online.macros.compatibility.I18n;
/*      */ import net.eq2online.macros.core.Macros;
/*      */ import net.eq2online.macros.core.overlays.IVanillaRecipe;
/*      */ import net.eq2online.macros.event.BuiltinEvent;
/*      */ import net.eq2online.macros.gui.helpers.SlotHelper;
/*      */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*      */ import net.eq2online.macros.scripting.variable.ItemID;
/*      */ import net.eq2online.util.Game;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class AutoCraftingManager
/*      */ {
/*   52 */   public static boolean TRACE = LiteLoaderLogger.DEBUG;
/*      */   
/*      */   static final int WAIT_FOR_GUI_TICKS = 40;
/*      */   
/*      */   static final int MAX_FAILED_TICKS = 10;
/*      */   static final int FAILED_MESSAGE_DISPLAY_TICKS = 40;
/*      */   static final int SLOT_OUTSIDE = -999;
/*      */   private final bib mc;
/*      */   private final Macros macros;
/*      */   private final SlotHelper slotHelper;
/*      */   
/*      */   abstract class CraftingAction
/*      */   {
/*      */     protected final SlotHelper slots;
/*      */     protected final AutoCraftingManager.Job job;
/*      */     protected final IVanillaRecipe recipe;
/*      */     protected final bmg craftingGui;
/*      */     
/*      */     CraftingAction(SlotHelper slots, AutoCraftingManager.Job job, bmg craftingGui) {
/*   71 */       this.slots = slots;
/*   72 */       this.job = job;
/*   73 */       this.recipe = job.getRecipe();
/*   74 */       this.craftingGui = craftingGui;
/*      */     }
/*      */ 
/*      */     
/*      */     protected final afr getContainer() {
/*   79 */       bmg gui = this.slots.getGuiContainer();
/*   80 */       if (gui != this.craftingGui)
/*      */       {
/*   82 */         AutoCraftingManager.debug("---------------> GUI MISMATCH! <----------------", new Object[0]);
/*      */       }
/*   84 */       return gui.h;
/*      */     }
/*      */     
/*      */     public abstract boolean process(); }
/*      */   
/*      */   class CraftingActionLayoutRecipe extends CraftingAction {
/*      */     private final int slotStart;
/*      */     private final int slotEnd;
/*      */     private final int craftingWidth;
/*      */     private final int rate;
/*   94 */     private int pauseTicks = 0;
/*      */ 
/*      */ 
/*      */     
/*      */     CraftingActionLayoutRecipe(SlotHelper slots, AutoCraftingManager.Job job, bmg craftingGui, afr container, int slotStart, int craftingWidth, int rate) {
/*   99 */       super(slots, job, craftingGui);
/*      */       
/*  101 */       this.slotStart = slotStart;
/*  102 */       this.craftingWidth = craftingWidth;
/*  103 */       this.slotEnd = container.b.size();
/*  104 */       this.rate = rate;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean process() {
/*  110 */       AutoCraftingManager.debug("CraftingActionLayoutRecipe::process", new Object[0]);
/*      */       
/*  112 */       if (this.pauseTicks-- > 0) {
/*      */         
/*  114 */         AutoCraftingManager.debug("Waiting %d ticks", new Object[] { Integer.valueOf(this.pauseTicks) });
/*  115 */         return false;
/*      */       } 
/*      */       
/*  118 */       afr container = getContainer();
/*      */ 
/*      */       
/*  121 */       for (int x = 0; x < this.recipe.getWidth(); x++) {
/*      */         
/*  123 */         for (int y = 0; y < this.recipe.getHeight(); y++) {
/*      */           
/*  125 */           int recipeOffset = y * this.recipe.getWidth() + x;
/*  126 */           int targetSlotNumber = y * this.craftingWidth + x + 1;
/*  127 */           akq recipeItem = (akq)this.recipe.getItems().get(recipeOffset);
/*      */           
/*  129 */           AutoCraftingManager.debug("  item at (%s, %s) [%s] is %s", new Object[] { Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(targetSlotNumber), recipeItem });
/*      */           
/*  131 */           agr targetSlot = container.a(targetSlotNumber);
/*  132 */           if (targetSlot.e()) {
/*      */             
/*  134 */             if (AutoCraftingManager.ingredientMatches(recipeItem, targetSlot.d())) {
/*      */               
/*  136 */               AutoCraftingManager.debug("    (%s, %s) Target slot already contains the correct stack: %s", new Object[] { Integer.valueOf(x), Integer.valueOf(y), recipeItem });
/*      */             }
/*      */             else {
/*      */               
/*  140 */               AutoCraftingManager.debug("    (%s, %s) Target slot contains an invalid stack %s, throwing it!", new Object[] { Integer.valueOf(x), Integer.valueOf(y), targetSlot.d() });
/*  141 */               AutoCraftingManager.this.slotClick(this.craftingGui, targetSlot, targetSlotNumber, 0, afw.a);
/*  142 */               AutoCraftingManager.this.slotClick(this.craftingGui, (agr)null, -999, 0, afw.a);
/*  143 */               this.pauseTicks = this.rate;
/*  144 */               return false;
/*      */             } 
/*      */           } else {
/*  147 */             AutoCraftingManager.debug("    (%s, %s) Target slot is empty", new Object[] { Integer.valueOf(x), Integer.valueOf(y) });
/*      */             
/*  149 */             if (recipeItem != null) {
/*      */               
/*  151 */               AutoCraftingManager.debug("    searching for %s in inventory", new Object[] { recipeItem });
/*  152 */               for (int slotNumber = this.slotStart; slotNumber < this.slotEnd; slotNumber++) {
/*      */                 
/*  154 */                 if (container.c.get(slotNumber) != null && 
/*  155 */                   AutoCraftingManager.ingredientMatches(recipeItem, container.a(slotNumber).d())) {
/*      */                   
/*  157 */                   AutoCraftingManager.debug("      Found %s in slotNumber %d", new Object[] { recipeItem, Integer.valueOf(slotNumber) });
/*      */ 
/*      */                   
/*  160 */                   agr fromSlot = container.a(slotNumber);
/*      */ 
/*      */ 
/*      */                   
/*  164 */                   AutoCraftingManager.this.slotClick(this.craftingGui, fromSlot, slotNumber, 0, afw.a);
/*  165 */                   AutoCraftingManager.this.slotClick(this.craftingGui, targetSlot, targetSlotNumber, 1, afw.a);
/*  166 */                   AutoCraftingManager.this.slotClick(this.craftingGui, fromSlot, slotNumber, 0, afw.a);
/*  167 */                   this.pauseTicks = this.rate;
/*  168 */                   return false;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  175 */       AutoCraftingManager.debug("CraftingActionLayoutRecipe::process completed laying out recipe", new Object[0]);
/*  176 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class CraftingActionTakeOutput
/*      */     extends CraftingAction
/*      */   {
/*      */     private final int outputSlot;
/*      */     private boolean pendingThrow;
/*      */     private int tick;
/*      */     private int craftedAmount;
/*      */     
/*      */     CraftingActionTakeOutput(SlotHelper slots, AutoCraftingManager.Job job, bmg craftingGui, afr container, int outputSlot) {
/*  190 */       super(slots, job, craftingGui);
/*  191 */       this.outputSlot = outputSlot;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean process() {
/*  197 */       AutoCraftingManager.debug("CraftingActionTakeOutput::process", new Object[0]);
/*      */       
/*  199 */       this.tick++;
/*      */       
/*  201 */       AutoCraftingManager.debug("Take output is processing CRAFTED=%d TICK=%d", new Object[] { Integer.valueOf(this.craftedAmount), Integer.valueOf(this.tick) });
/*  202 */       afr container = (this.slots.getGuiContainer()).h;
/*  203 */       agr output = container.a(this.outputSlot);
/*  204 */       if (this.craftedAmount == 0) {
/*      */         
/*  206 */         if (this.tick < 3)
/*      */         {
/*  208 */           return false;
/*      */         }
/*      */         
/*  211 */         AutoCraftingManager.debug("Take output is waiting for craft result", new Object[0]);
/*  212 */         if (!output.d().b() && this.job.getRecipeOutput().a(output.d())) {
/*      */           
/*  214 */           this.tick = 0;
/*  215 */           this.craftedAmount = output.d().E();
/*  216 */           AutoCraftingManager.debug("Take output found output with %d", new Object[] { Integer.valueOf(this.craftedAmount) });
/*  217 */           AutoCraftingManager.this.slotClick(this.craftingGui, output, 0, 0, !this.job.throwResult());
/*  218 */           this.pendingThrow = this.job.throwResult();
/*      */         } 
/*      */         
/*  221 */         boolean isCompleted = (this.tick >= 10);
/*  222 */         if (isCompleted)
/*      */         {
/*  224 */           this.job.onCrafted(0);
/*      */         }
/*  226 */         AutoCraftingManager.debug("Take output is returning %s", new Object[] { Boolean.valueOf(isCompleted) });
/*  227 */         return isCompleted;
/*      */       } 
/*      */       
/*  230 */       if (this.pendingThrow) {
/*      */         
/*  232 */         if (this.tick > 1) {
/*      */ 
/*      */           
/*  235 */           AutoCraftingManager.debug("Take output is throwing result!", new Object[0]);
/*  236 */           AutoCraftingManager.this.slotClick(this.craftingGui, (agr)null, -999, 0, afw.a);
/*  237 */           this.pendingThrow = false;
/*  238 */           this.tick = 0;
/*      */         } 
/*      */         
/*  241 */         return false;
/*      */       } 
/*      */       
/*  244 */       if (!output.d().b()) {
/*      */         
/*  246 */         if (this.tick >= 5) {
/*      */           
/*  248 */           this.tick = 0;
/*  249 */           this.craftedAmount = 0;
/*      */         } 
/*      */         
/*  252 */         return false;
/*      */       } 
/*      */       
/*  255 */       if (this.tick > 3) {
/*      */         
/*  257 */         AutoCraftingManager.debug("Take output completed, raising onCrafted with %d", new Object[] { Integer.valueOf(this.craftedAmount) });
/*  258 */         this.job.onCrafted(this.craftedAmount);
/*  259 */         return true;
/*      */       } 
/*      */       
/*  262 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Status
/*      */   {
/*  270 */     IDLE("crafting.status.idle"),
/*  271 */     WAITING("crafting.status.waiting"),
/*  272 */     CALCULATE("crafting.status.calculate"),
/*  273 */     WAITING_FOR_INVENTORY("crafting.status.waiting.inventory"),
/*  274 */     WAITING_FOR_WORKBENCH("crafting.status.waiting.workbench"),
/*  275 */     REQUESTED_WORKBENCH("crafting.status.opening.workbench"),
/*  276 */     CRAFTING("crafting.status.crafting"),
/*  277 */     CANCELLED("crafting.status.cancelled"),
/*  278 */     DONE("crafting.status.done");
/*      */     
/*      */     private final String message;
/*      */ 
/*      */     
/*      */     Status(String message) {
/*  284 */       this.message = message;
/*      */     }
/*      */     
/*      */     public String getMessage()
/*      */     {
/*  289 */       return this.message; } } public static class Job { private final SlotHelper slots; private final AutoCraftingToken token; private final IVanillaRecipe recipe; private final int autoCraftRate; private final int total; private final boolean needsTable; private final boolean throwResult; private final boolean verbose; public enum Status { public String getMessage() { return this.message; } IDLE("crafting.status.idle"), WAITING("crafting.status.waiting"), CALCULATE("crafting.status.calculate"), WAITING_FOR_INVENTORY("crafting.status.waiting.inventory"),
/*      */       WAITING_FOR_WORKBENCH("crafting.status.waiting.workbench"),
/*      */       REQUESTED_WORKBENCH("crafting.status.opening.workbench"),
/*      */       CRAFTING("crafting.status.crafting"),
/*      */       CANCELLED("crafting.status.cancelled"),
/*      */       DONE("crafting.status.done"); private final String message; Status(String message) { this.message = message; } }
/*  295 */     public enum FailureType { GENERAL("crafting.fail.general"),
/*  296 */       UNEXPECTED("crafting.fail.unexpected"),
/*  297 */       NORECIPE("crafting.fail.norecipe"),
/*  298 */       TIMEOUT("crafting.fail.timeout"),
/*  299 */       INGREDIENTS("crafting.fail.ingredients"),
/*  300 */       NOGRID("crafting.fail.nogrid", true),
/*  301 */       WORKBENCH("crafting.fail.workbench"),
/*  302 */       NOSPACE("crafting.fail.nospace"),
/*  303 */       CANCELLED("crafting.fail.cancelled");
/*      */ 
/*      */ 
/*      */       
/*      */       private final String message;
/*      */ 
/*      */ 
/*      */       
/*      */       private final boolean isTerminal;
/*      */ 
/*      */ 
/*      */       
/*      */       FailureType(String failedMessage, boolean isTerminal) {
/*  316 */         this.message = failedMessage;
/*  317 */         this.isTerminal = isTerminal;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getMessage() {
/*  322 */         return this.message;
/*      */       }
/*      */ 
/*      */       
/*      */       boolean isTerminal() {
/*  327 */         return this.isTerminal;
/*      */       } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  360 */     private Status status = Status.IDLE;
/*      */     
/*  362 */     private FailureType failure = FailureType.GENERAL;
/*      */     
/*      */     private boolean isCrafting = false;
/*      */     
/*  366 */     private int phase = 0;
/*      */     
/*  368 */     private AutoCraftingManager.CraftingAction action = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int amount;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean requestedTable = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  383 */     private int waitingForGui = 40;
/*      */     
/*  385 */     private int failedMessageTicks = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  390 */     private int craftingFailedTicks = 0;
/*      */ 
/*      */     
/*      */     Job(SlotHelper slots, AutoCraftingToken token, IVanillaRecipe recipe, int amount, boolean throwResult, boolean verbose, int autoCraftRate) {
/*  394 */       if (recipe == null || amount < 1)
/*      */       {
/*  396 */         throw new IllegalArgumentException("Invalid crafting request: RECIPE=" + recipe + " AMOUNT=" + amount);
/*      */       }
/*      */       
/*  399 */       this.slots = slots;
/*  400 */       this.token = token;
/*  401 */       this.recipe = recipe;
/*  402 */       this.total = this.amount = amount;
/*  403 */       this.needsTable = recipe.requiresCraftingTable();
/*  404 */       this.throwResult = throwResult;
/*  405 */       this.verbose = verbose;
/*  406 */       this.autoCraftRate = autoCraftRate;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  412 */       return String.format("Job[%s,%s]", new Object[] { this.recipe.b(), getProgressString() });
/*      */     }
/*      */ 
/*      */     
/*      */     void onTick() {
/*  417 */       if (this.failedMessageTicks > 0)
/*      */       {
/*  419 */         this.failedMessageTicks--;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void incrementFailedTaskTicks() {
/*  425 */       this.craftingFailedTicks++;
/*  426 */       if (this.craftingFailedTicks > 10) {
/*      */         
/*  428 */         this.failure = FailureType.TIMEOUT;
/*  429 */         this.failedMessageTicks = 40;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     int incrementFailedCraftTicks() {
/*  435 */       this.craftingFailedTicks++;
/*      */       
/*  437 */       if (this.craftingFailedTicks > 10) {
/*      */         
/*  439 */         this.failure = FailureType.INGREDIENTS;
/*  440 */         this.failedMessageTicks = 40;
/*  441 */         this.amount = 0;
/*      */       } 
/*      */       
/*  444 */       return this.craftingFailedTicks;
/*      */     }
/*      */ 
/*      */     
/*      */     void setStatus(Status status) {
/*  449 */       if (this.status != status)
/*      */       {
/*  451 */         addChatMessage(I18n.get(status.getMessage()));
/*      */       }
/*  453 */       this.status = status;
/*  454 */       if (status == Status.REQUESTED_WORKBENCH)
/*      */       {
/*  456 */         this.requestedTable = true;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     void failed(FailureType failure) {
/*  462 */       if (failure != null) {
/*      */         
/*  464 */         addChatMessage(I18n.get(failure.getMessage()));
/*  465 */         this.failure = failure;
/*  466 */         this.failedMessageTicks = 40;
/*  467 */         if (failure.isTerminal())
/*      */         {
/*  469 */           this.amount = 0;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  474 */         this.failure = FailureType.GENERAL;
/*  475 */         this.failedMessageTicks = 0;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void beginCrafting() {
/*  481 */       AutoCraftingManager.debug("beginCrafting", new Object[0]);
/*  482 */       this.status = Status.CRAFTING;
/*  483 */       this.waitingForGui = 0;
/*  484 */       this.craftingFailedTicks = 0;
/*  485 */       this.isCrafting = true;
/*  486 */       this.phase = 0;
/*  487 */       this.failure = FailureType.GENERAL;
/*  488 */       this.failedMessageTicks = 0;
/*      */     }
/*      */ 
/*      */     
/*      */     void cancel() {
/*  493 */       this.action = null;
/*  494 */       this.isCrafting = false;
/*  495 */       this.phase = 0;
/*  496 */       this.amount = 0;
/*  497 */       this.status = Status.CANCELLED;
/*  498 */       this.waitingForGui = 0;
/*  499 */       this.craftingFailedTicks = 0;
/*  500 */       failed(FailureType.CANCELLED);
/*      */     }
/*      */ 
/*      */     
/*      */     void stop() {
/*  505 */       this.phase = 0;
/*  506 */       this.action = null;
/*  507 */       this.requestedTable = false;
/*      */     }
/*      */ 
/*      */     
/*      */     void end() {
/*  512 */       this.isCrafting = false;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean processAction() {
/*  517 */       if (this.action != null) {
/*      */         
/*  519 */         AutoCraftingManager.debug("processAction()", new Object[0]);
/*  520 */         if (!this.action.process())
/*      */         {
/*  522 */           return true;
/*      */         }
/*      */         
/*  525 */         this.phase++;
/*  526 */         this.action = null;
/*      */       } 
/*      */       
/*  529 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     void onCrafted(int itemCount) {
/*  534 */       this.phase = 0;
/*  535 */       if (itemCount > 0) {
/*      */         
/*  537 */         this.amount -= itemCount;
/*  538 */         this.craftingFailedTicks = 0;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isCrafting() {
/*  544 */       return this.isCrafting;
/*      */     }
/*      */ 
/*      */     
/*      */     void nextPhase() {
/*  549 */       this.phase++;
/*      */     }
/*      */ 
/*      */     
/*      */     int getPhase() {
/*  554 */       return this.phase;
/*      */     }
/*      */ 
/*      */     
/*      */     AutoCraftingManager.CraftingAction getAction() {
/*  559 */       return this.action;
/*      */     }
/*      */ 
/*      */     
/*      */     void beginActionLayoutRecipe(AutoCraftingManager mgr, bmg craftingGui, afr container, int slotStart, int craftingWidth) {
/*  564 */       addChatMessage(I18n.get("crafting.status.progress", new Object[] { Integer.valueOf(getProgress() + 1), Integer.valueOf(this.total) }));
/*  565 */       mgr.getClass(); this.action = new AutoCraftingManager.CraftingActionLayoutRecipe(this.slots, this, craftingGui, container, slotStart, craftingWidth, this.autoCraftRate);
/*      */     }
/*      */ 
/*      */     
/*      */     void beginActionTakeOutput(AutoCraftingManager mgr, bmg craftingGui, afr container, int outputSlot) {
/*  570 */       mgr.getClass(); this.action = new AutoCraftingManager.CraftingActionTakeOutput(this.slots, this, craftingGui, container, outputSlot);
/*      */     }
/*      */ 
/*      */     
/*      */     int getAmount() {
/*  575 */       return this.amount;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isComplete() {
/*  580 */       return (this.amount < 1);
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isWaitingForGui() {
/*  585 */       return (this.waitingForGui > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isGuiTimeoutState() {
/*  590 */       return (--this.waitingForGui <= 0);
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isGridTimeoutState() {
/*  595 */       if (this.waitingForGui == 0) {
/*      */         
/*  597 */         this.waitingForGui = 40;
/*      */       }
/*  599 */       else if (--this.waitingForGui == 0) {
/*      */         
/*  601 */         return true;
/*      */       } 
/*      */       
/*  604 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean needsTable() {
/*  609 */       return this.needsTable;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean throwResult() {
/*  614 */       return this.throwResult;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean isVerbose() {
/*  619 */       return this.verbose;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean requestedTable() {
/*  624 */       return this.requestedTable;
/*      */     }
/*      */ 
/*      */     
/*      */     void addChatMessage(String message) {
/*  629 */       if (this.verbose)
/*      */       {
/*  631 */         Game.addChatMessage("§b[CRAFT] §a" + message);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public AutoCraftingToken getToken() {
/*  637 */       return this.token;
/*      */     }
/*      */ 
/*      */     
/*      */     public IVanillaRecipe getRecipe() {
/*  642 */       return this.recipe;
/*      */     }
/*      */ 
/*      */     
/*      */     public aip getRecipeOutput() {
/*  647 */       return this.recipe.b();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isFailed() {
/*  652 */       return (this.failedMessageTicks > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getProgress() {
/*  657 */       return this.total - this.amount;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getTotal() {
/*  662 */       return this.total;
/*      */     }
/*      */ 
/*      */     
/*      */     public Status getStatus() {
/*  667 */       return this.status;
/*      */     }
/*      */ 
/*      */     
/*      */     public FailureType getFailure() {
/*  672 */       return this.failure;
/*      */     }
/*      */     
/*      */     public String getProgressString()
/*      */     {
/*  677 */       return String.format("%s/%s", new Object[] { Integer.valueOf(getProgress()), Integer.valueOf(this.total) });
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  690 */   private final Queue<Job> jobs = new LinkedList<>();
/*      */   public enum FailureType {
/*  692 */     GENERAL("crafting.fail.general"), UNEXPECTED("crafting.fail.unexpected"), NORECIPE("crafting.fail.norecipe"), TIMEOUT("crafting.fail.timeout"), INGREDIENTS("crafting.fail.ingredients"), NOGRID("crafting.fail.nogrid", true), WORKBENCH("crafting.fail.workbench"), NOSPACE("crafting.fail.nospace"), CANCELLED("crafting.fail.cancelled"); private final String message; private final boolean isTerminal; FailureType(String failedMessage, boolean isTerminal) { this.message = failedMessage; this.isTerminal = isTerminal; } public String getMessage() { return this.message; } boolean isTerminal() { return this.isTerminal; } } private final Queue<SlotHelper.SlotClick> clicks = new LinkedList<>();
/*      */   
/*      */   private Job activeJob;
/*      */   private Job lastCompletedJob;
/*      */   
/*      */   public AutoCraftingManager(Macros macros, bib minecraft) {
/*  698 */     this.macros = macros;
/*  699 */     this.mc = minecraft;
/*  700 */     this.slotHelper = new SlotHelper(macros, minecraft);
/*      */   }
/*      */ 
/*      */   
/*      */   public Job getLastCompletedJob() {
/*  705 */     return this.lastCompletedJob;
/*      */   }
/*      */ 
/*      */   
/*      */   public Job getActiveJob() {
/*  710 */     return this.activeJob;
/*      */   }
/*      */ 
/*      */   
/*      */   public Queue<Job> getJobs() {
/*  715 */     return this.jobs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCraftingActive() {
/*  725 */     return (this.activeJob != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  730 */     if (this.activeJob != null) {
/*      */       
/*  732 */       this.activeJob.cancel();
/*  733 */       endJob(this.activeJob);
/*      */     } 
/*      */     
/*  736 */     this.jobs.clear();
/*  737 */     this.clicks.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AutoCraftingToken craft(IScriptActionProvider provider, IAutoCraftingInitiator initiator, bud thePlayer, String itemId, int damageValue, int amount, boolean shouldThrowResult, boolean verbose) {
/*  755 */     AutoCraftingToken token = new AutoCraftingToken(initiator);
/*      */ 
/*      */     
/*  758 */     if (itemId == null || thePlayer == null)
/*      */     {
/*  760 */       return token.notifyCompleted("INVALID");
/*      */     }
/*      */ 
/*      */     
/*  764 */     aec inventory = thePlayer.bv;
/*  765 */     if (inventory == null)
/*      */     {
/*  767 */       return token.notifyCompleted("INVALID");
/*      */     }
/*      */     
/*  770 */     ItemID itemID = new ItemID(itemId, damageValue);
/*      */ 
/*      */     
/*  773 */     if (this.mc.c.h() && creativeInventoryContains(itemID.item, damageValue))
/*      */     {
/*  775 */       return craftCreative(token, (aed)thePlayer, inventory, shouldThrowResult, damageValue, itemID, amount);
/*      */     }
/*      */     
/*  778 */     return craftSurvival(token, (aed)thePlayer, inventory, shouldThrowResult, damageValue, itemID, amount, verbose);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private AutoCraftingToken craftCreative(AutoCraftingToken token, aed thePlayer, aec inventory, boolean shouldThrowResult, int damageValue, ItemID itemID, int amount) {
/*  784 */     if (shouldThrowResult) {
/*      */       
/*  786 */       if (damageValue < 0)
/*      */       {
/*  788 */         damageValue = 0;
/*      */       }
/*      */       
/*  791 */       aip stack = new aip(itemID.item, amount, damageValue);
/*  792 */       thePlayer.a(stack, true, false);
/*  793 */       this.mc.c.a(stack);
/*      */     }
/*      */     else {
/*      */       
/*  797 */       thePlayer.bv.a(itemID.toItemStack(1));
/*  798 */       int l = thePlayer.bx.c.size() - 9 + thePlayer.bv.d;
/*  799 */       this.mc.c.a(thePlayer.bv.a(thePlayer.bv.d), l);
/*      */     } 
/*      */     
/*  802 */     sendCraftingCompletedEvent(null, "CREATIVE", null);
/*  803 */     return token.notifyCompleted("CREATIVE");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AutoCraftingToken craftSurvival(AutoCraftingToken token, aed thePlayer, aec inventory, boolean shouldThrowResult, int damageValue, ItemID itemID, int amount, boolean verbose) {
/*  810 */     boolean foundItemsForRecipe = false;
/*  811 */     boolean foundRecipe = false;
/*      */ 
/*      */     
/*  814 */     for (akt recipe : aku.a) {
/*      */       
/*  816 */       aip recipeStack = recipe.b();
/*      */       
/*  818 */       if (recipeStack != null && recipeStack.c() == itemID.item && (damageValue == -1 || recipeStack.j() == damageValue)) {
/*      */         
/*  820 */         foundRecipe = true;
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  825 */           if (beginCrafting(token, inventory, recipe, amount, shouldThrowResult, verbose)) {
/*      */             
/*  827 */             foundItemsForRecipe = true;
/*      */             
/*      */             break;
/*      */           } 
/*  831 */         } catch (Exception ex) {
/*      */           
/*  833 */           Log.printStackTrace(ex);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  838 */     if (!foundItemsForRecipe) {
/*      */       
/*  840 */       String reason = foundRecipe ? "NOTSTARTED" : "NORECIPE";
/*  841 */       Job.FailureType failure = foundRecipe ? Job.FailureType.INGREDIENTS : Job.FailureType.NORECIPE;
/*  842 */       sendCraftingCompletedEvent(null, reason, failure);
/*  843 */       return token.notifyCompleted(reason);
/*      */     } 
/*      */     
/*  846 */     return token;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean beginCrafting(AutoCraftingToken token, aec inventory, akt recipe, int amount, boolean throwResult, boolean verbose) throws IllegalArgumentException, SecurityException {
/*  864 */     if (!(recipe instanceof IVanillaRecipe))
/*      */     {
/*  866 */       return false;
/*      */     }
/*      */     
/*  869 */     IVanillaRecipe vanillaRecipe = (IVanillaRecipe)recipe;
/*  870 */     if (getRecipeIngredients(vanillaRecipe, inventory, verbose) == null)
/*      */     {
/*  872 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  876 */     return addJob(token, vanillaRecipe, amount, throwResult, verbose);
/*      */   }
/*      */ 
/*      */   
/*      */   void slotClick(bmg craftingGui, agr slot, int slotNumber, int button, boolean shift) {
/*  881 */     SlotHelper.SlotClick click = new SlotHelper.SlotClick(craftingGui, slot, slotNumber, button, shift);
/*  882 */     debug("Queuing click " + click, new Object[0]);
/*  883 */     this.clicks.add(click);
/*      */   }
/*      */ 
/*      */   
/*      */   void slotClick(bmg craftingGui, agr slot, int slotNumber, int button, afw clickType) {
/*  888 */     SlotHelper.SlotClick click = new SlotHelper.SlotClick(craftingGui, slot, slotNumber, button, clickType);
/*  889 */     debug("Queuing click " + click, new Object[0]);
/*  890 */     this.clicks.add(click);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean addJob(AutoCraftingToken token, IVanillaRecipe recipe, int amount, boolean throwResult, boolean verbose) {
/*      */     try {
/*  897 */       this.jobs.add(new Job(this.slotHelper, token, recipe, amount, throwResult, verbose, (this.macros.getSettings()).autoCraftRate));
/*  898 */       return true;
/*      */     }
/*  900 */     catch (Exception ex) {
/*      */       
/*  902 */       ex.printStackTrace();
/*      */ 
/*      */       
/*  905 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void endJob(Job job) {
/*  910 */     job.end();
/*  911 */     this.lastCompletedJob = job;
/*  912 */     if (job == this.activeJob)
/*      */     {
/*  914 */       this.activeJob = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int onTick(IScriptActionProvider provider) {
/*  929 */     if (this.lastCompletedJob != null)
/*      */     {
/*  931 */       this.lastCompletedJob.onTick();
/*      */     }
/*      */     
/*  934 */     SlotHelper.SlotClick click = this.clicks.poll();
/*  935 */     if (click != null) {
/*      */       
/*  937 */       debug("  PROCESSING click " + click, new Object[0]);
/*  938 */       click.execute(this.slotHelper);
/*  939 */       return 1;
/*      */     } 
/*      */     
/*  942 */     if (this.activeJob != null) {
/*      */       
/*  944 */       debug("Processing job: %s", new Object[] { this.activeJob });
/*  945 */       return processJob(provider, this.activeJob);
/*      */     } 
/*      */     
/*  948 */     Job nextJob = this.jobs.poll();
/*  949 */     if (nextJob != null) {
/*      */       
/*  951 */       this.activeJob = nextJob;
/*  952 */       debug("STARTING NEW JOB: %s", new Object[] { this.activeJob });
/*  953 */       return 1;
/*      */     } 
/*      */     
/*  956 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private int processJob(IScriptActionProvider provider, Job job) {
/*  961 */     if (job.isWaitingForGui()) {
/*      */       
/*  963 */       debug("Waiting for gui", new Object[0]);
/*  964 */       if (!openGui(provider, job)) {
/*      */         
/*  966 */         debug("Gui not open", new Object[0]);
/*  967 */         return 1;
/*      */       } 
/*      */     } 
/*      */     
/*  971 */     if (!job.isCrafting()) {
/*      */       
/*  973 */       debug("Not crafting", new Object[0]);
/*  974 */       return 1;
/*      */     } 
/*      */     
/*  977 */     boolean craftingOpen = this.mc.m instanceof bmn;
/*  978 */     boolean inventoryOpen = this.slotHelper.currentScreenIsInventory();
/*      */     
/*  980 */     if ((!inventoryOpen && !craftingOpen) || (job.needsTable() && !craftingOpen)) {
/*      */       
/*  982 */       if (job.isGridTimeoutState()) {
/*      */         
/*  984 */         debug("Too many failed ticks", new Object[0]);
/*  985 */         job.failed(Job.FailureType.NOGRID);
/*  986 */         sendCraftingCompletedEvent(job, "TIMEOUT", Job.FailureType.NOGRID);
/*  987 */         return 0;
/*      */       } 
/*      */ 
/*      */       
/*  991 */       debug("No gui available: inv=%s craft=%s table=%s", new Object[] { Boolean.valueOf(inventoryOpen), Boolean.valueOf(craftingOpen), Boolean.valueOf(job.needsTable()) });
/*  992 */       job.stop();
/*  993 */       return 1;
/*      */     } 
/*      */     
/*  996 */     debug("Process action", new Object[0]);
/*      */     
/*  998 */     if (job.processAction())
/*      */     {
/* 1000 */       return 1;
/*      */     }
/*      */     
/* 1003 */     bud player = this.mc.h;
/* 1004 */     aec inventory = player.bv;
/*      */ 
/*      */     
/* 1007 */     if (job.getPhase() < 2) {
/*      */       
/*      */       try {
/*      */         
/* 1011 */         debug("Phase = %s", new Object[] { Integer.valueOf(job.getPhase()) });
/*      */ 
/*      */         
/* 1014 */         List<akq> recipeIngredients = getRecipeIngredients(job.getRecipe(), inventory, false);
/* 1015 */         if (recipeIngredients == null) {
/*      */           
/* 1017 */           debug("No recipe items", new Object[0]);
/* 1018 */           if (job.incrementFailedCraftTicks() > 10)
/*      */           {
/* 1020 */             clearCraftingGrid(this.slotHelper.getGuiContainer());
/* 1021 */             if (job.isVerbose())
/*      */             {
/* 1023 */               getRecipeIngredients(job.getRecipe(), inventory, true);
/*      */             }
/* 1025 */             sendCraftingCompletedEvent(job, "NOITEMS", Job.FailureType.INGREDIENTS);
/* 1026 */             return 1;
/*      */           }
/*      */         
/*      */         } 
/* 1030 */       } catch (Exception ex) {
/*      */         
/* 1032 */         Log.printStackTrace(ex);
/* 1033 */         sendCraftingCompletedEvent(job, "ERROR", Job.FailureType.UNEXPECTED);
/* 1034 */         return 1;
/*      */       } 
/*      */     }
/*      */     
/* 1038 */     debug("Remaining: %d", new Object[] { Integer.valueOf(job.getAmount()) });
/*      */ 
/*      */     
/* 1041 */     if (job.getAmount() > 0 && (craftingOpen || (inventoryOpen && !job.needsTable())))
/*      */     {
/*      */       
/* 1044 */       if (!craftRecipe(provider, job))
/*      */       {
/*      */         
/* 1047 */         if (job.incrementFailedCraftTicks() > 10)
/*      */         {
/* 1049 */           sendCraftingCompletedEvent(job, "NOSPACE", Job.FailureType.NOSPACE);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 1055 */     if (job.isComplete()) {
/*      */       
/* 1057 */       clearCraftingGrid(this.slotHelper.getGuiContainer());
/* 1058 */       job.setStatus(Job.Status.DONE);
/* 1059 */       sendCraftingCompletedEvent(job, "DONE", null);
/*      */     } 
/*      */     
/* 1062 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean openGui(IScriptActionProvider provider, Job job) {
/* 1068 */     if (this.mc.c.h()) {
/*      */       
/* 1070 */       sendCraftingCompletedEvent(job, "CREATIVE", null);
/* 1071 */       return false;
/*      */     } 
/*      */     
/* 1074 */     boolean craftingOpen = this.mc.m instanceof bmn;
/* 1075 */     boolean inventoryOpen = this.slotHelper.currentScreenIsInventory();
/*      */ 
/*      */     
/* 1078 */     if (!job.needsTable()) {
/*      */       
/* 1080 */       if (!craftingOpen && !inventoryOpen) {
/*      */         
/* 1082 */         debug("Attempt to open inventory (%s, %s)", new Object[] { Boolean.valueOf(craftingOpen), Boolean.valueOf(inventoryOpen) });
/*      */         
/*      */         try {
/* 1085 */           this.mc.a((blk)new bmx((aed)this.mc.h));
/* 1086 */           job.setStatus(Job.Status.WAITING_FOR_INVENTORY);
/*      */         }
/* 1088 */         catch (Exception ex) {
/*      */           
/* 1090 */           ex.printStackTrace();
/* 1091 */           return false;
/*      */         } 
/*      */       } 
/*      */       
/* 1095 */       if (craftingOpen || inventoryOpen) {
/*      */         
/* 1097 */         job.beginCrafting();
/* 1098 */         return true;
/*      */       } 
/* 1100 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1104 */     if (!job.requestedTable()) {
/*      */       
/* 1106 */       debug("Waiting for workbench", new Object[0]);
/* 1107 */       job.setStatus(Job.Status.WAITING_FOR_WORKBENCH);
/*      */       
/* 1109 */       if (this.mc.s != null && this.mc.s.a == bhc.a.b) {
/*      */         
/* 1111 */         et blockPos = this.mc.s.a();
/* 1112 */         aow block = this.mc.f.o(blockPos).u();
/*      */ 
/*      */         
/* 1115 */         if (block == aox.ai) {
/*      */           
/* 1117 */           if (inventoryOpen) {
/*      */             
/* 1119 */             debug("Closing inventory", new Object[0]);
/* 1120 */             this.mc.a(null);
/* 1121 */             return false;
/*      */           } 
/*      */           
/* 1124 */           debug("Mouse is over workbench, opening", new Object[0]);
/* 1125 */           provider.actionUseItem(this.mc, this.mc.h, null, this.mc.h.bv.d);
/* 1126 */           job.setStatus(Job.Status.REQUESTED_WORKBENCH);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1132 */     if (job.isGuiTimeoutState()) {
/*      */       
/* 1134 */       sendCraftingCompletedEvent(job, "TIMEOUT", Job.FailureType.WORKBENCH);
/* 1135 */       return false;
/*      */     } 
/*      */     
/* 1138 */     if (craftingOpen) {
/*      */       
/* 1140 */       job.beginCrafting();
/* 1141 */       return true;
/*      */     } 
/*      */     
/* 1144 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void sendCraftingCompletedEvent(Job job, String reason, Job.FailureType failureType) {
/* 1154 */     if (job != null) {
/*      */       
/* 1156 */       if (job.getToken() != null)
/*      */       {
/* 1158 */         job.getToken().notifyCompleted(reason);
/*      */       }
/*      */       
/* 1161 */       job.failed(failureType);
/* 1162 */       endJob(job);
/*      */     } 
/*      */     
/* 1165 */     this.macros.sendEvent(BuiltinEvent.onAutoCraftingComplete.getName(), 50, new String[] { reason });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<akq> getRecipeIngredients(IVanillaRecipe recipe, aec inventory, boolean verbose) throws IllegalArgumentException, SecurityException {
/* 1183 */     List<akq> ingredients = new ArrayList<>();
/* 1184 */     List<akq> requiredIngredients = new ArrayList<>();
/*      */ 
/*      */     
/* 1187 */     for (akq recipeIngredient : recipe.getItems()) {
/*      */       
/* 1189 */       ingredients.add(recipeIngredient);
/* 1190 */       if (recipeIngredient != null && recipeIngredient != akq.a)
/*      */       {
/* 1192 */         requiredIngredients.add(recipeIngredient);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1197 */     while (requiredIngredients.size() > 0) {
/*      */       
/* 1199 */       Iterator<akq> iterator = requiredIngredients.iterator();
/* 1200 */       akq requiredIngredient = iterator.next();
/* 1201 */       iterator.remove();
/* 1202 */       int requiredCount = 1;
/*      */       
/* 1204 */       while (iterator.hasNext()) {
/*      */         
/* 1206 */         if (ingredientsMatch(iterator.next(), requiredIngredient)) {
/*      */           
/* 1208 */           iterator.remove();
/* 1209 */           requiredCount++;
/*      */         } 
/*      */       } 
/*      */       
/* 1213 */       for (int i = 0; i < inventory.a.size(); i++) {
/*      */         
/* 1215 */         aip stack = (aip)inventory.a.get(i);
/* 1216 */         if (!stack.b() && ingredientMatches(requiredIngredient, stack)) {
/*      */           
/* 1218 */           requiredCount -= stack.E();
/* 1219 */           debug("Found %s %s in inventory slot %s, requiredCount is now %d", new Object[] {
/* 1220 */                 Integer.valueOf(((aip)inventory.a.get(i)).E()), ((aip)inventory.a
/* 1221 */                 .get(i)).a(), Integer.valueOf(i), Integer.valueOf(requiredCount) });
/* 1222 */           if (requiredCount <= 0) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1230 */       if (requiredCount > 0) {
/*      */         
/* 1232 */         StringBuilder sb = new StringBuilder();
/* 1233 */         aip[] stacks = requiredIngredient.a();
/* 1234 */         for (int j = 0; j < stacks.length; j++) {
/*      */           
/* 1236 */           if (j > 0)
/*      */           {
/* 1238 */             sb.append(" or ");
/*      */           }
/* 1240 */           sb.append(stacks[j].r());
/*      */         } 
/*      */         
/* 1243 */         String message = "Not enough " + sb.toString() + " for recipe. Missing " + requiredCount;
/* 1244 */         Log.info(message);
/* 1245 */         if (verbose)
/*      */         {
/* 1247 */           Game.addChatMessage("§b[CRAFT] §a" + message);
/*      */         }
/* 1249 */         return null;
/*      */       } 
/*      */     } 
/*      */     
/* 1253 */     return ingredients;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean craftRecipe(IScriptActionProvider provider, Job job) {
/*      */     try {
/* 1266 */       return nextAction(provider, job);
/*      */     }
/* 1268 */     catch (Exception ex) {
/*      */       
/* 1270 */       Log.printStackTrace(ex);
/*      */ 
/*      */       
/* 1273 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean nextAction(IScriptActionProvider provider, Job job) {
/* 1284 */     debug("nextAction()", new Object[0]);
/* 1285 */     bmg craftingGui = this.slotHelper.getGuiContainer();
/* 1286 */     afr container = craftingGui.h;
/*      */ 
/*      */     
/* 1289 */     boolean craftingTable = craftingGui instanceof bmn;
/* 1290 */     int slotStart = craftingTable ? 10 : 9;
/* 1291 */     int craftingWidth = craftingTable ? 3 : 2;
/*      */     
/* 1293 */     if (job.getPhase() == 0) {
/*      */       
/* 1295 */       debug("Phase " + job.getPhase() + " clearing grid", new Object[0]);
/* 1296 */       aip is = this.mc.h.bv.q();
/* 1297 */       if (!is.b()) {
/*      */         
/* 1299 */         debug("ItemStack on cursor = %s", new Object[] { is });
/* 1300 */         slotClick(craftingGui, (agr)null, -999, 0, afw.a);
/* 1301 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 1305 */       if (!clearCraftingGrid(craftingGui))
/*      */       {
/* 1307 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1311 */       if (!container.a(0).d().b())
/*      */       {
/* 1313 */         return false;
/*      */       }
/*      */       
/* 1316 */       job.nextPhase();
/* 1317 */       return true;
/*      */     } 
/*      */     
/* 1320 */     if (job.getPhase() == 1) {
/*      */       
/* 1322 */       debug("Phase " + job.getPhase() + " building", new Object[0]);
/* 1323 */       job.beginActionLayoutRecipe(this, craftingGui, container, slotStart, craftingWidth);
/* 1324 */       return true;
/*      */     } 
/*      */     
/* 1327 */     if (job.getPhase() % 5 != 0) {
/*      */       
/* 1329 */       debug("Phase " + job.getPhase() + " waiting", new Object[0]);
/* 1330 */       job.nextPhase();
/* 1331 */       return true;
/*      */     } 
/*      */     
/* 1334 */     debug("Phase " + job.getPhase() + " crafting...", new Object[0]);
/*      */     
/* 1336 */     job.beginActionTakeOutput(this, craftingGui, container, 0);
/* 1337 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean clearCraftingGrid(bmg craftingGui) {
/* 1345 */     debug("clearCraftingGrid", new Object[0]);
/*      */     
/* 1347 */     int craftingLength = (craftingGui instanceof bmn) ? 9 : 4;
/* 1348 */     boolean cleared = true;
/*      */     
/* 1350 */     for (int pass = 0; pass < 2; pass++) {
/*      */       
/* 1352 */       cleared = true;
/* 1353 */       for (int slotNumber = 0; slotNumber < craftingLength; slotNumber++) {
/*      */         
/* 1355 */         agr slot = craftingGui.h.a(slotNumber + 1);
/* 1356 */         if (slot.e()) {
/*      */           
/* 1358 */           debug("Slot " + slotNumber + " has " + slot.d(), new Object[0]);
/* 1359 */           this.slotHelper.survivalInventorySlotClick(craftingGui, slot, slotNumber + 1, 0, true);
/* 1360 */           cleared = false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1365 */     return cleared;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean ingredientsMatch(akq ing1, akq ing2) {
/* 1370 */     aip[] stacks1 = ing1.a();
/* 1371 */     aip[] stacks2 = ing2.a();
/* 1372 */     for (int i = 0; i < stacks1.length; i++) {
/*      */       
/* 1374 */       boolean found = false;
/* 1375 */       for (int j = 0; j < stacks2.length; j++)
/*      */       {
/* 1377 */         found |= itemStackMatches(stacks1[i], stacks2[j]);
/*      */       }
/*      */       
/* 1380 */       if (!found)
/*      */       {
/* 1382 */         return false;
/*      */       }
/*      */     } 
/* 1385 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean ingredientMatches(akq ingredient, aip itemStack) {
/* 1398 */     if (itemStack == null)
/*      */     {
/* 1400 */       return (ingredient == null);
/*      */     }
/*      */     
/* 1403 */     return ingredient.a(itemStack);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean itemStackMatches(aip recipeItemStack, aip itemStack) {
/* 1416 */     if (itemStack == null)
/*      */     {
/* 1418 */       return (recipeItemStack == null);
/*      */     }
/*      */     
/* 1421 */     if (recipeItemStack.c() != itemStack.c())
/*      */     {
/* 1423 */       return false;
/*      */     }
/*      */     
/* 1426 */     if ((short)recipeItemStack.j() != Short.MAX_VALUE && recipeItemStack.j() != itemStack.j())
/*      */     {
/* 1428 */       return false;
/*      */     }
/*      */     
/* 1431 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean creativeInventoryContains(ain item, int damageValue) {
/* 1436 */     if (item != null && item.b() != null) {
/*      */       
/* 1438 */       fi<aip> subItems = fi.a();
/* 1439 */       item.a(item.b(), subItems);
/*      */       
/* 1441 */       for (aip subItem : subItems) {
/*      */         
/* 1443 */         if (subItem.j() == damageValue || damageValue == -1)
/*      */         {
/* 1445 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1450 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static void debug(String format, Object... args) {
/* 1455 */     if (!TRACE) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1462 */       System.err.printf(format + "\n", args);
/*      */     }
/* 1464 */     catch (IllegalFormatException ex) {
/*      */       
/* 1466 */       ex.printStackTrace();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\crafting\AutoCraftingManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */