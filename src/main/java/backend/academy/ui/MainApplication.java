package backend.academy.ui;

import backend.academy.SettingsLoader;
import backend.academy.generators.ImageGenerator;
import backend.academy.ui.components.AffineTransformationSettings;
import backend.academy.ui.components.FunctionalAlgorithmsSettingsPanel;
import backend.academy.ui.components.SettingsPanel;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainApplication {
    private final JFrame mainFrame = new JFrame("Fractal Lab");
    private final SettingsLoader settingsLoader = new SettingsLoader();
    private final ImageGenerator imageGenerator = new ImageGenerator(settingsLoader);
    private boolean updateEventStarted = false;
    private final Runnable drawProcess = imageGenerator::startCalculation;
    private Thread drawerThread;

    // Move image definition to class level
    private BufferedImage image;
    private final JPanel checklistPanel = new JPanel();
    private JPanel imagePanel;
    private final JPanel buttonPanel = new JPanel();
    private SettingsPanel settingsPanel;
    private FunctionalAlgorithmsSettingsPanel functionalAlgorithmsSettingsPanel;
    private int avgHitsPerSecond = 0;
    JLabel hitsPerSecondLabel = new JLabel("AHPS: 0");
    private JProgressBar progressBar = new JProgressBar();
    private Thread UIThread;

    private void startDrawing() {
        if (drawerThread == null || !drawerThread.isAlive()) {
            drawerThread = new Thread(drawProcess);
            drawerThread.start();
        }
    }

    private void stopDrawing() {
        if (drawerThread != null && drawerThread.isAlive()) {
            drawerThread.interrupt();
            try {
                drawerThread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

    public void runApp() {
        this.UIThread = new Thread(this::setupUI);
        this.UIThread.start();
    }

    private void setIconImage() {
        try {
            mainFrame.setIconImage(javax.imageio.ImageIO.read(
                Objects.requireNonNull(getClass().getResource("/icon.png"))));
        } catch (java.io.IOException e) {
            System.out.println("Error loading icon: " + e.getMessage());
        }
    }

    private void setupUI() {
        LafManager.setTheme(new DarculaTheme());
        LafManager.install();
        setIconImage();

        SwingUtilities.invokeLater(() -> {
            this.setupLayout();
            this.setupImagePanel();
            this.setupButtons();
            this.setUpSettingsPanel();
            this.setUpAlgorithmsSettingsPanel();
            this.addHitsPerSecondLabel();
            this.setUpAffineTransformationSettingsPanel();

            mainFrame.setVisible(true);
        });
    }

    private void setUpAlgorithmsSettingsPanel() {
        functionalAlgorithmsSettingsPanel = new FunctionalAlgorithmsSettingsPanel(settingsLoader);
        mainFrame.add(functionalAlgorithmsSettingsPanel, BorderLayout.WEST);
    }

    private void setUpAffineTransformationSettingsPanel() {
        // Panel for the affine transformation settings
        AffineTransformationSettings affineTransformationSettings = new AffineTransformationSettings(settingsLoader);
        mainFrame.add(affineTransformationSettings, BorderLayout.SOUTH);
    }

    private void setUpSettingsPanel() {
        JButton runtimeSettingsButton = new JButton("Apply Runtime Settings");
        runtimeSettingsButton.addActionListener(e -> {
            updateRuntimeSettings();
        });

        // Set dark background and foreground for the button
        runtimeSettingsButton.setBackground(UIManager.getColor("Button.background"));
        runtimeSettingsButton.setForeground(UIManager.getColor("Button.foreground"));

        JButton regenerateImageButton = new JButton("Generate Image");
        regenerateImageButton.addActionListener(e -> {
            updateRegenerateImageSettings();
        });

        // Set dark background and foreground for the button
        regenerateImageButton.setBackground(UIManager.getColor("Button.background"));
        regenerateImageButton.setForeground(UIManager.getColor("Button.foreground"));

        settingsPanel = new SettingsPanel(settingsLoader, runtimeSettingsButton, regenerateImageButton);
        mainFrame.add(settingsPanel, BorderLayout.EAST);
    }

    private void updateRegenerateImageSettings() {
        settingsPanel.updateSettingsLoader(this.settingsLoader);
        functionalAlgorithmsSettingsPanel.updateSettingsLoader(this.settingsLoader);
        this.startCalculation();
    }

    private void updateRuntimeSettings() {
        settingsPanel.updateSettingsLoader(this.settingsLoader);
        this.imageGenerator.getFractalImage().renderWithGamma(image, settingsLoader.getGamma());
        imagePanel.repaint();
    }

    private void setupButtons() {
        this.buttonPanel.setLayout(new FlowLayout());
        // Set dark background for the checklist panel
        this.buttonPanel.setBackground(UIManager.getColor("Panel.background"));
        this.buttonPanel.add(progressBar, BorderLayout.WEST);
        this.progressBar.setForeground(new Color(34, 94, 0));

        this.addSaveImageButton();
        this.mainFrame.add(buttonPanel, BorderLayout.NORTH);
    }

    private void startCalculation() {
        this.stopDrawing();
        settingsLoader.setFunctionalTransformations(settingsLoader.getFunctionalTransformations());
        this.initImage();
        this.progressBar.setMaximum(settingsLoader.getDrawersAmount());
        this.startDrawing();
        if (!updateEventStarted) {
            updateEventStarted = true;
            setupUpdateAction();
        }
    }

    private void setupLayout() {
        // Create the main application window
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(this.settingsLoader.getAppWidth(), this.settingsLoader.getAppHeight());

        // Create the layout for the main window
        mainFrame.setLayout(new BorderLayout());
    }

    private void setupImagePanel() {
        // Initialize the image panel (now as a class variable)
        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this); // Dynamically resize image
                }
            }
        };

        // Set dark background for the image panel
        imagePanel.setBackground(UIManager.getColor("Panel.background"));
        imagePanel.setBorder(BorderFactory.createTitledBorder("Image Viewer"));

        initImage();
        mainFrame.add(imagePanel, BorderLayout.CENTER);
    }

    private void initImage() {
        // Initialize the image with a default size (based on the panel size)
        image = new BufferedImage(
            this.settingsLoader.getImageWidth(),
            this.settingsLoader.getImageHeight(),
            BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.dispose();
    }

    private void setupUpdateAction() {
        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            this.imageGenerator.getFractalImage().renderWithGamma(image, settingsLoader.getGamma());
            imagePanel.repaint();
            avgHitsPerSecond = (this.imageGenerator.getFractalImage().getHitsFromLastCheck() + avgHitsPerSecond) / 2;
            hitsPerSecondLabel.setText("AHPS: " + humanize(avgHitsPerSecond));
            this.progressBar.setValue(this.imageGenerator.getDrawersFinished());
            if (settingsLoader.isImageFxApply()) {
                adjustContrast(image, settingsLoader.getImageContrast());
            }
            this.progressBar.repaint();
        });
        timer.start();
    }

    private void addHitsPerSecondLabel() {
        // Add a label to show the average hits per second
        hitsPerSecondLabel.setForeground(UIManager.getColor("Label.foreground"));
        hitsPerSecondLabel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Add the label at the bottom
        functionalAlgorithmsSettingsPanel.add(hitsPerSecondLabel);
    }

    private void addSaveImageButton() {
        // Add a button to save the image
        JButton saveImageButton = new JButton("save image");
        JButton applyImageEffectsButton = new JButton("Apply effects");
        JButton stopRenderButton = new JButton("stop render");

        applyImageEffectsButton.addActionListener(e -> {
            imagePanel.repaint();
        });

        stopRenderButton.addActionListener(e -> {
            stopDrawing();
        });
        saveImageButton.addActionListener(e -> {
            saveImage();
        });

        // Set dark background and foreground for the button
        saveImageButton.setBackground(UIManager.getColor("Button.background"));
        saveImageButton.setForeground(UIManager.getColor("Button.foreground"));

        // Add the button at the bottom
        buttonPanel.add(stopRenderButton);
        buttonPanel.add(saveImageButton);
        buttonPanel.add(applyImageEffectsButton);
    }

    private void saveImage() {
        try {
            java.io.File imagesDir = new java.io.File("images");
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
            }
            java.io.File file = new java.io.File(imagesDir, "fractal_image.png");
            int counter = 1;
            while (file.exists()) {
                file = new java.io.File(imagesDir, "fractal_image_" + counter + ".png");
                counter++;
            }
            if (settingsLoader.getImageWidth() != settingsLoader.getSaveWidth()
                || settingsLoader.getImageHeight() != settingsLoader.getSaveHeight()) {
                BufferedImage resizedImage = resizeImage(
                    image,
                    settingsLoader.getSaveWidth(),
                    settingsLoader.getSaveHeight()
                );
                javax.imageio.ImageIO.write(resizedImage, "png", file);
            } else {
                javax.imageio.ImageIO.write(image, "png", file);
            }
            JOptionPane.showMessageDialog(mainFrame, "Image saved successfully!", "Success",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (java.io.IOException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Error saving image: " + ex.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return resizedImage;
    }

    public static void adjustContrast(BufferedImage originalImage, float contrastFactor) {
        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int rgb = originalImage.getRGB(x, y);

                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Apply contrast adjustment
                red = clamp((int) ((red - 128) * contrastFactor + 128));
                green = clamp((int) ((green - 128) * contrastFactor + 128));
                blue = clamp((int) ((blue - 128) * contrastFactor + 128));

                int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
                originalImage.setRGB(x, y, newRgb);
            }
        }
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    private String humanize(int number) {
        if (number < 1000) {
            return String.valueOf(number);
        }
        if (number < 1000000) {
            return String.format("%.3fK", number / 1000.0);
        }
        return String.format("%.3fM", number / 1000000.0);
    }
}
