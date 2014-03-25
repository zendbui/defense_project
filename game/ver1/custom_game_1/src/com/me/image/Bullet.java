package com.me.image;

public class Bullet {
	float x;
	float y;
	float target_x;
	float target_y;
	float speed;
	float range = 5.0f;
	float bulletDamage;
	EnemyImage target;
	boolean isShot = false;

	public Bullet(float x, float y, float dam, float speed, EnemyImage target) {
		this.x = x;
		this.y = y;
		this.bulletDamage = dam;
		this.speed = speed;
		this.target = target;
		this.target_x = target.getCenterX();
		this.target_y = target.getCenterY();
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public float getTargetX() {
		return this.x;
	}

	public float getTargetY() {
		return this.y;
	}

	public void updateSpeed(float speed) {
		this.speed = speed;
	}
	public void updateDamage(float dam){
		this.bulletDamage = dam;
	}
	public void updateTarget() {
		// calculate new position
		float distance = CalcDistance(this.x, this.y, this.target_x,
				this.target_y);
		float old_x = this.x;
		float old_y = this.y;
		
		if (distance < speed) {
			this.x = target_x;
			this.y = target_y;
		} else {
			this.x = old_x + speed * (target_x - old_x) / distance;
			this.y = (this.x - old_x) * (target_y - old_y) / (target_x - old_x)
					+ old_y;
		}

		this.target_x = target.getCenterX();
		this.target_y = target.getCenterY();

	}

	public boolean isShot() {
		if (isShot) {
			return true;
		}
		if (CalcDistance(this.x, this.y, this.target_x, this.target_y) < range) {
			this.target.hit(bulletDamage);
			isShot = true;
		} else {
			isShot = false;
		}
		return isShot;
	}

	public float CalcDistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((double) (x1 - x2) * (x1 - x2) + (y1 - y2)
				* (y1 - y2));
	}
}
