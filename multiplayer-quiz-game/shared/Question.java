package shared;

import java.io.Serializable;

public class Question implements Serializable {
    public String questionText;
    public String[] options;
    public int correctIndex;

    public Question(String questionText, String[] options, int correctIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctIndex = correctIndex;
    }
}
