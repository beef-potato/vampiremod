package vampiremod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vampiremod.cards.tempCards.Blood;

import static vampiremod.vampiremod.makeID;

public class DrainagePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("DrainagePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    public DrainagePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
//            for (int i = 0; i < this.amount; i++) {
//// 决定把所有的食物卡，统一为一张卡；或者是加入一张特殊的卡。
//                addToBot(new MakeTempCardInHandAction(new Blood()));
//            }
            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, amount*2));
            addToBot(new MakeTempCardInHandAction(new Blood(), amount));
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }


    public void updateDescription() {
            this.description = DESCRIPTIONS[0];
        }

    //Optional, for CloneablePowerInterface.
    @Override
    public AbstractPower makeCopy() {
        return new DrainagePower(owner, amount);
    }
}


