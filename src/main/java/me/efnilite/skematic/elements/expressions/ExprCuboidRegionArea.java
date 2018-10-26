package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Bukkit;

@Name("Selection area")
@Description("Get the region area of a player's selection.")
@Examples("set {area} to selection area of player")
@Since("1.0.0")
public class ExprCuboidRegionArea extends SimplePropertyExpression<CuboidRegion, Number> {

    static {
        register(ExprCuboidRegionArea.class, Number.class, "area[s]", "cuboidregions");
    }

    @Override
    public Number convert(final CuboidRegion r) {

        if (r == null) {
            return null;
        }

        return r.getArea();
    }

    @Override
    protected String getPropertyName() {
        return "region area";
    }

    @Override
    public Class<Number> getReturnType() {
        return Number.class;
    }

}