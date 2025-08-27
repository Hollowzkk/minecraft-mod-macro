/*     */ package net.eq2online.macros.gui.controls.specialised;
/*     */ 
/*     */ import bib;
/*     */ import bit;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.gui.controls.GuiDropDownMenu;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.list.ConfigListEntry;
/*     */ import net.eq2online.macros.gui.list.ListEntry;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IRefreshable;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiListBoxConfigs
/*     */   extends GuiListBox<String>
/*     */   implements IRefreshable
/*     */ {
/*     */   private final Macros macros;
/*     */   private GuiDropDownMenu contextMenu;
/*  28 */   private Point contextMenuLocation = new Point();
/*     */   
/*     */   private String clickedConfig;
/*     */ 
/*     */   
/*     */   public GuiListBoxConfigs(Macros macros, bib minecraft, int controlId, int controlWidth, int controlHeight) {
/*  34 */     this(macros, minecraft, controlId, 202, 21, controlWidth, controlHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiListBoxConfigs(Macros macros, bib minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight) {
/*  39 */     super(minecraft, controlId, xPos, yPos, controlWidth, controlHeight, 16, true, false, false);
/*  40 */     this.macros = macros;
/*     */     
/*  42 */     this.contextMenu = new GuiDropDownMenu(minecraft, true);
/*  43 */     this.contextMenu.addItem("default", I18n.get("macro.config.setstartup"), 32, 40, ResourceLocations.EXT);
/*     */     
/*  45 */     this.iconSpacing = 18;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawControl(bib minecraft, int mouseX, int mouseY, float partialTicks) {
/*  51 */     super.drawControl(minecraft, mouseX, mouseY, partialTicks);
/*     */     
/*  53 */     if (this.m) {
/*     */       
/*  55 */       GL.glPushMatrix();
/*  56 */       GL.glTranslatef(0.0F, 0.0F, 500.0F);
/*  57 */       this.contextMenu.drawControlAt(this.contextMenuLocation, mouseX, mouseY);
/*  58 */       GL.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh() {
/*  68 */     clear();
/*  69 */     addItem((IListEntry)new ConfigListEntry(this.macros, -1, ""));
/*     */     
/*  71 */     int id = 0;
/*  72 */     for (String config : this.macros.getConfigNames())
/*     */     {
/*  74 */       addItem((IListEntry)new ConfigListEntry(this.macros, id++, config));
/*     */     }
/*     */     
/*  77 */     selectData(this.macros.getActiveConfig());
/*  78 */     scrollToCentre();
/*  79 */     addItem((IListEntry)new ListEntry(-2, I18n.get("options.addconfig")));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean b(bib minecraft, int mouseX, int mouseY) {
/*  85 */     GuiControlEx.HandledState result = mousePressed(mouseX, mouseY, 0);
/*  86 */     return (result != GuiControlEx.HandledState.NONE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean rightClicked(bib minecraft, int mouseX, int mouseY, boolean buttonState) {
/*  92 */     IListEntry<String> clicked = getItemAtPosition(mouseX, mouseY);
/*  93 */     if (this.m && clicked != null && clicked.getData() != null) {
/*     */       
/*  95 */       bit sr = new bit(this.mc);
/*  96 */       int width = sr.a();
/*  97 */       int height = sr.b();
/*     */       
/*  99 */       Dimension contextMenuSize = this.contextMenu.getSize();
/* 100 */       this
/*     */         
/* 102 */         .contextMenuLocation = new Point(Math.min(mouseX, width - contextMenuSize.width), Math.min(mouseY - 8, height - contextMenuSize.height));
/* 103 */       this.contextMenu.showDropDown();
/* 104 */       this.clickedConfig = (String)clicked.getData();
/* 105 */       return true;
/*     */     } 
/*     */     
/* 108 */     this.contextMenu.mousePressed(0, 0);
/* 109 */     this.clickedConfig = null;
/*     */     
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiControlEx.HandledState mousePressed(int mouseX, int mouseY, int button) {
/* 116 */     GuiDropDownMenu.Item item = this.contextMenu.mousePressed(mouseX, mouseY);
/* 117 */     if (item != null && this.clickedConfig != null) {
/*     */       
/* 119 */       (this.macros.getSettings()).initialConfiguration = this.clickedConfig;
/* 120 */       return GuiControlEx.HandledState.HANDLED;
/*     */     } 
/*     */     
/* 123 */     if (button == 0 && super.b(this.mc, mouseX, mouseY))
/*     */     {
/* 125 */       return this.actionPerformed ? GuiControlEx.HandledState.ACTION_PERFORMED : GuiControlEx.HandledState.HANDLED;
/*     */     }
/* 127 */     if (button == 1 && rightClicked(this.mc, mouseX, mouseY, true))
/*     */     {
/* 129 */       return GuiControlEx.HandledState.HANDLED;
/*     */     }
/*     */     
/* 132 */     return GuiControlEx.HandledState.NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY) {
/* 138 */     super.a(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiControlEx.HandledState keyTyped(char keyChar, int keyCode) {
/* 144 */     this.contextMenu.mousePressed(0, 0);
/*     */     
/* 146 */     if (keyCode == 200) {
/*     */       
/* 148 */       up();
/* 149 */       return GuiControlEx.HandledState.ACTION_PERFORMED;
/*     */     } 
/*     */     
/* 152 */     if (keyCode == 208) {
/*     */       
/* 154 */       down();
/* 155 */       return GuiControlEx.HandledState.ACTION_PERFORMED;
/*     */     } 
/*     */     
/* 158 */     if (keyCode == 28) {
/*     */       
/* 160 */       this.m = false;
/* 161 */       return GuiControlEx.HandledState.HANDLED;
/*     */     } 
/*     */     
/* 164 */     if (keyCode == 156) {
/*     */       
/* 166 */       this.m = false;
/* 167 */       return GuiControlEx.HandledState.HANDLED;
/*     */     } 
/*     */     
/* 170 */     if (keyCode == 1) {
/*     */       
/* 172 */       this.m = false;
/* 173 */       return GuiControlEx.HandledState.HANDLED;
/*     */     } 
/*     */     
/* 176 */     return GuiControlEx.HandledState.NONE;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\controls\specialised\GuiListBoxConfigs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */