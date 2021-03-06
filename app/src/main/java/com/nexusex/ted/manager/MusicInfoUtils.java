package com.nexusex.ted.manager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.nexusex.ted.bean.MusicInfo;
import com.nexusex.ted.bean.MusicInfoList;
import com.nexusex.ted.utils.Utils;
import java.util.ArrayList;

/**
 * Created by davinci42 on 2016/4/13.
 */
public class MusicInfoUtils {

	private static final String TAG = "MusicInfoUtils";

	public static MusicInfoList getAllMusicInfoList(Context context) {

		MusicInfoList musicInfoList = new MusicInfoList();
		musicInfoList.setMusicInfoList(new ArrayList<MusicInfo>());
		musicInfoList.setTitle("All");

		ContentResolver contentResolver = context.getContentResolver();
		Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor cursor = contentResolver.query(uri, null, null, null, null);
		if (cursor == null) {
			Utils.handleError(TAG, "Query Failed");
		} else if (!cursor.moveToFirst()) {
			Utils.handleError(TAG, "No Media");
		} else {
			int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
			int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
			int durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
			int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
			do {
				long thisDuration = cursor.getLong(durationColumn);
				// 屏蔽微信语音之类的音频文件
				if (thisDuration > 30000) {
					long thisId = cursor.getLong(idColumn);
					String thisTitle = cursor.getString(titleColumn);
					String thisArtist = cursor.getString(artistColumn);
					MusicInfo musicInfo = new MusicInfo();
					musicInfo.setId(thisId);
					musicInfo.setArtist(thisArtist);
					musicInfo.setDuration(thisDuration);
					musicInfo.setTitle(thisTitle);
					musicInfoList.getMusicInfoList().add(musicInfo);
				}
			} while (cursor.moveToNext());
		}

		if (cursor != null) {
			cursor.close();
		}

		return PlayListManager.arrangeMusicInfoListByTitle(musicInfoList);
	}

	public static Uri getUriWithId(long id) {
		return ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
	}
}
