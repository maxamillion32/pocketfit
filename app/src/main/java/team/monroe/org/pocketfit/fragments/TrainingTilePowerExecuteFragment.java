package team.monroe.org.pocketfit.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;

import org.monroe.team.android.box.app.ui.animation.AnimatorListenerSupport;
import org.monroe.team.android.box.app.ui.animation.apperrance.AppearanceController;

import static org.monroe.team.android.box.app.ui.animation.apperrance.AppearanceControllerBuilder.*;

import team.monroe.org.pocketfit.R;
import team.monroe.org.pocketfit.presentations.RoutineExercise;
import team.monroe.org.pocketfit.view.presenter.ClockViewPresenter;

public class TrainingTilePowerExecuteFragment extends TrainingTileFragment {

    private RoutineExercise mRoutineExercise;
    private ClockViewPresenter mSetClockPresenter;
    private AppearanceController mStopPanelAnimation;

    @Override
    protected int getTileLayoutId() {
        return R.layout.tile_training_power_execute;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        mStopPanelAnimation = animateAppearance(view(R.id.panel_stop),ySlide(0,70))
                .showAnimation(duration_constant(200),interpreter_accelerate_decelerate())
                .hideAndGone()
                .build();
        mRoutineExercise= application().getTrainingPlan().getCurrentExercise();
        view_text(R.id.exercise_name).setText(mRoutineExercise.exercise.title);
        RoutineExercise.PowerExerciseDetails details = (RoutineExercise.PowerExerciseDetails) mRoutineExercise.exerciseDetails;
        view_text(R.id.exercise_description).setText(details.times + " x " + details.weight + " times/kg");
        view_text(R.id.exercise_set).setText("Set " + (application().getTrainingPlan().getSetIndex() + 1));

        view(R.id.action_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                application().getTrainingPlan().stopSet();
                owner().updateTile();
            }
        });

        if (isSetStarted()){
            view(R.id.exercise_start_time).setVisibility(View.GONE);
            mSetClockPresenter = new ClockViewPresenter(view_text(R.id.exercise_timer_anchor));
            mSetClockPresenter.show();
            startTimer();
            mStopPanelAnimation.showWithoutAnimation();
        }else{
            mStopPanelAnimation.hideWithoutAnimation();
            view(R.id.exercise_start_time).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    application().getTrainingPlan().startSet();
                    view(R.id.exercise_start_time).setOnClickListener(null);
                    int[] fromLocation = getViewLocationOnScreen(v);
                    int[] toLocation = getViewLocationOnScreen(view(R.id.exercise_timer_anchor));
                    int xDelta = fromLocation[0] - toLocation[0];
                    int yDelta = fromLocation[1] - toLocation[1];
                    AppearanceController ac = combine(
                            animateAppearance(v, scale(1f,0.6f))
                            .showAnimation(duration_constant(600),interpreter_overshot()),
                            animateAppearance(v, xSlide(-xDelta,0))
                            .showAnimation(duration_constant(200),interpreter_decelerate(1f)),
                            animateAppearance(v, ySlide(-yDelta,0))
                            .showAnimation(duration_constant(200), interpreter_accelerate(0.5f))
                    );
                    ac.hideWithoutAnimation();
                    ac.showAndCustomize(new AppearanceController.AnimatorCustomization() {
                        @Override
                        public void customize(Animator animator) {
                            animator.addListener(new AnimatorListenerSupport(){
                                @Override
                                public void onAnimationEnd(final Animator animation) {
                                    super.onAnimationEnd(animation);
                                    mSetClockPresenter = new ClockViewPresenter((android.widget.TextView) v);
                                    startTimer();
                                    mStopPanelAnimation.showAndCustomize(new AppearanceController.AnimatorCustomization() {
                                        @Override
                                        public void customize(Animator anim) {
                                            anim.setStartDelay(500);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    private int[] getViewLocationOnScreen(View v) {
        int[] locationOnScreen = new int[2];
        v.getLocationOnScreen(locationOnScreen);
        return locationOnScreen;
    }

    private void startTimer() {
       mSetClockPresenter.startClock(application().getTrainingPlan().getSetStartDate());
    }

    private boolean isSetStarted() {
        return application().getTrainingPlan().isSetStarted();
    }

}
