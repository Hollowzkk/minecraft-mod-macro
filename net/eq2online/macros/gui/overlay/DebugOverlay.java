/*     */ package net.eq2online.macros.gui.overlay;
/*     */ 
/*     */ import bib;
/*     */ import bip;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.eq2online.macros.core.MacroModCore;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.SpamFilter;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import rk;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DebugOverlay
/*     */   extends Overlay
/*     */ {
/*     */   private final InputHandler inputHandler;
/*     */   
/*     */   static class Var
/*     */   {
/*     */     final String name;
/*     */     final String value;
/*     */     final int colour;
/*     */     final int width;
/*     */     
/*     */     Var(bip fontRenderer, IScriptActionProvider provider, String name) {
/*  33 */       Object value = provider.getVariable(name, (IVariableProvider)null);
/*  34 */       if (value == null) {
/*     */         
/*  36 */         this.colour = 5592405;
/*     */       }
/*  38 */       else if (value instanceof Boolean) {
/*     */         
/*  40 */         this.colour = ((Boolean)value).booleanValue() ? -11141291 : -43691;
/*     */       }
/*     */       else {
/*     */         
/*  44 */         this.colour = (value instanceof Integer) ? -11141121 : -171;
/*     */       } 
/*     */       
/*  47 */       this.name = name;
/*  48 */       this.value = (value == null) ? "<invalid>" : String.valueOf(value);
/*  49 */       this.width = fontRenderer.a(name) + 10;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*  55 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Column
/*     */   {
/*     */     final int nameWidth;
/*     */     final int varWidth;
/*     */     final int totalWidth;
/*     */     
/*     */     Column(int nameWidth, int varWidth) {
/*  67 */       this.nameWidth = nameWidth;
/*  68 */       this.varWidth = varWidth;
/*  69 */       this.totalWidth = nameWidth + varWidth;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  75 */   private Column[] columns = new Column[0];
/*  76 */   private final List<Var> vars = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public DebugOverlay(Macros macros, bib minecraft, OverlayHandler handler) {
/*  80 */     super(macros, minecraft, handler);
/*     */     
/*  82 */     this.inputHandler = macros.getInputHandler();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(int mouseX, int mouseY, long tick, float partialTick, boolean clock) {
/*  88 */     if (this.settings.enableDebug) {
/*     */       
/*  90 */       this.mc.B.a("debug");
/*  91 */       drawDebug(clock);
/*  92 */       this.mc.B.b();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawDebug(boolean clock) {
/*  98 */     if (this.settings.debugEnvironment && !(this.mc.m instanceof net.eq2online.macros.gui.GuiScreenEx)) {
/*     */       
/* 100 */       this.mc.B.c("env");
/* 101 */       drawEnvironment(clock);
/*     */     } 
/*     */     
/* 104 */     if (!(this.mc.m instanceof net.eq2online.macros.gui.screens.GuiScreenWithHeader)) {
/*     */       
/* 106 */       this.mc.B.c("debug");
/* 107 */       drawStatus();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawEnvironment(boolean clock) {
/* 113 */     int left = 4;
/* 114 */     float top = this.mc.t.ax ? 150.0F : 10.0F;
/* 115 */     int height = (int)((this.height - top) * this.scaleFactor) - 90;
/* 116 */     int rows = rk.c(height / 10.0D);
/*     */     
/* 118 */     if (clock)
/*     */     {
/* 120 */       updateEnvironment(rows);
/*     */     }
/*     */     
/* 123 */     GL.glPushMatrix();
/* 124 */     GL.glTranslatef(0.0F, top, 0.0F);
/* 125 */     GL.glScalef(1.0F / this.scaleFactor, 1.0F / this.scaleFactor, 1.0F);
/*     */     
/* 127 */     this.mc.B.a("draw");
/*     */     
/* 129 */     for (int col = 0; col < this.columns.length; col++) {
/*     */       
/* 131 */       Column column = this.columns[col];
/* 132 */       int pos = col * rows;
/*     */       
/* 134 */       for (int row = 0; row < rows && pos + row < this.vars.size(); row++) {
/*     */         
/* 136 */         int index = pos + row;
/* 137 */         Var var = this.vars.get(index);
/* 138 */         int yPos = row * 10;
/* 139 */         a(left + 1, yPos - 1, left + column.totalWidth - 1, yPos + 8, 1711276032);
/* 140 */         this.fontRenderer.a(var + " :", left + column.nameWidth - var.width, yPos, -1);
/* 141 */         this.fontRenderer.a(var.value, left + column.nameWidth, yPos, var.colour);
/*     */       } 
/*     */       
/* 144 */       left += column.totalWidth;
/*     */     } 
/*     */     
/* 147 */     this.mc.B.b();
/*     */     
/* 149 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateEnvironment(int rows) {
/* 154 */     this.mc.B.a("update");
/*     */     
/* 156 */     IScriptActionProvider provider = ScriptContext.MAIN.getScriptActionProvider();
/*     */     
/* 158 */     this.vars.clear();
/* 159 */     for (String var : provider.getEnvironmentVariables()) {
/*     */       
/* 161 */       if (!var.startsWith("KEY_") || this.settings.debugEnvKeys)
/*     */       {
/* 163 */         this.vars.add(new Var(this.fontRenderer, provider, var));
/*     */       }
/*     */     } 
/*     */     
/* 167 */     this.columns = new Column[rk.f(this.vars.size() / rows)];
/*     */     
/* 169 */     for (int col = 0; col < this.columns.length; col++) {
/*     */       
/* 171 */       int pos = col * rows;
/* 172 */       int nameWidth = 10;
/* 173 */       int varWidth = 4;
/*     */       
/* 175 */       for (int row = 0; row < rows && pos + row < this.vars.size(); row++) {
/*     */         
/* 177 */         int index = pos + row;
/* 178 */         Var var = this.vars.get(index);
/* 179 */         nameWidth = Math.max(nameWidth, var.width);
/* 180 */         varWidth = Math.max(varWidth, this.fontRenderer.a(var.value) + 4);
/*     */       } 
/*     */       
/* 183 */       this.columns[col] = new Column(nameWidth, varWidth);
/*     */     } 
/*     */     
/* 186 */     this.mc.B.b();
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawStatus() {
/* 191 */     GL.glDisableLighting();
/* 192 */     GL.glPushMatrix();
/*     */     
/* 194 */     if (this.mc.t.aG != 1) {
/*     */       
/* 196 */       GL.glTranslatef((this.width - 100), (this.height - 40), 0.0F);
/* 197 */       GL.glScalef(0.5F, 0.5F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 201 */       GL.glTranslatef((this.width - 200), (this.height - 80), 0.0F);
/*     */     } 
/*     */     
/* 204 */     a(-5, -5, 200, 80, -1342177280);
/*     */     
/* 206 */     this.fontRenderer.a("mod_Macros version " + MacroModCore.getVersion(), 0.0F, 0.0F, -1056964864);
/* 207 */     this.fontRenderer.a("Config: " + this.macros
/* 208 */         .getActiveConfigName(), 0.0F, 10.0F, -1073676544);
/* 209 */     this.fontRenderer.a("Overlay: " + this.macros
/* 210 */         .getOverlayConfigName("§c"), 0.0F, 20.0F, -1073676544);
/* 211 */     this.fontRenderer.a("Mode: " + (
/* 212 */         this.inputHandler.isFallbackMode() ? "§cCompatible Mode" : "§6Enhanced Mode"), 0.0F, 30.0F, -1073676544);
/* 213 */     this.fontRenderer.a("Run: " + this.macros
/* 214 */         .getExecutingMacroCount() + " running macro(s)", 0.0F, 40.0F, -1073676544);
/* 215 */     this.fontRenderer.a("Script: " + 
/* 216 */         ScriptAction.getTickedActionCount() + " tick " + ScriptAction.getExecutedActionCount() + " run " + 
/* 217 */         ScriptAction.getSkippedActionCount() + " op", 0.0F, 50.0F, -1073676544);
/* 218 */     this.fontRenderer.a("Input: " + this.inputHandler
/* 219 */         .getPendingKeyEventCount() + " pending key event(s)", 0.0F, 60.0F, -1073676544);
/* 220 */     this.fontRenderer.a("Keys: " + this.inputHandler
/* 221 */         .getKeyDebugInfo(), 0.0F, 70.0F, -1073676544);
/*     */     
/* 223 */     SpamFilter spamFilter = this.macros.getSpamFilter();
/* 224 */     if (spamFilter != null) {
/*     */       
/* 226 */       int warnLevel = 20;
/* 227 */       int level = spamFilter.getSpamLevel();
/* 228 */       int warn = Math.max(0, level - spamFilter.getSpamLimit() - warnLevel);
/* 229 */       level -= warn;
/* 230 */       int danger = Math.max(0, warn - warnLevel);
/* 231 */       warn -= danger;
/*     */       
/* 233 */       a(190, -10, 195, -10 - level, -16711936);
/* 234 */       a(190, -10 - level, 195, -10 - level - warn, -256);
/* 235 */       a(190, -10 - level - warn, 195, -10 - level - warn - danger, -65536);
/*     */     } 
/*     */     
/* 238 */     GL.glPopMatrix();
/*     */     
/* 240 */     GL.glEnableLighting();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\overlay\DebugOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */