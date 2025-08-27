package net.eq2online.macros.core.mixin;

import cdh;
import java.awt.image.BufferedImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({cdh.class})
public interface IThreadDownloadImageData {
  @Accessor("bufferedImage")
  BufferedImage getDownloadedImage();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\mixin\IThreadDownloadImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */