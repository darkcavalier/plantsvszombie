package com.example.plantsvszombie.layer;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.instant.CCShow;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.os.AsyncTask;
import android.view.MotionEvent;

import com.example.plantsvszombie.R;
import com.example.plantsvszombie.utils.CommonUtils;

/**
 * 娆㈣繋椤甸潰
 * 
 * 
 * 
 */
public class WelcomeLayer extends BaseLayer {

	private CCSprite logo;
	private CCSprite start;

	public WelcomeLayer() {
		logo = CCSprite.sprite("image/popcap_logo.png");
		logo.setPosition(winSize.width / 2, winSize.height / 2);// 灞忓箷灞呬腑
		this.addChild(logo);

		CCHide hide = CCHide.action();// 闅愯棌
		CCDelayTime delay = CCDelayTime.action(1);// 寤舵椂涓�绉�
		CCShow show = CCShow.action();// 鏄剧ず

		CCSequence s = CCSequence.actions(hide, delay, show, delay, hide,
				delay, CCCallFunc.action(this, "showWelcome"));

		logo.runAction(s);
         
		// 寮傛鍦ㄥ悗鍙板垵濮嬪寲鏁版嵁
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				start.setVisible(true);// 鏄剧ず鐐瑰嚮寮�濮嬬殑鎸夐挳
				setIsTouchEnabled(true);// 鎵撳紑鐐瑰嚮浜嬩欢
			}
		}.execute();

		SoundEngine engine = SoundEngine.sharedEngine();
		engine.playSound(CCDirector.theApp, R.raw.start, true);// 鎾斁寮�濮嬬殑闊充箰
	}

	/**
	 * 鏄剧ず娆㈣繋椤甸潰
	 */
	public void showWelcome() {
		logo.removeSelf();// 鍒犻櫎logo

		// 鍒濆鍖栨杩庨〉闈�
		CCSprite welcome = CCSprite.sprite("image/welcome.jpg");
		welcome.setAnchorPoint(0, 0);
		this.addChild(welcome);

		// 鍒濆鍖栧姞杞戒腑鐨勫浘鐗�
		CCSprite loading = CCSprite.sprite("image/loading/loading_01.png");
		loading.setPosition(winSize.width / 2, 30);
		this.addChild(loading);

		// 鍒濆鍖栧紑濮嬫寜閽�
		start = CCSprite.sprite("image/loading/loading_start.png");
		start.setPosition(winSize.width / 2, 30);
		start.setVisible(false);// 闅愯棌鎸夐挳
		this.addChild(start);

		CCAction animate = CommonUtils.animate(
				"image/loading/loading_%02d.png", 9, false);
		loading.runAction(animate);
	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint convertTouchToNodeSpace =this.convertTouchToNodeSpace(event);

		if (CGRect.containsPoint(start.getBoundingBox(),
				convertTouchToNodeSpace)) {
			System.out.println("鐐瑰嚮寮�濮�!!!");
			CommonUtils.changeLayer(new MenuLayer());
		}

		return super.ccTouchesBegan(event);
	}
}
