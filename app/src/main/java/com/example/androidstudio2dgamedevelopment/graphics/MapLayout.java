package com.example.androidstudio2dgamedevelopment.graphics;

class MapLayout {

    public static final int TILE_WIDTH_PIXELS = 64;
    public static final int TILE_HEIGHT_PIXELS = 64;
    public static final int NUMBER_OF_ROW_TILES = 60;
    public static final int NUMBER_OF_COLUMN_TILES = 60;
    private int[][] map;

    public MapLayout() {
        initializeLayout();
    }
    private void initializeLayout() {
        map = new int[NUMBER_OF_ROW_TILES][NUMBER_OF_COLUMN_TILES];
        for (int iRow = 0; iRow < NUMBER_OF_ROW_TILES; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_COLUMN_TILES; iCol++) {
                map[iRow][iCol] = 0;
            }
        }
    }

    public int[][] getLayout() {
        return map;
    }


}
