package com.bethena.walls.starry_sky

import android.os.Bundle
import android.view.*
import android.view.SurfaceHolder.Callback
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class StarrySkySettingFragment : Fragment() {

    lateinit var engineHandler: StarrySkyEngineHandler
    lateinit var surfaceView: SurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        engineHandler = StarrySkyEngineHandler(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_starry_sky_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<View>()
        surfaceView = view.findViewById<SurfaceView>(R.id.surfaceView)
        surfaceView.holder.addCallback(object : Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                engineHandler.onCreate(holder)
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

//        var bottomSheetDialogFragment = BottomSheetDialogFragment()
//        bottomSheetDialogFragment.show(parentFragmentManager,"")
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


}