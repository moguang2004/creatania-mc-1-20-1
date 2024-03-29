package zaftnotameni.creatania.machines.managenerator;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
//import com.simibubi.create.foundation.blockEntity.BlockEntityBehaviour;
//import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
//import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import zaftnotameni.creatania.config.CommonConfig;

import java.util.List;

import static zaftnotameni.creatania.registry.CreataniaFluids.ALL;
public class ManaGeneratorFluidHandler {
  public boolean contentsChanged;
  public SmartFluidTankBehaviour inputTankBehavior;
  public LazyOptional<IFluidHandler> fluidCapability;
  private ManaGeneratorBlockEntity manaGenerator;

  public ManaGeneratorFluidHandler(ManaGeneratorBlockEntity manaGenerator) {
    this.manaGenerator = manaGenerator;
    contentsChanged = true;
  }

  public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    this.inputTankBehavior = new SmartFluidTankBehaviour(
      SmartFluidTankBehaviour.INPUT,
      this.manaGenerator,
      1,
      CommonConfig.MANA_GENERATOR_MAX_MANA_FLUID_STORAGE.get(),
      false)
      .whenFluidUpdates(() -> this.contentsChanged = true);

    behaviours.add(inputTankBehavior);

    this.fluidCapability = LazyOptional.of(() -> {
      LazyOptional<? extends IFluidHandler> inputCap = inputTankBehavior.getCapability();
      return new CombinedTankWrapper(inputCap.orElse(null));
    });
  }

  public SmartFluidTankBehaviour.TankSegment getPrimaryTank() {
    if (this.inputTankBehavior == null || this.inputTankBehavior.getPrimaryTank() == null) return null;
    return this.inputTankBehavior.getPrimaryTank();
  }

  public int drainManaFluidFromTank(int amount) {
    var fluidCapability = this.inputTankBehavior.getCapability().orElse(null);
    if (fluidCapability == null) return 0;
    var manaFluidToBeConsumed = new FluidStack(ALL.PURE_MANA.get(), amount);
    var manaFluidDrained = fluidCapability.drain(manaFluidToBeConsumed, IFluidHandler.FluidAction.EXECUTE);
    this.getPrimaryTank().onFluidStackChanged();
    return manaFluidDrained.getAmount();
  }

  public float getManaFluidAvailable(){
    var primaryTank = this.getPrimaryTank();
    if (primaryTank == null) return 0f;
    return primaryTank.getFluidLevel().getValue() * 1000;
  }

  public float getManaTankCapacity(){
    return CommonConfig.MANA_GENERATOR_MAX_MANA_FLUID_STORAGE.get();
  }

  public void read(CompoundTag compound, boolean clientPacket) {

  }

  public void write(CompoundTag compound, boolean clientPacket) {

  }
  public void invalidate() {
    this.fluidCapability.invalidate();
  }
  public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    if (cap == ForgeCapabilities.FLUID_HANDLER)
      return fluidCapability.cast();
    return null;
  }
  public boolean serverTick() {
    if (!this.contentsChanged) return false;
    this.contentsChanged = false;
    return true;
  }
}
