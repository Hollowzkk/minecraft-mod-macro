package net.eq2online.macros.interfaces;

public interface IDragDrop<I> {
  boolean isValidDragTarget();
  
  boolean isValidDragSource();
  
  void addDragTarget(IDragDrop<I> paramIDragDrop);
  
  void addDragTarget(IDragDrop<I> paramIDragDrop, boolean paramBoolean);
  
  void removeDragTarget(IDragDrop<I> paramIDragDrop);
  
  void removeDragTarget(IDragDrop<I> paramIDragDrop, boolean paramBoolean);
  
  boolean dragDrop(IDragDrop<I> paramIDragDrop, IListEntry<I> paramIListEntry, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\IDragDrop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */