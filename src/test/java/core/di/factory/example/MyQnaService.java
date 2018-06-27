package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Service;

/**
 * Created by One0 on 2018. 6. 27..
 */
@Service
public class MyQnaService {
    private UserRepository userRepository;
    private QuestionRepository questionRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }

    @Inject
    public MyQnaService(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }
}
