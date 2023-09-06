package reskinContent.skinCharacter;

import character.Vampire;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import reskinContent.skinCharacter.skins.*;

public class VampireSkin extends AbstractSkinCharacter{

    public static final String ID = (CardCrawlGame.languagePack.getCharacterString("vampiremod:JVampire")).NAMES[0];
    //ID = 吸血鬼
//    public static final AbstractSkin[] SKINS = new AbstractSkin[] { new VampireOriginal(), new VampireSpring(),
//            new VampireNurse(), new VampireForm(),  new VampireIdle(), new VampireWaifu()};
    public static final AbstractSkin[] SKINS = new AbstractSkin[] { new VampireSpring()};
    public VampireSkin() {
        super(ID, SKINS);
    }

}
