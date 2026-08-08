package gridrunner.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface ITrigger {

    List<ITargetAction> actions = new ArrayList<>();

    default void trigger() {
        for (ITargetAction action : actions) {
            action.execute();
        }
    }

    default void addAction(ITargetAction action) {
        actions.add(action);
    }

    default void removeAction(ITargetAction action) {
        actions.remove(action);
    }

}