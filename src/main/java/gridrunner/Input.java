package gridrunner;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class Input {
    private Set<KeyCode> held;
    private Set<KeyCode> clicked;

    public Input ( ) {

        this.held = new HashSet<> ( );
        this.clicked = new HashSet<>();
    }

    public void keyPressed ( KeyEvent event ) {
        this.held.add ( event.getCode ( ) );
    }

    public void keyReleased ( KeyEvent event ) {
        this.held.remove ( event.getCode ( ) );
        this.clicked.add(event.getCode());
    }

    public boolean isDown ( KeyCode keyCode ) {
        return this.held.contains ( keyCode );
    }

    public boolean isClicked (KeyCode keyCode) {
        boolean clicked = this.clicked.contains (keyCode);
        this.clicked.remove(keyCode);
        return clicked;
    }

    public boolean up ( ) {
        return this.isDown ( KeyCode.UP ) || isDown ( KeyCode.W );
    }

    public boolean down ( ) {
        return this.isDown ( KeyCode.DOWN ) || this.isDown ( KeyCode.S );
    }

    public boolean left ( ) {
        return this.isDown ( KeyCode.LEFT ) || this.isDown ( KeyCode.A );
    }

    public boolean right ( ) {
        return this.isDown ( KeyCode.RIGHT ) || this.isDown ( KeyCode.D );
    }

    public boolean keyR() { return this.isClicked(KeyCode.R); }

}