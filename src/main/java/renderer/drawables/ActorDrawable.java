package renderer.drawables;

import business.DefaultConstants;
import com.badlogic.gdx.graphics.g2d.Batch;
import core.Direction;
import core.Vector;
import renderer.ImageList;
import renderer.animation.EnemyWalkAnimation;
import renderer.animation.EntityAnimationHandler;
import renderer.animation.EntityWalkAnimation;
import simulation.entity.state.AttackState;
import simulation.entity.state.CanBeAnimated;
import simulation.story.Actor;

import java.util.HashMap;
import java.util.Map;

public class ActorDrawable implements Drawable {

    private EntityAnimationHandler handler;
    Actor actor;

    public ActorDrawable(Actor actor) {
        this.actor = actor;
        handler = new EntityAnimationHandler();

//        Map<Direction, ImageList> enemyImages = new HashMap<>();
//        enemyImages.put(Direction.DOWN, new ImageList("characters/fire/fire.png",
//                new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE),
//                2,
//                new Vector()));
//        enemyImages.put(Direction.UP, new ImageList("characters/fire/fire.png",
//                new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE),
//                2,
//                new Vector()));
//        enemyImages.put(Direction.LEFT, new ImageList("characters/fire/fire.png",
//                new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE),
//                2,
//                new Vector()));
//        enemyImages.put(Direction.RIGHT, new ImageList("characters/fire/fire.png",
//                new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE),
//                2,
//                new Vector()));
//        handler.addEntityAnimation("walk", new EnemyWalkAnimation(enemyImages));

        handler.addEntityAnimation("walk", new EntityWalkAnimation(new ImageList(
                "characters/walkanimation01.png",
                new Vector(DefaultConstants.TILE_SIZE, DefaultConstants.TILE_SIZE),
                8,
                new Vector(DefaultConstants.PLAYER_OFFSET))));
        handler.setActiveAnimation("walk");

    }

    @Override
    public void draw(float delta, Batch spriteBatch) {
        CanBeAnimated wow = new CanBeAnimated() {
            @Override
            public AttackState getAttackState() {
                return AttackState.IDLE;
            }

            @Override
            public Direction getDirection() {
                return actor.getEntity().getDirection();
            }

            @Override
            public boolean isMoving() {
                return actor.isMoving();
            }

            @Override
            public Vector getPosition() {
                return actor.getEntity().getPosition();
            }
        };
        handler.update(delta, wow);
        handler.draw(spriteBatch);
    }

    @Override
    public Vector getPosition() {
        return actor.getEntity().getPosition();
    }

    @Override
    public float getZ() {
        return getPosition().y;
    }

    @Override
    public int compareTo(Drawable o) {
        return 0;
    }
}
