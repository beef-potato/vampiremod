package vampiremod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static vampiremod.vampiremod.makeID;

public class BloodPactPower extends BasePower implements CloneablePowerInterface, OnLoseTempHpPower {
    public static final String POWER_ID = makeID("BloodPactPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    public BloodPactPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            flash();
            addToTop(new DrawCardAction(AbstractDungeon.player, amount));
        }
    }

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        if (i > 0) {
            flash();
            addToTop(new DrawCardAction(AbstractDungeon.player, amount));
        }
        // this power won't modify the damage cost
        return i;
    }

    public void updateDescription() {
    this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new BloodPactPower(owner, amount);
    }


}
