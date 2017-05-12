package feup.lpoo.reversi.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.HashMap;
import java.util.Map;

import feup.lpoo.reversi.presenter.CellPresenter;

/**
 * Created by antonioalmeida on 02/05/2017.
 */

public class CellView extends Actor {
    private static final Texture black = new Texture(Gdx.files.internal("black.png"));
    private static final Texture white = new Texture(Gdx.files.internal("white.png"));

    private static final Texture angry = new Texture(Gdx.files.internal("angry.png"));
    private static final Texture blush = new Texture(Gdx.files.internal("blush.png"));
    private static final Texture gem = new Texture(Gdx.files.internal("gem.png"));

    private static Map<Character, Texture> ICONS = new HashMap<Character, Texture>();

    private CellPresenter presenter;

    static {
        ICONS.put('B', angry);
        ICONS.put('W', blush);
        ICONS.put('X', gem);
        ICONS.put('-', null);
    }

    private Texture getIcon(char piece) {
        Texture result = ICONS.get(piece);
        return result;
    }

    private final Texture color;
    private Texture icon;

    //The coordinates relative to it's parent that the cell is drawn
    private int actorX;
    private int actorY;

    public CellView(int color, int x, int y, int boardX, int boardY) {
        if(color == 0)
            this.color = white;
        else
            this.color = black;

        actorX = x;
        actorY = y;
        setBounds(actorX, actorY, black.getWidth(), black.getHeight());

        presenter = new CellPresenter(boardX, boardY);
        update();
        addListeners();
    }

    public void addListeners() {
        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                presenter.handleInput();
                return true;
            }
        });
    }

    public void update() {
        char currentPiece = presenter.getCurrentPiece();
        icon = getIcon(currentPiece);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(color,actorX,actorY);

        if(icon != null)
            batch.draw(icon, actorX, actorY);
    }
}
