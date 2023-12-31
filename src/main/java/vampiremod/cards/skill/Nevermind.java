package vampiremod.cards.skill;

import character.Vampire;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vampiremod.cards.BaseCard;
import vampiremod.util.CardInfo;

import static vampiremod.vampiremod.makeID;

public class Nevermind extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Nevermind", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            // what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL
            // and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );
    public static final String ID = makeID(cardInfo.baseId);

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int HP_LOST = 2;

    private static final int BLOCK = 9;
    private static final int UPG_BLOCK = 4;
    private static final int DRAW = 1;
    private static final int UPG_DRAW = 1;

    public Nevermind() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setBlock(BLOCK, UPG_BLOCK); //Sets the card's Block and how much it increases when upgraded.
        setMagic(DRAW, UPG_DRAW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, HP_LOST));
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new DrawCardAction(p, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Nevermind();
    }

}

