package eu.pintergabor.earlytobed;

import com.mojang.logging.LogUtils;
import eu.pintergabor.earlytobed.item.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import net.minecraft.world.item.CreativeModeTabs;

// The value here should match an entry in the META-INF/mods.toml file
@SuppressWarnings("unused")
@Mod(ModCommon.MODID)
public class ModCommon {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final String MODID = "earlytobed";

	public ModCommon() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModItems.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::addCreative);
	}

	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(ModItems.WOODEN_BUCKET_ITEM);
			event.accept(ModItems.WOODEN_WATER_BUCKET_ITEM);
			event.accept(ModItems.WOODEN_SHEARS_ITEM);
		}
	}
}
