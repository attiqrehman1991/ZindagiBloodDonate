/*
 * MIT License
 * Copyright (c) 2017. Attiq ur Rehman
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software. THE SOFTWARE IS PROVIDED
 * "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM,OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.zindagi.blood.donate.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.zindagi.blood.donate.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class Util {


    public static Map<String, Typeface> typefaceCache = new HashMap<>();

    public static void setTypeface(AttributeSet attrs, TextView textView) {
        Context context = textView.getContext();

        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.AnyTextView);
        String typefaceName = values.getString(R.styleable.AnyTextView_typeface);

        if (typefaceCache.containsKey(typefaceName)) {
            textView.setTypeface(typefaceCache.get(typefaceName));
        } else {
            Typeface typeface;
            try {

                typeface = Typeface.createFromAsset(textView.getContext().getAssets(), context.getString(R.string.assets_fonts_folder) + typefaceName);
            } catch (Exception e) {
                Log.v(context.getString(R.string.app_name), String.format(context.getString(R.string.typeface_not_found), typefaceName));
                return;
            }

            typefaceCache.put(typefaceName, typeface);
            textView.setTypeface(typeface);
        }
        values.recycle();
    }




	
	/*public static void setTypefaceArabic(AttributeSet attrs,  TextView textView) {
        Context context = textView.getContext();

        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.AnyTextView2);
        String typefaceName = values.getString(R.styleable.AnyTextView_typeface);

        if (typefaceCache.containsKey(typefaceName)) {
            textView.setTypeface(typefaceCache.get(typefaceName));
        } else {
            Typeface typeface;
            try {
                typeface = Typeface.createFromAsset(textView.getContext().getAssets(), context.getString(R.string.assets_fonts_folder) + MainActivity.ARABIC_FONT);
            } catch (Exception e) {
                Log.v(context.getString(R.string.app), String.format(context.getString(R.string.typeface_not_found), typefaceName));
                return;
            }

            typefaceCache.put(typefaceName, typeface);
            textView.setTypeface(typeface);
        }

        values.recycle();
    }

	public static void setTypefaceEnglish( TextView textView) {
		Context context = textView.getContext();

        *//*TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.AnyTextView);
        String typefaceName = values.getString(R.styleable.AnyTextView_typeface);*//*

      *//*  if (typefaceCache.containsKey(typefaceName)) {
            textView.setTypeface(typefaceCache.get(typefaceName));
        } else {*//*
        Typeface typeface;
		try {
			typeface = Typeface.createFromAsset(textView.getContext().getAssets(), context.getString(R.string.assets_fonts_folder) + MainActivity.ENGLISH_FONT);
		} catch (Exception e) {
			//Log.v(context.getString(R.string.app), String.format(context.getString(R.string.typeface_not_found), typefaceName));
			return;
		}

		//typefaceCache.put(typefaceName, typeface);
		textView.setTypeface(typeface);
        *//*}*//*

		//values.recycle();
	}*/
}