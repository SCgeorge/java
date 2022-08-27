import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 300;//size
    private final int DOT_SIZE = 15;//pixels
    private final int ALL_DOTS = 400;//amount
    private Image dot;
    private Image food;
    private int foodX;
    private int foodY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private Timer timer; //timer
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private int dots;

    public GameField(){
        setBackground(Color.black); //bg color
        loadImg();//callout
        initGame();//callout
        addKeyListener(new KeyListener());
        setFocusable(true);
    }

    public void initGame(){ //initialization
        dots = 3;
        for(int i = 0; i < dots;i++){
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(300,this);//300 ms for spawn
        timer.start();
        createFood();
    }

    public void createFood(){ //spawn of food
        foodX = new Random().nextInt(20)*DOT_SIZE;
        foodY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImg(){ //load an img etc.
            ImageIcon ia = new ImageIcon("food.png");
            food = ia.getImage();
            ImageIcon id = new ImageIcon("dot.png");
            dot = id.getImage();

     }

    @Override
    protected void paintComponent(Graphics g) {//rewriting field
        super.paintComponent(g);
        if(inGame) {
            g.drawImage(food, foodX, foodY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
            else{ //gameover
                String str = "Game Over";
                Font f = new Font("Sans-serif",Font.BOLD,14 );
                g.setColor(Color.white);
                g.setFont(f);
                g.drawString(str,125,SIZE/3);
            }
        }


    public void move(){ //moves
         for (int i = dots; i < 0; i--) {
             x[i] = x[i-1];
             y[i] = x[i-1];
         }
         if(left) {
             x[0] -= DOT_SIZE;
         }
         if(right) {
             x[0] += DOT_SIZE;
         }
         if(up) {
             y[0] -= DOT_SIZE;
         }
         if(down) {
             y[0] += DOT_SIZE;
         }

     }

     public void checkFood(){ // checker for the food spawn
        if(x[0] == foodX && y[0] == foodY){
            dots++;
            createFood();
        }
     }

     public void checkCollisions() { //collisions to move
         for (int i = dots; i > 0; i--) {
             if (i > 4 && x[0] == x[i] && y[0] == y[i]) ;
             {
                 inGame = false;
             }
         }
         if(x[0] > SIZE){
             inGame = false;
         }
         if(x[0] < 0){
             inGame = false;
         }
         if(y[0] > SIZE){
             inGame = false;
         }
         if(y[0] < 0){
             inGame = false;
         }
     }

     @Override
    public void actionPerformed(ActionEvent e){ //actiont event checker
        if(inGame){
            checkFood();
            checkCollisions();
            move();
        }
        repaint();
     }

     class KeyListener extends KeyAdapter{ //keylistener + restrictions for unrelated moves
        @Override
         public void keyPressed(KeyEvent e){
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                left = false;
                up = true;
                right = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                left = false;
                right = false;
                down = true;
            }
        }
     }
}
