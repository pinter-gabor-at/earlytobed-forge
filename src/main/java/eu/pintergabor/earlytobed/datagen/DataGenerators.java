package eu.pintergabor.earlytobed.datagen;

import java.util.concurrent.CompletableFuture;

import eu.pintergabor.earlytobed.ModCommon;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = ModCommon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
		generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
	}
}
