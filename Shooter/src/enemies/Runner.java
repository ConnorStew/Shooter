package enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import entities.Entity;
import entities.Player;
import ui.GameScreen;

/**
 * An enemy that flies towards the player and deals damage on contact.
 * @author Connor Stewart
 */
class Runner extends Enemy {
	
	/** The sound plays when this enemy dies. */
	private static final Sound DEATH_SOUND = Gdx.audio.newSound(Gdx.files.internal("atari_boom.wav"));
	
	/** The volume to play the orbs death sound at. */
	private static final float DEATH_SOUND_VOLUME = 0.2f;
	
	/** The orbs pixels per second. */
	private static final int SPEED = 15;
	
	/** Points awarded for killing the orb. */
	private static final int POINTS = 10;
	
	/** Damage the orb does on collision with the player. */
	private static final int DAMAGE = 2;
	
	/** Orbs maximum health. */
	private static final int MAX_HEALTH = 20;

	/**
	 * Create a runner at an x and y location.
	 * @param x the x location to spawn the runner at
	 * @param y the y location to spawn the runner at
	 */
	Runner(float x, float y) {
		super(x, y, POINTS, SPEED, DAMAGE, MAX_HEALTH, "enemy.png");
	}

	@Override
	public void onDestroy() {
		//play the death sound when a runner is destroyed
		DEATH_SOUND.setVolume(DEATH_SOUND.play(), DEATH_SOUND_VOLUME);
	}
	
	@Override
	public void update(float delta) {
		moveTowards(GameScreen.getPlayer(), delta); //go towards the player
	}
	
	@Override
	public boolean onCollision(Entity collidedWith) {
		if (collidedWith instanceof Player) //destroy the runner if it collides with the player
			return true;
		
		if (collidedWith instanceof Asteroid) //destroy the runner if it collides with an asteroid
			return true;
		
		if (takeProjectileDamage(collidedWith))
			return true; //destroy this enemy
	
		
		return false; //don't destroy the runner if it collides with anything else e.g other enemies
	}
	
}