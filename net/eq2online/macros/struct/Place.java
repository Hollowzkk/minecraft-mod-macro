/*     */ package net.eq2online.macros.struct;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.gui.controls.specialised.GuiListBoxPlaces;
/*     */ import net.eq2online.macros.gui.helpers.ListProvider;
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
/*     */ public class Place
/*     */ {
/*  21 */   public static Pattern PATTERN = Pattern.compile("^(.+?)=\\(([0-9\\-]+),([0-9\\-]+),([0-9\\-]+)\\)$");
/*     */ 
/*     */ 
/*     */   
/*     */   public String name;
/*     */ 
/*     */ 
/*     */   
/*     */   public int x;
/*     */ 
/*     */ 
/*     */   
/*     */   public int y;
/*     */ 
/*     */ 
/*     */   
/*     */   public int z;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Place(String name, int x, int y, int z) {
/*  43 */     this.name = name;
/*     */     
/*  45 */     this.x = x;
/*  46 */     this.y = y;
/*  47 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  53 */     return String.format("%s=(%d,%d,%d)", new Object[] { this.name, Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Place parsePlace(ListProvider listProvider, String serialisedPlace) {
/*  64 */     Matcher placeInfo = PATTERN.matcher(serialisedPlace);
/*     */     
/*  66 */     if (placeInfo.matches()) {
/*     */       
/*  68 */       String placeName = resolveConflictingName(listProvider, placeInfo.group(1), null);
/*  69 */       return parsePlace(listProvider, placeName, placeInfo.group(2), placeInfo.group(3), placeInfo.group(4), true);
/*     */     } 
/*     */     
/*  72 */     return null;
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
/*     */   
/*     */   public static Place parsePlace(ListProvider listProvider, String placeName, String placeXCoordinate, String placeYCoordinate, String placeZCoordinate, boolean resolveConflictingNames) {
/*     */     try {
/*  89 */       int x = Integer.parseInt(placeXCoordinate);
/*  90 */       int y = Integer.parseInt(placeYCoordinate);
/*  91 */       int z = Integer.parseInt(placeZCoordinate);
/*     */       
/*  93 */       return new Place(resolveConflictingNames ? resolveConflictingName(listProvider, placeName, null) : placeName, x, y, z);
/*     */     }
/*  95 */     catch (NumberFormatException numberFormatException) {
/*     */       
/*  97 */       return null;
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
/*     */   public static boolean exists(ListProvider listProvider, String placeName) {
/* 109 */     return ((GuiListBoxPlaces)listProvider.getListBox(MacroParam.Type.PLACE)).containsPlace(placeName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Place getByName(ListProvider listProvider, String placeName) {
/* 120 */     return ((GuiListBoxPlaces)listProvider.getListBox(MacroParam.Type.PLACE)).getPlace(placeName);
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
/*     */   public static String resolveConflictingName(ListProvider listProvider, String placeName, Place thisPlace) {
/* 132 */     Place existingPlace = getByName(listProvider, placeName);
/*     */     
/* 134 */     if (existingPlace == null || existingPlace == thisPlace)
/*     */     {
/* 136 */       return placeName;
/*     */     }
/*     */     
/* 139 */     int offset = 1;
/*     */     
/* 141 */     while (exists(listProvider, placeName + "[" + offset + "]"))
/*     */     {
/* 143 */       offset++;
/*     */     }
/*     */     
/* 146 */     return placeName + "[" + offset + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\struct\Place.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */