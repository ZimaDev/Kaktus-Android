package kg.zima.gesvitaly.zanozakg.utils.spanutils;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class SuperscriptSpanAdjuster extends MetricAffectingSpan {
    private double ratio = 0.5;

    public SuperscriptSpanAdjuster(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public void updateDrawState(TextPaint paint) {
        paint.baselineShift -= (int) (paint.ascent() * ratio);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        paint.baselineShift -= (int) (paint.ascent() * ratio);
    }
}
