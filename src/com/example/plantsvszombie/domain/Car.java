package com.example.plantsvszombie.domain;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import com.example.plantsvszombie.domain.base.BaseElement;
import com.example.plantsvszombie.domain.base.CarElement;
import com.example.plantsvszombie.domain.base.Plant;
import com.example.plantsvszombie.domain.base.Zombie;
import com.example.plantsvszombie.utils.CommonUtils;



public class Car extends CarElement{
	protected CGPoint startPoint;
	
	protected CGPoint endPoint;
	
	public Car(String filepath){
		super(filepath);
	}
	
	public Car(CGPoint startPoint,CGPoint endPoint) {
		super("image/car/LawnCleaner.png");
		attack=400;
	
		this.setPosition(startPoint);// 设置车子起点坐标
		
		

	}	
	public CGPoint getStartPoint(){
		return startPoint;
	}
	public CGPoint getEndPoint(){
		return endPoint;
	}
	
	public void setStartPoint(CGPoint carstartPoint){
		this.startPoint = carstartPoint;
		
	}
	public void setEndPoint(CGPoint carendPoint){
		this.endPoint=carendPoint;
	}
	
	

//	@Override
//	public void baseAction() {
//		
//		CCAction animate = CommonUtils.animate(
//				"image/car/LawnCleaner.png",1, true);
//		this.runAction(animate);
//	}

	@Override
	public void move() {
		float t = (CCDirector.sharedDirector().winSize().width - getPosition().x)
				/ speed;// 计算车子移动事件
		CCMoveTo move = CCMoveTo.action(
				t,
				ccp(CCDirector.sharedDirector().winSize().width,
						getPosition().y));// 车子移动到屏幕右侧边缘
		CCSequence sequence = CCSequence.actions(move,
				CCCallFunc.action(this, "destroy"));// 移动结束后,车子销毁
		runAction(sequence);
	}

	@Override
	public void attack(BaseElement element) {
		
		
	}

	@Override
	public void attacked(int flag) {
		
		
		
	}
	public int getAttack(){
		return attack;
	}
	@Override
	public void baseAction() {
		// TODO Auto-generated method stub
		
	}
	
	


	
}
