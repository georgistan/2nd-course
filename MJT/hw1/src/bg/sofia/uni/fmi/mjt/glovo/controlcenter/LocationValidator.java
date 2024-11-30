package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;

public class LocationValidator {
    public static boolean isValidLocation(MapEntity[][] map, boolean[][] visited, Location location) {
        return isInsideLayout(map, location) &&
            map[location.x()][location.y()].type().getSymbol() != '#' &&
            !visited[location.x()][location.y()];
    }

    public static boolean isInsideLayout(MapEntity[][] map, Location location) {
        return location.x() >= 0 && location.x() < map.length &&
            location.y() >= 0 && location.y() < map[0].length;
    }
}
