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

package com.zindagi.blood.donate.base.helps;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils.TruncateAt;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zindagi.blood.donate.R;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class UIHelper {

    public static void showLongToastInCenter(Context ctx, int messageId) {
        Toast toast = Toast.makeText(ctx, messageId, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showLongToastInCenter(Context ctx, String message) {
        message = (message != null && message.length() > 0) ? message : "";
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * @param ctx
     * @param message
     * @param status  1   Success
     *                2   Error
     *                3   Warning
     */
    public static void showShortToastInCenter(Context ctx, String message, int status) {
        if (status == 1)
            Toasty.success(ctx, message, Toast.LENGTH_SHORT, true).show();
        else if (status == 2)
            Toasty.error(ctx, message, Toast.LENGTH_SHORT, true).show();
        else if (status == 3)
            Toasty.warning(ctx, message, Toast.LENGTH_SHORT, true).show();
    }


    public static boolean isNullOrBlank(String s) {
        return (s == null || s.trim().equals(""));
    }

    public static void showLongToastInLeftTopCenter(Context ctx, String message) {
        message = (message != null && message.length() > 0) ? message : "";
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 100);
        toast.getView().setBackgroundColor(Color.WHITE);
        TextView v = (TextView) toast.getView().findViewById(
                android.R.id.message);
        v.setTextColor(Color.BLACK);
        toast.setDuration(toast.LENGTH_LONG);
        toast.show();
    }

    public static void showToast(Context ctx, String message) {
        message = (message != null && message.length() > 0) ? message : "";
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * @param ctx
     * @param message
     * @param status  1   Success
     *                2   Error
     *                3   Warning
     */
    public static void showToast(Context ctx, String message, int status) {
        if (status == 1)
            Toasty.success(ctx, message, Toast.LENGTH_SHORT, true).show();
        else if (status == 2)
            Toasty.error(ctx, message, Toast.LENGTH_SHORT, true).show();
        else if (status == 3)
            Toasty.warning(ctx, message, Toast.LENGTH_SHORT, true).show();
//
//
//
//        message = (message != null && message.length() > 0) ? message : "" ;
//        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
    }

    public static void showConnectionFailedToast(Context ctx) {
        //showLongToastInCenter(ctx, R.string.msg_connection_failed);
    }

    public static void showConnectionErrorToast(Context ctx) {
        //showLongToastInCenter(ctx, R.string.msg_connection_error);
    }

    public static void showSoftKeyboard(Context context, EditText editText) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

    }

    public static void hideSoftKeyboard(Context context, EditText editText) {

        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

    }

    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public static boolean isConnected(Object context, boolean showDialogBox) {
        boolean HaveConnectedWifi = false;
        boolean HaveConnectedMobile = false;
        Context localContextObject = (Context) context;
        ConnectivityManager cm = (ConnectivityManager) localContextObject.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    HaveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    HaveConnectedMobile = true;
        }
        if (!HaveConnectedMobile && !HaveConnectedWifi && showDialogBox) {

            //showErrorDialog(localContextObject,localContextObject.getString(R.string.no_network_access),localContextObject.getString(R.string.internet_connection_error));
        }

        //showToast(context, context.getString(R.string.no_network_access));
        //showErrorDialog(context,context.getString(R.string.no_network_access),

	/*	else if(!HaveConnectedMobile && !HaveConnectedWifi && displayCacheMsg)
            showToast(context, context.getString(R.string.display_date_from_cache));*/
        return HaveConnectedWifi || HaveConnectedMobile;
    }

    public static void showErrorDialog(Context context, String message, String title) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(
                        context.getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        builder.setView(null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertDialog(int title, int message,
                                       Context context, DialogInterface.OnClickListener onClickListener) {

        String titleS = context.getString(title);
        String messageS = context.getString(message);
        showAlertDialog(titleS, messageS, context, onClickListener);
    }


    public static void showAlertDialog(String title, String message,
                                       Context context, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle(title).setCancelable(false)
                .setNegativeButton(R.string.ok, onClickListener);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showAlertOptionDialog(String message, String textNeg, String textPos, CharSequence title,
                                             Context context) {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle(title).setCancelable(true)
				.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
*/
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        dialog.cancel();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.cancel();
                        break;
                }
            }
        };

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setMessage(message).setPositiveButton(textPos, dialogClickListener)
                .setNegativeButton(textNeg, dialogClickListener).show();
    }

    public static void showOptionAlertDialog(CharSequence title,
                                             final CharSequence[] itemsCollection, Context context,
                                             final IMessage imessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final CharSequence[] items = itemsCollection;
        int selectedId = -1;

        builder.setSingleChoiceItems(items, selectedId,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // TODO Auto-generated method stub
                        switch (item) {
                            case 0: {
                                imessage.messageReceived(items[item].toString());

                            }
                            break;
                            case 1: {
                                imessage.messageReceived(items[item].toString());

                            }

                            break;
                        }

                    }
                });

        builder.setCancelable(true).setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public static void showOptionCitiesAlertDialog(CharSequence title,
                                                   final List<String> itemsCollection, Context context,
                                                   final IMessage imessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final CharSequence[] items = itemsCollection.toArray(new CharSequence[itemsCollection.size()]);
        int selectedId = -1;

        builder.setSingleChoiceItems(items, selectedId,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // TODO Auto-generated method stub

//						switch (item) {
//						case 0: {
                        imessage.messageReceived(items[item].toString());

//						}
//							break;
//						case 1: {
//							imessage.messageReceived(items[item].toString());

//						}
//
//							break;
//						}

                    }
                });

        builder.setCancelable(true).setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public static Rect locateView(View v) {
        int[] loc_int = new int[2];
        if (v == null)
            return null;
        try {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe) {
            // Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }

    public static void textMarquee(TextView txtView) {
        // Use this to marquee Textview inside listview

        txtView.setEllipsize(TruncateAt.END);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static int getScreenWidth(Activity ctx) {
        Display display = ctx.getWindowManager().getDefaultDisplay();

        if (OSHelper.hasHoneycombMR2()) {
            Point size = new Point();
            display.getSize(size);
            return size.x;
        } else {
            return display.getWidth();
        }
    }

    public static void animateRise(final ViewGroup mLayout) {

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(250);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        animation.setDuration(500);
        set.addAnimation(animation);

        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLayout.setVisibility(View.INVISIBLE);
            }
        });

        mLayout.startAnimation(set);

    }

    public static void animateFall(ViewGroup mLayout) {

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(250);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(1000);
        set.addAnimation(animation);
        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(500);
        set.addAnimation(animation);

        mLayout.startAnimation(set);

    }

    public static void animateLayoutSlideDown(ViewGroup mLayout) {

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(250);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(550);
        set.addAnimation(animation);
        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(150);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.25f);
        mLayout.setLayoutAnimation(controller);

    }

    public static void animateLayoutSlideToRight(ViewGroup mLayout) {

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(750);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        animation.setDuration(750);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.25f);
        mLayout.setLayoutAnimation(controller);

    }

    public static void animateLayoutSlideFromRight(ViewGroup mLayout) {

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(750);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        animation.setDuration(750);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.25f);
        mLayout.setLayoutAnimation(controller);

    }

    public static void animateLayoutSlideToLeft(ViewGroup mLayout) {

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(750);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        animation.setDuration(750);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.25f);
        mLayout.setLayoutAnimation(controller);

    }

    public static void animateFromRight(ViewGroup mLayout) {

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(250);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(500);
        set.addAnimation(animation);

        mLayout.startAnimation(set);

    }

    public static void animateToRight(ViewGroup mLayout) {

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(250);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(500);
        set.addAnimation(animation);

        mLayout.startAnimation(set);

    }

}
