package com.example.plantsvszombie.domain;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;

import com.example.plantsvszombie.domain.base.Bullet;
import com.example.plantsvszombie.domain.base.Zombie;

/**
 * 豌豆射手的子�?
 * 
 * @author Kevin
 * 
 */
public class Pea extends Bullet {

	

	public Pea() {
		super("image/fight/bullet.png");
		setScale(0.65f);
	}

	@Override
	public void move() {
		float t = (CCDirector.sharedDirector().winSize().width - getPosition().x)
				/ speed;// 计算子弹移动事件
		CCMoveTo move = CCMoveTo.action(
				t,
				ccp(CCDirector.sharedDirector().winSize().width,
						getPosition().y));// 子弹移动到屏幕右侧边�?
		CCSequence sequence = CCSequence.actions(move,
				CCCallFunc.action(this, "destroy"));// 移动结束�?,子弹�?�?
		runAction(sequence);
	}

	@Override
	public void move1() {
		float t = ( getPosition().x)
				/ speed/2;// 计算子弹移动事件
//	if(getPosition().)
		CCMoveTo move = CCMoveTo.action(t,
				ccp(0 , getPosition().y));// 子弹移动到屏幕右侧边�?
		CCSequence sequence = CCSequence.actions(move,
				CCCallFunc.action(this, "destroy"));// 移动结束�?,子弹�?�?
		runAction(sequence);
	}
	

}
