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
    private int mapWidth = 5000;
    private int mapHeight = 5000;

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
                float chance = 0.01f;

                chance += getDirectNeighborCountOf(i, j, flowers) * 0.20f;

                if(r.nextFloat() > chance)
                {
                    mapData[i][j] = Map.grass;
                }else{
                    mapData[i][j] = Map.flowers;
                }
            }
        }
    }

    private int getNeighorCountOf(int x, int y, Terrain terrain)
    {
        int count = 0;
        if(x > 0)
        {
            count += mapData[x - 1][y] == terrain ? 1 : 0;

            if(y > 0)
            {
                count += mapData[x - 1][y - 1] == terrain ? 1 : 0;
            }

            if(y < mapData.length - 1)
            {
                count += mapData[x - 1][y + 1] == terrain ? 1 : 0;
            }
        }

        if(x < mapData.length - 1)
        {
            count += mapData[x + 1][y] == terrain ? 1 : 0;

            if(y > 0)
            {
                count += mapData[x + 1][y - 1] == terrain ? 1 : 0;
            }

            if(y < mapData.length - 1)
            {
                count += mapData[x + 1][y + 1] == terrain ? 1 : 0;
            }
        }

        if(y > 0)
        {
            count += mapData[x][y - 1] == terrain ? 1 : 0;
        }

        if(y < mapData.length - 1)
        {
            count += mapData[x][y + 1] == terrain ? 1 : 0;
        }

        return count;
    }

    private int getDirectNeighborCountOf(int x, int y, Terrain terrain)
    {
        int count = 0;
        if(x > 0)
            count += mapData[x - 1][y] == terrain ? 1 : 0;

        if(x < mapData.length - 1)
            count += mapData[x + 1][y] == terrain ? 1 : 0;

        if(y > 0)
            count += mapData[x][y - 1] == terrain ? 1 : 0;

        if(y < mapData.length - 1)
            count += mapData[x][y + 1] == terrain ? 1 : 0;

        return count;

    }

    public void render(SpriteBatch spriteBatch, float x, float y, float radius)
    {
        //TODO don't make an entity render itself
        int textureW = mapData[0][0].getTexture().getWidth();
        int textureH = mapData[0][0].getTexture().getHeight();

        int offsetX = mapWidth/2;
        int offsetY = mapHeight/2;

        x += offsetX;
        y += offsetY;

        for(int i = (int) ((x - radius)/textureW);
            i * textureW < x + radius && i < mapWidth;
            i++)
        {
            i = i < 0 ? 0 : i;
            for(int j = (int)((y - radius)/textureH); j * textureH < y + radius && j < mapHeight; j++)
            {
                j = j < 0 ? 0 : j;
                spriteBatch.draw(mapData[i][j].getTexture(), i * textureW - offsetX, j * textureH - offsetY);
            }

        }
    }
}
