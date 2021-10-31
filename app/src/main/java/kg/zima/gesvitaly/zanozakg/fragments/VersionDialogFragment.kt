package kg.zima.gesvitaly.zanozakg.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.google.android.material.dialog.MaterialDialogs
import kg.zima.gesvitaly.zanozakg.R
import kg.zima.gesvitaly.zanozakg.utils.VersionUtils.openUpdateLink

class VersionDialogFragment : DialogFragment() {
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    var description = arguments?.getString("description")
    if (description == null || description.isEmpty()) description = "В Google Play доступна новая версия приложения. Перейти к загрузке?"
    return MaterialStyledDialog.Builder(requireContext())
        .setHeaderDrawable(R.drawable.image_zanoza_long)
        .setTitle("Новая версия!")
        .setDescription(description)
        .setPositiveText("Обновить")
        .onPositive { openUpdateLink(requireContext()) }
        .setNegativeText("Не сейчас")
        .setScrollable(true,15)
        .show()
  }

  companion object {
    @JvmStatic
    fun newInstance(description: String?): VersionDialogFragment {
      val frag = VersionDialogFragment()
      val args = Bundle()
      args.putString("description",
                     description)
      frag.arguments = args
      return frag
    }
  }
}