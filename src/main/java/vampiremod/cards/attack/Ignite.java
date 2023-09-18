package vampiremod.cards.attack;

import character.Vampire;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vampiremod.cards.BaseCard;
import vampiremod.util.CardInfo;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.Objects;

import static vampiremod.vampiremod.makeID;

public class Ignite extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Ignite", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL
            // and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );
    public static final String ID = makeID(cardInfo.baseId);
    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;
    private static final int Exhaust_NUM = 1;
    private static final int HP_LOST = 2;

    public Ignite() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it increases when upgraded.
    }

    private ArrayList<AbstractCard> temp_hand = new ArrayList<>();

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, HP_LOST));

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        if (p.hand.group.size()>1){
            if (this.upgraded) {
                addToBot(new SelectCardsInHandAction(Exhaust_NUM,
                        cardStrings.EXTENDED_DESCRIPTION[0],
                        cards -> {
                            AbstractCard c = cards.get(0);
                            c.exhaust = true;
                            addToBot(new NewQueueCardAction(c, true, false, true));
                        }));

            } else {
                for (AbstractCard c : p.hand.group) {
                    if (!Objects.equals(c.cardID, this.cardID))
                        temp_hand.add(c);
                }
                AbstractCard c2 = temp_hand.get(AbstractDungeon.cardRandomRng.random(p.hand.group.size() - 2));
                c2.exhaust = true;
                addToBot(new NewQueueCardAction(c2, true, false, true));

            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Ignite();
    }

}

