package net.eq2online.macros.core.overlays;

import aip;
import com.mumfrey.liteloader.transformers.access.Accessor;
import com.mumfrey.liteloader.transformers.access.Invoker;
import com.mumfrey.liteloader.transformers.access.ObfTableClass;
import fi;
import net.eq2online.obfuscation.ObfTbl;

@ObfTableClass(ObfTbl.class)
@Accessor({"ContainerCreative"})
public interface IContainerCreative {
  @Accessor({"itemsList"})
  fi<aip> getItemsList();
  
  @Invoker({"scrollTo"})
  void scrollToPosition(float paramFloat);
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\overlays\IContainerCreative.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */