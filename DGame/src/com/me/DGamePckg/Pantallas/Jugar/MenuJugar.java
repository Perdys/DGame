package com.me.DGamePckg.Pantallas.Jugar;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.me.DGamePckg.DGameClass;
import com.me.DGamePckg.Pantalla;

public class MenuJugar extends Pantalla {

    DGameClass juego;
    Stage stage;
    OrthographicCamera camara;
    SpriteBatch batch;

    ImageTextButton boton_niveles, boton_personalizar,boton_atras;

    public MenuJugar(DGameClass juego) {
        this.juego = juego;

        camara = new OrthographicCamera();
        camara.setToOrtho(false);
        stage = new Stage();
        batch = new SpriteBatch();

        pantalla_actual = 1;

        boton_niveles = setButton("data/boton_niveles.png", "Niveles", juego);
        boton_niveles.setPosition(anchura_juego / 3 - boton_niveles.getWidth() / 2, (float) (altura_juego * 0.66) - boton_niveles.getHeight() / 2);
        boton_personalizar = setButton("data/boton_personalizar.png", "Personalizar", juego);
        boton_personalizar.setPosition((float)(anchura_juego * 0.66) - boton_personalizar.getWidth() / 2, (float) (altura_juego * 0.66) - boton_personalizar.getHeight() / 2);
        boton_atras = setButton("data/boton_atras.png", "Atras", juego);
        boton_atras.setPosition(anchura_juego / 2 - boton_atras.getWidth() / 2, (float) (altura_juego * 0.33) - boton_atras.getHeight() / 2);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camara.update();

        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {}

    public void pause() {}

    public void resume() {}

    public void show() {
        stage.addActor(boton_niveles);
        stage.addActor(boton_personalizar);
        stage.addActor(boton_atras);
        setStageButton(juego);

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
