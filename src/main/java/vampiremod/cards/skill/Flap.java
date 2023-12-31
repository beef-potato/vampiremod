package vampiremod.cards.skill;

import character.Vampire;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlightPower;
import vampiremod.cards.BaseCard;
import vampiremod.powers.FamiliarPower;
import vampiremod.util.CardInfo;

import static vampiremod.vampiremod.makeID;

public class Flap extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Flap", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            0, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            // what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then
            // SPECIAL
            // and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );
    public static final String ID = makeID(cardInfo.baseId);

    private static final int BLOCK = 3;
    private static final int UPG_BLOCK = 2;

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;


    public Flap() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setBlock(BLOCK, UPG_BLOCK); //Sets the card's Block and how much it increases when upgraded.
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FamiliarPower(p, magicNumber), magicNumber));
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Flap();
    }

}

