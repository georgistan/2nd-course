package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ShortestPathFinder {
    private static final int DIRECTIONS_COUNT = 4;
    private static final int PATH_SIZE_OVERFLOW = 1;
    private static final int[] DX = {-1, 1, 0, 0};
    private static final int[] DY = {0, 0, -1, 1};

    public static int findShortestPathLength(MapEntity[][] mapLayout, Location start, Location end) {
        boolean[][] visited = new boolean[mapLayout.length][mapLayout[0].length];

        Queue<Location> queue = new LinkedList<>();
        Map<Location, Location> parent = new HashMap<>();

        queue.add(start);
        visited[start.x()][start.y()] = true;

        while (!queue.isEmpty()) {
            Location current = queue.poll();

            if (current.equals(end)) {
                return reconstructPath(parent, end).size() - PATH_SIZE_OVERFLOW;
            }

            for (int i = 0; i < DIRECTIONS_COUNT; i++) {
                Location newLocation = new Location(current.x() + DX[i], current.y() + DY[i]);

                if (LocationValidator.isValidLocation(mapLayout, visited, newLocation)) {
                    queue.add(newLocation);
                    visited[newLocation.x()][newLocation.y()] = true;
                    parent.put(newLocation, current);
                }
            }
        }

        return 0;
    }

    private static List<Location> reconstructPath(Map<Location, Location> parent, Location end) {
        List<Location> path = new LinkedList<>();

        for (Location at = end; at != null; at = parent.get(at)) {
            path.addFirst(at);
        }

        return path;
    }
}