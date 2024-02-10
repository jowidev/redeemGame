package com.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Assets;
import com.mygdx.game.GameScreen;
import com.mygdx.game.TDGame;

import static com.mygdx.game.Assets.SKIN;
import static com.mygdx.game.TDGame.batch;

public class TeamScreen implements Screen {
    private final OrthographicCamera cam;
    private final Stage stage;
    public Skin skin;
    public Music selSong;
    private Table teamTable;
    public TDGame game;
    private ScreenViewport sVp;
    private Image bg;
    public TeamScreen(final TDGame game) {
        this.game = game;
        skin = Assets.manager.get(SKIN);

        Texture bg2 = game.assets.mmBgg;
        bg = new Image(bg2);
        cam = new OrthographicCamera();
        sVp = new ScreenViewport(cam);
        stage = new Stage(sVp, batch);
        bg.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        cam.setToOrtho(false,800, 600);
        Gdx.input.setInputProcessor(stage);
        posTeamTable();
        stage.addActor(bg);
        stage.addActor(teamTable);
        createButtons();

    }
    public enum Team {
        SLIME, BOULDER;
    }

    private void posTeamTable() {
        teamTable = new Table();
        teamTable.setFillParent(true);
        teamTable.setPosition(0,0);
        //teamTable.setDebug(true);
    }
    private void createButtons() {
        addButton("Slimes").left().
                addListener(
                        new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y){
                                game.setScreen(new GameScreen(game, Team.SLIME));

                            }
                        });
        addButton("Boulders").
                addListener(
                        new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y){
                                game.setScreen(new GameScreen(game, Team.BOULDER));
                            }
                        }
                );
    }
    private TextButton addButton(String name){
        TextButton button = new TextButton(name, skin);
        teamTable.add(button).
                width((float) Gdx.graphics.getWidth() /7.5f)
                .height((float) Gdx.graphics.getHeight() /5f).center();
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.4f,.5f,.7f,1);
        cam.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        batch.setProjectionMatrix(cam.combined);
    }

    @Override
    public void resize(int width, int height) {
        sVp.update(width, height,true);
    }

    @Override
    public void show() {

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
        stage.dispose();
    }
}
