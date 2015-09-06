package com.insight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.tests.utils.GdxTest;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameRenderer {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public GameRenderer (TiledMap map) {
        this.map = map;
        // load map texture TMX file
        //map = new TmxMapLoader().load("map.tmx");

        // top-down renderer for map with 1 unit = 10 pixels
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 10f);
        camera = new OrthographicCamera();

        // camera view = 80 x 60 units = 800x600 res
        camera.setToOrtho(false, 80, 60);
        camera.update();
        renderer.setView(camera);
    }

    public void renderMap(){
      renderer.render();
    }

    public void renderPlayer(){

    }
}
