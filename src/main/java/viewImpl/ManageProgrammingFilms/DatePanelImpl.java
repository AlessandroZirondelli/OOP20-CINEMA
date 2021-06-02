package viewImpl.ManageProgrammingFilms;

import static javax.swing.BorderFactory.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.text.DateFormatSymbols;
import java.time.LocalDate;

public class DatePanelImpl extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JComboBox monthNamesComboBox;
	private final JTextField dayTextField;
	private final JTextField yearTextField;
	
	private static final int topEmptyBorder = 10;
	private static final int leftEmptyBorder = 10;
	private static final int bottomEmptyBorder = 10;
	private static final int rightEmptyBorder = 10;
	private static final int topPanelBorder = 0;
	private static final int leftPanelBorder = 5;
	private static final int bottomPanelBorder = 0;
	private static final int rightPanelBorder = 0;
	private static final int rowsGrid = 2;
	private static final int colsGrid = 0;
	private static final int hGapGrid = 5;
	private static final int vGapGrid = 5;
	
	public DatePanelImpl(final LocalDate selectedDate)
	{
			
		setLayout(new BorderLayout());
		setBorder(createCompoundBorder
					(createTitledBorder("Enter Date"),
						createEmptyBorder(topEmptyBorder,leftEmptyBorder,bottomEmptyBorder,rightEmptyBorder)));
												
		final JPanel monthPanel = new JPanel(new GridLayout(rowsGrid,colsGrid,hGapGrid,vGapGrid));
		final DateFormatSymbols dateFormatter = DateFormatSymbols.getInstance(); // to retrieve months name
		final String months [] = dateFormatter.getMonths();
		monthNamesComboBox = new JComboBox(months);
		monthNamesComboBox.removeItemAt(12);
		monthPanel.add(new JLabel("Month",JLabel.CENTER));
		monthPanel.add(monthNamesComboBox);
		
		JPanel dayYearPanel = new JPanel(new GridLayout(rowsGrid,colsGrid,hGapGrid,vGapGrid));
		dayYearPanel.setBorder(createEmptyBorder(topPanelBorder,leftPanelBorder,bottomPanelBorder,rightPanelBorder));
		dayTextField = 	new JTextField();
		dayTextField.setHorizontalAlignment(JTextField.RIGHT);
		yearTextField = new JTextField();
		yearTextField.setHorizontalAlignment(JTextField.RIGHT);
		
		//set date values
		monthNamesComboBox.setSelectedIndex(selectedDate.getMonthValue()-1);
		dayTextField.setText(Integer.toString(selectedDate.getDayOfMonth()));
		yearTextField.setText(Integer.toString(selectedDate.getYear()));
		
		dayYearPanel.add(new JLabel("Day",JLabel.RIGHT));
		dayYearPanel.add(new JLabel("Year",JLabel.RIGHT));
		dayYearPanel.add(dayTextField);
		dayYearPanel.add(yearTextField);
		
		add(monthPanel,BorderLayout.WEST);
		add(dayYearPanel,BorderLayout.EAST);
		
	}
	
	 
	public Calendar getDate() throws NumberFormatException
	{
		if (yearTextField.getText().length() != 4)
		{
			throw new NumberFormatException
					("You must enter a 4-digit year number!");
		}
		
		//parseInt may throw a NumberFormatException (i.e. run time exception)
		int year = Integer.parseInt(yearTextField.getText());
		int day = Integer.parseInt(dayTextField.getText());
				
		int month = monthNamesComboBox.getSelectedIndex();
		Calendar requestedDate = Calendar.getInstance();
		requestedDate.set(year,month,1,0,0,0);
		requestedDate.set(Calendar.MILLISECOND,0);
		//validate date
		int max = requestedDate.getActualMaximum(Calendar.DAY_OF_MONTH); // get day of this specific month and year
		if (day < 1 || day > max)
		{
			throw new NumberFormatException
				("Day must be between 1 and " + max +"!");
		}
		
		requestedDate.set(Calendar.DATE,day);
		return requestedDate;
	}
	
	public void setDate(Calendar date)
	{
		monthNamesComboBox.setSelectedIndex(date.get(Calendar.MONTH));
		dayTextField.setText("" + date.get(Calendar.DAY_OF_MONTH));
		yearTextField.setText("" + date.get(Calendar.YEAR));
		
	}

}
