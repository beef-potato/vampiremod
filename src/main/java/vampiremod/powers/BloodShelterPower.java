package vampiremod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static vampiremod.vampiremod.makeID;

public class BloodShelterPower extends BasePower implements CloneablePowerInterface, OnLoseTempHpPower {
    public static final String POWER_ID = makeID("BloodShelterPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    public BloodShelterPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            flash();
            addToTop(new GainBlockAction(this.owner, amount));
        }
    }
    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        if (i > 0 && (i <= TempHPField.tempHp.get(AbstractDungeon.player))) {
            flash();
            addToTop(new GainBlockAction(this.owner, amount));
        }
        return i;
    }

    public void updateDescription() {
    this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new BloodShelterPower(owner, amount);
    }


}
