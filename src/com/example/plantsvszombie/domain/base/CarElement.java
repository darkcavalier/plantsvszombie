package com.example.plantsvszombie.domain.base;

import org.cocos2d.types.CGPoint;

public abstract class CarElement extends BaseElement{

	protected int life = 50;// 生命
    protected int attack = 400;// 攻击力
    protected int speed = 200;// 移动速度

    protected CGPoint startPoint;// 起点
    protected CGPoint endPoint;// 终点

    public CarElement(String filepath) {
        super(filepath);

        setScale(0.5);
        setAnchorPoint(0.5f, 0);// 将解析的点位放在两腿之间
    }

    /**
     * 移动
     */
    public abstract void move();

    /**
     * 攻击
     * 
     * @param element:攻击僵尸
     */
    public abstract void attack(BaseElement element);

    /**
     * 被攻击
     */
    public abstract void attacked(int flag);

    protected boolean isAttacking;//表示车子是否已经开始攻击

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
    
    public CGPoint getStartPoint(){
    	return startPoint;
    }
    
    public CGPoint getEndPoint(){
    	return endPoint;
    }

}
