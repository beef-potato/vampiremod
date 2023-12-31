package vampiremod.cards.skill;

import character.Vampire;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vampiremod.cards.BaseCard;
import vampiremod.powers.BleedingPower;
import vampiremod.util.CardInfo;

import static vampiremod.vampiremod.makeID;

public class Anticoagulant extends BaseCard{
    private final static CardInfo cardInfo = new CardInfo(
            "Anticoagulant", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            // what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );
    public static final String ID = makeID(cardInfo.baseId);
    private static final int BLUR_AMOUNT = 6;
    private static final int UPG_BLUR_AMOUNT = 3;
    private static final int HP_LOST = 2;

    public Anticoagulant() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setMagic(BLUR_AMOUNT, UPG_BLUR_AMOUNT);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, HP_LOST));
        addToBot(new ApplyPowerAction(m, p, new BleedingPower(m, magicNumber, false)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Anticoagulant();
    }

}

