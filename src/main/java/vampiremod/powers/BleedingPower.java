package vampiremod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vampiremod.cards.tempCards.Blood;

import static vampiremod.vampiremod.makeID;

public class BleedingPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("BleedingPower");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    private boolean justApplied = false;

    public BleedingPower(AbstractCreature owner, int amount, boolean isSourceMonster) {

        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        if (AbstractDungeon.actionManager.turnHasEnded && isSourceMonster)
            this.justApplied = true;
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        }

        addToBot(new MakeTempCardInHandAction(new Blood()));
    }


    public void updateDescription() {
    this.description = DESCRIPTIONS[0] ;
    }

    @Override
    public AbstractPower makeCopy() {
        return new BleedingPower(owner, amount, false);
    }
}
