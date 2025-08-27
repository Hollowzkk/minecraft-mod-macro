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
/*     */ public class LinkableKeyEntry
/*     */   implements bjm.a
/*     */ {
/*     */   private final Settings settings;
/*     */   private final bib minecraft;
/*     */   private final bme controlsGui;
/*     */   private final String enabledSetting;
/*     */   private final int maxDescriptionWidth;
/*     */   private final bhy binding;
/*     */   private final bhy linkTo;
/*     */   private final String description;
/*     */   private final bja bindButton;
/*     */   private final bja resetButton;
/*     */   
/*     */   public LinkableKeyEntry(Macros macros, bib minecraft, bme controlsGui, int maxDescriptionWidth, bhy binding, bhy linkTo, String enabledSetting) {
/*  30 */     this.settings = macros.getSettings();
/*  31 */     this.minecraft = minecraft;
/*  32 */     this.enabledSetting = enabledSetting;
/*  33 */     this.binding = binding;
/*  34 */     this.linkTo = linkTo;
/*  35 */     this.controlsGui = controlsGui;
/*  36 */     this.maxDescriptionWidth = maxDescriptionWidth;
/*     */     
/*  38 */     this.description = cey.a(binding.h(), new Object[0]);
/*  39 */     this.bindButton = new bja(0, 0, 0, 75, 18, cey.a(binding.h(), new Object[0]));
/*  40 */     this.resetButton = new bja(0, 0, 0, 50, 18, I18n.get("gui.link"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int id, int xPosition, int yPosition, int width, int height, int mouseX, int mouseY, boolean mouseOver, float partialTicks) {
/*  47 */     int keyCode = this.binding.j();
/*     */     
/*  49 */     boolean isEnabled = ((Boolean)this.settings.getSetting(this.enabledSetting)).booleanValue();
/*  50 */     boolean isLinked = (keyCode == this.binding.i());
/*  51 */     boolean isActive = (this.controlsGui.f == this.binding);
/*  52 */     boolean isConflicting = false;
/*     */     
/*  54 */     if (keyCode != 0)
/*     */     {
/*  56 */       for (bhy other : this.minecraft.t.as) {
/*     */         
/*  58 */         if (other != this.binding && other.j() == keyCode) {
/*     */           
/*  60 */           isConflicting = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*  66 */     this.minecraft.k.a(this.description, xPosition + 90 - this.maxDescriptionWidth, yPosition + height / 2 - this.minecraft.k.a / 2, 16777215);
/*     */ 
/*     */     
/*  69 */     this.resetButton.h = xPosition + 190;
/*  70 */     this.resetButton.i = yPosition;
/*  71 */     this.resetButton.j = !isEnabled ? I18n.get("gui.link") : (isLinked ? I18n.get("gui.unlink") : I18n.get("gui.disable"));
/*  72 */     this.resetButton.a(this.minecraft, mouseX, mouseY, partialTicks);
/*     */     
/*  74 */     this.bindButton.h = xPosition + 105;
/*  75 */     this.bindButton.i = yPosition;
/*  76 */     this.bindButton.l = isEnabled;
/*  77 */     this.bindButton
/*     */ 
/*     */       
/*  80 */       .j = isEnabled ? (isLinked ? (a.l + cey.a(this.linkTo.h(), new Object[0]).toUpperCase()) : bid.a(keyCode)) : I18n.get("gui.key.disabled");
/*     */     
/*  82 */     if (isActive) {
/*     */       
/*  84 */       this.bindButton.j = a.p + "> " + a.o + this.bindButton.j + a.p + " <";
/*     */     
/*     */     }
/*  87 */     else if (isConflicting) {
/*     */       
/*  89 */       this.bindButton.j = a.m + this.bindButton.j;
/*     */     } 
/*     */     
/*  92 */     this.bindButton.a(this.minecraft, mouseX, mouseY, partialTicks);
/*  93 */     this.bindButton.l = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(int id, int mouseX, int mouseY, int button, int relX, int relY) {
/*  99 */     boolean isEnabled = ((Boolean)this.settings.getSetting(this.enabledSetting)).booleanValue();
/*     */     
/* 101 */     if (this.bindButton.b(this.minecraft, mouseX, mouseY)) {
/*     */       
/* 103 */       if (!isEnabled) {
/*     */         
/* 105 */         this.settings.setSetting(this.enabledSetting, Boolean.TRUE);
/* 106 */         this.settings.save();
/* 107 */         this.minecraft.t.a(this.binding, this.binding.i());
/* 108 */         return true;
/*     */       } 
/*     */       
/* 111 */       this.controlsGui.f = this.binding;
/* 112 */       return true;
/*     */     } 
/* 114 */     if (this.resetButton.b(this.minecraft, mouseX, mouseY)) {
/*     */       
/* 116 */       boolean isDefault = (this.binding.j() == this.binding.i());
/* 117 */       int newValue = !isEnabled ? this.binding.i() : (isDefault ? this.linkTo.j() : this.binding.i());
/* 118 */       this.minecraft.t.a(this.binding, newValue);
/* 119 */       bhy.c();
/*     */       
/* 121 */       if (!isEnabled) {
/*     */         
/* 123 */         this.settings.setSetting(this.enabledSetting, Boolean.TRUE);
/* 124 */         this.settings.save();
/*     */       }
/* 126 */       else if (isEnabled && !isDefault) {
/*     */         
/* 128 */         this.settings.setSetting(this.enabledSetting, Boolean.FALSE);
/* 129 */         this.settings.save();
/*     */       } 
/*     */       
/* 132 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(int id, int mouseX, int mouseY, int button, int width, int height) {
/* 143 */     this.bindButton.a(mouseX, mouseY);
/* 144 */     this.resetButton.a(mouseX, mouseY);
/*     */   }
/*     */   
/*     */   public void a(int entryID, int insideLeft, int yPos, float partialTicks) {}
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\ext\LinkableKeyEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */