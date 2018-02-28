package com.example.plantsvszombie.layer.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

/**
 * 宸ュ叿绫�
 * 
 * @author Kevin
 * 
 */
public class CommonUtils {

	/**
	 * 鍔ㄧ敾宸ュ叿绫�
	 * 
	 * @param format
	 *            璺緞鐨刦ormat
	 * @param num
	 *            甯ф暟
	 * @param repeat
	 *            鍔ㄧ敾鏄惁寰幆
	 * @return
	 */
	public static CCAction animate(String format, int num, boolean repeat, float t) {
		ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
		for (int i = 1; i <= num; i++) {
			frames.add(CCSprite.sprite(String.format(format, i))
					.displayedFrame());
		}

		CCAnimation anim = CCAnimation.animation("loading", t, frames);// 鍙�2琛ㄧず姣忎竴甯ф樉绀烘椂闂�

		if (!repeat) {
			CCAnimate animate = CCAnimate.action(anim, false);// 鍙�2鏄痜alse琛ㄧず鍙墽琛屼竴娆�,榛樿鏄痶rue
			return animate;
		} else {
			CCAnimate animate = CCAnimate.action(anim);
			CCRepeatForever r = CCRepeatForever.action(animate);
			return r;
		}
	}
	
	public static CCAction animate(String format, int num, boolean repeat) {
		return animate(format, num, repeat, 0.2f);
	}

	/**
	 * 鍒囨崲鍥惧眰
	 */
	public static void changeLayer(CCLayer layer) {
		CCScene scene = CCScene.node();
		scene.addChild(layer);

		// CCJumpZoomTransition transition = CCJumpZoomTransition.transition(2,
		// scene);// 鍒囨崲鏁堟灉

		CCFadeTransition transition = CCFadeTransition.transition(1, scene);// 娣″叆娣″嚭
		CCDirector.sharedDirector().replaceScene(transition);// 鍒囨崲鍦烘櫙
	}

	/**
	 * 鍔犺浇鍧愭爣鐐�
	 */
	public static ArrayList<CGPoint> loadPoint(CCTMXTiledMap map,
			String groupName) {
		ArrayList<CGPoint> points = new ArrayList<CGPoint>();

		CCTMXObjectGroup objectGroupNamed = map.objectGroupNamed(groupName);
		ArrayList<HashMap<String, String>> objects = objectGroupNamed.objects;
		for (HashMap<String, String> hashMap : objects) {
			Integer x = Integer.parseInt(hashMap.get("x"));
			Integer y = Integer.parseInt(hashMap.get("y"));
			points.add(CCNode.ccp(x, y));
		}

		return points;
	}
}