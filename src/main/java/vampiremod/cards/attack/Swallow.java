package vampiremod.cards.attack;

import character.Vampire;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vampiremod.cards.BaseCard;
import vampiremod.util.CardInfo;

import static vampiremod.vampiremod.makeID;

public class Swallow extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Swallow", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL
            // and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something
            // like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );
    public static final String ID = makeID(cardInfo.baseId);

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    private static final int HP_LOST = 2;
    private static final int HP_GROWTH = 3;
    private static final int UPG_HP_GROWTH = 1;


    public Swallow() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it increases when upgraded.
        setMagic(HP_GROWTH, UPG_HP_GROWTH);
        setExhaust(true, true);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, HP_LOST));
        addToBot(new FeedAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), magicNumber));

    }
    @Override
    public AbstractCard makeCopy() {
        return new Swallow();
    }

}

