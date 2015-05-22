package team.monroe.org.pocketfit.view.presenter;

import android.os.Looper;
import android.widget.TextView;

import org.monroe.team.corebox.utils.DateUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ClockViewPresenter extends ViewPresenter<TextView>{

    private long mStartTime;
    private Timer mTimer;
    private final android.os.Handler uiHandler = new android.os.Handler(Looper.getMainLooper());

    public ClockViewPresenter(TextView rootView) {
        super(rootView);
    }

    public void startClock(Date fromTime){
        resetClock();
        mStartTime = fromTime.getTime();
        updateView();
        mTimer = new Timer("clock", true);
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateView();
                    }
                });
            }
        }, 0, 300);
    }

    private void updateView() {
        long delta = System.currentTimeMillis() - mStartTime;
        //days,hours,minutes,seconds, periodMs
        long[] values = DateUtils.splitperiod(delta);
        String timeString = twoDigits(values[1])+":"+twoDigits(values[2])+":"+twoDigits(values[3]);
        getRootView().setText(timeString);
    }

    private String twoDigits(long value) {
        if (value < 10){
            return "0"+value;
        }
        return ""+value;
    }


    public void resetClock() {
        if (mTimer!= null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
        getRootView().setText("00:00:00");
    }
}
