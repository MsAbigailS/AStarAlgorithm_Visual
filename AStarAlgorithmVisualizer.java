import javax.swing.*; // TODO: Come back and make efficient

public class AStarAlgorithmVisualizer {
    public static void main(String[] args) {

        // creating JFrame for graphics
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBounds(500, 100, 500, 500);

        // creating an instance of displayPanel
        DisplayPanel dp = new DisplayPanel();

        // setting content of displayPanel
        mainFrame.setContentPane(dp);
        mainFrame.setVisible(true);

        // "refreshing" the graphics
        while(true){
            dp.repaint();
        }
    }
}