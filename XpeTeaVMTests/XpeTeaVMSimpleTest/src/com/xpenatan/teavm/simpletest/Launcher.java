package com.xpenatan.teavm.simpletest;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.xml.Document;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.libgdx.TeaVMApplication;
import org.teavm.libgdx.TeaVMApplicationConfig;

public class Launcher {
	public static void main(String[] args) {
        Window window = Window.current();
        Document document = window.getDocument();
        TeaVMApplicationConfig config = new TeaVMApplicationConfig();
        config.setCanvas((HTMLCanvasElement)document.getElementById("gdx-canvas"));
        new TeaVMApplication(new App(), config).start();
    }
}
