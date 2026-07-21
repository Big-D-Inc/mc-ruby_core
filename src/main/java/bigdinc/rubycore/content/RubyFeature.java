package bigdinc.rubycore.content;

import bigdinc.rubycore.RubyCore;

import java.util.List;
import java.util.EnumMap;
import java.util.function.Supplier;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.TierSortingRegistry;
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
        RubyCore.SOUND_EVENTS.register(
        "block.ruby_block.break",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(RubyCore.MODID, "block.ruby_block.break"))
        );
    public static final DeferredHolder<SoundEvent, SoundEvent> RUBY_BLOCK_STEP =
        RubyCore.SOUND_EVENTS.register(
        "block.ruby_block.step",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(RubyCore.MODID, "block.ruby_block.step"))
        );
    public static final DeferredHolder<SoundEvent, SoundEvent> RUBY_BLOCK_PLACE =
        RubyCore.SOUND_EVENTS.register(
        "block.ruby_block.place",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(RubyCore.MODID, "block.ruby_block.place"))
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
        RubyCore.BLOCKS.registerSimpleBlock(
        "ruby_block",
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_RED)
            .strength(5.0f)
            .requiresCorrectToolForDrops()
            .sound(RUBY_BLOCK_SOUNDS)
    );
    //endregion
    
    //region 2 - Ruby Ore Block ...
    public static final DeferredBlock<DropExperienceBlock> RUBY_ORE = RubyCore.BLOCKS.register("ruby_ore",
        () -> new DropExperienceBlock(
            UniformInt.of(3, 7), // zakres XP: min 3, max 7
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_RED)
                .strength(3.0f, 3.0f)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE))
    );
    
    //endregion
    
    /***TIER***
     1 - Ruby Tool Tier
     **********/

    //region 1 - Ruby Tool Tier ...
    public static final TagKey<Block> NEEDS_RUBY_TOOL =
        TagKey.create(Registries.BLOCK, new ResourceLocation(RubyCore.MODID, "needs_ruby_tool"));
    public static final Tier RUBY_TIER = new SimpleTier(
        3,
        800,
        7.0f,
        2.5f,
        12,
        NEEDS_RUBY_TOOL,
        () -> Ingredient.of(RubyFeature.RUBY.get())
    );
    
    static {
        TierSortingRegistry.registerTier(
            RUBY_TIER,
            new ResourceLocation(RubyCore.MODID, "ruby"),
            List.of(Tiers.IRON),
            List.of(Tiers.DIAMOND)
        );
    }
    //endregion
    
    /***ITEMS***
     1 - Ruby Item
     2 - Blocks Items
     3 - Ruby Tools
    ***********/
    
    public static final DeferredItem<Item> RUBY =
        RubyCore.ITEMS.register(
            "ruby",
            () -> new Item(new Item.Properties())
    );
    
    //region 2 - Block Items ...
    public static final DeferredItem<BlockItem> RUBY_BLOCK_ITEM =
        RubyCore.ITEMS.registerSimpleBlockItem(
            "ruby_block",
            RUBY_BLOCK
    );
    
    public static final DeferredItem<BlockItem> RUBY_ORE_ITEM =
        RubyCore.ITEMS.registerSimpleBlockItem(
        "ruby_ore",
            RUBY_ORE
    );
    //endregion
    
    //region 3 - Ruby tools ...
    public static final DeferredItem<SwordItem> RUBY_SWORD =
        RubyCore.ITEMS.register("ruby_sword",
            () -> new SwordItem(RUBY_TIER, 3, -2.4f, new Item.Properties())
    );
    
    public static final DeferredItem<PickaxeItem> RUBY_PICKAXE =
        RubyCore.ITEMS.register("ruby_pickaxe",
            () -> new PickaxeItem(RUBY_TIER, 1, -2.8f, new Item.Properties())
    );
    
    public static final DeferredItem<AxeItem> RUBY_AXE =
        RubyCore.ITEMS.register("ruby_axe",
            () -> new AxeItem(RUBY_TIER, 5.0f, -3.0f, new Item.Properties())
    );
    
    public static final DeferredItem<ShovelItem> RUBY_SHOVEL =
        RubyCore.ITEMS.register("ruby_shovel",
            () -> new ShovelItem(RUBY_TIER, 1.5f, -3.0f, new Item.Properties())
    );
    
    public static final DeferredItem<HoeItem> RUBY_HOE =
        RubyCore.ITEMS.register("ruby_hoe",
            () -> new HoeItem(RUBY_TIER, 0, -3.0f, new Item.Properties())
    );
    //endregion
    
    /***ARMOR***
     1 - Ruby Armor Material
     2 - Ruby Armor
     3 - Ruby Horse Armor
     **********/

    //region 1 - Ruby Armor Material ...
    public enum RubyArmorMaterial implements ArmorMaterial {
        RUBY(
            "ruby_armor",
            37,
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.CHESTPLATE, 8);
                map.put(ArmorItem.Type.HELMET, 3);
            }),
            12,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            2.5f,
            0.0f,
            () -> Ingredient.of(RubyFeature.RUBY.get())
        );
        
        private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY =
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 13);
                map.put(ArmorItem.Type.LEGGINGS, 15);
                map.put(ArmorItem.Type.CHESTPLATE, 16);
                map.put(ArmorItem.Type.HELMET, 11);
            }
        );
        
        private final String name;
        private final int durabilityMultiplier;
        private final EnumMap<ArmorItem.Type, Integer> defenseValues;
        private final int enchantmentValue;
        private final SoundEvent equipSound;
        private final float toughness;
        private final float knockbackResistance;
        private final Supplier<Ingredient> repairIngredient;
        
        RubyArmorMaterial
        (String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> defenseValues,
        int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance,
        Supplier<Ingredient> repairIngredient) {
            this.name = name;
            this.durabilityMultiplier = durabilityMultiplier;
            this.defenseValues = defenseValues;
            this.enchantmentValue = enchantmentValue;
            this.equipSound = equipSound;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
            this.repairIngredient = repairIngredient;
        }
        
        @Override public String getName() {
            return RubyCore.MODID + ":" + name;
        }
        @Override public int getDurabilityForType(ArmorItem.Type type) {
            return BASE_DURABILITY.get(type) * durabilityMultiplier;
        }
        @Override public int getDefenseForType(ArmorItem.Type type) {
            return defenseValues.get(type);
        }
        @Override public int getEnchantmentValue() {
            return enchantmentValue;
        }
        @Override public SoundEvent getEquipSound() {
            return equipSound;
        }
        @Override public Ingredient getRepairIngredient() {
            return repairIngredient.get();
        }
        @Override public float getToughness() {
            return toughness;
        }
        @Override public float getKnockbackResistance() {
            return knockbackResistance;
        }
    }
    //endregion
    
    //region 2 - Ruby Armor ...
    public static final DeferredItem<ArmorItem> RUBY_HELMET = RubyCore.ITEMS.register(
        "ruby_helmet",
        () -> new ArmorItem(RubyArmorMaterial.RUBY, ArmorItem.Type.HELMET, new Item.Properties())
    );
    public static final DeferredItem<ArmorItem> RUBY_CHESTPLATE = RubyCore.ITEMS.register(
        "ruby_chestplate",
        () -> new ArmorItem(RubyArmorMaterial.RUBY, ArmorItem.Type.CHESTPLATE, new Item.Properties() )
    );
    public static final DeferredItem<ArmorItem> RUBY_LEGGINGS = RubyCore.ITEMS.register(
        "ruby_leggings",
        () -> new ArmorItem(RubyArmorMaterial.RUBY, ArmorItem.Type.LEGGINGS, new Item.Properties())
    );
    public static final DeferredItem<ArmorItem> RUBY_BOOTS = RubyCore.ITEMS.register(
        "ruby_boots",
        () -> new ArmorItem(RubyArmorMaterial.RUBY, ArmorItem.Type.BOOTS, new Item.Properties())
    );
    //endregion
    
    //region 3 - Ruby Horse Armor
    public static final DeferredItem<HorseArmorItem> RUBY_HORSE_ARMOR =
        RubyCore.ITEMS.register("ruby_horse_armor",
            () -> new HorseArmorItem(
                9,
                new ResourceLocation(RubyCore.MODID, "textures/entity/horse/armor/ruby_horse_armor.png"),
                new Item.Properties().stacksTo(1)
            )
        );
    //endregion
    
    /***METHODS***
     1 - load
     ************/
    
    public static void load() {
        RubyCore.LOGGER.info(">> [" + RubyCore.MODID + "]: Loading Ruby Core");
    }
}
