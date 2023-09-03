package vampiremod.cards.skill;

import character.Vampire;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DemonFormPower;
import vampiremod.cards.BaseCard;
import vampiremod.util.CardInfo;

import static vampiremod.vampiremod.makeID;

public class Fire extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Fire", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            3, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            // what you want to see what to use.
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then
            // SPECIAL
            // and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);
    
    private static final int DEMO_AMOUNT = 1;
    private static final int HP_LOST = 2;
    private static final int UPG_HEAL_AMOUNT = 1;


    public Fire() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setMagic(DEMO_AMOUNT);
        setCostUpgrade(2);
        setExhaust(true, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, HP_LOST));

        int count = AbstractDungeon.player.hand.size();
        int i;

        for (i = 0; i < count; i++) {
            if (Settings.FAST_MODE) {
                addToTop(new ExhaustAction(1, true, true, false,
                        Settings.ACTION_DUR_XFAST));
            } else {
                addToTop(new ExhaustAction(1, true, true));
            }

            addToBot(new ApplyPowerAction(p, p, new DemonFormPower(p, magicNumber), magicNumber));
        }


    }

    @Override
    public AbstractCard makeCopy() {
        return new Fire();
    }

}

