import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(){ //window area
        setTitle("Snake"); //snake
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit cross
        setSize(340,340);//size
        setLocation(400,400);//location
        add(new GameField());
        setVisible(true);
    }
    public static void main(String[] args){
        MainWindow mw = new MainWindow();

    }
}