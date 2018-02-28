package com.example.plantsvszombie.domain.base;
/**
 * 瀛愬脊
 */
public abstract class Bullet extends Product {
	protected int attack = 10;// 鏀诲嚮鍔�
	protected int speed =100;// 绉诲姩閫熷害
	protected String filepath = "image/fight/pea_02.png";// 绉诲姩閫熷害

	public Bullet(String filepath) {
		super(filepath);
	}

	@Override
	public void baseAction() {

	}
	/**
	 * 绉诲姩
	 */
	public abstract void move();
	
	public abstract void move1();

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	
	
	
	
	
	
}
