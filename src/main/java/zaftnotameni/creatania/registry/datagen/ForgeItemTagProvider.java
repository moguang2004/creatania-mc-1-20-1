package zaftnotameni.creatania.registry.datagen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
//import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import zaftnotameni.creatania.Constants;

import java.util.concurrent.CompletableFuture;

public class ForgeItemTagProvider extends ItemTagsProvider {
  public ForgeItemTagProvider(DataGenerator generator, ForgeBlockTagProvider blockTags,CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper , String modId) {
    super(generator.getPackOutput(), lookupProvider,blockTags.contentsGetter() ,modId,helper);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {

  }
}

