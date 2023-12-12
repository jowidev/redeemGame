package com.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
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
import com.mygdx.game.Gamemap;

import static com.mygdx.game.Assets.SKIN;
import static com.mygdx.game.Gamemap.batch;

public class TeamSelScreen implements Screen {
    private final OrthographicCamera cam;
    private final Stage stage;
    public Skin skin;
    public Music selSong;
    private Table teamTable;
    public TeamSelScreen(final Gamemap gamemap) {
        skin = Assets.manager.get(SKIN);
        Texture bando = gamemap.assets.bando;
        Image bandoImg = new Image(bando);
        selSong = gamemap.assets.trumpsong;
        cam = new OrthographicCamera();
        cam.setToOrtho(false,800, 600);
        final Viewport viewport = new ScreenViewport(cam);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        teamTable = new Table();
        stage.addActor(teamTable);
        teamTable.setFillParent(true);
        teamTable.setPosition(0,0);
        teamTable.add(bandoImg);
        teamTable.row();
        selSong.setVolume(.1f);

        addButton("Boulders").center().
                addListener(
                        new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y){
                                gamemap.setScreen(new GameScreen(gamemap, Team.BOULDER));
                                selSong.dispose();
                            }
                        }
                );
        addButton("Slimes").center().
                addListener(
                        new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y){
                                gamemap.setScreen(new GameScreen(gamemap, Team.SLIME)); //aca se lo pasa pq lo usa el boulder
                                selSong.dispose();

                            }
                        });
        teamTable.row();


        //teamTable.setDebug(true);

    }
    public enum Team {
        SLIME, BOULDER;
    }
    private TextButton addButton(String name){
        TextButton button = new TextButton(name, skin);
        teamTable.add(button).
                width((float) Gdx.graphics.getWidth() / 5)
                .height((float) Gdx.graphics.getHeight() /4);
        return button;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.4f,.5f,.7f,1);
        cam.update();
        selSong.play();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        batch.setProjectionMatrix(cam.combined);
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
