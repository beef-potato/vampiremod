package vampiremod.relics;

import character.Vampire;
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static vampiremod.vampiremod.makeID;

public class HolyCup extends BaseRelic implements ClickableRelic {
    private static final String NAME = "HolyCup"; //The name will be used for determining the image file as well as
    // the
    // ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity./SHOP/STARTER/SPECIAL/UNCOMMON/RARE
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private static int CUP_LEVEL = 0;
    private static final int HEAL_AMOUNT = 8;

    public HolyCup() {
        super(ID, NAME, Vampire.Enums.CARD_COLOR, RARITY, SOUND);
}
    public void onEquip() {
        this.counter = 0;
    }

    public int CupDamage(){
        double cup_damage = 4 * Math.pow(CUP_LEVEL, 2);
        if (cup_damage == 0){
            cup_damage = 1;
        }
        return (int) cup_damage;
    }

    public int SEP_FLOOR(){
        return (6 - CUP_LEVEL);
    }
    public void onEnterRoom(AbstractRoom room) {
            setCounter(counter + 1);
        if (this.counter >= SEP_FLOOR()){
            AbstractDungeon.player.heal(HEAL_AMOUNT);
            setCounter(0);
        }
    }

    @Override
    public void setCounter(int counter) {
        this.counter = counter;
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + HEAL_AMOUNT + DESCRIPTIONS[1] + CUP_LEVEL;
    }

    @Override
    public void onRightClick() {
//        addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, CupDamage()));
        // to see what's difference.
        if(CUP_LEVEL < 5) {
            flash();
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX,
                    AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.player.damage(new DamageInfo(null, this.CupDamage()));
            CUP_LEVEL += 1;

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
        return new HolyCup();
    }
}
