package Meeting02_Shooter;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

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
        > Okay!
        > I made a button to make things simpler and tidier when working with multiple properties.
        > Simply insert the values to the properties (Wind Force, Angle of direction, etc.), press the 'Apply Properties' button, and let it rip!
        > I also did some fine tuning.
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

    // physics attributes
    private int mass = 1;
    private int wind = 10;
    private int angle = 0;

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
        instruction.setBounds(5, 5, cpSize - 5, 100);
        JLabel bVLabel = new JLabel("Bullet Velocity");
        JTextField bulletVelocity = new JTextField("50");
        bulletVelocity.setBounds(5, 125, cpSize-5, 20);
        bVLabel.setBounds(5, 105, cpSize-5, 20);
        JLabel bulletsLabel = new JLabel(bulletString);
        bulletsLabel.setBounds(5, 145, cpSize-5, 20);
        JLabel massLabel = new JLabel("Cannon Mass");
        JTextField massField = new JTextField("1");
        massLabel.setBounds(5, 165, cpSize-5, 20);
        massField.setBounds(5, 185, cpSize-5, 20);
        JLabel windLabel = new JLabel("Wind Force");
        JTextField windField = new JTextField("10");
        windLabel.setBounds(5, 205, cpSize-5, 20);
        windField.setBounds(5, 225, cpSize-5, 20);
        JLabel angleLabel = new JLabel("Wind's Angle of Direction");
        JTextField angleField = new JTextField("0");
        angleLabel.setBounds(5, 245, cpSize-5, 20);
        angleField.setBounds(5, 265, cpSize-5, 20);
        JButton applyButton = new JButton("Apply Properties");
        applyButton.setBounds(5, 285, cpSize-5, 20);
        frame.add(instruction);
        frame.add(bVLabel);
        frame.add(bulletVelocity);
        frame.add(bulletsLabel);
        frame.add(massLabel);
        frame.add(massField);
        frame.add(windLabel);
        frame.add(windField);
        frame.add(angleLabel);
        frame.add(angleField);
        frame.add(applyButton);

        // setup drawing area
        DrawingArea drawingArea = new DrawingArea(frame.getWidth(), frame.getHeight(), cpSize);
        cannon = new Cannon(drawingArea.GRAPH_SCALE / 2, drawingArea.getOriginX(), drawingArea.getOriginY());
        drawingArea.setCannon(cannon);
        frame.add(drawingArea);

        // Keyboard shortcuts
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.requestFocusInWindow();
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        if(currentBullets > 0) {
                            bullet = new Bullet(cannon.getBarrelWidth() / 2, (int) cannon.getBarrelMouthX(), (int) cannon.getBarrelMouthY(), cannon.getAngle(),
                                                Double.parseDouble(bulletVelocity.getText()), drawingArea.getTime(),
                                                Integer.parseInt(massField.getText()), Integer.parseInt(windField.getText()), Integer.parseInt(angleField.getText()));
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