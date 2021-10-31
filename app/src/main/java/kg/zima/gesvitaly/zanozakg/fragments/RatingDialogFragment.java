package kg.zima.gesvitaly.zanozakg.fragments;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.muddzdev.styleabletoast.StyleableToast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import kg.zima.gesvitaly.zanozakg.MainApp;
import kg.zima.gesvitaly.zanozakg.R;
import kg.zima.gesvitaly.zanozakg.models.Rate;
import kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi.ResponseForSendRate;
import kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi.ZanozaApiClient;
import kg.zima.gesvitaly.zanozakg.restApi.zanozaRestApi.ZanozaApiService;
import kg.zima.gesvitaly.zanozakg.utils.Resolution;
import kg.zima.gesvitaly.zanozakg.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final RatingDialog ratingDialog = new RatingDialog.Builder(getContext())
                .icon(ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_rate, null))
                .session(10)
                .threshold(4)
                .title("Как бы вы оценили наше приложение?")
                .titleTextColor(R.color.black)
                .positiveButtonText("Не сейчас")
                .negativeButtonText("Никогда")
                .formTitle("Отправить отзыв")
                .formHint("Пожалуйста, опишите, что вам не понравилось, и мы поработаем над этим!")
                .formSubmitText("Отправить")
                .formCancelText("Отмена")
                .addOnThresholdCleared((ratingDialog1, rating, thresholdCleared) -> sendRate(rating, ""))
                .onRatingBarFormSumbit((feedback, rateValue) -> {
                    sendRate(rateValue, feedback);
                    StyleableToast
                        .makeText(requireContext(), getString(R.string.thanks_for_the_feedback), R.style.StyleableToast)
                        .show();
                })
                .build();

//        ratingDialog.showNever(false); // for debug
        ratingDialog.show();
        return ratingDialog;
    }

    private void sendRate(double rateValue, String feedback) {
        ZanozaApiService zanozaApiService = ZanozaApiClient.getClient().create(ZanozaApiService.class);
        Resolution screenResolution = Utils.getScreenResolution(getActivity());
        Rate rate = new Rate.Builder()
                .userUUID(((MainApp) getActivity().getApplication()).getUUID())
                .rateValue(rateValue)
                .text(feedback)
                .sdkVersion(Build.VERSION.SDK_INT)
                .androidVersion(android.os.Build.VERSION.RELEASE)
                .screenWidth(screenResolution.getWidth())
                .screenHeight(screenResolution.getHeight())
                .phoneManufacturer(android.os.Build.MANUFACTURER)
                .phoneModel(android.os.Build.MODEL)
                .isRooted(Utils.isRooted() ? 1 : 0)
                .hardware(Build.HARDWARE)
                .processor(Build.BOARD)
                .packageName(getContext().getPackageName())
                .appVersion(Utils.getAppVersion(getContext()))
                .build();
        Call<ResponseForSendRate> call = zanozaApiService.sendRate(
                rate.getUserUUID(),
                rate.getRateValue(),
                rate.getText(),
                rate.getAppVersion(),
                rate.getSdkVersion(),
                rate.getAndroidVersion(),
                rate.getScreenWidth(),
                rate.getScreenHeight(),
                rate.getPhoneManufacturer(),
                rate.getPhoneModel(),
                rate.getIsRooted(),
                rate.getHardware(),
                rate.getProcessor(),
                rate.getPackageName()
        );
        call.enqueue(new Callback<ResponseForSendRate>() {
            @Override
            public void onResponse(Call<ResponseForSendRate> call, Response<ResponseForSendRate> response) {

            }

            @Override
            public void onFailure(Call<ResponseForSendRate> call, Throwable t) {

            }
        });
    }
}
