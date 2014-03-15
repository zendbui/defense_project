package com.me.image;

public class Bullet {
	float x;
	float y;
	float target_x;
	float target_y;
	float speed = 10.0f;
	float range = 10.0f;
	EnemyImage target;

	public Bullet(float x, float y, EnemyImage target) {
		this.x = x;
		this.y = y;
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

	public void updateTarget() {
		// calculate new position
		float distance = CalcDistance(this.x, this.y, this.target_x,
				this.target_y);
		float old_x = this.x;
		float old_y = this.y;
//		System.out.println(x + ":" + y + ":" + speed + ":" + distance);
//		System.out.println(target_x + ":" + target_y);
		if (distance < speed) {
			this.x = target_x;
			this.y = target_y;
		} else {
			this.x = old_x + speed * (target_x - old_x) / distance;
			this.y = (this.x - old_x) * (target_y - old_y)
					/ (target_x - old_x) + old_y;
		}

		this.target_x = target.getCenterX();
		this.target_y = target.getCenterY();

	}

	public boolean isShot() {
		if (CalcDistance(this.x, this.y, this.target_x, this.target_y) < range) {
			return true;
		} else {
			return false;
		}
	}

	public float CalcDistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((double) (x1 - x2) * (x1 - x2) + (y1 - y2)
				* (y1 - y2));
	}
}
