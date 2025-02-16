package compose.thili.demo.data.repository

import compose.thili.demo.data.quiz.QuizInfo
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.quiz_answer_1
import thilicmp.composeapp.generated.resources.quiz_answer_10
import thilicmp.composeapp.generated.resources.quiz_answer_2
import thilicmp.composeapp.generated.resources.quiz_answer_3
import thilicmp.composeapp.generated.resources.quiz_answer_4
import thilicmp.composeapp.generated.resources.quiz_answer_5
import thilicmp.composeapp.generated.resources.quiz_answer_6
import thilicmp.composeapp.generated.resources.quiz_answer_7
import thilicmp.composeapp.generated.resources.quiz_answer_8
import thilicmp.composeapp.generated.resources.quiz_answer_9
import thilicmp.composeapp.generated.resources.quiz_question_1
import thilicmp.composeapp.generated.resources.quiz_question_10
import thilicmp.composeapp.generated.resources.quiz_question_2
import thilicmp.composeapp.generated.resources.quiz_question_3
import thilicmp.composeapp.generated.resources.quiz_question_4
import thilicmp.composeapp.generated.resources.quiz_question_5
import thilicmp.composeapp.generated.resources.quiz_question_6
import thilicmp.composeapp.generated.resources.quiz_question_7
import thilicmp.composeapp.generated.resources.quiz_question_8
import thilicmp.composeapp.generated.resources.quiz_question_9

class QuizRepository {

    private val quizData: Map<Int, QuizInfo> = mapOf(Pair(0,QuizInfo(Res.string.quiz_question_1,Res.string.quiz_answer_1,1,false)),
        Pair(1,QuizInfo(Res.string.quiz_question_2,Res.string.quiz_answer_2,2,false)),
        Pair(2,QuizInfo(Res.string.quiz_question_3,Res.string.quiz_answer_3,3,false)),
        Pair(3,QuizInfo(Res.string.quiz_question_4,Res.string.quiz_answer_4,4,true)),
        Pair(4,QuizInfo(Res.string.quiz_question_5,Res.string.quiz_answer_5,5,false)),
        Pair(5,QuizInfo(Res.string.quiz_question_6,Res.string.quiz_answer_6,6,true)),
        Pair(6,QuizInfo(Res.string.quiz_question_7,Res.string.quiz_answer_7,7,false)),
        Pair(7,QuizInfo(Res.string.quiz_question_8,Res.string.quiz_answer_8,8,true)),
        Pair(8,QuizInfo(Res.string.quiz_question_9,Res.string.quiz_answer_9,9,false)),
        Pair(9,QuizInfo(Res.string.quiz_question_10,Res.string.quiz_answer_10,10,false)))

    fun getQuizData(index: Int) = quizData[index] ?: throw NoSuchElementException("No quiz data found for question $index")

}