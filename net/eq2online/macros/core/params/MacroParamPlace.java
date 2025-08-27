/*     */ package net.eq2online.macros.core.params;
/*     */ 
/*     */ import bib;
/*     */ import blk;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macro;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.controls.GuiTextFieldEx;
/*     */ import net.eq2online.macros.gui.helpers.ListProvider;
/*     */ import net.eq2online.macros.gui.screens.GuiEditListEntry;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.struct.Place;
/*     */ 
/*     */ public class MacroParamPlace
/*     */   extends MacroParamGenericEditableList<Place>
/*     */ {
/*     */   public MacroParamPlace(Macros macros, bib mc, MacroParam.Type type, IMacroParamTarget target, MacroParams params, MacroParamProvider<Place> provider) {
/*  21 */     super(macros, mc, type, target, params, provider);
/*  22 */     this.enableTextField = Boolean.valueOf(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean replace() {
/*  31 */     if (this.target.getIteration() == 1 && this.target.getParamStore() != null)
/*     */     {
/*  33 */       this.target.getParamStore().setStoredParam(this.type, getParameterValue());
/*     */     }
/*     */     
/*  36 */     Place location = Place.getByName(this.macros.getListProvider(), getParameterValue());
/*     */     
/*  38 */     if (location != null) {
/*     */       
/*  40 */       String script = this.target.getTargetString();
/*  41 */       script = script.replaceAll("\\$\\$px", "" + location.x);
/*  42 */       script = script.replaceAll("\\$\\$py", "" + location.y);
/*  43 */       script = script.replaceAll("\\$\\$pz", "" + location.z);
/*  44 */       script = script.replaceAll("\\$\\$pn", "" + Macro.escapeReplacement(location.name));
/*  45 */       script = script.replaceAll("\\$\\$p", "" + String.format(this.settings.coordsFormat, new Object[] { Integer.valueOf(location.x), Integer.valueOf(location.y), Integer.valueOf(location.z) }));
/*  46 */       this.target.setTargetString(script);
/*     */     }
/*     */     else {
/*     */       
/*  50 */       this.target.setTargetString(this.provider.matcher(this.target.getTargetString()).replaceAll(""));
/*     */     } 
/*     */     
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPromptMessage() {
/*  62 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoPopulateSupported() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addItem(GuiEditListEntry<Place> gui, String newItem, String displayName, int iconID, Place newItemData) {
/*  83 */     Place place = Place.parsePlace(this.macros.getListProvider(), newItem, gui.getXCoordText(), gui.getYCoordText(), gui.getZCoordText(), true);
/*     */     
/*  85 */     if (place != null) {
/*     */       
/*  87 */       ListProvider listProvider = this.macros.getListProvider();
/*  88 */       if (Place.exists(listProvider, place.name)) {
/*     */         
/*  90 */         String placeName = place.name;
/*  91 */         int offset = 1;
/*     */         
/*  93 */         while (Place.exists(listProvider, placeName + "[" + offset + "]"))
/*     */         {
/*  95 */           offset++;
/*     */         }
/*     */         
/*  98 */         place.name = placeName + "[" + offset + "]";
/*     */       } 
/*     */       
/* 101 */       super.addItem(gui, place.name, displayName, -1, place);
/*     */     } 
/*     */     
/* 104 */     return false;
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
/*     */   public void editItem(GuiEditListEntry<Place> gui, String editedText, String displayName, int editedIconID, IListEntry<Place> editedObject) {
/*     */     try {
/* 118 */       int x = Integer.parseInt(gui.getXCoordText());
/* 119 */       int y = Integer.parseInt(gui.getYCoordText());
/* 120 */       int z = Integer.parseInt(gui.getZCoordText());
/*     */       
/* 122 */       Place place = (Place)editedObject.getData();
/*     */       
/* 124 */       place.name = Place.resolveConflictingName(this.macros.getListProvider(), editedText, place);
/* 125 */       place.x = x;
/* 126 */       place.y = y;
/* 127 */       place.z = z;
/*     */       
/* 129 */       editedObject.setText(place.name);
/* 130 */       this.itemListBox.save();
/*     */       
/* 132 */       this.mc.a((blk)this.parentScreen);
/*     */     }
/* 134 */     catch (NumberFormatException numberFormatException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteSelectedItem(boolean response) {
/* 145 */     if (this.itemListBox != null)
/*     */     {
/* 147 */       if (response) {
/*     */         
/* 149 */         this.itemListBox.removeSelectedItem();
/* 150 */         this.itemListBox.save();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 155 */         setParameterValue(((Place)this.itemListBox.getSelectedItem().getData()).name);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleListItemClick(IListEntry<Place> selectedItem) {
/* 167 */     setParameterValue(((Place)selectedItem.getData()).name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryWindow(GuiEditListEntry<Place> gui, boolean editing) {
/* 177 */     gui.displayText = I18n.get(editing ? "entry.editplace" : "entry.newplace");
/* 178 */     gui.enableIconChoice = false;
/* 179 */     gui.enableCoords = true;
/* 180 */     gui.windowHeight = 140;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupEditEntryTextbox(GuiTextFieldEx textField) {
/* 190 */     textField.minStringLength = 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\MacroParamPlace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */