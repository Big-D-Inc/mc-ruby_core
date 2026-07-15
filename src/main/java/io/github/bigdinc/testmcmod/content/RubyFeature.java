package io.github.bigdinc.testmcmod.content;

import io.github.bigdinc.testmcmod.TestMcMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

public class RubyFeature {
    /****SOUNDS***
     1 - Ruby Block Sounds
     *************/
    
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
    
    /****BLOCKS***
     1 - Ruby Block
     *************/
    
    public static final DeferredBlock<Block> RUBY_BLOCK =
        TestMcMod.BLOCKS.registerSimpleBlock(
        "ruby_block",
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_RED)
            .strength(5.0f)
            .requiresCorrectToolForDrops()
            .sound(RUBY_BLOCK_SOUNDS)
    );

    /****ITEMS***
     1 - Ruby Block Item
     2 - Ruby Item
    *************/

    public static final DeferredItem<BlockItem> RUBY_BLOCK_ITEM =
        TestMcMod.ITEMS.registerSimpleBlockItem(
        "ruby_block",
        RUBY_BLOCK
    );

    public static final DeferredItem<Item> RUBY =
        TestMcMod.ITEMS.register(
            "ruby",
            ()  -> new Item(
                new Item.Properties()
            )
    );
    
    /****FUNCTIONS***
     1 - load
     *************/
    
    public static void load() {
        TestMcMod.LOGGER.info(">> Loaded ruby feature");
    }
}
