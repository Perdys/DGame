package com.me.DGamePckg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.me.DGamePckg.Pantallas.Jugar.Juego.NivelUno;
import com.me.DGamePckg.Pantallas.Jugar.MenuJugar;
import com.me.DGamePckg.Pantallas.MenuPrincipal;
import com.me.DGamePckg.Pantallas.Opciones.MenuOpciones;
import com.me.DGamePckg.Pantallas.Records.MenuRecords;

public class Pantalla implements Screen {

    public static float anchura_juego = Gdx.graphics.getWidth();
    public static float altura_juego = Gdx.graphics.getHeight();
    public static float proporcion_ancho_juego = anchura_juego / 1080;
    public static float proporcion_alto_juego = altura_juego / 1920;
    public static float proporcion_ancho_nave = (float) (proporcion_ancho_juego * 0.9);
    public static float proporcion_alto_nave = (float) (proporcion_ancho_juego * 0.9);
    public static float proporcion_ancho_bloque = (float) (proporcion_ancho_juego * 3.3);
    public static float proporcion_alto_bloque = (float) (proporcion_alto_juego * 3.3);
    public static float lado = anchura_juego < altura_juego ? anchura_juego : altura_juego;
    public Integer pantalla_actual = 0;
    public static Stage stage_botones;
    public FreeTypeFontGenerator generator;
    public BitmapFont BMF_titulo, BMF_boton, BMF_texto;

    public Pantalla() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel.ttf"));
        BMF_titulo = generator.generateFont((int) anchura_juego / 4);
        BMF_boton = generator.generateFont((int) anchura_juego / 12);
        BMF_texto = generator.generateFont((int) anchura_juego / 13);
        BMF_titulo.setColor(Color.BLACK);
        BMF_boton.setColor(Color.BLACK);
        BMF_texto.setColor(Color.BLACK);
    }

    public void setStageButton(DGameClass juego) {
        stage_botones = new Stage();
        stage_botones.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.BACK:
                        switch (pantalla_actual) {
                            case 0:
                                Gdx.app.exit();
                                break;
                            default:
                                juego.setScreen(new MenuPrincipal(juego));
                                break;
                        }
                        break;
                    case Input.Keys.HOME:
                        Gdx.app.exit();
                    default:
                        break;
                }
                return false;
            }
        });
    }

    public ImageTextButton setButton(String imagen, String texto, DGameClass juego) {
        ImageTextButton.ImageTextButtonStyle estilo_boton = new ImageTextButton.ImageTextButtonStyle();
        Skin skin = new Skin();
        skin.add("boton", new Texture(imagen));
        estilo_boton.up = skin.getDrawable("boton");
        estilo_boton.font = BMF_boton;
        estilo_boton.fontColor = Color.BLACK;
        estilo_boton.unpressedOffsetY = -(lado / 6);
        estilo_boton.pressedOffsetY = -(lado / 6);
        
        ImageTextButton boton = new ImageTextButton(texto, estilo_boton);
        boton.setSize(lado / 4, lado / 4);

        boton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switch (imagen) {
                    case "data/boton_jugar.png": juego.setScreen(new MenuJugar(juego));
                        break;
                    case "data/boton_records.png": juego.setScreen(new MenuRecords(juego));
                        break;
                    case "data/boton_opciones.png": juego.setScreen(new MenuOpciones(juego));
                        break;
                    case "data/boton_atras.png": juego.setScreen(new MenuPrincipal(juego));
                        break;
                    case "data/boton_niveles.png": juego.setScreen(new NivelUno(juego));
                        break;
                    default: break;
                }
            }
        });
        return boton;
    }

    public static TextureRegion[] getSprites(String filepath, int col, int fil) {
        Texture walkSheet = new Texture(Gdx.files.internal(filepath));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / col, walkSheet.getHeight() / fil);
        TextureRegion[] walkFrames = new TextureRegion[col * fil];
        int index = 0;
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < fil; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        return walkFrames;
    }

    public void render(float delta) {}

    public void resize(int width, int height) {}

    public void pause() {}

    public void resume() {}

    public void show() {}

    public void hide() { dispose(); }

    public void dispose () {}

    public class Texto extends Actor {

        public String texto;
        public float x, y, espacio;
        public BitmapFont.HAlignment alineacion;
        public int tipo;

        public Texto(int tipo, String texto, float x, float y, float espacio, BitmapFont.HAlignment alineacion) {
            this.texto = texto;
            this.x = x;
            this.y = y;
            this.espacio = espacio;
            this.alineacion = alineacion;
            this.tipo = tipo;
        }

        public Texto(int tipo, String texto) {
            this.texto = texto;
            this.tipo = tipo;
        }

        public void setPosition(float x, float y, float espacio, BitmapFont.HAlignment alineacion) {
            this.x = x;
            this.y = y;
            this.espacio = espacio;
            this.alineacion = alineacion;
        }

        @Override
        public void draw(SpriteBatch batch, float alpha) {
            switch (tipo) {
                case 1: BMF_titulo.drawMultiLine(batch, texto, x, y, espacio, alineacion);
                    break;
                case 2: BMF_texto.drawMultiLine(batch, texto, x, y, espacio, alineacion);
                    break;
                default: break;
            }
        }
    }
}