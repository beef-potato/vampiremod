package vampiremod.cards.power;

import character.Vampire;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import vampiremod.cards.BaseCard;
import vampiremod.cards.tempCards.Blood;
import vampiremod.powers.DrainagePower;
import vampiremod.util.CardInfo;

import static vampiremod.vampiremod.makeID;

public class Drainage extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Drainage", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            // what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then
            // SPECIAL
            // and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );
    public static final String ID = makeID(cardInfo.baseId);
    private static final int HP_LOST = 2;
    private static final int BLOOD_NUM = 1;

    public Drainage() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        cardsToPreview = new Blood();
        setMagic(BLOOD_NUM);
        setInnate(false, true);
        cardsToPreview = new Blood();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1),1));
        addToBot(new ApplyPowerAction(p, p, new DrainagePower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Drainage();
    }

}

