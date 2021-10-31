package kg.zima.gesvitaly.zanozakg.fragments

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.muddzdev.styleabletoast.StyleableToast
import kg.zima.gesvitaly.zanozakg.R
import kg.zima.gesvitaly.zanozakg.utils.Utils

class ContactsDialogFragment : DialogFragment() {
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val font = Typeface.createFromAsset(activity!!.assets,
                                        "fonts/SegoeUISemilight.ttf")
    val dialog = MaterialDialog(requireContext()).show {
      title(text = "Контакты")
      customView(R.layout.dialog_contacts,
                 scrollable = false)
      positiveButton(text = "ОК")
    }
    val view: View = dialog.getCustomView()
    val phoneTV = view.findViewById<View>(R.id.phone_tv) as TextView
    val emailTV = view.findViewById<View>(R.id.email_tv) as TextView
    val callButton = view.findViewById<View>(R.id.call_btn) as AppCompatButton
    val emailButton = view.findViewById<View>(R.id.send_email_btn) as AppCompatButton
    val copyPhoneButton = view.findViewById<View>(R.id.copy_phone_btn) as AppCompatButton
    val copyEmailButton = view.findViewById<View>(R.id.copy_email_btn) as AppCompatButton
    phoneTV.typeface = font
    emailTV.typeface = font
    emailButton.setOnClickListener {
      val intent = Utils.getEmailIntent(getString(R.string.zanoza_email),
                                        null,
                                        null)
      startActivity(intent)
    }
    callButton.setOnClickListener {
      val intent = Intent(Intent.ACTION_VIEW)
      intent.data = Uri.parse("tel:" + getString(R.string.kaktus_phone))
      startActivity(intent)
    }
    copyPhoneButton.setOnClickListener {
      val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
      val clip = ClipData.newPlainText(getString(R.string.phone_label),
                                       getString(R.string.kaktus_phone))
      clipboard.setPrimaryClip(clip)
      StyleableToast
          .makeText(requireContext(), getString(R.string.phone_copied), R.style.StyleableToast)
          .show()
    }
    copyEmailButton.setOnClickListener {
      val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
      val clip = ClipData.newPlainText(getString(R.string.email_label),
                                       getString(R.string.zanoza_email))
      clipboard.setPrimaryClip(clip)
      StyleableToast
          .makeText(requireContext(), getString(R.string.email_copied), R.style.StyleableToast)
          .show()
    }
    return dialog
  }
}