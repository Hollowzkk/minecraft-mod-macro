/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import blk;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.controls.GuiCheckBox;
/*     */ import net.eq2online.macros.permissions.MacroModPermissions;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
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
/*     */ public class GuiPermissions
/*     */   extends GuiScreenWithHeader
/*     */ {
/*  30 */   private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("HH:mm:ss");
/*     */ 
/*     */ 
/*     */   
/*     */   private final Macros macros;
/*     */ 
/*     */ 
/*     */   
/*     */   private final blk parentScreen;
/*     */ 
/*     */ 
/*     */   
/*  42 */   private int refreshTimer = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiControl btnRefresh;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiCheckBox chkWarnings;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<String> deniedActionsList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiPermissions(Macros macros, bib minecraft, blk parentScreen) {
/*  66 */     super(minecraft, 0, 0);
/*     */     
/*  68 */     this.macros = macros;
/*  69 */     this.parentScreen = parentScreen;
/*  70 */     this.title = I18n.get("macro.permissions.title");
/*  71 */     this.bgBottomMargin = 28;
/*     */     
/*  73 */     refreshPermissions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshPermissions() {
/*  81 */     long lastUpdateTime = MacroModPermissions.getLastUpdateTime();
/*  82 */     String updated = (lastUpdateTime > 0L) ? FORMAT_DATE.format(Long.valueOf(lastUpdateTime)) : I18n.get("time.never");
/*  83 */     this.banner = I18n.get("macro.permissions.banner", new Object[] { updated });
/*  84 */     this.deniedActionsList = ScriptAction.getDeniedActionList(ScriptContext.MAIN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  93 */     clearControlList();
/*     */     
/*  95 */     super.b();
/*     */     
/*  97 */     boolean generatePermissionsWarnings = (this.macros.getSettings()).generatePermissionsWarnings;
/*     */     
/*  99 */     addControl(new GuiControl(0, this.l - 64, this.m - 24, 60, 20, I18n.get("gui.ok"), 7));
/* 100 */     addControl(this.btnRefresh = new GuiControl(1, 4, this.m - 24, 120, 20, I18n.get("macro.permissions.gui.refresh"), 11));
/* 101 */     addControl((GuiControl)(this.chkWarnings = new GuiCheckBox(this.j, 2, 6, this.m - 50, I18n.get("macro.permissions.option.scriptwarnings"), generatePermissionsWarnings)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/* 111 */     super.e();
/*     */     
/* 113 */     if (this.refreshTimer > 0) this.refreshTimer--;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTick) {
/* 123 */     if (this.j.f == null)
/*     */     {
/* 125 */       c();
/*     */     }
/*     */     
/* 128 */     a(2, this.m - 26, this.l - 2, this.m - 2, this.backColour);
/*     */     
/* 130 */     this.btnRefresh.setEnabled((this.refreshTimer == 0));
/*     */     
/* 132 */     super.a(mouseX, mouseY, partialTick);
/*     */     
/* 134 */     int left = 6;
/*     */     
/* 136 */     this.q.a(I18n.get("macro.permissions.script.title"), left, 26.0F, -22016);
/* 137 */     this.q.a(I18n.get("macro.permissions.script.info1"), left, 38.0F, -1);
/*     */     
/* 139 */     int xPos = left + 4, yPos = 54;
/* 140 */     int spacing = 80;
/*     */     
/* 142 */     for (int i = 0; i < this.deniedActionsList.size(); i++) {
/*     */       
/* 144 */       if (xPos + spacing > this.l) {
/*     */         
/* 146 */         xPos = left + 4;
/* 147 */         yPos += 12;
/*     */       } 
/*     */       
/* 150 */       this.q.a(this.deniedActionsList.get(i), xPos, yPos, -1166541);
/* 151 */       xPos += spacing;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bja guiButton) {
/* 161 */     if (guiButton.k == 0) {
/*     */       
/* 163 */       (this.macros.getSettings()).generatePermissionsWarnings = this.chkWarnings.checked;
/*     */       
/* 165 */       onCloseClick();
/*     */       
/*     */       return;
/*     */     } 
/* 169 */     if (guiButton.k == this.btnRefresh.k) {
/*     */       
/* 171 */       MacroModPermissions.refreshPermissions(this.j);
/* 172 */       this.refreshTimer = 300;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) throws IOException {
/* 182 */     if (keyCode == 1) {
/*     */       
/* 184 */       onCloseClick();
/*     */       
/*     */       return;
/*     */     } 
/* 188 */     super.a(keyChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 197 */     this.j.a(this.parentScreen);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiPermissions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */