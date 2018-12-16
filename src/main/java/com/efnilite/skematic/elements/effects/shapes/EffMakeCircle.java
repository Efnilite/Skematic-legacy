package com.efnilite.skematic.elements.effects.shapes;

import ch.njol.skript.Skript;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import org.bukkit.event.Event;

@Patterns("(make|create) [a] [new] [(1¦hollow)] (sphere|circle) at %location% [(with|and)] [the] pattern %string% [(with|and)] radius %integer%")
public class EffMakeCircle extends SkematicEffect {

    static {
        Skript.registerEffect(EffMakeCircle.class, "(make|create) [a] [new] [(1¦hollow)] (sphere|circle) at %location% [(with|and)] [the] pattern %string% [(with|and)] radius %integer%");
    }

    @Override
    protected void execute(Event event) {

    }
}