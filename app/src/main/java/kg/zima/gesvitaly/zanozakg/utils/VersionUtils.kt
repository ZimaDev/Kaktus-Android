package kg.zima.gesvitaly.zanozakg.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

private const val PLAY_STORE_LINK = "http://play.google.com/store/apps/details?id=%s&hl=en"

object VersionUtils {
  @JvmStatic
  fun openUpdateLink(context: Context) {
    context.startActivity(Intent(Intent.ACTION_VIEW,
                                 Uri.parse(getExternalAppLink(context))))
  }

  private fun getExternalAppLink(context: Context): String {
    return String.format(PLAY_STORE_LINK,
                         context.packageName)
  }
}