package com.mygdx.game;

import com.MenuScreens.GameOverScreen;
import com.MenuScreens.HUD;
import com.MenuScreens.TeamScreen;
import com.Server.Client;
import com.Server.Globals;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import jdk.vm.ci.code.BailoutException;

import java.util.ArrayList;

import static com.MenuScreens.TeamScreen.Team.BOULDER;
import static com.MenuScreens.TeamScreen.Team.SLIME;

public class GameScreen implements Screen {
    private final TDGame game;
    private final ArrayList<BaseTroop> troopArr = new ArrayList<>();
    private final ArrayList<BaseTroop> tempArr = new ArrayList<>();
    public final ArrayList<Bullet> bulletArr = new ArrayList<>();
    private final ArrayList<Bullet> bulletTemp = new ArrayList<>();
    public float money = 0;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Stage st;
    public OrthographicCamera cam;
    public FitViewport fVp;
    private final Music mainSong;
    public boolean songPlaying = true;
    private final HUD HUD;
    public boolean boulderReached = false;
    private final ArrayList<Defense> defenses;
    public GridStage grid;
    public Client client;
    public TeamScreen.Team team;

    private BaseTroop selector;

    public GameScreen(TDGame game, TeamScreen.Team t) {
        this.game = game;
        mainSong = TDGame.assets.mainsong2;
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

        Globals.clientScreen = this;
        client = new Client(this);



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
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && selector != null) {
            GridCell cell = grid.getCellByCoords(Gdx.input.getX(), (Gdx.graphics.getHeight() - Gdx.input.getY()));
            if (cell != null && cell.placeTroop(selector)) {
                client.sendEventToServer("plant#" + cell.getId() + "#" + Globals.determineTroopType(selector));
                selector = null;
            }
        }

        BaseTroop anySelected = null;

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            anySelected =
                    Globals.clientId == 0 ?
                    new MoneySlime(0, 0, money, true) :
                            new BasicBoulder(0, 0, true);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            anySelected = Globals.clientId == 0 ?
                    new ShooterSlime(0, 0, bulletArr, true) :
                    new FastBoulder(0, 0, true);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            anySelected = Globals.clientId == 0 ?
                    new ShieldSlime(0, 0, true)
                    : new ArmoredBoulder(0, 0, true);
        }
        if (anySelected != null) selector = anySelected;
        // CHECK MONEY.
        //!!!

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            if (songPlaying) mainSong.setVolume(0);
            else mainSong.setVolume(0.07f);
            songPlaying = !songPlaying;
        }
    }

    public void notEnoughMoney() {
        TDGame.assets.error.setVolume(1, .5f);
        TDGame.assets.error.play();
    }


    public void render(float delta) {
        Gdx.gl.glClearColor(.2f, .5f, .7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (!Globals.ready) return;

        // over = -1 ; over = 0 gana slime ; over = 1 gana boulder
        if(Globals.over != -1){
            game.setScreen(new GameOverScreen(Globals.over == 0 ? SLIME : BOULDER, game));

            Globals.over = -1;
            Globals.ready = false;
            Globals.clientId = null;
        }
        cam.update();
        TDGame.batch.setProjectionMatrix(cam.combined);

        fVp.apply();
        mapRenderer.setView((OrthographicCamera) fVp.getCamera());
        mapRenderer.render();

        st.act(Gdx.graphics.getDeltaTime());


        TDGame.batch.begin();

        handleInput();
        updateGameLogic(delta);
        renderGameLogic();

        TDGame.batch.end();
        st.draw();

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


    private void updateGameLogic(float delta) {
        // Game state updates, excluding rendering-specific code
        int numberOfMoneySlimes = countMoneySlimes();
        boolean hasMoneySlime = checkForMoneySlime();
        //updateTimer(delta);
        //HUD.updateMoney(delta, numberOfMoneySlimes, hasMoneySlime);
        HUD.moneyLabel((int) money);
        updateBullets();
        updateTroops();
        updateDefenses();
    }

    public void renderGameLogic() {
        if (selector != null) {
            Vector2 pos = fVp.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            selector.setPosition(pos.x, pos.y);
            selector.hitbox.set(pos.x - 1, pos.y - 1, selector.TROOP_WIDTH, selector.TROOP_HEIGHT);
            selector.render();
        }
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

    private void updateTroops() {
        for (BaseTroop troop : troopArr) {
            if (troop instanceof Slime) {
                troop.update(fVp, troopArr);
            } else {
                troop.update(fVp, troopArr, tempArr, boulderReached);
            }
        }
    }

    private void updateDefenses() {
        for (Defense defense : defenses) {
            defense.instakill(tempArr, troopArr);
        }
    }

    private void updateBullets() {
        for (Bullet bullet : bulletArr) {
            bullet.update(troopArr, tempArr, bulletTemp);
        }
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

    public boolean placeTroop(BaseTroop troop, int cellId){
        GridCell cell = grid.getCellById(cellId);
        if(cell != null && cell.placeTroop(troop)){
            troopArr.add(troop);
            return true;
        };
        return false;
    }

    public com.MenuScreens.HUD getHUD() {
        return HUD;
    }


    public void setTeam(TeamScreen.Team t){
        this.team = t;
        if (team == SLIME) st.addActor(HUD.getSlimeTable());
        else st.addActor(HUD.getBoulderTable());
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

    public void show() {
    }

    @Override
    public void dispose() { //se llama cuando se cierra el programa
        game.dispose();
        map.dispose();
        mapRenderer.dispose();
        mainSong.dispose();
        st.dispose();
    }

}