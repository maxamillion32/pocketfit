package team.monroe.org.pocketfit.manage;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import team.monroe.org.pocketfit.presentations.MealProduct;
import team.monroe.org.pocketfit.presentations.RoutineExercise;

public class Persist {

    public static class Routine implements Serializable {

        public final String id;
        public String title;
        public String description;
        public String imageId;
        public List<String> routineDaysIdList = new ArrayList<>();

        public Routine(String id) {
            this.id = id;
        }
    }

    public static class Meal implements Serializable {

        public final String id;
        public String title;
        public String imageId;
        public List<String> mealProductIdList = new ArrayList<>();
        public Meal(String id) {
            this.id = id;
        }
    }

    public static class RoutineDay implements Serializable {

        public final String id;
        public final String routineId;
        public Integer restDays;
        public String description;
        public List<String> routineExerciseIdList = new ArrayList<>();

        public RoutineDay(String id, String routineId) {
            this.id = id;
            this.routineId = routineId;
        }
    }

    public static class MealProduct implements Serializable {

        public final String id;
        public final String mealId;
        public final String productId;
        public Float gram;

        public MealProduct(String id, String mealId, String productId) {
            this.id = id;
            this.mealId = mealId;
            this.productId = productId;
        }
    }

    public static class RoutineExercise implements Serializable {

        public final String id;
        public final String dayId;
        public final String exerciseId;

        public team.monroe.org.pocketfit.presentations.RoutineExercise.ExerciseDetails details;

        public RoutineExercise(String id, String dayId, String exerciseId) {
            this.id = id;
            this.dayId = dayId;
            this.exerciseId = exerciseId;
        }
    }

}
