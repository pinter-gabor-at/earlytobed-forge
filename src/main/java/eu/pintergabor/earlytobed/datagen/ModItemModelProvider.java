package eu.pintergabor.earlytobed.datagen;

import eu.pintergabor.earlytobed.ModCommon;
import eu.pintergabor.earlytobed.item.ModItems;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, ModCommon.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		simpleItem(ModItems.WOODEN_BUCKET_ITEM);
		simpleItem(ModItems.WOODEN_WATER_BUCKET_ITEM);
		simpleItem(ModItems.WOODEN_SHEARS_ITEM);
	}

	@SuppressWarnings("UnusedReturnValue")
	private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(),
			new ResourceLocation("item/generated")).texture("layer0",
			new ResourceLocation(ModCommon.MODID, "item/" + item.getId().getPath()));
	}
}
