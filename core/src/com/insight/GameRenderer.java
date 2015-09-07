package com.insight;
import com.insight.game.objects.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.Texture;


public class GameRenderer {

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private Avatar player;


    public GameRenderer (TiledMap map, Avatar player) {
        this.map = map;
        this.player = player;


        // top-down renderer for map with 1 unit = 10 pixels
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 10f);
        camera = new OrthographicCamera();

        // camera view = 80 x 60 units = 800x600 res
        camera.setToOrtho(false, 80, 60);
        camera.update();
        mapRenderer.setView(camera);
    }

    public void renderMap(){
        System.out.print("test");
        mapRenderer.render();
    }

    public void renderPlayer(){
        Texture frame = player.getTexture();


        // TODO: texture based on player moving or standing
      //  switch (player.getState()) {
        //    case Standing:
                //frame = stand.getKeyFrame(player.stateTime);
         //       break;
        //    case Walking:
                //frame = walk.getKeyFrame(player.stateTime);
         //       break;

       // }

        // draw avatar
       // Batch batch = mapRenderer.getBatch();
        Batch batch = new SpriteBatch();
        batch.begin();
        batch.draw(frame, player.getPosition().x,player.getPosition().y);

        //TODO: draw avatar based on direction facing
        /**
        if (player.getDirection()) {
            batch.draw(frame, player.getPosition().x, player.getPosition().y, player.getWidth(), player.getHeight());
        } else {
            batch.draw(frame, player.getPosition().x + player.getWidth(), player.getPosition().y, -player.getWidth(), player.getHeight());
        }
         */
        batch.end();

    }
}
