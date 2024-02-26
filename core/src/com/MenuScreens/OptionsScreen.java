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
    private final Table MainTable;

    public OptionsScreen(final TDGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.table = new Table();
        this.stage.addActor(table);
        this.table.setFillParent(true);
        this.skin = Assets.manager.get(SKIN);
        this.img = new Image(game.assets.mmBg);
        this.img.setPosition(0, 0);
        this.img.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.MainTable = new Table();

        Gdx.input.setInputProcessor(stage);

        // Organizar los elementos en capas con Stack dentro de la tabla
        Stack stack = new Stack();
        stack.add(img); // Fondo
        stack.add(createUI());
        table.add(stack).expand().fill();
    }

    private Table createUI() {
        Table uiTable = new Table();
        final Slider slider = new Slider(0, 100, 1, false, skin);

        addText("1. MoneySlime Cost 15").addListener(new ClickListener() {

        });
        addText("2. ShooterSlime Cost 25").addListener(new ClickListener() {

        });
        addText("3. ShieldSlime Cost 20").addListener(new ClickListener() {

        });
        addText("8. BasicBoulder Cost 15").addListener(new ClickListener() {

        });
        addText("9. FastBoulder Cost 25").addListener(new ClickListener() {

        });
        addText("0. ArmoredBoulder Cost 35").addListener(new ClickListener() {

        });

        // Listener para el botón de volver
        addButton("Volver").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        // Listener para el slider
        slider.setValue(game.assets.mainsong2.getVolume() * 100);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                float volume = slider.getValue() / 100.0f;
                // Llamar al método en TDGame para establecer el volumen de la música
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
        uiTable.add(slider).colspan(2).align(Align.center).padBottom(50).fillX().row();
        uiTable.row();

        uiTable.add(addText("1. MoneySlime Cost 15")).align(Align.center).padBottom(10).padRight(20);
        uiTable.add(addText(" BasicBoulder Cost 15")).align(Align.center).padBottom(10).padLeft(20).row();
        uiTable.add(addText("2. ShooterSlime Cost 25")).align(Align.center).padBottom(10).padRight(20);
        uiTable.add(addText(" FastBoulder Cost 25")).align(Align.center).padBottom(10).padLeft(20).row();
        uiTable.add(addText("3. ShieldSlime Cost 20")).align(Align.center).padBottom(10).padRight(20);
        uiTable.add(addText(" ArmoredBoulder Cost 35")).align(Align.center).padBottom(10).padLeft(20).row();
        uiTable.add(addButton("Volver")).colspan(2).align(Align.center).padTop(10).fillX().row();

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
    private TextButton addText(String name) {
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