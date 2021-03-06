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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.insight.game.objects.Avatar;
import com.insight.networking.SessionManager;

public class PlayerScreen extends ScreenAdapter {
    Stage stage;
    Skin skin;
    SpriteBatch spriteBatch;
    Texture texture;
    Texture overlayTexture;
    Texture overlayFSTexture;

    SessionManager session;
    InsightGame game;
    //TODO: handle walking + standing sprite animations
    int state;
    private TiledMap map;
    private WorldRenderer renderer;
    private OrthographicCamera camera;
    private Sprite overlayNS;
    private Avatar playerOverlay;
    private Sprite overlayFS;
    private PlayerController controller;

    private Avatar currentPlayer;
    private Avatar otherPlayer;

    public PlayerScreen (InsightGame game){
        this.game = game;

        session = SessionManager.instance();
        playerOverlay = session.getPlayers().get("playerOverlay");

      if(session.clientRole == "playerNS") {
        currentPlayer = session.getPlayers().get("playerNS");
        otherPlayer = session.getPlayers().get("playerFS");
          overlayTexture = new Texture("NSOverlay.png");


      } else {
        currentPlayer = session.getPlayers().get("playerFS");
        otherPlayer = session.getPlayers().get("playerNS");
          overlayTexture = new Texture("FSOverlay.png");
      }

        // load map texture TMX file
        map = new TmxMapLoader().load("map2.tmx");

        //spriteBatch = new SpriteBatch();
        texture = new Texture("testSprite.png");
        //overlayNS = new Texture("NSoverlay.png");
        //overlayNS = new Sprite (overlayNSTexture);

        currentPlayer.setPlayerSprite(new Sprite(texture));
        otherPlayer.setPlayerSprite(new Sprite(texture));
        playerOverlay.setPlayerSprite(new Sprite(overlayTexture));

        System.out.println("BBB: " + currentPlayer + ", " + otherPlayer);

        currentPlayer.setPosition(Avatar.START_POSITION.x, Avatar.START_POSITION.y);
        otherPlayer.setPosition(Avatar.START_POSITION.x, Avatar.START_POSITION.y);
        playerOverlay.setCenter(currentPlayer.getX(), currentPlayer.getY());

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        //initialize player + map renderer
        //TODO: add world units parameter to WorldRenderer
        renderer = new WorldRenderer(map);
        controller = new PlayerController(stage);
        renderer.addSprite(currentPlayer.getPlayerSprite());
        renderer.addSprite(otherPlayer.getPlayerSprite());
        renderer.addSprite(playerOverlay.getPlayerSprite());

        session.listenForMovement();
    }

    @Override
    public void show() {
      Label titleLabel = new Label("Session Title: " + session.title, skin);
      Label descriptionLabel = new Label("Session Description: " + session.description, skin);
      Label sessionIdLabel = new Label("Session ID: " + session.sessionId, skin);
      Label sessionKeyLabel = new Label("Session Key: " + session.sessionKey, skin);
      Label clientRoleLabel = new Label("Session Role: " + session.clientRole, skin);

      Table t = new Table();
      t.add(titleLabel).align(Align.left).width(600);
      t.row();
      t.add(descriptionLabel).align(Align.left).width(600);
      t.row();
      t.add(sessionIdLabel).align(Align.left).width(600);
      t.row();
      t.add(sessionKeyLabel).align(Align.left).width(600);
      t.row();
      t.add(clientRoleLabel).align(Align.left).width(600);
      t.layout();
      stage.addActor(t);
      t.setPosition(320, 520);

      // Fade in the stage
      stage.getRoot().setColor(1, 1, 1, 0);
      stage.getRoot().addAction(Actions.fadeIn(0.5f));

      camera = new OrthographicCamera();
      camera.setToOrtho(false, 800, 608);
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
        controller.update(map, deltaTime);
        // visual scope for near-sighted player
        //overlayNS.setCenter(currentPlayer.getX(), currentPlayer.getY());

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
