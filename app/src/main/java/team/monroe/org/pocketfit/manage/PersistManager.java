package team.monroe.org.pocketfit.manage;

import android.content.Context;

import org.monroe.team.android.box.utils.SerializationMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import team.monroe.org.pocketfit.presentations.Routine;
import team.monroe.org.pocketfit.presentations.RoutineDay;

public class PersistManager {

    private final SerializationMap<String, Persist.Routine> routineSerializationMap;
    private final SerializationMap<String, Persist.RoutineDay> routineDaySerializationMap;

    public PersistManager(Context context) {
        this.routineSerializationMap = new SerializationMap<>("persist_routine.map", context);
        this.routineDaySerializationMap = new SerializationMap<>("persist_routinedays.map", context);
    }

    public void updateOrCreate(Persist.Routine routine) {
        routineSerializationMap.put(routine.id, routine);
    }

    public Persist.Routine get(String routineId) {
        return routineSerializationMap.get(routineId);
    }

    public Set<String> listRoutineIds() {
        return new HashSet<>(routineSerializationMap.keys());
    }

    public void remove(String routineId) {
        routineSerializationMap.remove(routineId);
    }

    public Persist.RoutineDay getDay(String dayId) {
        return routineDaySerializationMap.get(dayId);
    }

    public void updateOrCreateDay(Persist.RoutineDay update) {
        routineDaySerializationMap.put(update.id, update);
    }

    public void removeDay(String dayId) {
        routineDaySerializationMap.remove(dayId);
    }
}
