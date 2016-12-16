package lu.btsi.simulationapp.mainframe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.view.SurfaceHolder;

import java.util.Iterator;

import lu.btsi.simulationapp.DrawClasses.Color;
import lu.btsi.simulationapp.DrawClasses.Graphics;
import lu.btsi.simulationapp.objects.AllEntities;
import lu.btsi.simulationapp.objects.Entity;
import lu.btsi.simulationapp.objects.Gazelle;
import lu.btsi.simulationapp.objects.Tiger;

/**
 * Created by cardosoken on 23/11/2016.
 */

public class DrawThread extends Thread {

    private int rows = 20;
    private int cols = 20;
    //int backgroundColor, forgroundColor;
    lu.btsi.simulationapp.DrawClasses.Color backgroundColor = lu.btsi.simulationapp.DrawClasses.Color.WHITE;
    lu.btsi.simulationapp.DrawClasses.Color forgroundColor = lu.btsi.simulationapp.DrawClasses.Color.BLACK;
    // Field resolution
    private static final int myX = 500; // 1000
    private static final int myY = 500; // 800

    private int cellSize = 50;

    public int getCellSize() {
        return cellSize;
    }

    private AllEntities allCells = new AllEntities();

    /**
     * Handle to the surface manager object we interact with
     */
    private SurfaceHolder mSurfaceHolder;
    /**
     * Handle to the application context, used to e.g. fetch Drawables.
     */
    private Context mContext;
    /**
     * Message handler used by thread to interact with TextView
     */
    private Handler mHandler;

    // indicates weather we are running or not
    private boolean running = false;
    // Indicates weather we painted first time
    private boolean firstTimeDraw = true;

    private Object dataModel = new Object();

    public DrawThread(SurfaceHolder surfaceHolder,
                      Context context,
                      Handler handler) {


        // get handles to some important objects
        mSurfaceHolder = surfaceHolder;
        mHandler = handler;
        mContext = context;
    }

    public void setDataModel(Object dataModel) {
        this.dataModel = dataModel;
    }

    private void draw(Canvas c) {
        if (running) {
            // Make the background white. Else we get a nice black screen.
            Graphics g = new Graphics(c);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, c.getWidth(), c.getHeight());

            // Build field
            g.setColor(lu.btsi.simulationapp.DrawClasses.Color.BLACK);
            // Game Dimension field
            g.drawRect(0, 0, ((rows * cellSize)), (cols * cellSize));


            int x = 0, y = 0;
            for (int i = cellSize; i <= (rows * cellSize); i += cellSize) {
                for (int j = cellSize; j <= (cols * cellSize); j += cellSize) {
                    g.drawLine(i, 0, i, (cols * cellSize));
                    g.drawLine(0, j, (rows * cellSize), j);
                }
            }
            // Insert the new cells
            if (allCells.allEntities.size() > 0) {

                Iterator<Entity> iter = allCells.allEntities.iterator();
                while (iter.hasNext()) {
                    Entity e = null;
                    Entity entity = iter.next();
                    int cellX = (entity.getCellPointX() * cellSize);
                    int cellY = (entity.getCellPointY() * cellSize);
                    g.fillRect(cellX, cellY, cellSize, cellSize);
                    if (entity instanceof Tiger) {
                        g.setColor(Tiger.color);
                    }
                    if (entity instanceof Gazelle) {
                        g.setColor(Gazelle.color);
                    }
                }
            }
        }

    }


    public int getGameFieldSizeX() {
        return rows * cellSize;
    }

    public int getGameFieldSizeY() {
        return cols * cellSize;
    }

    public void setColsRows(int myCols, int myRows) {
        cols = myCols;
        rows = myRows;
    }

    public void setBackgroundColor(lu.btsi.simulationapp.DrawClasses.Color mybackgroundColor) {
        this.backgroundColor = mybackgroundColor;
    }

    public void setForgroundColor(lu.btsi.simulationapp.DrawClasses.Color myforgroundColor) {
        this.forgroundColor = myforgroundColor;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public Entity getTiger(Point readState) {
        Entity e = null;
        Iterator<Entity> iter = allCells.allEntities.iterator();
        while (iter.hasNext()) {
            Entity entity = iter.next();
            if (entity instanceof Tiger) {
                e = entity;
            }
        }
        return e;
    }

    public Entity getGazelle() {
        Entity e = null;
        Iterator<Entity> iter = allCells.allEntities.iterator();
        while (iter.hasNext()) {
            Entity entity = iter.next();
            if (entity instanceof Gazelle) {
                e = entity;
            }
        }
        return e;
    }

    public void doSteps() {
        if (allCells.allEntities.size() > 0) {
            Iterator<Entity> iter = allCells.allEntities.iterator();
            while (iter.hasNext()) {
                Entity entity = iter.next();
                if (entity instanceof Tiger) {
                    if (getGazelle() != null) {
                        if (entity.getCellPointX() == (rows - 1)) {
                            entity.resetCellPointX(0);
                        }
                        if (entity.getCellPointY() == (cols - 1)) {
                            entity.resetCellPointY(0);
                        }
                        entity.doStep(getGazelle());
                    }
                }
                if (entity instanceof Gazelle) {
                    // The old one the read state
                    Point readState = entity.getEntityPoint();
                    if (entity.getCellPointX() == (rows - 1)) {
                        entity.resetCellPointX(0);
                    }
                    if (entity.getCellPointY() == (cols - 1)) {
                        entity.resetCellPointY(0);
                    }
                    if (getTiger(readState) != null) {
                        // Get the write sate
                        Point writeState = entity.doStep(getTiger(readState));
                        if (readState.equals(getTiger(readState).getEntityPoint())) {
                            System.err.println("Cell was eaten");
                            iter.remove();
                        } else {
                            entity.setNewPoint(writeState);
                        }
                    }

                }

            }
        }

    }


    @Override
    public void run() {
        Canvas c = null;
        try {
            // get the surface
            c = mSurfaceHolder.lockCanvas(null);
            synchronized (mSurfaceHolder) {
                if (c != null) {
                    draw(c);
                }
            }
        } finally {
            // do this in a finally so that if an exception is thrown
            // during the above, we don't leave the surface in an
            // inconsistent state
            if (c != null) {
                mSurfaceHolder.unlockCanvasAndPost(c);
            }
            running = false;
        }
    }

    @Override
    public void start() {
        running = true;
        super.start();
        doSteps();

    }

    public boolean isRunning() {
        return running;
    }

    public boolean isFirstTimePainting() {
        return firstTimeDraw;
    }

    public void setDataModelCells(AllEntities dataModelCells) {
        this.allCells = dataModelCells;
    }
}
