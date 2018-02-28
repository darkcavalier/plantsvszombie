package com.example.plantsvszombie.domain.base;

import org.cocos2d.nodes.CCSprite;

/**
 * 瀵规垬鍏冪礌鍏辨��
 * 
 * @author Administrator
 */
public abstract class BaseElement extends CCSprite {

	public interface DieListener {
		void die();
	}

	private DieListener dieListener; // 姝讳骸鐨勭洃鍚�

	public void setDieListener(DieListener dieListener) {
		this.dieListener = dieListener;
	}

	public BaseElement(String filepath) {
		super(filepath);
	}

	/**
	 * 鍘熷湴涓嶅姩鐨勫熀鏈姩浣�
	 */
	public abstract void baseAction();

	public void destroy() {
		if (dieListener != null) {
			dieListener.die();
		}
		removeSelf();
	}

}
