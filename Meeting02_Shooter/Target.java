package Meeting02_Shooter;

import java.lang.Math;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

class Target {
    Color color = Color.blue;
    double radius;

    int positionX;
    int positionY;

    public Target(double radius, int positionX, int positionY) {
        this.radius = radius;
        this.positionX = positionX;
        this.positionY = positionY;
    }
    void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g.fillOval(positionX, positionY, (int)radius, (int)radius);
    }
    void move(double time) {
        positionY = 540 + (int)(Math.sin(time / 2) * 200);
    }
    void changeColor(Color c) {
        color = c;
    }
}