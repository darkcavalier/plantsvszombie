package com.example.plantsvszombie.layer;

import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.view.MotionEvent;

import com.example.plantsvszombie.utils.CommonUtils;

/**
 * 鑿滃崟椤甸潰
 * 
 * @author Kevin
 * 
 */
public class MenuLayer extends BaseLayer {
      
   private	CCSprite about;
   private CCSprite mine;
   private CCSprite help;
   private CCSprite conhel;
   
	public MenuLayer() {
		// 鍒濆鍖栧寳浜�
		CCSprite sprite = CCSprite.sprite("image/menu/main_menu_bg.jpg");
		 
		sprite.setAnchorPoint(0, 0);
		this.addChild(sprite);

		CCMenu menu = CCMenu.menu();// 鍒濆鍖朇CMenu

		CCSprite normalSprite = CCSprite
				.sprite("image/menu/start_adventure_default.png");// 榛樿鍥剧墖
		CCSprite selectedSprite = CCSprite
				.sprite("image/menu/start_adventure_press.png");// 閫変腑鍥剧墖
		CCMenuItemSprite item = CCMenuItemSprite.item(normalSprite,
				selectedSprite, this, "onClick");

		menu.addChild(item);
		menu.setScale(0.5f);
		menu.setPosition(winSize.width / 2 - 25, winSize.height / 2 - 110);
		menu.setRotation(4.5f);   
		this.addChild(menu);
		about=CCSprite.sprite("image/menu/about.png");
		about.setPosition(winSize.width / 2-15, 65);
		this.addChild(about);
		help=CCSprite.sprite("image/menu/help.png");
		help.setPosition(winSize.width-70, 65);
		this.addChild(help);
		setIsTouchEnabled(true);
		 mine=CCSprite.sprite("image/resum.png");
			mine.setPosition(winSize.width / 2, winSize.height / 2);
			this.addChild(mine);
			mine.setScale(0.6f);
			mine.setVisible(false);
			conhel=CCSprite.sprite("image/menu/conhel.png");
			conhel.setPosition(winSize.width / 2, winSize.height / 2);
			this.addChild(conhel);
			conhel.setScale(0.5f);
			conhel.setVisible(false);
			
	}

	/**
	 * 鑿滃崟鎸夐挳琚偣鍑� 蹇呴』甯︽湁object鍙傛暟,杩欐牱鎵嶈兘鍙嶅皠鍒拌鏂规硶閲�
	 */
	public void onClick(Object obj) {
		System.out.println("鑿滃崟鐐瑰嚮");
		CommonUtils.changeLayer(new FightLayer());

		SoundEngine.sharedEngine().realesAllSounds();// 鍋滄闊充箰
	}
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// TODO Auto-generated method stub
		CGPoint convertTouchToNodeSpace =this.convertTouchToNodeSpace(event);
//		if (CGRect.containsPoint(about.getBoundingBox(),
//				convertTouchToNodeSpace)) {
//			System.out.println("鐐瑰嚮鍏充簬!!!");
//			 mine=CCSprite.sprite("image/resum.png");
//			mine.setPosition(winSize.width / 2, winSize.height / 2);
//			this.addChild(mine);
//			mine.setScale(0.5f);
//		}
//		try {
//			if (CGRect.containsPoint(mine.getBoundingBox(),
//					convertTouchToNodeSpace)) {
//				mine.removeSelf();			
//			}
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		if (CGRect.containsPoint(about.getBoundingBox(),
				convertTouchToNodeSpace)) {
			
			 if(mine.getVisible()){
				 mine.setVisible(false);
			 }else{
				 mine.setVisible(true);
			 }
			
		}
		if (CGRect.containsPoint(help.getBoundingBox(),
				convertTouchToNodeSpace)) {
			
			 if(conhel.getVisible()){
				 conhel.setVisible(false);
			 }else{
				 conhel.setVisible(true);
			 }
			
		}
		
		return super.ccTouchesBegan(event);
	}

}
