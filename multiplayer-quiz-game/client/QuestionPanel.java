package client;

import shared.Question;

import javax.swing.*;
import java.awt.*;

public class QuestionPanel extends JPanel {
    private QuizClient client;
    private ButtonGroup optionGroup;
    private JRadioButton[] options;
    private JButton submitBtn;
    private JLabel questionLabel;

    public QuestionPanel(QuizClient client) {
        this.client = client;
        setLayout(new BorderLayout());

        // Question label at the top
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel, BorderLayout.NORTH);

        // Options in the center
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        optionGroup = new ButtonGroup();
        options = new JRadioButton[4];

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setFont(new Font("Arial", Font.PLAIN, 14));
            optionGroup.add(options[i]);
            centerPanel.add(options[i]);
        }

        // Submit button at the bottom
        submitBtn = new JButton("Submit Answer");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        submitBtn.addActionListener(e -> {
            int selected = getSelectedOptionIndex();
            if (selected != -1) {
                client.sendAnswer(selected);
                submitBtn.setEnabled(false);
            }
        });

        add(centerPanel, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.SOUTH);
    }

    public void displayQuestion(Question q) {
        optionGroup.clearSelection();
        submitBtn.setEnabled(true);

        questionLabel.setText(q.questionText);

        for (int i = 0; i < 4; i++) {
            options[i].setText(q.options[i]);
        }

        revalidate();
        repaint();
    }

    private int getSelectedOptionIndex() {
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }
}
