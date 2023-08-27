package vampiremod.cards.skill;

import character.Vampire;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.cards.curses.Pride;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import vampiremod.cards.BaseCard;
import vampiremod.util.CardInfo;


import static vampiremod.vampiremod.makeID;

public class BloodToGold extends BaseCard implements OnObtainCard{
    private final static CardInfo cardInfo = new CardInfo(
            "Blood_to_gold", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            // what you want to see what to use.
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL
            // and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );
    //This is theoretically optional, but you'll want it. The ID is how you refer to the card.
    //For example, to add a card to the starting deck, you need to use its ID.
    //With this, you can just use 'MyCard.ID'. Without it, you'd have to type out
    //'yourModID:MyCard' and make sure you don't make any mistakes, and you'd also have to update it
    //if you decided to change the card's ID.
    public static final String ID = makeID(cardInfo.baseId);

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int HP_LOST = 10;
    private static final int GOLD_AMOUNT = 80;
    private static final int UPG_GOLD_AMOUNT = 20;

    public BloodToGold() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setCostUpgrade(0);
        setMagic(GOLD_AMOUNT, UPG_GOLD_AMOUNT);
        setExhaust(true, true);
        this.cardsToPreview = new Pride();
    }

    @Override
    public void onObtainCard() {
        CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
        AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new Pride(), Settings.WIDTH / 2.0f,
                Settings.HEIGHT / 2.0f));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, HP_LOST));

        addToBot(new GainGoldAction(magicNumber));
        for(int i = 0; i < magicNumber; ++i) {
            AbstractDungeon.effectList.add(new GainPennyEffect(p, p.hb.cX, p.hb.cY,
                    p.hb.cX, p.hb.cY, true));
        }
    }
    @Override
    public AbstractCard makeCopy() {
        return new BloodToGold();
    }

}

