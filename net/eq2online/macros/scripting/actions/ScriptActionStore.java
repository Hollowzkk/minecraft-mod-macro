/*    */ package net.eq2online.macros.scripting.actions;
/*    */ 
/*    */ import net.eq2online.macros.core.params.MacroParam;
/*    */ import net.eq2online.macros.gui.controls.GuiListBox;
/*    */ import net.eq2online.macros.gui.helpers.ListProvider;
/*    */ import net.eq2online.macros.scripting.ScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.api.IMacro;
/*    */ import net.eq2online.macros.scripting.api.IMacroAction;
/*    */ import net.eq2online.macros.scripting.api.IReturnValue;
/*    */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*    */ import net.eq2online.macros.scripting.parser.ScriptAction;
/*    */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*    */ import net.eq2online.macros.struct.Place;
/*    */ import rk;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScriptActionStore
/*    */   extends ScriptAction
/*    */ {
/*    */   protected boolean overwrite = false;
/*    */   
/*    */   public ScriptActionStore(ScriptContext context) {
/* 28 */     super(context, "store");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ScriptActionStore(ScriptContext context, String actionName) {
/* 33 */     super(context, actionName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
/* 39 */     doStore((ScriptActionProvider)provider, params, this.overwrite);
/*    */     
/* 41 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   private void doStore(ScriptActionProvider scriptActionProvider, String[] params, boolean replace) {
/* 46 */     if (params.length > 0)
/*    */     {
/* 48 */       if (params[0].equalsIgnoreCase("place")) {
/*    */         
/* 50 */         ListProvider listProvider = this.macros.getListProvider();
/*    */         
/* 52 */         String placeName = (params.length > 1) ? params[1] : "Saved place";
/* 53 */         String xPos = String.valueOf(rk.c(this.mc.h.p));
/* 54 */         String yPos = String.valueOf(rk.c(this.mc.h.q));
/* 55 */         String zPos = String.valueOf(rk.c(this.mc.h.r));
/* 56 */         Place newPlace = Place.parsePlace(listProvider, placeName, xPos, yPos, zPos, true);
/* 57 */         GuiListBox<Place> placesList = listProvider.getListBox(MacroParam.Type.PLACE);
/*    */         
/* 59 */         if (replace && Place.exists(listProvider, placeName)) {
/*    */           
/* 61 */           Place oldPlace = Place.getByName(listProvider, placeName);
/*    */ 
/*    */           
/* 64 */           oldPlace.x = newPlace.x;
/* 65 */           oldPlace.y = newPlace.y;
/* 66 */           oldPlace.z = newPlace.z;
/*    */           
/* 68 */           placesList.save();
/*    */         }
/*    */         else {
/*    */           
/* 72 */           scriptActionProvider.actionAddItemToList(placesList, MacroParam.Type.PLACE, newPlace.name, newPlace.name, -1, newPlace);
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\actions\ScriptActionStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */