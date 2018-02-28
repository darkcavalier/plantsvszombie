package com.example.plantsvszombie;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;

import android.app.Activity;
import android.os.Bundle;

import com.example.plantsvszombie.engine.GameEngine;
import com.example.plantsvszombie.layer.FightLayer;
import com.example.plantsvszombie.layer.MenuLayer;
import com.example.plantsvszombie.layer.WelcomeLayer;

public class MainActivity extends Activity {

	private CCDirector director;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CCGLSurfaceView view = new CCGLSurfaceView(this);// 鍒涘缓涓�涓猄urfaceView,
															// 绫讳技瀵兼紨鐪煎墠鐨勫皬鑽у箷,
															// 鍦ㄦ蹇呴』浼爐his瀵硅薄,搴曞眰瑕佸己杞垚Activity
		setContentView(view);

		director = CCDirector.sharedDirector();
		director.attachInView(view);// 寮�鍚粯鍒剁嚎绋�

		director.setDisplayFPS(true);// 鏄剧ず甯х巼,琛ㄧず姣忕鍒锋柊鐣岄潰鐨勬鏁�, 涓�鑸綋甯х巼澶т簬30甯�,
										// 鍩烘湰涓婁汉鐪肩湅璧锋潵姣旇緝娴佺晠, 甯х巼鍜屾墜鏈烘�ц兘涓庣▼搴忔�ц兘鏈夊叧
		//director.setAnimationInterval(1 / 60f);// 璁剧疆鏈�楂樺抚鐜囦负60

		director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);// 璁剧疆寮哄埗妯睆鏄剧ず
		director.setScreenSize(480, 320);// 鐢ㄤ簬灞忓箷閫傞厤, 浼氬熀浜庝笉鍚屽ぇ灏忕殑灞忓箷绛夋瘮渚嬬缉鏀�,
											// 璁剧疆鎴戜滑寮�鍙戞椂鍊欑殑鍒嗚鲸鐜�

		CCScene scene = CCScene.node();// 鍒涘缓涓�涓満鏅璞�
		WelcomeLayer layer = new WelcomeLayer();

		scene.addChild(layer);// 缁欏満鏅坊鍔犲浘灞�

		director.runWithScene(scene);// 瀵兼紨杩愯鍦烘櫙
	}

	@Override
	protected void onResume() {
		super.onResume();
		director.resume();// 娓告垙缁х画
		SoundEngine.sharedEngine().resumeSound();// 闊充箰缁х画
	}

	@Override
	protected void onPause() {
		super.onPause();
		director.pause();// 娓告垙鏆傚仠
		SoundEngine.sharedEngine().pauseSound();// 闊充箰鏆傚仠
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		director.end();// 娓告垙缁撴潫
		//GameEngine.isStart = false;
		System.exit(0);//鏉�姝昏繘绋�,娓呯┖鎵�鏈夐潤鎬佸彉閲�
	}
}
