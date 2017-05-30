package feup.lpoo.reversi.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import feup.lpoo.reversi.Reversi;
import feup.lpoo.reversi.presenter.ai.EasyMoveStrategy;
import feup.lpoo.reversi.presenter.ai.HardMoveStrategy;
import feup.lpoo.reversi.presenter.ai.MediumMoveStrategy;

/**
 * Created by antonioalmeida on 29/05/2017.
 */

public class MultiplayerMenuView extends ScreenAdapter {
    private Reversi game;

    private Stage stage;
    private Table buttonTable;
    private Table titleTable;
    private Table backButtonTable;

    private TextButton localGameButton;
    private TextButton onlineGameButton;
    private TextButton checkGamesButton;
    private TextButton backButton;

    //Labels
    private Label mainTitle;

    public MultiplayerMenuView(Reversi game) {
        this.game = game;
        stage = new Stage(game.getViewport(), game.getBatch());

        addTitle();
        addOptionsButtons();
        addBackButton();
        addListeners();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(Reversi.BACKGROUND_COLOR.r, Reversi.BACKGROUND_COLOR.g, Reversi.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        stage.draw();
    }

    private void addTitle() {
        mainTitle = new Label("   Multiplayer   ", game.getSkin());
        mainTitle.setFontScale(1);

        titleTable = new Table();
        titleTable.setFillParent(true);
        titleTable.top();
        titleTable.add(mainTitle).center().padTop(120);

        stage.addActor(titleTable);
    }

    private void addOptionsButtons() {
        buttonTable = new Table();
        buttonTable.bottom();
        buttonTable.setFillParent(true);

        localGameButton = new TextButton("\n  Same Screen  \n", game.getSkin());
        onlineGameButton = new TextButton("\n  Online  \n", game.getSkin());
        checkGamesButton = new TextButton("\n  Check Games  \n", game.getSkin());

        buttonTable.add(localGameButton).center().padBottom(40);
        buttonTable.row();
        buttonTable.add(onlineGameButton).center().padBottom(40);
        buttonTable.row();
        buttonTable.add(checkGamesButton).center().padBottom(40);

        stage.addActor(buttonTable);
    }

    private void addBackButton(){
        backButtonTable = new Table();
        backButtonTable.top();
        backButtonTable.setFillParent(true);

        backButton = new TextButton("\n BACK \n", game.getSkin());
        //backButton.setTransform(true);
        //backButton.scaleBy(0.5f);
        backButton.setColor(Reversi.SECONDARY_COLOR);

        backButtonTable.add(backButton).right();

        stage.addActor(backButtonTable);
    }

    private void addListeners() {
        localGameButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameInfo info = new GameInfo(false, false);
                game.setScreen(new GameView(game, info));
                return true;
            }
        });

        onlineGameButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.getPlayServices().quickMatch();
                return true;
            }
        });

        checkGamesButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.getPlayServices().checkGames();
                return true;
            }
        });

        backButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuView(game));
                return true;
            }
        });
    }
}
