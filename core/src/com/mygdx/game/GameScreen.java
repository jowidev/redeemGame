package com.mygdx.game;

import com.MenuScreens.HUD;
import com.MenuScreens.TeamScreen;
import com.Server.Client;
import com.Troops.*;
import com.Troops.TeamTroops.*;
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

public class GameScreen implements Screen {
    private TDGame game;
    private Slime slime;
    private Boulder boulder;
    private final ArrayList<BaseTroop> troopArr = new ArrayList<>();
    private final ArrayList<BaseTroop> tempArr = new ArrayList<>();
    private final ArrayList<Bullet> bulletArr = new ArrayList<>();
    private final ArrayList<Bullet> bulletTemp = new ArrayList<>();
    private float money = 0;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private Stage st;
    public OrthographicCamera cam;
    public FitViewport fVp;

    private final Music mainsong;
    public boolean songPlaying = true;
    private final HUD HUD;
    public int points = 0;
    private ArrayList<Defense> lms;
    public GridCell gs;
    public Client client;
    public GameScreen(TDGame game, TeamScreen.Team team) {
        this.game = game;
        mainsong = game.assets.finalbattle;
        this.map = new TmxMapLoader().load("tilemap/tilemap.tmx");
        HUD = new HUD(game, money);
        mapRenderer = new OrthogonalTiledMapRenderer(map, Constants.PIXELTOTILE, TDGame.batch);
        lms = new ArrayList<>();

        cam = new OrthographicCamera();
        st = new Stage();
        fVp = new FitViewport(Constants.GAME_WORLD_WIDTH_tile, Constants.GAME_WORLD_HEIGHT_tile, cam);
        cam.position.set(Constants.GAME_WORLD_WIDTH_tile / 2, Constants.GAME_WORLD_HEIGHT_tile / 2, 0);
        Gdx.input.setInputProcessor(st);
        gs = new GridCell(st, slime);
        st.addActor(gs);

        for (float i = 1.2f; i < 10; i += 2.1f) {
            lms.add(new Defense(0, i));
        }

        client = new Client(this);


        if (team == TeamScreen.Team.SLIME) {
            st.addActor(HUD.getSlimeTable());
        } else {
            st.addActor(HUD.getBoulderTable());
        }
        st.addActor(HUD.getTimerTable());


        mainsong.setLooping(true);
        mainsong.setVolume(.01f);
        mainsong.play();

        client.start();
    }

    private void inputHandling() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            slime = new ShieldSlime(Gdx.input.getX(),Gdx.input.getY());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            slime = new MoneySlime(Gdx.input.getX(),Gdx.input.getY(),money);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            slime = new ShooterSlime(Gdx.input.getX(),Gdx.input.getY(), bulletArr);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            boulder = new BasicBoulder(Gdx.input.getX(),Gdx.input.getY());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
            boulder = new FastBoulder(Gdx.input.getX(),Gdx.input.getY());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            boulder = new ArmoredBoulder(Gdx.input.getX(),Gdx.input.getY());
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
        //System.out.println(troopArr);
        st.act(Gdx.graphics.getDeltaTime());
        if (slime != null&&!troopArr.contains(slime)) slime.update(fVp, boulder,troopArr);
        if (boulder != null&&!troopArr.contains(boulder)) boulder.update(fVp, slime, troopArr, tempArr, points);
        TDGame.batch.begin();
        gs.touched(slime, st.getViewport());
        troopRendering();
        renderTimer(delta);
        int numberOfMoneySlimes = countMoneySlimesOnBoard();
        boolean hasMoneySlime = checkForMoneySlime();
        HUD.updateMoney(delta, numberOfMoneySlimes, hasMoneySlime);
        TDGame.batch.end();
        st.draw();
        inputHandling();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void renderTimer(float delta) {
        if (HUD != null) {
            HUD.update(game, delta);
            if (HUD.getTime() <= 0) {
                HUD.stop();
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

        for (Defense lm : lms) {
            if (lm != null) {
                lm.draw();
                lm.instakill(boulder, tempArr, troopArr);
            }
        }
        for (Bullet bullet : bulletArr) {
            if (bullet != null) {
                bullet.draw();
                bullet.update(troopArr, tempArr, bulletTemp);
            }
        }

        for (Bullet bullet : bulletTemp) {
            bulletArr.remove(bullet);
        }
        System.out.println(bulletArr);
        for (BaseTroop troop : troopArr) {
            if (troop != null) {
                if (troop instanceof Slime) {
                    troop.update(fVp,boulder,troopArr);
                } else {
                    troop.update(fVp,slime, troopArr, tempArr, points);
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
            slime = new ShieldSlime(x, y);
        } else if (team == TeamScreen.Team.BOULDER) {
            boulder = new BasicBoulder(x, y);
        }
    }
    public boolean checkForMoneySlime() {
        for (BaseTroop troop : troopArr) {
            if (troop instanceof MoneySlime) {
                return true;
            }
        }
        return false;
    }
    public int countMoneySlimesOnBoard() {
        int count = 0;
        for (BaseTroop troop : troopArr) {
            if (troop instanceof MoneySlime) {
                count++;
            }
        }
        return count;
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

    public Stage getSt() {
        return st;
    }

}