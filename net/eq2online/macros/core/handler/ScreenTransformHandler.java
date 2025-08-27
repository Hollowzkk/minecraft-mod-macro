/*     */ package net.eq2online.macros.core.handler;
/*     */ 
/*     */ import bhy;
/*     */ import bib;
/*     */ import bip;
/*     */ import bjm;
/*     */ import blk;
/*     */ import bmd;
/*     */ import bme;
/*     */ import cey;
/*     */ import java.util.Deque;
/*     */ import java.util.LinkedList;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.mixin.IGuiControls;
/*     */ import net.eq2online.macros.core.mixin.IGuiKeyBindingList;
/*     */ import net.eq2online.macros.core.mixin.IKeyEntry;
/*     */ import net.eq2online.macros.gui.ext.BindScreenOptionEntry;
/*     */ import net.eq2online.macros.gui.ext.BindingButtonEntry;
/*     */ import net.eq2online.macros.gui.ext.LinkableKeyEntry;
/*     */ import net.eq2online.macros.input.InputHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScreenTransformHandler
/*     */ {
/*     */   private final Macros macros;
/*     */   private final bib mc;
/*     */   private bme lastTransformedScreen;
/*     */   private boolean scrollToButtons;
/*     */   
/*     */   public ScreenTransformHandler(Macros macros, bib minecraft) {
/*  35 */     this.macros = macros;
/*  36 */     this.mc = minecraft;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender() {
/*  41 */     if (this.mc != null && this.mc.m != null) {
/*     */       
/*  43 */       blk guiscreen = this.mc.m;
/*     */       
/*  45 */       if (guiscreen instanceof bme) {
/*     */         
/*  47 */         if (guiscreen != this.lastTransformedScreen)
/*     */         {
/*  49 */           this.lastTransformedScreen = (bme)guiscreen;
/*  50 */           transformKeyBindings(this.mc);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  55 */         this.lastTransformedScreen = null;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  60 */       this.lastTransformedScreen = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  66 */     this.lastTransformedScreen = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void enableScrollToButtons() {
/*  71 */     this.scrollToButtons = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transformKeyBindings(bib minecraft) {
/*     */     try {
/*  81 */       bmd keyBindingList = ((IGuiControls)this.lastTransformedScreen).getKeybindList();
/*  82 */       bjm.a[] keyBindingEntries = ((IGuiKeyBindingList)keyBindingList).getListEntries();
/*  83 */       Deque<bjm.a> newList = new LinkedList<>();
/*     */       
/*  85 */       int maxDescriptionWidth = calcMaxDescriptionWidth(minecraft);
/*  86 */       int bindButtonIndex = 0;
/*     */       
/*  88 */       for (int bindIndex = 0; bindIndex < keyBindingEntries.length; bindIndex++) {
/*     */         
/*  90 */         bjm.a listEntry = keyBindingEntries[bindIndex];
/*  91 */         newList.add(listEntry);
/*     */         
/*  93 */         if (listEntry instanceof IKeyEntry) {
/*     */           
/*  95 */           bhy binding = ((IKeyEntry)listEntry).getBinding();
/*  96 */           if (binding == InputHandler.KEY_OVERRIDE) {
/*     */             
/*  98 */             newList.removeLast();
/*  99 */             bindButtonIndex = bindIndex;
/*     */ 
/*     */             
/* 102 */             newList.add(new LinkableKeyEntry(this.macros, minecraft, this.lastTransformedScreen, maxDescriptionWidth, binding, minecraft.t.Z, "enableOverride"));
/*     */             
/* 104 */             newList.add(new BindScreenOptionEntry(this.macros, minecraft, this.lastTransformedScreen, maxDescriptionWidth));
/* 105 */             newList.add(new BindingButtonEntry(this.macros, minecraft, this.lastTransformedScreen));
/*     */           }
/* 107 */           else if (binding == InputHandler.KEY_REPL) {
/*     */             
/* 109 */             newList.removeLast();
/* 110 */             newList.add(new LinkableKeyEntry(this.macros, minecraft, this.lastTransformedScreen, maxDescriptionWidth, binding, InputHandler.KEY_ACTIVATE, "enableRepl"));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 116 */       ((IGuiKeyBindingList)keyBindingList).setListEntries((bjm.a[])newList.toArray((Object[])keyBindingEntries));
/*     */       
/* 118 */       if (this.scrollToButtons)
/*     */       {
/* 120 */         this.scrollToButtons = false;
/* 121 */         keyBindingList.h(-2147483648);
/* 122 */         keyBindingList.h(bindButtonIndex * 20 - 40);
/*     */       }
/*     */     
/* 125 */     } catch (Exception ex) {
/*     */       
/* 127 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int calcMaxDescriptionWidth(bib minecraft) {
/* 133 */     int maxEntryWidth = 0;
/* 134 */     bip fontRenderer = minecraft.k;
/*     */     
/* 136 */     for (bhy binding : minecraft.t.as) {
/*     */       
/* 138 */       int entryWidth = fontRenderer.a(cey.a(binding.h(), new Object[0]));
/* 139 */       if (entryWidth > maxEntryWidth) maxEntryWidth = entryWidth;
/*     */     
/*     */     } 
/* 142 */     return maxEntryWidth;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\handler\ScreenTransformHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */