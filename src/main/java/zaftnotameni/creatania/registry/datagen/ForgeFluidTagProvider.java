package zaftnotameni.creatania.registry.datagen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import zaftnotameni.creatania.Constants;
public class ForgeFluidTagProvider extends FluidTagsProvider {
  public ForgeFluidTagProvider(DataGenerator generator, ExistingFileHelper helper, String modId) {
    super(generator.getPackOutput(), null,modId,helper);
  }

  @Override
  protected void addTags(HolderLookup.Provider var1) {
  }
}

