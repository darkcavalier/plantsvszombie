package com.example.plantsvszombie.domain;

import org.cocos2d.actions.base.CCAction;

import com.example.plantsvszombie.domain.base.AttackPlant;
import com.example.plantsvszombie.domain.base.Bullet;
import com.example.plantsvszombie.domain.base.BaseElement.DieListener;
import com.example.plantsvszombie.layer.utils.CommonUtils;

public class SplitPeaPlant extends AttackPlant {

	public SplitPeaPlant() {
		super("image/plant/SplitPea/sp_01.png");
		// TODO Auto-generated constructor stub
		baseAction();
	}
	public Bullet createBullet() {
		if (bullets.size() < 1) {// 每次只能生产�?个子�?
			final Pea pea = new Pea();
			final Pea pea1 = new Pea();
			pea.setPosition(ccp(getPosition().x+20 , getPosition().y + 35));// 设置子弹的位�?
			pea1.setPosition(ccp(getPosition().x+20 , getPosition().y + 35));// 设置子弹的位�?
			
			pea.move();// 子弹移动
			pea1.move1();
			
			
		
			pea.setDieListener(new DieListener() {

				@Override
				public void die() {
					bullets.remove(pea);// 从集合移除子�?
					
				}
			});
			
			pea1.setDieListener(new DieListener() {

				@Override
				public void die() {
				        // 从集合移除子�?
					bullets.remove(pea1);
				}
			});
			
			
			
			bullets.add(pea);
			bullets.add(pea1);
			this.getParent().addChild(pea);// 子弹显示在地图上
			this.getParent().addChild(pea1);
			

			return pea;
			
		}

		return null;
	}


	@Override
	public void baseAction() {
		CCAction animate = CommonUtils.animate(
				"image/plant/SplitPea/sp_%02d.png", 8, true);
		runAction(animate);
	}
}
