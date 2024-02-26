package com.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;
import com.mygdx.game.TDGame;
import java.awt.*;

import javax.swing.*;

import static com.mygdx.game.Assets.SKIN;

public class GameOverScreen implements Screen {
    private final Skin skin;
    private final OrthographicCamera cam;
    private Stage stage;
    private Table table;
    private TDGame game;
    public GameOverScreen(TeamScreen.Team team, final TDGame game) {
        cam = new OrthographicCamera();
        skin = Assets.manager.get(SKIN);
        this.game = game;
        stage = new Stage();
        Label label = new Label("El equipo " + team+" Gana!", skin);
        table = new Table();
        label.setFontScale(1.5f,1.25f);
        table.setPosition(0,0);
        table.setFillParent(true);
        table.add(label);
        table.row();
        addButton("Volver").
                addListener(
                        new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y){
                                game.setScreen(new MainMenuScreen(game));
                            }
                        });
        label.setPosition((float) Gdx.graphics.getWidth() /2-label.getWidth()/2, (float) Gdx.graphics.getHeight() /2); // Set position as per your requirement
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    private TextButton addButton(String name){
        TextButton button = new TextButton(name, skin);
        table.add(button).
                width((float) Gdx.graphics.getWidth() / 3.5f).
                height((float) Gdx.graphics.getHeight() / 8).padBottom(10);
        table.row();
        return button;
    }
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(.5f,.3f,.7f,1);
        cam.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        TDGame.batch.setProjectionMatrix(cam.combined);
        stage.act(Gdx.graphics.getDeltaTime()); //el act registra clicks, mov mouse, etc.

        stage.draw();
        //stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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