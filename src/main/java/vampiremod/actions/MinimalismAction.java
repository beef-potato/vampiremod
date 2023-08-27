package vampiremod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.Iterator;

public class MinimalismAction extends AbstractGameAction {
    private int blockPerCard;

    public MinimalismAction(int blockAmount) {
        this.blockPerCard = blockAmount;
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.BLOCK;
    }

    public void update() {
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList();

        Iterator var3 = AbstractDungeon.player.discardPile.group.iterator();
        Iterator var4 = AbstractDungeon.player.drawPile.group.iterator();

        AbstractCard c;
        while(var3.hasNext()){
            c = (AbstractCard) var3.next();
            cardsToExhaust.add(c);
        }

        var3 = cardsToExhaust.iterator();

        while(var3.hasNext()){
            c = (AbstractCard) var3.next();
            this.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
        }

        AbstractCard c3;
        while(var4.hasNext()){
            c3 = (AbstractCard) var4.next();
            cardsToExhaust.add(c3);
        }

        var4 = cardsToExhaust.iterator();
        while (var4.hasNext()){
            c3 = (AbstractCard) var4.next();
            this.addToTop(new ExhaustSpecificCardAction(c3, AbstractDungeon.player.drawPile));
        }

        this.isDone = true;
    }
}

