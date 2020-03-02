package com.example.whatcolorisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatcolorisit.animations.ExtendButtons;
import com.example.whatcolorisit.animations.toggleMainFAB;
import com.example.whatcolorisit.animations.windows.WindowSettings;
import com.example.whatcolorisit.animations.windows.Window;
import com.example.whatcolorisit.animations.windows.WindowInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView tVTime, itColorOClock, background, tVInfoText;
    private View mContentView;
    public static FloatingActionButton FABMore, FABShare, FABSettings, FABInfo;
    private ScrollView sVSettings, sVInfo;
    private ImageButton btnCloseSettings, btnCloseInfo;
    private Spinner spinnerColorPaletteSize;

    public static final String SHARED_PREFERENCES = "sharedPreferences";

    private NameOfColor NOC;

    private boolean displayNameOfColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadColorNames();

        final WindowSettings windowSettings = new WindowSettings();
        final WindowInfo windowInfo = new WindowInfo();

        tVTime = findViewById(R.id.timeView);
        mContentView = findViewById(R.id.background);
        itColorOClock = findViewById(R.id.itscoloroclock);
        background = findViewById(R.id.background);

        FABMore = findViewById(R.id.floatingActionButton);
        FABShare = findViewById(R.id.fabshare);
        FABSettings = findViewById(R.id.fabSettings);
        FABInfo = findViewById(R.id.fabinfo);

        sVSettings = findViewById(R.id.scrollViewSettings);
        btnCloseSettings = findViewById(R.id.btnCloseSettings);
        spinnerColorPaletteSize = findViewById(R.id.spinnerColorPaletteSize);

        sVInfo = findViewById(R.id.scrollViewInfo);
        btnCloseInfo = findViewById(R.id.btnCloseInfo);
        tVInfoText = findViewById(R.id.textViewInformation);

        NOC = new NameOfColor();
        WindowSettings.setSettingsView(sVSettings);
        WindowInfo.setSettingsView(sVInfo);

        displayNameOfColor = Boolean.parseBoolean(getSavedData("displayName"));
        updateTime();

        Timer timer = new Timer();
        timer.schedule(refreshClock,1000,5);

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ExtendButtons.addView(FABShare);
        ExtendButtons.addView(FABSettings);
        ExtendButtons.addView(FABInfo);

        FABMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ExtendButtons.isVisible()) {
                    ExtendButtons.show();
                }else{
                    ExtendButtons.hide();
                }
            }
        });

        FABSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtendButtons.hide();
                FABMore.setEnabled(false);
                windowSettings.showWindow();
            }
        });

        FABShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri imageUri = bitmapToUri(combineBitmaps(screenShot(tVTime),screenShot(itColorOClock)));
                String shareText = itColorOClock.getText() + "\nYou want to know what color it is all the time? Get the App!";

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("*/*");
                startActivity(Intent.createChooser(shareIntent, "Share images to.."));
            }
        });

        FABInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtendButtons.hide();
                FABMore.setEnabled(false);
                windowInfo.showWindow();
            }
        });

        btnCloseSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowSettings.onClose();
            }
        });
        btnCloseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowInfo.onClose();
            }
        });

        spinnerColorPaletteSize.setSelection(getIndex(spinnerColorPaletteSize,getSavedData("ColorPaletteSize")));
        spinnerColorPaletteSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(spinnerColorPaletteSize.getSelectedItem());
                switch((String) spinnerColorPaletteSize.getSelectedItem()){
                    case "small":
                        colorPaletteSize = colorPaletteSizeSmall;
                        break;
                    case "large":
                        colorPaletteSize = colorPaletteSizeLarge;
                        break;
                }
                if(!getSavedData("ColorPaletteSize").equals(spinnerColorPaletteSize.getSelectedItem())) {
                    saveData("ColorPaletteSize", (String) spinnerColorPaletteSize.getSelectedItem());
                    NameOfColor.getColorNames().clear();
                    loadColorNames();
                    Toast.makeText(MainActivity.this, "Size of color palette changed to " + spinnerColorPaletteSize.getSelectedItem(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Window window : Window.getWindowArrayList()){
                    if(window.isVisible()) {
                        window.onClose();
                        return;
                    }
                }
                if(toggleMainFAB.isVisible()){
                    toggleMainFAB.setVisible(!toggleMainFAB.isVisible());
                    toggleMainFAB.hideFAB();
                }else{
                    toggleMainFAB.setVisible(!toggleMainFAB.isVisible());
                    toggleMainFAB.showFAB();
                }
            }
        });

        itColorOClock.setOnClickListener(listenerChangeNameDisplay);
        tVTime.setOnClickListener(listenerChangeNameDisplay);
    }

    View.OnClickListener listenerChangeNameDisplay = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            displayNameOfColor = !displayNameOfColor;
            saveData("displayName", displayNameOfColor+"");
            updateTime();
        }
    };

    @Override
    public void onBackPressed(){
        for(Window window : Window.getWindowArrayList()){
            if(window.isVisible()) {
                window.onClose();
                return;
            }
        }
        super.onBackPressed();
    }

    private void updateTime(){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                tVTime.setText(getFormattedTime());

                String str = String.format("#%02x%02x%02x",
                        getFraction(getTimeStamp(HOUR),24),
                        getFraction(getTimeStamp(MINUTE),60),
                        getFraction(getTimeStamp(SECOND),60));

                int currentColor = Color.parseColor(str);
                background.setBackgroundColor(currentColor);
                tVTime.setTextColor(getComplimentColor());
                tVTime.setBackgroundColor(currentColor);

                MainActivity.this.getWindow().setStatusBarColor(currentColor);

                itColorOClock.setText(String.format(getString(R.string.itscoloroclock),displayNameOfColor?NOC.getName(currentColor):str));
                itColorOClock.setTextColor(getComplimentColor());
                itColorOClock.setBackgroundColor(currentColor);

            }
        });
    }

    private static int getMainColor(){
        return getMainColor(new Date());
    }

    private static int getMainColor(Date date){
        return Color.parseColor(String.format("#%02x%02x%02x",
                getFraction(getTimeStamp(HOUR),24),
                getFraction(getTimeStamp(MINUTE),60),
                getFraction(getTimeStamp(SECOND),60)));
    }

    public static int getComplimentColor(){
        return getComplimentColor(getMainColor());
    }

    public static int getComplimentColor(int color) {
        // get existing colors
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int blue = Color.blue(color);
        int green = Color.green(color);

        // find compliments
        red = (~red) & 0xff;
        blue = (~blue) & 0xff;
        green = (~green) & 0xff;

        return Color.argb(alpha, red, green, blue);
    }

    private static String getFormattedTime(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(now);
    }

    private static Date getNow(){
        return new Date();
    }

    private static int getFraction(int num, int denum){
        return (num*255)/denum;
    }

    public static final int HOUR = 0;
    public static final int MINUTE = 1;
    public static final int SECOND = 2;

    private static int getTimeStamp(int partOfTime){
        SimpleDateFormat sdf = null;
        switch(partOfTime){
            case 0: sdf = new SimpleDateFormat("HH");
                break;
            case 1: sdf = new SimpleDateFormat("mm");
                break;
            case 2: sdf = new SimpleDateFormat("ss");
                break;
        }

        return Integer.parseInt(sdf.format(new Date()));
    }

    private TimerTask refreshClock = new TimerTask() {
        @Override
        public void run() {
            updateTime();

        }
    };

    public void saveData(String key, String value){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getSavedData(String key){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

    public static final int colorPaletteSizeSmall = 0;
    public static final int colorPaletteSizeLarge = 1;

    public static int colorPaletteSize = colorPaletteSizeSmall;

    private void loadColorNames(){
        switch(colorPaletteSize){
            case colorPaletteSizeSmall:
                colorPaletteS();
                break;
            case colorPaletteSizeLarge:
                colorPaletteL();
                break;
        }
    }

    private void colorPaletteS(){
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Black),0x00,0x00,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.White),0xff,0xff,0xff));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Red),0xff,0x00,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Green),0x00,0xff,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Blue),0x00,0x00,0xff));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Magenta),0xff,0x00,0xff));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Aqua),0x00,0xff,0xff));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Yellow),0xff,0xff,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Orange),0xff,0xae,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Lime),0xae,0xff,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MintCream),0x00,0xff,0xa0));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Purple),0xa0,0x00,0xff));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.TelekomMagenta),0xe2,0x00,0x74));
        //NameOfColor.getColorNames().add(new ColorName(getString(R.string.Gray),0x80,0x80,0x80));
        //NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightGray),0xb0,0xb0,0xb0));
        //NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkGray),0x5e,0x5e,0x5e));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkGreen),0x0a,0x7d,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkBlue),0x00,0x04,0x82));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkRed),0x82,0x00,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightGreen),0x82,0xff,0xa3));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightBlue),0x82,0x84,0xff));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Salmon),0xff,0x82,0x82));
    }

    private void colorPaletteL(){
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.AliceBlue),0xF0,0xF8,0xFF));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.AntiqueWhite),0xFA,0xEB,0xD7));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Aqua),0x00,0xFF,0xFF));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Aquamarine),0x7F,0xFF,0xD4));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Azure),0xF0,0xFF,0xFF));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Beige),0xF5,0xF5,0xDC));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Bisque),0xFF,0xE4,0xC4));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Black),0x00,0x00,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.BlanchedAlmond),0xFF,0xEB,0xCD));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Blue),0x00,0x00,0xFF));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.BlueViolet),0x8A,0x2B,0xE2));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Brown),0xA5,0x2A,0x2A));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.BurlyWood),0xDE,0xB8,0x87));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.CadetBlue),0x5F,0x9E,0xA0));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Chartreuse),0x7F,0xFF,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Chocolate),0xD2,0x69,0x1E));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Coral),0xFF,0x7F,0x50));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.CornflowerBlue),0x64,0x95,0xED));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Cornsilk),0xFF,0xF8,0xDC));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Crimson),0xDC,0x14,0x3C));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Cyan),0x00,0xFF,0xFF));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkBlue),0x00,0x00,0x8B));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkCyan),0x00,0x8B,0x8B));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkGoldenRod),0xB8,0x86,0x0B));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkGray),0xA9,0xA9,0xA9));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkGreen),0x00,0x64,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkKhaki),0xBD,0xB7,0x6B));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkMagenta),0x8B,0x00,0x8B));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkOliveGreen),0x55,0x6B,0x2F));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkOrange),0xFF,0x8C,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkOrchid),0x99,0x32,0xCC));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkRed),0x8B,0x00,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkSalmon),0xE9,0x96,0x7A));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkSeaGreen),0x8F,0xBC,0x8F));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkSlateBlue),0x48,0x3D,0x8B));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkSlateGray),0x2F,0x4F,0x4F));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkTurquoise),0x00,0xCE,0xD1));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DarkViolet),0x94,0x00,0xD3));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DeepPink),0xFF,0x14,0x93));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DeepSkyBlue),0x00,0xBF,0xFF));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DimGray),0x69,0x69,0x69));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.DodgerBlue),0x1E,0x90,0xFF));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.FireBrick),0xB2,0x22,0x22));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.FloralWhite),0xFF,0xFA,0xF0));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.ForestGreen),0x22,0x8B,0x22));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Fuchsia),0xFF,0x00,0xFF));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Gainsboro),0xDC,0xDC,0xDC));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.GhostWhite),0xF8,0xF8,0xFF));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Gold),0xFF,0xD7,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.GoldenRod),0xDA,0xA5,0x20));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Gray),0x80,0x80,0x80));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Green),0x00,0x80,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.GreenYellow),0xAD,0xFF,0x2F));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.HoneyDew),0xF0,0xFF,0xF0));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.HotPink),0xFF,0x69,0xB4));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.IndianRed),0xCD,0x5C,0x5C));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Indigo),0x4B,0x00,0x82));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Ivory),0xFF,0xFF,0xF0));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Khaki),0xF0,0xE6,0x8C));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Lavender),0xE6,0xE6,0xFA));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LavenderBlush),0xFF,0xF0,0xF5));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LawnGreen),0x7C,0xFC,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LemonChiffon),0xFF,0xFA,0xCD));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightBlue),0xAD,0xD8,0xE6));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightCoral),0xF0,0x80,0x80));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightCyan),0xE0,0xFF,0xFF));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightGoldenRodYellow),0xFA,0xFA,0xD2));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightGray),0xD3,0xD3,0xD3));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightGreen),0x90,0xEE,0x90));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightPink),0xFF,0xB6,0xC1));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightSalmon),0xFF,0xA0,0x7A));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightSeaGreen),0x20,0xB2,0xAA));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightSkyBlue),0x87,0xCE,0xFA));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightSlateGray),0x77,0x88,0x99));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightSteelBlue),0xB0,0xC4,0xDE));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LightYellow),0xFF,0xFF,0xE0));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Lime),0x00,0xFF,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.LimeGreen),0x32,0xCD,0x32));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Linen),0xFA,0xF0,0xE6));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Magenta),0xFF,0x00,0xFF));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Maroon),0x80,0x00,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MediumAquaMarine),0x66,0xCD,0xAA));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MediumBlue),0x00,0x00,0xCD));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MediumOrchid),0xBA,0x55,0xD3));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MediumPurple),0x93,0x70,0xDB));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MediumSeaGreen),0x3C,0xB3,0x71));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MediumSlateBlue),0x7B,0x68,0xEE));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MediumSpringGreen),0x00,0xFA,0x9A));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MediumTurquoise),0x48,0xD1,0xCC));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MediumVioletRed),0xC7,0x15,0x85));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MidnightBlue),0x19,0x19,0x70));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MintCream),0xF5,0xFF,0xFA));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.MistyRose),0xFF,0xE4,0xE1));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Moccasin),0xFF,0xE4,0xB5));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.NavajoWhite),0xFF,0xDE,0xAD));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Navy),0x00,0x00,0x80));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.OldLace),0xFD,0xF5,0xE6));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Olive),0x80,0x80,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.OliveDrab),0x6B,0x8E,0x23));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Orange),0xFF,0xA5,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.OrangeRed),0xFF,0x45,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Orchid),0xDA,0x70,0xD6));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.PaleGoldenRod),0xEE,0xE8,0xAA));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.PaleGreen),0x98,0xFB,0x98));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.PaleTurquoise),0xAF,0xEE,0xEE));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.PaleVioletRed),0xDB,0x70,0x93));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.PapayaWhip),0xFF,0xEF,0xD5));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.PeachPuff),0xFF,0xDA,0xB9));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Peru),0xCD,0x85,0x3F));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Pink),0xFF,0xC0,0xCB));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Plum),0xDD,0xA0,0xDD));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.PowderBlue),0xB0,0xE0,0xE6));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Purple),0x80,0x00,0x80));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Red),0xFF,0x00,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.RosyBrown),0xBC,0x8F,0x8F));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.RoyalBlue),0x41,0x69,0xE1));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.SaddleBrown),0x8B,0x45,0x13));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Salmon),0xFA,0x80,0x72));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.SandyBrown),0xF4,0xA4,0x60));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.SeaGreen),0x2E,0x8B,0x57));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.SeaShell),0xFF,0xF5,0xEE));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Sienna),0xA0,0x52,0x2D));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Silver),0xC0,0xC0,0xC0));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.SkyBlue),0x87,0xCE,0xEB));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.SlateBlue),0x6A,0x5A,0xCD));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.SlateGray),0x70,0x80,0x90));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Snow),0xFF,0xFA,0xFA));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.SpringGreen),0x00,0xFF,0x7F));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.SteelBlue),0x46,0x82,0xB4));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Tan),0xD2,0xB4,0x8C));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Teal),0x00,0x80,0x80));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.TelekomMagenta),0xe2,0x00,0x74));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Thistle),0xD8,0xBF,0xD8));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Tomato),0xFF,0x63,0x47));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Turquoise),0x40,0xE0,0xD0));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Violet),0xEE,0x82,0xEE));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Wheat),0xF5,0xDE,0xB3));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.White),0xFF,0xFF,0xFF));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.WhiteSmoke),0xF5,0xF5,0xF5));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.Yellow),0xFF,0xFF,0x00));
        NameOfColor.getColorNames().add(new ColorName(getString(R.string.YellowGreen),0x9A,0xCD,0x32));
    }

    public static FloatingActionButton getFABMore() {
        return FABMore;
    }

    private static int getIndex(Spinner spinner, String value){
        for(int i = 0; i < spinner.getCount(); i++)
            if(spinner.getItemAtPosition(i).toString().equals(value))
                return i;
        return 0;
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private Bitmap combineBitmaps(Bitmap time, Bitmap string){
        Bitmap result = Bitmap.createBitmap(time.getWidth(), time.getHeight()+string.getHeight(), time.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(time, 0f, 0f, null);
        canvas.drawBitmap(string, 0, time.getHeight(), null);
        return result;
    }

    private Uri bitmapToUri(Bitmap bitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }
}