package com.antonpa.sumgame.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.antonpa.sumgame.R
import com.antonpa.sumgame.domain.entities.GameResult
import java.util.concurrent.locks.Condition

interface OnOptionClickListener {

    fun onOptionClick(option: Int)
}

@BindingAdapter("emojiImage")
fun bindEmoji(imageView: ImageView, isWinner: Boolean) {
    imageView.setImageResource(
        if (isWinner) R.drawable.ic_smile
        else R.drawable.ic_sad
    )
}

@BindingAdapter("neededCountOfCorrectAnswers")
fun bindNeededCount(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("countOfCorrectAnswers")
fun bindCount(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        count
    )
}

@BindingAdapter("neededPercentOfCorrectAnswers")
fun bindNeededPercent(textView: TextView, percent: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        percent
    )
}

@BindingAdapter("percentOfCorrectAnswers")
fun bindPercent(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPercentOfCorrectAnswers(gameResult)
    )
}

@BindingAdapter("colorByCountOfCorrectAnswers")
fun bindColorCount(textView: TextView, condition: Boolean) {
    textView.setTextColor(condition.getColorByCondition(textView.context))
}

@BindingAdapter("colorByPercentOfCorrectAnswers")
fun bindColorPercent(progressBar: ProgressBar, condition: Boolean) {
    progressBar.progressTintList =
        ColorStateList.valueOf(condition.getColorByCondition(progressBar.context))
}

@BindingAdapter("intToString")
fun bindIntToString(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, onClick: OnOptionClickListener){
    textView.setOnClickListener {
        onClick.onOptionClick(textView.text.toString().toInt())
    }
}

private fun Boolean.getColorByCondition(context: Context): Int = ContextCompat.getColor(
    context, if (this)
        android.R.color.holo_green_light
    else
        android.R.color.holo_red_light
)


private fun getPercentOfCorrectAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfAllAnswers == 0)
        0
    else
        ((countOfRightAnswers / countOfAllAnswers.toDouble()) * 100).toInt()
}