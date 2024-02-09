package eu.pintergabor.earlytobed;

import com.mojang.logging.LogUtils;
import eu.pintergabor.earlytobed.item.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
		modEventBus.addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::addCreative);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
	}

	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(ModItems.WOODEN_BUCKET_ITEM);
			event.accept(ModItems.WOODEN_WATER_BUCKET_ITEM);
			event.accept(ModItems.WOODEN_SHEARS_ITEM);
		}
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
	}

	// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
		}
	}
}
