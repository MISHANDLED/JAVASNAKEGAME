package com.company.SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener{


    private Image apple;
    private Image dot;
    private Image head;

    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;

    private int apple_x;
    private int apple_y;
    private int score=0;

    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];

    private boolean leftDirection = false;
    private boolean rightDirection =  true;
    private boolean upDirection =  false;
    private boolean downDirection =  false;
    private boolean inGame = true;

    private int dots;

    private Timer timer;

    Board(){
        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.BLACK);
        addKeyListener(new TAdapter());
        setFocusable(true);
        loadImages();
        initGame();
    }

    public void loadImages(){
        ImageIcon image1 = new ImageIcon(ClassLoader.getSystemResource("com/company/Images/apple.png"));
        apple  = image1.getImage();

        ImageIcon image2 = new ImageIcon(ClassLoader.getSystemResource("com/company/Images/dot.png"));
        dot = image2.getImage();

        ImageIcon image3 = new ImageIcon(ClassLoader.getSystemResource("com/company/Images/head.png"));
        head = image3.getImage();
    }

    public void initGame(){

        dots = 3;

        for(int i = 0 ; i < dots ; i++){
            x[i] = 50 - i * DOT_SIZE;
            y[i] = 50;
        }

        locateApple();

        timer = new Timer(140, this);
        timer.start();
    }


    public void locateApple(){

        int RANDOM_POSITION = 29;
        int r = (int)(Math.random() * RANDOM_POSITION);
        apple_x = (r * DOT_SIZE);

        r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = (r * DOT_SIZE);
    }

    public void checkApple(){
        if((x[0] == apple_x) && (y[0] == apple_y)){
            dots++;
            score++;
            locateApple();
        }
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        draw(g);
    }

    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);

            for(int i = 0; i < dots ; i++){
                if(i == 0){
                    g.drawImage(head, x[i], y[i], this);
                }else{
                    g.drawImage(dot, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
    }


    public void gameOver(Graphics g){
        String message1 =  "Game Over";
        String message2 = "Apples Collected: " + score;
        String message3 = "Score: " + (score*15) ;
        Font font = new Font("TimesRoman", Font.BOLD, 14);
        FontMetrics metrics = getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(message1, (300 - metrics.stringWidth(message1)) / 2 , (300-metrics.getHeight()-20)/2);
        g.drawString(message2, (300 - metrics.stringWidth(message2)) / 2 , 300/2);
        g.drawString(message3, (300 - metrics.stringWidth(message3)) / 2 , (300+metrics.getHeight()+20)/2);
    }



    public void checkCollision(){

        for(int i = dots ; i > 0 ; i--){
            if((i > 4) && (x[0] == x[i]) && (y[0] == y[i])){
                inGame = false;
                break;
            }
        }

        if(y[0] >= 300 || x[0] >= 300 || x[0] < 0 || y[0] < 0){
            inGame = false;
        }

        if(!inGame){
            timer.stop();
        }
    }

    public void move(){

        for(int i = dots ; i > 0 ; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if(leftDirection){
            x[0] -= DOT_SIZE;
        }
        if(rightDirection){
            x[0] += DOT_SIZE;
        }
        if(upDirection){
            y[0] -= DOT_SIZE;
        }
        if(downDirection){
            y[0] += DOT_SIZE;
        }

    }

    public void actionPerformed(ActionEvent ae){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }


    private class TAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e){
            int key =  e.getKeyCode();

            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if(key == KeyEvent.VK_UP && (!downDirection)){
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }

            if(key == KeyEvent.VK_DOWN && (!upDirection)){
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }

}

