package zaftnotameni.creatania.registry.datagen;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import zaftnotameni.creatania.recipes.base.Inputs;
import zaftnotameni.creatania.recipes.base.Outputs;
import zaftnotameni.creatania.recipes.condenser.ManaCondenserRecipeBuilder;
import zaftnotameni.creatania.recipes.generator.ManaGeneratorRecipeBuilder;
import zaftnotameni.creatania.registry.CreataniaBlocks;

import java.util.function.Consumer;

import static zaftnotameni.creatania.registry.CreataniaFluids.ALL;
public class ForgeRecipeProvider extends RecipeProvider {
  public ForgeRecipeProvider(DataGenerator generator) { super(generator.getPackOutput()); }

  @Override
  protected void buildRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
    InventoryChangeTrigger.TriggerInstance trigger = inventoryTrigger(
      ItemPredicate.Builder.item().of(CreataniaBlocks.MANA_CASING.get().asItem()).build()
    );

    var botaniaManaFluid = new FluidStack(ALL.REAL_MANA.get(), 1000);
    var purifiedManaFluid = new FluidStack(ALL.PURE_MANA.get(), 1000);
    var air = net.minecraft.world.level.block.Blocks.AIR;
    var corruptedInertManaBlock = new ItemStack(CreataniaBlocks.CORRUPT_MANA_BLOCK.get(), 1);

    new ManaGeneratorRecipeBuilder(
      Inputs.fromFluidIngredient(FluidIngredient.fromFluidStack(purifiedManaFluid)),
      Outputs.fromFluidStack(botaniaManaFluid),
      "botania_mana")
      .unlockedBy("has_mana_machine_component", trigger)
      .save(pFinishedRecipeConsumer);

    new ManaCondenserRecipeBuilder(
      Inputs.empty(),
      Outputs.fromItemStack(corruptedInertManaBlock),
      "corrupted_inert_mana_block")
      .unlockedBy("has_mana_machine_component", trigger)
      .save(pFinishedRecipeConsumer);
  }
}
