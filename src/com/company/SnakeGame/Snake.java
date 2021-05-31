package com.company.SnakeGame;

import javax.swing.*;

public class Snake extends JFrame {
    public Snake() {
        add(new Board());
        pack();
        setLocationRelativeTo(null);
        setTitle("Snake Game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        new Snake().setVisible(true);
    }
}
