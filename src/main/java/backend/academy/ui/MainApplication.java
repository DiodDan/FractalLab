package backend.academy.ui;

import backend.academy.SettingsLoader;
import backend.academy.generators.ImageGenerator;
import backend.academy.imager.ImageProcessor;
import backend.academy.imager.ImageSaver;
import backend.academy.ui.components.AffineTransformationSettings;
import backend.academy.ui.components.FunctionalAlgorithmsSettingsPanel;
import backend.academy.ui.components.SettingsPanel;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

// I have to suppress the warning because the class is not intended to have translations or changeable colors
@SuppressFBWarnings(value = {"S508C_SET_COMP_COLOR", "S508C_NON_TRANSLATABLE_STRING"})
public class MainApplication {

    // Components
    private final JFrame mainFrame = new JFrame("Fractal Lab");
    private final SettingsLoader settingsLoader = new SettingsLoader();
    private final ImageGenerator imageGenerator = new ImageGenerator(settingsLoader);
    private final ImageProcessor imageProcessor = new ImageProcessor(settingsLoader);
    private final ImageSaver imageSaver = new ImageSaver(settingsLoader);

    // UI Elements
    private BufferedImage image;
    private JPanel imagePanel;
    private final JPanel buttonPanel = new JPanel();
    private SettingsPanel settingsPanel;
    private FunctionalAlgorithmsSettingsPanel functionalAlgorithmsSettingsPanel;
    private final JLabel hitsPerSecondLabel = new JLabel("AHPS: 0");
    private final JProgressBar progressBar = new JProgressBar();

    // State Variables
    private Thread drawerThread;
    private boolean updateEventStarted = false;
    private int avgHitsPerSecond = 0;
    private int fractalImageHash = 0;
    private boolean forceUpdate = false;

    // Constants
    public static final String PANEL_BACKGROUND = "Panel.background";
    public static final Color FOREGROUND_PROGRESSBAR = new Color(34, 94, 0);
    public static final int THOUSAND = 1000;
    public static final int UNIT_INCREMENT = 10;
    public static final int FONT_SIZE = 14;
    public static final int HITS_WIDTH = 150;
    public static final int HITS_HEIGHT = 30;
    public static final int IMAGE_REDRAW_DELAY = 500;

    public void runApp() {
        Thread uiThread = new Thread(this::initializeUI);
        uiThread.setPriority(Thread.MAX_PRIORITY);
        uiThread.start();
    }

    private void initializeUI() {
        LafManager.setTheme(new DarculaTheme());
        LafManager.install();
        setApplicationIcon();

        SwingUtilities.invokeLater(() -> {
            setupMainFrame();
            setupComponents();
            setupUpdateAction();
            mainFrame.setVisible(true);
        });
    }

    private void setApplicationIcon() {
        try {
            mainFrame.setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("/icon.png"))));
        } catch (IOException e) {
            System.err.println("Error loading icon: " + e.getMessage());
        }
    }

    private void setupMainFrame() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(new Dimension(settingsLoader.getAppWidth(), settingsLoader.getAppHeight()));
        mainFrame.pack();
        mainFrame.setLayout(new BorderLayout());
    }

    private void setupComponents() {
        setupImagePanel();
        setupSettingsPanel();
        setupButtonPanel();
        setupAlgorithmsSettingsPanel();
        setupAffineTransformationSettingsPanel();
    }

    private void setupImagePanel() {
        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        imagePanel.setBackground(UIManager.getColor(PANEL_BACKGROUND));
        imagePanel.setBorder(BorderFactory.createTitledBorder("Image Viewer"));
        initializeImage();
        mainFrame.add(imagePanel, BorderLayout.CENTER);
    }

    private void initializeImage() {
        image = new BufferedImage(settingsLoader.getImageWidth(), settingsLoader.getImageHeight(),
            BufferedImage.TYPE_INT_ARGB);
        clearImage();
    }

    private void clearImage() {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.dispose();
    }

    private void setupSettingsPanel() {
        JButton applySettingsButton = createButton("Apply Runtime Settings", e -> applyRuntimeSettings());
        JButton generateImageButton = createButton("Generate Image", e -> regenerateImage());

        settingsPanel = new SettingsPanel(settingsLoader, applySettingsButton, generateImageButton);
        applySettingsButton.addActionListener(e -> forceUpdate = true);
        JScrollPane scrollPane = new JScrollPane(settingsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(UNIT_INCREMENT);
        mainFrame.add(scrollPane, BorderLayout.EAST);
    }

    private void setupButtonPanel() {
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(UIManager.getColor(PANEL_BACKGROUND));

        progressBar.setForeground(FOREGROUND_PROGRESSBAR);
        setupAHPSLabel();
        buttonPanel.add(progressBar);

        addSaveImageButton();
        addStopRenderButton();
        mainFrame.add(buttonPanel, BorderLayout.NORTH);
    }

    private void setupAHPSLabel() {
        buttonPanel.add(hitsPerSecondLabel, BorderLayout.WEST);
        hitsPerSecondLabel.setLabelFor(progressBar);
        hitsPerSecondLabel.setToolTipText("Average hits per second");
        hitsPerSecondLabel.setForeground(Color.WHITE);
        hitsPerSecondLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        hitsPerSecondLabel.setPreferredSize(new Dimension(HITS_WIDTH, HITS_HEIGHT));
    }

    private void addSaveImageButton() {
        JButton saveImageButton = createButton("Save Image", e -> saveImage());
        buttonPanel.add(saveImageButton);
    }

    private void addStopRenderButton() {
        JButton stopRenderButton = createButton("Stop Render", e -> stopDrawing());
        buttonPanel.add(stopRenderButton);
    }

    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void setupAlgorithmsSettingsPanel() {
        functionalAlgorithmsSettingsPanel = new FunctionalAlgorithmsSettingsPanel(settingsLoader);
        mainFrame.add(functionalAlgorithmsSettingsPanel, BorderLayout.WEST);
    }

    private void setupAffineTransformationSettingsPanel() {
        AffineTransformationSettings affineSettings = new AffineTransformationSettings(settingsLoader);
        mainFrame.add(affineSettings, BorderLayout.SOUTH);
    }

    private void applyRuntimeSettings() {
        settingsPanel.updateSettingsLoader(settingsLoader);
        imageGenerator.getFractalImage().renderWithGamma(image, settingsLoader.getGamma());
        imagePanel.repaint();
    }

    private void regenerateImage() {
        settingsPanel.updateSettingsLoader(settingsLoader);
        functionalAlgorithmsSettingsPanel.updateSettingsLoader(settingsLoader);
        startCalculation();
    }

    private void saveImage() {
        BufferedImage resizedImage = imageProcessor.resizeImage(image);
        ImageSaver.ImageSaveMessage message = imageSaver.saveImage(resizedImage);
        JOptionPane.showMessageDialog(mainFrame, message.message(), message.title(), message.messageType());
    }

    private void setupUpdateAction() {
        Timer timer = new Timer(IMAGE_REDRAW_DELAY, e -> {
            int newFractalImageHash = imageGenerator.getFractalImage().hashPixelsState();
            if (this.fractalImageHash != newFractalImageHash || this.forceUpdate) {
                this.forceUpdate = false;
                this.fractalImageHash = newFractalImageHash;
                imageGenerator.getFractalImage().renderWithGamma(image, settingsLoader.getGamma());
                if (settingsLoader.isImageFxApply()) {
                    imageProcessor.adjustContrast(image);
                }
                imagePanel.repaint();
            }

            avgHitsPerSecond = (imageGenerator.getFractalImage().resetHits() + avgHitsPerSecond) / 2;
            hitsPerSecondLabel.setText("AHPS: " + formatHits(avgHitsPerSecond));
            progressBar.setValue(imageGenerator.getDrawersFinished());

            progressBar.repaint();
        });
        timer.start();
    }

    private String formatHits(int number) {
        if (number < THOUSAND) {
            return String.valueOf(number);
        }
        if (number < THOUSAND * THOUSAND) {
            return String.format("%.1fK", number / (double) THOUSAND);
        }
        return String.format("%.1fM", number / (double) (THOUSAND * THOUSAND));
    }

    private void startCalculation() {
        stopDrawing();
        initializeImage();
        progressBar.setMaximum(settingsLoader.getDrawersAmount());

        drawerThread = new Thread(imageGenerator::startCalculation);
        drawerThread.start();

        if (!updateEventStarted) {
            updateEventStarted = true;
        }
    }

    private void stopDrawing() {
        if (drawerThread != null && drawerThread.isAlive()) {
            drawerThread.interrupt();
            try {
                drawerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interruption: " + e.getMessage());
            }
        }
    }
}
