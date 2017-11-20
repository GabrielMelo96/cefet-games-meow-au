package br.cefetmg.games.minigames;

import br.cefetmg.games.graphics.MultiAnimatedSprite;
import br.cefetmg.games.minigames.util.DifficultyCurve;
import br.cefetmg.games.minigames.util.MiniGameState;
import br.cefetmg.games.minigames.util.MiniGameStateObserver;
import br.cefetmg.games.minigames.util.TimeoutBehavior;
import br.cefetmg.games.screens.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer.Task;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;

import java.util.HashMap;
import java.util.Random;

import static br.cefetmg.games.Config.WORLD_HEIGHT;
import static br.cefetmg.games.Config.WORLD_WIDTH;

public class JetRat extends MiniGame {

    private Calopsita mouse;
    private Texture mouseTexture;
    private Texture tubeTexture;
    private Texture cattubeTexture;
    private Texture bg1;
    private Array<Tube> enemies;

    // variáveis do desafio - variam com a dificuldade do minigame
    private float minimumEnemySpeed;
    private float screenWidth;
    private float screenHeight;
    private float posX, posY;
    int srcX, troca;
    float velocidade,contador;
    private Sound meon;

    public JetRat(BaseScreen screen,
            MiniGameStateObserver observer, float difficulty) {
        super(screen, observer, difficulty, 10f,
                TimeoutBehavior.WINS_WHEN_MINIGAME_ENDS);
    }

    @Override
    protected void onStart() {
        troca = 0;
        mouseTexture = assets.get("jet-rat/jatmouse.png", Texture.class);
        cattubeTexture = assets.get("jet-rat/tubecat.png", Texture.class);
        bg1 = assets.get("jet-rat/background.png", Texture.class);
        bg1.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        tubeTexture = assets.get("jet-rat/tube.png", Texture.class);
        meon = assets.get("jet-rat/meon.mp3", Sound.class);
        mouse = new Calopsita(mouseTexture);
        mouse.setScale(0.5f);
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        enemies = new Array<Tube>();

        posX = viewport.getScreenWidth() * 0.4f;
        posY = viewport.getScreenHeight() * 0.5f;
        timer.scheduleTask(new Task() {
            @Override
            public void run() {
                spawnEnemy();
            }

        }, 0, (float) Math.random() + 0.7f);
        srcX = 0;
        velocidade = -1 * WORLD_HEIGHT * 0.0005f;
        contador=0;
        meon.play(0.2f);
    }

    private void spawnEnemy() {

        float Ddwown = (float) Math.random() * viewport.getScreenHeight() * 0.3f;

        Vector2 tubeGoal = new Vector2(-screenWidth, Ddwown);
        Vector2 tubePosition = new Vector2();

        Vector2 tubeSpeed = tubeGoal
                .sub(tubePosition)
                .nor()
                .scl(this.minimumEnemySpeed);
        Tube enemy = new Tube(cattubeTexture);
        enemy.setSize(new Random().nextInt(4));

        enemy.setPosition(WORLD_WIDTH, 60 * enemy.getSize());
        enemy.setSpeed(tubeSpeed);
        enemies.add(enemy);
    }

    @Override
    protected void configureDifficultyParameters(float difficulty) {
        this.minimumEnemySpeed = DifficultyCurve.LINEAR
                .getCurveValueBetween(difficulty, 120, 220);
    }

    @Override
    public void onHandlePlayingInput() {

        // atualiza a posição do alvo de acordo com o mouse
        Vector3 position;
        position = new Vector3(posX, posY, 0);
        viewport.unproject(position);
        mouse.setCenter(position.x, position.y);
        for (Tube tubes : this.enemies) {
            if (mouse.getY() + 70 <= tubes.getHeight() + tubes.getSize() * 60
                    && (mouse.getX() > tubes.getX() - 80 && mouse.getX() < tubes.getX() + 80)) {

                super.challengeFailed();
                meon.stop();
                tubes.changePicture();
            }
        }

    }

    @Override
    public void onUpdate(float dt) {
        if (this.getState().equals(MiniGameState.PLAYER_SUCCEEDED)) {
            meon.stop();
        }
        mouse.update(dt);
        srcX += 5;
        /*if (aceleracao > -1 * WORLD_HEIGHT * 0.00009f);
        aceleracao -= WORLD_HEIGHT * 0.00005f; //gravidade
        */
        if (posY < screenHeight + 2) {
           // posY -= velocidade ;//2.5; 1;
            posY  += WORLD_HEIGHT * 0.005f;

        }
        if (posX > screenWidth / 2 - 16) {
            posX -= 0.5;
        }
        if (Gdx.input.justTouched()) {
            contador=15;
           // posY -= WORLD_HEIGHT * 0.07;
            posX += 0.5;
        }
        if(contador>0){
           posY -= WORLD_HEIGHT * 0.01;
           contador--;
        }
        // atualiza os inimigos (quadro de animação + colisão com dentes)
        for (int i = 0; i < this.enemies.size; i++) {
            Tube tube = this.enemies.get(i);
            tube.setPosition(tube.getX() - 5, tube.getY());
            tube.changePicture();
        }
        if (mouse.getY() + mouse.getHeight() / 2 > WORLD_HEIGHT) {
            super.challengeFailed();
            meon.stop();
        }
    }

    @Override
    public void onDrawGame() {
        batch.draw(bg1, 0, 0, srcX, 0, WORLD_WIDTH, WORLD_HEIGHT);

        for (Tube tubes : this.enemies) {
            tubes.draw(batch);
            for (int i = 0; i < tubes.getSize(); i++) {
                batch.draw(tubeTexture, tubes.getX(), 60 * i);
            }

        }

        mouse.draw(batch);
    }

    @Override
    public String getInstructions() {
        return "Voe!";
    }

    @Override
    public boolean shouldHideMousePointer() {
        return true;
    }

    class Calopsita extends AnimatedSprite {

        static final int FRAME_WIDTH = 131;//131
        static final int FRAME_HEIGHT = 156;//156

        public Calopsita(final Texture toothbrushTexture) {
            super(new Animation(0.22f, new Array<TextureRegion>() {
                {
                    TextureRegion[][] frames = TextureRegion.split(
                            toothbrushTexture, FRAME_WIDTH, FRAME_HEIGHT);
                    super.addAll(new TextureRegion[]{
                        frames[0][0],
                        frames[0][1],
                        frames[0][2],
                        frames[0][3],});
                }
            }));
            super.getAnimation().setPlayMode(Animation.PlayMode.LOOP);
            super.setAutoUpdate(false);
        }

        Vector2 getHeadPosition() {
            return new Vector2(
                    this.getX() + this.getWidth(),
                    this.getY() + this.getHeight());
        }

        float getHeadDistanceTo(float enemyX, float enemyY) {
            return getHeadPosition().dst(enemyX, enemyY);
        }
    }

    class Tube extends MultiAnimatedSprite {

        private Vector2 speed;

        private static final int FRAME_WIDTH = 220;
        private static final int FRAME_HEIGHT = 390;
        private int size;
        private int state;

        public Tube() {
            super(null, null);
        }
        public Tube(final Texture tubesSpritesheet) {

            super(new HashMap<String, Animation>() {
                {
                    TextureRegion[][] frames = TextureRegion
                            .split(tubesSpritesheet,
                                    FRAME_WIDTH, FRAME_HEIGHT);
                    Animation sleep = new Animation(0.1f,
                            frames[0][0]);
                    Animation acordado = new Animation(0.1f,
                            frames[0][1]);  
                    sleep.setPlayMode(Animation.PlayMode.NORMAL);
                    put("walking", sleep);
                    put("acordado", acordado);

                }
            }, "walking");

        }

        public void changePicture() {
            this.startAnimation("acordado");
        }

        @Override
        public void update(float dt) {

            super.update(dt);
            super.setPosition(super.getX() + this.speed.x * dt,
                    super.getY() + this.speed.y * dt);
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public Vector2 getSpeed() {
            return speed;
        }

        public void setSpeed(Vector2 speed) {
            this.speed = speed;
        }

    }

}
