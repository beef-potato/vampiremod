package vampiremod.relics;

import character.Vampire;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;
import java.util.Iterator;

import static vampiremod.vampiremod.makeID;

public class EnhancedTusk extends BaseRelic {
    private static final String NAME = "EnhancedTusk"; //The name will be used for determining the image file as
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity./SHOP/STARTER/SPECIAL/UNCOMMON/RARE
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private static final float LEACH_RATIO = 50;

    public EnhancedTusk() {
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
                && abstractCreature != AbstractDungeon.player
        && !this.grayscale) {
            int healAmount = (int) (n * LEACH_RATIO) / 100;
            if (healAmount < 0) {
                return;
            }
            if (healAmount > abstractCreature.currentHealth) {
                healAmount = abstractCreature.currentHealth;
            }
            if (healAmount > 0) {
                this.flash();
//                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player,
                        AbstractDungeon.player, healAmount, 0.1F));
            }
        }
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(Tusk.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(Tusk.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(Tusk.ID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + LEACH_RATIO + DESCRIPTIONS[1];
    }

}
