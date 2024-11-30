package bg.sofia.uni.fmi.mjt.glovo.mapper;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;

public class MapLayoutMapper {
    public static MapEntity[][] toMapEntityLayout(char[][] charLayout) {
        MapEntity[][] mapEntityLayout = new MapEntity[charLayout.length][charLayout[0].length];

        for (int i = 0; i < charLayout.length; i++) {
            for (int j = 0; j < charLayout[i].length; j++) {

                mapEntityLayout[i][j] = new MapEntity(
                    new Location(i, j),
                    MapEntityType.fromSymbol(charLayout[i][j])
                );

            }
        }

        return mapEntityLayout;
    }

    public static char[][] toCharLayout(MapEntity[][] mapEntityLayout) {
        char[][] charLayout = new char[mapEntityLayout.length][];

        for (int i = 0; i < mapEntityLayout.length; i++) {
            for (int j = 0; j < mapEntityLayout[i].length; j++) {

                charLayout[i][j] = mapEntityLayout[i][j].type().getSymbol();

            }
        }

        return charLayout;
    }
}
