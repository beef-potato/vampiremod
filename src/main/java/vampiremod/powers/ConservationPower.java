package vampiremod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;


import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static vampiremod.vampiremod.makeID;

public class ConservationPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("ConservationPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    //The only thing this controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if they're a buff or debuff.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public ConservationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    private static final float LEACH_RATIO = 20;

//    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
//        if (this.owner.isPlayer & ((int)(LEACH_RATIO * damageAmount)>=1) &  info.type == DamageInfo.DamageType.NORMAL){
//            this.flash();
////            this.addToBot(new HealAction(owner, owner, damageAmount));
//            player.heal((int)(LEACH_RATIO * damageAmount), true);
//
//            return damageAmount;
//        }
//        return damageAmount;
//    }

    public void onAttack(final DamageInfo damageInfo, final int n, final AbstractCreature abstractCreature){
        if (damageInfo.type == DamageInfo.DamageType.NORMAL && damageInfo.output > 0 && ! abstractCreature.isPlayer){
            int healAmount = (int) (n*LEACH_RATIO /100);
            if (healAmount < 0){
                return;
            }
            if (healAmount > abstractCreature.currentHealth){
                healAmount = abstractCreature.currentHealth;
            }
            if (healAmount > 0){
                this.flash();
                AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, healAmount));

            }
        }
    }

    public void updateDescription() {
    this.description = DESCRIPTIONS[0];

    }

    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new ConservationPower(owner, amount);
    }
}
