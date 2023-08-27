package vampiremod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static vampiremod.vampiremod.makeID;

public class TrainPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("TrainPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    public TrainPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    private boolean triggeredThisTurn = false;

    public void atStartOfTurn() {
        addToBot(new ApplyPowerAction(this.owner, this.owner, new VigorPower(this.owner, amount), amount));

    }

    public void updateDescription() {
            this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
        }

    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new TrainPower(owner, amount);
    }
}


