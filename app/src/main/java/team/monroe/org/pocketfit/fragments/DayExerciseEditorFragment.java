package team.monroe.org.pocketfit.fragments;

import android.os.Bundle;
import android.widget.Toast;

import team.monroe.org.pocketfit.PocketFitApp;
import team.monroe.org.pocketfit.R;
import team.monroe.org.pocketfit.presentations.Exercise;

public class DayExerciseEditorFragment extends BodyFragment {
    @Override
    protected boolean isHeaderSecondary() {
        return true;
    }

    @Override
    protected String getHeaderName() {
        return "Exercise Settings";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_day_exercise;
    }

    @Override
    public void onStart() {
        super.onStart();
        application().function_getExercise(getStringArgument("exercise_id"), observe_function(State.STOP, new PocketFitApp.DataAction<Exercise>() {
            @Override
            public void data(Exercise exercise) {
                view_text(R.id.text_title).setText(exercise.title);
                view_text(R.id.text_description).setText(exercise.description);
            }
        }));
    }
}
