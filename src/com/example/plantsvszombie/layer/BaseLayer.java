package com.example.plantsvszombie.layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

/**
 * 图层基类
 * 
 * @author Kevin
 * 
 */
public class BaseLayer extends CCLayer {

	public CGSize winSize = CCDirector.sharedDirector().winSize();// 屏幕宽高

	public BaseLayer() {
	}

	
}
