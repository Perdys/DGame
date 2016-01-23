package com.me.DGamePckg;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

public class Colision extends Pantalla {

    float naveAncho, naveAlto, bloqueAncho, bloqueAlto;
    Polygon[] nave;
    Polygon[][] tipoBloquePoligono;
    float X100, X190, X205, X225, X285, X305, X320, X410, X512;
    float Y95, Y125, Y170, Y210, Y300, Y340, Y380, Y415, Y512;

    public Colision(Texture[] tiposBloque, Animation naveImagen) {
        naveAncho = naveImagen.getKeyFrame(0).getRegionWidth() * proporcion_ancho_nave;
        naveAlto = naveImagen.getKeyFrame(0).getRegionHeight() * proporcion_alto_nave;
        bloqueAncho = tiposBloque[0].getWidth() * proporcion_alto_bloque;
        bloqueAlto = tiposBloque[0].getHeight() * proporcion_ancho_bloque;

        tipoBloquePoligono = new Polygon[tiposBloque.length][3];
        nave = new Polygon[]{new Polygon(new float[]{0,0, 0,naveAlto / 2, 0.1f, naveAlto / 2, 0.1f, 0}),
                             new Polygon(new float[]{0,0, 0,naveAlto / 2, 0.1f, naveAlto / 2, 0.1f, 0})};

        X100 = (float) 0.19530 * bloqueAncho;        Y95 = (float) 00.18850 * bloqueAlto;
        X190 = (float) 0.37110 * bloqueAncho;        Y125 = (float) 0.24410 * bloqueAlto;
        X205 = (float) 0.40040 * bloqueAncho;        Y170 = (float) 0.33200 * bloqueAlto;
        X225 = (float) 0.43940 * bloqueAncho;        Y210 = (float) 0.41010 * bloqueAlto;
        X285 = (float) 0.56050 * bloqueAncho;        Y300 = (float) 0.58590 * bloqueAlto;
        X305 = (float) 0.59570 * bloqueAncho;        Y340 = (float) 0.66406 * bloqueAlto;
        X320 = (float) 0.62500 * bloqueAncho;        Y380 = (float) 0.74218 * bloqueAlto;
        X410 = (float) 0.80078 * bloqueAncho;        Y415 = (float) 0.81050 * bloqueAlto;
        X512 = bloqueAncho;                          Y512 = bloqueAlto;

        //bloque recta, compuesto por un solo elemento
        tipoBloquePoligono[0][0] = new Polygon(new float[]{X190, 0, X190, Y512, X320, Y512, X320, 0});
        tipoBloquePoligono[0][1] = new Polygon(new float[]{X190, 0, X190, Y512, X320, Y512, X320, 0});
        tipoBloquePoligono[0][2] = new Polygon(new float[]{X190, 0, X190, Y512, X320, Y512, X320, 0});
        //bloque curva a derecha, compuesto por tres elementos
        tipoBloquePoligono[1][0] = new Polygon(new float[]{X190, 0, X100, Y170, X205, Y210, X320, 0});
        tipoBloquePoligono[1][1] = new Polygon(new float[]{X100, Y170, X100, Y340, X205, Y300, X205, Y210});
        tipoBloquePoligono[1][2] = new Polygon(new float[]{X100, Y340, X190, Y512, X320, Y512, X205, Y300});
        //bloque curva a izquierda, compuesto por tres elementos
        tipoBloquePoligono[2][0] = new Polygon(new float[]{X190, 0, X305, Y210, X410, Y170, X320, 0});
        tipoBloquePoligono[2][1] = new Polygon(new float[]{X305, Y210, X305, Y300, X410, Y340, X410, Y170});
        tipoBloquePoligono[2][2] = new Polygon(new float[]{X305, Y300, X190, Y512, X320, Y512, X410, Y340});
        //bloque embudo en recta, compuesto por tres elementos
        tipoBloquePoligono[3][0] = new Polygon(new float[]{X190, 0, X190, Y95, X225, Y125, X285, Y125, X320, Y95, X320, 0});
        tipoBloquePoligono[3][1] = new Polygon(new float[]{X225, Y125, X225, Y380, X285, Y380, X285, Y125});
        tipoBloquePoligono[3][2] = new Polygon(new float[]{X225, Y380, X190, Y415, X190, Y512, X320, Y512, X320, Y415, X285, Y380});

    }

    public boolean existeColision(int[] bloquesColision, float[] posicionBloque, float[] posicionNave, OrthographicCamera camara) {
        nave[0].setPosition(posicionNave[0], posicionNave[1] + naveAlto / 4);
        nave[1].setPosition(posicionNave[0] + naveAncho - 0.1f, posicionNave[1] + naveAlto / 4);

        if (posicionBloque[1] + bloqueAlto >= posicionNave[1] && posicionBloque[1] + bloqueAlto <= posicionNave[1] + naveAlto) {
            tipoBloquePoligono[bloquesColision[0]][2].setPosition(posicionBloque[0], posicionBloque[1]);
            tipoBloquePoligono[bloquesColision[1]][0].setPosition(posicionBloque[0], posicionBloque[1] + bloqueAlto);
            if ((Intersector.overlapConvexPolygons(nave[0], tipoBloquePoligono[bloquesColision[0]][2]) &&
                 Intersector.overlapConvexPolygons(nave[1], tipoBloquePoligono[bloquesColision[0]][2])) ||
                (Intersector.overlapConvexPolygons(nave[0], tipoBloquePoligono[bloquesColision[1]][0]) &&
                 Intersector.overlapConvexPolygons(nave[1], tipoBloquePoligono[bloquesColision[1]][0])))
                return false;
        }
        for (int j = 0; j < 3; j++) {
            if (posicionBloque[1] + bloqueAlto >= posicionNave[1] + naveAlto) {
                tipoBloquePoligono[bloquesColision[0]][j].setPosition(posicionBloque[0], posicionBloque[1]);
                if (Intersector.overlapConvexPolygons(nave[0], tipoBloquePoligono[bloquesColision[0]][j]) &&
                    Intersector.overlapConvexPolygons(nave[1], tipoBloquePoligono[bloquesColision[0]][j]))
                    return false;
            }
            if (posicionBloque[1] + bloqueAlto <= posicionNave[1]) {
                tipoBloquePoligono[bloquesColision[1]][j].setPosition(posicionBloque[0], posicionBloque[1] + bloqueAlto);
                if (Intersector.overlapConvexPolygons(nave[0], tipoBloquePoligono[bloquesColision[1]][j]) &&
                    Intersector.overlapConvexPolygons(nave[1], tipoBloquePoligono[bloquesColision[1]][j]))
                    return false;
            }
        }
        return true;
    }
}
/*
    PolygonSpriteBatch polyBatch = new PolygonSpriteBatch();
    Texture textureSolid1, textureSolid2;
    Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        pix.setColor(Color.BLUE);
        pix.fill();
        textureSolid1 = new Texture(pix);
        pix.setColor(Color.GREEN);
        pix.fill();
        textureSolid2 = new Texture(pix);

        PolygonRegion polyReg21 = new PolygonRegion(new TextureRegion(textureSolid1),tipoBloquePoligono[tipoBloque][0].getVertices());
        PolygonRegion polyReg22 = new PolygonRegion(new TextureRegion(textureSolid1),tipoBloquePoligono[tipoBloque][1].getVertices());
        PolygonRegion polyReg23 = new PolygonRegion(new TextureRegion(textureSolid1),tipoBloquePoligono[tipoBloque][2].getVertices());
        PolygonRegion polyReg24 = new PolygonRegion(new TextureRegion(textureSolid2),nave[0].getVertices());
        PolygonRegion polyReg25 = new PolygonRegion(new TextureRegion(textureSolid2),nave[1].getVertices());

        polyBatch.setProjectionMatrix(camara.combined);
        polyBatch.begin();
        polyBatch.draw(polyReg21, tipoBloquePoligono[tipoBloque][0].getX(), tipoBloquePoligono[tipoBloque][0].getY());
        polyBatch.draw(polyReg22, tipoBloquePoligono[tipoBloque][1].getX(), tipoBloquePoligono[tipoBloque][1].getY());
        polyBatch.draw(polyReg23, tipoBloquePoligono[tipoBloque][2].getX(), tipoBloquePoligono[tipoBloque][2].getY());
        polyBatch.draw(polyReg24, nave[0].getX(), nave[0].getY());
        polyBatch.draw(polyReg25, nave[1].getX(), nave[1].getY());
        polyBatch.end();

        return !(Intersector.overlapConvexPolygons(nave[0], tipoBloquePoligono[tipoBloque][0]) ||
                Intersector.overlapConvexPolygons(nave[1], tipoBloquePoligono[tipoBloque][0]) ||
                Intersector.overlapConvexPolygons(nave[0], tipoBloquePoligono[tipoBloque][1]) ||
                Intersector.overlapConvexPolygons(nave[1], tipoBloquePoligono[tipoBloque][1]) ||
                Intersector.overlapConvexPolygons(nave[0], tipoBloquePoligono[tipoBloque][2]) ||
                Intersector.overlapConvexPolygons(nave[1], tipoBloquePoligono[tipoBloque][2]));*/