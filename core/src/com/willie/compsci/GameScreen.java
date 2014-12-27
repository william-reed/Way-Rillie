package com.willie.compsci;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The actual game screen
 */
public class GameScreen implements Screen
{

	private Game game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Character duck, mojo;
	private Character[] characters;
	private Matrix4 debugMatrix;
	private float stateTime;
	private Texture background;

	public GameScreen(Game game)
	{
		this.game = game;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(w, h);
		// translate camera so the bottom left pixel is (0,0)
		camera.translate(w / 2, h / 2);
		camera.update();

		initBox();
		// create characters at opposite ends of screen
		float[] duckVertices = new float[]{0, 15, 0, 28, 26, 64, 40, 64, 64, 28, 64, 15, 32, 0};
		duck = new Character(100, 125, 10, true, new Texture("duckSheet.png"), new CharacterController(Keys.W, Keys.A, Keys.D, Keys.S, Keys.SHIFT_LEFT), world, duckVertices);
		float[] mojoVertices = new float[]{0, 0, 0, 64, 64, 64, 64, 0};
		mojo = new Character(700, 125, 10, false, new Texture("mojoSheet.png"), new CharacterController(Keys.UP, Keys.LEFT, Keys.RIGHT, Keys.DOWN, Keys.CONTROL_RIGHT), world, mojoVertices);
		characters = new Character[2];
		characters[0] = duck;
		characters[1] = mojo;

		// create spritebatch and camera
		batch = new SpriteBatch();
		
		stateTime = 0f;
		
		background = new Texture("bkg.png");
	}

	private World world;
	private Box2DDebugRenderer debugRenderer;

	private void initBox()
	{
		Box2D.init();
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();

		//bottom 
		new RectangularBoundry(world, camera.viewportWidth / 2, 10, camera.viewportWidth, 10);
		//left
		new RectangularBoundry(world, 5, camera.viewportHeight / 2, 20, camera.viewportHeight);
		//right
		new RectangularBoundry(world, camera.viewportWidth - 5, camera.viewportHeight / 2, 20, camera.viewportHeight);
		//top
		new RectangularBoundry(world, camera.viewportWidth / 2, camera.viewportHeight - 5, camera.viewportWidth, 8);

		//platform in middle of screen
		//TODO: probably will wind up getting rid of this or changing it eventually
		new RectangularBoundry(world, camera.viewportWidth / 2, 190, 200, 12);
	}

	public void update(float delta)
	{
		for (Character c : characters)
			c.update(delta);
	}

	public void draw()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		{
			batch.draw(background, 0, 0);
			for (Character c : characters)
				c.draw(batch, stateTime);
		}
		batch.end();
		debugMatrix = batch.getProjectionMatrix().cpy().scale(WayRillie.PIXELS_TO_METERS, WayRillie.PIXELS_TO_METERS, 0);
		debugRenderer.render(world, debugMatrix);
	}

	@Override
	public void render(float delta)
	{
		update(delta);
		stateTime += Gdx.graphics.getDeltaTime();
		draw();
		world.step(1 / 60f, 6, 2);
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void show()
	{
	}

	@Override
	public void hide()
	{
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}

	@Override
	public void dispose()
	{
	}

}
