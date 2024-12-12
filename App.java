// import javax.swing.*;

// public class App {
//     public static void main(String[] args) throws Exception {
//         int boardLength = 360;
//         int boardHeight = 640;

//         JFrame frame = new JFrame("Flappy Bird");
//         frame.setSize(boardLength, boardHeight);

//         frame.setLocationRelativeTo(null);
//         frame.setDefaultCloseOperation(3);

//         FlappyBird flappyBird = new FlappyBird();
//         frame.add(flappyBird);
//         frame.pack();
//         flappyBird.requestFocus();
//         frame.setVisible(true);
//     }
// }

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    public static void main(String[] args) {
        int boardLength = 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(boardLength, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Create game panel
        FlappyBird flappyBird = new FlappyBird();

        // Welcome Screen Panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
        JButton playButton = new JButton("Play");
        playButton.setFocusable(false);
        playButton.setFont(new Font("Arial", Font.BOLD, 24));
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "game");
                flappyBird.startGame(); // Start the game
                flappyBird.requestFocus();
            }
        });
        welcomePanel.add(new JLabel("Welcome to Flappy Bird!", JLabel.CENTER), BorderLayout.CENTER);
        welcomePanel.add(playButton, BorderLayout.SOUTH);

        // Add panels to main panel
        mainPanel.add(welcomePanel, "welcome");
        mainPanel.add(flappyBird, "game");

        // Set initial screen
        cardLayout.show(mainPanel, "welcome");
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
