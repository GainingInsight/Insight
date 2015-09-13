package com.insight.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.insight.*;
import com.insight.game.objects.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class PlayerScreen extends ScreenAdapter {
    Stage stage;
    Skin skin;
    SpriteBatch spriteBatch;
    Texture texture;

    InsightGame game;
    //TODO: handle walking + standing sprite animations
    int state;
    private TiledMap map;
    private PlayerRenderer renderer;
    private OrthographicCamera camera;
    private Sprite playerNS;
    private Sprite overlayNS;
    private PlayerController controller;


    public PlayerScreen (InsightGame game){
        this.game = game;

        // load map texture TMX file
        map = new TmxMapLoader().load("map.tmx");

        //spriteBatch = new SpriteBatch();
        texture = new Texture("testSprite.png");
        playerNS = new Sprite(texture);
        playerNS.setPosition(100,100);


        //initialize player + map renderer
        //TODO: add world units parameter to PlayerRenderer
        renderer = new PlayerRenderer(map);
        controller = new PlayerController();
        renderer.addSprite(playerNS);

    }

    @Override
    public void show() {
      stage = new Stage(new ScreenViewport());
      skin = new Skin(Gdx.files.internal("uiskin.json"));

      Label testLabel = new Label("Test label:", skin);
      Table t = new Table();
      t.add(testLabel).align(Align.left).width(200);
      t.layout();
      stage.addActor(t);
      t.setPosition(110, 20);

      // Fade in the stage
      stage.getRoot().setColor(1, 1, 1, 0);
      stage.getRoot().addAction(Actions.fadeIn(0.5f));

      camera = new OrthographicCamera();
      camera.setToOrtho(false, 800, 600);
      camera.update();

      Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float delta) {
        // clear screen
        Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // controller for player movement
        float deltaTime = Gdx.graphics.getDeltaTime();
        controller.update(playerNS, map, deltaTime);
        //TEST OVERLAY FOLLOWING PLAYER
        overlayNS.setPosition(playerNS.getX(),playerNS.getY());

        // render map
        camera.update();
        renderer.setView(camera);
        renderer.render();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();

    }
}
