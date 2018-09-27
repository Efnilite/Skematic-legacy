package me.efnilite.skematic.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.efnilite.skematic.Skematic;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EffDownloadFile extends Effect {

    private Expression<String> url;
    private Expression<String> path;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        url = (Expression<String>) exprs[0];
        path = (Expression<String>) exprs[1];
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "download the file from the url " + url.toString(event, debug) + " to path " + path.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        Skematic.getInstance().getServer().getScheduler().runTaskAsynchronously(Skematic.getInstance(), () -> {
            Path paths = Paths.get(path.toString());
            try {
                Files.copy(new URL(url.toString()).openStream(), paths);
            } catch (FileAlreadyExistsException exception) {
                Utilities.error("File '" + paths + "' already exists!", exception, false);
            } catch (Exception exception) {
                Utilities.error("Could not download file '" + url + "'", exception, false);
            }
        });
    }

}
