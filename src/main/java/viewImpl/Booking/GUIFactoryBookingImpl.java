package viewImpl.Booking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import utilities.ProgrammedFilm;
import utilities.Row;
import utilities.SeatState;
import view.Booking.GUIFactoryBooking;

public class GUIFactoryBookingImpl implements GUIFactoryBooking {
    private static final double WIDTH_PERC_FRAME = 0.5;
    private static final double HEIGHT_PERC_FRAME = 0.5;
    
    private static final double WIDTH_IMAGE_COVER = WIDTH_PERC_FRAME / 5;
    private static final double HEIGHT_IMAGE_COVER = HEIGHT_PERC_FRAME / 2;
    
    private static final Color COLOR_BORDER_INFOPANEL = Color.black;
   
    private static final String FS = File.separator;
    private static final String pathSeatTaken = System.getProperty("user.home") + FS + "OOPcinemaFile" + FS + "imageSeatTaken.png";
    private static final String pathSeatFree = System.getProperty("user.home") + FS + "OOPcinemaFile" + FS + "imageSeatFree.png";
    private static final String pathSeatSelected = System.getProperty("user.home") + FS + "OOPcinemaFile" + FS + "imageSeatSelected.png";
    private static final double WIDTH_IMAGE_SEAT = WIDTH_PERC_FRAME / 15;
    private static final double HEIGHT_IMAGE_SEAT = HEIGHT_PERC_FRAME/ 15;
    
    public JFrame getBaseFrame(String title) {
        final JFrame frame = new JFrame();
 
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.getWidth() * WIDTH_PERC_FRAME), (int) (screenSize.getHeight() * HEIGHT_PERC_FRAME));
  
        frame.setTitle(title);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        return frame;
    }
    
    public JPanel getInfoPanel(String info, ActionListener action) {
        JPanel infoPanel = new JPanel(new BorderLayout());
        JButton button = new JButton("back");
        
        JLabel label = new JLabel(info);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        infoPanel.add(label,BorderLayout.CENTER);
        infoPanel.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_INFOPANEL));
        infoPanel.add(button, BorderLayout.WEST);
        
        button.addActionListener( action );
        
        return infoPanel;
    }
    
    public JButton getButtonImage(String title, String path) {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   
        ImageIcon imageIcon = new ImageIcon(path); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance((int) (screenSize.getWidth() * WIDTH_IMAGE_COVER), (int) (screenSize.getHeight() * HEIGHT_IMAGE_COVER),  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
      
        JButton button = new JButton("Title:" + title, imageIcon);
      
        button.setIcon(imageIcon);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.TOP);
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
    }
    public JTable getTable(Set<ProgrammedFilm> film) {
       int row = film.size();
       String[] columnNames = new String[] {"Date","Time","Hall" };
       Object[][] data = new Object[row][columnNames.length];
       int i = 0;
       for(var elem : film) {
     //      data[i][0] = elem.getData();
    //       data[i][1] = elem.getTime();
    //       data[i][2] = elem.getHall();
     //      i++;
       }
       
       
       DefaultTableModel model = new DefaultTableModel(data, columnNames);
       JTable table = new JTable(model) {    
           /**
            * 
            */
           private static final long serialVersionUID = 1L;

           public boolean isCellEditable(int row, int column) {
                  return false;
           }
       };
       table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      
        return table;
    }
    public JButton getButtonSeat(SeatState state, int i, int j) {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon imageIcon = new ImageIcon();
        if(state.equals(SeatState.FREE)) {
             imageIcon = new ImageIcon(pathSeatFree); // load the image to a imageIcon
        }if(state.equals(SeatState.SELECTED)) {
             imageIcon = new ImageIcon(pathSeatSelected); // load the image to a imageIcon
        }if(state.equals(SeatState.TAKEN)) {
             imageIcon = new ImageIcon(pathSeatTaken); // load the image to a imageIcon
        }
        
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance((int) (screenSize.getWidth() * WIDTH_IMAGE_SEAT), (int) (screenSize.getHeight() * HEIGHT_IMAGE_SEAT),  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
      
        JButton button = new JButton( Row.values()[i] + " " + j, imageIcon);
        
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.TOP);
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
       
    }
   
}


