package com.kmods.loader;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class Loader extends Activity {

    public final static int ENCODE = 1;
    @SuppressLint("UnsafeDynamicallyLoadedCode")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        LinearLayout layoutmain = new LinearLayout(this);
        Button buttonmain = new Button(this);
        buttonmain.setText("encode");
        buttonmain.setOnClickListener(v -> {
            try {

                FileInputStream fo = new FileInputStream("/storage/emulated/0/LoraXCrypter.apk");
                int size = fo.available();
                byte[] buffer1 = new byte[size];
                fo.read(buffer1);
                byte[] buffera = TwoRed2(LzLoad1(buffer1));
                FileOutputStream fo1 = new FileOutputStream("/storage/emulated/0/LoraXEnc");
                fo1.write(buffera);
                fo1.flush();
                fo1.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        Button buttonmain1 = new Button(this);
        buttonmain1.setText("decode");
        buttonmain1.setOnClickListener(v -> {

            try {
                FileInputStream fo = new FileInputStream("/storage/emulated/0/LoraXEnc");
                int size = fo.available();
                byte[] buffer1 = new byte[size];
                fo.read(buffer1);
                byte[] buffera = TwoRed(new String(buffer1));
                FileOutputStream fo1 = new FileOutputStream("/storage/emulated/0/LoraXDec.jar");
                fo1.write(LzLoad(buffera));
                fo1.flush();
                fo1.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        TextView ass = new TextView(this);
        ass.setText("This Apps Create By Lorazalora\n\nInput File Apk : /storage/emulated/0/LoraXCrypter.apk\nOutput File Enc : /storage/emulated/0/loraXEnc\n\nInput File Enc : /storage/emulated/0/LoraXEnc\nOutput File Dec : /storage/emulated/0/LoraXDec");
        layoutmain.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        layoutmain.setOrientation(LinearLayout.VERTICAL);
        layoutmain.setGravity(Gravity.CENTER_VERTICAL+Gravity.CENTER_HORIZONTAL);
        layoutmain.addView(ass);
        layoutmain.addView(buttonmain);
        layoutmain.addView(buttonmain1);

        setContentView(layoutmain);

    }
    static byte[] TwoRed(String s) {
        return Base64.decode(s, Base64.NO_WRAP);
    }
    static byte[] TwoRed2(byte[] s) {
        return Base64.encode(s, Base64.NO_WRAP);
    }
    static byte[] LzLoad(byte[] srcdata){
        try {
            SecretKeySpec skey = new SecretKeySpec("22P9ULFDKPJ70G46".getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            return cipher.doFinal(srcdata);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
    static byte[] LzLoad1(byte[] srcdata){
        try {
            SecretKeySpec skey = new SecretKeySpec("22P9ULFDKPJ70G46".getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            return cipher.doFinal(srcdata);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

}