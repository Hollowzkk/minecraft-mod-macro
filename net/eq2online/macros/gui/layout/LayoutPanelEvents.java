/*    */ package net.eq2online.macros.gui.layout;
/*    */ 
/*    */ import bib;
/*    */ import bip;
/*    */ import bir;
/*    */ import java.awt.Point;
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import net.eq2online.macros.core.MacroTriggerType;
/*    */ import net.eq2online.macros.core.Macros;
/*    */ import net.eq2online.macros.event.MacroEventManager;
/*    */ import net.eq2online.macros.interfaces.ILayoutWidget;
/*    */ import net.eq2online.macros.rendering.FontRendererLegacy;
/*    */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LayoutPanelEvents
/*    */   extends LayoutPanelStandard
/*    */ {
/*    */   private static final int BUTTONS_PER_COLUMN = 9;
/*    */   private static final int COLUMN_WIDTH = 139;
/*    */   private static final int ROW_HEIGHT = 20;
/*    */   private static final int LEFT_POS = 6;
/*    */   private static final int TOP_POS = 4;
/*    */   
/*    */   public LayoutPanelEvents(Macros macros, bib minecraft, int controlId) {
/* 31 */     super(macros, minecraft, controlId, "events.layout", MacroTriggerType.EVENT);
/*    */     
/* 33 */     this.layoutSettingDescription = "Serialised version of the event layout, each param is {MappingID,X,Y}";
/* 34 */     this.defaultLayout = "{1000,6,4}{1001,6,24}{1002,6,44}{1003,6,64}{1004,6,84}{1005,6,104}{1006,6,124}{1007,6,144}{1016,6,164}{1008,145,4}{1009,145,24}{1010,145,44}{1011,145,64}{1012,145,84}{1013,145,104}{1014,145,124}{1015,145,144}{1017,145,164}{1018,284,4}";
/*    */ 
/*    */ 
/*    */     
/* 38 */     this.widgetWidth = 135;
/* 39 */     this.widgetHeight = 16;
/* 40 */     this.centreAlign = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void loadPanelLayout(String panelLayout) {
/* 47 */     this.widgets = new ILayoutWidget[10000];
/* 48 */     MacroEventManager eventManager = this.macros.getEventManager();
/* 49 */     List<IMacroEvent> events = eventManager.getEvents();
/* 50 */     int nextAvailablePos = 0;
/*    */     
/* 52 */     for (IMacroEvent event : events) {
/*    */       
/* 54 */       int row = nextAvailablePos % 9;
/* 55 */       int col = nextAvailablePos / 9;
/* 56 */       nextAvailablePos++;
/*    */       
/* 58 */       int xPosition = 6 + 139 * col;
/* 59 */       int yPosition = 4 + 20 * row;
/*    */       
/* 61 */       ILayoutWidget<LayoutPanelStandard> widget = getWidget(eventManager.getEventID(event.getName()), true);
/* 62 */       if (widget.getPosition((bir)this).equals(new Point(0, 0))) widget.setPosition((bir)this, xPosition, yPosition);
/*    */     
/*    */     } 
/* 65 */     Matcher layoutPatternMatcher = this.layoutPattern.matcher(panelLayout);
/*    */     
/* 67 */     while (layoutPatternMatcher.find()) {
/*    */       
/* 69 */       int id = Integer.parseInt(layoutPatternMatcher.group(1));
/*    */       
/* 71 */       if (eventManager.getEvent(id) != null) {
/*    */         
/* 73 */         int xCoord = Integer.parseInt(layoutPatternMatcher.group(2));
/* 74 */         int yCoord = Integer.parseInt(layoutPatternMatcher.group(3));
/* 75 */         ILayoutWidget<LayoutPanelStandard> widget = getWidget(id, true);
/* 76 */         if (widget != null) widget.setPosition((bir)this, xCoord, yCoord);
/*    */       
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ILayoutWidget createWidget(int id) {
/* 88 */     return new LayoutButtonEvent(this.macros, this.mc, (bip)FontRendererLegacy.getInstance(), id, this.macroType.getName(this.macros, id), this.widgetWidth, this.widgetHeight, this.centreAlign);
/*    */   }
/*    */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\layout\LayoutPanelEvents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */