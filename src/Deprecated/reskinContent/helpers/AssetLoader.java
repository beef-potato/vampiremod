package reskinContent.helpers;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class AssetLoader {
    private AssetManager assets = new AssetManager();

    public Texture loadImage(String filename){
        if (!this.assets.isLoaded(filename, Texture.class)) {
            TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
            param.minFilter = Texture.TextureFilter.Linear;
            param.magFilter = Texture.TextureFilter.Linear;
            this.assets.load(filename, Texture.class, param);
            try {
                this.assets.finishLoadingAsset(filename);
            } catch (GdxRuntimeException e) {
                return null;
            }
        }
        return (Texture)this.assets.get(filename, Texture.class);
    }

    public TextureAtlas loadAtlas(String fileName) {
        if (!this.assets.isLoaded(fileName, TextureAtlas.class)) {
            this.assets.load(fileName, TextureAtlas.class);
            this.assets.finishLoadingAsset(fileName);
        }
        return (TextureAtlas)this.assets.get(fileName, TextureAtlas.class);
    }
}