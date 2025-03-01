package com.valkryst.JToggleablePasswordField;

import lombok.Getter;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

/**
 * <p>
 *     Represents a {@link JPasswordField} which allows the user to toggle the visibility of the password text by
 *     clicking an icon.
 * </p>
 */
public class JToggleablePasswordField extends JPasswordField {
    /** Amount of padding to add around the displayed visibility icon. */
    private static final int ICON_PADDING = 4;

    /** {@link FontIcon} to display when this field's text is visible (non-obfuscated). */
    private final FontIcon visibleIcon;

    /** {@link FontIcon} to display when this field's text is invisible (obfuscated). */
    private final FontIcon invisibleIcon;

    /** Default border of this field, as set by the {@link LookAndFeel}. */
    private final Border defaultBorder = this.getBorder() == null ? BorderFactory.createEmptyBorder() : this.getBorder();

    /** Default character used for echoing of this field, as set by the {@link LookAndFeel}. */
    private final char defaultEchoChar = this.getEchoChar();

    /** Bounds of the displayed visibility icon. */
    private final Rectangle iconBounds = new Rectangle();

    /** Whether the password is currently visible (non-obfuscated). */
    @Getter private boolean isPasswordVisible = false;

    /** Creates a new, empty {@link JToggleablePasswordField}. */
    public JToggleablePasswordField() {
        this(null);
    }

    /**
     * Creates a new {@link JToggleablePasswordField} and displays the specified password.
     *
     * @param password Password to display.
     */
    public JToggleablePasswordField(final String password) {
        super(password);

        final int defaultFontSize = this.getDefaultFont().getSize();
        final Color defaultForegroundColor = this.getDefaultForegroundColor();
        visibleIcon = FontIcon.of(Material.VISIBILITY, defaultFontSize, defaultForegroundColor);
        invisibleIcon = FontIcon.of(Material.VISIBILITY_OFF, defaultFontSize, defaultForegroundColor);

        // Handle rescaling of the field and its contents.
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                rescale();
            }

            @Override
            public void componentShown(final ComponentEvent e) {
                rescale();
            }
        });

        // Handle password visibility toggle.
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (iconBounds.contains(e.getPoint())) {
                    togglePasswordVisibility();
                }
            }
        });

        // Handle cursor icon change, based on mouse position.
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(final MouseEvent e) {
                if (iconBounds.contains(e.getPoint())) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final int iconSize = this.getIconSize();
        final int x = this.getWidth() - iconSize - ICON_PADDING;
        final int y = (this.getHeight() - iconSize) / 2;

        this.iconBounds.setBounds(x, y, iconSize, iconSize);
        this.getVisibilityIcon().ifPresent(icon -> icon.paintIcon(this, g, x, y));
    }

    /** Toggles visibility of the password text. */
    public void togglePasswordVisibility() {
        this.isPasswordVisible = !this.isPasswordVisible;

        if (this.isPasswordVisible) {
            this.setEchoChar((char) 0);
        } else {
            this.setEchoChar(this.defaultEchoChar);
        }

        this.repaint();
    }

    /** Rescales the field's contents to match the current component size. */
    private void rescale() {
        // Update the border to include space for the visibility icon.
        this.getVisibilityIcon().ifPresent(icon -> this.setBorder(new CompoundBorder(
            this.defaultBorder,
            BorderFactory.createEmptyBorder(
                0,
                0,
                0,
                icon.getIconWidth() + ICON_PADDING
            )
        )));

        // Update the font size to match the new component height.
        final int defaultFontSize = this.getDefaultFont().getSize();
        final Insets margin = this.getMargin();
        super.setFont(
            super.getFont().deriveFont(
                (float) Math.max(defaultFontSize, (int)(this.getHeight() * 0.7) - (margin.top + margin.bottom))
            )
        );

        this.repaint();
    }

    /**
     * Retrieves the default {@link Font} for this field.
     *
     * @return The default {@link Font}.
     */
    private Font getDefaultFont() {
        Font defaultFont = UIManager.getFont("PasswordField.font");;
        if (defaultFont == null) {
            defaultFont = UIManager.getFont("TextField.font");    ;
        }

        if (defaultFont == null) {
            defaultFont = this.getFont();
        }

        if (defaultFont == null) {
            defaultFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        }

        return defaultFont;
    }

    /**
     * Retrieves the default foreground {@link Color} for this field.
     *
     * @return The default foreground {@link Color}.
     */
    private Color getDefaultForegroundColor() {
        Color defaultForegroundColor = UIManager.getColor("PasswordField.foreground");
        if (defaultForegroundColor == null) {
            defaultForegroundColor = UIManager.getColor("TextField.foreground");
        }

        if (defaultForegroundColor == null) {
            defaultForegroundColor = this.getForeground();
        }

        if (defaultForegroundColor == null) {
            defaultForegroundColor = Color.BLACK;
        }

        return defaultForegroundColor;
    }

    /**
     * Retrieves the current visibility icon, based on the value of {@link #isPasswordVisible}.
     *
     * @return The current visibility icon.
     */
    private Optional<FontIcon> getVisibilityIcon() {
        final FontIcon icon = this.isPasswordVisible ? this.visibleIcon : this.invisibleIcon;
        if (icon == null) {
            return Optional.empty();
        }

        return Optional.of(FontIcon.of(icon.getIkon(), this.getIconSize(), icon.getIconColor()));
    }

    /**
     * Calculates the appropriate icon size based on component height.
     *
     * @return The calculated icon size based on current height
     */
    public int getIconSize() {
        return (int) ((this.getHeight() * 0.8));
    }

    @Override
    public Insets getMargin() {
        Insets insets = super.getMargin();
        if (insets == null) {
            insets = new Insets(0, 0, 0, 0);
        }

        return insets;
    }

    @Override
    public void setFont(final Font font) {
        super.setFont(font);
        rescale();
    }
}