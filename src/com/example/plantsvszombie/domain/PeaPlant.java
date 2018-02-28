package com.example.plantsvszombie.domain;

import org.cocos2d.actions.base.CCAction;

import com.example.plantsvszombie.domain.base.AttackPlant;
import com.example.plantsvszombie.domain.base.Bullet;
import com.example.plantsvszombie.utils.CommonUtils;

/*
 * 豌豆射手
 */
public class PeaPlant extends AttackPlant {

	public PeaPlant() {
		super("image/plant/peas/p_2_01.png");
		baseAction();
	}

	@Override
	public Bullet createBullet() {
		if (bullets.size() < 1) {// 每次只能生产�?个子�?
			final Pea pea = new Pea();
			pea.setPosition(ccp(getPosition().x + 20, getPosition().y + 35));// 设置子弹的位�?
			pea.move();// 子弹移动

			pea.setDieListener(new DieListener() {

				@Override
				public void die() {
					bullets.remove(pea);// 从集合移除子�?
				}
			});
			
			bullets.add(pea);
			this.getParent().addChild(pea);// 子弹显示在地图上

			return pea;
		}

		return null;
	}

	@Override
	public void baseAction() {
		CCAction animate = CommonUtils.animate("image/plant/peas/p_2_%02d.png",
				8, true);
		this.runAction(animate);
	}

}
