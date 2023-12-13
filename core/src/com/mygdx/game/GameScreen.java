package com.mygdx.game;

import com.MenuScreens.Grid;
import com.MenuScreens.HUD;
import com.MenuScreens.MainMenuScreen;
import com.MenuScreens.TeamSelScreen;
import com.Server.Client;
import com.Troops.BaseTroop;
import com.Troops.Boulder;
import com.Troops.Slime;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import static com.MenuScreens.TeamSelScreen.Team.SLIME;

public class GameScreen implements Screen {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    public FitViewport viewport;
    public OrthographicCamera camera;

    public com.Troops.Slime Slime;
    public com.Troops.Boulder Boulder;
    private final Music mainsong;
    public Stage stage;
    public Gamemap gamemap; //el de arriba
    public static Float time;
    public boolean songPlaying;

    private ArrayList<BaseTroop> troopArr;
    public void show() {

    }
    public GameScreen(Gamemap gamemap, TeamSelScreen.Team team) {  //este
        this.gamemap = gamemap; //al de arriba le paso este
        troopArr = new ArrayList<BaseTroop>();
        Grid grid = new Grid();
        HUD hud = new HUD(gamemap);
        camera = new OrthographicCamera();
        stage = new Stage();
        //stage.addActor(grid);
        Client client = new Client();
        client.start();

        if (team.equals(TeamSelScreen.Team.SLIME)) {
            stage.addActor(hud.getSlimeTable());

        } else if (team.equals(TeamSelScreen.Team.BOULDER)){
            stage.addActor(hud.getBoulderHud());

        }
        stage.addActor(hud.getTimerTable());
        Gdx.input.setInputProcessor(stage);
        mainsong = gamemap.assets.finalbattle;
        mainsong.setLooping(true);
        mainsong.setVolume(.07f);
        songPlaying = true;
        this.map = new TmxMapLoader().load("tilemap/tilemap.tmx"); 		// mandarlo al assetmanager dsp si hincha las bolas
        mapRenderer = new OrthogonalTiledMapRenderer(map, Constants.pixeltotile, Gamemap.batch); // Create the map renderer
        this.viewport = new FitViewport(Constants.GAME_WORLD_WIDTH_tile,Constants.GAME_WORLD_HEIGHT_tile, camera);

        camera.position.set(Constants.GAME_WORLD_WIDTH_tile/2, Constants.GAME_WORLD_HEIGHT_tile/2, 0);
        //mainsong.play();

        time = 180.1f;

    }


    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            this.Slime = new Slime(gamemap, Gdx.input.getX(),Gdx.input.getY());
            //troopArr.add(Slime);

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            this.Boulder = new Boulder(gamemap, Gdx.input.getX(), Gdx.input.getY());
            //troopArr.add(Boulder);

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gamemap.setScreen(new MainMenuScreen(gamemap));
            map.dispose();
            mapRenderer.dispose();
            mainsong.dispose();
            stage.dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)&&songPlaying) {
            mainsong.setVolume(0);
            songPlaying = false;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)&&!songPlaying) {
            mainsong.setVolume(.07f);
            songPlaying = true;
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.2f, .5f, .7f, 1);
        camera.update();
        mainsong.play();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gamemap.batch.setProjectionMatrix(camera.combined);

        viewport.apply();
        stage.act(Gdx.graphics.getDeltaTime());
        mapRenderer.setView((OrthographicCamera) viewport.getCamera());
        mapRenderer.render();
        if (Slime != null) {
            Slime.update(viewport);

        }
        if (Boulder != null) {
            Boulder.update(viewport, Slime);
        }
        Gamemap.batch.begin();
        if (Boulder != null) {
            Boulder.render();
        }
        if (Slime != null) {
            Slime.render();
        }
        //System.out.println(troopArr);
        Gamemap.batch.end();
        stage.draw();
        handleInput();
        time -= Gdx.graphics.getDeltaTime(); // EL TIMER
        //System.out.println(time);
        if (time<=0) {
             gamemap.dispose();
        }


        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }





    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        stage.getViewport().update(width, height, true);
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //se llama cuando se cierra el programa
        map.dispose();
        mapRenderer.dispose();
        mainsong.dispose();
        stage.dispose();
    }
}
