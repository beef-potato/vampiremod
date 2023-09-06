package reskinContent.skinCharacter.skins;


import reskinContent.reskinContent;
import reskinContent.skinCharacter.AbstractSkin;

public class VampireForm extends AbstractSkin {
    public String getAssetPath(String filename) {
        return reskinContent.assetPath("img/" + getClass().getSimpleName() + "/" + filename);
    }


}
