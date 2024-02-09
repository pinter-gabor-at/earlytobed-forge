package eu.pintergabor.earlytobed.datagen;

import eu.pintergabor.earlytobed.item.ModItems;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

	public ModRecipeProvider(PackOutput pOutput) {
		super(pOutput);
	}

	@Override
	protected void buildRecipes(@NotNull RecipeOutput pWriter) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOODEN_BUCKET_ITEM.get())
			.pattern("   ")
			.pattern("W W")
			.pattern(" W ")
			.define('W', ItemTags.LOGS)
			.unlockedBy("has_logs", has(ItemTags.LOGS))
			.save(pWriter);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOODEN_SHEARS_ITEM.get())
			.pattern("   ")
			.pattern(" W ")
			.pattern("W  ")
			.define('W', ItemTags.LOGS)
			.unlockedBy("has_logs", has(ItemTags.LOGS))
			.save(pWriter);
	}
}
