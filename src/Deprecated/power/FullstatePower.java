package vampiremod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting.TargetField.target;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static vampiremod.vampiremod.makeID;

public class FullstatePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("Fullstate");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    //The only thing this controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if they're a buff or debuff.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public FullstatePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    private static final float LEACH_RATIO = 1f;

    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if (this.owner.isPlayer & ((int)(LEACH_RATIO * damageAmount)>=1) &  info.type == DamageInfo.DamageType.NORMAL){
            this.flash();
            player.heal((int)(LEACH_RATIO * damageAmount), true);
            return damageAmount;
        }
        return damageAmount;
    }


    public void updateDescription() {
    this.description = DESCRIPTIONS[0];
    }

    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new FullstatePower(owner, amount);
    }
}
