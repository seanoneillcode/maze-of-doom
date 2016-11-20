package simulation.story;

import simulation.dialog.Dialog;
import simulation.story.actions.DialogActionType;
import simulation.story.actions.SceneActionType;

import java.util.ArrayList;
import java.util.List;

public class ActorStream {

    private List<SceneActionType> actions;
    private Actor actor;
    private int currentactionIndex = 0;
    private boolean isDone = false;
    private SceneActionType currentAction;

    public ActorStream(List<SceneActionType> actions, Actor actor) {
        this.actor = actor;
        this.actions = actions;
        currentAction = actions.get(0);
    }

    public boolean isDone() {
        return isDone;
    }

    public void update(float delta) {
        if (!isDone) {
            currentAction.update(delta);
            if (currentAction.isDone()) {
                currentactionIndex++;
                if (currentactionIndex >= actions.size()) {
                    isDone = true;
                } else {
                    currentAction = actions.get(currentactionIndex);
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
        actions.get(currentactionIndex).start();
    }

    public static ActorStreamBuilder actionStream(Actor actor) {
        return new ActorStreamBuilder(actor);
    }

    public static class ActorStreamBuilder {
        List<SceneActionType> actions = new ArrayList<>();
        private Actor actor;

        public ActorStreamBuilder(Actor actor) {
            this.actor = actor;
        }

        public ActorStreamBuilder add(SceneActionType sceneActionType) {
            actions.add(sceneActionType);
            return this;
        }

        public ActorStream build() {
            return new ActorStream(actions, actor);
        }
    }

}
