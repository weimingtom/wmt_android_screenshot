package com.poofinc.screenshot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ScreenshotService extends Service {
	private static final boolean D = true;
    private static final String TAG = "ScreenshotService";
    
    //FIXME:point to app data path
    private static final String DATA_PATH = 
    	//"/data/data/com.poofinc.screenshot";
    	"/data/data/com.iteye.weimingtom.screenshot";

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        if (D) {
        	Log.d(TAG, "onCreate");
        }
    }

    @Override
    public void onDestroy() {
        if (D) {
        	Log.d(TAG, "onDestroy");
        }
    }

    public boolean createDirIfNotExists(String path) {
        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (file.exists() || file.mkdirs()) {
            return true;
        }
        Toast.makeText(this, "Problem creating Screenshot folder", 1).show();
        return false;
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Log.d(TAG, "onStart");
        if (createDirIfNotExists("Screenshot")) {
            File file;
            Process process;
            DataOutputStream os;
            //String screenshotDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            //FIXME:
            String screenshotDir = "/sdcard";
            if (!screenshotDir.endsWith("/")) {
                screenshotDir = new StringBuilder(String.valueOf(screenshotDir)).append("/").toString();
            }
            screenshotDir = new StringBuilder(String.valueOf(screenshotDir)).append("Screenshot/").toString();
            Calendar cal = Calendar.getInstance();
            String screenshotFile = new StringBuilder(String.valueOf(screenshotDir)).append(new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss").format(cal.getTime())).toString();
            boolean success = false;
            /*
            try {
            	if (D) {
            		Log.d(TAG, "/system/bin/screencap -p " + screenshotFile + ".png");
                }
                Runtime.getRuntime().exec("/system/bin/screencap -p " + screenshotFile + ".png").waitFor();
                success = true;
                file = new File(new StringBuilder(String.valueOf(screenshotFile)).append(".png").toString());
                if (!file.exists() || file.length() == 0) {
                    if (file.exists()) {
                        file.delete();
                    }
                    success = false;
                }
                if (D) {
                	Log.d(TAG, ">>>>>>success: " + success);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            */
            if (!success) {
                if (D) {
                	Log.d(TAG, ">>>>>>success2: " + success);
                }
                try {
                    process = Runtime.getRuntime().exec("su");
                    //process = Runtime.getRuntime().exec("sh");
                    try {
                        String str = "timeout";
                        Thread.sleep((long) (getSharedPreferences("PoofincScreenshot", 0).getInt(str, 5) * 1000));
                        if (D) {
                        	Log.d(TAG, "/system/bin/screencap " + screenshotFile);
                        }
                        os = new DataOutputStream(process.getOutputStream());
                        BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        os.writeBytes("/system/bin/screencap -p " + screenshotFile + ".png \n");
                        os.writeBytes("exit\n");
                        os.flush();
                        os.close();
                        StringBuilder total = new StringBuilder();
                        while (true) {
                            String line = r.readLine();
                            if (line == null) {
                                break;
                            }
                            total.append(line);
                        }
                        process.waitFor();
                        success = true;
                        file = new File(new StringBuilder(String.valueOf(screenshotFile)).append(".png").toString());
                        if (!file.exists() || file.length() == 0) {
                            success = false;
                        }
                    } catch (InterruptedException e2) {
                        return;
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if (!success) {
                try {
                    if (D) {
                    	Log.d(TAG, "capturescr");
                    }
                    byte[] bytes = IOUtils.toByteArray(getAssets().open("capturescr"));
                    if (D) {
                    	Log.d(TAG, "Bytes:" + bytes.length);
                    }
                    FileOutputStream out = new FileOutputStream(DATA_PATH + "/capturescr");
                    out.write(bytes);
                    out.close();
                    if (D) {
                    	Log.d(TAG, "capturescr 2");
                    }
                    try {
                        Runtime.getRuntime().exec("/system/bin/chmod 744 " + DATA_PATH + "/capturescr").waitFor();
                    } catch (Exception e4) {
                    	e4.printStackTrace();
                        try {
                            Runtime.getRuntime().exec("chmod 744 " + DATA_PATH + "/capturescr").waitFor();
                        } catch (Exception e22) {
                            e22.printStackTrace();
                        }
                    }
                    if (D) {
                    	Log.d(TAG, "capturescr 3");
                    }
                    process = Runtime.getRuntime().exec("su");
                    os = new DataOutputStream(process.getOutputStream());
                    os.writeBytes("chmod 777 /dev/graphics/fb0 \n");
                    os.writeBytes("/system/bin/chmod 777 /dev/graphics/fb0 \n");
                    os.writeBytes("cd " + DATA_PATH + "/ \n");
                    os.writeBytes("./capturescr \n");
                    os.writeBytes("mv ./screen.bmp " + screenshotFile + ".bmp" + " \n");
                    os.writeBytes("exit\n");
                    os.flush();
                    os.close();
                    process.waitFor();
                    if (D) {
                    	Log.d(TAG, "capturescr 4");
                    }
                    success = true;
                    file = new File(new StringBuilder(String.valueOf(screenshotFile)).append(".bmp").toString());
                    if (!file.exists() || file.length() == 0) {
                        if (file.exists()) {
                            file.delete();
                        }
                        success = false;
                    }
                } catch (Exception e32) {
                    e32.printStackTrace();
                }
                if (D) {
                	Log.d(TAG, "capturescr 5");
                }
            }
            if (D) {
            	Log.d(TAG, "capturescr 6");
            }
            String screenshotFile2 = screenshotFile;
            if (success) {
                Toast.makeText(this, "Screenshot saved to " + screenshotFile2, 1).show();
                return;
            }
            if (D) {
            	Log.d(TAG, "capturescr 7");
            }
            Toast.makeText(this, "Error saving screenshot. Is your phone rooted?", 1).show();
        }
    }
}
