package simulation.story.actions;

import simulation.dialog.Dialog;
import simulation.story.Actor;

public class DialogActionType implements SceneActionType {

    Dialog dialog;

    public DialogActionType(String text) {
        this.dialog = new Dialog(text);
    }

    @Override
    public void start() {
    }

    @Override
    public boolean isDone() {
        return dialog.isDone();
    }

    @Override
    public void update(Actor actor, float delta) {
        //dialog.update(delta);
    }

    public Dialog getDialog() {
        return dialog;
    }
}
