package vampiremod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static vampiremod.vampiremod.makeID;

public class NotStrengthPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("NotStrength");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing this controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if they're a buff or debuff.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public NotStrengthPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        //If NORMAL (attack) damage, modify damage by this power's amount
        return type == DamageInfo.DamageType.NORMAL ? damage + this.amount : damage;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new NotStrengthPower(owner, amount);
    }
}
