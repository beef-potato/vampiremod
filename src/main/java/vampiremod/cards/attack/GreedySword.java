package vampiremod.cards.attack;

import character.Vampire;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.BloodyIdol;
import vampiremod.cards.BaseCard;
import vampiremod.cards.tempCards.Blood;
import vampiremod.util.CardInfo;

import java.util.Iterator;
import java.util.Objects;

import static vampiremod.vampiremod.makeID;

public class GreedySword extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "GreedySword", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );
    public static final String ID = makeID(cardInfo.baseId);
    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;
    private static final int HP_LOST = 2;
    private static final int MAGIC = 2;

    public GreedySword() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it increases when upgraded.
        setMagic(MAGIC);
        cardsToPreview = new Blood();
    }

    public static int BloodNum(){
        int count = 0;
        Iterator<AbstractCard> var1 = AbstractDungeon.player.exhaustPile.group.iterator();
        while (var1.hasNext()){
            AbstractCard c  = var1.next();
            if (Objects.equals(c.cardID, Blood.ID)){
                count++;
            }
        }
        return count;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, HP_LOST));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * BloodNum();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    @Override
    public AbstractCard makeCopy() {
        return new GreedySword();
    }

}

