package zaftnotameni.creatania.mana.flowers.blazunia;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import zaftnotameni.creatania.registry.CreataniaBlockEntities;

public class BlazuniaFunctionalFlowerBlock extends Block implements IBE<BlazuniaFunctionalFlowerBlockEntity> {
  public BlazuniaFunctionalFlowerBlock(Properties pProperties) {
    super(pProperties);
  }
  @Override
  public Class<BlazuniaFunctionalFlowerBlockEntity> getBlockEntityClass() {
    return BlazuniaFunctionalFlowerBlockEntity.class;
  }
  @Override
  public BlockEntityType<? extends BlazuniaFunctionalFlowerBlockEntity> getBlockEntityType() {
    return CreataniaBlockEntities.BLAZUNIA_BLOCK_ENTITY.get();
  }
}