package com.me.DGamePckg.Pantallas.Opciones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.me.DGamePckg.DGameClass;
import com.me.DGamePckg.Pantalla;

public class MenuOpciones extends Pantalla {

    DGameClass juego;
    Stage stage;
    OrthographicCamera camara;
    SpriteBatch batch;

    ImageTextButton boton_atras, boton_audio;

    public MenuOpciones (DGameClass juego) {
        this.juego = juego;

        camara = new OrthographicCamera();
        camara.setToOrtho(false);
        stage = new Stage();
        batch = new SpriteBatch();

        pantalla_actual = 3;

        boton_audio = setButton("data/boton_audio.png", "Audio", juego);
        boton_audio.setPosition(anchura_juego / 2 - boton_audio.getWidth() / 2, (float) (altura_juego * 0.66) - boton_audio.getHeight() / 2);
        boton_atras = setButton("data/boton_atras.png", "Atras", juego);
        boton_atras.setPosition(anchura_juego / 2 - boton_atras.getWidth() / 2, (float) (altura_juego * 0.33) - boton_atras.getHeight() / 2);
    }

    public void render (float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camara.update();

        batch.setProjectionMatrix(camara.combined);

        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {}

    public void pause() {}

    public void resume() {}

    public void show() {
        stage.addActor(boton_audio);
        stage.addActor(boton_atras);
        setStageButton(juego);

        InputMultiplexer inputs = new InputMultiplexer();
        inputs.addProcessor(stage_botones);
        inputs.addProcessor(stage);
        Gdx.input.setInputProcessor(inputs);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }

    public void hide() { dispose(); }

    public void dispose () {
        batch.dispose();
        stage.dispose();
    }
}
