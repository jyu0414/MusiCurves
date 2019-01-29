package jp.ac.keio.sfc.oop.musicurves;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Sketch extends JPanel implements MouseListener, MouseMotionListener {

    public Sketch(){
        addMouseListener(this);
        addMouseMotionListener(this);
        repaint();
    }

    MouseMode mode = MouseMode.Pen;

    Point2D mousePoint = new Point2D.Float(0,0);

    Point2D mouseStart = new Point2D.Float(0,0);

    Boolean isDragging = false;

    int referenceTonePosition;
    int octaveDistance;

    private static final double[] SCALE = {1,0.5,1,1,0.5,1,1};


    ArrayList<ArrayList<Point2D>> lines = new ArrayList<>();

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D grap = (Graphics2D) g;

        drawStave(grap);

        lines.forEach(line -> {

            if(line.size() != 0)
            {
                GeneralPath path = new GeneralPath();
                grap.setPaint(ThemeColour.purple);
                grap.setStroke(new BasicStroke(5));
                path.moveTo(line.get(0).getX(),line.get(0).getY());
                for(int i = 1; i < line.size(); i++) {
                    path.lineTo(line.get(i).getX(), line.get(i).getY());
                }
                grap.draw(path);
            }

        });


        switch (mode) {
            case Pen:
                drawPen(grap);
                break;

            case Eraser:
                drawEraser(grap);
                break;

            case None:
                break;

        }


    }

    void drawStave(Graphics2D grap)
    {
        grap.setStroke(new BasicStroke(1));

        int verticalMargin = (int) (grap.getClipBounds().height * 0.8 / 10);
        referenceTonePosition = (int)((float)grap.getClipBounds().height * 0.1 + (float)verticalMargin * 2.5f);
        octaveDistance = (int)((float)verticalMargin * 3.5f);


        for(int i = 0; i < 11; i++)
        {

            if(i == 5) continue;
            int y = (int) (grap.getClipBounds().height * 0.1 + i * verticalMargin);
            grap.drawLine(0,y,grap.getClipBounds().width,y);
        }

        try {
            Image img = ImageIO.read(new File("resources/bassclef.png"));
            float aspectRatio = (float)(((BufferedImage) img).getWidth()) / (float)(((BufferedImage) img).getHeight());
            grap.drawImage(img,30 ,(int)(grap.getClipBounds().height * 0.1 + verticalMargin * 6)+5,(int) (180 * aspectRatio),180,null);

            Image img2 = ImageIO.read(new File("resources/trebleclef.png"));
            float aspectRatio2 =  (float)(((BufferedImage) img2).getWidth())/ (float)(((BufferedImage) img2).getHeight());
            grap.drawImage(img2,20 ,(int)(grap.getClipBounds().height * 0.1) - 25,(int) (300 * aspectRatio2),300,null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    ArrayList<double[][]> getFrequencyLines()
    {
        ToneScaleManager tsm = new ToneScaleManager(referenceTonePosition,octaveDistance);
        ArrayList<double[][]> freqLines = new ArrayList<>();
        for(int j = 0; j < lines.size(); j++)
        {
            if(lines.get(j).size() == 0) continue;

            freqLines.add(tsm.getfrequencyFromLine(lines.get(j)));
        }
        return freqLines;
    }



    void drawPen(Graphics2D grap)
    {
        grap.setStroke(new BasicStroke(2));
        grap.setColor(ThemeColour.orange);
        grap.drawLine((int)mousePoint.getX(),0,(int)mousePoint.getX(),grap.getClipBounds().height);

        if(isDragging){
            grap.setColor(ThemeColour.orange);
            grap.drawLine((int)mouseStart.getX(),0,(int)mouseStart.getX(),grap.getClipBounds().height);

            grap.setColor(new Color(ThemeColour.orange.getRed(),ThemeColour.orange.getGreen(),ThemeColour.orange.getBlue(),100));
            grap.fillRect((int)mouseStart.getX(),0,(int)mousePoint.getX() - (int)mouseStart.getX(),grap.getClipBounds().height);
        }

        grap.setColor(ThemeColour.purple);
        grap.drawOval((int)mousePoint.getX() - 6,(int)mousePoint.getY() - 6,12,12);
    }

    void drawEraser(Graphics2D grap)
    {
        grap.setStroke(new BasicStroke(2));
        grap.setColor(Color.white);
        grap.fillOval((int)mousePoint.getX() - 6,(int)mousePoint.getY() - 6,12,12);
        grap.setColor(ThemeColour.purple);
        grap.drawOval((int)mousePoint.getX() - 6,(int)mousePoint.getY() - 6,12,12);

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isDragging = false;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        BufferedImage image = new BufferedImage(16,16,BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(new Color(0,0,0,0));
        g2.fillRect(0,0, 16,16);
        g2.dispose();
        ((Component) e.getSource()).setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0,0), "null_cursor"));
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ((Component) e.getSource()).setCursor(Cursor.getDefaultCursor());
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        switch(mode){
            case Pen:
                if(!isDragging)
                {
                    isDragging = true;
                    mouseStart.setLocation(mousePoint.getX(),mousePoint.getX());
                    lines.add(new ArrayList<Point2D>());
                }
                mousePoint.setLocation(mousePoint.getX(),e.getY());
                if(mousePoint.getX() < e.getX()) {
                    mousePoint.setLocation(e.getX(),mousePoint.getY());
                    lines.get(lines.size() - 1).add(new Point2D.Double(mousePoint.getX(),mousePoint.getY()));
                }
                break;
            case Eraser:
                mousePoint.setLocation(e.getX(),e.getY());
                for(int j = 0; j < lines.size(); j++)
                {
                    for(int i = 0; i < lines.get(j).size(); i++) {
                        if(Point2D.distance(e.getX(),e.getY(), lines.get(j).get(i).getX(), lines.get(j).get(i).getY()) < 10)
                        {
                            lines.remove(j);
                            break;
                        }
                    }
                }

        }


        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePoint.setLocation(e.getX(),e.getY());
        repaint();
    }

    enum MouseMode {
        Pen,
        Eraser,
        None
    }
}
