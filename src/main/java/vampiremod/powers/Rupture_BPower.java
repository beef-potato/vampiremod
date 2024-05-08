package vampiremod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.omg.CORBA.TRANSACTION_UNAVAILABLE;

import static vampiremod.vampiremod.makeID;

public class Rupture_BPower extends BasePower implements CloneablePowerInterface, OnLoseTempHpPower {
    public static final String POWER_ID = makeID("Rupture_BPower");
    private static final PowerType TYPE = PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public Rupture_BPower(AbstractCreature owner, int amount){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void wasHPLost(DamageInfo info, int damageAmount){
        if (damageAmount > 0 && info.owner == this.owner){
            flash();
            addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount),
                    this.amount));
        }
    }

    @Override
    public int onLoseTempHp(DamageInfo info, int i){
        if (i > 0 && (i <= TempHPField.tempHp.get(AbstractDungeon.player) && info.owner == this.owner)){
            flash();
            addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount),
                    this.amount));
        }
        return i;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new Rupture_BPower(owner, amount);
    }
}
