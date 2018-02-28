package com.example.plantsvszombie.domain;

import java.util.concurrent.CopyOnWriteArrayList;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCJumpTo;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import com.example.plantsvszombie.domain.base.Product;
import com.example.plantsvszombie.layer.FightLayer;

/**
 * 阳光
 */
public class Sun extends Product {

	public static final CopyOnWriteArrayList<Sun> suns = new CopyOnWriteArrayList<Sun>();// 放置生产出来的阳�?

	private CGPoint start;
	private CGPoint end;

	private int speed = 20;

	private static final String resPath = "image/product/sun.png";
	public static int totalMoney = 1000;

	private CCNode parent;// 地图对象

	public static void setTotalMoney(int totalMoney) {
		Sun.totalMoney = totalMoney;
	}

	public Sun(CCNode parent, CGPoint start, CGPoint end) {
		super(resPath);
		this.start = start;
		this.end = end;
		this.parent = parent;

		setScale(0.3);

		setPosition(start);
		parent.getParent().addChild(this);// 应该�?图层上添加太�?, 否则会被已�?�框挡住
		suns.add(this);

		baseAction();
	}

	@Override
	public void destroy() {
		suns.remove(this);
		super.destroy();
	}

	@Override
	public void baseAction() {
		// 弧线动作
		float t = (start.y - end.y) / speed;
		// CCMoveTo moveTo = CCMoveTo.action(t, end);
		CCJumpTo jump = CCJumpTo.action(t, end, 20, 1);
		CCCallFunc callFunc = CCCallFunc.action(this, "destroy");
		CCSequence sequence = CCSequence.actions(jump, CCDelayTime.action(5),// 如果5秒后还不收集,就销毁掉
				callFunc);
		runAction(sequence);

		// 旋转动作
		runAction(CCRepeatForever.action(CCRotateBy.action(1, 180)));
	}

	// 处理阳光的点击事�?
	public void collectAction() {
		stopAllActions();// 停止之前的动�?
		float t = CGPointUtil.distance(
				CCNode.ccp(0, CCDirector.sharedDirector().winSize().height),
				end) / 200;
		CCMoveTo moveTo = CCMoveTo.action(t, CCNode.ccp(10, CCDirector
				.sharedDirector().winSize().height - 10));
		CCSequence sequence = CCSequence.actions(moveTo,
				CCCallFunc.action(this, "collect"));
		runAction(sequence);
	}
//新加�?
	public void setcollect(){
		totalMoney += 0;
		System.out.println("阳光�?:" + totalMoney);
		
		// 更新阳光数的文字
		CCLabel label = (CCLabel) parent.getParent().getChildByTag(
				FightLayer.TAG_TOTAL_MONEY);
		label.setString(String.valueOf(totalMoney));
		
		destroy();// 删除阳光
	}

	// 阳光被收集之�?, 分�?�要增加
	public void collect() {
		totalMoney += 25;// 每个阳光增加25
		System.out.println("阳光�?:" + totalMoney);
		
		// 更新阳光数的文字
		CCLabel label = (CCLabel) parent.getParent().getChildByTag(
				FightLayer.TAG_TOTAL_MONEY);
		label.setString(String.valueOf(totalMoney));
		
		destroy();// 删除阳光
	}
	
	public void reduce(int i){
		if(i==1){
			totalMoney -= 100;// 阳光值减�?100
			// 更新阳光数的文字
			CCLabel label = (CCLabel) parent.getParent().getChildByTag(
					FightLayer.TAG_TOTAL_MONEY);
			label.setString(String.valueOf(totalMoney));
			
		}else if(i==2){
			totalMoney -= 50;//阳光值减�?50
			// 更新阳光数的文字
						CCLabel label = (CCLabel) parent.getParent().getChildByTag(
								FightLayer.TAG_TOTAL_MONEY);
						label.setString(String.valueOf(totalMoney));
			
		}else if(i==3){
			totalMoney -= 125;//阳光值减�?50
			// 更新阳光数的文字
						CCLabel label = (CCLabel) parent.getParent().getChildByTag(
								FightLayer.TAG_TOTAL_MONEY);
						label.setString(String.valueOf(totalMoney));
		}else if(i==4){
			totalMoney -= 50;//阳光值减�?50
			// 更新阳光数的文字
						CCLabel label = (CCLabel) parent.getParent().getChildByTag(
								FightLayer.TAG_TOTAL_MONEY);
						label.setString(String.valueOf(totalMoney));
		}
		
		
		else if(i==8){
			totalMoney -= 200;//阳光值减�?50
			// 更新阳光数的文字
						CCLabel label = (CCLabel) parent.getParent().getChildByTag(
								FightLayer.TAG_TOTAL_MONEY);
						label.setString(String.valueOf(totalMoney));
			
			
		}
		else if(i==9){
			totalMoney -= 250;//阳光值减�?50
			// 更新阳光数的文字
						CCLabel label = (CCLabel) parent.getParent().getChildByTag(
								FightLayer.TAG_TOTAL_MONEY);
						label.setString(String.valueOf(totalMoney));
		}
	}
	
	public boolean check(){
		if(totalMoney<50){
			return false;
		}else{
			return true;
		}
		
	}
	
	public boolean checked(){
		
		
		if(totalMoney<100){
			return false;
		}else{
			return true;
		}
		
	}
	public int getTotalMoney(){
		return totalMoney;
	}
	
	public void add(int i){
		totalMoney+=i;

	}
	public boolean test(int i){
		totalMoney-=i;
		
		if(totalMoney<0){
			return false;
		}else{
			return true;
		}
		
	}
	
	
}
