package vampiremod.cards.skill;

import character.Vampire;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vampiremod.cards.BaseCard;
import vampiremod.cards.tempCards.Blood;
import vampiremod.powers.BleedingPower;
import vampiremod.util.CardInfo;

import static vampiremod.vampiremod.makeID;

public class Haste extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Haste", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF_AND_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            // what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL
            // and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );
    public static final String ID = makeID(cardInfo.baseId);
    private static final int HP_LOST = 2;
    private boolean CANUSE = false;

    public Haste() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setCostUpgrade(0);
        cardsToPreview = new Blood();
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p,p, HP_LOST));
        if (m!=null && m.hasPower(BleedingPower.POWER_ID)){
            int blood_amount = m.getPower(BleedingPower.POWER_ID).amount;
            addToBot(new RemoveSpecificPowerAction(m, p, BleedingPower.POWER_ID));
            addToBot(new MakeTempCardInHandAction(new Blood(), blood_amount));
            addToBot(new DamageAction(m,  new DamageInfo(p, blood_amount*2, DamageInfo.DamageType.HP_LOSS)));
        }else{
            //empty
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Haste();
    }

}

