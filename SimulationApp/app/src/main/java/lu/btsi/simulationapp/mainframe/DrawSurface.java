package lu.btsi.simulationapp.mainframe;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import lu.btsi.simulationapp.objects.AllEntities;

/**
 * Created by cardosoken on 23/11/2016.
 */

public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback {

    AllEntities allCells = new AllEntities();

    // a reference to the drawing thread
    private DrawThread drawThread = null;
    private boolean firstDraw = true;
    // your application certainly needs some data model
    private Object dataModel;
    Timer timer = new Timer();
    int timerSpeed = 200;

    public DrawSurface(Context context) {
        super(context);
        init(context);
    }

    public DrawSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context)
    {
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        // make sure we get key events
        setFocusable(true);
        // in case your application needs one or more timers,
        // you have to put them here
        repaint();


    }

    public void startTimer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                repaint();
            }
        }, 0, timerSpeed);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // react on touch events
        System.err.println("There was touch event");
        
        //repaint();
        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0)
    {
        // do a first painting
        repaint();
    }

    public void repaint()
    {

        // post a task to the UI thread
        this.post(new Runnable() {
            @Override
            public void run() {
                // create a new drawThread
                drawThread = new DrawThread(getHolder(), getContext(), new Handler() {
                    @Override
                    public void handleMessage(Message m) {
                    }
                });
                // call the setter for the pointer to the model
                drawThread.setDataModel(dataModel);
                // start the thread and set the Model for the allCells
                drawThread.setDataModelCells(allCells);
                drawThread.start();
                invalidate();
            }
        });
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0)
    {
        // stop the drawThread properly
        boolean retry = true;
        while (retry)
        {
            try
            {
                // wait for it to finish
                drawThread.join();
                retry = false;
            }
            catch (InterruptedException e)
            {
                // ignore any error
            }
        }
        // set it to null, so that a new one can be created in case of a resume
        drawThread=null;
    }
    // load JSON data from Link
    public void setData(String JSONData) {

        allCells.loadFromJSONFile(true, JSONData, null);
    }
}
