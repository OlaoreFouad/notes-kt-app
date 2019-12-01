package dev.foodie.notes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import dev.foodie.notes.R
import dev.foodie.notes.utils.Constants

class TagsAlertDialog(var _tag: String = Constants.UNCATEGORIZED) : DialogFragment() {

    private lateinit var mOnTagSelectedListener: OnTagSelectedListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = LayoutInflater.from(activity).inflate(R.layout.tags_dialog, null)
        val radio_group = view.findViewById<RadioGroup>(R.id.tags_radio_group)

        when(tag) {
            Constants.FAMIY -> radio_group.check(R.id.family_tag)
            Constants.PERSONAL -> radio_group.check(R.id.personal_tag)
            Constants.STUDY -> radio_group.check(R.id.study_tag)
            Constants.WORK -> radio_group.check(R.id.work_tag)
            else -> radio_group.check(R.id.uncategorised_tag)
        }

        val dialog = AlertDialog.Builder(activity)
            .setView(view)
            .setNegativeButton("Cancel") { p0, _ ->
                p0?.dismiss()
                mOnTagSelectedListener.tagSelected(resources.getString(R.string.uncategorised_text))
            }
            .setPositiveButton("Done") { p0, _ ->
                p0.dismiss()
                mOnTagSelectedListener.tagSelected(view.findViewById<RadioButton>(radio_group.checkedRadioButtonId).text.toString())
            }

        return dialog.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnTagSelectedListener = context as OnTagSelectedListener
    }

}

interface OnTagSelectedListener {
    fun tagSelected(tag: String)
}