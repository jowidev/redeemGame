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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Assets;
import com.mygdx.game.GameScreen;
import com.mygdx.game.TDGame;

import static com.mygdx.game.Assets.SKIN;
import static com.mygdx.game.TDGame.batch;

public class TeamSelScreen implements Screen {
    private final OrthographicCamera cam;
    private final Stage stage;
    public Skin skin;
    public Music selSong;
    private Table teamTable;
    public TDGame TDGame;
    private ScreenViewport sVp;
    public TeamSelScreen(final TDGame TDGame) {
        this.TDGame = TDGame;
        skin = Assets.manager.get(SKIN);
        Texture bando = TDGame.assets.bando;
        Image bandoImg = new Image(bando);
        selSong = TDGame.assets.trumpsong;

        cam = new OrthographicCamera();
        sVp = new ScreenViewport(cam);
        stage = new Stage(sVp, batch);


        cam.setToOrtho(false,800, 600);
        Gdx.input.setInputProcessor(stage);
        posTeamTable(bandoImg);
        stage.addActor(teamTable);
        //selSong.setVolume(.1f);

        createButtons();
    }
    public enum Team {
        SLIME, BOULDER;
    }

    private void posTeamTable(Image bandoImg) {
        teamTable = new Table();
        teamTable.setFillParent(true);
        teamTable.setPosition(0,0);
        teamTable.add(bandoImg);
        teamTable.row();

        //teamTable.setDebug(true);
    }
    private void createButtons() {
        addButton("Slimes").
                addListener(
                        new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y){
                                TDGame.setScreen(new GameScreen(TDGame, Team.SLIME));
                                selSong.dispose();

                            }
                        });
        addButton("Boulders").
                addListener(
                        new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y){
                                TDGame.setScreen(new GameScreen(TDGame, Team.BOULDER));
                                selSong.dispose();
                            }
                        }
                );
    }
    private TextButton addButton(String name){
        TextButton button = new TextButton(name, skin);
        teamTable.add(button).
                width((float) Gdx.graphics.getWidth() / 6)
                .height((float) Gdx.graphics.getHeight() /6.5f);
        teamTable.row();
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
