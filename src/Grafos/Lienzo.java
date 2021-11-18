package Grafos;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Lienzo extends JPanel implements MouseListener, MouseMotionListener{
    
    private Vector<Nodo> vectorNodo;
    private Vector<Enlace> vectorEnlace;
    private Point p1, p2;
    private Nodo nodoMover;
    private int iNodo;
    
    public Lienzo(){
        this.vectorNodo=new Vector<>();
        this.vectorEnlace=new Vector<>();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    public void paint(Graphics g){
        super.paint(g);
        for(Nodo nodos:vectorNodo){
            nodos.pintar(g);
        }
        for(Enlace enlace: vectorEnlace){
            enlace.pintar(g);
        }
    }
    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getButton()==MouseEvent.BUTTON1){
            String nombre=JOptionPane.showInputDialog("Ingrese un nombre");
            this.vectorNodo.add(new Nodo(me.getX(),me.getY(), nombre));
            repaint();
        }
        if(me.getButton()==MouseEvent.BUTTON3){
            for(Nodo nodo:vectorNodo){
                if(new Rectangle(nodo.getX()-Nodo.d/2, nodo.getY()-Nodo.d/2, Nodo.d,Nodo.d).contains(me.getPoint())){
                    if (p1==null)
                        p1=new Point(nodo.getX(),nodo.getY());
                    else{
                        p2=new Point(nodo.getX(),nodo.getY());
                        String nombre=JOptionPane.showInputDialog("Ingrese un nombre");
                        this.vectorEnlace.add(new Enlace(p1.x, p1.y, p2.x, p2.y, nombre));
                        repaint();
                        p1=null;
                        p2=null;
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        int iN = 0;
        for(Nodo nodo: vectorNodo){
            if(new Rectangle(nodo.getX()-Nodo.d/2, nodo.getY()-Nodo.d/2, Nodo.d, Nodo.d).contains(me.getPoint())){
                nodoMover = nodo;
                iNodo= iN;
                break;
            }
            iN++;
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        nodoMover=null;
        iNodo=-1;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if(nodoMover!=null){
            this.vectorNodo.set(iNodo,new Nodo(me.getX(), me.getY(), nodoMover.getNombre()));
            int iE=0;
            for(Enlace enlace:vectorEnlace){
                if(new Rectangle(enlace.getX1()-Nodo.d/2, enlace.getY1()-Nodo.d/2, Nodo.d, Nodo.d).contains(me.getPoint())){
                    this.vectorEnlace.set(iE, new Enlace(me.getX(), me.getY(), enlace.getX2(), enlace.getY2(), enlace.getNombre()));
                }
                else if(new Rectangle(enlace.getX2()-Nodo.d/2, enlace.getY2()-Nodo.d/2, Nodo.d, Nodo.d).contains(me.getPoint())){
                    this.vectorEnlace.set(iE, new Enlace(me.getX(), me.getY(), enlace.getX1(), enlace.getY1(), enlace.getNombre()));
                }
                iE++;
            }
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }

}
