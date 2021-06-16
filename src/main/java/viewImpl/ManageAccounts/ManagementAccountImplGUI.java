package viewImpl.ManageAccounts;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controller.ManageAccounts.AccountsController;
import utilities.Film;
import utilities.ManageAccounts.Account;
import utilities.ManageAccounts.TypeAccount;
import utilitiesImpl.ManageAccounts.LoggedAccount;
import view.ManageAccounts.ManagementAccountGUI;

public class ManagementAccountImplGUI implements ManagementAccountGUI{
    //GRID BAG LAYOUT + FLOW LAYOUT 

    private static final String FRAME_NAME = "Management account";
    private static final double PROPORTION = 1.15;
    private final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    private final JFrame frame = new JFrame();

    //components
    private final JLabel title = new JLabel("List account"); 
    private final JButton add = new JButton("Add");
    private final JButton home = new JButton("Home");
    private final JButton delete = new JButton("Delete");
    private JTable table = new JTable();

    //real dimension of the screen
    private final int screenWidth = (int) screen.getWidth();
    private final int screenHeight = (int) screen.getHeight();
    //real dimension of my frame
    private final int frameWidth = (int) (screenWidth / PROPORTION);
    private final int frameHeight = (int) (screenHeight / PROPORTION);

    private AccountsController observer;
    public static final int SPACE = 5;

    //private Map<JButton, Account> map = new HashMap<>();

    public ManagementAccountImplGUI() {

        frame.setTitle(FRAME_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final GridBagConstraints cnst = new GridBagConstraints();
        cnst.gridy = 0;
        cnst.insets = new Insets(SPACE, SPACE, SPACE, SPACE); 
        cnst.fill = GridBagConstraints.HORIZONTAL;

        final JPanel pNorth = new JPanel(new FlowLayout());
        pNorth.add(title);
        cnst.gridy++;

        DefaultTableModel dm = new DefaultTableModel(new Object[][] {}, new Object[] {"Username", "Name", "Surname", "Type"});
        table = new JTable(dm) {
            private static final long serialVersionUID = 1L;
                public boolean isCellEditable(final int row, final int column) {
                    return false;
                }
            };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        final JScrollPane scroll = new JScrollPane(table); 
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        final JPanel pWestInternal = new JPanel(new GridBagLayout()); 
        pWestInternal.add(scroll);

        final JPanel pEst = new JPanel();
        pEst.add(home);

        final JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pSouth.add(add);
        pSouth.add(delete);

        frame.add(pNorth, BorderLayout.NORTH);
        frame.add(pWestInternal, BorderLayout.CENTER);
        frame.add(pSouth, BorderLayout.SOUTH);
        frame.add(pEst, BorderLayout.EAST);

        //method to registration view 
        add.addActionListener(event -> {
            observer.showRegistrationAccountView(); 
            frame.setVisible(false);
        });

        //method to show menu
        home.addActionListener(event -> {
            observer.showMenu();
            frame.setVisible(false);
        });

        delete.addActionListener(event -> {
            final int row = table.getSelectedRow();
            if (row != -1) {
                Set<Account> setAccount = observer.getAccounts();
                final int row1 = setAccount.size();
                
                if (row1 <= 1) {
                    final TypeAccount type = (TypeAccount) table.getModel().getValueAt(row, 3);
                    type.equals("AMMINISTRATOR");
                    Account account2 = observer.getAccounts().stream().filter(a -> a.type().equals(type)).findFirst().get();
                    JOptionPane.showMessageDialog(frame, "It isn't possible to delete the last administrator account");
                    this.update();
                    
                 } else {

                    final String username = (String) table.getModel().getValueAt(row, 0);
                    Account account = observer.getAccounts().stream()
                    .filter(a -> a.getUsername().equals(username))
                    .findFirst().get();

                    final LoggedAccount ar = LoggedAccount.getIstance();
                    String personalAcc = ar.getIstance().getAccount().getUsername();
                    String selectAccount = account.getUsername();
                    
                    if (personalAcc.equals(selectAccount)) {
                        JOptionPane.showMessageDialog(frame, "You cannot delete your own account");
                    } else {
                        observer.deleteAccount(account);
                        this.update();
                    }
                 } 
            } else {
                JOptionPane.showMessageDialog(frame, "No row selected");
            }

        });

        frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        frame.validate();
    }

    @Override
    public void show() {
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        /*if (observer.getAccounts().isEmpty()) {
            this.showDialog();
        }*/
    }

    @Override
    public void setObserver(final AccountsController observer) {
        this.observer = observer;
    }

    //update table of list account
    @Override
    public void update() {
        Set<Account> setAccount = observer.getAccounts();
        final int row = setAccount.size();
        final String[] columnNames = {"Username", "Name", "Surname", "Type" };
        Object[][] data = new Object[row][columnNames.length];
        int i = 0;
        for (final var acc : setAccount) {
            data[i][0] = acc.getUsername();
            data[i][1] = acc.getName();
            data[i][2] = acc.getSurname();
            data[i][3] = acc.type();
            i++;
        }

        final DefaultTableModel model = (DefaultTableModel) table.getModel();
        table.setModel(new  DefaultTableModel(data, columnNames));
        model.fireTableDataChanged();
        frame.validate();
    }

    
    //da togliere
    private void showDialog() {
        JOptionPane.showMessageDialog(frame, "Please add your personal information so as to avoid using default username and password." );
    }
}