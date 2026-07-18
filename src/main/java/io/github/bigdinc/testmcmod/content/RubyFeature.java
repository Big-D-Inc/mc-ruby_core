package io.github.bigdinc.testmcmod.content;

import io.github.bigdinc.testmcmod.TestMcMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

public class RubyFeature {
    /***SOUNDS***
     1 - Ruby Block Sounds
     ************/
    
    //region 1 - Ruby Block Sounds ...
    public static final DeferredHolder<SoundEvent, SoundEvent> RUBY_BLOCK_BREAK =
        TestMcMod.SOUND_EVENTS.register(
        "block.ruby_block.break",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TestMcMod.MODID, "block.ruby_block.break"))
        );
    public static final DeferredHolder<SoundEvent, SoundEvent> RUBY_BLOCK_STEP =
        TestMcMod.SOUND_EVENTS.register(
        "block.ruby_block.step",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TestMcMod.MODID, "block.ruby_block.step"))
        );
    public static final DeferredHolder<SoundEvent, SoundEvent> RUBY_BLOCK_PLACE =
        TestMcMod.SOUND_EVENTS.register(
        "block.ruby_block.place",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TestMcMod.MODID, "block.ruby_block.place"))
    );
    public static final SoundType RUBY_BLOCK_SOUNDS = new DeferredSoundType(
        1.0F,
        1.0F,
        RUBY_BLOCK_BREAK,
        RUBY_BLOCK_STEP,
        RUBY_BLOCK_PLACE,
        RUBY_BLOCK_BREAK,
        RUBY_BLOCK_STEP
    );
    //endregion
    
    /***BLOCKS***
     1 - Ruby Block
     2 - Ruby Ore
     ***********/
    
    //region 1 - Ruby block ...
    public static final DeferredBlock<Block> RUBY_BLOCK =
        TestMcMod.BLOCKS.registerSimpleBlock(
        "ruby_block",
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_RED)
            .strength(5.0f)
            .requiresCorrectToolForDrops()
            .sound(RUBY_BLOCK_SOUNDS)
    );
    //endregion
    
        //region 2 - Ruby Ore Block ...
    public static final DeferredBlock<DropExperienceBlock> RUBY_ORE = TestMcMod.BLOCKS.register("ruby_ore",
        () -> new DropExperienceBlock(
            UniformInt.of(3, 7), // zakres XP: min 3, max 7
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_RED)
                .strength(3.0f, 3.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE))
    );
    
    //endregion
    
    /***ITEMS***
     1 - Ruby Block Item
     2 - Ruby Item
     3 - Ruby Ore Block Item
    ***********/

    public static final DeferredItem<BlockItem> RUBY_BLOCK_ITEM =
        TestMcMod.ITEMS.registerSimpleBlockItem(
        "ruby_block",
            RUBY_BLOCK
        );

    public static final DeferredItem<Item> RUBY =
        TestMcMod.ITEMS.register(
            "ruby",
            () -> new Item(new Item.Properties())
        );
    
    public static final DeferredItem<BlockItem> RUBY_ORE_ITEM =
        TestMcMod.ITEMS.registerSimpleBlockItem(
        "ruby_ore",
            RUBY_ORE
        );
    
    /***METHODS***
     1 - load
     ************/
    
    public static void load() {
        TestMcMod.LOGGER.info(">> Loaded ruby feature");
    }
}
