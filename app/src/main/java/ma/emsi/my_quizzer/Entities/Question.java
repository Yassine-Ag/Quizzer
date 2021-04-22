package ma.emsi.my_quizzer.Entities;

public class Question {
    private String img, value;
    private Choice choice1, choice2;

    public Question() {
        img = "xxxxxxxxxx";
        value = "xxxxxxxxxx";
        choice1 = new Choice ("xxx", false);
        choice2 = new Choice ("xxx", true);

    }

    public Question(String img, String value, Choice choice1, Choice choice2) {
        this.img = img;
        this.value = value;
        this.choice1 = choice1;
        this.choice2 = choice2;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Choice getChoice1() {
        return choice1;
    }

    public void setChoice1(Choice choice1) {
        this.choice1 = choice1;
    }

    public Choice getChoice2() {
        return choice2;
    }

    public void setChoice2(Choice choice2) {
        this.choice2 = choice2;
    }

    @Override
    public String toString() {
        return "Question{" +
                "img=" + img + "\n" +
                ", value='" + value + "\n" +
                ", choice1=" + choice1 +
                ", choice2=" + choice2 +
                '}';
    }
}
