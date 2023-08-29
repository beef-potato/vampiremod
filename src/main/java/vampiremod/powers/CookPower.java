package vampiremod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vampiremod.cards.skill.*;
import vampiremod.cards.tempCards.GreenTea;
import vampiremod.cards.tempCards.MysteryMeat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static vampiremod.vampiremod.makeID;

public class CookPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("CookPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    public CookPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    private static final List<AbstractCard> FOODS = new ArrayList<>();

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (int i = 0; i < this.amount; i++) {
// 决定把所有的食物卡，统一为一张卡；或者是加入一张特殊的卡。
                int temp  = generateRandomInt();

                switch (temp){
                    case 0:
                        addToBot(new MakeTempCardInHandAction(new Shortcake(), this.amount, false));
                        break;
                    case 1:
                        addToBot(new MakeTempCardInHandAction(new BlackTea(), this.amount, false));
                        break;
                    case 2:
                        addToBot(new MakeTempCardInHandAction(new GreenTea(), this.amount, false));
                        break;
                    case 3:
                        addToBot(new MakeTempCardInHandAction(new Coffee(), this.amount, false));
                        break;
                    case 4:
                        addToBot(new MakeTempCardInHandAction(new MysteryMeat(), this.amount, false));
                        break;
                }
            }
        }
    }
    private int generateRandomInt() {
        Random random = new Random();
        return random.nextInt(5);
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
        return new CookPower(owner, amount);
    }
}


