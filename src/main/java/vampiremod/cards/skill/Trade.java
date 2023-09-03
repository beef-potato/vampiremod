package vampiremod.cards.skill;

import character.Vampire;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.trials.LoseMaxHpTrial;
import vampiremod.cards.BaseCard;
import vampiremod.util.CardInfo;

import java.util.Random;

import static vampiremod.vampiremod.makeID;

public class Trade extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Trade", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            2, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            // what you want to see what to use.
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL
            // and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int MAGIC_AMOUNT = 3;
    private static final int UPG_MAGIC_AMOUNT = 1;

    public Trade() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setCostUpgrade(1);
        setMagic(MAGIC_AMOUNT, UPG_MAGIC_AMOUNT);
        setExhaust(true, true);
        setEthereal(true,true);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, p.maxHealth/magicNumber));

        double temp  = generateRandomDecimal();
        if (temp < 0.70){
            AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
        } else if (temp < 0.95) {
            AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.UNCOMMON);
        }else{
            AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.RARE);
        }
// test needed
    }
    private double generateRandomDecimal() {
        Random random = new Random();
        return random.nextDouble();
    }


    @Override
    public AbstractCard makeCopy() {
        return new Trade();
    }

}

