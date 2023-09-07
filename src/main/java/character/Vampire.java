package character;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import vampiremod.cards.attack.Dash_V;
import vampiremod.cards.attack.Stab;
import vampiremod.cards.attack.Strike_V;
import vampiremod.cards.skill.Block_V;
import vampiremod.relics.Tusk;

import java.util.ArrayList;

import static vampiremod.vampiremod.characterPath;
import static vampiremod.vampiremod.makeID;

public class Vampire extends CustomPlayer {
    //Stats
    public static final int ENERGY_PER_TURN = 3;
    public static final int MAX_HP = 70;
    // consider the balance of game, I will make the hp of vampire as 52 at start.
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    //Strings
    private static final String ID = makeID("JVampire"); //This should match whatever
    // you have in the CharacterStrings.json file
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    //animations
    private static final String VAMPIRE_SKELETON_ATLAS = characterPath("animation/xixuegui/xixuegui.atlas");
    private static final String VAMPIRE_SKELETON_JSON = characterPath("animation/xixuegui/xixuegui.json");
    private static final String VAMPIRE_ANIMATION = "normal";


    //reskinContent
    //take animation url
//    private static final String VAMPIRE_SKELETON_ATLAS =
//            (CharacterSelectScreenPatches.characters[0]).skins[CharacterSelectScreenPatches.characters[0].reskinCount].atlasURL;
//
//    private static final String VAMPIRE_SKELETON_JSON =
//            (CharacterSelectScreenPatches.characters[0]).skins[CharacterSelectScreenPatches.characters[0].reskinCount].jsonURL;


    //Image file paths
    private static final String SHOULDER_1 = characterPath("shoulder3.png"); //Shoulder 1 and 2 are used at rest sites.
    private static final String SHOULDER_2 = characterPath("shoulder3.png");
    private static final String CORPSE = characterPath("corpse.png"); //Corpse is when you die.

    public static class Enums {
        //These are used to identify your character, as well as your character's card color.
        //Library color is basically the same as card color, but you need both because that's how the game was made.
        @SpireEnum
        public static AbstractPlayer.PlayerClass VAMPIRE;
        @SpireEnum(name = "VAMPIRE_GRAY_COLOR") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor CARD_COLOR;
        @SpireEnum(name = "VAMPIRE_GRAY_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    public static class CustomTags
    {
        @SpireEnum
        public static AbstractCard.CardTags FOOD;
        @SpireEnum public static AbstractCard.CardTags MY_OTHER_TAG;
        @SpireEnum public static AbstractCard.CardTags LOOK_AT_ME;
    }

    public Vampire() {

        super(NAMES[0], Enums.VAMPIRE,
                new CustomEnergyOrb(null, null, null),  null , null );//Energy Orb
//                new SpineAnimation("animation/xixuegui/xixuegui.atlas",
//                        "animation/xixurgui/xixuegui.skel", 1f));


        initializeClass((String)null, // origin: characterPath("animation/idle_vampire.png"),
                SHOULDER_2,
                SHOULDER_1,
                CORPSE,
                getLoadout(),
                20.0F, -20.0F, 200.0F, 250.0F, //Character hitbox. x y position, then width and height.
                new EnergyManager(ENERGY_PER_TURN));

        //Animation
        loadAnimation(VAMPIRE_SKELETON_ATLAS, VAMPIRE_SKELETON_JSON, 1f); //make render scale = 1f
        AnimationState.TrackEntry e = state.setAnimation(0, VAMPIRE_ANIMATION, true);
        this.stateData.setMix("yun", "normal", 0.1F);
        e.setTimeScale(0.6F);


        //Location for text bubbles. You can adjust it as necessary later. For most characters, these values are fine.
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        //List of IDs of cards for your starting deck.
        //If you want multiple of the same card, you have to add it multiple times.
        retVal.add(Strike_V.ID);
        retVal.add(Strike_V.ID);
        retVal.add(Strike_V.ID);
        retVal.add(Strike_V.ID);
        retVal.add(Block_V.ID);
        retVal.add(Block_V.ID);
        retVal.add(Block_V.ID);
        retVal.add(Block_V.ID);
        retVal.add(Dash_V.ID);
        retVal.add(Stab.ID);


        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        //IDs of starting relics. You can have multiple, but one is recommended.
        retVal.add(Tusk.ID);

        return retVal;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        //This card is used for the Gremlin card matching game.
        //It should be a non-strike non-defend starter card, but it doesn't have to be.
        return new Dash_V();
    }

    /*- Below this is methods that you should *probably* adjust, but don't have to. -*/

    @Override
    public int getAscensionMaxHPLoss() {
        return 4; //Max hp reduction at ascension 14+
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        //These attack effects will be used when you attack the heart.
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
        };
    }

    private final Color cardRenderColor = Color.LIGHT_GRAY.cpy(); //Used for some vfx on moving cards (sometimes) (maybe)
    private final Color cardTrailColor = Color.LIGHT_GRAY.cpy(); //Used for card trail vfx during gameplay.
    private final Color slashAttackColor = Color.LIGHT_GRAY.cpy(); //Used for a screen tint effect when you attack the heart.
    @Override
    public Color getCardRenderColor() {
        return cardRenderColor;
    }

    @Override
    public Color getCardTrailColor() {
        return cardTrailColor;
    }

    @Override
    public Color getSlashAttackColor() {
        return slashAttackColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        //Font used to display your current energy.
        //energyNumFontRed, Blue, Green, and Purple are used by the basegame characters.
        //It is possible to make your own, but not convenient.
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        //This occurs when you click the character's button in the character select screen.
        //See SoundMaster for a full list of existing sound effects, or look at BaseMod's wiki for adding custom audio.
        CardCrawlGame.sound.playA("ATTACK_DAGGER_2", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        //Similar to doCharSelectScreenSelectEffect, but used for the Custom mode screen. No shaking.
        return "ATTACK_DAGGER_2";
    }

    //Don't adjust these four directly, adjust the contents of the CharacterStrings.json file.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }


    public void damage(DamageInfo info) {
        if ((info.owner != null) && (info.type != DamageInfo.DamageType.THORNS) && (
                info.output - this.currentBlock > 0) && (info.type != DamageInfo.DamageType.HP_LOSS)) {
            AnimationState.TrackEntry e =
                    this.state.setAnimation(0, "yun", false);
            this.state.addAnimation(0, "normal", true, 0.0F);
            e.setTime(0.6F);
            e.setTimeScale(6f);
            e.setEndTime(0.33f);
        }
        super.damage(info);
        // it a be-hit damage
    }

    @Override
    public String getVampireText() {
        return TEXT[2]; //Generally, the only difference in this text is how the vampires refer to the player.
    }

    /*- You shouldn't need to edit any of the following methods. -*/

    //This is used to display the character's information on the character selection screen.
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.CARD_COLOR;
    }

    @Override
    public AbstractPlayer newInstance() {
        //Makes a new instance of your character class.
        return new Vampire();
    }
}
