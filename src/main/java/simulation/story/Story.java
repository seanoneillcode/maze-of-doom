package simulation.story;


import java.util.HashMap;
import java.util.Map;

import core.Vector;
import simulation.story.actions.MoveActionType;
import simulation.story.actions.PauseActionType;
import simulation.story.actions.PositionActionType;
import simulation.story.actions.DialogActionType;

public class Story {

    Map<String, Scene> scenes;

    public Story() {
        scenes = new HashMap<>();
        Scene testScene = Scene.builder()
                .addAction(new PositionActionType(new Vector(128,20)))
                .addAction(new PauseActionType(0.4f))
                .addAction(new MoveActionType(new Vector(128, 144)))
                .addAction(new PauseActionType(0.5f))
                .addAction(new MoveActionType(new Vector(32, 144)))
                .addAction(new MoveActionType(new Vector(32, 96)))
                .addAction(new MoveActionType(new Vector(48, 96)))
                .addAction(new MoveActionType(new Vector(48, 80)))
                .addAction(new DialogActionType("The book is in this house, bring it to me before the house burns to the ground."))
                .build();
        scenes.put("test-scene", testScene);
    }

    public Scene getScene(String id) {
        return scenes.get(id);
    }
}
