package backend.enemies;

import backend.animations.ExplosionAnimation;
import backend.entities.Entity;
import backend.projectiles.Projectile;
import backend.projectiles.ProjectileType;
import ui.SPGame;

/**
 * Generic class for enemies.
 * @author Connor Stewart
 */
public abstract class Enemy extends Entity {
	
	/** The amount of points killing this enemy rewards. */
	private final int POINTS;
	
	/** The amount of damage the enemy does. */
	private final int DAMAGE;

	/** The game screen this enemy has been spawned on. */
	final SPGame GAME;
	
	/**
	 * Create an enemy.
	 * @param x the x coordinate to spawn them on
	 * @param y the y coordinate to spawn them on
	 * @param points the amount of points this enemy rewards
	 * @param speed how many pixels this enemy moves per second
	 * @param damage the amount of damage the enemy does
	 * @param maxHealth the maximum health of this enemy
	 * @param size the size of the sprite
	 * @param imageLocation the location of this enemies image
	 */
	Enemy(float x, float y , int points, int speed, int damage, int maxHealth, int size, String imageLocation, SPGame game) {
		super(imageLocation, maxHealth, speed);
		this.GAME = game;
		this.POINTS = points;
		this.DAMAGE = damage;
		setSize(size,size);
		setPosition(x, y);
		setOriginCenter();
	}
	
	/**
	 * Handles damage from a projectile to this enemy.
	 * @param collidedWith the entity this enemy collided with
	 * @return whether the damage destroyed the enemy
	 */
	boolean takeProjectileDamage(Entity collidedWith) {
		if (collidedWith instanceof Projectile) {
			if (((Projectile) collidedWith).getType().equals(ProjectileType.PLAYER)) { //if the projectile was fired by a player
				reduceHealth(((Projectile) collidedWith).getDamage());
				
				if (health <= 0) { //remove the enemy if it has no health left
					GAME.addToScore(POINTS); //add this enemies points to the score
					return true; //destroy this enemy
				}
			}
		}
		return false;
	}
	
	/**
	 * @return the amount of damage the enemy does on collision
	 */
	public double getDamage() {
		return DAMAGE;
	}
	
	@Override
	public void onDestroy() {
		GAME.addAnimation(new ExplosionAnimation(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
	}
	
}
