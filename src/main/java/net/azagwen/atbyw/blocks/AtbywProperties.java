package net.azagwen.atbyw.blocks;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.Direction;

public class AtbywProperties {
    public static final BooleanProperty SHEARED = BooleanProperty.of("sheared");
    public static final DirectionProperty VERTICAL_FACING;
    public static final DirectionProperty HORIZONTAL_FACING;
    public static final IntProperty APPEARANCE;
    public static final IntProperty TIMER;

    static {
        VERTICAL_FACING = DirectionProperty.of("vertical_facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);
        HORIZONTAL_FACING = DirectionProperty.of("horizontal_facing", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);
        APPEARANCE = IntProperty.of("appearance", 1, 3);
        TIMER = IntProperty.of("timer", 0, 6);
    }
}
