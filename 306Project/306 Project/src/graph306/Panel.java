package graph306;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Juno on 19/08/2016.
 */
public class Panel extends JPanel {


    @Override
    public void paintComponent(Graphics G) {
        super.paintComponent(G);
        G.drawRect(0, 0, this.getWidth(), this.getHeight());
    }

}

