package ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * This class is used to define the main menu layout.
 * @author Connor Stewart
 */
public class MenuScreen implements Screen {
	
	/** The singleton instance of this class. */
	private final static MenuScreen instance = new MenuScreen();
	
	/** The stage to display elements. */
	private Stage stage;
	
	/** Buttons. */
	private TextButton btnPlay, btnQuit;
	
	/** {@link #getInstance()} should be used to obtain an instance of this class.  */
	private MenuScreen(){};

	@Override
	public void show() {
		//make background
		Image background = new Image(new Texture(Gdx.files.internal("space.png")));
		background.setFillParent(true);
		background.setPosition(0, 0);
		
		//load the font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Star Trek Enterprise Future.ttf"));
		
		FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
		fontParameter.size = 100; //setting font size
		
		//creating the font based on the font parameters
		BitmapFont font = generator.generateFont(fontParameter);
		
		//dispose the generator since its finished being used
		generator.dispose();
		
		//set style for buttons
		TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
		buttonStyle.font = font;
		
		//initialising the buttons
		btnPlay = new TextButton("Play", buttonStyle);
		btnPlay.setPosition(Gdx.graphics.getWidth() / 2 - btnPlay.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 20);
		btnQuit = new TextButton("Quit", buttonStyle);
		btnQuit.setPosition((btnPlay.getX() + btnQuit.getWidth() / 8) - 15, btnPlay.getY() - btnPlay.getHeight() + 20);
		
		//initialising the stage which will stretch
		stage = new Stage(new StretchViewport(900, 700));
		
		//initialising the lose label
		Label lblTitle = new Label("Space Defence", new Label.LabelStyle(font, Color.WHITE));
		lblTitle.setPosition((Gdx.graphics.getWidth() / 2) - lblTitle.getWidth() / 2, Gdx.graphics.getHeight() - 100);
		
		//allowing the stage to receive input events
		Gdx.input.setInputProcessor(stage);
		
		//adding actors to the stage
		stage.addActor(background);
		stage.addActor(lblTitle);
		stage.addActor(btnPlay);
		stage.addActor(btnQuit);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta); //update actors
		stage.draw(); //draw actors
		
		//goto the game screen if the play button is pressed
		if (btnPlay.isPressed())
			MainGame.changeScreen(MainGame.GAME_SCREEN);
		
		if (btnQuit.isPressed())
			Gdx.app.exit();
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
	}

	/**
	 * @return singleton instance of this class
	 */
	public static Screen getInstance() {
		return instance;
	}

}
