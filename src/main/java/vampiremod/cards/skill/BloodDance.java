package vampiremod.cards.skill;

import character.Vampire;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vampiremod.cards.BaseCard;
import vampiremod.cards.tempCards.Blood;
import vampiremod.util.CardInfo;

import static vampiremod.vampiremod.makeID;

public class BloodDance extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BloodDance", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );
    public static final String ID = makeID(cardInfo.baseId);
    private static final int MAGIC = 3;
    private static final int UPG_CARD_DRAW = 1;
    private static final int BLOCK = 3;

    public BloodDance() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setMagic(MAGIC, UPG_CARD_DRAW);
        setBlock(BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p,p, magicNumber*2));
        addToBot(new MakeTempCardInHandAction(new Blood(), magicNumber));
        for ( int i=0; i < magicNumber; i++){
            addToBot(new GainBlockAction(p,p, block));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BloodDance();
    }

}

