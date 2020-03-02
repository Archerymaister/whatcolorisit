package com.example.whatcolorisit;

import android.content.res.Resources;
import android.graphics.Color;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class NameOfColor extends MainActivity{
    private static ArrayList<ColorName> colorNames = new ArrayList<>();

    public NameOfColor(){
        loadNames();

    }

    public String getName(int color){
        float minDiff = Integer.MAX_VALUE;
        ColorName closestColor = null;
        try {
            for (ColorName cn : colorNames) {

                if (cn.getDistance(Color.valueOf(color)) < minDiff) {
                    minDiff = cn.getDistance(Color.valueOf(color));

                    closestColor = cn;
                }

            }
        }catch(ConcurrentModificationException e){
            System.out.println(e.getMessage());
        }

        if(closestColor == null){
            return "No Color found!";
        }
        return closestColor.getName();

    }

    public static ArrayList<ColorName> getColorNames() {
        return colorNames;
    }

    private void loadNames(){
        /*colorNames.add(new ColorName(getString(R.string.AliceBlue), 0xF0, 0xF8, 0xFF));
        colorNames.add(new ColorName("AntiqueWhite", 0xFA, 0xEB, 0xD7));
        colorNames.add(new ColorName("Aqua", 0x00, 0xFF, 0xFF));
        colorNames.add(new ColorName("Aquamarine", 0x7F, 0xFF, 0xD4));
        colorNames.add(new ColorName("Azure", 0xF0, 0xFF, 0xFF));
        colorNames.add(new ColorName("Beige", 0xF5, 0xF5, 0xDC));
        colorNames.add(new ColorName("Bisque", 0xFF, 0xE4, 0xC4));
        colorNames.add(new ColorName("Black", 0x00, 0x00, 0x00));
        colorNames.add(new ColorName("Blanched Almond", 0xFF, 0xEB, 0xCD));
        colorNames.add(new ColorName("Blue", 0x00, 0x00, 0xFF));
        colorNames.add(new ColorName("Blue Violet", 0x8A, 0x2B, 0xE2));
        colorNames.add(new ColorName("Brown", 0xA5, 0x2A, 0x2A));
        colorNames.add(new ColorName("Burly Wood", 0xDE, 0xB8, 0x87));
        colorNames.add(new ColorName("Cadet Blue", 0x5F, 0x9E, 0xA0));
        colorNames.add(new ColorName("Chartreuse", 0x7F, 0xFF, 0x00));
        colorNames.add(new ColorName("Chocolate", 0xD2, 0x69, 0x1E));
        colorNames.add(new ColorName("Coral", 0xFF, 0x7F, 0x50));
        colorNames.add(new ColorName("Cornflower Blue", 0x64, 0x95, 0xED));
        colorNames.add(new ColorName("Cornsilk", 0xFF, 0xF8, 0xDC));
        colorNames.add(new ColorName("Crimson", 0xDC, 0x14, 0x3C));
        colorNames.add(new ColorName("Cyan", 0x00, 0xFF, 0xFF));
        colorNames.add(new ColorName("Dark Blue", 0x00, 0x00, 0x8B));
        colorNames.add(new ColorName("Dark Cyan", 0x00, 0x8B, 0x8B));
        colorNames.add(new ColorName("Dark Golden Rod", 0xB8, 0x86, 0x0B));
        colorNames.add(new ColorName("Dark Gray", 0xA9, 0xA9, 0xA9));
        colorNames.add(new ColorName("Dark Green", 0x00, 0x64, 0x00));
        colorNames.add(new ColorName("Dark Khaki", 0xBD, 0xB7, 0x6B));
        colorNames.add(new ColorName("Dark Magenta", 0x8B, 0x00, 0x8B));
        colorNames.add(new ColorName("Dark Olive Green", 0x55, 0x6B, 0x2F));
        colorNames.add(new ColorName("Dark Orange", 0xFF, 0x8C, 0x00));
        colorNames.add(new ColorName("Dark Orchid", 0x99, 0x32, 0xCC));
        colorNames.add(new ColorName("Dark Red", 0x8B, 0x00, 0x00));
        colorNames.add(new ColorName("Dark Salmon", 0xE9, 0x96, 0x7A));
        colorNames.add(new ColorName("Dark Sea Green", 0x8F, 0xBC, 0x8F));
        colorNames.add(new ColorName("Dark Slate Blue", 0x48, 0x3D, 0x8B));
        colorNames.add(new ColorName("Dark Slate Gray", 0x2F, 0x4F, 0x4F));
        colorNames.add(new ColorName("Dark Turquoise", 0x00, 0xCE, 0xD1));
        colorNames.add(new ColorName("Dark Violet", 0x94, 0x00, 0xD3));
        colorNames.add(new ColorName("Deep Pink", 0xFF, 0x14, 0x93));
        colorNames.add(new ColorName("DeepSky Blue", 0x00, 0xBF, 0xFF));
        colorNames.add(new ColorName("Dim Gray", 0x69, 0x69, 0x69));
        colorNames.add(new ColorName("Dodger Blue", 0x1E, 0x90, 0xFF));
        colorNames.add(new ColorName("Fire Brick", 0xB2, 0x22, 0x22));
        colorNames.add(new ColorName("Floral White", 0xFF, 0xFA, 0xF0));
        colorNames.add(new ColorName("Forest Green", 0x22, 0x8B, 0x22));
        colorNames.add(new ColorName("Fuchsia", 0xFF, 0x00, 0xFF));
        colorNames.add(new ColorName("Gainsboro", 0xDC, 0xDC, 0xDC));
        colorNames.add(new ColorName("Ghost White", 0xF8, 0xF8, 0xFF));
        colorNames.add(new ColorName("Gold", 0xFF, 0xD7, 0x00));
        colorNames.add(new ColorName("Golden Rod", 0xDA, 0xA5, 0x20));
        colorNames.add(new ColorName("Gray", 0x80, 0x80, 0x80));
        colorNames.add(new ColorName("Green", 0x00, 0x80, 0x00));
        colorNames.add(new ColorName("Green Yellow", 0xAD, 0xFF, 0x2F));
        colorNames.add(new ColorName("Honey Dew", 0xF0, 0xFF, 0xF0));
        colorNames.add(new ColorName("Hot Pink", 0xFF, 0x69, 0xB4));
        colorNames.add(new ColorName("Indian Red", 0xCD, 0x5C, 0x5C));
        colorNames.add(new ColorName("Indigo", 0x4B, 0x00, 0x82));
        colorNames.add(new ColorName("Ivory", 0xFF, 0xFF, 0xF0));
        colorNames.add(new ColorName("Khaki", 0xF0, 0xE6, 0x8C));
        colorNames.add(new ColorName("Lavender", 0xE6, 0xE6, 0xFA));
        colorNames.add(new ColorName("Lavender Blush", 0xFF, 0xF0, 0xF5));
        colorNames.add(new ColorName("Lawn Green", 0x7C, 0xFC, 0x00));
        colorNames.add(new ColorName("Lemon Chiffon", 0xFF, 0xFA, 0xCD));
        colorNames.add(new ColorName("Light Blue", 0xAD, 0xD8, 0xE6));
        colorNames.add(new ColorName("Light Coral", 0xF0, 0x80, 0x80));
        colorNames.add(new ColorName("Light Cyan", 0xE0, 0xFF, 0xFF));
        colorNames.add(new ColorName("Light Golden Rod Yellow", 0xFA, 0xFA, 0xD2));
        colorNames.add(new ColorName("Light Gray", 0xD3, 0xD3, 0xD3));
        colorNames.add(new ColorName("Light Green", 0x90, 0xEE, 0x90));
        colorNames.add(new ColorName("Light Pink", 0xFF, 0xB6, 0xC1));
        colorNames.add(new ColorName("Light Salmon", 0xFF, 0xA0, 0x7A));
        colorNames.add(new ColorName("Light Sea Green", 0x20, 0xB2, 0xAA));
        colorNames.add(new ColorName("Light Sky Blue", 0x87, 0xCE, 0xFA));
        colorNames.add(new ColorName("Light Slate Gray", 0x77, 0x88, 0x99));
        colorNames.add(new ColorName("Light Steel Blue", 0xB0, 0xC4, 0xDE));
        colorNames.add(new ColorName("Light Yellow", 0xFF, 0xFF, 0xE0));
        colorNames.add(new ColorName("Lime", 0x00, 0xFF, 0x00));
        colorNames.add(new ColorName("Lime Green", 0x32, 0xCD, 0x32));
        colorNames.add(new ColorName("Linen", 0xFA, 0xF0, 0xE6));
        colorNames.add(new ColorName("Magenta", 0xFF, 0x00, 0xFF));
        colorNames.add(new ColorName("Maroon", 0x80, 0x00, 0x00));
        colorNames.add(new ColorName("Medium Aqua Marine", 0x66, 0xCD, 0xAA));
        colorNames.add(new ColorName("Medium Blue", 0x00, 0x00, 0xCD));
        colorNames.add(new ColorName("Medium Orchid", 0xBA, 0x55, 0xD3));
        colorNames.add(new ColorName("Medium Purple", 0x93, 0x70, 0xDB));
        colorNames.add(new ColorName("Medium Sea Green", 0x3C, 0xB3, 0x71));
        colorNames.add(new ColorName("Medium Slate Blue", 0x7B, 0x68, 0xEE));
        colorNames.add(new ColorName("Medium Spring Green", 0x00, 0xFA, 0x9A));
        colorNames.add(new ColorName("Medium Turquoise", 0x48, 0xD1, 0xCC));
        colorNames.add(new ColorName("Medium Violet Red", 0xC7, 0x15, 0x85));
        colorNames.add(new ColorName("Midnight Blue", 0x19, 0x19, 0x70));
        colorNames.add(new ColorName("Mint Cream", 0xF5, 0xFF, 0xFA));
        colorNames.add(new ColorName("Misty Rose", 0xFF, 0xE4, 0xE1));
        colorNames.add(new ColorName("Moccasin", 0xFF, 0xE4, 0xB5));
        colorNames.add(new ColorName("Navajo White", 0xFF, 0xDE, 0xAD));
        colorNames.add(new ColorName("Navy", 0x00, 0x00, 0x80));
        colorNames.add(new ColorName("Old Lace", 0xFD, 0xF5, 0xE6));
        colorNames.add(new ColorName("Olive", 0x80, 0x80, 0x00));
        colorNames.add(new ColorName("Olive Drab", 0x6B, 0x8E, 0x23));
        colorNames.add(new ColorName("Orange", 0xFF, 0xA5, 0x00));
        colorNames.add(new ColorName("Orange Red", 0xFF, 0x45, 0x00));
        colorNames.add(new ColorName("Orchid", 0xDA, 0x70, 0xD6));
        colorNames.add(new ColorName("Pale Golden Rod", 0xEE, 0xE8, 0xAA));
        colorNames.add(new ColorName("Pale Green", 0x98, 0xFB, 0x98));
        colorNames.add(new ColorName("Pale Turquoise", 0xAF, 0xEE, 0xEE));
        colorNames.add(new ColorName("Pale Violet Red", 0xDB, 0x70, 0x93));
        colorNames.add(new ColorName("Papaya Whip", 0xFF, 0xEF, 0xD5));
        colorNames.add(new ColorName("Peach Puff", 0xFF, 0xDA, 0xB9));
        colorNames.add(new ColorName("Peru", 0xCD, 0x85, 0x3F));
        colorNames.add(new ColorName("Pink", 0xFF, 0xC0, 0xCB));
        colorNames.add(new ColorName("Plum", 0xDD, 0xA0, 0xDD));
        colorNames.add(new ColorName("Powder Blue", 0xB0, 0xE0, 0xE6));
        colorNames.add(new ColorName("Purple", 0x80, 0x00, 0x80));
        colorNames.add(new ColorName("Red", 0xFF, 0x00, 0x00));
        colorNames.add(new ColorName("Rosy Brown", 0xBC, 0x8F, 0x8F));
        colorNames.add(new ColorName("Royal Blue", 0x41, 0x69, 0xE1));
        colorNames.add(new ColorName("Saddle Brown", 0x8B, 0x45, 0x13));
        colorNames.add(new ColorName("Salmon", 0xFA, 0x80, 0x72));
        colorNames.add(new ColorName("Sandy Brown", 0xF4, 0xA4, 0x60));
        colorNames.add(new ColorName("Sea Green", 0x2E, 0x8B, 0x57));
        colorNames.add(new ColorName("Sea Shell", 0xFF, 0xF5, 0xEE));
        colorNames.add(new ColorName("Sienna", 0xA0, 0x52, 0x2D));
        colorNames.add(new ColorName("Silver", 0xC0, 0xC0, 0xC0));
        colorNames.add(new ColorName("Sky Blue", 0x87, 0xCE, 0xEB));
        colorNames.add(new ColorName("Slate Blue", 0x6A, 0x5A, 0xCD));
        colorNames.add(new ColorName("Slate Gray", 0x70, 0x80, 0x90));
        colorNames.add(new ColorName("Snow", 0xFF, 0xFA, 0xFA));
        colorNames.add(new ColorName("Spring Green", 0x00, 0xFF, 0x7F));
        colorNames.add(new ColorName("Steel Blue", 0x46, 0x82, 0xB4));
        colorNames.add(new ColorName("Tan", 0xD2, 0xB4, 0x8C));
        colorNames.add(new ColorName("Teal", 0x00, 0x80, 0x80));
        colorNames.add(new ColorName("Telekom-Magenta",0xe2,0x00,0x74));
        colorNames.add(new ColorName("Thistle", 0xD8, 0xBF, 0xD8));
        colorNames.add(new ColorName("Tomato", 0xFF, 0x63, 0x47));
        colorNames.add(new ColorName("Turquoise", 0x40, 0xE0, 0xD0));
        colorNames.add(new ColorName("Violet", 0xEE, 0x82, 0xEE));
        colorNames.add(new ColorName("Wheat", 0xF5, 0xDE, 0xB3));
        colorNames.add(new ColorName("White", 0xFF, 0xFF, 0xFF));
        colorNames.add(new ColorName("White Smoke", 0xF5, 0xF5, 0xF5));
        colorNames.add(new ColorName("Yellow", 0xFF, 0xFF, 0x00));
        colorNames.add(new ColorName("Yellow Green", 0x9A, 0xCD, 0x32));*/

        //NameOfColor.getColorNames();

        //colorNames.add(new ColorName(getString(R.string.AliceBlue), 0xF0, 0xF8, 0xFF));
        /*colorNames.add(new ColorName(getString(R.string.AntiqueWhite), 0xFA, 0xEB, 0xD7));
        colorNames.add(new ColorName(getString(R.string.Aqua), 0x00, 0xFF, 0xFF));
        colorNames.add(new ColorName(getString(R.string.Aquamarine), 0x7F, 0xFF, 0xD4));
        colorNames.add(new ColorName(getString(R.string.Azure), 0xF0, 0xFF, 0xFF));
        colorNames.add(new ColorName(getString(R.string.Beige), 0xF5, 0xF5, 0xDC));
        colorNames.add(new ColorName(getString(R.string.Bisque), 0xFF, 0xE4, 0xC4));
        colorNames.add(new ColorName(getString(R.string.Black), 0x00, 0x00, 0x00));
        colorNames.add(new ColorName(getString(R.string.BlanchedAlmond), 0xFF, 0xEB, 0xCD));
        colorNames.add(new ColorName(getString(R.string.Blue), 0x00, 0x00, 0xFF));
        colorNames.add(new ColorName(getString(R.string.BlueViolet), 0x8A, 0x2B, 0xE2));
        colorNames.add(new ColorName(getString(R.string.Brown), 0xA5, 0x2A, 0x2A));
        colorNames.add(new ColorName(getString(R.string.BurlyWood), 0xDE, 0xB8, 0x87));
        colorNames.add(new ColorName(getString(R.string.CadetBlue), 0x5F, 0x9E, 0xA0));
        colorNames.add(new ColorName(getString(R.string.Chartreuse), 0x7F, 0xFF, 0x00));
        colorNames.add(new ColorName(getString(R.string.Chocolate), 0xD2, 0x69, 0x1E));
        colorNames.add(new ColorName(getString(R.string.Coral), 0xFF, 0x7F, 0x50));
        colorNames.add(new ColorName(getString(R.string.CornflowerBlue), 0x64, 0x95, 0xED));
        colorNames.add(new ColorName(getString(R.string.Cornsilk), 0xFF, 0xF8, 0xDC));
        colorNames.add(new ColorName(getString(R.string.Crimson), 0xDC, 0x14, 0x3C));
        colorNames.add(new ColorName(getString(R.string.Cyan), 0x00, 0xFF, 0xFF));
        colorNames.add(new ColorName(getString(R.string.DarkBlue), 0x00, 0x00, 0x8B));
        colorNames.add(new ColorName(getString(R.string.DarkCyan), 0x00, 0x8B, 0x8B));
        colorNames.add(new ColorName(getString(R.string.DarkGoldenRod), 0xB8, 0x86, 0x0B));
        colorNames.add(new ColorName(getString(R.string.DarkGray), 0xA9, 0xA9, 0xA9));
        colorNames.add(new ColorName(getString(R.string.DarkGreen), 0x00, 0x64, 0x00));
        colorNames.add(new ColorName(getString(R.string.DarkKhaki), 0xBD, 0xB7, 0x6B));
        colorNames.add(new ColorName(getString(R.string.DarkMagenta), 0x8B, 0x00, 0x8B));
        colorNames.add(new ColorName(getString(R.string.DarkOliveGreen), 0x55, 0x6B, 0x2F));
        colorNames.add(new ColorName(getString(R.string.DarkOrange), 0xFF, 0x8C, 0x00));
        colorNames.add(new ColorName(getString(R.string.DarkOrchid), 0x99, 0x32, 0xCC));
        colorNames.add(new ColorName(getString(R.string.DarkRed), 0x8B, 0x00, 0x00));
        colorNames.add(new ColorName(getString(R.string.DarkSalmon), 0xE9, 0x96, 0x7A));
        colorNames.add(new ColorName(getString(R.string.DarkSeaGreen), 0x8F, 0xBC, 0x8F));
        colorNames.add(new ColorName(getString(R.string.DarkSlateBlue), 0x48, 0x3D, 0x8B));
        colorNames.add(new ColorName(getString(R.string.DarkSlateGray), 0x2F, 0x4F, 0x4F));
        colorNames.add(new ColorName(getString(R.string.DarkTurquoise), 0x00, 0xCE, 0xD1));
        colorNames.add(new ColorName(getString(R.string.DarkViolet), 0x94, 0x00, 0xD3));
        colorNames.add(new ColorName(getString(R.string.DeepPink), 0xFF, 0x14, 0x93));
        colorNames.add(new ColorName(getString(R.string.DeepSkyBlue), 0x00, 0xBF, 0xFF));
        colorNames.add(new ColorName(getString(R.string.DimGray), 0x69, 0x69, 0x69));
        colorNames.add(new ColorName(getString(R.string.DodgerBlue), 0x1E, 0x90, 0xFF));
        colorNames.add(new ColorName(getString(R.string.FireBrick), 0xB2, 0x22, 0x22));
        colorNames.add(new ColorName(getString(R.string.FloralWhite), 0xFF, 0xFA, 0xF0));
        colorNames.add(new ColorName(getString(R.string.ForestGreen), 0x22, 0x8B, 0x22));
        colorNames.add(new ColorName(getString(R.string.Fuchsia), 0xFF, 0x00, 0xFF));
        colorNames.add(new ColorName(getString(R.string.Gainsboro), 0xDC, 0xDC, 0xDC));
        colorNames.add(new ColorName(getString(R.string.GhostWhite), 0xF8, 0xF8, 0xFF));
        colorNames.add(new ColorName(getString(R.string.Gold), 0xFF, 0xD7, 0x00));
        colorNames.add(new ColorName(getString(R.string.GoldenRod), 0xDA, 0xA5, 0x20));
        colorNames.add(new ColorName(getString(R.string.Gray), 0x80, 0x80, 0x80));
        colorNames.add(new ColorName(getString(R.string.Green), 0x00, 0x80, 0x00));
        colorNames.add(new ColorName(getString(R.string.GreenYellow), 0xAD, 0xFF, 0x2F));
        colorNames.add(new ColorName(getString(R.string.HoneyDew), 0xF0, 0xFF, 0xF0));
        colorNames.add(new ColorName(getString(R.string.HotPink), 0xFF, 0x69, 0xB4));
        colorNames.add(new ColorName(getString(R.string.IndianRed), 0xCD, 0x5C, 0x5C));
        colorNames.add(new ColorName(getString(R.string.Indigo), 0x4B, 0x00, 0x82));
        colorNames.add(new ColorName(getString(R.string.Ivory), 0xFF, 0xFF, 0xF0));
        colorNames.add(new ColorName(getString(R.string.Khaki), 0xF0, 0xE6, 0x8C));
        colorNames.add(new ColorName(getString(R.string.Lavender), 0xE6, 0xE6, 0xFA));
        colorNames.add(new ColorName(getString(R.string.LavenderBlush), 0xFF, 0xF0, 0xF5));
        colorNames.add(new ColorName(getString(R.string.LawnGreen), 0x7C, 0xFC, 0x00));
        colorNames.add(new ColorName(getString(R.string.LemonChiffon), 0xFF, 0xFA, 0xCD));
        colorNames.add(new ColorName(getString(R.string.LightBlue), 0xAD, 0xD8, 0xE6));
        colorNames.add(new ColorName(getString(R.string.LightCoral), 0xF0, 0x80, 0x80));
        colorNames.add(new ColorName(getString(R.string.LightCyan), 0xE0, 0xFF, 0xFF));
        colorNames.add(new ColorName(getString(R.string.LightGoldenRodYellow), 0xFA, 0xFA, 0xD2));
        colorNames.add(new ColorName(getString(R.string.LightGray), 0xD3, 0xD3, 0xD3));
        colorNames.add(new ColorName(getString(R.string.LightGreen), 0x90, 0xEE, 0x90));
        colorNames.add(new ColorName(getString(R.string.LightPink), 0xFF, 0xB6, 0xC1));
        colorNames.add(new ColorName(getString(R.string.LightSalmon), 0xFF, 0xA0, 0x7A));
        colorNames.add(new ColorName(getString(R.string.LightSeaGreen), 0x20, 0xB2, 0xAA));
        colorNames.add(new ColorName(getString(R.string.LightSkyBlue), 0x87, 0xCE, 0xFA));
        colorNames.add(new ColorName(getString(R.string.LightSlateGray), 0x77, 0x88, 0x99));
        colorNames.add(new ColorName(getString(R.string.LightSteelBlue), 0xB0, 0xC4, 0xDE));
        colorNames.add(new ColorName(getString(R.string.LightYellow), 0xFF, 0xFF, 0xE0));
        colorNames.add(new ColorName(getString(R.string.Lime), 0x00, 0xFF, 0x00));
        colorNames.add(new ColorName(getString(R.string.LimeGreen), 0x32, 0xCD, 0x32));
        colorNames.add(new ColorName(getString(R.string.Linen), 0xFA, 0xF0, 0xE6));
        colorNames.add(new ColorName(getString(R.string.Magenta), 0xFF, 0x00, 0xFF));
        colorNames.add(new ColorName(getString(R.string.Maroon), 0x80, 0x00, 0x00));
        colorNames.add(new ColorName(getString(R.string.MediumAquaMarine), 0x66, 0xCD, 0xAA));
        colorNames.add(new ColorName(getString(R.string.MediumBlue), 0x00, 0x00, 0xCD));
        colorNames.add(new ColorName(getString(R.string.MediumOrchid), 0xBA, 0x55, 0xD3));
        colorNames.add(new ColorName(getString(R.string.MediumPurple), 0x93, 0x70, 0xDB));
        colorNames.add(new ColorName(getString(R.string.MediumSeaGreen), 0x3C, 0xB3, 0x71));
        colorNames.add(new ColorName(getString(R.string.MediumSlateBlue), 0x7B, 0x68, 0xEE));
        colorNames.add(new ColorName(getString(R.string.MediumSpringGreen), 0x00, 0xFA, 0x9A));
        colorNames.add(new ColorName(getString(R.string.MediumTurquoise), 0x48, 0xD1, 0xCC));
        colorNames.add(new ColorName(getString(R.string.MediumVioletRed), 0xC7, 0x15, 0x85));
        colorNames.add(new ColorName(getString(R.string.MidnightBlue), 0x19, 0x19, 0x70));
        colorNames.add(new ColorName(getString(R.string.MintCream), 0xF5, 0xFF, 0xFA));
        colorNames.add(new ColorName(getString(R.string.MistyRose), 0xFF, 0xE4, 0xE1));
        colorNames.add(new ColorName(getString(R.string.Moccasin), 0xFF, 0xE4, 0xB5));
        colorNames.add(new ColorName(getString(R.string.NavajoWhite), 0xFF, 0xDE, 0xAD));
        colorNames.add(new ColorName(getString(R.string.Navy), 0x00, 0x00, 0x80));
        colorNames.add(new ColorName(getString(R.string.OldLace), 0xFD, 0xF5, 0xE6));
        colorNames.add(new ColorName(getString(R.string.Olive), 0x80, 0x80, 0x00));
        colorNames.add(new ColorName(getString(R.string.OliveDrab), 0x6B, 0x8E, 0x23));
        colorNames.add(new ColorName(getString(R.string.Orange), 0xFF, 0xA5, 0x00));
        colorNames.add(new ColorName(getString(R.string.OrangeRed), 0xFF, 0x45, 0x00));
        colorNames.add(new ColorName(getString(R.string.Orchid), 0xDA, 0x70, 0xD6));
        colorNames.add(new ColorName(getString(R.string.PaleGoldenRod), 0xEE, 0xE8, 0xAA));
        colorNames.add(new ColorName(getString(R.string.PaleGreen), 0x98, 0xFB, 0x98));
        colorNames.add(new ColorName(getString(R.string.PaleTurquoise), 0xAF, 0xEE, 0xEE));
        colorNames.add(new ColorName(getString(R.string.PaleVioletRed), 0xDB, 0x70, 0x93));
        colorNames.add(new ColorName(getString(R.string.PapayaWhip), 0xFF, 0xEF, 0xD5));
        colorNames.add(new ColorName(getString(R.string.PeachPuff), 0xFF, 0xDA, 0xB9));
        colorNames.add(new ColorName(getString(R.string.Peru), 0xCD, 0x85, 0x3F));
        colorNames.add(new ColorName(getString(R.string.Pink), 0xFF, 0xC0, 0xCB));
        colorNames.add(new ColorName(getString(R.string.Plum), 0xDD, 0xA0, 0xDD));
        colorNames.add(new ColorName(getString(R.string.PowderBlue), 0xB0, 0xE0, 0xE6));
        colorNames.add(new ColorName(getString(R.string.Purple), 0x80, 0x00, 0x80));
        colorNames.add(new ColorName(getString(R.string.Red), 0xFF, 0x00, 0x00));
        colorNames.add(new ColorName(getString(R.string.RosyBrown), 0xBC, 0x8F, 0x8F));
        colorNames.add(new ColorName(getString(R.string.RoyalBlue), 0x41, 0x69, 0xE1));
        colorNames.add(new ColorName(getString(R.string.SaddleBrown), 0x8B, 0x45, 0x13));
        colorNames.add(new ColorName(getString(R.string.Salmon), 0xFA, 0x80, 0x72));
        colorNames.add(new ColorName(getString(R.string.SandyBrown), 0xF4, 0xA4, 0x60));
        colorNames.add(new ColorName(getString(R.string.SeaGreen), 0x2E, 0x8B, 0x57));
        colorNames.add(new ColorName(getString(R.string.SeaShell), 0xFF, 0xF5, 0xEE));
        colorNames.add(new ColorName(getString(R.string.Sienna), 0xA0, 0x52, 0x2D));
        colorNames.add(new ColorName(getString(R.string.Silver), 0xC0, 0xC0, 0xC0));
        colorNames.add(new ColorName(getString(R.string.SkyBlue), 0x87, 0xCE, 0xEB));
        colorNames.add(new ColorName(getString(R.string.SlateBlue), 0x6A, 0x5A, 0xCD));
        colorNames.add(new ColorName(getString(R.string.SlateGray), 0x70, 0x80, 0x90));
        colorNames.add(new ColorName(getString(R.string.Snow), 0xFF, 0xFA, 0xFA));
        colorNames.add(new ColorName(getString(R.string.SpringGreen), 0x00, 0xFF, 0x7F));
        colorNames.add(new ColorName(getString(R.string.SteelBlue), 0x46, 0x82, 0xB4));
        colorNames.add(new ColorName(getString(R.string.Tan), 0xD2, 0xB4, 0x8C));
        colorNames.add(new ColorName(getString(R.string.Teal), 0x00, 0x80, 0x80));
        colorNames.add(new ColorName(getString(R.string.TelekomMagenta), 0xe2, 0x00, 0x74));
        colorNames.add(new ColorName(getString(R.string.Thistle), 0xD8, 0xBF, 0xD8));
        colorNames.add(new ColorName(getString(R.string.Tomato), 0xFF, 0x63, 0x47));
        colorNames.add(new ColorName(getString(R.string.Turquoise), 0x40, 0xE0, 0xD0));
        colorNames.add(new ColorName(getString(R.string.Violet), 0xEE, 0x82, 0xEE));
        colorNames.add(new ColorName(getString(R.string.Wheat), 0xF5, 0xDE, 0xB3));
        colorNames.add(new ColorName(getString(R.string.White), 0xFF, 0xFF, 0xFF));
        colorNames.add(new ColorName(getString(R.string.WhiteSmoke), 0xF5, 0xF5, 0xF5));
        colorNames.add(new ColorName(getString(R.string.Yellow), 0xFF, 0xFF, 0x00));
        colorNames.add(new ColorName(getString(R.string.YellowGreen), 0x9A, 0xCD, 0x32));*/

    }
}
