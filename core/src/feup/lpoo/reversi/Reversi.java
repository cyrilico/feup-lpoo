package feup.lpoo.reversi;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import feup.lpoo.reversi.model.PlayerModel;
import feup.lpoo.reversi.view.MainMenuView;

public class Reversi extends Game {

	private PlayServices playServices;

	private SpriteBatch batch;
	private AssetManager assetManager;
	private Viewport viewport;

	private Skin skin;
	private TextureAtlas atlas;

	public static Color BACKGROUND_COLOR = new Color(0.38f, 0.50f, 0.56f, 1);
	public static Color PRIMARY_COLOR = new Color(88, 164, 176, 255);
	public static Color SECONDARY_COLOR = new Color(255, 164, 0, 255);

	public Reversi(PlayServices ps) {
		this.playServices = ps;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
        assetManager = new AssetManager();
		viewport = new ExtendViewport(512, 854);

		//atlas = new TextureAtlas("reversi-cyan/reversi-cyan.atlas");
		//skin = new Skin(Gdx.files.internal("reversi-cyan/reversi-cyan.json"), atlas);

		atlas = new TextureAtlas("retro-test/retro-test.atlas");
		skin = new Skin(Gdx.files.internal("retro-test/retro-test.json"), atlas);
		loadAssets();

		setScreen(new MainMenuView(this));
	}

	@Override
	public void render () {
        super.render();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
        assetManager.dispose();
        atlas.dispose();
		skin.dispose();
	}

	private void loadAssets() {
		assetManager.load("tile.png", Texture.class);
		assetManager.load("white.png", Texture.class);
		assetManager.load("black.png", Texture.class);
		assetManager.load("hint.png", Texture.class);
		assetManager.load("paddle.png", Texture.class);
		assetManager.finishLoading();
	}

	public Viewport getViewport() {
		return viewport;
	}

	public Skin getSkin() {
		return skin;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public PlayServices getPlayServices() {
		return playServices;
	}
}
