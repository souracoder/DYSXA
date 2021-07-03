package com.subhaaspa.dysxa;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.vision.digitalink.DigitalInkRecognition;
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModel;
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModelIdentifier;
import com.google.mlkit.vision.digitalink.DigitalInkRecognizer;
import com.google.mlkit.vision.digitalink.DigitalInkRecognizerOptions;
import com.google.mlkit.vision.digitalink.Ink;

public class InkView extends View {

    DigitalInkRecognitionModelIdentifier modelIdentifier = null;
    DigitalInkRecognitionModel model;
    private Canvas drawCanvas;
    private Path currentStroke;
    private Paint currentStrokePaint;
    public String recognizedObject = "";
    float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
    private int STROKE_WIDTH = 20;
    private float left,top,right,bottom;

    Ink.Builder inkBuilder = Ink.builder();
    Ink.Stroke.Builder strokeBuilder;


    public InkView(Context context) {
        super(context);
    }

    public InkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        drawCanvas = new Canvas();
        currentStroke = new Path();
        currentStrokePaint = new Paint();


        //drawPath.drawPaint = new Paint();
        currentStrokePaint.setColor(Color.BLACK);
        currentStrokePaint.setAntiAlias(true);
        currentStrokePaint.setStrokeWidth(STROKE_WIDTH);
        currentStrokePaint.setStyle(Paint.Style.STROKE);
        currentStrokePaint.setStrokeJoin(Paint.Join.ROUND);
        currentStrokePaint.setStrokeCap(Paint.Cap.ROUND);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float w = canvas.getWidth();
        float h = canvas.getHeight();


        currentStrokePaint.setStyle(Paint.Style.STROKE);
        currentStrokePaint.setStrokeWidth(20);

        canvas.drawRect(0,0,w,h,currentStrokePaint);

        currentStrokePaint.setStrokeWidth(STROKE_WIDTH);
        canvas.drawPath(currentStroke,currentStrokePaint);

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;



    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        long t = System.currentTimeMillis();
        //Toast.makeText(getApplicationContext(),)
        switch(event.getAction()){

            case MotionEvent.ACTION_DOWN:
                //currentStroke.moveTo(x,y);
                touchStart(x,y);
                strokeBuilder = Ink.Stroke.builder();
                strokeBuilder.addPoint(Ink.Point.create(x, y, t));

                break;
            case MotionEvent.ACTION_MOVE:
                //currentStroke.lineTo(x,y);
                touchMove(x,y);
                strokeBuilder.addPoint(Ink.Point.create(x, y, t));
                break;
            case MotionEvent.ACTION_UP:
                currentStroke.lineTo(x,y);
                drawCanvas.drawPath(currentStroke,currentStrokePaint);
                //currentStroke.reset();
                strokeBuilder.addPoint(Ink.Point.create(x, y, t));
                inkBuilder.addStroke(strokeBuilder.build());
                strokeBuilder = null;
                break;
            default :
                return false;


        }

        invalidate();
        return true;

    }



    public void drawCircle() {

        // Calculate the rectangle bounds of path
        Path sourcePath = new Path(currentStroke);
        RectF rectF = new RectF();
        sourcePath.computeBounds(rectF, true);
        currentStroke.reset();
        currentStroke.addOval(rectF, Path.Direction.CCW);
        invalidate();

        //You can get width and height by calling rectF.width(), rectF.height()
    }
        public void drawRectangle(){

            // Calculate the rectangle bounds of path

            Path sourcePath = new Path(currentStroke);
            RectF rectF = new RectF();
            sourcePath.computeBounds(rectF, true);
            currentStroke.reset();
            currentStroke.addRect(rectF, Path.Direction.CCW);
            invalidate();



        //return rectF;
    }

    // Call this each time there is a new event.
//    public void addNewTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//        long t = System.currentTimeMillis();
//
//        // If your setup does not provide timing information, you can omit the
//        // third paramater (t) in the calls to Ink.Point.create
//        int action = event.getActionMasked();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                strokeBuilder = Ink.Stroke.builder();
//                strokeBuilder.addPoint(Ink.Point.create(x, y, t));
//                break;
//            case MotionEvent.ACTION_MOVE:
//                strokeBuilder.addPoint(Ink.Point.create(x, y, t));
//                break;
//            case MotionEvent.ACTION_UP:
//                strokeBuilder.addPoint(Ink.Point.create(x, y, t));
//                inkBuilder.addStroke(strokeBuilder.build());
//                strokeBuilder = null;
//                break;
//        }
//
//
//    }



    public void initInkRecognition(String lang_model, Context context){
        try {
            modelIdentifier =
                    DigitalInkRecognitionModelIdentifier.fromLanguageTag(lang_model);
        } catch (MlKitException e) {
            // language tag failed to parse, handle error.
            Toast.makeText(context,"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        if (modelIdentifier == null) {
            // no model was found, handle error.
        }
        model = DigitalInkRecognitionModel.builder(modelIdentifier).build();

        RemoteModelManager remoteModelManager = RemoteModelManager.getInstance();

        //if (remoteModelManager.isModelDownloaded(model));

        remoteModelManager
                .download(model, new DownloadConditions.Builder().build())
                .addOnSuccessListener(aVoid -> Toast.makeText(context,"Download success",Toast.LENGTH_LONG).show())
                .addOnFailureListener(
                        e -> Toast.makeText(context,"Error"+e,Toast.LENGTH_LONG).show());
    }

    public void recognizeObject( Context context ){

        Ink ink = inkBuilder.build();
        String letter = "";
        DigitalInkRecognizer recognizer = DigitalInkRecognition.getClient(
                DigitalInkRecognizerOptions.builder(model).build());

        String text;

        recognizer.recognize(ink)
                .addOnSuccessListener(
                        // `result` contains the recognizer's answers as a RecognitionResult.
                        // Logs the text from the top candidate.

                        result -> identifyLetter(result.getCandidates().get(0).getText(),context) )

                .addOnFailureListener(
                        // e -> Log.e(TAG, "Error during recognition: " + e));
                        e-> Toast.makeText(context,"Error: "+e, Toast.LENGTH_LONG));


    }


    public Ink getInkInstance(){
        Ink ink = inkBuilder.build();
        return ink;
    }

    private void touchMove(float x, float y){
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y-mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
        {
           currentStroke.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
           //currentStroke.addCircle(mX,mY,(mY-mX), Path.Direction.CCW);
            //currentStroke.quadTo(mX,mY,(x+mX),(y+mY));
            mX = x;
            mY = y;

        }
    }

    private void touchStart(float x, float y){
        mX = x;
        mY = y;
        //currentStroke.reset();
        currentStroke.moveTo(x,y);
    }

    public void clear(){
        drawCanvas.drawColor(Color.WHITE);
        currentStroke.reset();
        inkBuilder = Ink.builder();
        invalidate();
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void drawBoundRectangle(){
       // drawCanvas.drawRect(drawCanvas.getWid,currentStrokePaint);
      //  Rect rect = this.getClipBounds();

       // drawCanvas.drawRect(rect,currentStrokePaint);

    }

    private void identifyLetter(String result, Context context){


           recognizedObject = result;
        //Toast.makeText(context, "Hello:"+result,Toast.LENGTH_LONG).show();

    }

    public String getRecognizedObject(){
        return recognizedObject;
    }

}
