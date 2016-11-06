package simulation.story;


import simulation.dialog.Dialog;
import simulation.story.actions.SceneActionType;
import simulation.story.actions.DialogActionType;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    List<SceneActionType> sceneActions;
    int currentactionIndex = 0;
    boolean isDone = false;
    SceneActionType currentAction;

    public Scene(List<SceneActionType> sceneActions) {
        this.sceneActions = sceneActions;
        currentAction = sceneActions.get(0);
    }

    public List<SceneActionType> getActions() {
        return this.sceneActions;
    }

    public boolean isDone() {
        return isDone;
    }

    public void update(Actor actor, float delta) {
        if (!isDone) {
            currentAction.update(actor, delta);
            if (currentAction.isDone()) {
                currentactionIndex++;
                if (currentactionIndex >= sceneActions.size()) {
                    isDone = true;
                } else {
                    currentAction = sceneActions.get(currentactionIndex);
                    currentAction.start();
                }
            }
        }
    }

    public Dialog getDialog() {
        Dialog dialog = null;
        if (currentAction instanceof DialogActionType) {
            return ((DialogActionType) currentAction).getDialog();
        }
        return dialog;
    }

    public void start() {
        currentactionIndex = 0;
        sceneActions.get(currentactionIndex).start();
    }

    public static SceneBuilder builder() {
        return new SceneBuilder();
    }

    public static class SceneBuilder {

        List<SceneActionType> sceneActions = new ArrayList<>();

        public SceneBuilder addAction(SceneActionType actionType) {
            sceneActions.add(actionType);
            return this;
        }

        public Scene build() {
            return new Scene(sceneActions);
        }
    }
}
