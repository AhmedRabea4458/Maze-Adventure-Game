import javax.swing.*;
import java.awt.*;

public class CustomMessage {

    public static boolean showDialog(
            Component parent,
            String title,
            String message,
            String iconPath,
            String yesText,
            String noText,
            Color bgColor
    ) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setSize(320, 220);
        dialog.setLocationRelativeTo(parent);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);
        dialog.add(panel);

        if (iconPath != null) {
            JLabel iconLabel = new JLabel(new ImageIcon(iconPath), SwingConstants.CENTER);
            panel.add(iconLabel, BorderLayout.NORTH);
        }

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setOpaque(false);

        final boolean[] result = {false};

        JButton yesBtn = new JButton(yesText);
        styleButton(yesBtn, AppColors.BTN_YES);
        yesBtn.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });
        btnPanel.add(yesBtn);

        if (noText != null) {
            JButton noBtn = new JButton(noText);
            styleButton(noBtn, AppColors.BTN_NO);
            noBtn.addActionListener(e -> dialog.dispose());
            btnPanel.add(noBtn);
        }

        panel.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);

        return result[0];
    }

    private static void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
    }
}
