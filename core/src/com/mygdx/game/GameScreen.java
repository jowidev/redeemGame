package com.mygdx.game;

import com.MenuScreens.Timer;
import com.MenuScreens.TeamScreen;
import com.Server.Client;
import com.Troops.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private TDGame game;
    private Slime slime;
    private Boulder boulder;
    private final ArrayList<BaseTroop> troopArr = new ArrayList<>();
    private final ArrayList<BaseTroop> tempArr = new ArrayList<>();
    private float money;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    public Stage st;
    public OrthographicCamera cam;
    public FitViewport fVp;

    private final Music mainsong;
    public boolean songPlaying = true;
    private final Timer timer;
    private Lawnmower lm;
    private ArrayList<Lawnmower> lms;
    public GameScreen(TDGame game, TeamScreen.Team team) {
        this.game = game;
        mainsong = game.assets.finalbattle;
        this.map = new TmxMapLoader().load("tilemap/tilemap.tmx");
        timer = new Timer(game);
        mapRenderer = new OrthogonalTiledMapRenderer(map, Constants.PIXELTOTILE, TDGame.batch);
        lms = new ArrayList<>();

        cam = new OrthographicCamera();
        st = new Stage();
        fVp = new FitViewport(Constants.GAME_WORLD_WIDTH_tile, Constants.GAME_WORLD_HEIGHT_tile, cam);
        cam.position.set(Constants.GAME_WORLD_WIDTH_tile / 2, Constants.GAME_WORLD_HEIGHT_tile / 2, 0);
        Gdx.input.setInputProcessor(st);

        GridCell gs = new GridCell(st);
        st.addActor(gs);

        for (int i = 0; i < 24; i += 2) {
            lms.add(new Lawnmower(0, i));
        }


        if (team == TeamScreen.Team.SLIME) {
            st.addActor(timer.getSlimeTable());
        } else {
            st.addActor(timer.getBoulderTable());
        }
        st.addActor(timer.getTimerTable());


        mainsong.setLooping(true);
        mainsong.setVolume(.01f);
        mainsong.play();

        // client.start();
    }

    private void inputHandling() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            slime = new Slime(Gdx.input.getX(), Gdx.input.getY());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            boulder = new Boulder(Gdx.input.getX(), Gdx.input.getY());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            if (songPlaying) {
                mainsong.setVolume(0);
            } else {
                mainsong.setVolume(0.07f);
            }
            songPlaying = !songPlaying;
        }
    }


    public void render(float delta) {
        Gdx.gl.glClearColor(.2f, .5f, .7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        TDGame.batch.setProjectionMatrix(cam.combined);
        fVp.apply();

        mapRenderer.setView((OrthographicCamera) fVp.getCamera());
        mapRenderer.render();

        st.act(Gdx.graphics.getDeltaTime());
        if (slime != null&&!troopArr.contains(slime)) slime.update(fVp, troopArr);
        if (boulder != null&&!troopArr.contains(boulder)) boulder.update(fVp, slime, troopArr, tempArr);

        TDGame.batch.begin();

        troopRendering();
        renderTimer(delta);

        TDGame.batch.end();

        st.draw();
        inputHandling();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void renderTimer(float delta) {
        if (timer != null) {
            timer.update(game, delta);
            if (timer.getTime() <= 0) {
                timer.stop();
                //timer things
            }
        }
    }
    public void troopRendering() {
        tempArr.clear();
        if (slime != null) {
            if (!troopArr.contains(slime)) {
                slime.render();
           }
        }
        if (boulder != null) {
            if (!troopArr.contains(boulder)) {
                boulder.render();
            }
        }

        for (Lawnmower lm : lms) {
            if (lm != null) {
                lm.draw();
                lm.instakill(boulder, tempArr, troopArr);
            }
        }
        for (BaseTroop troop : troopArr) {
            if (troop != null) {
                if (troop instanceof Slime) {
                    troop.update(fVp,troopArr);
                } else {
                    troop.update(fVp,slime, troopArr, tempArr);
                }
                troop.render();
            }
        }
        for (BaseTroop tempTroop : tempArr) {
            troopArr.remove(tempTroop);
        }
    }
    @Override
    public void resize(int width, int height) {
        fVp.update(width, height, true);
    }
    public void handleReceivedTroopCoordinates(String message, TeamScreen.Team team) {
        // Example: "Slime placed at x,y:100:200"
        if (message.startsWith("Slime placed at") || message.startsWith("Boulder placed at")) {
            String[] parts = message.split(":");
            int x = (int) Float.parseFloat(parts[4]);
            int y = (int) Float.parseFloat(parts[5]);

            // Render the troop in the game screen
            renderReceivedTroop(x, y, team);

            // Render the troop in the game screen
        }
    }
    private void renderReceivedTroop(int x, int y, TeamScreen.Team team) {

        // Create the troop based on the team and render it
        if (team == TeamScreen.Team.SLIME) {
            slime = new Slime(x, y);
        } else if (team == TeamScreen.Team.BOULDER) {
            boulder = new Boulder(x, y);
        }
    }
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    public void show() {}
    @Override
    public void dispose() { //se llama cuando se cierra el programa
        game.dispose();
        map.dispose();
        mapRenderer.dispose();
        mainsong.dispose();
        st.dispose();
    }
}
