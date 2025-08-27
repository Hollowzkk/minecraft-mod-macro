/*    */ package net.eq2online.macros.gui.list;
/*    */ 
/*    */ import com.mumfrey.liteloader.util.render.Icon;
/*    */ import net.eq2online.macros.compatibility.IconTiled;
/*    */ import net.eq2online.macros.gui.skins.UserSkinHandler;
/*    */ import net.eq2online.macros.res.ResourceLocations;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnlineUserListEntry
/*    */   extends ListEntry<String>
/*    */ {
/*    */   private final UserSkinHandler userSkinHandler;
/* 20 */   int previousIconID = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OnlineUserListEntry(int id, String username, UserSkinHandler userSkinHandler) {
/* 30 */     super(id, username, username, true, ResourceLocations.PLAYERS, 0);
/* 31 */     this.userSkinHandler = userSkinHandler;
/*    */ 
/*    */     
/* 34 */     this.userSkinHandler.addUser(username);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IconTiled getIcon() {
/* 40 */     return this.userSkinHandler.getIconForSkin(this.text);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\list\OnlineUserListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */