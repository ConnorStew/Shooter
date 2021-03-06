package backend.projectiles;

import backend.enemies.Enemy;
import backend.entities.Entity;
import com.badlogic.gdx.math.Polygon;
import ui.SPGame;

/**
 * Projectile for use when the player gets the {@link backend.pickups.AutoAim} powerup.
 * @author Connor Stewart
 */
public class LockOn extends Projectile {
	
	/** The amount of damage this projectile does. */
	private static final int DAMAGE = 1;
	
	/** The amount of pixels per seconds this projectile moves at. */
	private static final int SPEED = 20;
	
	/** The size of the projectile. */
	private static final int SIZE = 1;
	
	/** The maximum distance this projectile can see. */
	private static final int VIEW_DISTANCE = 15;
	
	/** The max width this projectile can see. */
	private static final int VIEW_WIDTH = 25;

	private SPGame screen;

	private Polygon vision;

	public LockOn(float x, float y, float rotation, SPGame screen) {
		super(x, y, rotation, DAMAGE, SPEED, SIZE, "projectiles/autoAim.png", ProjectileType.PLAYER);
		vision = new Polygon();
		this.screen = screen;
	}
	
	@Override
	public void update(float delta) {
		float x = getCenterX();
		float y = getCenterY();
		
		float[] vertices = {x, y, 
				x - VIEW_WIDTH, y + VIEW_DISTANCE,
				x + VIEW_WIDTH, y + VIEW_DISTANCE};
		
		vision.setVertices(vertices);
		vision.setOrigin(x, y);
		vision.setRotation(this.getRotation() - 90);
		
		Enemy toChase = screen.getNearestVisibleEnemy(this);
		if (toChase != null)
			moveTowards(toChase, delta);
		else
			moveForward(speed * delta);
	}

	@Override
	public void onDestroy() {}

	/**
	 * Whether this projectile can see another entity.
	 * @param entity the entity to check
	 * @return whether this projectile can see that entity
	 */
	public boolean canSee(Entity entity) {
		return vision.contains(entity.getCenterX(), entity.getCenterY());
	}
}
