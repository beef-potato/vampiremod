package vampiremod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;

import static vampiremod.vampiremod.makeID;

public class FamiliarPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("FamiliarPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public FamiliarPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_MANTRA", 0.05F);
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 10) {
            this.addToBot(new ApplyPowerAction(this.owner , this.owner, new IntangiblePlayerPower(this.owner, 1)));
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new DoubleDamagePower(this.owner, 1, false)));
            this.amount -= 10;
        }
    }


    public void updateDescription() {
    this.description = DESCRIPTIONS[0];
    }

    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new FamiliarPower(owner, amount);
    }
}
