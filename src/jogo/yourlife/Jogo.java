package jogo.yourLife;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import jogo.personagem.Personagem;
import static jdk.nashorn.internal.objects.ArrayBufferView.buffer;
import jogo.bloco.Bloco;
import jogo.objeto.Objeto;

public class Jogo extends JPanel implements ActionListener, KeyListener {
    int x = 100;
    int y = 0;
    int grade = 16;
    int telaLargura = grade*21;
    int telaAltura = grade*21;
    int centroTelaX = telaLargura/2;
    int centroTelaY= telaAltura/2;
    int h=1;
    boolean t;
    boolean cameraFixa=false;
    int tempo=0;
    BufferedImage buffer;
    ArrayList <Personagem> listaPerson = new ArrayList <Personagem> ();
    ArrayList<Bloco> listaBloco = new ArrayList<Bloco>();
    ArrayList<Objeto> listaObjeto = new ArrayList<Objeto>();
    Random rnd = new Random();
    Timer timer = new Timer(1000,this);
    BufferedImage IMG;
    Personagem person = new Personagem (0,0,grade, grade) ;
    Scanner scn = new Scanner(System.in);
    
    public Jogo(){
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        buffer = new BufferedImage(telaLargura, telaAltura,
		BufferedImage.TYPE_4BYTE_ABGR);
    }
    
    
    public void actionPerformed(ActionEvent e){
        tempo++;
        roda();
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        int c = e.getKeyCode();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_SPACE){
            cameraFixa = !cameraFixa;
        }
        if (c == KeyEvent.VK_S){
           scn.close();
        }
    }

    private void fixaCamera() {
        int diferencaX = centroTelaX - listaPerson.get(0).getCentroX();
        int diferencaY = centroTelaY - listaPerson.get(0).getCentroY();
        for (int i = 0; i < listaPerson.size(); i++) {
            listaPerson.get(i).x += diferencaX;
            listaPerson.get(i).y += diferencaY;
        }
        for (int i = 0; i < listaBloco.size(); i++) {
            listaBloco.get(i).x += diferencaX;
            listaBloco.get(i).y += diferencaY;
        }
    }
    
    private void criaBlocos(){
            try {
			IMG=ImageIO.read(getClass().getResource("../imagem/bloco.png"));
            } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            }
            listaPerson.clear();
    }
    
    
    

    private void adicionaObjetos() {
        listaPerson.add(new Personagem(0, 0, grade, grade));
        listaPerson.add(new Personagem(40, 0, grade, grade));
        
        criaCasa(4,0);
        criaCasa(20,0);
        
        
        listaBloco.add(new Bloco(grade*10, grade*10, grade, grade));
        listaBloco.add(new Bloco(grade*10, grade*11, grade, grade));
        listaBloco.add(new Bloco(grade*10, grade*12, grade, grade));
        listaBloco.add(new Bloco(grade*10, grade*13, grade, grade));
        listaBloco.add(new Bloco(grade*10, grade*14, grade, grade));
        listaBloco.add(new Bloco(grade*10, grade*15, grade, grade));
        
    }

    private void criaCasa(int x, int y) {
        listaBloco.add(new Bloco(grade*(x), grade*(y), grade, grade));
        listaBloco.add(new Bloco(grade*(x+1), grade*(y), grade, grade));
        listaBloco.add(new Bloco(grade*(x+2), grade*(y), grade, grade));
        listaBloco.add(new Bloco(grade*(x+2), grade*(y+1), grade, grade));
        listaBloco.add(new Bloco(grade*(x+2), grade*(y+2), grade, grade));
        listaBloco.add(new Bloco(grade*(x+1), grade*(y+2), grade, grade));
        listaBloco.add(new Bloco(grade*(x), grade*(y+2), grade, grade));
        listaBloco.add(new Bloco(grade*(x), grade*(y+1), grade, grade));
    }
    

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D dbg = (Graphics2D) g;
        dbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        dbg.setColor(Color.BLACK);
        //desenha todos os personagens
        for (int i = 0; i < listaPerson.size(); i++) {
            dbg.drawOval(listaPerson.get(i).x, listaPerson.get(i).y, listaPerson.get(i).largura, listaPerson.get(i).altura);
        }
        
        //desenha todas os blocos
        for (int i = 0; i < listaBloco.size(); i++) {
            dbg.fillRect(listaBloco.get(i).x, listaBloco.get(i).y, listaBloco.get(i).largura, listaBloco.get(i).altura);
            listaBloco.get(i).desenha(g, IMG);
        }
        
        //desenha cruz de localização do centro da tela
        dbg.drawLine(centroTelaX, 0, centroTelaX, telaAltura);
        dbg.drawLine(0, centroTelaY, telaLargura, centroTelaY);
        
        dbg.setColor(Color.red);
        dbg.drawString("tempo: "+tempo, 0, 10);
    }
    
    public void roda(){
        if(tempo < 4){
        listaPerson.get(0).y += grade;
        listaPerson.get(0).x += 0;
        }
        if(tempo > 5){
        listaPerson.get(0).y += 0;
        listaPerson.get(0).x += grade;
        }
        if(cameraFixa){
            fixaCamera();
        }
    }
    

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Sample Frame");
        Jogo jogo = new Jogo();
        frame.add(jogo);
        frame.setSize(jogo.telaLargura, jogo.telaAltura);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jogo.criaBlocos();
        jogo.adicionaObjetos();
        while(true){
            while(true){
            //jogo.roda();
            jogo.repaint();
            Thread.sleep(10);
            }
        }
    }
}