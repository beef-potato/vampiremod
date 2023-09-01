package vampiremod.relics;

import character.Vampire;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static vampiremod.vampiremod.makeID;

public class BloodShield extends BaseRelic {
    private static final String NAME = "BloodShield"; //The name will be used for determining the image file as
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity./SHOP/STARTER/SPECIAL/UNCOMMON/RARE
    private static final LandingSound SOUND = LandingSound.HEAVY; //The sound played when the relic is clicked.
    private static final int TempHP = 10;

    public BloodShield() {
        super(ID, NAME, Vampire.Enums.CARD_COLOR, RARITY, SOUND);
}
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + TempHP + DESCRIPTIONS[1];
    }

    public void atBattleStart(){
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new AddTemporaryHPAction(AbstractDungeon.player,null, TempHP));
        this.grayscale = false;
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }
    @Override
    public AbstractRelic makeCopy()
    {
        return new BloodShield();
    }
}
