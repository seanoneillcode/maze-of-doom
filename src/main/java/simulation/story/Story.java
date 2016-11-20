package simulation.story;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.DefaultConstants;
import core.Vector;
import simulation.dialog.Dialog;
import simulation.entity.MovingEntity;
import simulation.entity.Player;
import simulation.story.actions.MoveActionType;
import simulation.story.actions.PauseActionType;
import simulation.story.actions.PositionActionType;
import simulation.story.actions.DialogActionType;

import static simulation.story.ActorStream.actionStream;

public class Story {

    Map<String, Scene> scenes;
    Map<String, Boolean> storyState = new HashMap<>();
    private Scene activeScene;
    List<Actor> actors = new ArrayList<>();

    public Story(Player player) {
        MovingEntity oldMan = new MovingEntity(new Vector(), DefaultConstants.PLAYER_SIZE, DefaultConstants.PLAYER_SPEED);
        Actor oldManActor = new Actor(oldMan);
        Actor playerActor = new Actor(player);
        actors.add(oldManActor);
        scenes = new HashMap<>();


        Scene walkScene = Scene.scene()
                .add(actionStream(playerActor)
                        .add(new PositionActionType(playerActor, new Vector(128, 0)))
                        .add(new PauseActionType(0.2f))
                        .add(new MoveActionType(playerActor, new Vector(128, 144)))
                        .add(new PauseActionType(0.2f))
                        .add(new MoveActionType(playerActor, new Vector(32, 144)))
                        .add(new MoveActionType(playerActor, new Vector(32, 96)))
                        .add(new MoveActionType(playerActor, new Vector(48, 96)))
                        .add(new MoveActionType(playerActor, new Vector(48, 80)))
                        .add(new DialogActionType("The book is in this house, bring it to me before the house burns to the ground."))
                        .build())
                .add(actionStream(oldManActor)
                        .add(new PositionActionType(oldManActor, new Vector(128, 20)))
                        .add(new MoveActionType(oldManActor, new Vector(128, 144)))
                        .add(new MoveActionType(oldManActor, new Vector(32, 144)))
                        .add(new MoveActionType(oldManActor, new Vector(32, 80)))
                        .build())
                .build();
        scenes.put("walk-scene", walkScene);

        activeScene = null;
        storyState.put("sword-item-0", Boolean.FALSE);
    }

    public Scene getScene(String id) {
        return scenes.get(id);
    }

    public Scene getActiveScene() {
        return activeScene;
    }

    public Dialog getActiveDialog() {
        return activeScene != null && !activeScene.isDone() ? activeScene.getDialog() : null;
    }

    public void setActiveScene(Scene scene) {
        this.activeScene = scene;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void update(Player player, float delta) {
        if (activeScene != null) {
            activeScene.update(delta);
            if (activeScene.isDone()) {
                activeScene = null;
            }
        }
    }
}
