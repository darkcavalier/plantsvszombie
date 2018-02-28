package com.example.plantsvszombie.domain.base;

import org.cocos2d.types.CGPoint;

public abstract class CarElement extends BaseElement{

	protected int life = 50;// ����
    protected int attack = 400;// ������
    protected int speed = 200;// �ƶ��ٶ�

    protected CGPoint startPoint;// ���
    protected CGPoint endPoint;// �յ�

    public CarElement(String filepath) {
        super(filepath);

        setScale(0.5);
        setAnchorPoint(0.5f, 0);// �������ĵ�λ��������֮��
    }

    /**
     * �ƶ�
     */
    public abstract void move();

    /**
     * ����
     * 
     * @param element:������ʬ
     */
    public abstract void attack(BaseElement element);

    /**
     * ������
     */
    public abstract void attacked(int flag);

    protected boolean isAttacking;//��ʾ�����Ƿ��Ѿ���ʼ����

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
