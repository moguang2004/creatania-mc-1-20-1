package zaftnotameni.creatania.registry;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CreataniaRegistrate extends CreateRegistrate {
  public CreataniaRegistrate(String modid) {
    super(modid);
  }
  public static CreataniaRegistrate forMod(String id) { return new CreataniaRegistrate(id); }
  @Override
  public CreateRegistrate registerEventListeners(IEventBus bus) { return super.registerEventListeners(bus); }

  @Override
  public @Nullable CreataniaRegistrate setCreativeTab(RegistryObject<CreativeModeTab> tab) {
    this.currentTab = tab;
    return (CreataniaRegistrate)this.self();
  }

  public static <I extends BlockItem> NonNullBiConsumer<DataGenContext<Item, I>, RegistrateItemModelProvider> sameAsBlockItemModel(
    String... folders) {
    return (c, p) -> {
      String path = "block";
      for (String string : folders) path += "/" + ("_".equals(string) ? c.getName() : string);
      p.withExistingParent("item/" + c.getName(), p.modLoc(path));
    };
  }
//  public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> standardFluid(String name) {
//    return fluid(name, Index.resource("fluid/" + name + "_still"), Index.resource("fluid/" + name + "_flow"));
//  }
//
//  public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> waterLikeFluid(String name) {
//    return fluid(name,
//      Fluids.WATER_STILL_RL,
//      Fluids.WATER_FLOWING_RL,
//      Fluids.CreataniaFlowingFluidFlowing::new);
//  }
//  public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> lavaLikeFluid(String name) {
//    return fluid(name,
//      Fluids.LAVA_STILL_RL,
//      Fluids.LAVA_FLOWING_RL,
//      Fluids.CreataniaFlowingFluidFlowing::new);
//
//  }
//  public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> texturedFluid(String name) {
//    return fluid(name,
//      Index.resource("fluid/" + name + "_still"),
//      Index.resource("fluid/" + name + "_flow"),
//    Fluids.CreataniaFlowingFluidFlowing::new);
//

  public CreataniaRegistrate creativeModeTabFor(CreativeModeTab creataniaCreativeTab) {
    super.defaultCreativeTab(creataniaCreativeTab);
    return this;
  }
}
