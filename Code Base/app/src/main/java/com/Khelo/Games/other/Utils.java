import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Quiz {

    private List<Question> questions;
    private int currentQuestionIndex;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
    }

    public Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    public void nextQuestion() {
        currentQuestionIndex++;
    }

    public boolean isQuizOver() {
        return currentQuestionIndex >= questions.size();
    }

    public int getScore() {
        int score = 0;
        for (Question question : questions) {
            if (question.getSelectedAnswerIndex() == question.getCorrectAnswerIndex()) {
                score++;
            }
        }
        return score;
    }

    public static Quiz generateMathQuiz(int size) {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int a = new Random().nextInt(100) + 1;
            int b = new Random().nextInt(100) + 1;
            Question question = new Question(String.format("%d + %d = ?", a, b), new int[]{a + b, a - b, a * b, a / b}, 0);
            questions.add(question);
        }
        return new Quiz(questions);
    }

    public static Quiz generateLiteratureQuiz(int size) {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Question question = new Question("Which American writer published ‘A brave and startling truth’ in 1996?", new int[]{0, 1, 2, 3}, 0);
            questions.add(question);
        }
        return new Quiz(questions);
    }

    public static Quiz generateGeographyQuiz(int size) {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Question question = new Question("The scarcity or crop failure of which of the following can cause a serious edible oil crisis in India?", new int[]{0, 1, 2, 3}, 2);
            questions.add(question);
        }
        return new Quiz(questions);
    }

    public static void main(String[] args) {
        System.out.println("Choose a quiz type: math, literature, geography");
        String quizType = System.console().readLine();

        Quiz quiz;
        if (quizType.equals("math")) {
            quiz = generateMathQuiz(10);
        } else if (quizType.equals("literature")) {
            quiz = generateLiteratureQuiz(10);
        } else if (quizType.equals("geography")) {
            quiz = generateGeographyQuiz(10);
        } else {
            System.out.println("Invalid quiz type");
            return;
        }

        while (!quiz.isQuizOver()) {
            Question question = quiz.getCurrentQuestion();

            System.out.println(question.getText());
            for (int i = 0; i < question.getOptions().length; i++) {
                System.out.println(String.format("%d. %s", i + 1, question.getOptions()[i]));
            }

            int answerIndex = Integer.parseInt(System.console().readLine());

            question.setSelectedAnswerIndex(answerIndex - 1);

            quiz.nextQuestion();
        }

        int score = quiz.getScore();

        System.out.println(String.format("Your score is %d/%d", score, quiz.questions.size()));
    }
}