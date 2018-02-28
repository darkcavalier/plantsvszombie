package com.example.plantsvszombie.domain.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 鏀诲嚮鍨嬫鐗�
 * 
 * @author Administrator
 * 
 */
public abstract class AttackPlant extends Plant {
    // 寮瑰す
    protected List<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();

    public AttackPlant(String filepath) {
        super(filepath);
    }

    /**
     * 鐢熶骇鐢ㄤ簬鏀诲嚮鐨勫瓙寮�
     * 
     * @return
     */
    public abstract Bullet createBullet();
    /**
     * 寮瑰す  绠＄悊鎴戜骇鐢熺殑瀛愬脊
     * @return
     */
    public List<Bullet> getBullets() {
        return bullets;
    }


}
