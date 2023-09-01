package vampiremod.relics;

import character.Vampire;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static vampiremod.vampiremod.makeID;

public class IVBag extends BaseRelic implements ClickableRelic {
    private static final String NAME = "IVBag"; //The name will be used for determining the image file as
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity./SHOP/STARTER/SPECIAL/UNCOMMON/RARE
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private static final int GOLDS = 75;
    private static final int HPLOSE = 10;
    private static boolean USAGE = false;

    public IVBag() {
        super(ID, NAME, Vampire.Enums.CARD_COLOR, RARITY, SOUND);
}

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + HPLOSE + DESCRIPTIONS[1] + GOLDS + DESCRIPTIONS[2];
    }


    public void onEnterRoom(AbstractRoom room){
        if (room instanceof com.megacrit.cardcrawl.rooms.RestRoom){
            USAGE = true;
        } else if (room instanceof com.megacrit.cardcrawl.rooms.ShopRoom) {
            USAGE = true;
        } else if (room instanceof com.megacrit.cardcrawl.rooms.TreasureRoom) {
            USAGE = true;
        } else USAGE = room instanceof com.megacrit.cardcrawl.rooms.EventRoom;
    }

    @Override
    public void onRightClick() {
//        addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, CupDamage()));
        // to see what's difference.
        if (USAGE){
                flash();
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX,
                        AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.player.damage(new DamageInfo(null, HPLOSE, DamageInfo.DamageType.HP_LOSS));
                AbstractDungeon.player.gainGold(GOLDS);

                //update description.
                description = getUpdatedDescription();
                tips.clear();
                tips.add(new PowerTip(name, description));
                initializeTips();
        }
    }

    @Override
    public AbstractRelic makeCopy()
    {
        return new IVBag();
    }
}
