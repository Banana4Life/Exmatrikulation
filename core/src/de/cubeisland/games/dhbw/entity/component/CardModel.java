package de.cubeisland.games.dhbw.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

public class CardModel extends Component {
    private Decal frontDecal;
    private Decal backDecal;

    private static TextureRegion backTex;
}
