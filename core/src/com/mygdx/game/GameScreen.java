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
    private final OrthogonalTiledMapRenderer mapRenderer;
    private Stage st;
    public OrthographicCamera cam;
    public FitViewport fVp;
    private final Music mainSong;
    public boolean songPlaying = true;
    private final HUD HUD;
    public boolean boulderReached = false;
    private ArrayList<Defense> defenses;
    public GridStage grid;
    public Client client;
    protected TeamScreen.Team team;
    public GameScreen(TDGame game, TeamScreen.Team t) {
        this.game = game;
        mainSong = game.assets.mainsong2;
        this.team = t;
        this.map = new TmxMapLoader().load("tilemap/tilemap.tmx");
        HUD = new HUD(game, money);
        mapRenderer = new OrthogonalTiledMapRenderer(map, Constants.PIXELTOTILE, TDGame.batch);
        defenses = new ArrayList<>();

        cam = new OrthographicCamera();
        st = new Stage();
        fVp = new FitViewport(Constants.GAME_WORLD_WIDTH_tile, Constants.GAME_WORLD_HEIGHT_tile, cam);
        cam.position.set(Constants.GAME_WORLD_WIDTH_tile / 2, Constants.GAME_WORLD_HEIGHT_tile / 2, 0);
        Gdx.input.setInputProcessor(st);
        grid = new GridStage(st);

        for (float i = 1.2f; i < 10; i += 2.1f) {
            defenses.add(new Defense(0, i));
        }

        client = new Client(this);


        if (team == SLIME) st.addActor(HUD.getSlimeTable());
        else st.addActor(HUD.getBoulderTable());

        st.addActor(HUD.getTimerTable());


        createMusic();

        client.start();
    }
    private void createMusic() {
        mainSong.setLooping(true);
        mainSong.setVolume(.25f);
        mainSong.play();
    }
    private void handleInput() {
        //if(team == TeamScreen.Team.SLIME) {

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                if (HUD.hasEnoughMoney(MoneySlime.COST)) {
                    createSlime(1, Gdx.input.getX(),Gdx.input.getY());

                } else {
                    notEnoughMoney();
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                if (HUD.hasEnoughMoney(ShooterSlime.COST)) {
                    createSlime(2, Gdx.input.getX(),Gdx.input.getY());

                } else {
                    notEnoughMoney();
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                if (HUD.hasEnoughMoney(ShieldSlime.COST)) {
                    createSlime(3, Gdx.input.getX(),Gdx.input.getY());

                } else {
                    notEnoughMoney();
                }
            }
       // } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
                if (HUD.hasEnoughMoney(BasicBoulder.COST)) {
                    createBoulder(1,Gdx.input.getX(),Gdx.input.getY());
                } else {
                    notEnoughMoney();
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
                if (HUD.hasEnoughMoney(FastBoulder.COST)) {
                   createBoulder(2,Gdx.input.getX(),Gdx.input.getY());
                } else {
                    notEnoughMoney();
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
                if (HUD.hasEnoughMoney(ArmoredBoulder.COST)) {
                    createBoulder(3,Gdx.input.getX(),Gdx.input.getY());
                } else {
                    notEnoughMoney();
                }
           // }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))  Gdx.app.exit();

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            if (songPlaying) mainSong.setVolume(0);
                else mainSong.setVolume(0.07f);
            songPlaying = !songPlaying;
        }
    }

    private void createSlime(int type, int x, int y) {
        switch (type) {
            case 1:
                slime = new MoneySlime(x, y, money, true);
                HUD.decreaseSlimeMoney(MoneySlime.COST);
                break;
            case 2:
                slime = new ShooterSlime(x,y,bulletArr,true);
                HUD.decreaseSlimeMoney(ShooterSlime.COST);
                break;
            case 3:
                slime = new ShieldSlime(x,y,true);
                HUD.decreaseSlimeMoney(ShieldSlime.COST);
                break;
            default:
                slime = null;

        }
    }
    private void createBoulder(int type, int x, int y) {
        switch (type) {
            case 1:
                boulder = new BasicBoulder(x, y, true, game);
                HUD.decreaseBoulderMoney(BasicBoulder.COST);
                break;
            case 2:
                boulder = new FastBoulder(x,y,true,game);
                HUD.decreaseBoulderMoney(FastBoulder.COST);

                break;
            case 3:
                boulder = new ArmoredBoulder(x,y,true, game);
                HUD.decreaseBoulderMoney(ArmoredBoulder.COST);

                break;
            default:
                boulder = null;
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
        if (slime != null && !troopArr.contains(slime)) slime.update(fVp, boulder, troopArr);
        if (boulder != null && !troopArr.contains(boulder))
            boulder.update(fVp, slime, troopArr, tempArr, boulderReached);
        TDGame.batch.begin();

        updateGameLogic(delta);
        renderGameLogic();

        TDGame.batch.end();
        st.draw();
        handleInput();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    // Updates game logic not directly linked with rendering
    private void updateGameLogic(float delta) {
        // Game state updates, excluding rendering-specific code
        // Consider renaming 'gridChecker' & 'renderTimer' if they are not rendering
        // e.g., rename 'renderTimer' to 'updateTimer'
        int numberOfMoneySlimes = countMoneySlimes();
        boolean hasMoneySlime = checkForMoneySlime();
        updateTimer(delta);
        HUD.updateMoney(delta, numberOfMoneySlimes, hasMoneySlime);
        updateGrid();
        updateBullets();
        updateTroops();
        updateDefenses();
    }

    public void renderGameLogic() {
        renderActiveSlime();
        renderActiveBoulder();
        renderDefenses();
        renderTroops();
        renderBullets();

        removeTempEntities();
    }

    private void renderTroops() {
        for (BaseTroop troop : troopArr) {
            troop.render();
        }
    }
    private void renderBullets() {
        for (Bullet bullet : bulletArr) {
            bullet.render();
        }
    }
    private void renderDefenses() {
        for (Defense defense : defenses) {
            defense.render();
        }
    }
    private void renderActiveSlime() {
        if (slime != null && !troopArr.contains(slime)) {
            slime.render();
        }

    }

    private void renderActiveBoulder() {
        if (boulder != null && !troopArr.contains(boulder)) {
            boulder.render();
        }
    }
    private void updateTroops() {
        for (BaseTroop troop : troopArr) {
            if (troop instanceof Slime) {
                troop.update(fVp, boulder, troopArr);
            } else {
                troop.update(fVp, slime, troopArr, tempArr, boulderReached);
            }
        }
    }
    private void updateDefenses() {
        for (Defense defense : defenses) {
            defense.instakill(boulder, tempArr, troopArr);
        }
    }

    private void updateBullets() {
        for (Bullet bullet : bulletArr) {
            bullet.update(troopArr, tempArr, bulletTemp);
        }
    }
    public void updateGrid() {
        for (int i=0; i<9;i++) {
            for (int j=0; j<5;j++) {
                grid.gridCells[i][j].touched(boulder, slime,st.getViewport());
            }
        }
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

    }
    public void updateTimer(float delta) {
        if (HUD != null) {
            HUD.update(game, delta);
            if (HUD.getTime() <= 0) {
                HUD.stop();
                game.setScreen(new GameOverScreen(TeamScreen.Team.SLIME, game));
            }
        }
    }

    private void removeTempTroops() {
        for (BaseTroop tempTroop : tempArr) {
            troopArr.remove(tempTroop);
        }
        tempArr.clear();
    }
    private void removeTempBullets() {
        for (Bullet bullet : bulletTemp) {
            bulletArr.remove(bullet);
        }
        bulletTemp.clear();
    }
    private void removeTempEntities() {
        removeTempBullets();
        removeTempTroops();
    }

    @Override
    public void resize(int width, int height) {
        fVp.update(width, height, true);
    }

    public void handleTroopCoords(String message, TeamScreen.Team team) {
        // Example message format: "slime:2:3"
        if (message.contains(":")) {
            String[] parts = message.split(":");
            String unitTeam = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            // Render the troop in the game screen
            System.out.println(unitTeam + " " + x);
            System.out.println(unitTeam + " " + y);
            renderTroopCoords(x, y, team);
        }
    }

    private void renderTroopCoords(int x, int y, TeamScreen.Team team) {

        // Create the troop based on the team and render it
        if (team == SLIME) {
            slime = new ShooterSlime(x, y,bulletArr,false);
            //slime.update(fVp,boulder, tempArr);

        } else if (team == TeamScreen.Team.BOULDER) {
            boulder = new BasicBoulder(x, y,false,game);
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
    public int countMoneySlimes() {
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
        mainSong.dispose();
        st.dispose();
    }

}