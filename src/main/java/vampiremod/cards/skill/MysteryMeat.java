package vampiremod.cards.skill;

import character.Vampire;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import vampiremod.cards.BaseCard;
import vampiremod.util.CardInfo;

import static vampiremod.vampiremod.makeID;
import java.util.Random;

public class MysteryMeat extends BaseCard{
    private final static CardInfo cardInfo = new CardInfo(
            "MysteryMeat", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            0, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            // what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL
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
    private static final int MAGIC_AMOUNT = 4;
    private static final int UPG_MAGIC_AMOUNT = 7;

    public MysteryMeat() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setMagic(MAGIC_AMOUNT, UPG_MAGIC_AMOUNT);
        setSelfRetain(true, true);
        ExhaustiveVariable.setBaseValue(this, 2);
        tags.add(Vampire.CustomTags.FOOD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, magicNumber));
        double temp  = generateRandomDecimal();
        if (temp < 0.6){
            addToBot(new ApplyPowerAction(p, p, new PoisonPower(p, p, magicNumber), magicNumber));
        }
    }

    private double generateRandomDecimal() {
        Random random = new Random();
        return random.nextDouble();
    }
    public void triggerOnExhaust() {
        addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player,  magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MysteryMeat();
    }

}

