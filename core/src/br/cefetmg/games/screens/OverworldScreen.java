package br.cefetmg.games.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import br.cefetmg.games.transition.TransitionScreen;
import java.util.Arrays;
import br.cefetmg.games.minigames.factories.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import java.util.ArrayList;
import java.util.HashSet;

public class OverworldScreen extends BaseScreen {

    private Vector2 click;
    private Stage stage;
    protected Sound click1,click2;
    private boolean check = false;
    private boolean stop,bool1=false;
    private Vector2[] posicaoIcone;
    private boolean[] openStages;
    private Image map, arrow,
            icon1, stage1,
            icon2, stage2,
            icon3, stage3,
            icon4, stage4,
            icon5, stage5,
            exit, menu, play, water;
    private ArrayList<Image> cadeados;
    private boolean desenhaMeio=true;
    private Music backgroundMusic;
    private int currentStage;
    private int score;
    FileHandle file;
    public OverworldScreen(Game game, BaseScreen previous) {
        super(game, previous);
    }

    @Override
    public void appear() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        map = new Image(new Texture(Gdx.files.internal("world/desert.png")));
        arrow = new Image(new Texture(Gdx.files.internal("world/arrow.png")));
        icon1 = new Image(new Texture(Gdx.files.internal("world/icon1.png")));
        stage1 = new Image(new Texture(Gdx.files.internal("world/stage1.png")));
        stage2 = new Image(new Texture(Gdx.files.internal("world/stage2.png")));
        stage3 = new Image(new Texture(Gdx.files.internal("world/stage3.png")));
        stage5 = new Image(new Texture(Gdx.files.internal("world/stage4.png")));
        stage4 = new Image(new Texture(Gdx.files.internal("world/stage5.png")));
        exit = new Image(new Texture(Gdx.files.internal("world/menu.png")));
        icon2 = new Image(new Texture(Gdx.files.internal("world/icon2.png")));
        icon3 = new Image(new Texture(Gdx.files.internal("world/icon3.png")));
        icon4 = new Image(new Texture(Gdx.files.internal("world/icon4.png")));
        icon5 = new Image(new Texture(Gdx.files.internal("world/icon5.png")));
        menu = new Image(new Texture(Gdx.files.internal("world/menu.png")));
        play = new Image(new Texture(Gdx.files.internal("world/play.png")));
        water = new Image(new Texture(Gdx.files.internal("world/water.jpg")));
        file = Gdx.files.local("data/ProgressFile.txt");
        assets.load("menu/click2.mp3", Sound.class);
        assets.load("menu/click3.mp3", Sound.class);
        assets.load("world/overworldtheme.mp3", Music.class); 
        assets.load("world/overworldtheme.mp3", Music.class);
        

    }

    @Override
    protected void assetsLoaded() {
        cadeados = new ArrayList<Image>();
        openStages = new boolean[5];
        for(int i = 0; i < 5; i++) {
            openStages[i] = false;
        }
        for (int i = 0; i < 5; i++) {
            cadeados.add(new Image(new Texture(Gdx.files.internal("world/cadeado.png"))));
        }
        bool1 = true;
        desenhaMeio = true;
        click1 = assets.get("menu/click2.mp3", Sound.class);
        click2 = assets.get("menu/click3.mp3", Sound.class);
        backgroundMusic = assets.get("world/overworldtheme.mp3", Music.class);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        stage = new Stage(new ScreenViewport());
        Group group = new Group();
        map.setName("map");
        water.setName("water");
        play.setName("play");
        menu.setName("menu");
        arrow.setName("arrow");
        icon1.setName("icon1");
        icon2.setName("icon2");
        icon3.setName("icon3");
        icon4.setName("icon4");
        icon5.setName("icon5");
        stage1.setName("stage1");
        stage2.setName("stage2");
        stage3.setName("stage3");
        stage4.setName("stage4");
        stage5.setName("stage5");
        exit.setName("exit");
        
       // group.addActor(cadeados.get(0));
       // group.addActor(cadeados.get(1));
        //group.addActor(cadeados.get(2));
        //group.addActor(cadeados.get(3));
        //group.addActor(cadeados.get(4));
        
        group.addActor(stage1);
        group.addActor(stage2);
        group.addActor(stage3);
        group.addActor(stage4);
        group.addActor(stage5);
        group.addActor(play);
        group.addActor(exit);
        group.addActor(water);
        group.addActor(map);
        group.addActor(icon1);
        group.addActor(icon2);
        group.addActor(icon3);
        group.addActor(icon4);
        group.addActor(icon5);
        group.addActor(menu);
        group.addActor(arrow);
        
        
        stage.addActor(group);

        map.setZIndex(2);
        water.setZIndex(1);
        stage1.setZIndex(0);
        stage2.setZIndex(0);
        stage3.setZIndex(0);
        stage4.setZIndex(0);
        stage5.setZIndex(0);
        play.setZIndex(0);
        exit.setZIndex(0);
        arrow.setZIndex(20);

        map.setOrigin(0, 0);
        map.setScale(viewport.getWorldWidth() / map.getWidth(), viewport.getWorldHeight() / map.getHeight());
        map.setPosition(0, 0);
        water.setOrigin(0, 0);
        water.setScale(viewport.getWorldWidth() / water.getWidth(), viewport.getWorldHeight() / water.getHeight());
        water.setPosition(0, 0);

        play.setScale(.9f);
        play.setOrigin(0, 0);
        play.setPosition(viewport.getWorldWidth() / 2 + 50, viewport.getWorldHeight() / 2 - 100);
        exit.setScale(.9f);
        exit.setOrigin(0, 0);
        exit.setPosition(viewport.getWorldWidth() / 2 - 225, viewport.getWorldHeight() / 2 - 100);
        
        posicaoIcone = new Vector2[5];
        
        posicaoIcone[0]= new Vector2(775.29376f,176.95001f);
        posicaoIcone[1]= new Vector2(325.83545f, 453.82504f);
        posicaoIcone[2]= new Vector2(570.648f-20, 545.2626f);
        posicaoIcone[3]= new Vector2(630.8559f, 316.95004f);
        posicaoIcone[4]= new Vector2(983.3172f, 320.38754f);
      
        icon1.setScale(0.2f);
        icon1.setOrigin(0, 0);
        icon1.setPosition(posicaoIcone[0].x,posicaoIcone[0].y);
        stage1.setScale(0.8f);
        stage1.setOrigin(0, 0);
        
        icon2.setScale(0.9f);
        icon2.setOrigin(0, 0);
        icon2.setPosition(posicaoIcone[1].x,posicaoIcone[1].y);
        stage2.setScale(0.8f);
        stage2.setOrigin(0, 0);
        
        icon3.setScale(0.3f);
        icon3.setOrigin(0, 0);
        icon3.setPosition(posicaoIcone[2].x+20,posicaoIcone[2].y);
        stage3.setScale(0.8f);
        stage3.setOrigin(0, 0);
        
        icon4.setScale(0.25f);
        icon4.setOrigin(0, 0);
        icon4.setPosition(posicaoIcone[3].x,posicaoIcone[3].y);
        stage4.setScale(0.8f);
        stage4.setOrigin(0, 0);
        
        icon5.setScale(0.4f);
        icon5.setOrigin(0, 0);
        icon5.setPosition(posicaoIcone[4].x,posicaoIcone[4].y);
        stage5.setScale(0.8f);
        stage5.setOrigin(0, 0);
        
        arrow.setScale(0.08f);
        arrow.setOrigin(0, 0);
        arrow.setPosition(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2);

        menu.setScale(.8f);
        menu.setOrigin(0, 0);
        menu.setPosition(0, 0);

        stage.setViewport(viewport);
        stage.act(Gdx.graphics.getDeltaTime());
        int i =0;
        for (Image cadeado : cadeados) {
            cadeado.setPosition(posicaoIcone[i].x + cadeado.getImageHeight()/2,posicaoIcone[i].y + + cadeado.getImageWidth()/2);
            cadeado.setScale(0.5f);
            i++;
        }
        
        // File Handle
        // Read and Create Progress File
        if (!file.exists()) {
            file.writeString("0:", false);
            file.writeString("0", true);
            currentStage = 0;
            score = 0;
        }else {
            String arquivo = new String(file.readString());
            String[] split = arquivo.split(":");
            currentStage = Integer.parseInt(split[0]);
            score = Integer.parseInt(split[1]);
        }
    }

    @Override
    public void cleanUp() {
        assets.dispose();
        backgroundMusic.stop();
    }

    @Override
    public void handleInput() {
        Gdx.input.setCursorCatched(true);

        click = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(click);

        if (!stop) {
            arrow.setPosition(click.x - arrow.getWidth() / 2 * arrow.getScaleX(), click.y - arrow.getHeight() / 2 * arrow.getScaleY());
        }

        arrow.setZIndex(0);
        Actor hitActor = stage.hit(arrow.getX(), arrow.getY() + arrow.getHeight() * arrow.getScaleY(), false);
        
        growEffect();
        if (Gdx.input.justTouched() && hitActor != null && !stop) {
            if ("menu".equals(hitActor.getName())) {
                click1.play();
                transitionScreen(new MenuScreen(super.game, this),
                        TransitionScreen.Effect.FADE_IN_OUT, 1f);
                stop = true;
            } else if (openStages[0]){
                 if ("play".equals(hitActor.getName())) {
                    stop = true;
                    click1.play();
                    firstStage(true);
                    desenhaMeio=true;
                }
                if ("exit".equals(hitActor.getName())) {
                    click1.play();
                    play.setZIndex(0);
                    exit.setZIndex(0);
                    openStages[0] = false;
                    desenhaMeio=true;
                }
            }else if (openStages[1]) {
                if ("play".equals(hitActor.getName())) {
                    stop = true;
                    click1.play();
                    secondStage(true);
                    desenhaMeio=true;
                }
                if ("exit".equals(hitActor.getName())) {
                    click1.play();
                    play.setZIndex(0);
                    exit.setZIndex(0);
                    openStages[1] = false;
                    desenhaMeio=true;
                }
            } else if (openStages[2]) {
                if ("play".equals(hitActor.getName())) {
                    stop = true;
                    click1.play();
                    thirdStage(true);
                    desenhaMeio=true;
                }
                if ("exit".equals(hitActor.getName())) {
                    click1.play();
                    play.setZIndex(0);
                    exit.setZIndex(0);
                    openStages[2] = false;
                    desenhaMeio=true;
                }
            } else if (openStages[3]) {
                if ("play".equals(hitActor.getName())) {
                    stop = true;
                    click1.play();
                    lastStage(true);
                    desenhaMeio=true;
                }
                if ("exit".equals(hitActor.getName())) {
                    click1.play();
                    play.setZIndex(0);
                    exit.setZIndex(0);
                    openStages[3] = false;
                    desenhaMeio=true;
                }
            } else if (openStages[4]) {
                if ("play".equals(hitActor.getName())) {
                    
                    stop = true;
                    click1.play();
                    fourthStage(true);
                    desenhaMeio=true;
                }
                if ("exit".equals(hitActor.getName())) {
                    
                    click1.play();
                    play.setZIndex(0);
                    exit.setZIndex(0);
                    openStages[4] = false;
                    desenhaMeio=true;
                }
            } else if ("icon1".equals(hitActor.getName())) {
                click1.play();desenhaMeio=false;
                firstStage(false);
            } else if ("icon2".equals(hitActor.getName()) && currentStage >= 1) {
                click1.play();desenhaMeio=false;
                secondStage(false);
            } else if ("icon3".equals(hitActor.getName()) && currentStage >= 2) {
                click1.play();desenhaMeio=false;
                thirdStage(false);
            } else if ("icon4".equals(hitActor.getName()) && currentStage >= 3) {
                click1.play();desenhaMeio=false;
                lastStage(false);
            } else if ("icon5".equals(hitActor.getName()) && currentStage >= 4) {
                click1.play();desenhaMeio=false;
                fourthStage(false);
            } else if((("icon5".equals(hitActor.getName()) && currentStage<4 )|| ("icon4".equals(hitActor.getName())&& currentStage<3)||("icon3".equals(hitActor.getName())&& currentStage<2) || ("icon2".equals(hitActor.getName())&& currentStage<1))){
                click2.play();
                stop = false;
            }else{
            }
        }
        arrow.setZIndex(20);
    }

   private void firstStage(boolean go) {
        openStages[0] = true;
        stage1.setZIndex(18);
        if (go) {
            transitionScreen(new PlayingGamesScreen(super.game, this, 5, new HashSet<MiniGameFactory>(
                    Arrays.asList(
                            
                            new TheFridgeGameFactory(),
                            new MouseAttackFactory(),
                            // gustavo henrique e rogenes
                            new BasCATballFactory(),
                            new RunningFactory()
                    )
            ), .1f, .2f), TransitionScreen.Effect.FADE_IN_OUT,
                    1f);
            score += 2;
            if (currentStage == 0) currentStage = 1;
            String stage = (String.valueOf(currentStage)+":"+String.valueOf(score));
            file.writeString(stage, false);
        }
        
    }

    private void secondStage(boolean go) {
        openStages[1] = true;
        stage2.setZIndex(18);
        if (go) {
            transitionScreen(new PlayingGamesScreen(super.game, this, 5, new HashSet<MiniGameFactory>(
                    Arrays.asList(
                            // rafael e luis carlos
                            //new DodgeTheVeggiesFactory(),
                            //new CatchThatHomeworkFactory(),
                            // adriel
                            //new UnderwaterCatFactory(),
                            // arthur e pedro
                            //new DogBarksCatFleeFactory(),
                            new ClickFindCatFactory()
                    )
            ), .3f, .4f), TransitionScreen.Effect.FADE_IN_OUT, 1f);
            score += 4;
            if (currentStage == 1) currentStage = 2;
            String stage = (String.valueOf(currentStage)+":"+String.valueOf(score));
            file.writeString(stage, false);
        }
    }

    private void thirdStage(boolean go) {
        openStages[2] = true;
        stage3.setZIndex(18);
        if (go) {
            transitionScreen(new PlayingGamesScreen(super.game, this, 5, new HashSet<MiniGameFactory>(
                    Arrays.asList(
                            // cassiano e gustavo jordão
                            new RainingCatsFactory(),
                            new JumpTheObstaclesFactory(),
                            // luiza e pedro cordeiro
                            new SpyFishFactory(),
                            new PhantomCatFactory()
                    )
            ), .5f, .6f), TransitionScreen.Effect.FADE_IN_OUT, 1f);
            score += 6;
            if (currentStage == 2) currentStage = 3;
            String stage = (String.valueOf(currentStage)+":"+String.valueOf(score));
            file.writeString(stage, false);
        }
    }

    private void fourthStage(boolean go) {
        openStages[3] = true;
        stage4.setZIndex(18);
        if (go) {
            stage4.setZIndex(stage4.getZIndex() + 7);
            transitionScreen(new PlayingGamesScreen(super.game, this, 5, new HashSet<MiniGameFactory>(
                    Arrays.asList(
                            // gabriel e natália
                            new TicCatDogFactory(),
                            new JetRatFactory(),
                            // emanoel e vinícius
                            new HeadSoccerFactory(),
                            new CatAvoiderFactory()
                    )
            ), .7f, .8f), TransitionScreen.Effect.FADE_IN_OUT, 1f);
            score += 8;
            if (currentStage == 3) currentStage = 4;
            String stage = (String.valueOf(currentStage)+":"+String.valueOf(score));
            file.writeString(stage, false);
        }
    }

    private void lastStage(boolean go) {
        openStages[4] = true;
        stage5.setZIndex(18);
        if (go) {
            transitionScreen(new PlayingGamesScreen(super.game, this, 5, new HashSet<MiniGameFactory>(
                    Arrays.asList(
                            // joão e miguel
                            new CannonCatFactory(),
                            new MeowsicFactory(),
                            // túlio
                            new NinjaCatFactory(),
                            //estevao e sarah//
                            new KillTheRatsFactory()
                    )
            ), 0.9f, 1), TransitionScreen.Effect.FADE_IN_OUT, 1f);
            score += 10;
            if (currentStage == 4) currentStage = 5;
            String stage = (String.valueOf(currentStage)+":"+String.valueOf(score));
            file.writeString(stage, false);
        }
    }
    
    private void DesenharCadeados(){
        int i=0;
        for (Image cadeado : cadeados ) {
            if(i>currentStage){
               if(i==3){
                    if(desenhaMeio)
                        cadeado.draw(batch,1);
               }
               else
                cadeado.draw(batch,1);  
            }
            i++;
        }
    }
    
    private void growEffect() {
        Actor hitActor = stage.hit(arrow.getX(), arrow.getY() + arrow.getHeight() * arrow.getScaleY(), false);
        if (!stop && hitActor != null && !openStages[1] && !openStages[2] && !openStages[3] && !openStages[0] && !openStages[4]) {
            if ("icon1".equals(hitActor.getName())) {
                if (check) {
                    icon1.setScale(.28f);
                    icon1.setPosition(755.29376f, 166.95001f);
                    check = !check;
                }
            } else if ("icon2".equals(hitActor.getName())) {
                if (check) {
                    icon2.setScale(1.1f);
                    icon2.setPosition(325.83545f, 443.82504f);
                    check = !check;
                }
            } else if ("icon3".equals(hitActor.getName())) {
                if (check) {
                    icon3.setScale(.4f);
                    icon3.setPosition(560.648f, 535.2626f);
                    check = !check;
                }
            } else if ("icon4".equals(hitActor.getName())) {
                if (check) {
                    icon4.setScale(.31f);
                    icon4.setPosition(620.8559f, 306.95004f);
                    check = !check;
                }
            } else if ("icon5".equals(hitActor.getName())) {
                if (check) {
                    icon5.setScale(.5f);
                    icon5.setPosition(973.3172f, 310.38754f);
                    check = !check;
                }
            } else {
                icon1.setScale(0.2f);
                icon1.setPosition(775.29376f, 176.95001f);

                icon2.setScale(0.9f);
                icon2.setPosition(325.83545f, 453.82504f);

                icon3.setScale(0.3f);
                icon3.setPosition(570.648f, 545.2626f);

                icon4.setScale(0.25f);
                icon4.setPosition(630.8559f, 316.95004f);

                icon5.setScale(0.4f);
                icon5.setPosition(983.3172f, 320.38754f);
                check = true;
            }
        }
    }

    private void showStage(Image stage) {
        if (stage.getScaleX() < .8f) {
            stage.setScale(stage.getScaleX() + .1f);
            stage.setPosition(viewport.getWorldWidth() / 2 - stage.getWidth() * stage.getScaleX() / 2, viewport.getWorldHeight() / 2 - stage.getHeight() * stage.getScaleY() / 2);

        } else {
            exit.setZIndex(19);
            play.setZIndex(19);
            arrow.setZIndex(20);
        }
    }

    private void hideStage(Image stage) {
        if (stage.getScaleX() > 0) {
            stage.setScale(stage.getScaleX() - .1f);
            stage.setPosition(viewport.getWorldWidth() / 2 - stage.getWidth() * stage.getScaleX() / 2, viewport.getWorldHeight() / 2 - stage.getHeight() * stage.getScaleY() / 2);
        }
    }

    @Override
    public void draw() {
        stage.draw();
        Batch batch = this.batch;
	batch.setProjectionMatrix(camera.combined);
        batch.begin();
            DesenharCadeados();
        batch.end();
    }

    @Override
    public void update(float dt) {
        if (openStages[0]) {
            showStage(stage1);
        } else {
            hideStage(stage1);
        }
        if (openStages[1]) {
            showStage(stage2);
        } else {
            hideStage(stage2);
        }
        if (openStages[2]) {
            showStage(stage3);
        } else {
            hideStage(stage3);
        }
        if (openStages[3]) {
            showStage(stage4);
        } else {
            hideStage(stage4);
        }
        if (openStages[4]) {
            showStage(stage5);
        } else {
            hideStage(stage5);
        }
    }
}
