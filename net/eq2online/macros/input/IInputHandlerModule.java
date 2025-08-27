package net.eq2online.macros.input;

import bib;
import java.nio.ByteBuffer;
import java.util.List;
import net.eq2online.macros.interfaces.ISettingsObserver;
import net.eq2online.macros.interfaces.ISettingsStore;

public interface IInputHandlerModule extends ISettingsObserver {
  void initialise(InputHandler paramInputHandler, ISettingsStore paramISettingsStore);
  
  void register(bib parambib, ISettingsStore paramISettingsStore);
  
  void update(List<InputHandler.InputEvent> paramList, boolean paramBoolean1, boolean paramBoolean2);
  
  boolean onTick(List<InputHandler.InputEvent> paramList);
  
  boolean injectEvents(InputHandler.BufferProcessor paramBufferProcessor, ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\input\IInputHandlerModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */