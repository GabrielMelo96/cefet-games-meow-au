package br.cefetmg.games.minigames.factories;

import br.cefetmg.games.minigames.DogBarksCatFlee;
import br.cefetmg.games.minigames.MiniGame;
import br.cefetmg.games.screens.BaseScreen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import java.util.Map;
import br.cefetmg.games.minigames.util.MiniGameStateObserver;

public class DogBarksCatFleeFactory implements MiniGameFactory {

    @Override
    public MiniGame createMiniGame(BaseScreen screen,
            MiniGameStateObserver observer, float difficulty) {
        return new DogBarksCatFlee(screen, observer, difficulty);
    }

    @Override
    public Map<String, Class> getAssetsToPreload() {
        return new HashMap<String, Class>() {
            {
                put("DogBarksCatFlee/dog_separado_1.png", Texture.class);
                put("DogBarksCatFlee/kitten1-alt_3.png", Texture.class);
                put("DogBarksCatFlee/kitten1-alt_4.png", Texture.class);
                put("DogBarksCatFlee/dog_separado_2.png", Texture.class);
                put("DogBarksCatFlee/dog_separado_3.png", Texture.class);
                put("DogBarksCatFlee/dog_separado_4.png", Texture.class);
                put("DogBarksCatFlee/dog_spritesheet.png", Texture.class);
                put("DogBarksCatFlee/spritesheet2.png", Texture.class);
                put("DogBarksCatFlee/kitten1-alt.png", Texture.class);
                put("DogBarksCatFlee/tile0.png", Texture.class);
                put("DogBarksCatFlee/tile1.png", Texture.class);
                put("DogBarksCatFlee/tile2.png", Texture.class);
                put("DogBarksCatFlee/tile3.png", Texture.class);
                put("DogBarksCatFlee/tile4.png", Texture.class);
                //Audio Disponivel em:
                //https://freesound.org/people/Jace/sounds/155309/
                put("DogBarksCatFlee/BarkSound.wav", Sound.class);
                //Audio Disponivel em:
                //https://freesound.org/people/tuberatanka/sounds/110011/
                put("DogBarksCatFlee/cat-meow.wav", Sound.class);
                //Audio Disponivel em:
                //https://freesound.org/people/Piink_Aces/sounds/257824/
                put("DogBarksCatFlee/dog-whining-sound.mp3", Sound.class);
            }
        };
    }
}
