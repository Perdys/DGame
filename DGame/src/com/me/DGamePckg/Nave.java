package com.me.DGamePckg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Nave extends Pantalla {

    SpriteBatch batch;
    OrthographicCamera camara;
    TextureRegion currentFrame;
    float aceleracionx, aceleraciony, stateTime = 0;

    public Nave(OrthographicCamera camara) {
        this.camara = camara;
        batch = new SpriteBatch();
    }

    public int MoverNave (Animation nave, float[] posicionNave) {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = nave.getKeyFrame(stateTime, true);
        int naveAncho = (int) (nave.getKeyFrame(stateTime).getRegionWidth() * proporcion_ancho_nave);
        int naveAlto = (int) (nave.getKeyFrame(stateTime).getRegionHeight() * proporcion_alto_nave);
        aceleracionx = Gdx.input.getAccelerometerX() * 2;
        aceleraciony = Gdx.input.getAccelerometerY() * 2;

        if ((Math.abs(aceleracionx) > 1f) && (0 <= posicionNave[0] && aceleracionx > 0) || (aceleracionx < 0 && posicionNave[0] < anchura_juego - naveAncho)) {
            batch.setProjectionMatrix(camara.combined);
            batch.begin();
            batch.draw(currentFrame, posicionNave[0] -= aceleracionx, posicionNave[1], naveAncho, naveAlto);
            batch.end();
        } else {
            batch.setProjectionMatrix(camara.combined);
            batch.begin();
            batch.draw(currentFrame, posicionNave[0], posicionNave[1], naveAncho, naveAlto);
            batch.end();
        }

        if (!(Math.abs(aceleraciony) > 2f))
            return 1;

        if (aceleraciony < 2f)
            return 2;

        return 0;
    }

    public void dispose() {
        batch.dispose();
    }
}