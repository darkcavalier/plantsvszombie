package com.example.plantsvszombie.domain;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;

import com.example.plantsvszombie.utils.CommonUtils;

/**
 * չ�ֵĽ�ʬ
 * 
 * @author Kevin
 * 
 */
public class ShowZombie2 extends CCSprite {

	public ShowZombie2() {
		super("image/zombies/zombies_1/shake/up_01.png");//��ʼ����ʬͼƬ
		
		setScale(0.5);// ���ý�ʬ��С
		setAnchorPoint(0.5f, 0);// ê��������֮��

		CCAction animate = CommonUtils.animate(
				"image/zombies/zombies_1/shake/up_%02d.png", 6, true);
		
		
		runAction(animate);// ��ʬ����
		
	}


}
