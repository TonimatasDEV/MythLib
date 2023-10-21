package dev.tonimatas.mythlib.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.tonimatas.mythlib.fluid.base.FluidHolder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.apache.commons.lang3.NotImplementedException;

public class ClientHooks {
    @ExpectPlatform
    public static TextureAtlasSprite getFluidSprite(FluidHolder fluid) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static int getFluidColor(FluidHolder fluid) {
        throw new NotImplementedException();
    }
}
