package com.insight.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.insight.*;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
//import com.badlogicgames.superjumper.World.WorldListener;



/**
 * Created by jamesyanyuk on 9/5/15.
 */
public class PlayerScreen extends ScreenAdapter {
    InsightGame game;
    int state;
    OrthographicCamera guiCam;
    Vector3 touchPoint;
    //World world;
    //WorldListener worldListener;
    //GameRenderer renderer;
    OrthographicCamera cam;
    SpriteBatch batch;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public PlayerScreen (InsightGame game){
        this.game = game;

        // load map texture
        map = new TmxMapLoader().load("map.tmx");
       // renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
        renderer = new OrthogonalTiledMapRenderer(map,1/10f);

        // create an orthographic camera, shows us 30x20 units of the world
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 80, 60);
        camera.update();

    }



    @Override
    public void render (float delta) {
        renderer.setView(camera);
        renderer.render();
    }
}
