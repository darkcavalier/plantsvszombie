package com.example.plantsvszombie.domain;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;

import com.example.plantsvszombie.utils.CommonUtils;

/**
 * 展现的僵�?
 * 
 * @author Kevin
 * 
 */
public class ShowZombie extends CCSprite {

	public ShowZombie() {
		super("image/zombies/zombies_1/shake/z_1_01.png");//初始化僵尸图�?
		setScale(0.5);// 设置僵尸大小
		setAnchorPoint(0.5f, 0);// 锚点是两脚之�?

		CCAction animate = CommonUtils.animate(
				"image/zombies/zombies_1/shake/z_1_%02d.png", 2, true);
		runAction(animate);// 僵尸颤抖
	}

}
