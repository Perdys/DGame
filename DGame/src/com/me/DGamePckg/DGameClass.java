package com.me.DGamePckg;

import com.badlogic.gdx.Game;
import com.me.DGamePckg.Pantallas.MenuPrincipal;

public class DGameClass extends Game {

    public void create() { setScreen(new MenuPrincipal(this)); }

    public void render() { super.render(); }

    public void resize(int width, int height) {
        super.resize(width, height);
    }

    public void pause() {
        super.pause();
    }

    public void resume() { super.resume(); }

    public void dispose() {
        super.dispose();
    }
}
