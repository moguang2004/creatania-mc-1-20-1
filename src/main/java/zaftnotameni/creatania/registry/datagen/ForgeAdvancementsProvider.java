package zaftnotameni.creatania.registry.datagen;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import zaftnotameni.creatania.advancements.CreataniaAdvancement;
import zaftnotameni.creatania.registry.CreataniaAdvancements;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ForgeAdvancementsProvider implements DataProvider {
  // Datagen
  private static final Logger LOGGER = LogUtils.getLogger();
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  public final DataGenerator generator;
  public ForgeAdvancementsProvider(DataGenerator generatorIn) {
    this.generator = generatorIn;
  }
  @Override
  public CompletableFuture<?> run(CachedOutput cache) {
    Path path = this.generator.getPackOutput().getOutputFolder();
    Set<ResourceLocation> set = Sets.newHashSet();
    Consumer<Advancement> consumer = (advancement) -> {
      if (!set.add(advancement.getId()))
        throw new IllegalStateException("Duplicate advancement " + advancement.getId());

      Path path1 = getPath(path, advancement);

        DataProvider.saveStable(cache, advancement.deconstruct().serializeToJson(), path1);
    };

    for (CreataniaAdvancement advancement : CreataniaAdvancements.ENTRIES) advancement.save(consumer);
    return null;
  }
  private static Path getPath(Path pathIn, Advancement advancementIn) {
    return pathIn.resolve(
      "data/" + advancementIn.getId().getNamespace() + "/advancements/"
        + advancementIn.getId().getPath() + ".json");
  }
  @Override
  public String getName() {
    return "Creatania's Advancements";
  }
  public static JsonObject provideLangEntries() {
    JsonObject object = new JsonObject();
    for (CreataniaAdvancement advancement : CreataniaAdvancements.ENTRIES) advancement.appendToLang(object);
    return object;
  }
}

