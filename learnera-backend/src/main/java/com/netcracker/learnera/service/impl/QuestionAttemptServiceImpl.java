package com.netcracker.learnera.service.impl;

import com.netcracker.learnera.entity.template.lesson.*;
import com.netcracker.learnera.repository.QuestionAttemptRepository;
import com.netcracker.learnera.service.QuestionAttemptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class QuestionAttemptServiceImpl extends CrudServiceImpl<QuestionAttempt, Long> implements QuestionAttemptService {

    private QuestionAttemptRepository questionAttemptRepository;

    @Override
    public Iterable<QuestionAttempt> findAllByQuestionId(Long id) {
        return questionAttemptRepository.findAllByQuestionId(id);
    }

    @Override
    public Iterable<QuestionAttempt> findAllCourseAttempts(Long courseId) {
        return questionAttemptRepository.findAllByCourseId(courseId);
    }

    @Override
    public Iterable<QuestionAttempt> scoreAllAttempts(Iterable<QuestionAttempt> attempts) {
        List<QuestionAttempt> attemptsMapped = new ArrayList<>();
        for (QuestionAttempt attempt : attempts) {
            attempt.setTime(new Date());

            Question question = attempt.getQuestion();
            if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
                StringBuilder answerBuilder = new StringBuilder();
                for (MultipleChoiceVariant variant : mcQuestion.getVariants()) {
                    answerBuilder.append(variant.getCorrect() ? "1" : "0");
                }

                attempt.setScore(answerBuilder.toString().equals(attempt.getAnswer()) ? question.getPoints() : 0);

            } else if (question instanceof FixedAnswerQuestion) {
                attempt.setScore(question.getAnswer().equals(attempt.getAnswer())
                        ? attempt.getQuestion().getPoints()
                        : 0);
            } else if (question instanceof RegexAnswerQuestion) {
                Pattern p = Pattern.compile(question.getAnswer());
                Matcher m = p.matcher(attempt.getAnswer());
                attempt.setScore(m.matches() ? question.getPoints() : 0);
            }

            attemptsMapped.add(questionAttemptRepository.save(attempt));
        }

        return attemptsMapped;
    }

    @Override
    public Iterable<QuestionAttempt> findFirstByUserIdModuleId(Long userId, Long moduleId) {
        return questionAttemptRepository.findFirstByUserIdModuleId(userId, moduleId);
    }

    public QuestionAttemptServiceImpl(QuestionAttemptRepository questionAttemptRepository) {
        super(questionAttemptRepository);
        this.questionAttemptRepository = questionAttemptRepository;
    }

}