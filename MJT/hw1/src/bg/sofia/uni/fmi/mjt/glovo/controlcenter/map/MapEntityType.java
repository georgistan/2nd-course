package bg.sofia.uni.fmi.mjt.glovo.controlcenter.map;

public enum MapEntityType {
    ROAD('.'),
    WALL('#'),
    RESTAURANT('R'),
    CLIENT('C'),
    DELIVERY_GUY_CAR('A'),
    DELIVERY_GUY_BIKE('B');

    private final char symbol;

    private MapEntityType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static MapEntityType fromSymbol(char symbol) {
        return switch (symbol) {
            case '.' -> MapEntityType.ROAD;
            case '#' -> MapEntityType.WALL;
            case 'R'-> MapEntityType.RESTAURANT;
            case 'C' -> MapEntityType.CLIENT;
            case 'A' -> MapEntityType.DELIVERY_GUY_CAR;
            case 'B' -> MapEntityType.DELIVERY_GUY_BIKE;
            default -> throw new IllegalArgumentException("Symbol cannot be recognised as enum value.");
        };
    }
}