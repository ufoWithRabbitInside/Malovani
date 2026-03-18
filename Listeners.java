
import com.sun.jdi.Value;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class Listeners{
    public static ActionListener GetButton1Listener(){
        return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Tlačítko bylo stisknuto!");
                }
            };
    }

    public static ActionListener GetRucickaListener(){
        return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Main.state = State.Rucicka;
                }
            };
    }
    public static ActionListener GetCarkaListener(){
        return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Main.state = State.Carka;
                }
            };
    }
    public static ActionListener GetCarkyListener(){
        return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Main.state = State.Carky;
                }
            };
    }
    public static ActionListener GetKrouzekListener(){
        return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Main.state = State.Krouzek;
                }
            };
    }
    public static ActionListener GetKyblicekListener(){
        return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Main.state = State.Kyblicek;
                }
            };
    }
    public static ActionListener GetTuzkaListener(){
        return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Main.state = State.Tuzka;
                }
            };
    }
    public static ActionListener GetMazatkoListener(){
        return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Main.state = State.Mazatko;
                }
            };
    }
    public static ChangeListener GetColorListener(){
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float value = (float)((javax.swing.JSlider) e.getSource()).getValue();
                value /= 100;
                Main.color = Color.HSBtoRGB(value,1.0f,1.0f);
            }
        };
    }
    public static ChangeListener GetStrenghtListener(){
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Main.strenght = ((javax.swing.JSlider) e.getSource()).getValue();
            }
        };
    }
    public static ChangeListener GetPaddingListener(){
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Main.padding = ((javax.swing.JSlider) e.getSource()).getValue();
            }
        };
    }
    public static ChangeListener GetRndToFillListener(){
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Main.rnd_to_fill = ((javax.swing.JSlider) e.getSource()).getValue();
                Main.rnd_to_fill /= 100;
            }
        };
    }
}
/*
import java.awt.event.MouseAdapter;
import org.w3c.dom.events.MouseEvent;

public class MouseAdapters {
    MouseMotionAdapter mouseAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                // vykresli pixel pod myší
                //image.setRGB(mouseX, mouseY, Color.RED.getRGB());
                //repaint();

                Line line = new Line(new Point(200, 150), new Point(mouseX, mouseY));
                drawer.Clear();
                drawer.DrawLine(line, Color.YELLOW.getRGB(), false);
                image = drawer.GetImage();
                repaint();
            }
        }
}
*/