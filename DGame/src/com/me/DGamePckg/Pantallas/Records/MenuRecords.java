package com.me.DGamePckg.Pantallas.Records;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.me.DGamePckg.DGameClass;
import com.me.DGamePckg.Pantalla;

public class MenuRecords extends Pantalla {

    DGameClass juego;
    Stage stage;
    OrthographicCamera camara;
    SpriteBatch batch;

    ImageTextButton boton_atras;
    Table scrolltable;
    Table tabla;
    final ScrollPane scroller;

    public MenuRecords (DGameClass juego) {
        this.juego = juego;

        camara = new OrthographicCamera();
        camara.setToOrtho(false);
        stage = new Stage();
        batch = new SpriteBatch();

        pantalla_actual = 2;

        Texto records = new Texto(2, "- NINGUN RECORD TODAVIA!!!\nf\ng\nh\n4\nf\ne");
        String[] lineas = records.texto.split("\r\n|\r|\n");
        records.setPosition(0, (float) (anchura_juego * 0.15), 0, BitmapFont.HAlignment.LEFT);

        scrolltable = new Table();
        scrolltable.add(records).width(BMF_texto.getBounds(records.texto).width).height(BMF_texto.getBounds(records.texto).height * lineas.length);

        scroller = new ScrollPane(scrolltable);

        tabla = new Table();
        tabla.add(scroller).width((float) (anchura_juego * 0.75)).height((float) (altura_juego * 0.25));
        tabla.setPosition((float) (anchura_juego * 0.5), (float) (altura_juego * 0.66));

        boton_atras = setButton("data/boton_atras.png", "Atras", juego);
        boton_atras.setPosition(anchura_juego / 2 - boton_atras.getWidth() / 2, (float) (altura_juego * 0.33) - boton_atras.getHeight() / 2);
    }

    public void render(float delta) {
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
        stage.addActor(tabla);
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

