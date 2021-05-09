package net.azagwen.atbyw.world.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.azagwen.atbyw.block.AtbywBlocks;
import net.azagwen.atbyw.block.RedstoneLanternBlock;
import net.azagwen.atbyw.world.AtbywWorldGen;
import net.azagwen.atbyw.world.StructurePoolHelper;
import net.minecraft.block.Blocks;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.Identifier;

import static net.azagwen.atbyw.main.AtbywMain.AtbywID;

public class SavanaMineshaftData {
    public static final StructurePool BASE_POOL;
    private static final String rootFile = "savana_mineshaft";

    public static void init() {
    }

    public static final StructureProcessorList DEGRADE_DIRT = AtbywWorldGen.registerStructProcessor(
            AtbywID("degrade_dirt"),
            ImmutableList.of(
                    new RuleStructureProcessor(
                            ImmutableList.of(
                                    new StructureProcessorRule(
                                            new RandomBlockMatchRuleTest(Blocks.DIRT, 0.25F),
                                            AlwaysTrueRuleTest.INSTANCE,
                                            Blocks.GRAVEL.getDefaultState()
                                    ),
                                    new StructureProcessorRule(
                                            new RandomBlockMatchRuleTest(Blocks.DIRT, 0.5F),
                                            AlwaysTrueRuleTest.INSTANCE,
                                            Blocks.COARSE_DIRT.getDefaultState()
                                    ),
                                    new StructureProcessorRule(
                                            new RandomBlockMatchRuleTest(Blocks.DIRT, 0.125F),
                                            AlwaysTrueRuleTest.INSTANCE,
                                            AtbywBlocks.DIRT_SLAB.getDefaultState()
                                    )
                            )
                    )
            )
    );

    public static final StructureProcessorList DEGRADE_DIRT_NO_SLABS = AtbywWorldGen.registerStructProcessor(
            AtbywID("degrade_dirt_no_slabs"),
            ImmutableList.of(
                    new RuleStructureProcessor(
                            ImmutableList.of(
                                    new StructureProcessorRule(
                                            new RandomBlockMatchRuleTest(Blocks.DIRT, 0.25F),
                                            AlwaysTrueRuleTest.INSTANCE,
                                            Blocks.GRAVEL.getDefaultState()
                                    ),
                                    new StructureProcessorRule(
                                            new RandomBlockMatchRuleTest(Blocks.DIRT, 0.5F),
                                            AlwaysTrueRuleTest.INSTANCE,
                                            Blocks.COARSE_DIRT.getDefaultState()
                                    )
                            )
                    )
            )
    );


    public static final StructureProcessorList SHUFFLE_FLOOR_LANTERNS = AtbywWorldGen.registerStructProcessor(
            AtbywID("shuffle_floor_lanterns"),
            ImmutableList.of(
                    new RuleStructureProcessor(
                            ImmutableList.of(
                                    new StructureProcessorRule(
                                            new RandomBlockMatchRuleTest(AtbywBlocks.REDSTONE_LANTERN, 0.5F),
                                            AlwaysTrueRuleTest.INSTANCE,
                                            AtbywBlocks.REDSTONE_LANTERN.getDefaultState().with(RedstoneLanternBlock.POWER_INTENSITY, 10)
                                    ),
                                    new StructureProcessorRule(
                                            new RandomBlockMatchRuleTest(AtbywBlocks.REDSTONE_LANTERN, 0.5F),
                                            AlwaysTrueRuleTest.INSTANCE,
                                            AtbywBlocks.REDSTONE_LANTERN.getDefaultState().with(RedstoneLanternBlock.POWER_INTENSITY, 5)
                                    )
                            )
                    )
            )
    );

    static {
        BASE_POOL = StructurePools.register(new StructurePool(
                AtbywID(rootFile + "/shaft_entrance"),
                new Identifier("empty"),
                ImmutableList.of(
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/entrance/top")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/entrance/top_alt")), 1)
                ),
                StructurePool.Projection.RIGID
        ));
        StructurePools.register(new StructurePool(
                AtbywID(rootFile + "/entrance/sections"),
                AtbywID(rootFile + "/entrance/sections"),
                ImmutableList.of(
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/entrance/sections/section_1")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/entrance/sections/section_2")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/entrance/sections/section_3")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/entrance/sections/section_4")), 2)
                ),
                StructurePool.Projection.RIGID
        ));
        StructurePools.register(new StructurePool(
                AtbywID(rootFile + "/entrance/junctions"),
                AtbywID(rootFile + "/entrance/junctions"),
                ImmutableList.of(
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/entrance/junctions/junction_1"), DEGRADE_DIRT), 2),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/entrance/junctions/junction_2"), DEGRADE_DIRT), 3),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/entrance/junctions/junction_3"), DEGRADE_DIRT), 3),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/entrance/junctions/junction_4"), DEGRADE_DIRT), 3)
                ),
                StructurePool.Projection.RIGID
        ));
        StructurePools.register(new StructurePool(
                AtbywID(rootFile + "/tunnels"),
                AtbywID(rootFile + "/terminators"),
                ImmutableList.of(
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/tunnels/tunnel_1"), DEGRADE_DIRT), 3),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/tunnels/tunnel_2"), DEGRADE_DIRT), 7),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/tunnels/tunnel_l"), DEGRADE_DIRT), 8),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/tunnels/tunnel_t"), DEGRADE_DIRT), 5),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/tunnels/tunnel_x"), DEGRADE_DIRT), 4)
                        ),
                StructurePool.Projection.RIGID
        ));
        StructurePools.register(new StructurePool(
                AtbywID(rootFile + "/terminators"),
                new Identifier("empty"),
                ImmutableList.of(
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/terminators/terminator_1"), DEGRADE_DIRT_NO_SLABS), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/terminators/terminator_2"), DEGRADE_DIRT_NO_SLABS), 1)
                ),
                StructurePool.Projection.RIGID
        ));
        StructurePools.register(new StructurePool(
                AtbywID(rootFile + "/tunnels/rooms"),
                AtbywID(rootFile + "/tunnels/rooms"),
                ImmutableList.of(
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/tunnels/rooms/room_1"), DEGRADE_DIRT), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/tunnels/rooms/room_2"), DEGRADE_DIRT), 1)
                ),
                StructurePool.Projection.RIGID
        ));
        StructurePools.register(new StructurePool(
                AtbywID(rootFile + "/lighting"),
                new Identifier("empty"),
                ImmutableList.of(
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lighting/lamp_a5")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lighting/lamp_a4")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lighting/lamp_a3")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lighting/lamp_a2")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lighting/lamp_b5")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lighting/lamp_b4")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lighting/lamp_b3")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lighting/lamp_b2")), 1)
                ),
                StructurePool.Projection.RIGID
        ));

        StructurePools.register(new StructurePool(
                AtbywID(rootFile + "/lighting_short"),
                new Identifier("empty"),
                ImmutableList.of(
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lighting/lamp_a2")), 3),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lighting/lamp_b2")), 3),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lanterns/ceiling/lantern_1")), 2),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lanterns/ceiling/lantern_2")), 2),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lanterns/ceiling/lantern_3")), 2)
                ),
                StructurePool.Projection.RIGID
        ));
        StructurePools.register(new StructurePool(
                AtbywID(rootFile + "/lanterns/ceiling"),
                new Identifier("empty"),
                ImmutableList.of(
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lanterns/ceiling/lantern_1")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lanterns/ceiling/lantern_2")), 1),
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lanterns/ceiling/lantern_3")), 1)
                ),
                StructurePool.Projection.RIGID
        ));
        StructurePools.register(new StructurePool(
                AtbywID(rootFile + "/lanterns/floor"),
                new Identifier("empty"),
                ImmutableList.of(
                        Pair.of(StructurePoolHelper.addSinglePoolElement(AtbywID(rootFile + "/lanterns/floor"), SHUFFLE_FLOOR_LANTERNS), 1)
                ),
                StructurePool.Projection.RIGID
        ));
    }
}
