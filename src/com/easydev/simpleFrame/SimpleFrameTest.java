package com.easydev.simpleFrame;

import javax.swing.*;
import java.awt.*;

public class SimpleFrameTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SimpleFrame simpleFrame = new SimpleFrame();
            Container contentPane = simpleFrame.getContentPane();
            simpleFrame.setVisible(true);
        });
    }

    public void addFrameContent (Container contentPane) {

    }

}

class SimpleFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;

    public SimpleFrame () {
        //全屏
//        setUndecorated(true);//去边框
//        setLayout(null);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);//最大化
//        setResizable(false);//不能改变大小
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new NotHelloWorld());
        pack();
    }
}

class NotHelloWorld extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
        for (int i = 0 ; i < 10 ; i++) {
            g.drawString("hello world swing" , 75 , 100 + 10 * i);
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300 , 200);
    }
}