package CourseWork.courseWork;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * Write a description of class GUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GUI
{
    public static void main (String [] args){
        JFrame frame = new JFrame (" My GUi");
        frame.setSize(430, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label =new JLabel("Lalit Bhandari",JLabel.CENTER);
        frame.add(label);
        frame.setVisible(true);
        
    }
}