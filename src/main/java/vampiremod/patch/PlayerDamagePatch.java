package vampiremod.patch;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CtBehavior;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import vampiremod.patch.interfaces.OnLoseTempHpCard;

import java.util.ArrayList;
import java.util.Iterator;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage",
        requiredModId = "stslib"
)

public class PlayerDamagePatch {
    static boolean hadTempHP;
    public PlayerDamagePatch(){}

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"damageAmount", "hadBlock"}
    )
    public static void Insert(AbstractCreature __instance, DamageInfo info, @ByRef int[] damageAmount, @ByRef boolean[] hadBlock) {
        hadTempHP = false;
        if (damageAmount[0] > 0) {
            Iterator var4 = DamageModifierManager.getDamageMods(info).iterator();
            while(var4.hasNext()) {
                AbstractDamageModifier mod = (AbstractDamageModifier)var4.next();
                if (mod.ignoresTempHP(__instance)) {
                    return;
                }
            }

            int temporaryHealth = (Integer)TempHPField.tempHp.get(__instance);
            if (temporaryHealth > 0) {
                if (__instance instanceof AbstractPlayer) {
                    // 从弃牌堆寻找继承了 OnLoseTempHpCard 的卡牌
                    // card version of OnLoseTempHpCard
                    Iterator<AbstractCard> var9 = ((AbstractPlayer)__instance).discardPile.group.iterator();

                    while(var9.hasNext()) {
                        AbstractCard card = (AbstractCard)var9.next();
                        if (card instanceof OnLoseTempHpCard) {
                            damageAmount[0] = ((OnLoseTempHpCard)card).onLoseTempHp(info, damageAmount[0]);
                        }
                    }
                }

                hadTempHP = true;
            }

        }
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "decrementBlock");
            return offset(LineFinder.findInOrder(ctMethodToPatch, new ArrayList(), finalMatcher), 1);
        }

        private static int[] offset(int[] originalArr, int offset) {
            for(int i = 0; i < originalArr.length; ++i) {
                originalArr[i] += offset;
            }

            return originalArr;
        }
    }
}


