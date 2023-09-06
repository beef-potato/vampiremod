package vampiremod.cards.attack;

import character.Vampire;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import vampiremod.cards.BaseCard;
import vampiremod.util.CardInfo;

import static vampiremod.vampiremod.makeID;
import java.util.Random;

public class Roulette extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Roulette", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            -2, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ALL, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            // what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then
            // SPECIAL
            // and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );

    public static final String ID = makeID(cardInfo.baseId);

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 5;

    public Roulette() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it increases when upgraded.
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public void triggerWhenDrawn() {

        int all_target = create_num();
        Random random = new Random();
        double randomValue = random.nextDouble();
        double playerPotion =  1.0  / (all_target)  / 100;

        addToBot(new VFXAction(new GrandFinalEffect(), 0.7F));

        if (randomValue < playerPotion){
            addToBot(new GainBlockAction(AbstractDungeon.player, damage/2));
            // will be better if it is a bullet effect.
        }
        else {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.FIRE));
        }

        addToBot(new DiscardSpecificCardAction(this, AbstractDungeon.player.hand));

    }

    private int create_num() {
        int count = 0;
        for (AbstractMonster m2 : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!m2.isDeadOrEscaped()) {
                count++;
            }
        }
        count += 1;

    return count;
    }


    @Override
    public AbstractCard makeCopy() {
        return new Roulette();
    }
}

