/*    */ package net.eq2online.macros.gui.list;
/*    */ 
/*    */ import com.mumfrey.liteloader.util.render.Icon;
/*    */ import net.eq2online.macros.compatibility.IconTiled;
/*    */ import net.eq2online.macros.interfaces.IStringSerialisable;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ import net.eq2online.macros.struct.Place;
/*    */ import nf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlaceListEntry
/*    */   extends EditableListEntry<Place>
/*    */   implements IStringSerialisable<Place>
/*    */ {
/*    */   public PlaceListEntry(int id, Place data) {
/* 19 */     super(id, 15, (String)null, ResourceLocations.EXT, data);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void initIcon(nf iconTexture, int iconID) {
/* 25 */     this.iconTexture = iconTexture;
/* 26 */     this.icon = (Icon)new IconTiled(iconTexture, 35, 32);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setData(Place newData) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getText() {
/* 38 */     return this.data.name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDisplayName() {
/* 44 */     return getText();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDisplayName(String newDisplayName) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void setText(String newText) {
/* 55 */     this.data.name = newText;
/* 56 */     super.setText(newText);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 62 */     return super.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String serialise() {
/* 68 */     return this.data.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\list\PlaceListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */