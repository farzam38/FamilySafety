package com.example.familysafety

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.familysafety.databinding.FragmentMapsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ola.mapsdk.interfaces.OlaMapCallback
import com.ola.mapsdk.model.OlaLatLng
import com.ola.mapsdk.view.OlaMap
import com.ola.mapsdk.view.OlaMapView

class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var olaMapView: OlaMapView
    private lateinit var fetchLocation: FloatingActionButton
    private lateinit var olaMap2: OlaMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        olaMapView = binding.mapView
        fetchLocation = binding.userLoc

        fetchLocation.setOnClickListener {
            val currentLocation: OlaLatLng? = olaMap2.getCurrentLocation()
            if (currentLocation != null) {
                olaMap2.moveCameraToLatLong(currentLocation, 15.0, 2000)
            } else {
                // Handle the case where the location is not available
            }
        }

        olaMapView.getMap(
            apiKey = "UKgJQ6Uryy7lLCHKhukCWIst6wKV4TPuxqwCaXhn",
            olaMapCallback = object : OlaMapCallback {
                override fun onMapReady(olaMap: OlaMap) {
                    // Map is ready to use
                    olaMap2 = olaMap

                    olaMap2.showCurrentLocation()
                }

                override fun onMapError(error: String) {
                    // Handle map error
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapsFragment()
    }
}

