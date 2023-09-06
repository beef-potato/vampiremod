package reskinContent;

import basemod.BaseMod;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.StartGameSubscriber;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reskinContent.helpers.AssetLoader;
import reskinContent.patches.CharacterSelectScreenPatches;
import reskinContent.skinCharacter.AbstractSkinCharacter;

import static com.megacrit.cardcrawl.core.Settings.language;

@SpireInitializer
public class reskinContent implements EditStringsSubscriber, StartGameSubscriber {
    public static String MOD_ID = "reskinContent";
    public static final String MODNAME = "reskinContent";

    public static final String AUTHOR = "Rita,  overwrite by bf";

    public static final String DESCRIPTION = "";

    public static boolean unlockAllReskin = true;

    public static String makeID(String id) {
        return MOD_ID + ":" + id;
    }

    public static String assetPath(String path) {
        return MOD_ID + "/" + path;
    }

    public static Properties reskinContentDefaults = new Properties();
    private static final String resourcesFolder = "reskinContent"; // can not access the resources folder

    public static final Logger logger = LogManager.getLogger(reskinContent.class.getSimpleName());

    public static AssetLoader assets = new AssetLoader();

    public static void initialize() {
        new reskinContent();
    }

    public reskinContent() {
        BaseMod.subscribe((ISubscriber)this);
    }

    public static String getLanguageString() {
        if (Objects.requireNonNull(language) == Settings.GameLanguage.ZHS) {
            return "zhs";
        }
        return "eng";
    }

    public void receiveEditStrings() {
        String language = getLanguageString();
        String uiStrings =
                Gdx.files.internal(resourcesFolder + "/localization/" + language + "/UIString.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
    }


    public void receivePostInitialize() {
        loadSettings();
    }

    public static void saveSettings() {
        try {
            SpireConfig config = new SpireConfig("reskinContent", "reskinContentSaveData", reskinContentDefaults);
            for (int i = 0; i <= CharacterSelectScreenPatches.characters.length - 1; i++) {
                config.setBool(CardCrawlGame.saveSlot + "ReskinUnlock" + i, (CharacterSelectScreenPatches.characters[i]).reskinUnlock);
                config.setInt(CardCrawlGame.saveSlot + "reskinCount" + i, (CharacterSelectScreenPatches.characters[i]).reskinCount);
                for (int k = 0; k <= (CharacterSelectScreenPatches.characters[i]).skins.length - 1; k++)
                    config.setInt(CardCrawlGame.saveSlot + "portraitAnimationType" + i + "_" + k, ((CharacterSelectScreenPatches.characters[i]).skins[k]).portraitAnimationType);
            }
            System.out.println("==============reskin saving==============");
                    config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        try {
            SpireConfig config = new SpireConfig("reskinContent", "reskinContentSaveData", reskinContentDefaults);
            config.load();
            System.out.println("==============reskin load settings");
            for (int i = 0; i <= CharacterSelectScreenPatches.characters.length - 1; i++) {
                (CharacterSelectScreenPatches.characters[i]).reskinUnlock = config.getBool(CardCrawlGame.saveSlot + "ReskinUnlock" + i);
                (CharacterSelectScreenPatches.characters[i]).reskinCount = config.getInt(CardCrawlGame.saveSlot + "reskinCount" + i);
                for (int k = 0; k <= (CharacterSelectScreenPatches.characters[i]).skins.length - 1; k++) {
                    if (!((CharacterSelectScreenPatches.characters[i]).skins[k]).forcePortraitAnimationType) {
                        ((CharacterSelectScreenPatches.characters[i]).skins[k]).portraitAnimationType = config.getInt(CardCrawlGame.saveSlot + "portraitAnimationType" + i + "_" + k);
                        if (((CharacterSelectScreenPatches.characters[i]).skins[k]).portraitAnimationType > 2 || ((CharacterSelectScreenPatches.characters[i]).skins[k]).portraitAnimationType < 0)
                            ((CharacterSelectScreenPatches.characters[i]).skins[k]).portraitAnimationType = 2;
                        if (!(CharacterSelectScreenPatches.characters[i]).skins[k].hasAnimation())
                            ((CharacterSelectScreenPatches.characters[i]).skins[k]).portraitAnimationType = 0;
                    }
                }
                if ((CharacterSelectScreenPatches.characters[i]).reskinCount > (CharacterSelectScreenPatches.characters[i]).skins.length - 1)
                    (CharacterSelectScreenPatches.characters[i]).reskinCount = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            clearSettings();
        }
    }

    public static void clearSettings() {
        saveSettings();
    }

    public static void unlockAllReskin() {
        for (AbstractSkinCharacter c : CharacterSelectScreenPatches.characters)
            c.reskinUnlock = true;
        saveSettings();
    }



    public void receiveStartGame() {}

}
