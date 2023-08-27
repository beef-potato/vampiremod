package vampiremod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static vampiremod.vampiremod.makeID;

public class SelectPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SelectPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    public SelectPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    private boolean triggeredThisTurn = false;

    public void atStartOfTurn() {
        this.triggeredThisTurn = false;
    }

    public void onExhaust(AbstractCard card) {
        if (!this.triggeredThisTurn) {
            this.triggeredThisTurn = true;
            flash();
            addToBot(new GainEnergyAction(1));
        }
    }


        public void updateDescription() {
            this.description = DESCRIPTIONS[0];
        }

    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new SelectPower(owner, amount);
    }
}


