
package com.example.plantsvszombie.domain.base;

import org.cocos2d.types.CGPoint;

/**
 * 鍍靛案鍩虹被
 * 
 * @author Administrator
 */
public abstract class Zombie extends BaseElement {

    protected int life = 50;// 鐢熷懡
    protected int attack = 10;// 鏀诲嚮鍔�
    protected int speed = 10;// 绉诲姩閫熷害

    protected CGPoint startPoint;// 璧风偣
    protected CGPoint endPoint;// 缁堢偣

    public Zombie(String filepath) {
        super(filepath);

        setScale(0.5);
        setAnchorPoint(0.5f, 0);// 灏嗚В鏋愮殑鐐逛綅鏀惧湪涓よ吙涔嬮棿
    }

    /**
     * 绉诲姩
     */
    public abstract void move();

    /**
     * 鏀诲嚮
     * 
     * @param element:鏀诲嚮妞嶇墿锛屾敾鍑诲兊灏�
     */
    public abstract void attack(BaseElement element);

    /**
     * 琚敾鍑�
     */
    public abstract void attacked(int attack);

    protected boolean isAttacking;//琛ㄧず鍍靛案鏄惁宸茬粡寮�濮嬫敾鍑�

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

}
