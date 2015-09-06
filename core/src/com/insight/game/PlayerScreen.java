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

        // load map texture TMX file
        map = new TmxMapLoader().load("map.tmx");
       // renderer with 1 unit = 10 pixels
        renderer = new OrthogonalTiledMapRenderer(map,1/10f);

        // create an orthographic camera, shows us 80x60 units of the world (800x600 res)
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 80, 60);
        camera.update();

    }



    @Override
    public void render (float delta) {

        // clear screen
        Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // call renderer
        renderer.setView(camera);
        renderer.render();
    }
}
