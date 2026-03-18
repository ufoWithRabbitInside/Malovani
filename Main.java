import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

class Main extends JPanel {

    //pořádek je pro blbce, inteligent ovládá chaos

    

    int mouseX = 0;
    int mouseY = 0;
    Point mouse = new  Point(0,0);

    static int color = Color.YELLOW.getRGB();
    static int strenght = 10;
    static int padding = 5;
    static double rnd_to_fill = 0.6;
    int back_color = 0;


    BufferedImage image;
    Drawer drawer = new Drawer(400, 300, back_color);
    Remember remember = new Remember();
    static State state = State.Carka;

    Point GetNearest(Point point, Remember remember){
        Point nearest = null;
        nearest_radius = null;
        double lowestDistance = Double.MAX_VALUE;
        for (Line line : remember.Lines) {
            double distance = Geometry.Distance(point.X - line.point1.X, point.Y - line.point1.Y);
            if(distance < lowestDistance){
                lowestDistance = distance;
                nearest = line.point1;
                nearest_radius = null;
            }
            distance = Geometry.Distance(point.X - line.point2.X, point.Y - line.point2.Y);
            if(distance < lowestDistance){
                lowestDistance = distance;
                nearest = line.point2;
                nearest_radius = null;
            }
        }
        for (Circle circle : remember.Circles) {
            double distance = Geometry.Distance(point.X - circle.centre.X, point.Y - circle.centre.Y);
            if(distance < lowestDistance){
                lowestDistance = distance;
                nearest = circle.centre;
                nearest_radius = null;
            }
            
            distance = Math.abs(Geometry.Distance(point.X - circle.centre.X, point.Y - circle.centre.Y) - circle.radius);
            if(distance < lowestDistance){
                lowestDistance = distance;
                nearest_radius = circle;
                nearest = null;
            }
            
        }
        for (NAngle nAngle : remember.NAngles) {
            for (int i = 0; i < nAngle.Points.size(); i++) {
                double distance = Geometry.Distance(point.X - nAngle.Points.get(i).X, point.Y - nAngle.Points.get(i).Y);
                if(distance < lowestDistance){
                    lowestDistance = distance;
                    nearest = nAngle.Points.get(i);
                    nearest_radius = null;
                }
            }
        }
        return nearest;
    }
    Point nearest = null;
    Circle nearest_radius = null;

    public Main() {
        drawer.Clear();

        //List<Line> lines = new ArrayList<>();
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                mouse.X = mouseX;
                mouse.Y = mouseY;
                /*
                // vykresli pixel pod myší
                //image.setRGB(mouseX, mouseY, Color.RED.getRGB());
                //repaint();

                Line line = new Line(new Point(200, 150), new Point(mouseX, mouseY));
                drawer.Clear();
                drawer.DrawLine(line, Color.YELLOW.getRGB(), false);
                image = drawer.GetImage();
                repaint();
                */
                
                drawer.DeleteEverything(remember);

                {
                    if(state == State.Rucicka){
                        if(nearest != null || nearest_radius != null){
                            if(nearest != null){
                            nearest.X = mouseX;
                            nearest.Y = mouseY;
                            }
                            if(nearest_radius != null){
                                nearest_radius.radius = (int)Geometry.Distance(nearest_radius.centre.X - mouseX, nearest_radius.centre.Y - mouseY);
                            }
                        }
                    }
                    if(state == State.Carka){
                        if(remember.previewLine != null){
                            remember.previewLine.point2.X = mouseX;
                            remember.previewLine.point2.Y = mouseY;
                            remember.previewLine.isSolid = !e.isControlDown();
                            if(e.isShiftDown())
                                remember.previewLine.ZarovnejSe(Math.PI/8);
                        }
                    }
                    if(state == State.Krouzek){
                        if(remember.previewCircle != null){
                            remember.previewCircle.radius = (int)Geometry.Distance(mouseX - remember.previewCircle.centre.X, mouseY - remember.previewCircle.centre.Y);
                        }
                    }
                    if(state == State.Carky){
                        if(remember.previewNAngle != null){
                            remember.previewNAngle.Points.get(remember.previewNAngle.Points.size() - 1).X = mouseX;
                            remember.previewNAngle.Points.get(remember.previewNAngle.Points.size() - 1).Y = mouseY;
                        }
                    }
                    if(state == State.Tuzka && e.isControlDown()){
                        drawer.Rucicka(mouse, strenght, color);
                    }
                    System.err.println(e.getButton());
                    if(state == State.Mazatko && e.isControlDown()){
                        drawer.Rucicka(mouse, strenght, back_color);

                    }
                }

                drawer.DrawEverything(remember);
                image = drawer.GetImage();
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    System.out.println("Levé tlačítko stisknuto");
                }
                        System.out.println("Nová linka");

                
                if (SwingUtilities.isLeftMouseButton(e)) {
                    
                    if(state == State.Rucicka){
                        if(nearest == null && nearest_radius == null){
                            nearest = GetNearest(mouse, remember);
                        }
                        else{
                            nearest = null;
                            nearest_radius = null;
                        }
                    }
                    if(state == State.Carka){
                        if(remember.previewLine == null){
                            remember.previewLine = new Line(mouse.Copy(),mouse.Copy(),color,strenght,!e.isControlDown(),padding);
                    
                        }
                        else{
                            remember.Lines.add(remember.previewLine.Copy());
                            remember.previewLine = null;
                        }
                    }
                    if(state == State.Krouzek){
                        if(remember.previewCircle == null){
                            remember.previewCircle = new Circle(mouse.Copy(),10,strenght,color);
                        }
                        else{
                            remember.Circles.add(remember.previewCircle.Copy());
                            remember.previewCircle = null;
                        }
                    }
                    if(state == State.Carky){
                        if(remember.previewNAngle == null){
                            remember.previewNAngle = new NAngle(mouse.Copy(), strenght,color);
                            remember.previewNAngle.Points.add(mouse.Copy());
                        }
                        else{
                            remember.previewNAngle.Points.add(mouse.Copy());
                            
                        }
                    }
                    if(state != State.Carky){
                        if(remember.previewNAngle != null){
                            remember.NAngles.add(remember.previewNAngle.Copy());
                            remember.previewNAngle = null;
                        }
                    }
                    //if(state == State.Tuzka){
                    //    mouseX = e.getX();
                    //    mouseY = e.getY();
                    //    mouse.X = mouseX;
                    //    mouse.Y = mouseY;
                    //    drawer.Rucicka(mouse, strenght, color);
                    //}
                    if(state == State.Kyblicek){
                        Fill newFill = new Fill(drawer.GetImage().getRGB(mouseX, mouseY),color,rnd_to_fill);
                        remember.Fills.add(newFill);
                        newFill.actual_border.add(mouse.Copy());
                        new Thread(() -> {
                            while (drawer.Occupy(newFill)) {
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException ex) {
                                    Thread.currentThread().interrupt();
                                    return;
                                }

                                image = drawer.GetImage();

                                SwingUtilities.invokeLater(() -> repaint());
                            }
                        }).start();
                    }
                    //if(state == State.Mazatko){
                    //    drawer.Rucicka(mouse, strenght, back_color);
                    //
                    //}
                    /*
                    else if(remember.previewLine != null){
                        //remember.Lines.add(new Line(remember.previewLine.point1, remember.previewLine.point2, remember.previewLine.color, remember.previewLine.isSolid));
                        remember.Lines.add(remember.previewLine.Copy());
                        remember.previewLine = null;
                    }
                    */
                }
                
            }
        });
        
        







        //Line line = new Line(new Point(40, 80), new Point(50, 40),Color.YELLOW.getRGB(),10,true,5);
        //drawer.DrawLine(line, Color.YELLOW.getRGB(),true);

        //drawer.Draw(line);
        //drawer.DrawLine(line);

        //image = drawer.GetImage();
        //repaint();
        //while(true){
        //
        //}
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pixel Drawing");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(400, 300);

        JPanel panel = new Main();

            JButton button_rucicka = new JButton("Rucicka");
            button_rucicka.addActionListener(Listeners.GetRucickaListener());
            panel.add(button_rucicka);

            JButton button_carka = new JButton("Carka");
            button_carka.addActionListener(Listeners.GetCarkaListener());
            panel.add(button_carka);

            JButton button_carky = new JButton("Carky");
            button_carky.addActionListener(Listeners.GetCarkyListener());
            panel.add(button_carky);

            JButton button_krouzek = new JButton("Krouzek");
            button_krouzek.addActionListener(Listeners.GetKrouzekListener());
            panel.add(button_krouzek);

            JButton button_kyblicek = new JButton("Kyblicek");
            button_kyblicek.addActionListener(Listeners.GetKyblicekListener());
            panel.add(button_kyblicek);

            JButton button_tuzka = new JButton("Tuzka");
            button_tuzka.addActionListener(Listeners.GetTuzkaListener());
            panel.add(button_tuzka);

            JButton button_mazatko = new JButton("Mazatko");
            button_mazatko.addActionListener(Listeners.GetMazatkoListener());
            panel.add(button_mazatko);

            JSlider color_slider = new JSlider(0, 100, 50);
            color_slider.addChangeListener(Listeners.GetColorListener());
            panel.add(color_slider);

            JSlider thickness_slider = new JSlider(1, 100,strenght);
            thickness_slider.addChangeListener(Listeners.GetStrenghtListener());
            panel.add(thickness_slider);

            JSlider padding_slider = new JSlider(0, 100,padding);
            padding_slider.addChangeListener(Listeners.GetPaddingListener());
            panel.add(padding_slider);

            JSlider rnd_to_fill_slider = new JSlider(0, 100,(int)(rnd_to_fill*100));
            rnd_to_fill_slider.addChangeListener(Listeners.GetRndToFillListener());
            panel.add(rnd_to_fill_slider);

        
        frame.add(new Main());

        frame.add(panel);

        frame.setVisible(true);
        
    }
}
