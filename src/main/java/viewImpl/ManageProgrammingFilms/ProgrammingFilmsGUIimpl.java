package viewImpl.ManageProgrammingFilms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.EventObject;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


import com.mindfusion.common.ChangeListener;
import com.mindfusion.common.DateTime;
import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.CalendarView;
import com.mindfusion.scheduling.ThemeType;





public class ProgrammingFilmsGUIimpl {
        
        private static final long serialVersionUID = 7114066347061701832L;
        
       // private final InfoFilmsGUIfactory factory = new InfoFilmsGUIfactoryImpl();
        
        private static final String FRAME_NAME = "Programming  film";
        private static final double PROPORTION = 1.15;
        private final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        private final JFrame frame = new JFrame(FRAME_NAME);
        private final Container container = frame.getContentPane();

        //real dimension of the screen
        private final int screenWidth = (int) screen.getWidth();
        private final int screenHeight = (int) screen.getHeight();
        //real dimension of my frame
        private final int frameWidth = (int) (screenWidth / PROPORTION);
        private final int frameHeight = (int) (screenHeight / PROPORTION);
        
        //private final JLabel title = new JLabel ("Manage films programmation");
        private JButton home = new JButton("Home");
        private JButton addProgrammation = new JButton("Add programmation");
        private JButton filterBy = new JButton("Filter by");
        private Calendar calendar;
        
        public ProgrammingFilmsGUIimpl() {     
        	
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel();
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel westPanel = new JPanel(new BorderLayout(0,50));
        JPanel eastPanel = new JPanel();
        
        JPanel optionPanel = new JPanel();
        
        
        
        
        
        String[] columnNames = new String[] {"Film","Hall","Start time", "End time" };
        Object[][] data = new Object[2][4]; // row columns 
        
        data[0][0] = "Film1";
        data[0][1] = "Film1 Hall";
        data[0][2] = "Film1 start time";
        data[0][3] = "Film1 end time";
        
        data[1][0] = "Film2";
        data[1][1] = "Film2 Hall";
        data[1][2] = "Film2 start time";
        data[1][3] = "Film2 end time";
        
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model) {
        	public boolean isCellEditable(int row, int column) {
                return false;
         }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        calendar = new Calendar();
        
        
        calendar.setCurrentTime(DateTime.now());
		calendar.setDate(DateTime.today());//year ,month, day
		
		
		// Calendar initialization start
		calendar.beginInit();
		calendar.setCurrentView(CalendarView.SingleMonth);
		calendar.setTheme(ThemeType.Silver);
		calendar.getMonthRangeSettings().setMonthsPerRow(1);
		calendar.getMonthRangeSettings().setNumberOfMonths(1);
		//calendar.getMonthRangeSettings().setScrollInterval(1);
		calendar.getSelection().setAllowMultiple(false);
		calendar.getMonthSettings().getDaySettings().setHeaderSize(0);
		calendar.endInit();
		// Calendar initialization end
		
		// Listen for selection changes
		calendar.getSelection().addChangeListener(new ChangeListener(){
		@Override
			public void changed(EventObject e) {
					onSelectionChanged();
					//System.out.println(calendar.getDa);
			}
		});
		
		
		
		
		
        
		mainPanel.add(southPanel,BorderLayout.SOUTH);
		mainPanel.add(northPanel,BorderLayout.NORTH);
		mainPanel.add(westPanel,BorderLayout.WEST);
		mainPanel.add(eastPanel,BorderLayout.EAST);
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		
		northPanel.add(home,BorderLayout.EAST);
		
		
		optionPanel.setLayout(new BoxLayout(optionPanel,BoxLayout.Y_AXIS));
		
		westPanel.add(optionPanel,BorderLayout.SOUTH); // add panel in south of west panel
			
		westPanel.add(calendar, BorderLayout.NORTH);
		calendar.setPreferredSize(new Dimension(250,300));
		
		
		optionPanel.add(addProgrammation);
		addProgrammation.setMaximumSize(new Dimension(250,100)); //width , height
		addProgrammation.setPreferredSize(new Dimension(250,100));
				
		optionPanel.add(filterBy,BorderLayout.CENTER);
		filterBy.setMaximumSize(new Dimension(250,100)); //width,height 
		filterBy.setPreferredSize(new Dimension(250,100));

		
		container.add(mainPanel);
		
 
			
		
        frame.pack();
        frame.setMinimumSize(new Dimension(screenWidth/2,screenHeight/2));
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        }
        

        protected void onSelectionChanged() {
    		if (calendar.getSelection().getIsEmpty())
    		{
    			//scheduler.getTimetableSettings().getDates().clear();
    			return;
    		}

    		int day = calendar.getSelection().getRanges().get(0).getDay();
    		int month = calendar.getSelection().getRanges().get(0).getMonth();
    		int year = calendar.getSelection().getRanges().get(0).getYear();
    		
    		LocalDate localDate = LocalDate.of(year, month , day);
    		System.out.println(localDate);
    		
    	}
        
        
        
        
        public static void main (String [] args) {
        	ProgrammingFilmsGUIimpl p = new ProgrammingFilmsGUIimpl();
        }
        
        
}
