/*     */ package net.eq2online.macros.gui;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import blk;
/*     */ import cgp;
/*     */ import cgt;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.gui.controls.GuiMiniToolbarButton;
/*     */ import net.eq2online.macros.gui.controls.GuiTextEditor;
/*     */ import net.eq2online.macros.gui.screens.GuiEditText;
/*     */ import net.eq2online.macros.interfaces.IDialogClosedListener;
/*     */ import net.eq2online.macros.interfaces.IDialogParent;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import qf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiScreenEx
/*     */   extends blk
/*     */   implements IDialogParent, IDialogClosedListener
/*     */ {
/*     */   protected final GuiRenderer renderer;
/*  32 */   protected final List<GuiMiniToolbarButton> miniButtons = new ArrayList<>();
/*     */   
/*  34 */   protected GuiMiniToolbarButton hoverButton = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int promptBarStart;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int promptBarEnd;
/*     */ 
/*     */   
/*     */   protected String prompt;
/*     */ 
/*     */   
/*     */   protected bja selectedButtonEx;
/*     */ 
/*     */   
/*     */   protected GuiControl rightClickedButton;
/*     */ 
/*     */   
/*  54 */   protected int updateCounter = 0;
/*     */ 
/*     */   
/*     */   protected int rowPos;
/*     */   
/*  59 */   protected int rowSpacing = 12;
/*     */   
/*     */   private boolean dragEvents = false;
/*     */ 
/*     */   
/*     */   public GuiScreenEx(bib minecraft) {
/*  65 */     this(minecraft, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiScreenEx(bib minecraft, boolean generateDragEvents) {
/*  70 */     this.j = minecraft;
/*  71 */     this.q = minecraft.k;
/*  72 */     this.dragEvents = generateDragEvents;
/*  73 */     this.renderer = new GuiRenderer(minecraft);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  82 */     this.updateCounter++;
/*     */     
/*  84 */     for (bja control : this.n) {
/*     */       
/*  86 */       if (control instanceof GuiControlEx) ((GuiControlEx)control).updateCounter++; 
/*  87 */       if (control instanceof GuiTextEditor) ((GuiTextEditor)control).onUpdate();
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getPrompt() {
/*  93 */     return this.prompt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPrompt(String prompt) {
/*  98 */     this.prompt = prompt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void clearControlList() {
/* 103 */     this.n.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void addControl(GuiControl control) {
/* 108 */     this.n.add(control);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void removeControl(GuiControl control) {
/* 113 */     this.n.remove(control);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreenWithEnabledState(int mouseX, int mouseY, float partialTicks, boolean enabled) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawMiniButtons(int mouseX, int mouseY, float partialTicks) {
/* 124 */     this.hoverButton = null;
/* 125 */     this.promptBarStart = 2;
/* 126 */     this.promptBarEnd = this.l - 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawPromptBar(int mouseX, int mouseY, float partialTicks, int foreColour, int backColour) {
/* 131 */     a(this.promptBarStart, this.m - 14, this.promptBarEnd, this.m - 2, backColour);
/* 132 */     if (this.prompt != null)
/*     */     {
/* 134 */       c(this.q, this.prompt, this.promptBarStart + 2, this.m - 12, foreColour);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void q() throws IOException {
/* 144 */     super.q();
/*     */     
/* 146 */     int mouseWheelDelta = Mouse.getDWheel();
/*     */     
/* 148 */     if (mouseWheelDelta != 0)
/*     */     {
/* 150 */       mouseWheelScrolled(mouseWheelDelta);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void k() throws IOException {
/* 157 */     super.k();
/*     */     
/* 159 */     if (this.dragEvents && Mouse.getEventButton() == -1) {
/*     */       
/* 161 */       int mouseX = Mouse.getEventX() * this.l / this.j.d;
/* 162 */       int mouseY = this.m - Mouse.getEventY() * this.m / this.j.e - 1;
/*     */       
/* 164 */       a(mouseX, mouseY, -1, 0L);
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
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(int mouseX, int mouseY, int button) throws IOException {
/* 188 */     for (int controlIndex = 0; controlIndex < this.n.size(); controlIndex++) {
/*     */       
/* 190 */       bja guibutton = this.n.get(controlIndex);
/*     */ 
/*     */       
/* 193 */       if (button == 0 && guibutton.b(this.j, mouseX, mouseY)) {
/*     */         
/* 195 */         this.selectedButtonEx = guibutton;
/*     */         
/* 197 */         if (!(guibutton instanceof GuiControlEx) || ((GuiControlEx)guibutton).isActionPerformed())
/*     */         {
/* 199 */           this.j.U().a((cgt)cgp.a(qf.ic, 1.0F));
/* 200 */           a(guibutton);
/*     */ 
/*     */           
/* 203 */           if (this.j.m != this)
/*     */           {
/* 205 */             guibutton.a(mouseX, mouseY);
/*     */           }
/*     */         }
/*     */       
/* 209 */       } else if (button == 1 && guibutton instanceof GuiControl && ((GuiControl)guibutton).isMouseOver(this.j, mouseX, mouseY)) {
/*     */         
/* 211 */         this.rightClickedButton = (GuiControl)guibutton;
/* 212 */         this.rightClickedButton.rightClicked(this.j, mouseX, mouseY, true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void b(int mouseX, int mouseY, int button) {
/* 223 */     if (this.selectedButtonEx != null && button == 0) {
/*     */       
/* 225 */       this.selectedButtonEx.a(mouseX, mouseY);
/* 226 */       this.selectedButtonEx = null;
/*     */     } 
/*     */     
/* 229 */     if (this.rightClickedButton != null && button == 1) {
/*     */       
/* 231 */       if (this.rightClickedButton.isMouseOver(this.j, mouseX, mouseY))
/*     */       {
/* 233 */         this.rightClickedButton.rightClicked(this.j, mouseX, mouseY, false);
/*     */       }
/*     */       
/* 236 */       this.rightClickedButton = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onFinishEditingTextFile(GuiEditText editor, File file) {
/* 242 */     this.j.a(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public blk getDelegate() {
/* 248 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResolution(bib minecraft, int width, int height) {
/* 254 */     a(minecraft, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initParentGui() {
/* 260 */     b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayDialog(GuiDialogBox dialog) {
/* 266 */     this.j.a(dialog);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogClosed(GuiDialogBox dialog) {
/* 272 */     this.j.a(this);
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
/*     */   protected void drawSpacedString(String text, int xPos, int foreColour) {
/* 285 */     int additionalSpacing = 0;
/*     */     
/* 287 */     if (text != null) {
/*     */       
/* 289 */       while (text.endsWith("¬")) {
/*     */         
/* 291 */         additionalSpacing += 5;
/* 292 */         text = text.substring(0, text.length() - 1);
/*     */       } 
/*     */       
/* 295 */       this.q.a(text, xPos, this.rowPos, foreColour);
/*     */     } 
/*     */     
/* 298 */     this.rowPos += this.rowSpacing + additionalSpacing;
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
/*     */   protected void drawTooltip(String tooltipText, int mouseX, int mouseY, int colour, int backgroundColour) {
/* 312 */     this.renderer.drawTooltip(tooltipText, mouseX, mouseY, this.l, this.m, colour, backgroundColour);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawMiniButton(GuiMiniToolbarButton button, int mouseX, int mouseY, float partialTicks, boolean selected) {
/* 317 */     if (button == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 322 */     if (button.drawControlAt(this.j, mouseX, mouseY, partialTicks, this.promptBarStart, this.m - 14, selected))
/*     */     {
/* 324 */       this.hoverButton = button;
/*     */     }
/*     */     
/* 327 */     this.promptBarStart += button.getWidth() + 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawMiniButton(GuiMiniToolbarButton button, int mouseX, int mouseY, float partialTicks) {
/* 332 */     if (button == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 337 */     if (button.drawControlAt(this.j, mouseX, mouseY, partialTicks, this.promptBarEnd - button.getWidth(), this.m - 14))
/*     */     {
/* 339 */       this.hoverButton = button;
/*     */     }
/*     */     
/* 342 */     this.promptBarEnd -= button.getWidth() + 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\GuiScreenEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */