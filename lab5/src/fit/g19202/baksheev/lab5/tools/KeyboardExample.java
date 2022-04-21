package com.edu4java.minitennis4;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class KeyboardExample extends JPanel {

    public KeyboardExample() {
        KeyListener listener = new MyKeyListener();
        addKeyListener(listener);
        add(new JLabel("SUS"));
        setFocusable(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mini Tennis");
        KeyboardExample keyboardExample = new KeyboardExample();
        frame.add(keyboardExample);
        frame.setSize(200, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("keyPressed=" + KeyEvent.getKeyText(e.getKeyCode()));
        }

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("keyReleased=" + KeyEvent.getKeyText(e.getKeyCode()));
        }
    }
}