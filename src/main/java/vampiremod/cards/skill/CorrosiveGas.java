package vampiremod.cards.skill;

import character.Vampire;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import vampiremod.cards.BaseCard;
import vampiremod.util.CardInfo;

import java.util.ArrayList;
import java.util.HashSet;

import static vampiremod.vampiremod.makeID;

public class CorrosiveGas extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "CorrosiveGas", //Card ID. Will be prefixed with mod id, so the final ID will be "modID:MyCard" with
            // whatever your mod's ID is.
            1, //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to
            // what you want to see what to use.
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL
            // and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            Vampire.Enums.CARD_COLOR //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
    );
    public static final String ID = makeID(cardInfo.baseId);
    private static final int HP_LOST = 3;


    public static final ArrayList<String> stealPowIDs = new ArrayList<String>() {{
        add(ArtifactPower.POWER_ID);
        add(PlatedArmorPower.POWER_ID);
        add(ThornsPower.POWER_ID);
        add(MetallicizePower.POWER_ID);
    }};

    public CorrosiveGas() {
        super(cardInfo); //Pass the cardInfo to the BaseCard constructor.
        setCostUpgrade(0);
        setExhaust(true, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, HP_LOST));

        for (AbstractMonster mo :AbstractDungeon.getMonsters().monsters) {
            for (AbstractPower pow : mo.powers) {
                if ((pow.type == AbstractPower.PowerType.BUFF) && (!pow.canGoNegative || pow.amount > 0)) {
                    addToBot(new RemoveSpecificPowerAction(pow.owner, p, pow.ID));
                    break;
                }
            }
        }
        if (this.upgraded){
            addToBot(new ApplyPowerAction(p, p,  new ArtifactPower(p, 1),1 ));
        }

    }


    @Override
    public AbstractCard makeCopy() {
        return new CorrosiveGas();
    }

}

