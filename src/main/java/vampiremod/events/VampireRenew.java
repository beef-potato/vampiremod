package vampiremod.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import vampiremod.relics.ChaliceofBlood;


public class VampireRenew extends AbstractImageEvent {
    private enum CurScreen {
        INTRO, RESULT;
    }
    public static final String ID = "State Room";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("State Room");

    public static final String NAME = eventStrings.NAME;

    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String DIALOG_1 = DESCRIPTIONS[0];
    private static final String PREY_RESULT = DESCRIPTIONS[1];
    private CurScreen screen = CurScreen.INTRO;
    private static final String TAKE_RESULT = DESCRIPTIONS[2];
    private static final String LEAVE_RESULT = DESCRIPTIONS[3];
    private int loseAmt = 9;
    private int goldAmt = 100;

    private AbstractRelic relicMetric = null;

    public VampireRenew(){
        super(ID, DIALOG_1, "vampiremod/events/mausoleum.jpg");

        //This is where you would create your dialog options
        this.loseAmt = (int) (AbstractDungeon.player.maxHealth * 0.1);
        this.imageEventText.setDialogOption(OPTIONS[0] + this.goldAmt + OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2]);
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }
    @Override
    protected void buttonEffect(int buttonPressed) {
       switch(this.screen) {
           case INTRO:
               switch (buttonPressed) {
                   case 0:
                       AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldAmt));
                       AbstractDungeon.player.gainGold(this.goldAmt);
                       this.imageEventText.updateBodyText(PREY_RESULT);
                       AbstractEvent.logMetricGainGold("State Room", "Prey", goldAmt);
                       break;
                   case 1:


                       if (AbstractDungeon.player.hasRelic(ChaliceofBlood.ID)) {
                           this.relicMetric = RelicLibrary.getRelic(Circlet.ID).makeCopy();
                       } else {
                           this.relicMetric = RelicLibrary.getRelic(ChaliceofBlood.ID).makeCopy();
                       }
                       AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH/2), (Settings.HEIGHT/2),
                               this.relicMetric);

                       this.imageEventText.updateBodyText(TAKE_RESULT);
                       AbstractDungeon.player.decreaseMaxHealth(loseAmt);
                       CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                       CardCrawlGame.sound.play("BLUNT_FAST");

                       AbstractEvent.logMetricObtainRelicAndLoseMaxHP("State Room", "Lose Max HP", this.relicMetric,
                               this.loseAmt
                       );
                       this.screenNum =1;
                       break;
                   case 2:
                       this.imageEventText.updateBodyText(LEAVE_RESULT);
                       AbstractEvent.logMetricIgnored(VampireRenew.ID);
                       break;
               }

               this.imageEventText.clearAllDialogs();
               this.imageEventText.setDialogOption(OPTIONS[3]);
               this.screen = CurScreen.RESULT;
               return;
       }

                openMap();
    }

    public void logMetric(String actionTaken) {
        AbstractEvent.logMetric("State Room", actionTaken);
}
    }
