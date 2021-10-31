package kg.zima.gesvitaly.zanozakg;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CarouselFlags {
    public static final int SHOW_AUTHORS = 0;
    public static final int SHOW_DESCRIPTION = 1;

    @IntDef({SHOW_AUTHORS, SHOW_DESCRIPTION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CarouselFlag {
    }

    public @CarouselFlag static int carouselFlagTranslate(int val)
    {
        switch(val)
        {
            case 0:  return SHOW_AUTHORS;
            case 1:  return SHOW_DESCRIPTION;
            // Если при преобразовании из Parcelable возвращен неизвестный int, то сказать, что это SHOW_AUTHORS
            default: return SHOW_AUTHORS;
        }
    }
}
