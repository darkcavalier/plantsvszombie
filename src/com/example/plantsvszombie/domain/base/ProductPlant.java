package com.example.plantsvszombie.domain.base;
/**
 * 鐢熶骇鍨嬫鐗�
 * @author Administrator
 *
 */
public abstract class ProductPlant extends Plant {

	public ProductPlant(String filepath) {
		super(filepath);
	}

	/**
	 * 闃冲厜銆侀噾甯�
	 */
	public abstract void create();
	

}
