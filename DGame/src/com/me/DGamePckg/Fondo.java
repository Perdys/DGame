package com.me.DGamePckg;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

public class Fondo extends Pantalla{

    SpriteBatch batch;
    OrthographicCamera camara;
    ArrayDeque<Texture> bloques;
    Texture[] tipos;
    Random aleatorio;
    float ejex = anchura_juego / 2, ejeyInicial = 0, ejey = 0;

    public Fondo (ArrayDeque<Texture> bloques, Texture[] tipos, int bloquesPantalla, OrthographicCamera camara) {
        this.bloques = bloques;
        this.tipos = tipos;
        this.camara = camara;
        aleatorio = new Random();
        batch = new SpriteBatch();

        for (int n = 0; n < bloquesPantalla; n++)
            bloques.add(tipos[aleatorio.nextInt(tipos.length)]);
    }

    public float[] MoverFondo (int velocidad) {

        float bloqueAncho = bloques.element().getWidth() * proporcion_alto_bloque;
        float bloqueAlto = bloques.element().getHeight() * proporcion_ancho_bloque;

        ejey = ejeyInicial;

        for (int n = 0; n < bloques.size(); n++) {
            batch.setProjectionMatrix(camara.combined);
            batch.begin();
            batch.draw(bloques.element(), ejex - bloqueAncho / 2, ejey, bloqueAncho, bloqueAlto);
            batch.end();
            bloques.add(bloques.poll());
            ejey += bloqueAlto;
        }

        if (ejeyInicial + bloqueAlto <= 0) {
            ejeyInicial = 0;
            bloques.poll();
            bloques.add(tipos[aleatorio.nextInt(tipos.length)]);
        } else
            ejeyInicial -= proporcion_alto_juego * (velocidad * 3 + 3);

        return new float[] {ejex - bloqueAncho / 2, ejeyInicial};
    }

    public void dispose() {
        batch.dispose();
    }
}