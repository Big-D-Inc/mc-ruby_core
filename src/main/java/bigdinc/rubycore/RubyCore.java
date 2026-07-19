package bigdinc.rubycore;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import bigdinc.rubycore.content.RubyFeature;

@Mod(RubyCore.MODID)
public class RubyCore {
    @SuppressWarnings("SpellCheckingInspection")
    public static final String MODID = "rubycore";
    public static final Logger LOGGER = LogUtils.getLogger();
    
    //region MC Registers ...
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    //endregion
    
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB =
        CREATIVE_MODE_TABS.register(
        "rubycore",
            () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.rubycore.rubycore"))
                .icon(() -> RubyFeature.RUBY.get().getDefaultInstance())
                .displayItems((params, output) -> {
                    //blocks
                    output.accept(RubyFeature.RUBY.get());
                    output.accept(RubyFeature.RUBY_BLOCK.get());
                    output.accept(RubyFeature.RUBY_ORE.get());
                    //tools
                    output.accept(RubyFeature.RUBY_SWORD.get());
                    output.accept(RubyFeature.RUBY_PICKAXE.get());
                    output.accept(RubyFeature.RUBY_AXE.get());
                    output.accept(RubyFeature.RUBY_SHOVEL.get());
                    output.accept(RubyFeature.RUBY_HOE.get());
                }).build()
        );

    public RubyCore(IEventBus modEventBus) {
        registerFeatures();
        
        SOUND_EVENTS.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        
        // NeoForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    
    private void registerFeatures() {
        RubyFeature.load();
    }
}
