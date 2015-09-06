package com.insight.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.insight.*;
import com.insight.game.objects.*;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayerScreen extends ScreenAdapter {
    InsightGame game;
    int state;
    private TiledMap map;
    //private OrthogonalTiledMapRenderer renderer;
    private GameRenderer renderer;
    private OrthographicCamera camera;
    private Avatar playerNS;

    public PlayerScreen (InsightGame game){
        this.game = game;

        // load map texture TMX file
        map = new TmxMapLoader().load("map.tmx");
        playerNS = new Avatar();
        playerNS.setPosition(20,20);


        // initialize game renderer
        renderer = new GameRenderer(map, playerNS);
    }

    @Override
    public void render (float delta) {

        // clear screen
        Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render map
        renderer.renderMap();
        renderer.renderPlayer();
    }
}
