package com.cedricmartens.hectogon.client.core.world;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Map
{
    private static Terrain grass;
    private static Terrain flowers;

    private Terrain[][] mapData;
    private int mapWidth = 50;
    private int mapHeight = 50;

    public Map(AssetManager assetManager)
    {
        Map.grass = new Terrain();
        Map.grass.setSpeed(1);
        Map.grass.setTexture(assetManager.get("tiles/grass_tile.png", Texture.class));

        Map.flowers = new Terrain();
        Map.flowers.setSpeed(1);
        Map.flowers.setTexture(assetManager.get("tiles/grassflowers_tile.png", Texture.class));

        generateMap();
    }

    private void generateMap()
    {
        Random r = new Random(0xdeadbeef);
        mapData = new Terrain[mapWidth][mapHeight];
        for(int i = 0; i < mapWidth; i++)
        {
            for(int j = 0; j < mapHeight; j++)
            {
                if(r.nextInt(15) != 0)
                {
                    mapData[i][j] = Map.grass;
                }else{
                    mapData[i][j] = Map.flowers;
                }
            }
        }
    }

    public void render(SpriteBatch spriteBatch)
    {
        int textureW = mapData[0][0].getTexture().getWidth();
        int textureH = mapData[0][0].getTexture().getHeight();

        for(int i = 0; i < mapWidth; i++)
        {
            for(int j = 0; j < mapHeight; j++)
            {
                spriteBatch.draw(mapData[i][j].getTexture(), i * textureW, j * textureH);
            }
        }
    }
}
