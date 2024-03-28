package com.example.admindata.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.example.admindata.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient


class HomeFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var placesClient: PlacesClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        Places.initialize(requireContext(), getString(R.string.google_maps_key))
        placesClient = Places.createClient(requireContext())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val searchView = rootView.findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchPlaces(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes if needed
                return false
            }
        })

        return  rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Move the camera to a default location
        val defaultLocation = LatLng(37.7749, -122.4194)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))
    }
    private fun searchPlaces(query: String) {
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)

        // Define a new FindAutocompletePredictionsRequest
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()

        // Retrieve autocomplete predictions
        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            for (prediction in response.autocompletePredictions) {
                val placeId = prediction.placeId

                // Fetch place details using the place ID
                val placeRequest = FetchPlaceRequest.builder(placeId, fields).build()

                placesClient.fetchPlace(placeRequest).addOnSuccessListener { placeResponse ->
                    val place = placeResponse.place

                    // Extract location information
                    val latLng = place.latLng
                    if (latLng != null) {
                        // Add marker to the map
                        mMap.addMarker(MarkerOptions().position(latLng).title(place.name))

                        // Move camera to the selected location
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    }
                }.addOnFailureListener { exception ->
                    // Handle failure while fetching place details
                }
            }
        }.addOnFailureListener { exception ->
            // Handle failure while fetching autocomplete predictions
        }
    }

}