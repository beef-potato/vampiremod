package vampiremod.relics;

import character.Vampire;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import static vampiremod.vampiremod.makeID;

public class Tusk extends BaseRelic {
    private static final String NAME = "Tusk"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity./SHOP/STARTER/SPECIAL/UNCOMMON/RARE
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private static final float LEACH_RATIO = 30;
    private static final float COUNT = 3;

    public Tusk() {

        super(ID, NAME, Vampire.Enums.CARD_COLOR, RARITY, SOUND);
    }


    public void atBattleStart() {
        this.counter = 0;
    }

    public void atTurnStart() {
        if (!this.grayscale)
            this.counter++;
        if (this.counter == 4) {
            flash();
//            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.counter = -1;
            this.grayscale = true;
        }
    }

    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }

    public void onAttack(final DamageInfo damageInfo, final int n, final AbstractCreature abstractCreature) {
        if (damageInfo.type == DamageInfo.DamageType.NORMAL && damageInfo.output > 0 && abstractCreature != null
                && abstractCreature != AbstractDungeon.player && !this.grayscale
        ) {
            int healAmount = (int) (n * LEACH_RATIO) / 100;
            if (healAmount < 0) {
                return;
            }
            if (healAmount > abstractCreature.currentHealth) {
                healAmount = abstractCreature.currentHealth;
            }
            if (healAmount > 0) {
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player,
                        healAmount, 0.1F));
                //XFast duration

            }
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }

}
