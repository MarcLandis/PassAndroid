package org.ligi.passandroid;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

import java.util.Map;

public class AnalyticsTracker implements TrackerInterface {

    private final Tracker tracker;
    private final Context ctx;

    public AnalyticsTracker(Context ctx) {
        this.ctx = ctx;
        final GoogleAnalytics analytics = GoogleAnalytics.getInstance(ctx);
        tracker = analytics.newTracker(R.xml.analytics);
    }

    @Override
    public void trackException(String s, Exception e, boolean fatal) {
        final String description = new StandardExceptionParser(ctx, null)
                .getDescription(Thread.currentThread().getName(), e);

        final Map<String, String> exceptionMap = new HitBuilders.ExceptionBuilder()
                .setDescription(s + " " + description)
                .setFatal(fatal).build();

        tracker.send(exceptionMap);
    }

    @Override
    public void trackException(String s, boolean fatal) {
        tracker.send(new HitBuilders.ExceptionBuilder().setDescription(s).setFatal(fatal).build());
    }

    @Override
    public void trackEvent(String category, String action, String label, Long val) {
        final HitBuilders.EventBuilder eventMapBuilder = new HitBuilders.EventBuilder();

        if (category != null) {
            eventMapBuilder.setCategory(category);
        }

        if (action != null) {
            eventMapBuilder.setAction(action);
        }

        if (label != null) {
            eventMapBuilder.setLabel(label);
        }

        if (val != null) {
            eventMapBuilder.setValue(val);
        }

        tracker.send(eventMapBuilder.build());
    }

}
