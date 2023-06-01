package com.example.theqoiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat

class QuesActivity : AppCompatActivity(),View.OnClickListener {

    private var currentPosition: Int = 1
    private var quesList: ArrayList<Questions>? = null
    private var selectedOptionPos: Int = 0
    private var mUserName :String? = null

    private var mCorrectAnswers: Int = 0

    private var progressBar: ProgressBar? = null
    private var question: TextView? = null
    private var ivImage: ImageView? = null

    private var tvProgress: TextView? = null

    private var optionOne: TextView? = null
    private var optionTwo: TextView? = null
    private var optionThree: TextView? = null
    private var optionFour: TextView? = null
    private var submitButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ques)

        mUserName = intent.getStringExtra(Constants.USER_NAME)


        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.barText)
        question = findViewById(R.id.Question)
        ivImage = findViewById(R.id.imageView)
        optionOne = findViewById(R.id.Option1)
        optionTwo = findViewById(R.id.Option2)
        optionThree = findViewById(R.id.Option3)
        optionFour = findViewById(R.id.Option4)
        submitButton = findViewById(R.id.submit)

        quesList = Constants.getQuestions()

        setQuestion()
        optionOne?.setOnClickListener(this)
        optionTwo?.setOnClickListener(this)
        optionThree?.setOnClickListener(this)
        optionFour?.setOnClickListener(this)
        submitButton?.setOnClickListener(this)

    }

    private fun setQuestion() {
        defaultOptionView()
        val fullQuestion = quesList!![currentPosition - 1]
        if (currentPosition == quesList!!.size) {
            submitButton?.text = "FINISH"
        } else {
            submitButton?.text = "SUBMIT"
        }
        question?.text = fullQuestion.Question
        progressBar?.progress = currentPosition
        ivImage?.setImageResource(fullQuestion.Image)
        tvProgress?.text = "${currentPosition}/${progressBar?.max}"
        optionOne?.text = fullQuestion.OptOne
        optionTwo?.text = fullQuestion.OptTwo
        optionThree?.text = fullQuestion.OptThree
        optionFour?.text = fullQuestion.OptFour

    }

    private fun defaultOptionView() {
        val options = ArrayList<TextView>()

        optionOne?.let {
            options.add(0, it)
        }
        optionTwo?.let {
            options.add(1, it)
        }
        optionThree?.let {
            options.add(2, it)
        }
        optionFour?.let {
            options.add(3, it)
        }

        for (i in options) {
            i.setTextColor(Color.parseColor("#76D1FF"))
            i.typeface = Typeface.DEFAULT

            i.background = ContextCompat.getDrawable(
                this,

                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(textView: TextView, selectedOption: Int) {
        defaultOptionView()
        selectedOptionPos = selectedOption

        textView.setTextColor(Color.parseColor("#363A43"))
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.Option1 -> {
                optionOne?.let {
                    selectedOptionView(it, 1)
                }

            }

            R.id.Option2 -> {
                optionTwo?.let {
                    selectedOptionView(it, 2)
                }

            }

            R.id.Option3 -> {
                optionThree?.let {
                    selectedOptionView(it, 3)
                }

            }

            R.id.Option4 -> {
                optionFour?.let {
                    selectedOptionView(it, 4)
                }

            }

            R.id.submit -> {
                if (selectedOptionPos == 0) {
                    currentPosition++
                    when {

                        currentPosition <= quesList!!.size -> {

                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, quesList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else{
                    val question = quesList?.get(currentPosition - 1)

                    // This is to check if the answer is wrong
                    if (question!!.correctAns != selectedOptionPos) {
                        answerView(selectedOptionPos, R.drawable.wrong_option_border_bg)
                    }
                    else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAns, R.drawable.correct_option_border_bg)
                    if (currentPosition == quesList!!.size) {
                        submitButton?.text = "FINISH"
                    } else {
                        submitButton?.text = "GO TO NEXT QUESTION"
                    }

                    selectedOptionPos= 0



                }
            }

        }


    }
    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {

            1 -> {
                optionOne?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            2 -> {
                optionTwo?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            3 -> {
                optionThree?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            4 -> {
                optionFour?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }
}