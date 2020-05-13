/*
 * Copyright 2020 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.uamp.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet

/**
 * A custom SeekBar implementation to show usage of View.setSystemGestureExclusionRects()
 * This is only an example and SeekBar handles this automatically on Android 10+
 */
class MySeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = android.R.attr.seekBarStyle
) : androidx.appcompat.widget.AppCompatSeekBar(context, attrs, defStyle) {
    private val gestureExclusionRects = mutableListOf<Rect>()

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (changed) {
            updateGestureExclusion()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        updateGestureExclusion()
    }

    private fun updateGestureExclusion() {
        // Skip this call if we're not running on Android 10+
        if (Build.VERSION.SDK_INT < 29) return

        // First, lets clear out any existing rectangles
        gestureExclusionRects.clear()

        // Now lets work out which areas should be excluded. For a SeekBar this will
        // be the bounds of the thumb drawable.
        thumb?.also { t ->
            gestureExclusionRects += t.copyBounds()
        }

        // Finally pass our updated list of rectangles to the system
        systemGestureExclusionRects = gestureExclusionRects
    }
}
