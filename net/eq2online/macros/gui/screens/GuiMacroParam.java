/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import bkq;
/*     */ import blk;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.ChatRenderer;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxFile;
/*     */ import net.eq2online.macros.gui.interfaces.IContentRenderer;
/*     */ import net.eq2online.macros.input.IProhibitOverride;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.interfaces.IDragDrop;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Keyboard;
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
/*     */ public class GuiMacroParam<TItem>
/*     */   extends GuiScreenEx
/*     */   implements IProhibitOverride
/*     */ {
/*     */   private final Macros macros;
/*     */   private final IMacroParamTarget target;
/*     */   private final IContentRenderer contentRenderer;
/*     */   protected MacroParam<TItem> param;
/*     */   protected MacroParam.Type paramType;
/*     */   protected GuiListBox<TItem> itemListBox;
/*     */   protected GuiListBox<TItem> sourceListBox;
/*     */   protected GuiControl btnImportAll;
/*     */   protected GuiControl btnRefreshList;
/*     */   protected bkq yesNoScreen;
/*     */   private float lastUpdatePartialTick;
/*  83 */   protected float screenTween = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiMacroParam(Macros macros, bib minecraft, IMacroParamTarget target) {
/*  92 */     this(macros, minecraft, target, (IContentRenderer)new ChatRenderer(minecraft, macros));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiMacroParam(Macros macros, bib minecraft, IMacroParamTarget target, IContentRenderer contentRenderer) {
/* 102 */     super(minecraft);
/*     */     
/* 104 */     this.macros = macros;
/* 105 */     this.target = target;
/* 106 */     this.contentRenderer = contentRenderer;
/*     */     
/* 108 */     this.btnImportAll = new GuiControl(0, this.l - 182, this.m - 58, 90, 20, I18n.get("gui.importall"));
/* 109 */     this.btnRefreshList = new GuiControl(1, this.l - 62, this.m - 58, 60, 20, I18n.get("gui.refresh"));
/*     */ 
/*     */     
/* 112 */     setParam(target.getNextParam());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setParam(MacroParam<TItem> param) {
/* 117 */     this.param = param;
/* 118 */     if (param != null) {
/*     */       
/* 120 */       this.paramType = param.getType();
/* 121 */       param.setParent(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reInit() {
/* 131 */     if (this.param.isType(this.paramType) && this.paramType != MacroParam.Type.NAMED) {
/*     */       
/* 133 */       this.screenTween = 0.5F;
/* 134 */       this.lastUpdatePartialTick = -1.0F;
/*     */     } 
/*     */     
/* 137 */     this.paramType = this.param.getType();
/*     */ 
/*     */     
/* 140 */     if (this.sourceListBox != null) {
/*     */       
/* 142 */       if (this.itemListBox != null) this.itemListBox.removeDragTarget((IDragDrop)this.sourceListBox); 
/* 143 */       this.sourceListBox.clear();
/* 144 */       this.sourceListBox = null;
/*     */     } 
/*     */ 
/*     */     
/* 148 */     this.itemListBox = null;
/* 149 */     b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/* 158 */     Keyboard.enableRepeatEvents(true);
/*     */     
/* 160 */     if (this.contentRenderer != null)
/*     */     {
/* 162 */       this.contentRenderer.connect(this.l, this.m);
/*     */     }
/*     */     
/* 165 */     clearControlList();
/*     */     
/* 167 */     this.itemListBox = this.param.getListBox(this.l, this.m);
/*     */     
/* 169 */     if (this.itemListBox != null) {
/*     */       
/* 171 */       addControl((GuiControl)this.itemListBox);
/*     */       
/* 173 */       if (this.sourceListBox != null) {
/*     */         
/* 175 */         if (this.itemListBox.isValidDragSource())
/*     */         {
/* 177 */           this.itemListBox.addDragTarget((IDragDrop)this.sourceListBox, true);
/*     */         }
/*     */         
/* 180 */         this.sourceListBox.setSizeAndPosition(this.l - 182, 20, 180, this.m - 80, 20, true);
/* 181 */         addControl((GuiControl)this.sourceListBox);
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     this.param.initControls(this.l, this.m);
/*     */     
/* 187 */     this.btnRefreshList.setYPosition(this.m - 58);
/* 188 */     this.btnImportAll.setYPosition(this.m - 58);
/* 189 */     this.btnImportAll.setXPosition(this.l - 182);
/* 190 */     this.btnRefreshList.setXPosition(this.l - 62);
/*     */     
/* 192 */     addControl(this.btnImportAll);
/* 193 */     addControl(this.btnRefreshList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {
/* 202 */     Keyboard.enableRepeatEvents(false);
/*     */     
/* 204 */     if (this.contentRenderer != null)
/*     */     {
/* 206 */       this.contentRenderer.disconnect();
/*     */     }
/*     */     
/* 209 */     if (this.itemListBox != null && this.sourceListBox != null && this.itemListBox.isValidDragSource())
/*     */     {
/* 211 */       this.itemListBox.removeDragTarget((IDragDrop)this.sourceListBox, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bja guibutton) throws IOException {
/* 221 */     boolean shiftDown = InputHandler.isShiftDown();
/*     */     
/* 223 */     if (guibutton.k == 555)
/*     */     {
/* 225 */       this.param.sortList();
/*     */     }
/*     */ 
/*     */     
/* 229 */     if (guibutton.k == 0 && this.sourceListBox != null)
/*     */     {
/* 231 */       this.param.importAllFrom(this.sourceListBox);
/*     */     }
/*     */ 
/*     */     
/* 235 */     if (guibutton.k == 1 && this.sourceListBox != null)
/*     */     {
/* 237 */       autoPopulate();
/*     */     }
/*     */ 
/*     */     
/* 241 */     if (guibutton.k == 12 && this.sourceListBox != null)
/*     */     {
/* 243 */       if (shiftDown) {
/*     */         
/* 245 */         IListEntry<TItem> selectedItem = this.sourceListBox.getSelectedItem();
/*     */         
/* 247 */         if (selectedItem != null) {
/*     */           
/* 249 */           this.sourceListBox.removeSelectedItem();
/* 250 */           this.itemListBox.addItemAt(selectedItem, this.itemListBox.getItemCount());
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 256 */     if (guibutton.k == 2 && this.itemListBox != null)
/*     */     {
/* 258 */       this.param.handleListBoxClick(this);
/*     */     }
/*     */     
/* 261 */     super.a(guibutton);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void autoPopulate() {
/* 267 */     this.param.setParent(this);
/* 268 */     this.param.autoPopulate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean createFileAndEdit(String text) {
/* 279 */     if (!text.toLowerCase().endsWith(".txt")) {
/*     */       
/* 281 */       if (text.lastIndexOf('.') > 0)
/*     */       {
/* 283 */         text = text.substring(0, text.lastIndexOf('.'));
/*     */       }
/*     */       
/* 286 */       text = text + ".txt";
/*     */     } 
/*     */ 
/*     */     
/* 290 */     if (text.startsWith("."))
/*     */     {
/* 292 */       text = text.substring(1);
/*     */     }
/*     */ 
/*     */     
/* 296 */     if (text.length() > 4) {
/*     */       
/* 298 */       GuiEditText editor = new GuiEditTextFile(this.macros, this.j, this, this.macros.getFile(text), ScriptContext.MAIN);
/* 299 */       this.j.a((blk)editor);
/* 300 */       return true;
/*     */     } 
/*     */     
/* 303 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFinishEditingTextFile(GuiEditText editor, File file) {
/* 313 */     if (file != null && file.exists() && file.getName().toLowerCase().equals("macros.txt"))
/*     */     {
/* 315 */       this.macros.load();
/*     */     }
/*     */     
/* 318 */     if (this.itemListBox instanceof GuiListBoxFile) {
/*     */       
/* 320 */       ((GuiListBoxFile)this.itemListBox).refresh();
/* 321 */       if (file != null) this.itemListBox.selectData(file.getName());
/*     */     
/*     */     } 
/* 324 */     this.j.a((blk)this);
/*     */     
/* 326 */     IListEntry<TItem> selectedItem = this.itemListBox.getSelectedItem();
/* 327 */     if (selectedItem != null && selectedItem.getId() > -1)
/*     */     {
/* 329 */       this.param.setParameterValue(selectedItem.getData().toString());
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
/*     */ 
/*     */   
/*     */   public void onAutoPopulateComplete(MacroParam<TItem> macroParam, List<String> items) {
/* 345 */     if (macroParam.isType(this.param.getType()) && (macroParam.isType(MacroParam.Type.FRIEND) || items.size() > 0)) {
/*     */       
/* 347 */       if (this.sourceListBox == null) {
/*     */         
/* 349 */         this.sourceListBox = new GuiListBox(this.j, 12, this.l - 182, 20, 180, this.m - 80, 20, true, true, true);
/*     */         
/* 351 */         this.itemListBox.addDragTarget((IDragDrop)this.sourceListBox, true);
/* 352 */         addControl((GuiControl)this.sourceListBox);
/*     */       }
/*     */       else {
/*     */         
/* 356 */         this.sourceListBox.clear();
/*     */       } 
/*     */       
/* 359 */       if (macroParam.isType(MacroParam.Type.FRIEND))
/*     */       {
/* 361 */         this.macros.getAutoDiscoveryHandler().populateUserListBox(this.sourceListBox, true);
/*     */       }
/*     */       else
/*     */       {
/* 365 */         for (int i = 0; i < items.size(); i++)
/*     */         {
/* 367 */           if (!this.itemListBox.containsItem(items.get(i)))
/*     */           {
/* 369 */             int iconId = (int)(Math.random() * 255.0D);
/* 370 */             IListEntry<TItem> newItem = this.itemListBox.createObject(items.get(i), iconId);
/* 371 */             this.sourceListBox.addItem(newItem);
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 378 */     } else if (this.sourceListBox != null) {
/*     */       
/* 380 */       this.itemListBox.removeDragTarget((IDragDrop)this.sourceListBox);
/* 381 */       this.n.remove(this.sourceListBox);
/* 382 */       this.sourceListBox = null;
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
/*     */   
/*     */   public void a(boolean response, int worldIndex) {
/* 397 */     this.param.deleteSelectedItem(response);
/*     */     
/* 399 */     this.j.a((blk)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 409 */     if (this.param.mouseClicked(mouseX, mouseY, button))
/*     */     {
/* 411 */       super.a(mouseX, mouseY, button);
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
/*     */   protected void a(char keyChar, int keyCode) throws IOException {
/* 424 */     if (this.updateCounter < 1 || this.param == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 429 */     GuiControlEx.HandledState handled = this.param.keyTyped(keyChar, keyCode);
/*     */     
/* 431 */     if (handled == GuiControlEx.HandledState.ACTION_PERFORMED) {
/*     */       
/* 433 */       a((bja)this.itemListBox);
/*     */       
/*     */       return;
/*     */     } 
/* 437 */     if (handled == GuiControlEx.HandledState.HANDLED) {
/*     */       return;
/*     */     }
/* 440 */     if (keyCode == 1) {
/*     */       
/* 442 */       this.target.handleCancelled();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 447 */     if (keyCode == 28 || keyCode == 156) {
/*     */       
/* 449 */       apply();
/*     */       
/*     */       return;
/*     */     } 
/* 453 */     if (keyChar == '\023' && keyCode == 31) {
/*     */       
/* 455 */       this.param.sortList();
/*     */       
/*     */       return;
/*     */     } 
/* 459 */     this.param.textFieldKeyTyped(keyChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply() {
/* 468 */     if (!this.param.apply()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 474 */     setParam(this.target.getNextParam());
/*     */ 
/*     */     
/* 477 */     if (this.param != null) {
/*     */       
/* 479 */       reInit();
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 484 */       this.target.handleCompleted();
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
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) throws IOException {
/* 497 */     if (this.itemListBox != null) {
/*     */       
/* 499 */       mouseWheelDelta /= 120;
/*     */       
/* 501 */       while (mouseWheelDelta > 0) {
/*     */         
/* 503 */         this.itemListBox.up();
/* 504 */         a((bja)this.itemListBox);
/* 505 */         mouseWheelDelta--;
/*     */       } 
/*     */       
/* 508 */       while (mouseWheelDelta < 0) {
/*     */         
/* 510 */         this.itemListBox.down();
/* 511 */         a((bja)this.itemListBox);
/* 512 */         mouseWheelDelta++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 523 */     super.e();
/* 524 */     this.param.updateScreen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float f) {
/* 533 */     drawScreenWithEnabledState(mouseX, mouseY, f, true);
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
/*     */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float partialTick, boolean enabled) {
/* 547 */     if (this.contentRenderer != null)
/*     */     {
/* 549 */       this.contentRenderer.render(mouseX, mouseY, partialTick);
/*     */     }
/*     */     
/* 552 */     updateTween(partialTick);
/*     */     
/* 554 */     if (this.itemListBox != null)
/*     */     {
/* 556 */       this.itemListBox.setEnabled(enabled);
/*     */     }
/*     */     
/* 559 */     if (this.sourceListBox != null)
/*     */     {
/* 561 */       this.sourceListBox.setVisible(enabled);
/*     */     }
/*     */     
/* 564 */     this.btnImportAll.setVisible((this.sourceListBox != null && enabled));
/* 565 */     this.btnRefreshList.setVisible((this.sourceListBox != null && enabled));
/*     */     
/* 567 */     GL.glPushMatrix();
/* 568 */     GL.glTranslatef(-180.0F * (1.0F - (float)Math.sin(Math.PI * (0.5F - this.screenTween))), 0.0F, 0.0F);
/*     */     
/* 570 */     super.a(mouseX, mouseY, partialTick);
/*     */     
/* 572 */     GL.glPopMatrix();
/*     */     
/* 574 */     this.param.drawControls(this.j, mouseX, mouseY, partialTick, enabled, this.q, this.l, this.m, this.updateCounter);
/*     */     
/* 576 */     if (enabled && this.sourceListBox != null) {
/*     */       
/* 578 */       a(this.l - 182, 2, this.l - 2, 18, -2146562560);
/* 579 */       c(this.q, I18n.get("query.results"), this.l - 178, 6, 16777215);
/*     */     } 
/*     */     
/* 582 */     if (enabled && this.itemListBox != null && this.itemListBox.isSortable() && InputHandler.isControlDown())
/*     */     {
/* 584 */       drawTooltip(I18n.get("macro.prompt.list.sort"), 10, 22, -16711936, -1342177280);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateTween(float partialTick) {
/* 590 */     if (this.lastUpdatePartialTick == -1.0F)
/*     */     {
/* 592 */       this.lastUpdatePartialTick = this.updateCounter + partialTick;
/*     */     }
/*     */     
/* 595 */     float deltaTime = this.updateCounter + partialTick - this.lastUpdatePartialTick;
/* 596 */     this.lastUpdatePartialTick = this.updateCounter + partialTick;
/*     */     
/* 598 */     if (this.screenTween > 0.0F)
/*     */     {
/* 600 */       this.screenTween -= deltaTime * 0.05F;
/*     */     }
/*     */     
/* 603 */     if (this.screenTween < 0.0F)
/*     */     {
/* 605 */       this.screenTween = 0.0F;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiMacroParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */