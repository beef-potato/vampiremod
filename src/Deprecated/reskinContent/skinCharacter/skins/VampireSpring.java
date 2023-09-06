package reskinContent.skinCharacter.skins;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import reskinContent.reskinContent;
import reskinContent.skinCharacter.AbstractSkin;
import vampiremod.util.TextureLoader;

public class VampireSpring extends AbstractSkin {

    public VampireSpring() {
        this.NAME = (CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinVampire")).TEXT[1];
        this.DESCRIPTION = (CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinVampire")).EXTRA_TEXT[1];
//        this.portraitAnimation_IMG = TextureLoader.getTexture(getAssetPath("portrait.png"));
        this.portraitAnimation_IMG =
                TextureLoader.getTexture(vampiremod.vampiremod.characterPath("select/portrait_3" + ".png"));
        this.portraitAnimationType = 1;
        this.forcePortraitAnimationType = true;
        this.SHOULDER1 = vampiremod.vampiremod.characterPath("shoulder.png");
        this.SHOULDER2 = vampiremod.vampiremod.characterPath("shoulder2.png");
//        this.CORPSE = getAssetPath("corpse.png");
        this.CORPSE = vampiremod.vampiremod.characterPath("corpse.png");
        this.portraitAtlasPath = getAssetPath("animation/VampireSpring");
        this.atlasURL = this.portraitAtlasPath + ".atlas";
        this.jsonURL = this.portraitAtlasPath + ".json";
        this.renderscale = 1.0F;
        loadPortraitAnimation();
        this.portraitAtlasPath = null;
    }

    public Texture updateBgImg() {
        reskinContent.saveSettings();
        return this.portraitAnimation_IMG;
    }

    public void setAnimation() {
        this.portraitState.addAnimation(0, "normal", true, 0.0F);
        // animationName changed
    }

    public void render(SpriteBatch sb) {
        this.portraitState.update(Gdx.graphics.getDeltaTime());
        this.portraitState.apply(this.portraitSkeleton);
        this.portraitSkeleton.updateWorldTransform();
        this.portraitSkeleton.setPosition(1400.0F * Settings.scale, Settings.HEIGHT - 1050.0F * Settings.scale);
        this.portraitSkeleton.setColor(Color.WHITE.cpy());
        this.portraitSkeleton.setFlipX(true);
        this.portraitSkeleton.getRootBone().setScale(1.6F);
        sb.end();
        CardCrawlGame.psb.begin();
        AbstractCreature.sr.draw(CardCrawlGame.psb, this.portraitSkeleton);
        CardCrawlGame.psb.end();
        sb.begin();
    }

    public String getAssetPath(String filename) {
        return reskinContent.assetPath("img/" + getClass().getSimpleName() + "/" + filename);
    }


}
