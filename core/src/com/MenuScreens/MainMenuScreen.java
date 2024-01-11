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

public class MainMenuScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final OrthographicCamera cam;
    private final Table MainTable;
    public TDGame TDGame;
    private final Music menuSong;
    private final ScreenViewport vp;

    public MainMenuScreen(final TDGame TDGame) {
        this.TDGame = TDGame;
        Texture bg = TDGame.assets.bgTxT;
        Image bgImg = new Image(bg);
        skin = Assets.manager.get(SKIN);
        menuSong = TDGame.assets.selSong;

        cam = new OrthographicCamera();
        vp = new ScreenViewport(cam);
        stage = new Stage(vp);
        cam.setToOrtho(false,800, 600);
        MainTable = new Table();

        //menuSong.play();
        //menuSong.setVolume(.1f);
        Gdx.input.setInputProcessor(stage);
        bgImg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(bgImg);
        stage.addActor(MainTable);

        setTablePos(bgImg);
        createButtons();

    }
    private void setTablePos(Image bgImg) {
        MainTable.setFillParent(true);
        MainTable.setPosition(0,0);
        //MainTable.setDebug(true);

        MainTable.add(bgImg).width((float) Gdx.graphics.getWidth() /1.5f).
                height((float) Gdx.graphics.getHeight() /5.5f).
                padBottom((float) Gdx.graphics.getHeight() /4.5f);

        MainTable.row();

    }
    private void createButtons() {
        addButton("Jugar").
                addListener(
                        new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y){
                                TDGame.setScreen(new GameScreen(TDGame, TeamSelScreen.Team.BOULDER));
                                menuSong.dispose();
                            }
                        });

        addButton("Donar").
                addListener(
                        new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y){
                                String websiteURL = "https://ko-fi.com/goatedjowi";
                                Gdx.net.openURI(websiteURL);

                            }
                        }
                );

        addButton("Salir").
                addListener(
                        new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y){
                                Gdx.app.exit();

                            }
                        });
    }
    private TextButton addButton(String name){
        TextButton button = new TextButton(name, skin);
        MainTable.add(button).
                width((float) Gdx.graphics.getWidth() / 3).
                height((float) Gdx.graphics.getHeight() / 8).padBottom(10);
        MainTable.row();
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.5f,.7f,.7f,1);
        cam.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        TDGame.batch.setProjectionMatrix(cam.combined);

        stage.act(Gdx.graphics.getDeltaTime()); //el act registra clicks, mov mouse, etc.
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        vp.update(width,height,true);
        //cam.position.set((float) width / 2, (float) height / 2, 0);
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
        menuSong.dispose();
    }
}