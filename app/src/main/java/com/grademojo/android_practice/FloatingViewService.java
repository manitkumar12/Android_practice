package com.grademojo.android_practice;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


public class FloatingViewService extends Service {


    private WindowManager windowManager;

    private View floating_view;

    public FloatingViewService(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public  void onCreate(){


        super.onCreate();


        floating_view = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget,null);


         final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(

                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT

        );



        layoutParams.gravity = Gravity.TOP | Gravity.LEFT;

        layoutParams.x = 0;

        layoutParams.y = 100;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        windowManager.addView(floating_view, layoutParams);


        final View collapsedView = floating_view.findViewById(R.id.collapse_view);

        final View ExpandedView = floating_view.findViewById(R.id.expanded_container);

        ImageView closeButtonCollapsed = (ImageView) floating_view.findViewById(R.id.close_btn);

        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopSelf();

            }
        });

        ImageView play_button = (ImageView) floating_view.findViewById(R.id.play_btn);

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatingViewService.this, "playing song", Toast.LENGTH_SHORT).show();
            }
        });


        ImageView nextButton = (ImageView) floating_view.findViewById(R.id.next_btn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatingViewService.this, "Playing next song.", Toast.LENGTH_LONG).show();
            }
        });


        //Set the pause button.
        ImageView prevButton = (ImageView) floating_view.findViewById(R.id.prev_btn);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatingViewService.this, "Playing previous song.", Toast.LENGTH_LONG).show();
            }
        });


        //Set the close button
        ImageView closeButton = (ImageView) floating_view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedView.setVisibility(View.VISIBLE);
                ExpandedView.setVisibility(View.GONE);
            }
        });

        ImageView openButton = (ImageView) floating_view.findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FloatingViewService.this, Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                stopSelf();

            }
        });

        floating_view.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {


            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:

                        initialX = layoutParams.x;
                        initialY = layoutParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();


                        return true;

                    case MotionEvent.ACTION_UP:

                        int X_diff = (int)(event.getRawX()- initialTouchX);
                        int Ydiff = (int)(event.getRawY()- initialTouchY);

                        if (X_diff <10 && Ydiff <10)

                        {

                            if (isViewCollapsed())

                            {

                                collapsedView.setVisibility(View.GONE);
                                ExpandedView.setVisibility(View.VISIBLE);

                            }
                        }

                        return true;

                    case MotionEvent.ACTION_MOVE:

                        layoutParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        layoutParams.y = initialY + (int) (event.getRawY() - initialTouchY);

                        windowManager.updateViewLayout(floating_view, layoutParams);

                        return true;



                }


                return false;
            }
        });

    }

    private boolean isViewCollapsed() {
        return floating_view == null || floating_view.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floating_view != null) windowManager.removeView(floating_view);
    }

}
