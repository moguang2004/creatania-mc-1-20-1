package zaftnotameni.creatania.registry.datagen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import zaftnotameni.creatania.Constants;
import zaftnotameni.creatania.registry.datagen.botania.*;
import zaftnotameni.creatania.registry.datagen.processing.ForgeCreateProcessingRecipeProvider;

import java.util.concurrent.CompletableFuture;

import static zaftnotameni.creatania.Constants.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeDatagenInitializer {
  @SubscribeEvent
  public static void onGatherDataEvent(GatherDataEvent evt) {
    var generator = evt.getGenerator();
    var helper = evt.getExistingFileHelper();
    var lookupprovider = evt.getLookupProvider();
    // var helper = new ExistingFileHelper(Collections.emptyList(), Collections.emptySet(), false, null, null);
    // generator.addProvider(true,new LangMerger(generator, Constants.MODID, "Creatania", LangPartials.values()));

    if (evt.includeServer()) {
      serverDataGen(generator, helper, lookupprovider);
    }

  }
  public static void serverDataGen(DataGenerator generator, ExistingFileHelper helper , CompletableFuture<HolderLookup.Provider> lookupprovider) {
    var blockTagProvider = new ForgeBlockTagProvider(generator, lookupprovider,helper,MODID);

    generator.addProvider(true,blockTagProvider);
    generator.addProvider(true,new ForgeItemTagProvider(generator, blockTagProvider, lookupprovider, helper, MODID));
    generator.addProvider(true,new ForgeRecipeProvider(generator));
    generator.addProvider(true,new ForgeBlockLootProvider(generator));
    generator.addProvider(true,new ForgeFluidTagProvider(generator, helper, MODID));
    generator.addProvider(true,new ForgeBlockstatesProvider(generator, helper));
    generator.addProvider(true,new ForgeSequencedAssemblyRecipeProvider(generator));
    generator.addProvider(true,new ForgeAdvancementsProvider(generator));
//
    ForgeCreateProcessingRecipeProvider.registerAll(generator);
    generator.addProvider(true,new ElvenTradeRecipeGen(generator));
    generator.addProvider(true,new ManaInfusionRecipeGen(generator));
    generator.addProvider(true,new RuneAltarRecipeGen(generator));
    generator.addProvider(true,new TerraPlateRecipeGen(generator));
    generator.addProvider(true,new PureDaisyRecipeGen(generator));
  }
}