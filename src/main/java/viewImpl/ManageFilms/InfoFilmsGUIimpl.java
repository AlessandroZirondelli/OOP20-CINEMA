package viewImpl.ManageFilms;
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
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import utilities.Film;
import utilities.ManagerWorkingDIR;
import utilities.Factory.FilmFactory;
import utilitiesImpl.GeneralSettings;
import utilitiesImpl.FactoryImpl.FilmBasicImpl;
import utilitiesImpl.FactoryImpl.FilmFactoryImpl;
import view.ManageFilms.ContainerFilmsGUI;
import view.ManageFilms.InfoFilmsGUI;
import view.ManageFilms.Factory.InfoFilmsGUIfactory;
import view.Settings.InfoFilmSettingsDefault;
import viewImpl.ManageFilms.Factory.InfoFilmsGUIfactoryImpl;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import org.apache.commons.io.*;

import controller.FilmsController;

public class InfoFilmsGUIimpl implements InfoFilmsGUI {
        
        private static final long serialVersionUID = 7114066347061701832L;
        
        private final InfoFilmsGUIfactory factory = new InfoFilmsGUIfactoryImpl();
        
        private static final String FRAME_NAME = "Info film";
        private static final double PROPORTION = 1.15;
        private final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        private final JFrame frame = new JFrame(FRAME_NAME);
        private final Container container = frame.getContentPane();
        private FilmsController observer;
        private Optional<Film> focusFilm;
        private Optional<String> selectedImagePath;

        private final JTextField duration = factory.createTextField("Duration (minutes)");
        private final JTextField genre = factory.createTextField("Genre");
        private final JTextArea description = factory.createTextArea("Description");
        private final JButton save = factory.createButtonWithText("Save");
        private final JButton delete = factory.createButtonWithText("Delete");
        private final JButton home = factory.createButtonWithText("Home");
        private final JButton back = factory.createButtonWithText("Back");
        private final JButton pic = factory.createButtonWithText("");
        private final JTextField title = factory.createTextField("Title");
        //real dimension of the screen
        private final int screenWidth = (int) screen.getWidth();
        private final int screenHeight = (int) screen.getHeight();
        //real dimension of my frame
        private final int frameWidth = (int) (screenWidth / PROPORTION);
        private final int frameHeight = (int) (screenHeight / PROPORTION);

        public InfoFilmsGUIimpl() {     
        
        final JPanel mainPanel = factory.createPanel(new BorderLayout());
        final JPanel centralPanel = factory.createPanel(new BorderLayout());
        final JPanel westPanel = factory.createPanel(new BorderLayout());
        final JPanel southPanel = factory.createPanel(new BorderLayout());
        final JPanel northPanel = factory.createPanel(new BorderLayout());
        focusFilm = Optional.ofNullable(null); // focusFilm empty
        selectedImagePath = Optional.ofNullable(null);
        //filmFactory = new FilmFactoryImpl(observer.getManagerIdsFilms());

        final URL imgURL = ClassLoader.getSystemResource(GeneralSettings.IMAGEFILMSTANDARD);
        final ImageIcon icon = new ImageIcon(imgURL);
        
        pic.setIcon(
                factory.getScaledIcon(icon, (int) (frameWidth / InfoFilmSettingsDefault.ImageWidthProportion), (int) (frameHeight / InfoFilmSettingsDefault.ImageHeightProportion))
        );
        
        pic.setMargin(new Insets(0, 0, 0, 0));
        pic.setPreferredSize(new Dimension((int) (frameWidth / InfoFilmSettingsDefault.ImageWidthProportion), (int) (frameHeight / InfoFilmSettingsDefault.ImageHeightProportion)));
        
        
        final JPanel firstInfoPanel =  factory.createPanel(new FlowLayout(FlowLayout.LEFT, InfoFilmSettingsDefault.HgapFlowLayout, InfoFilmSettingsDefault.VgapFlowLayout));
        final JPanel secondInfoPanel =  factory.createPanel(new FlowLayout(FlowLayout.LEFT, InfoFilmSettingsDefault.HgapFlowLayout, InfoFilmSettingsDefault.VgapFlowLayout));
        final JPanel actionPanel = factory.createPanel(new FlowLayout(FlowLayout.CENTER));
        
        mainPanel.add(centralPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(westPanel, BorderLayout.WEST); 
        northPanel.add(back, BorderLayout.WEST);
        back.setPreferredSize(new Dimension(frameHeight / InfoFilmSettingsDefault.ButtonHeightProportion, frameWidth / InfoFilmSettingsDefault.ButtonWidthProportion));
        northPanel.add(home, BorderLayout.EAST);
        home.setPreferredSize(new Dimension(frameHeight / InfoFilmSettingsDefault.ButtonHeightProportion, frameWidth / InfoFilmSettingsDefault.ButtonWidthProportion));
        southPanel.add(actionPanel, BorderLayout.SOUTH);
        actionPanel.add(save);
        actionPanel.add(delete);
        
        southPanel.add(description, BorderLayout.NORTH);
        westPanel.add(pic, BorderLayout.NORTH);
        centralPanel.add(firstInfoPanel, BorderLayout.WEST);
        centralPanel.add(secondInfoPanel, BorderLayout.CENTER);
        title.setPreferredSize(new Dimension(frameWidth / InfoFilmSettingsDefault.JTextFieldHeightProportion, frameWidth / InfoFilmSettingsDefault.JTextFieldWidthProportion));
        duration.setPreferredSize(new Dimension(frameWidth / InfoFilmSettingsDefault.JTextFieldHeightProportion, frameWidth / InfoFilmSettingsDefault.JTextFieldWidthProportion));
        genre.setPreferredSize(new Dimension(frameWidth / InfoFilmSettingsDefault.JTextFieldHeightProportion, frameWidth / InfoFilmSettingsDefault.JTextFieldWidthProportion));
        firstInfoPanel.add(title);
        firstInfoPanel.add(duration);
        secondInfoPanel.add(genre);
        description.setPreferredSize(new Dimension(frameWidth, frameHeight / InfoFilmSettingsDefault.JTEXTAREAHEIGHTPROPORTION));
        container.add(mainPanel);
        frame.pack();

        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        description.addFocusListener(new FocusListener() {
            public void focusGained(final FocusEvent e) {
                if ("Description".equals(description.getText())) { 
                    description.setText("");
                }
            }

            public void focusLost(final FocusEvent e) {
        
            }
        });

        genre.addFocusListener(new FocusListener() {
            public void focusGained(final FocusEvent e) {
                if ("Genre".equals(genre.getText())) { 
                    genre.setText("");
                }
            }

            public void focusLost(final FocusEvent e) {
            }
        });
        
        title.addFocusListener(new FocusListener() {
            public void focusGained(final FocusEvent e) {
                if ("Title".equals(title.getText())) { 
                    title.setText("");
                }
            }

            public void focusLost(final FocusEvent e) {
            }
        });
        duration.addFocusListener(new FocusListener() {
            public void focusGained(final FocusEvent e) {
               if ("Duration (minutes)".equals(duration.getText())) { 
                   duration.setText("");
               }

            }

            public void focusLost(final FocusEvent e) {
            }
        });
        
        
        pic.addActionListener(e -> {
            final JFileChooser chooser = new JFileChooser();
            final FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG  & PNG Images", "jpg", "png", "jpeg", "JPG");
            chooser.setFileFilter(filter);
            final int returnVal = chooser.showOpenDialog(frame);
            final Optional<String> pathDestFile = Optional.ofNullable(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
               final File selectedFile = chooser.getSelectedFile();
               final ManagerWorkingDIR manager = observer.getManagerWorkingDIR();
               final ImageIcon img = new ImageIcon(selectedFile.getAbsolutePath());
               pic.setIcon(factory.getScaledIcon(img, (int) (frameWidth / InfoFilmSettingsDefault.ImageWidthProportion), (int) (frameHeight / InfoFilmSettingsDefault.ImageHeightProportion)));
               selectedImagePath = Optional.ofNullable(selectedFile.getAbsolutePath());
            }
        }
        );      
        back.addActionListener(event -> {
               frame.setVisible(false); 
               focusFilm = Optional.ofNullable(null);
               observer.showContainerFilmsView();
            }
        );
        
        save.addActionListener(event ->
        {
            String pathWhereStored = new String();
            Film film;
            FilmFactory filmFactory = new FilmFactoryImpl(observer.getManagerIdsFilms());

            int durationTime = 0;

            try {
              durationTime = Integer.parseInt(duration.getText());
            } catch (java.lang.NumberFormatException exception) {
              durationTime = 0;
            }
            if (focusFilm.isEmpty()) { // If users clicks on add new film
                if(selectedImagePath.isEmpty()) {// If users has not selected any image
                    film = filmFactory.createBasicFilm(title.getText(), genre.getText(), description.getText(), Optional.ofNullable(null), durationTime);
                    System.out.println("Just created:"+film);
                    observer.addFilm(film);
                }else {
                    try {
                        pathWhereStored = observer.getManagerWorkingDIR().copyFile(new File(selectedImagePath.get()), GeneralSettings.IMAGESSELECTEDDIR);
                        film = filmFactory.createBasicFilm(title.getText(), genre.getText(), description.getText(), Optional.ofNullable(pathWhereStored), durationTime);
                        System.out.println("Just created:" + film);
                        observer.addFilm(film);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                } 
                
            } else {// If users wants edit an existing film
                int oldIdFilm = focusFilm.get().getID();
                if (selectedImagePath.isEmpty()) {
                    Optional<String> equalPath = focusFilm.get().getCoverPath();
                    film = filmFactory.createBasicFilmById(title.getText(), genre.getText(), description.getText(),equalPath,durationTime, oldIdFilm);
                    observer.deleteFilm(focusFilm.get());
                    observer.addFilm(film);
                }else {// If users wants edit an existing film and he has selected specific image
                    final Optional<String> imagePathToDelete = focusFilm.get().getCoverPath();
                    if (imagePathToDelete.isPresent()) {
                        observer.getManagerWorkingDIR().deleteFileWithSpecificName(new File(imagePathToDelete.get()));
                    }
                    
                    try {
                        pathWhereStored = observer.getManagerWorkingDIR().copyFile(new File(selectedImagePath.get()), GeneralSettings.IMAGESSELECTEDDIR);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    film = filmFactory.createBasicFilmById(title.getText(), genre.getText(), description.getText(), Optional.ofNullable(pathWhereStored), durationTime,oldIdFilm);
                    observer.deleteFilm(focusFilm.get());
                    observer.addFilm(film);
                }
                System.out.println("Just modified:" + film);
            }
            System.out.print("newPath:" + pathWhereStored);
            frame.setVisible(false);
            observer.showContainerFilmsView();
        }
        );
        
        delete.
        addActionListener(event -> {
            System.out.println("focus film in delete listener:"+focusFilm);
            final Optional<String> imagePathToDelete = focusFilm.get().getCoverPath();
            System.out.println("Ids before delete:"+ observer.getManagerIdsFilms().getUsedIDs());
            if (imagePathToDelete.isPresent()) {
                observer.getManagerWorkingDIR().deleteFileWithSpecificName(new File(imagePathToDelete.get()));
                System.out.println("Deleted image");
            }
            observer.getManagerIdsFilms().removeFilmId(focusFilm.get().getID());
            observer.deleteFilm(focusFilm.get());
            System.out.println("Ids before delete:"+ observer.getManagerIdsFilms().getUsedIDs());
            frame.setVisible(false);
            observer.showContainerFilmsView();
        }
        );
        
        
        frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        frame.validate();
        
        }
        
        
    @Override   
    public void loadFilm(final Film film) {
            this.reset();
            focusFilm = Optional.of(film);
            System.out.println("I'm loading:" + film);
            System.out.println("Focus on :" + focusFilm);
            title.setText(film.getName());
            genre.setText(film.getGenre());
            duration.setText(new Integer(film.getDuration()).toString());
            description.setText(film.getDescription());

            if (film.getCoverPath().isPresent()) {
                final ImageIcon icon = new ImageIcon(film.getCoverPath().get());
               // selectedImagePath = Optional.of(film.getCoverPath().get());
                pic.setIcon(
                        factory.getScaledIcon(icon, (int) (frameWidth / InfoFilmSettingsDefault.ImageWidthProportion), (int) (frameHeight / InfoFilmSettingsDefault.ImageHeightProportion))
                );
            } else {
                final URL imgURL = ClassLoader.getSystemResource("images/filmStandardIco.png");
                final ImageIcon icon = new ImageIcon(imgURL);
                //selectedImagePath = Optional.ofNullable(null);
                pic.setIcon(factory.getScaledIcon(icon, (int) (frameWidth / InfoFilmSettingsDefault.ImageWidthProportion), (int) (frameHeight / InfoFilmSettingsDefault.ImageHeightProportion)));
            }


            System.out.print("focusFilm:" + film);
            delete.setEnabled(true);
        }       

    @Override
    public void start() {
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }


    @Override
    public void setObserver(final FilmsController observer) {
        this.observer = observer;
    }


    @Override
    public void reset() {
        focusFilm = Optional.ofNullable(null);
        selectedImagePath = Optional.ofNullable(null);
        duration.setText("Duration (minutes)");
        genre.setText(("Genre"));
        description.setText("Description");
        title.setText("Title");
        final URL imgURL = ClassLoader.getSystemResource("images/filmStandardIco.png");
        ImageIcon icon = new ImageIcon(imgURL);
        pic.setIcon(
                factory.getScaledIcon(icon, (int) (frameWidth / InfoFilmSettingsDefault.ImageWidthProportion), (int) (frameHeight / InfoFilmSettingsDefault.ImageHeightProportion))
        );
        delete.setEnabled(false);
    }

}
