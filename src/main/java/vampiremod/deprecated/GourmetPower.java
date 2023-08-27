package vampiremod.deprecated;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vampiremod.powers.BasePower;

import static vampiremod.vampiremod.makeID;

public class GourmetPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("GourmetPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    public GourmetPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    private boolean triggeredThisTurn = false;

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (int i = 0; i < this.amount; i++)
            {
                // randomly add cards with tags seems not work.
                addToBot((AbstractGameAction)new MakeTempCardInHandAction(
                        AbstractDungeon.getCard(AbstractCard.CardRarity.RARE, AbstractDungeon.cardRandomRng)
                                .makeCopy(), 1, false));
            }
        }
    }


    public void updateDescription() {
            this.description = DESCRIPTIONS[0];
        }

    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new GourmetPower(owner, amount);
    }
}


