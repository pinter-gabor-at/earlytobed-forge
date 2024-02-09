package eu.pintergabor.earlytobed.item;

import eu.pintergabor.earlytobed.ModCommon;

import net.minecraft.world.item.ShearsItem;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;

public final class ModItems {
	public static final DeferredRegister<Item> ITEMS =
		DeferredRegister.create(ForgeRegistries.ITEMS, ModCommon.MODID);
	// A wooden bucket
	public static final RegistryObject<Item> WOODEN_BUCKET_ITEM = ITEMS.register("wooden_bucket",
		() -> new WoodenBucketItem(Fluids.EMPTY, new Item.Properties()));
	// A wooden bucket, filled with water
	public static final RegistryObject<Item> WOODEN_WATER_BUCKET_ITEM = ITEMS.register("wooden_water_bucket",
		() -> new WoodenBucketItem(Fluids.WATER, new Item.Properties()));
	// A pair of wooden shears
	public static final RegistryObject<Item> WOODEN_SHEARS_ITEM = ITEMS.register("wooden_shears",
		() -> new ShearsItem(new Item.Properties().durability(3)));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
