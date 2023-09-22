package vampiremod.cards.attack;

import character.Vampire;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import vampiremod.cards.BaseCard;
import vampiremod.patch.interfaces.OnLoseTempHpCard;
import vampiremod.util.CardInfo;

import static vampiremod.vampiremod.makeID;

public class FlurryOfBlows extends BaseCard implements OnLoseTempHpCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FlurryOfBlows", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            0, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL
            // and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );
    public static final String ID = makeID(cardInfo.baseId);
    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    public FlurryOfBlows() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it increases when upgraded.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p,
                this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void tookDamage() {
        super.tookDamage();
        addToBot(new DiscardToHandAction(this));
    }

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        if (i>0 && (i <= TempHPField.tempHp.get(AbstractDungeon.player))){
            addToBot(new DiscardToHandAction(this));
        }
        return i;
    }

    @Override
    public AbstractCard makeCopy() {
        return new FlurryOfBlows();
    }


}

