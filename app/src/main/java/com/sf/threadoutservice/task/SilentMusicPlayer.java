package com.sf.threadoutservice.task;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;

import com.orhanobut.logger.Logger;
import com.sf.threadoutservice.App;
import com.sf.threadoutservice.AppForegroundWatcher;

import java.io.IOException;

/**
 * @author by liyunlei
 * <p>
 * write on 2020/5/27
 * <p>
 * Class desc:
 */
public class SilentMusicPlayer implements AppForegroundWatcher {

    private MediaPlayer mediaPlayer;
    private static final String REMINDER_MEDIA_FILE = "reminder.wav";

    private static final String TAG = "SilentMusicPlayer";

    private Handler handler;
    private HandlerThread handlerThread;
    private boolean isPlayerPrepared;

    public SilentMusicPlayer() {
        handlerThread = new HandlerThread("SilentMusicPlayer");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        initMedia();
    }


    private void initMedia() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer == null) {
                    try {
                        AssetFileDescriptor desc = App.getInstance().getAssets().openFd(REMINDER_MEDIA_FILE);
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(desc.getFileDescriptor(), desc.getStartOffset(), desc.getLength());
                        desc.close();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                            @Override
                            public boolean onError(MediaPlayer mp, int what, int extra) {
                                mediaPlayer.reset();
                                mediaPlayer.start();
                                return false;
                            }
                        });
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mediaPlayer.setVolume(0.0f, 0.0f);
//                                mediaPlayer.setVolume(100.0f, 100.0f);
                                mediaPlayer.start();
                            }
                        });
                    } catch (IOException e) {
                        Logger.e(e.getMessage());
                    }

                }
            }
        });

    }

    /**
     * 继续播放
     */
    private void resumeMediaPlayer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            }
        });
    }

    /**
     * 暂停播放
     */
    private void pauseMediaPlayer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        });

    }

    private void playMedia() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer == null || mediaPlayer.isPlaying()) {
                    return;
                }

                mediaPlayer.setVolume(0.0f, 0.0f);
//                mediaPlayer.setVolume(100.0f, 100.0f);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        isPlayerPrepared = true;
                        mediaPlayer.start();
                    }
                });
            }
        });

    }

    @Override
    public void onForeground() {
        pauseMediaPlayer();
    }

    @Override
    public void onBackground() {
        if (isPlayerPrepared) {
            resumeMediaPlayer();
        } else {
            playMedia();
        }
    }
}
