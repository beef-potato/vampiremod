package vampiremod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.interfaces.CloneablePowerInterface;

import static vampiremod.vampiremod.makeID;

public class DfInvinciblePower extends BasePower implements CloneablePowerInterface{

    public static final String POWER_ID = makeID("DfInvinciblePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private boolean activated = false;
    private int amount2;

    public DfInvinciblePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount2 = 5;
        this.owner = owner;
        ID = POWER_ID;
        this.amount = amount;

        this.loadRegion("heartDef");
        this.updateDescription();
        this.priority = 99;
        this.activated = false;
    }

    public int onLoseHp(int damageAmount){
        if (damageAmount > amount2){
            damageAmount = this.amount2;
            activated = true;
        }

        this.amount2 -= damageAmount;
        if (this.amount2 < 0){
            this.amount2 = 0;
        }
        this.updateDescription();
        return damageAmount;

    }

    @Override
    public void atEndOfRound(){
        if (activated) {
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
        this.amount2 = 5;
    }


    public void updateDescription() {
        if (this.amount2 == 1) {
//            this.amount2 >= 1
            this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3] + amount + DESCRIPTIONS[4];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new DfInvinciblePower(owner, amount);
    }
}
