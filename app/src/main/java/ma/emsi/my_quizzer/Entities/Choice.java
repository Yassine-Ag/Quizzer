package ma.emsi.my_quizzer.Entities;

public class Choice {
    private String Answer;
    private boolean isCorrect;

    public Choice() {

        Answer = "xxxx";
        isCorrect = false;

    }

    public Choice(String answer, boolean isCorrect) {
        Answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "Answer='" + Answer + "\n" +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
