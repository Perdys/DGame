package com.me.DGamePckg.Pantallas.Jugar.Juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.me.DGamePckg.*;
import com.me.DGamePckg.Pantallas.MenuPrincipal;

import java.util.ArrayDeque;

public class NivelUno extends Pantalla {

    DGameClass juego;
    Nave naveTipo;
    Fondo fondo;

    Stage stage;
    OrthographicCamera camara;
    SpriteBatch batch;
    Animation[] nave;
    ArrayDeque<Texture> bloques;
    Colision colision;
    Texture[] tiposBloque;
    Texture marcador;
    float[] posicionNave;
    int tipoNave = 1, bloquesPantalla = 3;
    Texto checker;
    Integer puntuacion = 0;

    public NivelUno (DGameClass juego){
        this.juego = juego;
        stage = new Stage();
        batch = new SpriteBatch();
        Texture.setEnforcePotImages(false);

        pantalla_actual = 11;

        marcador = new Texture(Gdx.files.internal("data/marcador.png"));

        nave = new Animation[] {new Animation(0.1f, getSprites("data/nave_lento.png", 3, 3)),
                                new Animation(0.1f, getSprites("data/nave_normal.png", 3, 3)),
                                new Animation(0.1f, getSprites("data/nave_rapido.png", 3, 3))};
        posicionNave = new float[] {anchura_juego / 2 - (float) (nave[0].getKeyFrame(0).getRegionWidth() * 0.9) / 2 * proporcion_ancho_juego, altura_juego / 7};

        tiposBloque = new Texture[] {new Texture(Gdx.files.internal("data/bloque_recta.png")),
                                     new Texture(Gdx.files.internal("data/bloque_curder.png")),
                                     new Texture(Gdx.files.internal("data/bloque_curizq.png")),
                                     new Texture(Gdx.files.internal("data/bloque_embrec.png"))};

        bloques = new ArrayDeque<>();

        camara = new OrthographicCamera(anchura_juego, altura_juego);
        camara.position.set(anchura_juego / 2, altura_juego / 2, 0);

        fondo = new Fondo(bloques, tiposBloque, bloquesPantalla, camara);
        naveTipo = new Nave(camara);
        colision = new Colision(tiposBloque, nave[tipoNave]);

        checker = new Texto(1, "(+)");
        checker.setPosition(anchura_juego / 4 - BMF_titulo.getBounds(checker.texto).width / 2, altura_juego, 0, BitmapFont.HAlignment.CENTER);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float[] posicionBloque = fondo.MoverFondo(tipoNave);
        tipoNave = naveTipo.MoverNave(nave[tipoNave], posicionNave);

        int[] bloquesColision = {0,0};
        Texture primerBloque = bloques.getFirst();
        bloques.add(bloques.poll());
        Texture segundoBloque = bloques.getFirst();
        bloques.add(bloques.poll());
        bloques.add(bloques.poll());

        for (int n = 0; n < tiposBloque.length; n++) {
            if (primerBloque == tiposBloque[n])
                bloquesColision[0] = n;
            if (segundoBloque == tiposBloque[n])
                bloquesColision[1] = n;
        }

        batch.begin();
        batch.draw(marcador, 0, altura_juego - marcador.getHeight(), anchura_juego, BMF_titulo.getBounds(checker.texto).height * 2);
        batch.end();

        if (colision.existeColision(bloquesColision, posicionBloque, posicionNave, camara))
            checker.texto = ((Integer)(Math.round(--puntuacion) / 100)).toString();
        else
            checker.texto = ((Integer)(Math.round(++puntuacion) / 100)).toString();

        checker.setPosition(anchura_juego / 4 - BMF_titulo.getBounds(checker.texto).width / 2, altura_juego, 0, BitmapFont.HAlignment.CENTER);

        camara.update();
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {}

    public void pause() {}

    public void resume() {}

    public void show() {
        setStageButton(juego);
        stage.addActor(checker);

        InputMultiplexer inputs = new InputMultiplexer();
        inputs.addProcessor(stage_botones);
        inputs.addProcessor(stage);
        Gdx.input.setInputProcessor(inputs);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }

    public void hide() {
        dispose();
    }

    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
