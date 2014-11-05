package de.cubeisland.games.dhbw.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class CardLoader {

	private static BitmapFont font;

	// fills the front Texture of a Card with Name, Image and Description
	public static Texture loadTexture() {
		Texture cardfront;

		// Bild als Pixmap holen, Pixmap darauf zeichnen, aus der Pixmap eine Textur machen

		Pixmap background = new Pixmap(Gdx.files.internal("cards/cardfront.png"));
		background.setFilter(Pixmap.Filter.NearestNeighbour);
		Pixmap image = new Pixmap(Gdx.files.internal("images/derExmatrikulator.png"));

		background.drawPixmap(image, 25, 49);

		// font = new BitmapFont(Gdx.files.internal("text.fnt"));
		// font.setScale(.25f, -.25f);
		// font.draw(background, "ABCDEFG", 10, 10);

		/*
		FrameBuffer fbo;
		SpriteBatch fb = new SpriteBatch();

		fb.enableBlending();
		Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);

		Gdx.gl.glClearColor(1, 0, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		fbo = new FrameBuffer(Pixmap.Format.RGBA8888, background.getWidth(), background.getHeight(), false);
		fbo.begin();
		fb.begin();

		// draw the card background
		fb.draw(background, 0, 0);

		// draw the card image
		fb.draw(image, 10, 10);

		fb.end();
		fbo.end();

		Sprite mySprite = new Sprite(fbo.getColorBufferTexture());

		// mySprite.rotate(180);
		// mySprite.setOrigin(mySprite.getWidth()/2, mySprite.getHeight()/2);

		cardfront = mySprite.getTexture();

		// cardfront = new Texture("cards/cardfront.png");
		*/

		// cardfront = new Texture("cards/cardfront.png");

		cardfront = new Texture(background);

		return cardfront;
	}
}