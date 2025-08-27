/*     */ package net.eq2online.macros.gui.ext;
/*     */ 
/*     */ import a;
/*     */ import bhy;
/*     */ import bib;
/*     */ import bid;
/*     */ import bja;
/*     */ import bjm;
/*     */ import bme;
/*     */ import cey;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.settings.Settings;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OverrideKeyEntry
/*     */   implements bjm.a
/*     */ {
/*     */   private final Settings settings;
/*     */   private final bib minecraft;
/*     */   private final bme controlsGui;
/*     */   private final int maxDescriptionWidth;
/*     */   private final bhy binding;
/*     */   private final String description;
/*     */   private final bja bindButton;
/*     */   private final bja resetButton;
/*  28 */   private String textLink = I18n.get("gui.link");
/*  29 */   private String textUnlink = I18n.get("gui.unlink");
/*  30 */   private String textDisable = I18n.get("gui.disable");
/*  31 */   private String textDisabled = I18n.get("gui.key.disabled");
/*  32 */   private String textSprint = cey.a("key.sprint", new Object[0]).toUpperCase();
/*     */ 
/*     */   
/*     */   public OverrideKeyEntry(Macros macros, bib minecraft, bme controlsGui, int maxDescriptionWidth, bhy binding) {
/*  36 */     this.settings = macros.getSettings();
/*  37 */     this.minecraft = minecraft;
/*  38 */     this.binding = binding;
/*  39 */     this.controlsGui = controlsGui;
/*  40 */     this.maxDescriptionWidth = maxDescriptionWidth;
/*     */     
/*  42 */     this.description = cey.a(binding.h(), new Object[0]);
/*  43 */     this.bindButton = new bja(0, 0, 0, 75, 18, cey.a(binding.h(), new Object[0]));
/*  44 */     this.resetButton = new bja(0, 0, 0, 50, 18, this.textLink);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int id, int xPosition, int yPosition, int width, int height, int mouseX, int mouseY, boolean mouseOver, float partialTicks) {
/*  50 */     int keyCode = this.binding.j();
/*     */     
/*  52 */     boolean isEnabled = this.settings.enableOverride;
/*  53 */     boolean isDefault = (keyCode == this.binding.i());
/*  54 */     boolean isActive = (this.controlsGui.f == this.binding);
/*  55 */     boolean isConflicting = false;
/*     */     
/*  57 */     if (keyCode != 0)
/*     */     {
/*  59 */       for (bhy other : this.minecraft.t.as) {
/*     */         
/*  61 */         if (other != this.binding && other.j() == keyCode) {
/*     */           
/*  63 */           isConflicting = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*  69 */     this.minecraft.k.a(this.description, xPosition + 90 - this.maxDescriptionWidth, yPosition + height / 2 - this.minecraft.k.a / 2, 16777215);
/*     */     
/*  71 */     this.resetButton.h = xPosition + 190;
/*  72 */     this.resetButton.i = yPosition;
/*  73 */     this.resetButton.j = !isEnabled ? this.textLink : (isDefault ? this.textUnlink : this.textDisable);
/*  74 */     this.resetButton.a(this.minecraft, mouseX, mouseY, partialTicks);
/*     */     
/*  76 */     this.bindButton.h = xPosition + 105;
/*  77 */     this.bindButton.i = yPosition;
/*  78 */     this.bindButton.l = isEnabled;
/*  79 */     this.bindButton.j = !isEnabled ? this.textDisabled : (isDefault ? (a.l + this.textSprint) : bid.a(keyCode));
/*     */     
/*  81 */     if (isActive) {
/*     */       
/*  83 */       this.bindButton.j = a.p + "> " + a.o + this.bindButton.j + a.p + " <";
/*     */     }
/*  85 */     else if (isConflicting) {
/*     */       
/*  87 */       this.bindButton.j = a.m + this.bindButton.j;
/*     */     } 
/*     */     
/*  90 */     this.bindButton.a(this.minecraft, mouseX, mouseY, partialTicks);
/*  91 */     this.bindButton.l = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(int id, int mouseX, int mouseY, int button, int relX, int relY) {
/*  97 */     boolean isEnabled = this.settings.enableOverride;
/*     */     
/*  99 */     if (this.bindButton.b(this.minecraft, mouseX, mouseY)) {
/*     */       
/* 101 */       if (!isEnabled) {
/*     */         
/* 103 */         this.settings.enableOverride = true;
/* 104 */         this.settings.save();
/* 105 */         this.minecraft.t.a(this.binding, this.binding.i());
/* 106 */         return true;
/*     */       } 
/*     */       
/* 109 */       this.controlsGui.f = this.binding;
/* 110 */       return true;
/*     */     } 
/* 112 */     if (this.resetButton.b(this.minecraft, mouseX, mouseY)) {
/*     */       
/* 114 */       boolean isDefault = (this.binding.j() == this.binding.i());
/* 115 */       int newValue = !isEnabled ? this.binding.i() : (isDefault ? 29 : this.binding.i());
/* 116 */       this.minecraft.t.a(this.binding, newValue);
/* 117 */       bhy.c();
/*     */       
/* 119 */       if (!isEnabled) {
/*     */         
/* 121 */         this.settings.enableOverride = true;
/* 122 */         this.settings.save();
/*     */       }
/* 124 */       else if (isEnabled && !isDefault) {
/*     */         
/* 126 */         this.settings.enableOverride = false;
/* 127 */         this.settings.save();
/*     */       } 
/*     */       
/* 130 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(int id, int mouseX, int mouseY, int button, int p_148277_5_, int p_148277_6_) {
/* 141 */     this.bindButton.a(mouseX, mouseY);
/* 142 */     this.resetButton.a(mouseX, mouseY);
/*     */   }
/*     */   
/*     */   public void a(int p_178011_1_, int p_178011_2_, int p_178011_3_, float partialTicks) {}
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\ext\OverrideKeyEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */