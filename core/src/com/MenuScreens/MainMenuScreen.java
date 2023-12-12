package com.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.mygdx.game.Gamemap;

import static com.mygdx.game.Assets.SKIN;

public class MainMenuScreen implements Screen { //implements screen?
    private final Stage stage;
    private final Skin skin;
    private final OrthographicCamera cam;
    private final Table MainTable;
    public Gamemap gamemap;
    private Music menuSong;

    public MainMenuScreen(final Gamemap gamemap) {
        Texture bg = gamemap.assets.bgTxT;
        Image bgImg = new Image(bg);
        skin = Assets.manager.get(SKIN);
        this.gamemap = gamemap;
        cam = new OrthographicCamera();
        cam.setToOrtho(false,800, 600);
        menuSong = gamemap.assets.selSong;
        menuSong.play();
        menuSong.setVolume(.1f);


        Viewport viewport = new ScreenViewport(cam);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        bgImg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(bgImg);

        MainTable = new Table();
        stage.addActor(MainTable);
        MainTable.setFillParent(true);

        MainTable.setPosition(0,0);
        //MainTable.setDebug(true);
        MainTable.add(bgImg).width((float) Gdx.graphics.getWidth() /1.5f).height((float) Gdx.graphics.getHeight() /5.5f).padBottom((float) Gdx.graphics.getHeight() /4.5f);

        MainTable.row();
        addButton("Jugar").
                addListener(
                        new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y){
                            gamemap.setScreen(new TeamSelScreen(gamemap));
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
        MainTable.add(button).width((float) Gdx.graphics.getWidth() / 3).height((float) Gdx.graphics.getHeight() /8).padBottom(10);
        MainTable.row();
        return button;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.4f,.5f,.7f,1);
        cam.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gamemap.batch.setProjectionMatrix(cam.combined);

        stage.act(Gdx.graphics.getDeltaTime()); //el act registra clicks, mov mouse, boludeces
        stage.draw();
        handleInput();
    }

    public void handleInput () {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

    }
    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
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
        stage.dispose();
        menuSong.dispose();
    }
}