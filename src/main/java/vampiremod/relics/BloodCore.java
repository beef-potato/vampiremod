package vampiremod.relics;

import character.Vampire;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static vampiremod.vampiremod.makeID;

public class BloodCore extends BaseRelic {
    private static final String NAME = "BloodCore"; //The name will be used for determining the image file as
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity./SHOP/STARTER/SPECIAL/UNCOMMON/RARE
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public BloodCore() {
        super(ID, NAME, Vampire.Enums.CARD_COLOR, RARITY, SOUND);
}
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart(){
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new MakeTempCardInDrawPileAction(new Injury(), 2, true,true));
    }

    public void onEquip(){
        AbstractDungeon.player.energy.energyMaster++;
    }

    public void onUnequip(){
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new BloodCore();
    }
}
