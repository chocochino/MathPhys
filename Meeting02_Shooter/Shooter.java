package Meeting02_Shooter;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/*
    MatFis pertemuan 2
    Note that every dimension-related are measured in pixel
    Except for angle, which is measured in radian
    Explain how parabolic motion of projectile works.
    What is the difference between mapping code in Cartesian coordinates and pixel coordinates?

    TODO:
     1. Add a text field to adjust bullet's velocity
        > DONE!
        > Input desired velocity, press space to shoot immediately.
     2. Make cannon able to shoot more than one bullet
        > OK!!
        > Press space multiple times to shoot indefinitely!
     3. Limit the amount of bullet in the cannon
        > Roger that.
        > I made a label to indicate how much bullets the player has at the moment.
     4. Add wind force, with its direction (which impacts acceleration on x-axis and y-axis; use Newton's second law)
     5. Make a shooter game with simple moving target (yes, over-achievers, I need SIMPLE)

    Extra:
    Q: Does this mean I can make a bullet hell game for my final project?
    A: Yes, but since the concept is already explained in class, you won't get Liv's extra brownie point.
 */


class Shooter {
    private JFrame frame;

    // game area
    private Bullet bullet = null;
    private Cannon cannon;

    // bullet magazine system
    private int maxBullets = 5;
    private int currentBullets = 5;
    private String bulletString = "Bullets: " + Integer.toString(currentBullets) + "/" + Integer.toString(maxBullets);

    private static final String INSTRUCTION = "Welcome to Cannon Simulation!\n" +
            "\nMove cannon's position = W A S D\n" +
            "Move shooting direction = Left | Right \n" +
            "Launch bullet = Space\n" +
            "\nThere can only one bullet at a time";

    private int cpSize = 230;      // set control panel's width

    private void updateBullets() {
        bulletString = "Bullets: " + Integer.toString(currentBullets) + "/" + Integer.toString(maxBullets);
    }

    public Shooter() {
        // setup the frame
        frame = new JFrame("Graphing App");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setFocusable(true);
        frame.setVisible(true);

        // setup control panel itself
        JTextArea instruction = new JTextArea(INSTRUCTION);
        instruction.setBounds(5, 5, cpSize - 5, 50);
        JLabel bVLabel = new JLabel("Bullet Velocity");
        JTextField bulletVelocity = new JTextField("50");
        bulletVelocity.setBounds(5, 75, cpSize-5, 20);
        bVLabel.setBounds(5, 55, cpSize-5, 20);
        JLabel bulletsLabel = new JLabel(bulletString);
        bulletsLabel.setBounds(5, 95, cpSize-5, 20);
        frame.add(instruction);
        frame.add(bVLabel);
        frame.add(bulletVelocity);
        frame.add(bulletsLabel);

        // setup drawing area
        DrawingArea drawingArea = new DrawingArea(frame.getWidth(), frame.getHeight(), cpSize);
        cannon = new Cannon(drawingArea.GRAPH_SCALE / 2, drawingArea.getOriginX(), drawingArea.getOriginY());
        drawingArea.setCannon(cannon);
        frame.add(drawingArea);

        // Keyboard shortcuts
        bulletVelocity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        if(currentBullets > 0) {
                            frame.requestFocusInWindow();
                            bullet = new Bullet(cannon.getBarrelWidth() / 2, (int) cannon.getBarrelMouthX(), (int) cannon.getBarrelMouthY(), cannon.getAngle(), Double.parseDouble(bulletVelocity.getText()), drawingArea.getTime());
                            drawingArea.setBullet(bullet);
                            currentBullets--;
                            updateBullets();
                            bulletsLabel.setText(bulletString);
                        }
                        break;
                }
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        if(currentBullets > 0) {
                            bullet = new Bullet(cannon.getBarrelWidth() / 2, (int) cannon.getBarrelMouthX(), (int) cannon.getBarrelMouthY(), cannon.getAngle(), Double.parseDouble(bulletVelocity.getText()), drawingArea.getTime());
                            drawingArea.setBullet(bullet);
                            currentBullets--;
                            updateBullets();
                            bulletsLabel.setText(bulletString);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        cannon.rotateLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        cannon.rotateRight();
                        break;
                    case KeyEvent.VK_W:
                        cannon.moveUp();
                        break;
                    case KeyEvent.VK_A:
                        cannon.moveLeft();
                        break;
                    case KeyEvent.VK_S:
                        cannon.moveDown();
                        break;
                    case KeyEvent.VK_D:
                        cannon.moveRight();
                        break;
                }
            }
        });

        drawingArea.start();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Shooter::new);
    }
}