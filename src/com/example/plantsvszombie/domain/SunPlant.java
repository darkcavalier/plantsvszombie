package com.example.plantsvszombie.domain;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;

import com.example.plantsvszombie.domain.base.ProductPlant;
import com.example.plantsvszombie.utils.CommonUtils;

/**
 * 向日�?
 * 
 * @author Administrator
 * 
 */
public class SunPlant extends ProductPlant {

	public SunPlant() {
		super("image/plant/sunflower/p_1_01.png");
		life = 100;
		baseAction();
		create();
	}

	@Override
	public void create() {
		CCScheduler.sharedScheduler().schedule("create", this, 10, false);//每隔10秒产生阳�?
	}

	/**
	 * 产生阳光
	 * @param f
	 */
	public void create(float f) {
		new Sun(this.getParent(), ccp(getPosition().x, getPosition().y + 40),
				ccp(getPosition().x + 25, getPosition().y));
	}

	@Override
	public void baseAction() {
		CCAction animate = CommonUtils.animate(
				"image/plant/sunflower/p_1_%02d.png", 8, true);
		runAction(animate);
	}
}
