package com.chesskabaddi.game;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class GameScreen implements Screen {
    final ChessKabaddi game;

    Texture dropImage;
    Texture bucketImage;
    Texture backgroundImage;
    Texture knightImage;
    Texture kingImage;
    Texture bishopImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Rectangle background;
    Rectangle king;
    Rectangle bishop;
    Rectangle knight1;
    Rectangle knight2;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;

    public GameScreen(final ChessKabaddi game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        backgroundImage = new Texture(Gdx.files.internal("background.png"));
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        knightImage = new Texture(Gdx.files.internal("knight.png"));
        bishopImage = new Texture(Gdx.files.internal("bishop.png"));
        kingImage = new Texture(Gdx.files.internal("king.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);

        background = new Rectangle();
        background.x = 0; // center the bucket horizontally
        background.y = 0; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        background.width = 1200;
        background.height = 800;

        // create a Rectangle to logically represent the bucket
        knight1 = new Rectangle();
        knight1.x = 600; // center the bucket horizontally
        knight1.y = 0 ; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        knight1.width = 200;
        knight1.height = 200;

        knight2 = new Rectangle();
        knight2.x = 800; // center the bucket horizontally
        knight2.y = 0 ; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        knight2.width = 200;
        knight2.height = 200;

        bishop = new Rectangle();
        bishop.x = 1000; // center the bucket horizontally
        bishop.y = 0 ; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bishop.width = 200;
        bishop.height = 200;

        king = new Rectangle();
        king.x = 0; // center the bucket horizontally
        king.y = 600 ; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        king.width = 200;
        king.height = 200;

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        spawnRaindrop();

    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 1200 - 64);
        raindrop.y = 800;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.batch.draw(backgroundImage,background.x,background.y,background.width,background.height);
        game.batch.draw(kingImage, king.x, king.y, king.width, king.height);
        game.batch.draw(knightImage, knight1.x, knight1.y, knight1.width, knight1.height);
        game.batch.draw(knightImage, knight2.x, knight2.y, knight2.width, knight2.height);
        game.batch.draw(bishopImage, bishop.x, bishop.y, bishop.width, bishop.height);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
//            bucket.x = touchPos.x - 64 / 2;
        }
//        if (Gdx.input.isKeyPressed(Keys.LEFT))
//            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
//        if (Gdx.input.isKeyPressed(Keys.RIGHT))
//            bucket.x += 200 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
//        if (bucket.x < 0)
//            bucket.x = 0;
//        if (bucket.x > 1200 - 64)
//            bucket.x = 1200 - 64;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
//              if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                iter.remove();
//            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

}