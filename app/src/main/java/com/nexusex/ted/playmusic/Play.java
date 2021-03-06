package com.nexusex.ted.playmusic;

import com.nexusex.ted.bean.MusicInfo;

/**
 * 音频播放接口,播放类实现此接口
 */
public interface Play {

	/**
	 * 初始化
	 */
	void initPlayer();

	/**
	 * 准备播放
	 */
	void prepare(MusicInfo musicInfo);

	/**
	 * 开始播放
	 */
	void start();

	/**
	 * 暂停播放
	 */
	void pause();

	/**
	 * 停止播放
	 */
	void stop();

	/**
	 * 释放播放器资源
	 */
	void release();

	/**
	 * 重建播放器
	 */
	void reset();

	/**
	 * 切换播放位置
	 */
	void setCurrentPosition(int position);

	/**
	 * 切歌
	 */
	void playNextAudio(MusicInfo musicInfo);
}
