/*
 * Copyright (C) XBUP Project
 *
 * This application or library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This application or library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along this application.  If not, see <http://www.gnu.org/licenses/>.
 */
package frinthesia.gui.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

/**
 * Some simple static methods usable for windows and dialogs.
 *
 * @version 0.1.25 2015/04/11
 * @author XBUP Project (http://xbup.org)
 */
public class WindowUtils {

    private static final int BUTTON_CLICK_TIME = 150;

    private WindowUtils() {
    }

    public static void invokeWindow(final Window window) {
        // Set the Nimbus look and feel
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(WindowUtils.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>
        //</editor-fold>

        /**
         * Create and display the dialog.
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (window instanceof JDialog) {
                    ((JDialog) window).setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                }

                window.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                window.setVisible(true);
            }
        });
    }

    public static void closeWindow(Window window) {
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Find frame component for given component.
     *
     * @param component instantiated component
     * @return frame instance if found
     */
    public static Frame getFrame(Component component) {
        Component parentComponent = SwingUtilities.getWindowAncestor(component);
        while (!(parentComponent == null || parentComponent instanceof Frame)) {
            parentComponent = parentComponent.getParent();
        }
        return (Frame) parentComponent;
    }

    /**
     * Assign ESCAPE/ENTER key for all focusable components recursively.
     *
     * @param component target component
     * @param closeButton button which will be used for closing operation
     */
    public static void assignGlobalKeyListener(Container component, final JButton closeButton) {
        assignGlobalKeyListener(component, closeButton, closeButton);
    }

    /**
     * Assign ESCAPE/ENTER key for all focusable components recursively.
     *
     * @param component target component
     * @param okButton button which will be used for default ENTER
     * @param cancelButton button which will be used for closing operation
     */
    public static void assignGlobalKeyListener(Container component, final JButton okButton, final JButton cancelButton) {
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    boolean performOkAction = true;

                    if (evt.getSource() instanceof JButton) {
                        ((JButton) evt.getSource()).doClick(BUTTON_CLICK_TIME);
                        performOkAction = false;
                    } else if (evt.getSource() instanceof JTextArea) {
                        performOkAction = !((JTextArea) evt.getSource()).isEditable();
                    } else if (evt.getSource() instanceof JTextPane) {
                        performOkAction = !((JTextPane) evt.getSource()).isEditable();
                    } else if (evt.getSource() instanceof JEditorPane) {
                        performOkAction = !((JEditorPane) evt.getSource()).isEditable();
                    }

                    if (okButton != null && performOkAction) {
                        okButton.doClick(BUTTON_CLICK_TIME);
                    }

                } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancelButton.doClick(BUTTON_CLICK_TIME);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        assignGlobalKeyListener(component, keyListener);
    }

    /**
     * Assign key listener for all focusable components recursively.
     *
     * @param component target component
     * @param keyListener key lisneter
     */
    public static void assignGlobalKeyListener(Container component, KeyListener keyListener) {
        Component[] comps = component.getComponents();
        for (Component item : comps) {
            if (item.isFocusable()) {
                item.addKeyListener(keyListener);
            }

            if (item instanceof Container) {
                assignGlobalKeyListener((Container) item, keyListener);
            }
        }
    }

    public static void doButtonClick(JButton button) {
        button.doClick(BUTTON_CLICK_TIME);
    }
}
