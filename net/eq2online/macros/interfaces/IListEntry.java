/*    */ package net.eq2online.macros.interfaces;public interface IListEntry<T> { CustomAction getCustomAction(boolean paramBoolean);
/*    */   boolean hasIcon();
/*    */   nf getIconTexture();
/*    */   Icon getIcon();
/*    */   void setIconId(int paramInt);
/*    */   boolean renderIcon(bib parambib, int paramInt1, int paramInt2);
/*    */   T getData();
/*    */   void setData(T paramT);
/*    */   String getText();
/*    */   void setText(String paramString);
/*    */   String getDisplayName();
/*    */   void setDisplayName(String paramString);
/*    */   int getId();
/*    */   void setId(int paramInt);
/*    */   String getIdentifier();
/*    */   
/* 17 */   public enum CustomAction { NONE, EDIT, DELETE; }
/*    */    }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\IListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */