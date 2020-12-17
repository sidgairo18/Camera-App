# Camera-App
This is a basic camera app built for android using CameraX.
I had been looking online and on opensource code for basic camera apps, but most of them were either too complicated or used deprecated (legacy code).  

The new [CameraX api](https://developer.android.com/training/camerax) from android, is an Android Jetpack library makes camera development very easy, and is a wrapper around [Camera2](https://developer.android.com/reference/android/hardware/camera2/package-summary). So one gets most of the functionality from Camera2 in a much simpler manner, which is sufficient for most use cases.


## Current Features
```
* Take Picture
* Tap to focus
* Draw on Preview
* View Images
* Zoom Feature for View Images
* Play sound after image capture
```

## Code snippets

### Touch to Focus
This is mostly based on [this](https://proandroiddev.com/android-camerax-tap-to-focus-pinch-to-zoom-zoom-slider-eb88f3aa6fc6) implementation, but adapted for *PreviewView*

XML
```
<androidx.camera.view.PreviewView
    android:id="@+id/viewFinder"
    android:layout_width="match_parent"
    android:layout_height="match_parent" 
/>
```

Code
```
viewFinder.setOnTouchListener { _, event ->
    if (event.action != MotionEvent.ACTION_UP) {
        return@setOnTouchListener true
    }

    val factory = viewFinder.getMeteringPointFactory()
        val point = factory.createPoint(event.x, event.y)
        val action = FocusMeteringAction.Builder(point).build()
        camera.cameraControl.startFocusAndMetering(action)

        return@setOnTouchListener true
}
```

### Draw Rectangle

XML
```
<com.example.camera.RectOverlay
    android:id="@+id/rect_overlay"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
/>
```
Code

RectOverlay Class
```
class RectOverlay constructor(context: Context?, attributeSet: AttributeSet?) :
        View(context, attributeSet) {

    private val rectBounds: MutableList<RectF> = mutableListOf()
    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context!!, android.R.color.holo_green_light)
        strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Pass it a list of RectF (rectBounds)
        rectBounds.forEach { canvas.drawRect(it, paint) }
    }

    fun drawRectBounds(rectBounds: List<RectF>) {
        this.rectBounds.clear()
        this.rectBounds.addAll(rectBounds)
        invalidate()
    }
}

```

Drawing the rectangle:
```
val focusRects = listOf(RectF(event.x-rectSize, event.y-rectSize, event.x+rectSize, event.y+rectSize))
rect_overlay.post { rect_overlay.drawRectBounds(focusRects) }
```

### Image Capture sound
Code:
```
val sound = MediaActionSound()
sound.play(MediaActionSound.SHUTTER_CLICK)
```

For the **zoom** functionality in view images, have used **[PhotoView](https://github.com/chrisbanes/PhotoView)** which works really well.

## TODO
```
* Video Recording
* Zoom ability in camera.
* Rotaion
* Flash
```

**Note 1:** Parts of this code have been referrenced from open tutorials and resources which are linked at the bottom.
**Note 2:** But this bundles the entire code in the form of an app which works.

## References
* [Getting started with CameraX](https://codelabs.developers.google.com/codelabs/camerax-getting-started) (a google colab notebook tutorial).
* [Android CameraX: Preview, Analyze, Capture.](https://proandroiddev.com/android-camerax-preview-analyze-capture-1b3f403a9395)
* [Android CameraX: Control and Query the Camera.](https://proandroiddev.com/android-camerax-tap-to-focus-pinch-to-zoom-zoom-slider-eb88f3aa6fc6)
* [Gist: camerax-tap-to-focus](https://gist.github.com/husaynhakeem/1eec93bc18ff863ae84c0acb6d406ac8#file-camerax_tap_to_focus-kt)
* [Stackoverflow: PreviewView focus](https://stackoverflow.com/a/60585382)
* [Stackoverflow: How to use manual focus in Android CameraX](https://stackoverflow.com/questions/59136897/how-to-use-manual-focus-in-android-camerax)
* [Stackoverflow: How to draw on previewview](https://stackoverflow.com/questions/63090795/how-to-draw-on-previewview)
* [PhotoView](https://github.com/chrisbanes/PhotoView)
