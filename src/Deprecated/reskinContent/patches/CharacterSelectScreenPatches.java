package reskinContent.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import javassist.CtBehavior;
import reskinContent.reskinContent;
import reskinContent.skinCharacter.AbstractSkinCharacter;
import reskinContent.skinCharacter.VampireSkin;

public class CharacterSelectScreenPatches {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(reskinContent.makeID("ReSkin"));

    public static final String[] TEXT = uiStrings.TEXT;

    public static Hitbox reskinRight;

    public static Hitbox reskinLeft;

    public static Hitbox portraitAnimationLeft;

    public static Hitbox portraitAnimationRight;

    private static float reskin_Text_W = FontHelper.getSmartWidth(FontHelper.cardTitleFont, TEXT[1], 9999.0F, 0.0F);

    private static float reskin_W = reskin_Text_W + 200.0F * Settings.scale;

    private static float reskinX_center = 600.0F * Settings.scale;

    public static float allTextInfoX = 0.0F;

    public static float allTextInfoY = 0.0F;

    private static boolean bgIMGUpdate = false;

    public static ArrayList<AbstractGameEffect> char_effectsQueue = new ArrayList<>();

    public static ArrayList<AbstractGameEffect> char_effectsQueue_toRemove = new ArrayList<>();

    public static AbstractSkinCharacter[] characters =
            new AbstractSkinCharacter[] {new VampireSkin()};

    @SpirePatch(clz = CharacterSelectScreen.class, method = "initialize")
    public static class CharacterSelectScreenPatch_Initialize {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance) {
            reskinContent.loadSettings();
            CharacterSelectScreenPatches.char_effectsQueue.clear();
            CharacterSelectScreenPatches.reskinRight = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
            CharacterSelectScreenPatches.reskinLeft = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
            CharacterSelectScreenPatches.portraitAnimationLeft = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
            CharacterSelectScreenPatches.portraitAnimationRight = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
            CharacterSelectScreenPatches.reskinRight.move(Settings.WIDTH / 2.0F + CharacterSelectScreenPatches.reskin_W / 2.0F - CharacterSelectScreenPatches.reskinX_center + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 0.0F * Settings.scale);
            CharacterSelectScreenPatches.reskinLeft.move(Settings.WIDTH / 2.0F - CharacterSelectScreenPatches.reskin_W / 2.0F - CharacterSelectScreenPatches.reskinX_center + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 0.0F * Settings.scale);
            CharacterSelectScreenPatches.portraitAnimationLeft.move(Settings.WIDTH / 2.0F - CharacterSelectScreenPatches.reskin_W / 2.0F - CharacterSelectScreenPatches.reskinX_center + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 120.0F * Settings.scale);
            CharacterSelectScreenPatches.portraitAnimationRight.move(Settings.WIDTH / 2.0F + CharacterSelectScreenPatches.reskin_W / 2.0F - CharacterSelectScreenPatches.reskinX_center + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 120.0F * Settings.scale);
        }

        @SpirePatch(clz = CharacterOption.class, method = "renderInfo")
        public static class CharacterOptionRenderInfoPatch {
            @SpireInsertPatch(locator = CharacterSelectScreenPatches.CharacterSelectScreenPatch_Initialize.renderRelicsLocator.class, localvars = {"infoX", "infoY", "charInfo", "flavorText"})
            public static SpireReturn<Void> Insert(CharacterOption _instance, SpriteBatch sb, float infoX, float infoY, CharSelectInfo charInfo, @ByRef String[] flavorText) {
                CharacterSelectScreenPatches.allTextInfoX = infoX - 200.0F * Settings.scale;
                CharacterSelectScreenPatches.allTextInfoY = infoY + 250.0F * Settings.scale;
                for (AbstractSkinCharacter character : CharacterSelectScreenPatches.characters) {
                    if (charInfo.name.equals(character.id))
                        flavorText[0] = (character.skins[character.reskinCount]).DESCRIPTION;
                }
                return SpireReturn.Continue();
            }
        }

        private static class renderRelicsLocator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(CharacterOption.class, "renderRelics");
                return LineFinder.findAllInOrder(ctMethodToPatch, (Matcher)methodCallMatcher);
            }
        }

        @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
        public static class CharacterSelectScreenPatch_Render {
            @SpirePostfixPatch
            public static void Initialize(CharacterSelectScreen __instance, SpriteBatch sb) {
                for (CharacterOption o : __instance.options) {
                    for (AbstractSkinCharacter c : CharacterSelectScreenPatches.characters) {
                        c.InitializeReskinCount();
                        if (o.name.equals(c.id) && o.selected) {
                            CharacterSelectScreenPatches.reskinRight.render(sb);
                            CharacterSelectScreenPatches.reskinLeft.render(sb);
                            CharacterSelectScreenPatches.portraitAnimationLeft.render(sb);
                            CharacterSelectScreenPatches.portraitAnimationRight.render(sb);
                            c.skins[c.reskinCount].extraHitboxRender(sb);
                            if (c.skins[c.reskinCount].hasAnimation() && c.reskinUnlock) {
                                if (CharacterSelectScreenPatches.portraitAnimationLeft.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - CharacterSelectScreenPatches.reskin_W / 2.0F - CharacterSelectScreenPatches.reskinX_center - 36.0F * Settings.scale + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 84.0F * Settings.scale, 0.0F, 0.0F, 48.0F, 48.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 48, 48, false, false);
                                if (CharacterSelectScreenPatches.portraitAnimationRight.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F + CharacterSelectScreenPatches.reskin_W / 2.0F - CharacterSelectScreenPatches.reskinX_center - 36.0F * Settings.scale + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 84.0F * Settings.scale, 0.0F, 0.0F, 48.0F, 48.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 48, 48, false, false);
                            }
                            if (c.reskinUnlock) {
                                if (CharacterSelectScreenPatches.reskinRight.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(ImageMaster.CF_RIGHT_ARROW, Settings.WIDTH / 2.0F + CharacterSelectScreenPatches.reskin_W / 2.0F - CharacterSelectScreenPatches.reskinX_center - 36.0F * Settings.scale + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY - 36.0F * Settings.scale, 0.0F, 0.0F, 48.0F, 48.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 48, 48, false, false);
                                if (CharacterSelectScreenPatches.reskinLeft.hovered || Settings.isControllerMode) {
                                    sb.setColor(Color.WHITE.cpy());
                                } else {
                                    sb.setColor(Color.LIGHT_GRAY.cpy());
                                }
                                sb.draw(ImageMaster.CF_LEFT_ARROW, Settings.WIDTH / 2.0F - CharacterSelectScreenPatches.reskin_W / 2.0F - CharacterSelectScreenPatches.reskinX_center - 36.0F * Settings.scale + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY - 36.0F * Settings.scale, 0.0F, 0.0F, 48.0F, 48.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 48, 48, false, false);
                            }
                            FontHelper.cardTitleFont.getData().setScale(1.0F);
                            FontHelper.losePowerFont.getData().setScale(0.8F);
                            if (c.skins[c.reskinCount].hasAnimation() && c.reskinUnlock) {
                                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, (CardCrawlGame.languagePack.getUIString(reskinContent.makeID("PortraitAnimationType"))).TEXT[(c.skins[c.reskinCount]).portraitAnimationType], Settings.WIDTH / 2.0F - CharacterSelectScreenPatches.reskinX_center + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 120.0F * Settings.scale, Settings.GOLD_COLOR.cpy());
                                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, (CardCrawlGame.languagePack.getUIString(reskinContent.makeID("PortraitAnimation"))).TEXT[0], Settings.WIDTH / 2.0F - CharacterSelectScreenPatches.reskinX_center + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 170.0F * Settings.scale, Settings.GOLD_COLOR);
                            }
                            if (c.reskinUnlock) {
                                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, CharacterSelectScreenPatches.TEXT[0], Settings.WIDTH / 2.0F - CharacterSelectScreenPatches.reskinX_center + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 50.0F * Settings.scale, Settings.GOLD_COLOR.cpy());
                                FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, (c.skins[c.reskinCount]).NAME, Settings.WIDTH / 2.0F - CharacterSelectScreenPatches.reskinX_center + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 0.0F * Settings.scale, Settings.GOLD_COLOR.cpy());
                            }
                        }
                    }
                }
            }
        }

        @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
        public static class CharacterSelectScreenPatch_portraitSkeleton {
            @SpireInsertPatch(rloc = 62)
            public static void Insert(CharacterSelectScreen __instance, SpriteBatch sb) {
                for (CharacterOption o : __instance.options) {
                    for (AbstractSkinCharacter c : CharacterSelectScreenPatches.characters) {
                        c.InitializeReskinCount();
                        if (o.name.equals(c.id) && o.selected && (c.skins[c.reskinCount]).portraitAnimationType != 0) {
                            c.skins[c.reskinCount].render(sb);
                            if (CharacterSelectScreenPatches.char_effectsQueue.size() > 0) {
                                for (int k = 0; k < CharacterSelectScreenPatches.char_effectsQueue.size(); k++) {
                                    if (!((AbstractGameEffect)CharacterSelectScreenPatches.char_effectsQueue.get(k)).isDone) {
                                        ((AbstractGameEffect)CharacterSelectScreenPatches.char_effectsQueue.get(k)).update();
                                        ((AbstractGameEffect)CharacterSelectScreenPatches.char_effectsQueue.get(k)).render(sb);
                                    } else {
                                        if (CharacterSelectScreenPatches.char_effectsQueue_toRemove == null)
                                            CharacterSelectScreenPatches.char_effectsQueue_toRemove = new ArrayList<>();
                                        if (!CharacterSelectScreenPatches.char_effectsQueue_toRemove.contains(CharacterSelectScreenPatches.char_effectsQueue.get(k)))
                                            CharacterSelectScreenPatches.char_effectsQueue_toRemove.add(CharacterSelectScreenPatches.char_effectsQueue.get(k));
                                    }
                                }
                                if (CharacterSelectScreenPatches.char_effectsQueue_toRemove != null)
                                    CharacterSelectScreenPatches.char_effectsQueue.removeAll(CharacterSelectScreenPatches.char_effectsQueue_toRemove);
                            }
                        }
                    }
                }
            }
        }
    }

    @SpirePatch(clz = CharacterOption.class, method = "updateHitbox")
    public static class CharacterOptionPatch_reloadAnimation {
        @SpireInsertPatch(rloc = 56)
        public static void Insert(CharacterOption __instance) {
            CharacterSelectScreenPatches.char_effectsQueue.clear();
            CharacterSelectScreenPatches.bgIMGUpdate = false;
            for (AbstractSkinCharacter c : CharacterSelectScreenPatches.characters) {
                c.InitializeReskinCount();
                if (__instance.name.equals(c.id) && (c.skins[c.reskinCount]).portraitAnimationType != 0) {
                    c.skins[c.reskinCount].clearWhenClick();
                    if (c.skins[c.reskinCount].hasAnimation())
                        c.skins[c.reskinCount].loadPortraitAnimation();
                    System.out.println("立绘载入2");
                }
            }
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class CharacterSelectScreenPatch_Update {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen __instance) {
            for (CharacterOption o : __instance.options) {
                for (AbstractSkinCharacter c : CharacterSelectScreenPatches.characters) {
                    c.InitializeReskinCount();
                    if (o.name.equals(c.id) && o.selected && c.reskinUnlock) {
                        if (!CharacterSelectScreenPatches.bgIMGUpdate) {
                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                            CharacterSelectScreenPatches.bgIMGUpdate = true;
                        }
                        if (InputHelper.justClickedLeft && CharacterSelectScreenPatches.reskinLeft.hovered) {
                            CharacterSelectScreenPatches.reskinLeft.clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                        }
                        if (InputHelper.justClickedLeft && CharacterSelectScreenPatches.reskinRight.hovered) {
                            CharacterSelectScreenPatches.reskinRight.clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                        }
                        if (InputHelper.justClickedLeft && CharacterSelectScreenPatches.portraitAnimationLeft.hovered && c.reskinCount > 0) {
                            CharacterSelectScreenPatches.portraitAnimationLeft.clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                        }
                        if (InputHelper.justClickedLeft && CharacterSelectScreenPatches.portraitAnimationRight.hovered && c.reskinCount > 0) {
                            CharacterSelectScreenPatches.portraitAnimationRight.clickStarted = true;
                            CardCrawlGame.sound.play("UI_CLICK_1");
                        }
                        if (CharacterSelectScreenPatches.reskinLeft.justHovered || CharacterSelectScreenPatches.reskinRight.justHovered)
                            CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
                        if ((CharacterSelectScreenPatches.portraitAnimationLeft.justHovered || CharacterSelectScreenPatches.portraitAnimationRight.justHovered) && c.reskinCount > 0)
                            CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
                        CharacterSelectScreenPatches.reskinRight.move(Settings.WIDTH / 2.0F + CharacterSelectScreenPatches.reskin_W / 2.0F - CharacterSelectScreenPatches.reskinX_center + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 0.0F * Settings.scale);
                        CharacterSelectScreenPatches.reskinLeft.move(Settings.WIDTH / 2.0F - CharacterSelectScreenPatches.reskin_W / 2.0F - CharacterSelectScreenPatches.reskinX_center + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 0.0F * Settings.scale);
                        CharacterSelectScreenPatches.portraitAnimationLeft.move(Settings.WIDTH / 2.0F - CharacterSelectScreenPatches.reskin_W / 2.0F - CharacterSelectScreenPatches.reskinX_center + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 120.0F * Settings.scale);
                        CharacterSelectScreenPatches.portraitAnimationRight.move(Settings.WIDTH / 2.0F + CharacterSelectScreenPatches.reskin_W / 2.0F - CharacterSelectScreenPatches.reskinX_center + CharacterSelectScreenPatches.allTextInfoX, CharacterSelectScreenPatches.allTextInfoY + 120.0F * Settings.scale);
                        CharacterSelectScreenPatches.reskinLeft.update();
                        CharacterSelectScreenPatches.reskinRight.update();
                        if (c.reskinCount > 0) {
                            CharacterSelectScreenPatches.portraitAnimationLeft.update();
                            CharacterSelectScreenPatches.portraitAnimationRight.update();
                        }
                        if (CharacterSelectScreenPatches.reskinRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            CharacterSelectScreenPatches.reskinRight.clicked = false;
                            c.skins[c.reskinCount].clearWhenClick();
                            CharacterSelectScreenPatches.char_effectsQueue.clear();
                            if (c.reskinCount < c.skins.length - 1) {
                                c.reskinCount++;
                            } else {
                                c.reskinCount = 0;
                            }
                            c.skins[c.reskinCount].loadPortraitAnimation();
                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                        }
                        if (CharacterSelectScreenPatches.reskinLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            CharacterSelectScreenPatches.reskinLeft.clicked = false;
                            c.skins[c.reskinCount].clearWhenClick();
                            CharacterSelectScreenPatches.char_effectsQueue.clear();
                            if (c.reskinCount > 0) {
                                c.reskinCount--;
                            } else {
                                c.reskinCount = c.skins.length - 1;
                            }
                            c.skins[c.reskinCount].loadPortraitAnimation();
                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                        }
                        if (CharacterSelectScreenPatches.portraitAnimationLeft.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            CharacterSelectScreenPatches.portraitAnimationLeft.clicked = false;
                            c.skins[c.reskinCount].clearWhenClick();
                            CharacterSelectScreenPatches.char_effectsQueue.clear();
                            if ((c.skins[c.reskinCount]).portraitAnimationType <= 0) {
                                (c.skins[c.reskinCount]).portraitAnimationType = 2;
                            } else {
                                (c.skins[c.reskinCount]).portraitAnimationType--;
                            }
                            c.skins[c.reskinCount].loadPortraitAnimation();
                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                        }
                        if (CharacterSelectScreenPatches.portraitAnimationRight.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                            CharacterSelectScreenPatches.portraitAnimationRight.clicked = false;
                            c.skins[c.reskinCount].clearWhenClick();
                            CharacterSelectScreenPatches.char_effectsQueue.clear();
                            if ((c.skins[c.reskinCount]).portraitAnimationType >= 2) {
                                (c.skins[c.reskinCount]).portraitAnimationType = 0;
                            } else {
                                (c.skins[c.reskinCount]).portraitAnimationType++;
                            }
                            c.skins[c.reskinCount].loadPortraitAnimation();
                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                        }
                        c.skins[c.reskinCount].update();
                        if (c.skins[c.reskinCount].extraHitboxClickCheck())
                            __instance.bgCharImg = c.skins[c.reskinCount].updateBgImg();
                        c.InitializeReskinCount();
                    }
                }
            }
        }
    }
}
