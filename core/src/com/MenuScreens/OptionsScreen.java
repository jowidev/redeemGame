package com.MenuScreens;

import static com.mygdx.game.Assets.SKIN;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Assets;
import com.mygdx.game.TDGame;

public class OptionsScreen implements Screen {
    private final TDGame game;
    private final Stage stage;
    private final Table table;
    private final Skin skin;
    private final Image img;

    public OptionsScreen(final TDGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        table = new Table();
        stage.addActor(table);
        table.setFillParent(true);
        skin = Assets.manager.get(SKIN);
        img = new Image(game.assets.mmBg);
        img.setPosition(0, 0);
        img.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.input.setInputProcessor(stage);

        Stack stack = new Stack();
        stack.add(img); // Fondo
        stack.add(createUI());
        table.add(stack).expand().fill();
    }

    private Table createUI() {
        Table uiTable = new Table();
        final Slider slider = new Slider(0, 100, 1, false, skin);

        addTextSlime("1. MoneySlime Cost 15").addListener(new ClickListener() {

        });
        addTextSlime("2. ShooterSlime Cost 25").addListener(new ClickListener() {

        });
        addTextSlime("3. ShieldSlime Cost 20").addListener(new ClickListener() {

        });
        addTextBoulder("8. BasicBoulder Cost 15").addListener(new ClickListener() {

        });
        addTextBoulder("9. FastBoulder Cost 25").addListener(new ClickListener() {

        });
        addTextBoulder("0. ArmoredBoulder Cost 35").addListener(new ClickListener() {

        });

        addButton("Volver").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        slider.setValue(game.assets.mainsong2.getVolume() * 100);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                float volume = slider.getValue() / 100.0f;
                game.setMusicVolume(volume);
            }
        });
        addButton("Volver").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });


        // Añadir componentes a la tabla de la interfaz de usuario
        uiTable.add(slider).align(Align.center).padBottom(50).fillX();
        uiTable.row();

        uiTable.add(addTextSlime("1. MoneySlime Cost 15")).align(Align.center).padBottom(10).padRight(20);
        uiTable.add(addTextBoulder(" BasicBoulder Cost 15")).align(Align.center).padBottom(10).padLeft(20).row();
        uiTable.add(addTextSlime("2. ShooterSlime Cost 25")).align(Align.center).padBottom(10).padRight(20);
        uiTable.add(addTextBoulder(" FastBoulder Cost 25")).align(Align.center).padBottom(10).padLeft(20).row();
        uiTable.add(addTextSlime("3. ShieldSlime Cost 20")).align(Align.center).padBottom(10).padRight(20);
        uiTable.add(addTextBoulder(" ArmoredBoulder Cost 35")).align(Align.center).padBottom(10).padLeft(20).row();
        uiTable.add(addButton("Volver")).align(Align.center).padTop(10).fillX().row();

        return uiTable;
    }
    private TextButton addButton(String name) {
        TextButton button = new TextButton(name, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        return button;
    }
    private TextButton addTextSlime(String name) {
        TextButton Text = new TextButton(name, skin);

        return Text;
    }
    private TextButton addTextBoulder(String name) {
        TextButton Text = new TextButton(name, skin);

        return Text;
    }


    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();

    }
}