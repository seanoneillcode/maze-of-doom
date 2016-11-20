package simulation.story;


import simulation.dialog.Dialog;
import simulation.story.actions.SceneActionType;
import simulation.story.actions.DialogActionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene {

    List<ActorStream> actorStreams;

    public Scene(List<ActorStream> actorStreams) {
        this.actorStreams = actorStreams;
    }

    public List<ActorStream> getActorStreams() {
        return this.actorStreams;
    }

    public boolean isDone() {
        for (ActorStream stream : actorStreams) {
            boolean isDone = stream.isDone();
            if (!isDone) {
                return false;
            }
        }
        return true;
    }

    public Dialog getDialog() {
        for (ActorStream stream : actorStreams) {
            Dialog dialog = stream.getDialog();
            if (dialog != null) {
                return dialog;
            }
        }
        return null;
    }

    public void start() {
        for (ActorStream stream : actorStreams) {
            stream.start();
        }
    }

    public void update(float delta) {
        for (ActorStream stream : actorStreams) {
            stream.update(delta);
        }
    }

    public static SceneBuilder scene() {
        return new SceneBuilder();
    }

    public static class SceneBuilder {

        List<ActorStream> sceneActions = new ArrayList<>();

        public SceneBuilder add(ActorStream actorStream) {
            sceneActions.add(actorStream);
            return this;
        }

        public Scene build() {
            return new Scene(sceneActions);
        }
    }
}
