package com.bethena.walls.starry_sky

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.SurfaceHolder.Callback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bethena.base_wall.BaseConfigFragment
import com.bethena.base_wall.colorpicker.ColorItem
import com.bethena.base_wall.colorpicker.ColorPickerAdapter
import com.bethena.base_wall.utils.ColorUtil
import com.bethena.base_wall.utils.LogUtil
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.Slider


class StarrySkyConfigFragment : BaseConfigFragment(), Slider.OnChangeListener,
    Slider.OnSliderTouchListener {

    private val TAG = "StarrySkyConfigFragment"

    lateinit var engineHandler: StarrySkyEngineHandler
    lateinit var surfaceView: SurfaceView
    var isSliderTouching = false
    lateinit var bottomDrawerBehaviorColor: BottomSheetBehavior<View>
    lateinit var bottomDrawerBehaviorEqualizer: BottomSheetBehavior<View>
    var slider_star_num: Slider? = null
    var slider_speed: Slider? = null
    var slider_mash: Slider? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        engineHandler = StarrySkyEngineHandler(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun layoutId(): Int {
        return R.layout.fragment_starry_sky_config
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<View>()
        surfaceView = view.findViewById(R.id.surfaceView)
        surfaceView.holder.addCallback(object : Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                //这里会调用两次，不知道为什么
                LogUtil.d("surfaceView surfaceCreated")
                engineHandler.surfaceCreated(holder)
                initSettingParams(view)
                initColorAdapter(view)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {

            }
        })

        initDrawer(view)

        var bar = view.findViewById<BottomAppBar>(R.id.bar)

        slider_star_num = view.findViewById(R.id.slider_star_num)
        slider_speed = view.findViewById(R.id.slider_speed)
        slider_mash = view.findViewById(R.id.slider_mash)

        initBtnCheck(view)

        (activity as AppCompatActivity?)!!.setSupportActionBar(bar)


    }

    fun initDrawer(view: View) {
        val bottomDrawerColor: View = view.findViewById(R.id.bottom_drawer_color)
        bottomDrawerBehaviorColor = BottomSheetBehavior.from(bottomDrawerColor)
        bottomDrawerBehaviorColor.setUpdateImportantForAccessibilityOnSiblings(true)
        bottomDrawerBehaviorColor.state = BottomSheetBehavior.STATE_HIDDEN

        bottomDrawerBehaviorColor.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        val bottomDrawerEqualizer: View = view.findViewById(R.id.bottom_drawer_equalizer)
        bottomDrawerBehaviorEqualizer = BottomSheetBehavior.from(bottomDrawerEqualizer)
        bottomDrawerBehaviorEqualizer.setUpdateImportantForAccessibilityOnSiblings(true)
        bottomDrawerBehaviorEqualizer.state = BottomSheetBehavior.STATE_HIDDEN

        bottomDrawerBehaviorEqualizer.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    fun initBtnCheck(view: View) {
        var fab_check = view.findViewById<FloatingActionButton>(R.id.fab_check)
        fab_check.setOnClickListener {
            checkWall()
        }
    }

    var isSlideInited = false
    fun initSettingParams(view: View) {
        LogUtil.d("initSettingParams")
        if (isSlideInited) {
            return
        }
        slider_star_num?.let {
            var value = engineHandler.spUtils?.getInt(
                StarrySkyConst.KEY_STARS_COUNT, StarrySkyConst.KEY_INIT_STARS_COUNT
            )
            it.value = value!!.toFloat()
            it.addOnChangeListener(this@StarrySkyConfigFragment)
            it.addOnSliderTouchListener(this@StarrySkyConfigFragment)

        }
        slider_speed?.let {
            var value = engineHandler.spUtils?.getFloat(
                StarrySkyConst.KEY_STARS_SPEED, StarrySkyConst.KEY_INIT_STARS_SPEED
            )
            it.value = value!!
            it.setLabelFormatter { value ->
                "${value}x"
            }
            it.addOnChangeListener(this@StarrySkyConfigFragment)
            it.addOnSliderTouchListener(this@StarrySkyConfigFragment)
        }
        slider_mash?.let {
            var value = engineHandler.spUtils?.getInt(
                StarrySkyConst.KEY_MASH_PERCENT, StarrySkyConst.KEY_INIT_MASH
            )
            it.value = value!!.toFloat()
            it.setLabelFormatter { value ->
                "${value}%"
            }
            it.addOnChangeListener(this@StarrySkyConfigFragment)
            it.addOnSliderTouchListener(this@StarrySkyConfigFragment)
        }
        isSlideInited = true
    }

    var isAdapterInit = false

    fun initColorAdapter(view: View) {
        if (isAdapterInit) {
            return
        }
        var rv_background_color_picker =
            view.findViewById<RecyclerView>(R.id.rv_background_color_picker)
        var colorItems = arrayListOf<ColorItem>()
        engineHandler.backgroundColors.forEach {
            colorItems.add(ColorItem(it, it == engineHandler.backgroundColor))
        }
        var backgroundColorAdapter = ColorPickerAdapter(colorItems)
        backgroundColorAdapter.setOnItemChildClickListener { adapter, view, position ->
            for (colorItem in colorItems) {
                colorItem.isSelected = colorItem == colorItems[position]
            }
            adapter.notifyDataSetChanged()
            engineHandler.backgroundColor = colorItems[position].colorValue
            engineHandler.spUtils?.putInt(
                StarrySkyConst.KEY_BACKGROUND_COLOR, engineHandler.backgroundColor
            )
        }
        rv_background_color_picker?.adapter = backgroundColorAdapter

        var rv_star_color_picker =
            view.findViewById<RecyclerView>(R.id.rv_star_color_picker)
        var starColorItems = arrayListOf<ColorItem>()
        engineHandler.starColors.forEach {
            starColorItems.add(ColorItem(it, it == engineHandler.starColor))
        }
        var starColorAdapter = ColorPickerAdapter(starColorItems)
        starColorAdapter.setOnItemChildClickListener { adapter, view, position ->
            for (colorItem in starColorItems) {
                colorItem.isSelected = colorItem == starColorItems[position]
            }
            adapter.notifyDataSetChanged()
            engineHandler.starColor = starColorItems[position].colorValue
            engineHandler.spUtils?.putInt(
                StarrySkyConst.KEY_STAR_COLOR, engineHandler.starColor
            )
            engineHandler.restart()
        }
        rv_star_color_picker?.adapter = starColorAdapter
        isAdapterInit = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_config_starry_sky, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_config_color -> {
                bottomDrawerBehaviorColor.state = BottomSheetBehavior.STATE_EXPANDED
            }

            R.id.menu_config_equalizer -> {
                bottomDrawerBehaviorEqualizer.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed(): Boolean {
        if (bottomDrawerBehaviorColor.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomDrawerBehaviorColor.state = BottomSheetBehavior.STATE_HIDDEN
            return true
        }

        if (bottomDrawerBehaviorEqualizer.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomDrawerBehaviorEqualizer.state = BottomSheetBehavior.STATE_HIDDEN
            return true
        }

        return false
    }


    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        if (!fromUser) {
            return
        }
        LogUtil.d(TAG, "onValueChange ---- isSliderTouching = $isSliderTouching ")
        when (slider.id) {
            R.id.slider_mash -> {
                engineHandler.apply {
                    spUtils?.putInt(
                        StarrySkyConst.KEY_MASH_PERCENT,
                        value.toInt()
                    )
                    mashColor = ColorUtil.adjustAlpha(Color.BLACK, value / 100)
                }
            }
        }

    }


    override fun onResume() {
        super.onResume()
        surfaceView.post {
            engineHandler.onVisibilityChanged(true)
        }
    }

    override fun onPause() {
        super.onPause()
        engineHandler.onVisibilityChanged(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        slider_star_num?.let {
            it.removeOnChangeListener(this)
            it.removeOnSliderTouchListener(this)
        }

        slider_speed?.let {
            it.removeOnChangeListener(this)
            it.removeOnSliderTouchListener(this)
        }
        slider_mash?.let {
            it.removeOnChangeListener(this)
            it.removeOnSliderTouchListener(this)
        }
    }

    override fun onStartTrackingTouch(slider: Slider) {
        LogUtil.d(TAG, "onStartTrackingTouch ---- ")
        isSliderTouching = true

    }

    override fun onStopTrackingTouch(slider: Slider) {
        LogUtil.d(TAG, "onStopTrackingTouch ---- ")
        isSliderTouching = false
        when (slider.id) {
            R.id.slider_star_num -> {
                engineHandler.apply {
                    spUtils?.putInt(
                        StarrySkyConst.KEY_STARS_COUNT,
                        slider.value.toInt()
                    )
                    starCount = slider.value.toInt()
                    restart()
                }
            }
            R.id.slider_speed -> {
                engineHandler.apply {
                    spUtils?.putFloat(
                        StarrySkyConst.KEY_STARS_SPEED,
                        slider.value
                    )
                    speed = slider.value
                    restart()
                }
            }

        }
    }


}