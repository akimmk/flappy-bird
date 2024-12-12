import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
// import java.util.random.*;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardHeight = 640;
    int boardLength = 360;

    //Image
    Image backgroundImage;
    Image birdImage;
    Image toppipImage;
    Image bottompipImage;

    //Bird
    int birdX = boardLength/8;
    int birdY = boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird{
        int x = birdX;
        int y = birdY;
        int width =  birdWidth;
        int height = birdHeight;
        Image img;
        Bird(Image img){
            this.img = img;
        }
    }

    //pips
    int pipeX = boardLength;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img){
            this.img = img;
        }
    }

    //game lop
    Bird bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer placePipeTimer;
    Timer gameLoop;
    
    boolean gameOver = false;
    double score = 0;

    FlappyBird(){
        setFocusable(true);
        addKeyListener(this);
        setPreferredSize(new Dimension(boardLength, boardHeight));
        setBackground(Color.blue);

        backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImage =  new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        toppipImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottompipImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        //Bird
        bird = new Bird(birdImage);

        pipes = new ArrayList<Pipe>();
        //place pipe timer
        placePipeTimer = new Timer(1500, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                placePipe();
            }
        });
        // placePipeTimer.start();`

        //game Timer
        gameLoop = new Timer(1000/60, this);
        // gameLoop.start();
    }

    public void placePipe(){
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*pipeHeight/2);
        Pipe toppipe = new Pipe(toppipImage);
        toppipe.y = randomPipeY;
        pipes.add(toppipe);

        int openingSpace = boardHeight/4;

        Pipe bottompipe = new Pipe(bottompipImage);
        bottompipe.y = toppipe.y + pipeHeight + openingSpace;
        pipes.add(bottompipe);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void move(){
        velocityY+=gravity;
        bird.y+=velocityY;
        bird.y=Math.max(bird.y,0);

        for(int i = 0;i<pipes.size();i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;
                score += 0.5;
            }

            if(collision(bird, pipe)){
                gameOver = true;
            }
    
        }

        if(bird.y > boardHeight){
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b){
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImage, 0,0, boardLength, boardHeight,null);
        g.drawImage(birdImage, bird.x, bird.y, bird.width, bird.height, null);

        for( int i = 0 ;i<pipes.size();i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.ITALIC,32));
        if(gameOver){
            g.drawString("Game Over" + String.valueOf((int) score) , 10,35);
        }else{
            g.drawString(String.valueOf((int) score), 10, 35);
        }

    }

    public void startGame() {
        // Reset game state
        bird.y = birdY;
        velocityY = 0;
        pipes.clear();
        score = 0;
        gameOver = false;

        // Start timers
        placePipeTimer.start();
        gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if(gameOver){
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            velocityY=-9;

            if(gameOver){
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipeTimer.start();
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyReleased(KeyEvent e) {
    }
}
