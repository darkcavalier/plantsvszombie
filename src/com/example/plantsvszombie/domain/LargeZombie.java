package com.example.plantsvszombie.domain;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import com.example.plantsvszombie.domain.base.BaseElement;
import com.example.plantsvszombie.domain.base.Plant;
import com.example.plantsvszombie.domain.base.Zombie;
import com.example.plantsvszombie.utils.CommonUtils;

/**
 * 中型僵尸
 * 
 * @author Administrator
 * 
 */
public class LargeZombie extends Zombie {
	private Plant mPlant;
	private int flag=200;

	public LargeZombie(CGPoint startPoint, CGPoint endPoint) {
		super("image/zombies/zombies_1/walk/uupoop_01.png");
		life=200;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.setPosition(startPoint);// 设置僵尸起点坐标
		move();

	}

	@Override
	public void move() {
		CCMoveTo move = CCMoveTo
				.action(CGPointUtil.distance(getPosition(), endPoint) / speed,
						endPoint);

		CCSequence s = CCSequence.actions(move,
				CCCallFunc.action(this, "destroy"));// 僵尸走到头,要销毁

		this.runAction(s);
		baseAction();
	}

	
	
	@Override
	public void attack(BaseElement element) {
		if (element instanceof Plant && !isDieding) {// 判断是否是植物
			mPlant = (Plant) element;
			this.stopAllActions();// 停止僵尸所有动作

			CCAction animate = CommonUtils
					.animate("image/zombies/zombies_1/attack/uupoopa_%02d.png",
							11, true);
			this.runAction(animate);// 僵尸咬植物的动画

			CCScheduler scheduler = CCScheduler.sharedScheduler();
			scheduler.schedule("attackPlant", this, 1, false);// 每隔一秒,咬一口植物

		}
	}
	
	/**
	 * 僵尸攻击植物
	 * 
	 * @param f
	 */
	public void attackPlant(float f) {
		if (mPlant != null && !isDieding) {
			mPlant.attacked(attack);// 植物掉血
			if (mPlant.getLife() <= 0) {// 植物挂了
				CCScheduler.sharedScheduler().unschedule("attackPlant", this);// 停止定时器
				this.stopAllActions();// 停止僵尸所有动作
				isAttacking = false;// 表示僵尸已经攻击结束
				move();// 僵尸继续前行
				
			}
		} else {
			CCScheduler.sharedScheduler().unschedule("attackPlant", this);// 停止攻击植物
		}
	}

	boolean isDieding = false;// 标记僵尸是否正在死亡

	@Override
	public void attacked(int attack) {
		life -= attack;// 僵尸掉血
		
		if(attack==400){
			isDieding = false;
			flag-=400;
		}
		
		if(life<=200&&life>100&&!isDieding&&flag>=100){
			
			isDieding = false;
			 if (!isAttacking){
//				 CCAnimate animate = (CCAnimate) CommonUtils.animate(
//						 "image/zombies/zombies_1/walk/uupoop_%02d.png",15,
//						 false);
				 
				 CCAction animate = CommonUtils
							.animate("image/zombies/zombies_1/walk/uupoop_%02d.png",
									15, true);
					this.runAction(animate);// 僵尸咬植物的动画
//				 this.runAction(animate);
			 }else{
//				 CCAnimate animate = (CCAnimate) CommonUtils.animate(
//						 "image/zombies/zombies_1/attack/uupoopa_%02d.png",11,
//						 false);
				 CCAction animate = CommonUtils
							.animate("image/zombies/zombies_1/attack/uupoopa_%02d.png",
									11, true);
					this.runAction(animate);
//				 this.runAction(animate);
			 }
			 flag-=10;
		}
		if(life<=100&&life>0&&!isDieding&&flag>=0){
			
			 isDieding = false;
			 if (!isAttacking){
//				 CCAnimate animate = (CCAnimate) CommonUtils.animate(
//						 "image/zombies/zombies_1/walk/z_1_%02d.png", 7,
//						 false);
				 CCAction animate = CommonUtils
							.animate("image/zombies/zombies_1/walk/z_1_%02d.png",
									7, true);
					this.runAction(animate);
//				 this.runAction(animate);
			 }else{
//				 CCAnimate animate = (CCAnimate) CommonUtils.animate(
//						 "image/zombies/zombies_1/attack/z_1_attack_%02d.png", 10,
//						 false);
				 CCAction animate = CommonUtils
							.animate("image/zombies/zombies_1/attack/z_1_attack_%02d.png",
									10, true);
				 this.runAction(animate);
			 }
			 flag-=10;
		}

		if(life<=0&&!isDieding&&flag<0){
			
			 isDieding = true;
			 this.stopAllActions();
			 if (!isAttacking) {// 没有攻击的动画
			 CCAnimate animate1 = (CCAnimate) CommonUtils.animate(
			 "image/zombies/zombies_1/head/z_1_head_%02d.png", 6,
			 false);
			 CCAnimate animate2 = (CCAnimate) CommonUtils.animate(
			 "image/zombies/zombies_1/die/z_1_die_%02d.png", 6,
			 false);
			 CCSequence sequence = CCSequence.actions(animate1, animate2,
			 CCCallFunc.action(this, "died"));
			 this.runAction(sequence);
			 } else {// 正在攻击的动画
			 CCAnimate animate = (CCAnimate) CommonUtils
			 .animate(
			 "image/zombies/zombies_1/attack_losthead/z_1_attack_losthead_%02d.png",
			 8, false);
			 CCAction die = CommonUtils.animate(
			 "image/zombies/zombies_1/die/z_1_die_%02d.png", 6,
			 false);
			 CCSequence sequence = CCSequence.actions((CCAnimate) animate,
			 (CCAnimate) die, CCCallFunc.action(this, "died"));
			 this.runAction(sequence);
			 }
			 flag-=10;
		}
		
		
		

	}

	@Override
	public void baseAction() {
		// 僵尸行走
		CCAction animate = CommonUtils.animate(
				"image/zombies/zombies_1/walk/uupoop_%02d.png", 15, true);
		this.runAction(animate);
		
		if(life<=100&&life>0&&!isDieding&&flag>=0){
			
			 isDieding = false;
			 if (!isAttacking){
//				 CCAnimate animate = (CCAnimate) CommonUtils.animate(
//						 "image/zombies/zombies_1/walk/z_1_%02d.png", 7,
//						 false);
				 CCAction animate1 = CommonUtils
							.animate("image/zombies/zombies_1/walk/z_1_%02d.png",
									7, true);
					this.runAction(animate1);
//				 this.runAction(animate);
			 }else{
//				 CCAnimate animate = (CCAnimate) CommonUtils.animate(
//						 "image/zombies/zombies_1/attack/z_1_attack_%02d.png", 10,
//						 false);
				 CCAction animate2 = CommonUtils
							.animate("image/zombies/zombies_1/attack/z_1_attack_%02d.png",
									10, true);
				 this.runAction(animate2);
			 }
			 flag-=10;
		}

		if(life<=0&&!isDieding&&flag<0){
			
			 isDieding = true;
			 this.stopAllActions();
			 if (!isAttacking) {// 没有攻击的动画
			 CCAnimate animate3 = (CCAnimate) CommonUtils.animate(
			 "image/zombies/zombies_1/head/z_1_head_%02d.png", 6,
			 false);
			 CCAnimate animate4 = (CCAnimate) CommonUtils.animate(
			 "image/zombies/zombies_1/die/z_1_die_%02d.png", 6,
			 false);
			 CCSequence sequence = CCSequence.actions(animate3, animate4,
			 CCCallFunc.action(this, "died"));
			 this.runAction(sequence);
			 } else {// 正在攻击的动画
			 CCAnimate animate5 = (CCAnimate) CommonUtils
			 .animate(
			 "image/zombies/zombies_1/attack_losthead/z_1_attack_losthead_%02d.png",
			 8, false);
			 CCAction die = CommonUtils.animate(
			 "image/zombies/zombies_1/die/z_1_die_%02d.png", 6,
			 false);
			 CCSequence sequence = CCSequence.actions((CCAnimate) animate5,
			 (CCAnimate) die, CCCallFunc.action(this, "died"));
			 this.runAction(sequence);
			 }
			 flag-=10;
		}
		
		


	}

	/**
	 * 僵尸死了
	 */
	public void died() {
		destroy();
		isDieding = false;
	}

}
