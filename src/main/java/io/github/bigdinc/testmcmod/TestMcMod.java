package io.github.bigdinc.testmcmod;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TestMcMod.MODID)
public class TestMcMod {
    public static final String MODID = "testmcmod"; //mod id for the registers
    public static final Logger LOGGER = LogUtils.getLogger(); // Directly reference the logger

    // default registers
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // creating our ruby block
    public static final DeferredHolder<SoundEvent, SoundEvent> RUBY_BLOCK_BREAK = SOUND_EVENTS.register("block.ruby_block.break",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "block.ruby_block.break")));

    public static final DeferredHolder<SoundEvent, SoundEvent> RUBY_BLOCK_STEP = SOUND_EVENTS.register("block.ruby_block.step",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "block.ruby_block.step")));

    public static final DeferredHolder<SoundEvent, SoundEvent> RUBY_BLOCK_PLACE = SOUND_EVENTS.register("block.ruby_block.place",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "block.ruby_block.place")));

    public static final SoundType RUBY_BLOCK_SOUNDS = new DeferredSoundType(
            1.0F, // volume
            1.0F, // pitch
            RUBY_BLOCK_BREAK,
            RUBY_BLOCK_STEP,
            RUBY_BLOCK_PLACE,
            RUBY_BLOCK_BREAK,
            RUBY_BLOCK_STEP
    );
    public static final DeferredBlock<Block> RUBY_BLOCK =
        BLOCKS.registerSimpleBlock(
            "ruby_block",
            BlockBehaviour.Properties.of()
                 .mapColor(MapColor.COLOR_RED)
                .strength(5.0f)
                .requiresCorrectToolForDrops()
                .sound(RUBY_BLOCK_SOUNDS)
    );
    public static final DeferredItem<BlockItem> RUBY_BLOCK_ITEM =
        ITEMS.registerSimpleBlockItem(
            "ruby_block",
            RUBY_BLOCK
    );

    // Creates a new food item with the id "testmcmod:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(2f).build())
    );

    // Creates a creative tab for our mod id
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.testmcmod")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                // items we are adding to out tab
                output.accept(EXAMPLE_ITEM.get());
                output.accept(RUBY_BLOCK_ITEM.get());
            }).build());


    public TestMcMod(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);

        SOUND_EVENTS.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (TestMcMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(RUBY_BLOCK_ITEM.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = TestMcMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
