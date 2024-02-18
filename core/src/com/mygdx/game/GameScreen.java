package com.mygdx.game;

import com.MenuScreens.GameOverScreen;
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

import static com.MenuScreens.TeamScreen.Team.SLIME;

public class GameScreen implements Screen {
    private TDGame game;
    private Slime slime;
    private Boulder boulder;
    private final ArrayList<BaseTroop> troopArr = new ArrayList<>();
    private final ArrayList<BaseTroop> tempArr = new ArrayList<>();
    private final ArrayList<Bullet> bulletArr = new ArrayList<>();
    private final ArrayList<Bullet> bulletTemp = new ArrayList<>();
    private float money = 999;
    private final TiledMap map;
    private int boulderPassed = 0;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private Stage st;
    public OrthographicCamera cam;
    public FitViewport fVp;
    private final Music mainsong;
    public boolean songPlaying = true;
    private final HUD HUD;
    public boolean boulderReached = false;
    private ArrayList<Defense> lms;
    public GridStage grid;
    public Client client;
    protected TeamScreen.Team team;


    public GameScreen(TDGame game, TeamScreen.Team t) {
        this.game = game;
        mainsong = game.assets.mainsong2;
        this.team = t;
        this.map = new TmxMapLoader().load("tilemap/tilemap.tmx");
        HUD = new HUD(game, money);
        mapRenderer = new OrthogonalTiledMapRenderer(map, Constants.PIXELTOTILE, TDGame.batch);
        lms = new ArrayList<>();

        cam = new OrthographicCamera();
        st = new Stage();
        fVp = new FitViewport(Constants.GAME_WORLD_WIDTH_tile, Constants.GAME_WORLD_HEIGHT_tile, cam);
        cam.position.set(Constants.GAME_WORLD_WIDTH_tile / 2, Constants.GAME_WORLD_HEIGHT_tile / 2, 0);
        Gdx.input.setInputProcessor(st);
        grid = new GridStage(st);

        for (float i = 1.2f; i < 10; i += 2.1f) {
            lms.add(new Defense(0, i));
        }

        client = new Client(this);


        if (team == SLIME) st.addActor(HUD.getSlimeTable());
        else st.addActor(HUD.getBoulderTable());

        st.addActor(HUD.getTimerTable());


        createMusic();

        client.start();
    }
    private void createMusic() {
        mainsong.setLooping(true);
        mainsong.setVolume(.0f);
        mainsong.play();
    }
    private void inputHandling() {
        //if(team == TeamScreen.Team.SLIME) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                if (HUD.hasEnoughMoney(MoneySlime.COST)) {
                    slime = new MoneySlime(Gdx.input.getX(),Gdx.input.getY(),money,true);
                    if (!slime.troopPlaced) {
                        HUD.decreaseSlimeMoney(MoneySlime.COST);
                    }
                } else {
                    notEnoughMoney();
                }
            }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            if (HUD.hasEnoughMoney(MoneySlime.COST)) {
                boulder = new BasicBoulder(9,1,false);}}

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                if (HUD.hasEnoughMoney(ShooterSlime.COST)) {
                    slime = new ShooterSlime(Gdx.input.getX(),Gdx.input.getY(), bulletArr,true);
                    HUD.decreaseSlimeMoney(ShooterSlime.COST);
                } else {
                    notEnoughMoney();
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                if (HUD.hasEnoughMoney(ShieldSlime.COST)) {
                    slime = new ShieldSlime(Gdx.input.getX(), Gdx.input.getY(),true);
                    HUD.decreaseSlimeMoney(ShieldSlime.COST);
                } else {
                    notEnoughMoney();
                }
            }
       // }
        //else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
                if (HUD.hasEnoughMoney(BasicBoulder.COST)) {
                    boulder = new BasicBoulder(Gdx.input.getX(),Gdx.input.getY(),true);
                    HUD.decreaseBoulderMoney(BasicBoulder.COST);
                } else {
                    notEnoughMoney();
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
                if (HUD.hasEnoughMoney(FastBoulder.COST)) {
                    boulder = new FastBoulder(Gdx.input.getX(),Gdx.input.getY(),true);
                    HUD.decreaseBoulderMoney(FastBoulder.COST);
                } else {
                    notEnoughMoney();
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
                if (HUD.hasEnoughMoney(ArmoredBoulder.COST)) {
                    boulder = new ArmoredBoulder(Gdx.input.getX(),Gdx.input.getY(),true);
                    HUD.decreaseBoulderMoney(ArmoredBoulder.COST);
                } else {
                    notEnoughMoney();
                }
            }
        //}
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
    public void notEnoughMoney() {
        TDGame.assets.error.setVolume(1,.5f);
        TDGame.assets.error.play();
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
        if (slime != null&&!troopArr.contains(slime)) slime.update(fVp, boulder,troopArr);
        if (boulder != null&&!troopArr.contains(boulder)) boulder.update(fVp, slime, troopArr, tempArr, boulderReached);
        TDGame.batch.begin();

        boulderWin();
        gridChecker();
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

    public void gridChecker() {
        /*for (int i=0; i<9;i++) {
            for (int j=0; j<5;j++) {
                if (i<6) {
                    grid.gridCells[i][j].touched(slime,st.getViewport());
                }
                else {
                    grid.gridCells[i][j].touched(boulder,st.getViewport());
                }
            }
        }*/
        for (int i=0; i<9;i++) {
            for (int j=0; j<5;j++) {
                grid.gridCells[i][j].touched(boulder, slime,st.getViewport());
            }
        }

    }
    public void renderTimer(float delta) {
        if (HUD != null) {
            HUD.update(game, delta);
            if (HUD.getTime() <= 0) {
                HUD.stop();
                game.setScreen(new GameOverScreen(TeamScreen.Team.SLIME, game));
            }
        }
    }
    public void boulderWin() {
        if (boulder != null && boulder.boulderMov(tempArr, boulderReached)) {
            boulderPassed++;
            if (boulderPassed >= 3) {
                System.out.println("¡Los boulders ganaron!");
                game.setScreen(new GameOverScreen(TeamScreen.Team.BOULDER, game));
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
        for (BaseTroop troop : troopArr) {
            if (troop != null) {
                if (troop instanceof Slime) {
                    troop.update(fVp,boulder,troopArr);
                } else {
                    troop.update(fVp,slime, troopArr, tempArr, boulderReached);
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
        // Example message format: "2:3"
        if (message.contains(":")) {
            String[] parts = message.split(":");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            // Render the troop in the game screen
            System.out.println(x);
            System.out.println(y);
            renderReceivedTroop(x, y, team);
        }
    }

    private void renderReceivedTroop(int x, int y, TeamScreen.Team team) {

        // Create the troop based on the team and render it
        if (team == SLIME) {
            slime = new ShooterSlime(x, y,bulletArr,false);
            //slime.update(fVp,boulder, tempArr);

        } else if (team == TeamScreen.Team.BOULDER) {
            boulder = new BasicBoulder(x, y,false);
            //boulder.update(fVp, slime, troopArr, tempArr, boulderReached);
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

}