package vampiremod.cards.skill;

import character.Vampire;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import vampiremod.cards.BaseCard;
import vampiremod.cards.tempCards.Blood;
import vampiremod.util.CardInfo;

import java.util.Objects;

import static vampiremod.vampiremod.makeID;

public class BloodCollect extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BloodCollect", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            -2, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            // what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then
           Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 2;

    public BloodCollect() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setMagic(MAGIC, UPG_MAGIC);
        cardsToPreview = new Blood();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (Objects.equals(c.cardID, Blood.ID)) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                    new StrengthPower(AbstractDungeon.player, magicNumber)));
            addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                    new LoseStrengthPower(AbstractDungeon.player, magicNumber)));

            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new BloodCollect();
    }

}

