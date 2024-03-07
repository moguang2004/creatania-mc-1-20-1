package zaftnotameni.creatania.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import zaftnotameni.creatania.Constants;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static zaftnotameni.creatania.Constants.MODID;
import static zaftnotameni.creatania.registry.CreataniaIndex.CREATANIA_INDEX;
import static zaftnotameni.creatania.registry.CreataniaItems.INDEX;

public class CreativeModeTabs {
    //public static  final CreativeModeTab.Builder builder = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP,9){
    //};

  //public static final CreativeModeTab CREATANIA_ITEMS = builder.icon(() -> new ItemStack(CreataniaBlocks.REAL_MANA_BLOCK.get()))
    //      .title(Component.literal("Creatania")).build()
   // @Override
    //public ItemStack getDisplayItems() { return new ItemStack(CreataniaBlocks.REAL_MANA_BLOCK.get()); }
    //@Override
    //public Component getDisplayName() { return Component.literal("Creatania"); }
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<CreativeModeTab> Creatania = REGISTRY.register("creatania", () -> CreativeModeTab.builder()
                    .title(Component.literal("Creatania"))
                    .icon(() -> new ItemStack(CreataniaBlocks.REAL_MANA_BLOCK.get()))
                    .displayItems(new RegistrateDisplayItemsGenerator(CreativeModeTabs.Creatania))

                    .build());
    private static class RegistrateDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator{

        @Override
        public void accept(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output output) {
            List<Item> items = new LinkedList<>();
            items.addAll(collectBlocks());

            items.addAll(collectItems(tabFilter, (item) -> exclude.contains(item)));

            outputAll(output, items);
        }

        private static void outputAll(CreativeModeTab.Output output, List<Item> items) {
            for (Item item : items) {
                output.accept(item);
            }
        }
        private final RegistryObject<CreativeModeTab> tabFilter;

        public RegistrateDisplayItemsGenerator(RegistryObject<CreativeModeTab> tabFilter) {

            this.tabFilter = tabFilter;
        }
    private List<Item> collectBlocks() {
        List<Item> items = new ReferenceArrayList<>();
        for (RegistryEntry<Block> entry : CreataniaRegistrate.forMod(MODID).getAll(Registries.BLOCK)) {
            if (!CreataniaRegistrate.isInCreativeTab(entry, tabFilter))
                continue;
            Item item = entry.get()
                    .asItem();
            if (item == Items.AIR)
                continue;
            items.add(item);
        }
        items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
        return items;
    }

    private List<Item> collectItems(RegistryObject<CreativeModeTab> tab, Predicate<Item> exclusionPredicate) {
        List<Item> items = new ReferenceArrayList<>();


        for (RegistryEntry<Item> entry : CreataniaRegistrate.forMod(MODID).getAll(Registries.ITEM)) {
            if (!CreataniaRegistrate.isInCreativeTab(entry, tab))
                continue;
            Item item = entry.get();
            if (item instanceof BlockItem)
                continue;
            if (!exclusionPredicate.test(item))
                items.add(item);
        }
        return items;
    }
        List<Item> exclude = List.of(

        );
    }
    ;
    //public static NonNullSupplier<CreativeModeTab> creataniaCreativeTab = () -> CreativeModeTabs.CREATANIA_ITEMS;
  //public static CreativeModeTab creataniaCreativeTab = CREATANIA_ITEMS;

  /*public static final DeferredRegister<CreativeModeTab> creataniaCreativeTab = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

  public static RegistryObject<CreativeModeTab> FIRST_TAB = creataniaCreativeTab.register("moditem1",() ->
          CreativeModeTab.builder().icon(() -> new ItemStack(CreataniaBlocks.REAL_MANA_BLOCK.get())).title(Component.literal("Creatania")).build());
  //每个物品栏的注册都将使用一个这一格式的语句，与物品类似。*/
}
