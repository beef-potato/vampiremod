package reskinContent.skinCharacter.skins;


import reskinContent.skinCharacter.AbstractSkin;
import reskinContent.reskinContent;

public class VampireNurse extends AbstractSkin {
    public String getAssetPath(String filename) {
        return reskinContent.assetPath("img/" + getClass().getSimpleName() + "/" + filename);
    }


}
