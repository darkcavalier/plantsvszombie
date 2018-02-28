package com.example.plantsvszombie.domain.base;
/**
 * 闃插尽鍨嬫鐗�
 * @author Administrator
 *
 */
public abstract class DefancePlant extends Plant {

	public DefancePlant(String filepath) {
		super(filepath);
		life = 200;
	}

}
