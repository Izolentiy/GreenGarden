package org.android.learning.sunflower.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.android.learning.sunflower.R

class DeletePlantDialog : DialogFragment() {

    interface Listener {
        fun onPositiveDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val style = R.style.Theme_SunflowerClone_DialogStyle
        return MaterialAlertDialogBuilder(requireContext(), style)
            .setView(R.layout.content_delete_plant_dialog)
            .setPositiveButton(getString(R.string.delete_plant_dialog_pos_action)) { _, _ ->
                (requireParentFragment() as Listener).onPositiveDialog()
            }
            .setTitle(getString(R.string.delete_plant_dialog_title))
            .create()
    }

    companion object {
        val TAG = "${DeletePlantDialog::class.java.simpleName}_TAG"
    }

}